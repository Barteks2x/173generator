package com.github.barteks2x.b173gen.regenbiomes;

import com.github.barteks2x.b173gen.Generator;
import com.github.barteks2x.b173gen.biome.BetaBiome;
import com.github.barteks2x.b173gen.generator.ChunkProviderGenerate;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

class FindChunksAndRegenBiomesSyncTask extends BukkitRunnable {
    private final Generator plugin;
    private final CommandSender sender;
    private final UUID worldUuid;
    private final List<RegionInfo> regionFiles;
    private final Queue<ChunkCoords> chunks;
    private final ChunkUpdateProgress progress;
    private int regionIndex = 0;
    private final int maxMillis;
    private final TaskStatus status;

    //TODO: IMPORTANT - REFACTOR IT
    private FindChunksAndRegenBiomesSyncTask(Generator plugin, CommandSender sender, UUID worldUuid, List<RegionInfo> regionFiles, int maxMillis, TaskStatus status, int regionIndex, Queue<ChunkCoords> chunks, ChunkUpdateProgress progress) {
        this.plugin = plugin;
        this.sender = sender;
        this.worldUuid = worldUuid;
        this.regionFiles = regionFiles;
        this.progress = progress;
        this.progress.setMax(this.regionFiles.size());
        this.maxMillis = maxMillis;
        this.status = status;
        this.regionIndex = regionIndex;
        this.chunks = chunks;
    }
    
    FindChunksAndRegenBiomesSyncTask(Generator plugin, CommandSender sender, UUID worldUuid, List<RegionInfo> regionFiles, int maxMillis, TaskStatus status) {
        this(plugin, sender, worldUuid, regionFiles, maxMillis, status, 0, new ArrayDeque<ChunkCoords>(32*32), new ChunkUpdateProgress());
    }

    public void run() {
        this.plugin.getLogger().finest("FindChunksAndRegenBiomesSyncTask tick");
        if (this.chunks.isEmpty()) {
            if (regionIndex == this.regionFiles.size()) {
                plugin.getLogger().info("Finished regenerating biomes.");
                status.finished = true;
                return;
            }
            this.progress.increment();
            this.sender.sendMessage(this.progress.getMessage());
            this.plugin.getLogger().finest("FindChunksAndRegenBiomesSyncTask - next region");
            RegionInfo region = this.regionFiles.get(regionIndex);
            regionIndex++;
            this.getChunksInRegion(region, chunks);
            this.scheduleItself();
            return;
        }
        this.scheduleItself();
        this.regenBiomes();
    }

    private Queue<ChunkCoords> getChunksInRegion(RegionInfo region, Queue<ChunkCoords> chunks) {
        plugin.getLogger().log(Level.FINE, "Reading region: {0}", region.getFile().getName());
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(region.getFile()));
            byte[] data = new byte[4096];
            int read = bis.read(data);
            if (read != 4096) {
                plugin.getLogger().log(Level.WARNING, "Corrupted region file: {0}", region.getFile().getName());
                return chunks;
            }
            int chunkXBase = region.getX() << 5;
            int chunkZBase = region.getZ() << 5;
            for (int x = 0; x < 32; ++x) {
                for (int z = 0; z < 32; ++z) {
                    int index = 4 * ((x & 31) + (z & 31) * 32);
                    if (data[index] != 0 || data[index + 1] != 0 || data[index + 2] != 0 || data[index + 3] != 0) {
                        plugin.getLogger().log(Level.FINER, "Found chunk at {0}, {1}", new Object[]{chunkXBase | x, chunkZBase | z});
                        chunks.add(new ChunkCoords(chunkXBase | x, chunkZBase | z));
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            plugin.getLogger().log(Level.WARNING, "Couldn''t open region file: {0} - {1}", new Object[]{region.getFile().getName(), ex.getClass().getName()});
        } catch (IOException ex) {
            plugin.getLogger().log(Level.WARNING, "Couldn''t open region file: {0} - {1}", new Object[]{region.getFile().getName(), ex.getClass().getName()});
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException ex) {
                    plugin.getLogger().log(Level.SEVERE, "Error occurred when closing input stream, continuing: ", ex);
                    // ignore?
                }
            }
        }
        return chunks;
    }

    private void regenBiomes() {
        long time = System.currentTimeMillis();
        do {
            plugin.getLogger().log(Level.FINER, "FindChunksAndRegenBiomesSyncTask - nextChunk");
            ChunkCoords coords = this.chunks.poll();
            if (coords == null) {
                plugin.getLogger().log(Level.FINER, "FindChunksAndRegenBiomesSyncTask - queue is empty");
                break;
            }
            if (!this.regenBiomesInChunk(coords)) {
                plugin.getLogger().log(Level.WARNING, "FindChunksAndRegenBiomesSyncTask - regenBiomesInChunk failed");
                break;
            }
        } while (System.currentTimeMillis() - time < this.maxMillis);
    }

    private boolean regenBiomesInChunk(ChunkCoords c) {
        World world = Bukkit.getServer().getWorld(worldUuid);
        if (world == null) {
            plugin.getLogger().log(Level.WARNING, "World with UUID: {0} doesn''t exist. Did you unload the world?", worldUuid.toString());
            this.status.successful = false;
            this.cancelTask();
            return false;
        }
        ChunkProviderGenerate gen = plugin.getDefaultWorldGenerator(world.getName(), null);
        int chunkX = c.getX();
        int chunkZ = c.getZ();
        boolean loadedBefore = world.isChunkLoaded(chunkX, chunkZ);
        world.loadChunk(chunkX, chunkZ, false);
        Chunk chunk = world.getChunkAt(chunkX, chunkZ);
        BetaBiome[] biomeArray = new BetaBiome[256];
        biomeArray = gen.wcm.getBiomes(biomeArray, chunkX << 4, chunkZ << 4, 16, 16);
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                world.setBiome(chunkX << 4 | x, chunkZ << 4 | z, biomeArray[x << 4 | z].getBiome(gen.getConfig()));
            }
        }
        if (!loadedBefore) {
            chunk.unload();
        }
        return true;
    }

    private void cancelTask() {
        int id = this.getTaskId();
        Bukkit.getScheduler().cancelTask(id);
    }

    private void scheduleItself() {
        new FindChunksAndRegenBiomesSyncTask(plugin, sender, worldUuid, regionFiles, maxMillis, status, regionIndex, chunks, progress).runTaskLater(plugin, 1);
    }

}

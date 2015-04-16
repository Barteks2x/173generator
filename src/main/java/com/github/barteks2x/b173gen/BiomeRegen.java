package com.github.barteks2x.b173gen;

import com.github.barteks2x.b173gen.biome.BetaBiome;
import com.github.barteks2x.b173gen.oldgen.WorldChunkManagerOld;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BiomeRegen {

    public static void regenBiomes(final World world, final CommandSender sender, final Generator plugin, final WorldChunkManagerOld wcm) {
        sender.sendMessage("Reading world region data...");
        File worldFolder = world.getWorldFolder();
        File regionFolder = new File(worldFolder, "region");
        File[] regions = regionFolder.listFiles();
        final List<ChunkCoords> chunks = new LinkedList<ChunkCoords>();
        final ChunkUpdateProgress progress = new ChunkUpdateProgress();
        for(File regionFile: regions) {
            String name = regionFile.getName();
            if(!name.startsWith("r.") || !name.endsWith(".mca")) {
                plugin.getLogger().log(Level.WARNING, "non-region file: {0}", name);
                continue;
            }
            String s[] = name.split("\\.");//"." is a reserved character in regular expression (use "\." instead)
            if(s.length != 4) {
                plugin.getLogger().log(Level.WARNING, "incorrect region file name: {0} length: {1}", new Object[] {name, s.length});
                continue;
            }
            int x, z;
            try {
                x = Integer.parseInt(s[1]);
                z = Integer.parseInt(s[2]);
            } catch(NumberFormatException ex) {
                plugin.getLogger().log(Level.WARNING, "Couldn't parse region position: {0}", name);
                continue;
            } catch(NullPointerException ex) {
                plugin.getLogger().log(Level.WARNING, "Couldn't parse region position: {0}", name);
                continue;
            }
            progress.max += regenBiomesInRegion(world, (sender instanceof Player) ? (Player)sender : null, regionFile, x, z, chunks, plugin);
        }
        final Iterator<ChunkCoords> it = chunks.iterator();
        new Task(plugin, it, world, wcm, progress, (sender instanceof Player) ? sender.getName() : null).runTaskLater(plugin, 1);

    }

    private static int regenBiomesInRegion(World world, Player sender, File regionFile, int regionX, int regionZ, List<ChunkCoords> chunks, Generator plugin) {
        BufferedInputStream bis = null;
        int chunksToUpdate = 0;
        try {
            bis = new BufferedInputStream(new FileInputStream(regionFile));
            byte[] data = new byte[4096];
            int read = bis.read(data);
            if(read != 4096) {
                plugin.getLogger().log(Level.WARNING, "Corrupted region file: {0}", regionFile.getName());
                return chunksToUpdate;
            }
            int chunkXBase = regionX << 5;
            int chunkZBase = regionZ << 5;
            for(int x = 0; x < 32; ++x) {
                for(int z = 0; z < 32; ++z) {
                    int i = 4 * (w(x % 32) + w(z % 32) * 32);
                    if(data[i] != 0 || data[i + 1] != 0 || data[i + 2] != 0 || data[i + 3] != 0) {
                        chunks.add(new ChunkCoords(chunkXBase | x, chunkZBase | z));
                        ++chunksToUpdate;
                    }
                }
            }

        } catch(FileNotFoundException ex) {
            plugin.getLogger().log(Level.WARNING, "Couldn''t open region file: {0} - {1}", new Object[] {regionFile.getName(), ex.getClass().getName()});
        } catch(IOException ex) {
            plugin.getLogger().log(Level.WARNING, "Couldn''t open region file: {0} - {1}", new Object[] {regionFile.getName(), ex.getClass().getName()});
        } finally {
            if(bis != null) {
                try {
                    bis.close();
                } catch(IOException ex) {

                    //ignore?
                }
            }
        }
        return chunksToUpdate;
    }

    private static int w(int a) {
        return a < 0 ? a + 32 : a;
    }

    private BiomeRegen() {
        throw new RuntimeException("You can't instantiate this class!");
    }

    private static class ChunkCoords {
        private final int x, z;

        private ChunkCoords(int x, int z) {
            this.x = x;
            this.z = z;
        }
    }

    private static class ChunkUpdateProgress {
        int max, done;

        private ChunkUpdateProgress() {
        }

        private void increment() {
            ++done;
        }
    }

    private static class Task extends BukkitRunnable {
        private final Iterator<ChunkCoords> it;
        private final World world;
        private final WorldChunkManagerOld wcm;
        private final ChunkUpdateProgress progress;
        private final Generator plugin;
        private final String player;

        private Task(Generator plugin, Iterator<ChunkCoords> it, World world, WorldChunkManagerOld wcm, ChunkUpdateProgress progress, String player) {
            this.plugin = plugin;
            this.it = it;
            this.world = world;
            this.wcm = wcm;
            this.progress = progress;
            this.player = player;
        }

        public void run() {
            for(int i = 0; i < 32 && it.hasNext(); ++i) {
                ChunkCoords c = it.next();
                boolean loadedBefore = world.isChunkLoaded(c.x, c.z);
                world.loadChunk(c.x, c.z, false);
                Chunk chunk = world.getChunkAt(c.x, c.z);
                BetaBiome[] biomeArray = new BetaBiome[256];
                biomeArray = wcm.getBiomes(biomeArray, c.z << 4, c.z << 4, 16, 16);

                for(int x = 0; x < 16; ++x) {
                    for(int z = 0; z < 16; ++z) {
                        world.setBiome(c.x << 4 | x, c.z << 4 | z, biomeArray[x << 4 | z].getBiome());
                    }
                }
                progress.increment();
                if(!loadedBefore) {
                    chunk.unload();
                }
            }

            if(progress.max == 0) {//Division by 0 isn't allowed
                progress.done = 1;
                progress.max = 1;
            }

            plugin.getLogger().log(Level.INFO, "Regenerating biomes: {0}/{1} ({2}%)", new Object[] {progress.done, progress.max, (progress.done * 100) / progress.max});
            if(it.hasNext()) {
                new Task(plugin, it, world, wcm, progress, player).runTaskLater(plugin, 1);
            } else {
                plugin.getLogger().info("Regenerating biomes: done!");
                if(player != null && plugin.getServer().getPlayer(player) != null) {
                    plugin.getServer().getPlayer(player).sendMessage("Regenerating biomes: done!");
                }
            }
        }

    }
}

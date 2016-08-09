package com.github.barteks2x.b173gen.test.fakeimpl;

import com.github.barteks2x.b173gen.test.util.ChunkData;
import com.github.barteks2x.b173gen.test.util.IChunkSource;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BukkitWorldStub implements World {

    private long seed;
    private String name;
    private IChunkSource chunkSource;

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChunkSource(IChunkSource chunkSource) {
        this.chunkSource = chunkSource;
    }

    @Override
    public long getSeed() {
        return this.seed;
    }

    @Override
    public String getName() {
        return this.name;
    }

    //Simple world implementation:
    @Override
    public Block getBlockAt(int x, int y, int z) {
        ChunkData chunk = chunkSource.getChunkData(x >> 4, z >> 4);
        if (chunk == null || y >= 128 || y < 0) {
            return new BukkitBlockStub(this, x, y, z, Material.AIR);
        }
        return new BukkitBlockStub(this, x, y, z, chunk.getBlock(x & 0xF, y, z & 0xF));
    }

    public void setTypeDirectly(int x, int y, int z, Material type) {
        ChunkData chunk = chunkSource.getChunkData(x >> 4, z >> 4);
        if (chunk == null || y >= 128 || y < 0) {
            return;
        }
        chunk.setBlock(x & 0xF, y, z & 0xF, type);
    }

    public ChunkData getChunkDataAt(int x, int z) {
        return chunkSource.getChunkData(x, z);
    }

    //--------------------------------
    //         UNIMPLEMENTED
    //--------------------------------


    @Override
    public Block getBlockAt(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBlockTypeIdAt(int i, int i1, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBlockTypeIdAt(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getHighestBlockYAt(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getHighestBlockYAt(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getHighestBlockAt(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getHighestBlockAt(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk getChunkAt(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk getChunkAt(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk getChunkAt(Block block) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isChunkLoaded(Chunk chunk) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk[] getLoadedChunks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadChunk(Chunk chunk) {
    }

    @Override
    public boolean isChunkLoaded(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isChunkInUse(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadChunk(int i, int i1) {
    }

    @Override
    public boolean loadChunk(int i, int i1, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunk(Chunk chunk) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunk(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunk(int i, int i1, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunk(int i, int i1, boolean b, boolean b1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunkRequest(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunkRequest(int i, int i1, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean regenerateChunk(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean refreshChunk(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Item dropItem(Location location, ItemStack itemStack) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Item dropItemNaturally(Location location, ItemStack itemStack) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Arrow spawnArrow(Location location, Vector vector, float v, float v1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Arrow> T spawnArrow(Location location, Vector vector, float v, float v1, Class<T> aClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean generateTree(Location location, TreeType treeType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean generateTree(Location location, TreeType treeType, BlockChangeDelegate blockChangeDelegate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entity spawnEntity(Location location, EntityType entityType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LightningStrike strikeLightning(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LightningStrike strikeLightningEffect(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Entity> getEntities() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<LivingEntity> getLivingEntities() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T>... classes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> aClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Entity> getEntitiesByClasses(Class<?>... classes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Player> getPlayers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Entity> getNearbyEntities(Location location, double v, double v1, double v2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UUID getUID() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location getSpawnLocation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setSpawnLocation(int i, int i1, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTime(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getFullTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFullTime(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasStorm() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStorm(boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getWeatherDuration() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWeatherDuration(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isThundering() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setThundering(boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getThunderDuration() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setThunderDuration(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(double v, double v1, double v2, float v3) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(double v, double v1, double v2, float v3, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(double v, double v1, double v2, float v3, boolean b, boolean b1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(Location location, float v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(Location location, float v, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Environment getEnvironment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getPVP() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPVP(boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChunkGenerator getGenerator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<BlockPopulator> getPopulators() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Entity> T spawn(Location location, Class<T> aClass) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public FallingBlock spawnFallingBlock(Location location, Material material, byte b) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public FallingBlock spawnFallingBlock(Location location, int i, byte b) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void playEffect(Location location, Effect effect, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void playEffect(Location location, Effect effect, int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void playEffect(Location location, Effect effect, T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void playEffect(Location location, Effect effect, T t, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChunkSnapshot getEmptyChunkSnapshot(int i, int i1, boolean b, boolean b1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSpawnFlags(boolean b, boolean b1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAllowAnimals() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAllowMonsters() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Biome getBiome(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBiome(int i, int i1, Biome biome) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getTemperature(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getHumidity(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxHeight() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getSeaLevel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getKeepSpawnInMemory() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setKeepSpawnInMemory(boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAutoSave() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAutoSave(boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDifficulty(Difficulty difficulty) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Difficulty getDifficulty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public File getWorldFolder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public WorldType getWorldType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canGenerateStructures() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTicksPerAnimalSpawns() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTicksPerAnimalSpawns(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTicksPerMonsterSpawns() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTicksPerMonsterSpawns(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMonsterSpawnLimit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMonsterSpawnLimit(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAnimalSpawnLimit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAnimalSpawnLimit(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWaterAnimalSpawnLimit(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAmbientSpawnLimit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAmbientSpawnLimit(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void playSound(Location location, Sound sound, float v, float v1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void playSound(Location location, String s, float v, float v1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String[] getGameRules() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getGameRuleValue(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setGameRuleValue(String s, String s1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isGameRule(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public WorldBorder getWorldBorder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnParticle(Particle particle, Location location, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnParticle(Particle particle, double v, double v1, double v2, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int i, T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, double v, double v1, double v2, int i, T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2, T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2, double v3) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2, double v3, T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6, T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMetadata(String s, MetadataValue metadataValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MetadataValue> getMetadata(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasMetadata(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeMetadata(String s, Plugin plugin) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendPluginMessage(Plugin plugin, String s, byte[] bytes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getListeningPluginChannels() {
        throw new UnsupportedOperationException();
    }
}

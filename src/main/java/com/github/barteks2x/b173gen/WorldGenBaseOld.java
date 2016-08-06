package com.github.barteks2x.b173gen;

import java.util.Random;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public abstract class WorldGenBaseOld {
    protected int maxGenerationRadius;
    protected Random rand;

    public WorldGenBaseOld() {
        maxGenerationRadius = 8;
        rand = new Random();
    }

    @SuppressWarnings("cast")
    public void generate(World world, int generatedChunkX, int generatedChunkZ, ChunkGenerator.ChunkData data) {
        int radius = maxGenerationRadius;
        rand.setSeed(world.getSeed());
        long l = (rand.nextLong() / 2L) * 2L + 1L;
        long l1 = (rand.nextLong() / 2L) * 2L + 1L;
        for(int structureOriginXhunkX = generatedChunkX - radius; structureOriginXhunkX <= generatedChunkX + radius; structureOriginXhunkX++) {
            for(int structureOriginChunkZ = generatedChunkZ - radius; structureOriginChunkZ <= generatedChunkZ + radius; structureOriginChunkZ++) {
                rand.setSeed((long)structureOriginXhunkX * l + (long)structureOriginChunkZ * l1 ^ world.getSeed());
                generate(world, structureOriginXhunkX, structureOriginChunkZ, generatedChunkX, generatedChunkZ, data);
            }
        }
    }

    protected abstract void generate(World world, int structureOriginChunkX, int structureOriginChunkZ, int generatedChunkX, int generatedChunkZ, ChunkGenerator.ChunkData data);
}

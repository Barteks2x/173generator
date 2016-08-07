package com.github.barteks2x.b173gen.generator;

import com.github.barteks2x.b173gen.biome.BetaBiome;
import com.github.barteks2x.b173gen.oldgen.WorldChunkManagerOld;
import org.bukkit.World;

import java.util.Random;

public class PopulatorState {
    private final Random random;
    private final int chunkX;
    private final int chunkZ;
    private final BetaBiome biome;
    private final double[] temperatures;

    public PopulatorState(Builder builder) {
        this.random = builder.random;
        this.chunkX = builder.chunkX;
        this.chunkZ = builder.chunkZ;
        this.biome = builder.biome;
        this.temperatures = builder.temperatures;
    }

    public boolean isChunk(int chunkX, int chunkZ) {
        return this.chunkX == chunkX && this.chunkZ == chunkZ;
    }

    public Random getRng() {
        return random;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public static PopulatorState forChunk(World world, WorldChunkManagerOld wcm, int chunkX, int chunkZ) {
        Random rand = new Random(world.getSeed());
        long randX = (rand.nextLong() / 2L) * 2L + 1L;
        long randZ = (rand.nextLong() / 2L) * 2L + 1L;
        rand.setSeed((long) chunkX * randX + (long) chunkZ * randZ ^ world.getSeed());
        return new Builder().
                setRandom(rand).
                setChunkX(chunkX).setChunkZ(chunkZ).
                setBiome(wcm.getBiome(chunkX * 16 + 16, chunkZ * 16 + 16)).
                setTemperatures(wcm.getTemperatures(null, chunkX * 16 + 8, chunkZ * 16 + 8, 16, 16)).
                build();
    }

    public int getBlockX() {
        return chunkX * 16 + 8;
    }

    public int getBlockZ() {
        return chunkZ * 16 + 8;
    }

    public BetaBiome getBiome() {
        return biome;
    }

    public double[] getTemperatures() {
        return temperatures;
    }

    public static class Builder {
        private Random random;
        private int chunkX;
        private int chunkZ;
        private BetaBiome biome;
        private double[] temperatures;

        public Builder setRandom(Random random) {
            this.random = random;
            return this;
        }

        public Builder setChunkX(int chunkX) {
            this.chunkX = chunkX;
            return this;
        }

        public Builder setChunkZ(int chunkZ) {
            this.chunkZ = chunkZ;
            return this;
        }

        public Builder setBiome(BetaBiome biome) {
            this.biome = biome;
            return this;
        }

        public Builder setTemperatures(double[] temperatures) {
            this.temperatures = temperatures;
            return this;
        }

        public PopulatorState build() {
            return new PopulatorState(this);
        }
    }
}

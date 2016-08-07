package com.github.barteks2x.b173gen.biome;

import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.oldnoisegen.NoiseGeneratorOctaves3D;
import org.bukkit.block.Biome;

import java.util.Random;

public class BetaBiome {

    public static final BetaBiome RAINFOREST = new BetaBiome("Rainforest", "RAINFOREST", "JUNGLE"),
            SWAMPLAND = new BetaBiome("Swampland", "SWAMPLAND"),
            SEASONAL_FOREST = new BetaBiome("Seasonal forest", "SEASONAL_FOREST", "SEASONALFOREST", "FOREST"),
            FOREST = new BetaBiome("Forest", "FOREST"),
            SAVANNA = new BetaBiome("Savanna", "SAVANNA", "PLAINS"),
            SHRUBLAND = new BetaBiome("Shrubland", "SHRUBLAND", "PLAINS"),
            TAIGA = new BetaBiome("Taiga", "TAIGA"),
            DESERT = new BetaBiome("Desert", "DESERT"),
            PLAINS = new BetaBiome("Plains", "PLAINS"),
            ICE_DESERT = new BetaBiome("Ice desert", "ICE_DESERT", "COLD_BEACH", "DESERT"),
            TUNDRA = new BetaBiome("Tundra", "TUNDRA", "ICE_PLAINS", "ICEPLAINS", "TAIGA");

    private final Biome bukkitBiome;
    private final String name;

    BetaBiome(String name, String... bukkitNames) {
        this.name = name;
        Biome temp = null;
        for (String n : bukkitNames) {
            try {
                temp = Biome.valueOf(n);
                break;//break if no exception (value exists)
            } catch (Exception ex) {
            }
        }
        this.bukkitBiome = temp != null ? temp : Biome.PLAINS;
    }

    public Biome getBiome(WorldConfig cfg) {
        if (cfg.noswamps && this.bukkitBiome == Biome.SWAMPLAND) {
            return Biome.PLAINS;
        }
        return bukkitBiome;
    }

    public String getName() {
        return this.name;
    }

    public int getTreesPerChunk(Random random, NoiseGeneratorOctaves3D noiseGen, int chunkX, int chunkZ) {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int treesRand = (int) ((noiseGen.generateNoise(x * 0.5D, z * 0.5D) / 8D + random.nextDouble() * 4D + 4D) / 3D);

        int trees = 0;
        if (random.nextInt(10) == 0) {
            trees++;
        }
        if (this == FOREST) {
            trees += treesRand + 5;
        }

        if (this == RAINFOREST) {
            trees += treesRand + 5;
        }

        if (this == SEASONAL_FOREST) {
            trees += treesRand + 2;
        }

        if (this == TAIGA) {
            trees += treesRand + 5;
        }

        if (this == DESERT) {
            trees -= 20;
        }

        if (this == TUNDRA) {
            trees -= 20;
        }

        if (this == PLAINS) {
            trees -= 20;
        }
        return trees;
    }

    public int getCactusForBiome() {
        int k16 = 0;
        if (equals(DESERT)) {
            k16 += 10;
        }
        return k16;
    }

    public int getDeadBushPerChunk() {
        int byte1 = 0;
        if (equals(DESERT)) {
            byte1 = 2;
        }
        return byte1;
    }

    public int getGrassPerChunk() {
        int byte1 = 0;
        if (equals(FOREST)) {
            byte1 = 2;
        }

        if (equals(RAINFOREST)) {
            byte1 = 10;
        }

        if (equals(SEASONAL_FOREST)) {
            byte1 = 2;
        }

        if (equals(TAIGA)) {
            byte1 = 1;
        }

        if (equals(PLAINS)) {
            byte1 = 10;
        }
        return byte1;
    }

    public int getFlowersPerChunk() {
        int flowers = 0;
        if (equals(FOREST)) {
            flowers = 2;
        }

        if (equals(SEASONAL_FOREST)) {
            flowers = 4;
        }

        if (equals(TAIGA)) {
            flowers = 2;
        }

        if (equals(PLAINS)) {
            flowers = 3;
        }
        return flowers;
    }
}

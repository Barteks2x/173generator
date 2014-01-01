package com.github.barteks2x.b173gen.biome;

import static com.github.barteks2x.b173gen.biome.BetaBiomeEnum.*;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import com.github.barteks2x.b173gen.oldgen.*;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.Biome;

public class BiomeOld {

    private static final BetaBiomeEnum[] biomeLookupTable = new BetaBiomeEnum[4096];

    static {
        generateBiomeLookup();
    }

    public static void generateBiomeLookup() {
        for(int i = 0; i < 64; ++i) {
            for(int j = 0; j < 64; ++j) {
                biomeLookupTable[(i + j * 64)] = getBiome(i / 63.0F, j / 63.0F);
            }
        }
    }

    public static WorldGenerator173 getRandomTreeGen(Random rand, BetaBiomeEnum biome) {
        if(biome.getBiome() == Biome.FOREST) {
            return rand.nextInt(5) == 0 ? new WorldGenForestOld() : (rand.nextInt(3) == 0
                    ? new WorldGenBigTreeOld() : new WorldGenTreeOld());
        }
        if(biome.getBiome() == Biome.JUNGLE) {
            return rand.nextInt(3) == 0 ? new WorldGenBigTreeOld() : new WorldGenTreeOld();
        }
        if(biome.getBiome() == Biome.TAIGA) {
            return rand.nextInt(3) == 0 ? new WorldGenTaiga1Old() : new WorldGenTaiga2Old();
        }
        return (rand.nextInt(10) == 0 ? new WorldGenBigTreeOld() : new WorldGenTreeOld());
    }

    public static byte top(Biome biome) {
        if(biome == Biome.DESERT) {
            return (byte)Material.SAND.getId();
        }
        return (byte)Material.GRASS.getId();
    }

    public static byte filler(Biome biome) {
        if(biome == Biome.DESERT) {
            return (byte)Material.SAND.getId();
        }
        return (byte)Material.DIRT.getId();
    }

    public static BetaBiomeEnum getBiomeFromLookup(double d, double d1) {
        int i = (int)(d * 63.0D);
        int j = (int)(d1 * 63.0D);
        return biomeLookupTable[(i + j * 64)];
    }

    public static BetaBiomeEnum getBiome(float f, float f1) {
        f1 *= f;
        return f < 0.1F ? TUNDRA : (f1 < 0.2F ? (f < 0.5F ? TUNDRA
                : (f < 0.95F ? SAVANNA : DESERT)) : (f1 > 0.5F && f < 0.7F ? SWAMPLAND
                : (f < 0.5F ? TAIGA : (f < 0.97F ? (f1 < 0.35F ? SHRUBLAND : FOREST)
                : (f1 < 0.45F ? PLAINS : (f1 < 0.9F ? SEASONAL_FOREST : RAINFOREST))))));
    }

    public WorldGenerator173 getRandomTreeGen(Random random) {
        if(random.nextInt(10) == 0) {
            return new WorldGenBigTreeOld();
        }
        return new WorldGenTreeOld();
    }
}

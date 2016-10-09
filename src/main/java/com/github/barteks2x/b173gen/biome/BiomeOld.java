package com.github.barteks2x.b173gen.biome;

import com.github.barteks2x.b173gen.Generator;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import com.github.barteks2x.b173gen.oldgen.MathHelper;
import com.github.barteks2x.b173gen.oldgen.WorldGenBigTreeOld;
import com.github.barteks2x.b173gen.oldgen.WorldGenBirchTreeOld;
import com.github.barteks2x.b173gen.oldgen.WorldGenGrassOld;
import com.github.barteks2x.b173gen.oldgen.WorldGenTaiga1Old;
import com.github.barteks2x.b173gen.oldgen.WorldGenTaiga2Old;
import com.github.barteks2x.b173gen.oldgen.WorldGenTreeOld;
import org.bukkit.GrassSpecies;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.Configuration;
import org.bukkit.material.LongGrass;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import static com.github.barteks2x.b173gen.biome.BetaBiome.DESERT;
import static com.github.barteks2x.b173gen.biome.BetaBiome.FOREST;
import static com.github.barteks2x.b173gen.biome.BetaBiome.PLAINS;
import static com.github.barteks2x.b173gen.biome.BetaBiome.RAINFOREST;
import static com.github.barteks2x.b173gen.biome.BetaBiome.SAVANNA;
import static com.github.barteks2x.b173gen.biome.BetaBiome.SEASONAL_FOREST;
import static com.github.barteks2x.b173gen.biome.BetaBiome.SHRUBLAND;
import static com.github.barteks2x.b173gen.biome.BetaBiome.SWAMPLAND;
import static com.github.barteks2x.b173gen.biome.BetaBiome.TAIGA;
import static com.github.barteks2x.b173gen.biome.BetaBiome.TUNDRA;
import static org.bukkit.Material.LONG_GRASS;

public class BiomeOld {

    private static final BetaBiome[] biomeLookupTable = new BetaBiome[4096];

    public static void generateBiomeLookup() {
        for(int i = 0; i < 64; ++i) {
            for(int j = 0; j < 64; ++j) {
                biomeLookupTable[(i + j * 64)] = getBiome(i / 63.0F, j / 63.0F);
            }
        }
    }

    public static void init(Configuration config) {
        if(config.getBoolean("global.experimental.biomesExperimental", true)) {
            HashMap<Biome, Float> temp = new HashMap<Biome, Float>(128);
            HashMap<Biome, Float> humid = new HashMap<Biome, Float>(128);
            Biome[] array = Biome.values();
            int a = 0;
            for(Biome b: array) {
                if(!config.contains("global.experimental.biomes." + b.name())) {
                    continue;
                }
                List<Double> values = config.getDoubleList("global.experimental.biomes." + b.name());
                ++a;
                Iterator<Double> it = values.iterator();
                if(!it.hasNext()) {
                    Generator.logger().log(Level.WARNING, "Incorrect biome data format: {0}", b.name());
                    continue;
                }
                double temperature = it.next();
                if(!it.hasNext()) {
                    Generator.logger().log(Level.WARNING, "Incorrect biome data format: {0}", b.name());
                    continue;
                }
                double humidity = it.next();
                temperature *= 0.1;
                humidity *= 0.1;
                //System.out.println("biome: " + b.name() + ", " + b.toString() + " t: " + temperature + " h: " + humidity);
                temp.put(b, (float)MathHelper.clamp(temperature, 0.0, 1.0));
                humid.put(b, (float)MathHelper.clamp(humidity, 0.0, 1.0));
            }
            for(int i = 0; i < 64; ++i) {
                for(int j = 0; j < 64; ++j) {
                    int index = (i + j * 64);
                    float biomeTemp = i / 63.0F;
                    float biomeHumid = j / 63.0F;
                    Biome lastBiome = array[0];
                    float lastDistSquared = Float.MAX_VALUE;
                    for(Biome b: temp.keySet()) {
                        float dt = biomeTemp - temp.get(b);
                        float dh = biomeHumid - humid.get(b);
                        float distSquared = dt * dt + dh * dh;
                        if(distSquared < lastDistSquared) {
                            lastBiome = b;
                            lastDistSquared = distSquared;
                        }
                    }
                    biomeLookupTable[index] = new BetaBiome(getBiome(biomeTemp, biomeHumid).getName(), lastBiome.name());
                }
            }
        } else {
            generateBiomeLookup();
        }
    }

    public static WorldGenerator173 getBiomeTreeGenerator(Random rand, BetaBiome biome) {
        if(FOREST.equals(biome)) {
            return rand.nextInt(5) == 0 ? new WorldGenBirchTreeOld() : (rand.nextInt(3) == 0
                    ? new WorldGenBigTreeOld() : new WorldGenTreeOld());
        }
        if(RAINFOREST.equals(biome)) {
            return rand.nextInt(3) == 0 ? new WorldGenBigTreeOld() : new WorldGenTreeOld();
        }
        if(TAIGA.equals(biome)) {
            return rand.nextInt(3) == 0 ? new WorldGenTaiga1Old() : new WorldGenTaiga2Old();
        }
        return (rand.nextInt(10) == 0 ? new WorldGenBigTreeOld() : new WorldGenTreeOld());
    }

    public static WorldGenerator173 getRandomGrassGenerator(Random rand, BetaBiome biome) {
        if (biome.equals(RAINFOREST) && rand.nextInt(3) != 0) {
            return new WorldGenGrassOld(LONG_GRASS, new LongGrass(GrassSpecies.FERN_LIKE));
        } else {
            return new WorldGenGrassOld(LONG_GRASS, new LongGrass(GrassSpecies.NORMAL));
        }
    }

    public static Material top(BetaBiome biome) {
        if(biome.equals(DESERT)) {
            return Material.SAND;
        }
        return Material.GRASS;
    }

    public static Material filler(BetaBiome biome) {
        if(biome.equals(DESERT)) {
            return Material.SAND;
        }
        return Material.DIRT;
    }

    public static BetaBiome getBiomeFromLookup(double d, double d1) {
        int i = (int)(d * 63.0D);
        int j = (int)(d1 * 63.0D);
        return biomeLookupTable[(i + j * 64)];
    }

    private static BetaBiome getBiome(float f, float f1) {
        f1 *= f;
        return f < 0.1F ? TUNDRA
                : (f1 < 0.2F ? (f < 0.5F ? TUNDRA : (f < 0.95F ? SAVANNA : DESERT))
                : (f1 > 0.5F && f < 0.7F ? SWAMPLAND
                : (f < 0.5F ? TAIGA : (f < 0.97F ? (f1 < 0.35F ? SHRUBLAND : FOREST)
                : (f1 < 0.45F ? PLAINS : (f1 < 0.9F ? SEASONAL_FOREST : RAINFOREST))))));
    }
}

package com.github.barteks2x.b173gen.biome;

import com.github.barteks2x.b173gen.Generator;
import static com.github.barteks2x.b173gen.biome.BetaBiome.*;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import com.github.barteks2x.b173gen.oldgen.*;
import java.util.*;
import java.util.logging.Level;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.block.Biome;

public class BiomeOld {

    private static final BetaBiome[] biomeLookupTable = new BetaBiome[4096];

    public static void generateBiomeLookup() {
        for(int i = 0; i < 64; ++i) {
            for(int j = 0; j < 64; ++j) {
                biomeLookupTable[(i + j * 64)] = getBiome(i / 63.0F, j / 63.0F);
            }
        }
    }

    public static void init(Generator plugin) {
        if(plugin.getConfig().getBoolean("global.experimental.biomesExperimental", true)) {
            HashMap<Biome, Float> temp = new HashMap<Biome, Float>(128);
            HashMap<Biome, Float> humid = new HashMap<Biome, Float>(128);
            Biome[] array = Biome.values();
            int a = 0;
            for(Biome b: array) {
                //System.out.println("biome: " + b.name());
                if(!plugin.getConfig().contains("global.experimental.biomes." + b.name())) {
                    continue;
                }
                List<Double> values = plugin.getConfig().getDoubleList("global.experimental.biomes." + b.name());
                ++a;
                Iterator<Double> it = values.iterator();
                if(!it.hasNext()) {
                    plugin.getLogger().log(Level.WARNING, "Incorrect biome data format: {0}", b.name());
                    continue;
                }
                double temperature = it.next();
                if(!it.hasNext()) {
                    plugin.getLogger().log(Level.WARNING, "Incorrect biome data format: {0}", b.name());
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

    public static WorldGenerator173 getRandomTreeGen(Random rand, BetaBiome biome) {
        if(FOREST.equals(biome)) {
            return rand.nextInt(5) == 0 ? new WorldGenForestOld() : (rand.nextInt(3) == 0
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

    public static byte top(BetaBiome biome) {
        if(biome.equals(DESERT)) {
            return (byte)Material.SAND.getId();
        }
        return (byte)Material.GRASS.getId();
    }

    public static byte filler(BetaBiome biome) {
        if(biome.equals(DESERT)) {
            return (byte)Material.SAND.getId();
        }
        return (byte)Material.DIRT.getId();
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

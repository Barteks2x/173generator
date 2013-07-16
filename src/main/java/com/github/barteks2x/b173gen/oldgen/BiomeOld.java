package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.Biome;

public class BiomeOld {

	private static Biome[] biomeLookupTable = new Biome[4096];

	static {
		generateBiomeLookup();
	}

	public static void generateBiomeLookup() {
		for (int i = 0; i < 64; ++i) {
			for (int j = 0; j < 64; ++j) {
				biomeLookupTable[(i + j * 64)] = getBiome(i / 63.0F, j / 63.0F);
			}
		}
	}

	public static WorldGenerator173 getRandomTreeGen(Random rand, Biome biome) {
		if (biome == Biome.FOREST) {
			return rand.nextInt(5) == 0 ? new WorldGenForestOld() : (rand.nextInt(3) == 0 ?
					new WorldGenBigTreeOld() : new WorldGenTreeOld());
		}
		if (biome == Biome.JUNGLE) {
			return rand.nextInt(3) == 0 ? new WorldGenBigTreeOld() : new WorldGenTreeOld();
		}
		if (biome == Biome.TAIGA) {
			return rand.nextInt(3) == 0 ? new WorldGenTaiga1Ref() : new WorldGenTaiga2Ref();
		}
		return (rand.nextInt(10) == 0 ? new WorldGenBigTreeOld() : new WorldGenTreeOld());
	}

	public static byte top(Biome biome) {
		if (biome == Biome.DESERT) {
			return (byte)Material.SAND.getId();
		}
		return (byte)Material.GRASS.getId();
	}

	public static byte filler(Biome biome) {
		if (biome == Biome.DESERT) {
			return (byte)Material.SAND.getId();
		}
		return (byte)Material.DIRT.getId();
	}

	public WorldGenerator173 getRandomTreeGen(Random random) {
		if (random.nextInt(10) == 0) {
			return new WorldGenBigTreeOld();
		}
		return new WorldGenTreeOld();
	}

	public static Biome getBiomeFromLookup(double d, double d1) {
		int i = (int)(d * 63.0D);
		int j = (int)(d1 * 63.0D);
		return biomeLookupTable[(i + j * 64)];
	}

	public static Biome getBiome(float temp, float humid) {
		humid *= temp;
		if (temp < 0.1F) {
			return Biome.ICE_PLAINS;
		}
		if (humid < 0.2F) {
			if (temp < 0.5F) {
				return Biome.ICE_PLAINS;
			}
			if (temp < 0.95F) {
				return Biome.BEACH;
			}

			return Biome.DESERT;
		}

		if ((humid > 0.5F) && (temp < 0.7F)) {
			return Biome.SWAMPLAND;
		}
		if (temp < 0.5F) {
			return Biome.TAIGA;
		}
		if (temp < 0.97F) {
			if (humid < 0.35F) {
				return Biome.DESERT_HILLS;
			}

			return Biome.FOREST;
		}

		if (humid < 0.45F) {
			return Biome.PLAINS;
		}
		if (humid < 0.9F) {
			return Biome.FOREST_HILLS;
		}

		return Biome.JUNGLE;
	}
}

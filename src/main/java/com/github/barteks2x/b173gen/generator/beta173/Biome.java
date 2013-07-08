package com.github.barteks2x.b173gen.generator.beta173;

import java.lang.reflect.Field;
import java.util.Random;
import net.minecraft.server.v1_6_R1.*;

public class Biome extends BiomeBase {

	//public static final BiomeGenBase[] biomeList = new BiomeGenBase[256];
	public static final Biome rainforest = new Biome(BiomeBase.JUNGLE.id);
	public static final Biome swampland = new Biome(BiomeBase.SWAMPLAND.id);
	public static final Biome seasonalForest = new Biome(BiomeBase.FOREST.id);
	public static final Biome forest = new Biome(BiomeBase.FOREST.id);
	public static final Biome savanna = new Biome(BiomeBase.PLAINS.id);
	public static final Biome shrubland = new Biome(BiomeBase.PLAINS.id);
	public static final Biome taiga = new Biome(BiomeBase.TAIGA.id);
	public static final Biome desert = new Biome(BiomeBase.DESERT.id);
	public static final Biome plains = new Biome(BiomeBase.PLAINS.id);
	public static final Biome iceDesert = new Biome(BiomeBase.DESERT.id);
	public static final Biome tundra = new Biome(BiomeBase.ICE_PLAINS.id);
	private static BiomeBase[] biomeLookupTable = new Biome[4096];

	static {
		generateBiomeLookup();
	}

	@SuppressWarnings("MismatchedReadAndWriteOfArray")
	protected Biome(int i) {
		super(255);//DO NOT override biome
		try {
			Field field;
			field = BiomeBase.class.getDeclaredField("id");
			field.setAccessible(true);
			field.setByte(this, (byte)i);
		} catch (NoSuchFieldException ex) {
			throw new ExceptionOnBiomeInitError("Couldn't find field: id");
		} catch (SecurityException ex) {
			throw new ExceptionOnBiomeInitError("Impossible Exception!");
		} catch (IllegalArgumentException ex) {
			throw new ExceptionOnBiomeInitError("Impossible excpetion!");
		} catch (IllegalAccessException ex) {
			throw new ExceptionOnBiomeInitError("Impossible Exception!");
		}
	}

	public static void generateBiomeLookup() {
		for (int i = 0; i < 64; ++i) {
			for (int j = 0; j < 64; ++j) {
				biomeLookupTable[(i + j * 64)] = getBiome(i / 63.0F, j / 63.0F);
			}
		}
		desert.A = (byte)Block.SAND.id;
		desert.B = (byte)Block.SAND.id;
		iceDesert.A = (byte)Block.SAND.id;
		iceDesert.B = (byte)Block.SAND.id;
	}

	public WorldGenerator getRandomTreeGen(Random random) {
		if (random.nextInt(10) == 0) {
			return new WorldGenBigTreeOld();
		}
		return new WorldGenTreeOld();
	}

	public static BiomeBase getBiomeFromLookup(double d, double d1) {
		int i = (int)(d * 63.0D);
		int j = (int)(d1 * 63.0D);
		return biomeLookupTable[(i + j * 64)];
	}

	public static Biome getBiome(float f, float f1) {
		f1 *= f;
		if (f < 0.1F) {
			return tundra;
		}
		if (f1 < 0.2F) {
			if (f < 0.5F) {
				return tundra;
			}
			if (f < 0.95F) {
				return savanna;
			}

			return desert;
		}

		if ((f1 > 0.5F) && (f < 0.7F)) {
			return swampland;
		}
		if (f < 0.5F) {
			return taiga;
		}
		if (f < 0.97F) {
			if (f1 < 0.35F) {
				return shrubland;
			}

			return forest;
		}

		if (f1 < 0.45F) {
			return plains;
		}
		if (f1 < 0.9F) {
			return seasonalForest;
		}

		return rainforest;
	}

	private static class ExceptionOnBiomeInitError extends RuntimeException {

		public ExceptionOnBiomeInitError(String text) {
			super(text);
		}
	}
}

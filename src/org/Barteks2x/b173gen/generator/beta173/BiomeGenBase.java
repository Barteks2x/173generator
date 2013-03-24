package org.Barteks2x.b173gen.generator.beta173;

import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_5_R1.BiomeBase;
import net.minecraft.server.v1_5_R1.Block;
import net.minecraft.server.v1_5_R1.EnumCreatureType;
import net.minecraft.server.v1_5_R1.WorldGenerator;
import org.Barteks2x.b173gen.generator.WorldGenerator173;

public class BiomeGenBase extends BiomeBase {

	public static final BiomeGenBase[] biomeList = new BiomeGenBase[256];
	public static final BiomeGenBase rainforest = new BiomeGenRainforest(21).b(588342).a(
		"Rainforest").a(2094168);
	public static final BiomeGenBase swampland = new BiomeGenSwamp(1).b(8362784).a("Swampland").
		a(9154376);
	public static final BiomeGenBase seasonalForest = new BiomeGenBase(4).b(10215459).a(
		"Seasonal Forest");
	public static final BiomeGenBase forest = new BiomeGenForest(4).b(353825).a("Forest").a(
		5159473);
	public static final BiomeGenBase savanna = new BiomeGenDesert(1, false).b(14278691).a(
		"Savanna");
	public static final BiomeGenBase shrubland = new BiomeGenBase(1).b(10595616).a("Shrubland");
	public static final BiomeGenBase taiga = new BiomeGenTaiga(5).b(3060051).a("Taiga").b().a(
		8107825);
	public static final BiomeGenBase desert = new BiomeGenDesert(2, false).b(16421912).a(
		"Desert").m();
	public static final BiomeGenBase plains = new BiomeGenBase(1).b(16767248).a(
		"Plains");
	public static final BiomeGenBase iceDesert = new BiomeGenDesert(2, true).b(16772499).a(
		"Ice Desert").b().a(12899129);
	public static final BiomeGenBase tundra = new BiomeGenBase(5).b(5762041).a("Tundra").b().a(
		12899129);
	private static BiomeBase[] biomeLookupTable = new BiomeGenBase[4096];

	static {
		generateBiomeLookup();
	}

	protected BiomeGenBase(int i) {
		super(i);
	}

	public static void generateBiomeLookup() {
		for (int i = 0; i < 64; ++i) {
			for (int j = 0; j < 64; ++j) {
				biomeLookupTable[(i + j * 64)] = getBiome(i / 63.0F, j / 63.0F);
			}
		}

		desert.A = (byte) Block.SAND.id;
		desert.B = (byte) Block.SAND.id;
		iceDesert.A = (byte) Block.SAND.id;
		iceDesert.B = (byte) Block.SAND.id;
	}

	@Override
	public WorldGenerator a(Random random) {
		if (random.nextInt(10) == 0) {
			return new WorldGenBigTree();
		}
		return new WorldGenTrees();
	}

	@Override
	protected BiomeGenBase b() {
		super.b();
		return this;
	}

	protected BiomeGenBase m() {
		return this;
	}

	@Override
	protected BiomeGenBase a(String s) {
		super.a(s);
		return this;
	}

	@Override
	protected BiomeGenBase a(int i) {
		this.C = i;
		return this;
	}

	@Override
	protected BiomeGenBase b(int i) {
		super.b(i);
		return this;
	}

	public static BiomeBase getBiomeFromLookup(double d, double d1) {
		int i = (int) (d * 63.0D);
		int j = (int) (d1 * 63.0D);
		return biomeLookupTable[(i + j * 64)];
	}

	public static BiomeGenBase getBiome(float f, float f1) {
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

	@SuppressWarnings("rawtypes")
	@Override
	public List getMobs(EnumCreatureType enumcreaturetype) {
		if (enumcreaturetype == EnumCreatureType.MONSTER) {
			return this.J;
		}
		if (enumcreaturetype == EnumCreatureType.CREATURE) {
			return this.K;
		}
		if (enumcreaturetype == EnumCreatureType.WATER_CREATURE) {
			return this.L;
		}
		return null;
	}

	@Override
	public boolean c() {
		return super.c();
	}

	@Override
	public boolean d() {
		return super.d();
	}
}
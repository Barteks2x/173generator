// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package org.Barteks2x.b173gen.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_4_R1.BiomeBase;
import net.minecraft.server.v1_4_R1.Block;
import net.minecraft.server.v1_4_R1.BlockSand;
import net.minecraft.server.v1_4_R1.Chunk;
import net.minecraft.server.v1_4_R1.ChunkPosition;
import net.minecraft.server.v1_4_R1.EnumCreatureType;
import net.minecraft.server.v1_4_R1.IChunkProvider;
import net.minecraft.server.v1_4_R1.IProgressUpdate;
import net.minecraft.server.v1_4_R1.Material;
import net.minecraft.server.v1_4_R1.WorldGenStronghold;
import net.minecraft.server.v1_4_R1.WorldGenerator;

import org.Barteks2x.b173gen.generator.beta173.BiomeGenBase;
import org.Barteks2x.b173gen.generator.beta173.MapGenBase;
import org.Barteks2x.b173gen.generator.beta173.MapGenCaves;
import org.Barteks2x.b173gen.generator.beta173.NoiseGeneratorOctaves;
import org.Barteks2x.b173gen.generator.beta173.Wcm;
import org.Barteks2x.b173gen.generator.beta173.WorldGenCactus;
import org.Barteks2x.b173gen.generator.beta173.WorldGenClay;
import org.Barteks2x.b173gen.generator.beta173.WorldGenDeadBush;
import org.Barteks2x.b173gen.generator.beta173.WorldGenDungeons;
import org.Barteks2x.b173gen.generator.beta173.WorldGenFlowers;
import org.Barteks2x.b173gen.generator.beta173.WorldGenLakes;
import org.Barteks2x.b173gen.generator.beta173.WorldGenLiquids;
import org.Barteks2x.b173gen.generator.beta173.WorldGenMinable;
import org.Barteks2x.b173gen.generator.beta173.WorldGenPumpkin;
import org.Barteks2x.b173gen.generator.beta173.WorldGenReed;
import org.Barteks2x.b173gen.generator.beta173.WorldGenTallGrass;
import org.Barteks2x.b173gen.plugin.Generator;
import org.Barteks2x.b173gen.plugin.WorldConfig;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_4_R1.CraftWorld;
import org.bukkit.generator.ChunkGenerator;

// Referenced classes of package net.minecraft.src:
//            IChunkProvider, MapGenCaves, NoiseGeneratorOctaves, Block, 
//            BiomeGenBase, Chunk, World, WorldChunkManager, 
//            MapGenBase, BlockSand, WorldGenLakes, WorldGenDungeons, 
//            WorldGenClay, WorldGenMinable, WorldGenerator, WorldGenFlowers, 
//            BlockFlower, WorldGenTallGrass, BlockTallGrass, WorldGenDeadBush, 
//            BlockDeadBush, WorldGenReed, WorldGenPumpkin, WorldGenCactus, 
//            WorldGenLiquids, Material, IProgressUpdate

public class ChunkProviderGenerate extends ChunkGenerator implements
		IChunkProvider {
	private Random rand;
	private NoiseGeneratorOctaves noiseOctaves1;
	private NoiseGeneratorOctaves noiseOctaves2;
	private NoiseGeneratorOctaves noiseOctaves3;
	private NoiseGeneratorOctaves noiseOctaves4;
	private NoiseGeneratorOctaves noiseOctaves5;
	public NoiseGeneratorOctaves noiseOctaves6;
	public NoiseGeneratorOctaves noiseOctaves7;
	public NoiseGeneratorOctaves mobSpawnerNoise;
	private net.minecraft.server.v1_4_R1.World worldObj;
	private double noiseArray[];
	private double sandNoise[] = new double[256];
	private double gravelNoise[] = new double[256];
	private double stoneNoise[] = new double[256];
	private MapGenBase caves = new MapGenCaves();
	private BiomeBase[] biomesForGeneration;
	double field_4185_d[];
	double field_4184_e[];
	double field_4183_f[];
	double field_4182_g[];
	double field_4181_h[];
	int field_914_i[][] = new int[32][32];
	private float[] generatedTemperatures;
	private Wcm wcm;
	private List<org.bukkit.generator.BlockPopulator> populatorList;
	private WorldConfig worldSettings;
	private Generator plugin;

	@Override
	public List<org.bukkit.generator.BlockPopulator> getDefaultPopulators(
			World world) {
		return populatorList;

	}

	public void init(net.minecraft.server.v1_4_R1.World workWorld, Wcm wcm1,
			long seed) {

		worldObj = workWorld;
		this.wcm = wcm1;

		rand = new Random(seed);
		noiseOctaves1 = new NoiseGeneratorOctaves(rand, 16);
		noiseOctaves2 = new NoiseGeneratorOctaves(rand, 16);
		noiseOctaves3 = new NoiseGeneratorOctaves(rand, 8);
		noiseOctaves4 = new NoiseGeneratorOctaves(rand, 4);
		noiseOctaves5 = new NoiseGeneratorOctaves(rand, 4);
		noiseOctaves6 = new NoiseGeneratorOctaves(rand, 10);
		noiseOctaves7 = new NoiseGeneratorOctaves(rand, 16);
		mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);
		caves = new MapGenCaves();
		field_914_i = new int[32][32];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ChunkProviderGenerate(WorldConfig worldSettings, Generator plugin) {
		this.plugin = plugin;
		this.worldSettings = worldSettings;
		this.worldSettings.chunkProvider = this;
		this.populatorList = new ArrayList();
		this.populatorList.add(new BlockPopulator(this));
	}

	public void generateTerrain(int i, int j, byte terrainBlockArray[],
			double ad[]) {
		byte byte0 = 4;
		byte byte1 = 64;
		int k = byte0 + 1;
		byte byte2 = 17;
		int l = byte0 + 1;
		noiseArray = initializeNoiseField(noiseArray, i * byte0, 0, j * byte0,
				k, byte2, l);
		for (int i1 = 0; i1 < byte0; i1++) {
			for (int j1 = 0; j1 < byte0; j1++) {
				for (int k1 = 0; k1 < 16; k1++) {
					double d = 0.125D;
					double d1 = noiseArray[((i1 + 0) * l + (j1 + 0)) * byte2
							+ (k1 + 0)];
					double d2 = noiseArray[((i1 + 0) * l + (j1 + 1)) * byte2
							+ (k1 + 0)];
					double d3 = noiseArray[((i1 + 1) * l + (j1 + 0)) * byte2
							+ (k1 + 0)];
					double d4 = noiseArray[((i1 + 1) * l + (j1 + 1)) * byte2
							+ (k1 + 0)];
					double d5 = (noiseArray[((i1 + 0) * l + (j1 + 0)) * byte2
							+ (k1 + 1)] - d1)
							* d;
					double d6 = (noiseArray[((i1 + 0) * l + (j1 + 1)) * byte2
							+ (k1 + 1)] - d2)
							* d;
					double d7 = (noiseArray[((i1 + 1) * l + (j1 + 0)) * byte2
							+ (k1 + 1)] - d3)
							* d;
					double d8 = (noiseArray[((i1 + 1) * l + (j1 + 1)) * byte2
							+ (k1 + 1)] - d4)
							* d;
					for (int l1 = 0; l1 < 8; l1++) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						for (int i2 = 0; i2 < 4; i2++) {
							int blockInArray = i2 + i1 * 4 << 11
									| 0 + j1 * 4 << 7 | k1 * 8 + l1;
							char c = '\200';
							double d14 = 0.25D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;
							for (int k2 = 0; k2 < 4; k2++) {
								double d17 = ad[(i1 * 4 + i2) * 16
										+ (j1 * 4 + k2)];
								int block = 0;
								if (k1 * 8 + l1 < byte1) {
									if (d17 < 0.5D && k1 * 8 + l1 >= byte1 - 1) {
										block = Block.ICE.id;
									} else {
										block = Block.STATIONARY_WATER.id;
									}
								}
								if (d15 > 0.0D) {
									block = Block.STONE.id;
								}
								terrainBlockArray[blockInArray] = (byte) block;
								blockInArray += c;
								d15 += d16;
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}

				}

			}

		}
	}

	public byte[] generate(int x, int z) {
		this.rand.setSeed(x * 341873128712L + z * 132897987541L);
		byte abyte[] = new byte[32768];
		Chunk chunk = new Chunk(worldObj, abyte, x, z);
		biomesForGeneration = wcm.getBiomeBlock(biomesForGeneration, x * 16,
				z * 16, 16, 16);
		double ad[] = this.wcm.temperature;
		generateTerrain(x, z, abyte, ad);
		replaceBlocksForBiome(x, z, abyte, biomesForGeneration);
		caves.func_867_a(this, worldObj, x, z, abyte);// caves
		chunk.initLighting();
		return abyte;
	}

	@Override
	public byte[][] generateBlockSections(World world, Random random, int x,
			int z, BiomeGrid biomes) {

		byte[] blockArray = generate(x, z);
		// //System.out.println("genBlockSection");
		byte[][] sectionBlocks = new byte[16][];

		// TODO Too slow, for fix need change generator output.

		int blockInArray = 0;
		for (int blockInSectionX = 0; blockInSectionX < 16; blockInSectionX++) {
			for (int blockInSectionZ = 0; blockInSectionZ < 16; blockInSectionZ++) {
				for (int section = 0; section < 8; section++) {
					for (int blockInSectionY = 0; blockInSectionY < 16; blockInSectionY++) {
						sectionBlocks[section][blockInSectionX
								| (blockInSectionZ << 4)
								| (blockInSectionY << 8)] = blockArray[blockInArray++];
					}
				}
			}
		}
		return sectionBlocks;

	}

	@Override
	public short[][] generateExtBlockSections(World world, Random random,
			int x, int z, BiomeGrid biomes) {
		byte[] blockArray;
		try {
			blockArray = generate(x, z);
		} catch (java.lang.NullPointerException e) {
			net.minecraft.server.v1_4_R1.World workWorld = ((CraftWorld) world)
					.getHandle();
			Wcm wcm1 = new Wcm(workWorld.getSeed());
			workWorld.worldProvider.d = wcm1;
			this.init(workWorld, wcm1, workWorld.getSeed());
			blockArray = generate(x, z);
		}

		short[][] sectionBlocks = new short[16][4096];
		int blockInArray = 0;
		for (int blockInSectionX = 0; blockInSectionX < 16; blockInSectionX++) {
			for (int blockInSectionZ = 0; blockInSectionZ < 16; blockInSectionZ++) {
				for (int section = 0; section < 8; section++) {
					for (int blockInSectionY = 0; blockInSectionY < 16; blockInSectionY++) {
						sectionBlocks[section][blockInSectionX
								| (blockInSectionZ << 4)
								| (blockInSectionY << 8)] = blockArray[blockInArray++];
					}
				}
			}
		}
		return sectionBlocks;

	}

	public void replaceBlocksForBiome(int i, int j, byte terrainBlockArray[],
			BiomeBase abiomebase[]) {
		byte byte0 = 64;
		double d = 0.03125D;
		sandNoise = noiseOctaves4.generateNoiseOctaves(sandNoise, i * 16,
				j * 16, 0.0D, 16, 16, 1, d, d, 1.0D);
		gravelNoise = noiseOctaves4.generateNoiseOctaves(gravelNoise, i * 16,
				109.0134D, j * 16, 16, 1, 16, d, 1.0D, d);
		stoneNoise = noiseOctaves5.generateNoiseOctaves(stoneNoise, i * 16,
				j * 16, 0.0D, 16, 16, 1, d * 2D, d * 2D, d * 2D);
		for (int k = 0; k < 16; k++) {
			for (int l = 0; l < 16; l++) {
				BiomeBase biomegenbase = abiomebase[k + l * 16];
				boolean randomSand = sandNoise[k + l * 16] + rand.nextDouble()
						* 0.20000000000000001D > 0.0D;
				boolean randomGravel = gravelNoise[k + l * 16]
						+ rand.nextDouble() * 0.20000000000000001D > 3D;
				int i1 = (int) (stoneNoise[k + l * 16] / 3D + 3D + rand
						.nextDouble() * 0.25D);
				int j1 = -1;
				byte topBlock = biomegenbase.A;
				byte fillerBlock = biomegenbase.B;
				for (int k1 = 127; k1 >= 0; k1--) {
					int currentBlockInArrayNum = (l * 16 + k) * 128 + k1;
					if (k1 <= 0 + rand.nextInt(5)) {
						terrainBlockArray[currentBlockInArrayNum] = (byte) Block.BEDROCK.id;
						continue;
					}
					byte currentBlock = terrainBlockArray[currentBlockInArrayNum];
					if (currentBlock == 0) {
						j1 = -1;
						continue;
					}
					if (currentBlock != Block.STONE.id) {
						continue;
					}
					if (j1 == -1) {
						if (i1 <= 0) {
							topBlock = 0;
							fillerBlock = (byte) Block.STONE.id;
						} else if (k1 >= byte0 - 4 && k1 <= byte0 + 1) {
							topBlock = biomegenbase.A;
							fillerBlock = biomegenbase.B;
							if (randomGravel) {
								topBlock = 0;
							}
							if (randomGravel) {
								fillerBlock = (byte) Block.GRAVEL.id;
							}
							if (randomSand) {
								topBlock = (byte) Block.SAND.id;
							}
							if (randomSand) {
								fillerBlock = (byte) Block.SAND.id;
							}
						}
						if (k1 < byte0 && topBlock == 0) {
							topBlock = (byte) Block.STATIONARY_WATER.id;
						}
						j1 = i1;
						if (k1 >= byte0 - 1) {
							terrainBlockArray[currentBlockInArrayNum] = topBlock;
						} else {
							terrainBlockArray[currentBlockInArrayNum] = fillerBlock;
						}
						continue;
					}
					if (j1 <= 0) {
						continue;
					}
					j1--;
					terrainBlockArray[currentBlockInArrayNum] = fillerBlock;
					if (j1 == 0 && fillerBlock == Block.SAND.id) {
						j1 = rand.nextInt(4);
						fillerBlock = (byte) Block.SANDSTONE.id;
					}
				}

			}

		}
	}

	public Chunk getChunkAt(int i, int j) {
		return getOrCreateChunk(i, j);
	}

	@SuppressWarnings("cast")
	public Chunk getOrCreateChunk(int i, int j) {
		rand.setSeed((long) i * 0x4f9939f508L + (long) j * 0x1ef1565bd5L);
		byte abyte0[] = new byte[32768];
		Chunk chunk = new Chunk(worldObj, abyte0, i, j);
		biomesForGeneration = this.wcm.getBiomeBlock(biomesForGeneration,
				i * 16, j * 16, 16, 16);
		double ad[] = this.wcm.temperature;
		generateTerrain(i, j, abyte0, ad);
		replaceBlocksForBiome(i, j, abyte0, biomesForGeneration);
		caves.func_867_a(this, worldObj, i, j, abyte0);
		chunk.initLighting();
		return chunk;
	}

	@SuppressWarnings("cast")
	private double[] initializeNoiseField(double ad[], int i, int j, int k,
			int l, int i1, int j1) {
		if (ad == null) {
			ad = new double[l * i1 * j1];
		}
		double d = 684.41200000000003D;
		double d1 = 684.41200000000003D;
		double ad1[] = this.wcm.temperature;
		double ad2[] = this.wcm.rain;
		field_4182_g = noiseOctaves6.func_4109_a(field_4182_g, i, k, l, j1,
				1.121D, 1.121D, 0.5D);
		field_4181_h = noiseOctaves7.func_4109_a(field_4181_h, i, k, l, j1,
				200D, 200D, 0.5D);
		field_4185_d = noiseOctaves3.generateNoiseOctaves(field_4185_d, i, j,
				k, l, i1, j1, d / 80D, d1 / 160D, d / 80D);
		field_4184_e = noiseOctaves1.generateNoiseOctaves(field_4184_e, i, j,
				k, l, i1, j1, d, d1, d);
		field_4183_f = noiseOctaves2.generateNoiseOctaves(field_4183_f, i, j,
				k, l, i1, j1, d, d1, d);
		int k1 = 0;
		int l1 = 0;
		int i2 = 16 / l;
		for (int j2 = 0; j2 < l; j2++) {
			int k2 = j2 * i2 + i2 / 2;
			for (int l2 = 0; l2 < j1; l2++) {
				int i3 = l2 * i2 + i2 / 2;
				double d2 = ad1[k2 * 16 + i3];
				double d3 = ad2[k2 * 16 + i3] * d2;
				double d4 = 1.0D - d3;
				d4 *= d4;
				d4 *= d4;
				d4 = 1.0D - d4;
				double d5 = (field_4182_g[l1] + 256D) / 512D;
				d5 *= d4;
				if (d5 > 1.0D) {
					d5 = 1.0D;
				}
				double d6 = field_4181_h[l1] / 8000D;
				if (d6 < 0.0D) {
					d6 = -d6 * 0.29999999999999999D;
				}
				d6 = d6 * 3D - 2D;
				if (d6 < 0.0D) {
					d6 /= 2D;
					if (d6 < -1D) {
						d6 = -1D;
					}
					d6 /= 1.3999999999999999D;
					d6 /= 2D;
					d5 = 0.0D;
				} else {
					if (d6 > 1.0D) {
						d6 = 1.0D;
					}
					d6 /= 8D;
				}
				if (d5 < 0.0D) {
					d5 = 0.0D;
				}
				d5 += 0.5D;
				d6 = (d6 * (double) i1) / 16D;
				double d7 = (double) i1 / 2D + d6 * 4D;
				l1++;
				for (int j3 = 0; j3 < i1; j3++) {
					double d8 = 0.0D;
					double d9 = (((double) j3 - d7) * 12D) / d5;
					if (d9 < 0.0D) {
						d9 *= 4D;
					}
					double d10 = field_4184_e[k1] / 512D;
					double d11 = field_4183_f[k1] / 512D;
					double d12 = (field_4185_d[k1] / 10D + 1.0D) / 2D;
					if (d12 < 0.0D) {
						d8 = d10;
					} else if (d12 > 1.0D) {
						d8 = d11;
					} else {
						d8 = d10 + (d11 - d10) * d12;
					}
					d8 -= d9;
					if (j3 > i1 - 4) {
						double d13 = (float) (j3 - (i1 - 4)) / 3F;
						d8 = d8 * (1.0D - d13) + -10D * d13;
					}
					ad[k1] = d8;
					k1++;
				}
			}
		}
		// //System.out.println("selectBiome end");
		return ad;
	}

	@SuppressWarnings("cast")
	public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {

		BlockSand.instaFall = true;
		int k = i * 16;
		int l = j * 16;
		BiomeGenBase biomegenbase = this.wcm.getBiome(k + 16, l + 16);
		rand.setSeed(worldObj.getSeed());
		long l1 = (rand.nextLong() / 2L) * 2L + 1L;
		long l2 = (rand.nextLong() / 2L) * 2L + 1L;
		rand.setSeed((long) i * l1 + (long) j * l2 ^ worldObj.getSeed());
		double d = 0.25D;
		if (rand.nextInt(4) == 0) {
			int i1 = k + rand.nextInt(16) + 8;
			int l4 = rand.nextInt(128);
			int i8 = l + rand.nextInt(16) + 8;
			(new WorldGenLakes(Block.WATER.id)).a(worldObj, rand, i1, l4, i8);
		}
		if (rand.nextInt(8) == 0) {
			int j1 = k + rand.nextInt(16) + 8;
			int i5 = rand.nextInt(rand.nextInt(120) + 8);
			int j8 = l + rand.nextInt(16) + 8;
			if (i5 < 64 || rand.nextInt(10) == 0) {
				(new WorldGenLakes(Block.LAVA.id))
						.a(worldObj, rand, j1, i5, j8);
			}
		}
		for (int k1 = 0; k1 < 8; k1++) {
			int j5 = k + rand.nextInt(16) + 8;
			int k8 = rand.nextInt(128);
			int j11 = l + rand.nextInt(16) + 8;
			(new WorldGenDungeons()).a(worldObj, rand, j5, k8, j11);
		}

		for (int i2 = 0; i2 < 10; i2++) {
			int k5 = k + rand.nextInt(16);
			int l8 = rand.nextInt(128);
			int k11 = l + rand.nextInt(16);
			(new WorldGenClay(32)).a(worldObj, rand, k5, l8, k11);
		}

		for (int j2 = 0; j2 < 20; j2++) {
			int l5 = k + rand.nextInt(16);
			int i9 = rand.nextInt(128);
			int l11 = l + rand.nextInt(16);
			(new WorldGenMinable(Block.DIRT.id, 32)).a(worldObj, rand, l5, i9,
					l11);
		}

		for (int k2 = 0; k2 < 10; k2++) {
			int i6 = k + rand.nextInt(16);
			int j9 = rand.nextInt(128);
			int i12 = l + rand.nextInt(16);
			(new WorldGenMinable(Block.GRAVEL.id, 32)).a(worldObj, rand, i6,
					j9, i12);
		}

		for (int i3 = 0; i3 < 20; i3++) {
			int j6 = k + rand.nextInt(16);
			int k9 = rand.nextInt(128);
			int j12 = l + rand.nextInt(16);
			(new WorldGenMinable(Block.COAL_ORE.id, 16)).a(worldObj, rand, j6,
					k9, j12);
		}

		for (int j3 = 0; j3 < 20; j3++) {
			int k6 = k + rand.nextInt(16);
			int l9 = rand.nextInt(64);
			int k12 = l + rand.nextInt(16);
			(new WorldGenMinable(Block.IRON_ORE.id, 8)).a(worldObj, rand, k6,
					l9, k12);
		}

		for (int k3 = 0; k3 < 2; k3++) {
			int l6 = k + rand.nextInt(16);
			int i10 = rand.nextInt(32);
			int l12 = l + rand.nextInt(16);
			(new WorldGenMinable(Block.GOLD_ORE.id, 8)).a(worldObj, rand, l6,
					i10, l12);
		}

		for (int l3 = 0; l3 < 8; l3++) {
			int i7 = k + rand.nextInt(16);
			int j10 = rand.nextInt(16);
			int i13 = l + rand.nextInt(16);
			(new WorldGenMinable(Block.REDSTONE_ORE.id, 7)).a(worldObj, rand,
					i7, j10, i13);
		}

		for (int i4 = 0; i4 < 1; i4++) {
			int j7 = k + rand.nextInt(16);
			int k10 = rand.nextInt(16);
			int j13 = l + rand.nextInt(16);
			(new WorldGenMinable(Block.DIAMOND_ORE.id, 7)).a(worldObj, rand,
					j7, k10, j13);
		}

		for (int j4 = 0; j4 < 1; j4++) {
			int k7 = k + rand.nextInt(16);
			int l10 = rand.nextInt(16) + rand.nextInt(16);
			int k13 = l + rand.nextInt(16);
			(new WorldGenMinable(Block.LAPIS_ORE.id, 6)).a(worldObj, rand, k7,
					l10, k13);
		}

		d = 0.5D;
		int k4 = (int) ((mobSpawnerNoise.func_806_a((double) k * d, (double) l
				* d)
				/ 8D + rand.nextDouble() * 4D + 4D) / 3D);
		int l7 = 0;
		if (rand.nextInt(10) == 0) {
			l7++;
		}
		if (biomegenbase == BiomeGenBase.forest) {
			l7 += k4 + 5;
		}
		if (biomegenbase == BiomeGenBase.rainforest) {
			l7 += k4 + 5;
		}
		if (biomegenbase == BiomeGenBase.seasonalForest) {
			l7 += k4 + 2;
		}
		if (biomegenbase == BiomeGenBase.taiga) {
			l7 += k4 + 5;
		}
		if (biomegenbase == BiomeGenBase.desert) {
			l7 -= 20;
		}
		if (biomegenbase == BiomeGenBase.tundra) {
			l7 -= 20;
		}
		if (biomegenbase == BiomeGenBase.plains) {
			l7 -= 20;
		}
		for (int i11 = 0; i11 < l7; i11++) {
			int l13 = k + rand.nextInt(16) + 8;
			int j14 = l + rand.nextInt(16) + 8;
			WorldGenerator worldgenerator = biomegenbase.a(rand);
			worldgenerator.a(1.0D, 1.0D, 1.0D);
			worldgenerator.a(worldObj, rand, l13,
					worldObj.getHighestBlockYAt(l13, j14), j14);
		}

		byte byte0 = 0;
		if (biomegenbase == BiomeGenBase.forest) {
			byte0 = 2;
		}
		if (biomegenbase == BiomeGenBase.seasonalForest) {
			byte0 = 4;
		}
		if (biomegenbase == BiomeGenBase.taiga) {
			byte0 = 2;
		}
		if (biomegenbase == BiomeGenBase.plains) {
			byte0 = 3;
		}
		for (int i14 = 0; i14 < byte0; i14++) {
			int k14 = k + rand.nextInt(16) + 8;
			int l16 = rand.nextInt(128);
			int k19 = l + rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.YELLOW_FLOWER.id)).a(worldObj, rand,
					k14, l16, k19);
		}

		byte byte1 = 0;
		if (biomegenbase == BiomeGenBase.forest) {
			byte1 = 2;
		}
		if (biomegenbase == BiomeGenBase.rainforest) {
			byte1 = 10;
		}
		if (biomegenbase == BiomeGenBase.seasonalForest) {
			byte1 = 2;
		}
		if (biomegenbase == BiomeGenBase.taiga) {
			byte1 = 1;
		}
		if (biomegenbase == BiomeGenBase.plains) {
			byte1 = 10;
		}
		for (int l14 = 0; l14 < byte1; l14++) {
			byte byte2 = 1;
			if (biomegenbase == BiomeGenBase.rainforest && rand.nextInt(3) != 0) {
				byte2 = 2;
			}
			int l19 = k + rand.nextInt(16) + 8;
			int k22 = rand.nextInt(128);
			int j24 = l + rand.nextInt(16) + 8;
			(new WorldGenTallGrass(Block.LONG_GRASS.id, byte2)).a(worldObj,
					rand, l19, k22, j24);
		}

		byte1 = 0;
		if (biomegenbase == BiomeGenBase.desert) {
			byte1 = 2;
		}
		for (int i15 = 0; i15 < byte1; i15++) {
			int i17 = k + rand.nextInt(16) + 8;
			int i20 = rand.nextInt(128);
			int l22 = l + rand.nextInt(16) + 8;
			(new WorldGenDeadBush(Block.DEAD_BUSH.id)).a(worldObj, rand, i17,
					i20, l22);
		}

		if (rand.nextInt(2) == 0) {
			int j15 = k + rand.nextInt(16) + 8;
			int j17 = rand.nextInt(128);
			int j20 = l + rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.RED_ROSE.id)).a(worldObj, rand, j15,
					j17, j20);
		}
		if (rand.nextInt(4) == 0) {
			int k15 = k + rand.nextInt(16) + 8;
			int k17 = rand.nextInt(128);
			int k20 = l + rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.BROWN_MUSHROOM.id)).a(worldObj, rand,
					k15, k17, k20);
		}
		if (rand.nextInt(8) == 0) {
			int l15 = k + rand.nextInt(16) + 8;
			int l17 = rand.nextInt(128);
			int l20 = l + rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.RED_MUSHROOM.id)).a(worldObj, rand, l15,
					l17, l20);
		}
		for (int i16 = 0; i16 < 10; i16++) {
			int i18 = k + rand.nextInt(16) + 8;
			int i21 = rand.nextInt(128);
			int i23 = l + rand.nextInt(16) + 8;
			(new WorldGenReed()).a(worldObj, rand, i18, i21, i23);
		}

		if (rand.nextInt(32) == 0) {
			int j16 = k + rand.nextInt(16) + 8;
			int j18 = rand.nextInt(128);
			int j21 = l + rand.nextInt(16) + 8;
			(new WorldGenPumpkin()).a(worldObj, rand, j16, j18, j21);
		}
		int k16 = 0;
		if (biomegenbase == BiomeGenBase.desert) {
			k16 += 10;
		}
		for (int k18 = 0; k18 < k16; k18++) {
			int k21 = k + rand.nextInt(16) + 8;
			int j23 = rand.nextInt(128);
			int k24 = l + rand.nextInt(16) + 8;
			(new WorldGenCactus()).a(worldObj, rand, k21, j23, k24);
		}

		for (int l18 = 0; l18 < 50; l18++) {
			int l21 = k + rand.nextInt(16) + 8;
			int k23 = rand.nextInt(rand.nextInt(120) + 8);
			int l24 = l + rand.nextInt(16) + 8;
			(new WorldGenLiquids(Block.WATER.id)).a(worldObj, rand, l21, k23,
					l24);
		}

		for (int i19 = 0; i19 < 20; i19++) {
			int i22 = k + rand.nextInt(16) + 8;
			int l23 = rand.nextInt(rand.nextInt(rand.nextInt(112) + 8) + 8);
			int i25 = l + rand.nextInt(16) + 8;
			(new WorldGenLiquids(Block.LAVA.id)).a(worldObj, rand, i22, l23,
					i25);
		}

		generatedTemperatures = this.wcm.getTemperatures(generatedTemperatures,
				k + 8, l + 8, 16, 16);
		for (int j19 = k + 8; j19 < k + 8 + 16; j19++) {
			for (int j22 = l + 8; j22 < l + 8 + 16; j22++) {
				int i24 = j19 - (k + 8);
				int j25 = j22 - (l + 8);
				int k25 = worldObj.getHighestBlockYAt(j19, j22);
				double d1 = generatedTemperatures[i24 * 16 + j25]
						- ((double) (k25 - 64) / 64D) * 0.29999999999999999D;
				if (d1 < 0.5D
						&& k25 > 0
						&& k25 < 128
						&& worldObj.isEmpty(j19, k25, j22)
						&& worldObj.getMaterial(j19, k25 - 1, j22).isSolid()
						&& worldObj.getMaterial(j19, k25 - 1, j22) != Material.ICE) {
					worldObj.setTypeId(j19, k25, j22, Block.SNOW.id);
				}
			}

		}

		BlockSand.instaFall = false;
	}

	public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
		return true;
	}

	public boolean canSave() {
		return true;
	}

	public String makeString() {
		return "RandomLevelSource";
	}

	@SuppressWarnings("rawtypes")
	public List getMobsFor(EnumCreatureType type, int x, int y, int z) {
		BiomeBase biome = this.wcm.getBiome(x, z);
		return biome.getMobs(type);
	}

	public boolean unloadChunks() {
		return false;
	}

	public boolean isChunkLoaded(int x, int z) {
		return true;
	}

	private final WorldGenStronghold strongholdGen = new WorldGenStronghold();

	public ChunkPosition findNearestMapFeature(
			net.minecraft.server.v1_4_R1.World world, String type, int x, int y,
			int z) {
		return ((("Stronghold".equals(type)) && (this.strongholdGen != null)) ? this.strongholdGen
				.getNearestGeneratedFeature(world, x, y, z) : null);
	}

	public int getLoadedChunks() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		return plugin.getDescription().getName();
	}

	public void recreateStructures(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}
}

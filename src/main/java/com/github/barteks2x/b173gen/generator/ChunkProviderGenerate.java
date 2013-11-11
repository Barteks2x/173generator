package com.github.barteks2x.b173gen.generator;

import com.github.barteks2x.b173gen.Generator;
import com.github.barteks2x.b173gen.WorldGenBaseOld;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.newgen.*;
import com.github.barteks2x.b173gen.oldgen.*;
import com.github.barteks2x.b173gen.oldnoisegen.NoiseGeneratorOctaves3D;
import com.github.barteks2x.b173gen.reflection.Util;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

import static org.bukkit.Material.*;

public class ChunkProviderGenerate extends ChunkGenerator {

	private Random rand;
	private NoiseGeneratorOctaves3D noiseGen1;
	private NoiseGeneratorOctaves3D noiseGen2;
	private NoiseGeneratorOctaves3D noiseGen3;
	private NoiseGeneratorOctaves3D noiseGen4;
	private NoiseGeneratorOctaves3D noiseGen5;
	private NoiseGeneratorOctaves3D noiseGen6;
	private NoiseGeneratorOctaves3D noiseGen7;
	private NoiseGeneratorOctaves3D treeNoise;
	private double noise[];
	private double sandNoise[] = new double[256];
	private double gravelNoise[] = new double[256];
	private double stoneNoise[] = new double[256];
	private WorldGenBaseOld caves;
	private Biome[] biomes;
	private double noise3[];
	private double noise1[];
	private double noise2[];
	private double noise6[];
	private double noise7[];
	private double[] temperatures;
	private WorldChunkManagerOld wcm;
	private List<org.bukkit.generator.BlockPopulator> populatorList;
	private World world;
	private WorldConfig config;
	private Generator plugin;
	private WorldGenerator173 dungeonGen,
			dirtGen,
			gravelGen,
			coalGen,
			ironGen,
			goldGen,
			redstoneGen,
			diamondGen,
			emeraldGen,
			lapisGen,
			yellowFlowerGen,
			longGrassGenNormal,
			longGrassGenRainforest,
			deadBushGen,
			redFlowerGen,
			brownMushroomGen,
			redMushroomGen,
			reedGen,
			pumpkinGen,
			cactusGen,
			liquidWaterGen,
			liquidLavaGen,
			clayGen,
			waterLakeGen,
			lavaLakeGen;
	private WorldGenCanyonRef canyonGen;
	private WorldGenStrongholdRef strongholdGen;
	private WorldGenMineshaftRef mineshaftGen;
	private WorldGenVillageRef villageGen;
	//temples
	private WorldGenLargeFeatureRef largeFeatureGen;
	private static final int EMERALD_ORE_ID = 129;

	@Override
	public List<org.bukkit.generator.BlockPopulator> getDefaultPopulators(org.bukkit.World w) {
		plugin.initWorld(w);
		return populatorList;
	}

	@SuppressWarnings("unchecked")
	public void init(World world, WorldChunkManagerOld wcm) {
		this.world = world;
		this.wcm = wcm;

		rand = new Random(world.getSeed());
		noiseGen1 = new NoiseGeneratorOctaves3D(rand, 16);
		noiseGen2 = new NoiseGeneratorOctaves3D(rand, 16);
		noiseGen3 = new NoiseGeneratorOctaves3D(rand, 8);
		noiseGen4 = new NoiseGeneratorOctaves3D(rand, 4);
		noiseGen5 = new NoiseGeneratorOctaves3D(rand, 4);
		noiseGen6 = new NoiseGeneratorOctaves3D(rand, 10);
		noiseGen7 = new NoiseGeneratorOctaves3D(rand, 16);
		treeNoise = new NoiseGeneratorOctaves3D(rand, 8);

		clayGen = config.newClayGen ? new WorldGenClayRef(6) :
				new WorldGenClayOld(32);
		waterLakeGen = config.newLakeGen ? new WorldGenLakesRef(WATER.getId()) :
				new WorldGenLakesOld(WATER.getId());
		lavaLakeGen = config.newLakeGen ? new WorldGenLakesRef(LAVA.getId()) :
				new WorldGenLakesOld(LAVA.getId());
		caves = config.newCaveGen ? new WorldGenCavesRef() : new WorldGenCavesOld();
		dungeonGen = config.newDungeonGen ? new WorldGenDungeonRef() : new WorldGenDungeonOld();
		dirtGen = new WorldGenMinableOld(DIRT.getId(), 32);
		gravelGen = new WorldGenMinableOld(GRAVEL.getId(), 32);
		coalGen = new WorldGenMinableOld(COAL_ORE.getId(), 16);
		ironGen = new WorldGenMinableOld(IRON_ORE.getId(), 8);
		goldGen = new WorldGenMinableOld(GOLD_ORE.getId(), 8);
		redstoneGen = new WorldGenMinableOld(REDSTONE_ORE.getId(), 7);
		diamondGen = new WorldGenMinableOld(DIAMOND_ORE.getId(), 7);
		lapisGen = new WorldGenMinableOld(LAPIS_ORE.getId(), 6);
		yellowFlowerGen = new WorldGenFlowersRef(YELLOW_FLOWER.getId());
		longGrassGenNormal = new WorldGenGrassRef(LONG_GRASS.getId(), 1);
		longGrassGenRainforest = new WorldGenGrassRef(LONG_GRASS.getId(), 2);
		deadBushGen = new WorldGenDeadBushRef(DEAD_BUSH.getId());
		redFlowerGen = new WorldGenFlowersRef(RED_ROSE.getId());
		brownMushroomGen = new WorldGenFlowersRef(BROWN_MUSHROOM.getId());
		redMushroomGen = new WorldGenFlowersRef(RED_MUSHROOM.getId());
		reedGen = new WorldGenReedRef();
		pumpkinGen = new WorldGenPumpkinRef();
		cactusGen = new WorldGenCactusRef();
		liquidWaterGen = new WorldGenLiquidsRef(WATER.getId());
		liquidLavaGen = new WorldGenLiquidsRef(LAVA.getId());
		canyonGen = config.generateCanyons ? new WorldGenCanyonRef() : null;
		strongholdGen = config.generateStrongholds ? new WorldGenStrongholdRef() : null;
		mineshaftGen = config.generateMineshafts ? new WorldGenMineshaftRef() : null;
		villageGen = config.generateVillages ? new WorldGenVillageRef() : null;
		largeFeatureGen = config.generateTemples ? new WorldGenLargeFeatureRef() : null;
		emeraldGen = config.generateEmerald ? new WorldGenMinableOld(EMERALD_ORE_ID, 2) : null;//For 1.2.* compatibility
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public ChunkProviderGenerate(WorldConfig config, Generator plugin) {
		this.plugin = plugin;
		this.config = config;
		this.populatorList = new ArrayList();
		this.populatorList.add(new BlockPopulator(this));
	}

	public static Biome biomeMapping(Biome b) {
		Biome map;
		map = (b == Biome.BEACH || b == Biome.DESERT_HILLS || b == Biome.SWAMPLAND) ?
				Biome.PLAINS : b;
		return map;
	}

	public void generateTerrain(int x, int z, byte terrain[], double temperatures[]) {
		byte byte0 = 4;
		byte oceanHeight = 64;
		int k = byte0 + 1;
		byte b2 = 17;
		int l = byte0 + 1;
		noise = initNoiseField(noise, x * byte0, 0, z * byte0, k, b2, l);
		for (int i1 = 0; i1 < byte0; i1++) {
			for (int j1 = 0; j1 < byte0; j1++) {
				for (int yPiece = 0; yPiece < 16; yPiece++) {
					double d = 0.125D;
					double d1 = noise[((i1 + 0) * l + (j1 + 0)) * b2 + (yPiece + 0)];
					double d2 = noise[((i1 + 0) * l + (j1 + 1)) * b2 + (yPiece + 0)];
					double d3 = noise[((i1 + 1) * l + (j1 + 0)) * b2 + (yPiece + 0)];
					double d4 = noise[((i1 + 1) * l + (j1 + 1)) * b2 + (yPiece + 0)];
					double d5 = (noise[((i1 + 0) * l + (j1 + 0)) * b2 + (yPiece + 1)] - d1) * d;
					double d6 = (noise[((i1 + 0) * l + (j1 + 1)) * b2 + (yPiece + 1)] - d2) * d;
					double d7 = (noise[((i1 + 1) * l + (j1 + 0)) * b2 + (yPiece + 1)] - d3) * d;
					double d8 = (noise[((i1 + 1) * l + (j1 + 1)) * b2 + (yPiece + 1)] - d4) * d;
					for (int l1 = 0; l1 < 8; l1++) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						for (int i2 = 0; i2 < 4; i2++) {
							int blockLoc = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | yPiece * 8 + l1;
							short height = 128;
							double d14 = 0.25D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;
							for (int k2 = 0; k2 < 4; k2++) {
								double d17 = temperatures[(i1 * 4 + i2) * 16 + (j1 * 4 + k2)];
								int block = 0;
								if (yPiece * 8 + l1 < oceanHeight) {
									if (d17 < 0.5D && yPiece * 8 + l1 >= oceanHeight - 1) {
										block = ICE.getId();
									} else {
										block = STATIONARY_WATER.getId();
									}
								}
								if (d15 > 0.0D) {
									block = STONE.getId();
								}
								terrain[blockLoc] = (byte)block;
								blockLoc += height;
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

	@Override
	public byte[][] generateBlockSections(World world, Random random, int x, int z,
			BiomeGrid biomeGrid) {
		this.rand.setSeed(x * 341873128712L + z * 132897987541L);
		byte terrain[] = new byte[32768];
		biomes = wcm.getBiomeBlock(biomes, x * 16, z * 16, 16, 16);
		double temp[] = this.wcm.temperatures;
		generateTerrain(x, z, terrain, temp);
		replaceBlocksForBiomeAndSetBiome(x, z, terrain, biomes);
		caves.generate(world, x, z, terrain);
		if (canyonGen != null) {
			canyonGen.generate(world, x, z, terrain);
		}
		if (strongholdGen != null) {
			strongholdGen.generate(world, x, z, terrain);
		}
		if (mineshaftGen != null) {
			mineshaftGen.generate(world, x, z, terrain);
		}
		if (villageGen != null) {
			villageGen.generate(world, x, z, terrain);
		}
		if (largeFeatureGen != null) {
			largeFeatureGen.generate(world, x, z, terrain);
		}
		int n = 0;
		for (int x_t = 0; x_t < 16; ++x_t) {
			for (int z_t = 0; z_t < 16; ++z_t) {
				biomeGrid.setBiome(x_t, z_t, biomeMapping(biomes[n]));
				++n;
			}
		}
		byte[][] sections = new byte[16][4096];
		for (int x_t = 0; x_t < 16; ++x_t) {
			for (int y_t = 0; y_t < 128; ++y_t) {
				for (int z_t = 0; z_t < 16; ++z_t) {
					sections[y_t >> 4][((y_t & 0xF) << 8) | (z_t << 4) | x_t] =
							terrain[(x_t << 11) | (z_t << 7) | y_t];
				}
			}
		}
		return sections;
	}

	public void replaceBlocksForBiomeAndSetBiome(int xPos, int zPos, byte terrain[], Biome biomes[]) {
		byte oceanHeight = 64;
		double d = 0.03125D;
		sandNoise = noiseGen4.generateNoiseArray(sandNoise, xPos * 16, zPos * 16, 0.0D,
				16, 16, 1, d, d, 1.0D);
		gravelNoise = noiseGen4.generateNoiseArray(gravelNoise, xPos * 16, 109.0134D,
				zPos * 16, 16, 1, 16, d, 1.0D, d);
		stoneNoise = noiseGen5.
				generateNoiseArray(stoneNoise, xPos * 16, zPos * 16, 0.0D, 16, 16, 1, d * 2D,
				d * 2D, d * 2D);
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				Biome biome = biomes[x + z * 16];
				boolean sand = sandNoise[x + z * 16] + rand.nextDouble() * 0.2D >
						0.0D;
				boolean gravel =
						gravelNoise[x + z * 16] + rand.nextDouble() * 0.2D > 3D;
				int depth = (int)(stoneNoise[x + z * 16] / 3D + 3D + rand.
						nextDouble() * 0.25D);
				int prevDepth = -1;
				byte topBlock = BiomeOld.top(biome);
				byte fillerBlock = BiomeOld.filler(biome);
				for (int y = 127; y >= 0; y--) {
					int blockIndex = (((z << 4) + x) << 7) + y;
					if (y <= 0 + rand.nextInt(5)) {
						terrain[blockIndex] = (byte)BEDROCK.getId();
						continue;
					}
					byte blockID = terrain[blockIndex];
					if (blockID == 0) {
						prevDepth = -1;
						continue;
					}
					if (blockID != STONE.getId()) {
						continue;
					}
					if (prevDepth == -1) {
						if (depth <= 0) {
							topBlock = 0;
							fillerBlock = (byte)STONE.getId();
						} else if (y >= oceanHeight - 4 && y <= oceanHeight + 1) {
							topBlock = BiomeOld.top(biome);
							fillerBlock = BiomeOld.filler(biome);
							if (gravel) {
								topBlock = 0;
								fillerBlock = (byte)GRAVEL.getId();
							}
							if (sand) {
								topBlock = (byte)SAND.getId();
								fillerBlock = (byte)SAND.getId();
							}
						}
						if (y < oceanHeight && topBlock == 0) {
							topBlock = (byte)STATIONARY_WATER.getId();
						}
						prevDepth = depth;
						if (y >= oceanHeight - 1) {
							terrain[blockIndex] = topBlock;
						} else {
							terrain[blockIndex] = fillerBlock;
						}
						continue;
					}
					if (prevDepth <= 0) {
						continue;
					}
					prevDepth--;
					terrain[blockIndex] = fillerBlock;
					if (prevDepth == 0 && fillerBlock == SAND.getId()) {
						prevDepth = rand.nextInt(4);
						fillerBlock = (byte)SANDSTONE.getId();
					}
				}

			}

		}
	}

	private double[] initNoiseField(double array[], int xPos, int yPos, int zPos, int xSize,
			int ySize, int zSize) {
		if (array == null) {
			array = new double[xSize * ySize * zSize];
		}
		double d0 = 684.41200000000003D;
		double d1 = 684.41200000000003D;
		double temp[] = this.wcm.temperatures;
		double rain[] = this.wcm.rain;
		noise6 = noiseGen6.
				generateNoiseArray(noise6, xPos, zPos, xSize, zSize, 1.121D, 1.121D, 0.5D);
		noise7 = noiseGen7.generateNoiseArray(noise7, xPos, zPos, xSize, zSize, 200D, 200D, 0.5D);
		noise3 = noiseGen3.generateNoiseArray(noise3, (double)xPos, (double)yPos, (double)zPos,
				xSize, ySize, zSize, d0 / 80D, d1 / 160D, d0 / 80D);
		noise1 = noiseGen1.generateNoiseArray(noise1, (double)xPos, (double)yPos, (double)zPos,
				xSize, ySize, zSize, d0, d1, d0);
		noise2 = noiseGen2.generateNoiseArray(noise2, (double)xPos, (double)yPos, (double)zPos,
				xSize, ySize, zSize, d0, d1, d0);
		int k1 = 0;
		int l1 = 0;
		int i2 = 16 / xSize;
		for (int x = 0; x < xSize; x++) {
			int k2 = x * i2 + i2 / 2;
			for (int z = 0; z < zSize; z++) {
				int i3 = z * i2 + i2 / 2;
				double d2 = temp[k2 * 16 + i3];
				double d3 = rain[k2 * 16 + i3] * d2;
				double d4 = 1.0D - d3;
				d4 *= d4;
				d4 *= d4;
				d4 = 1.0D - d4;
				double d5 = (noise6[l1] + 256D) / 512D;
				d5 *= d4;
				if (d5 > 1.0D) {
					d5 = 1.0D;
				}
				double d6 = noise7[l1] / 8000D;
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
				d6 = (d6 * (double)ySize) / 16D;
				double d7 = (double)ySize / 2D + d6 * 4D;
				l1++;
				for (int y = 0; y < ySize; y++) {
					double d8 = 0.0D;
					double d9 = (((double)y - d7) * 12D) /
							d5;
					if (d9 < 0.0D) {
						d9 *= 4D;
					}
					double d10 = noise1[k1] / 512D;
					double d11 = noise2[k1] / 512D;
					double d12 = (this.noise3[k1] / 10D +
							1.0D) / 2D;
					if (d12 < 0.0D) {
						d8 = d10;
					} else if (d12 > 1.0D) {
						d8 = d11;
					} else {
						d8 = d10 + (d11 - d10) * d12;
					}
					d8 -= d9;
					if (y > ySize - 4) {
						double d13 = (double)((float)(y - (ySize - 4)) / 3F);
						d8 = d8 * (1.0D - d13) + -10D * d13;
					}
					array[k1] = d8;
					k1++;
				}
			}
		}
		return array;
	}

	public void populate(int chunkX, int chunkZ) {
		int x = chunkX * 16;
		int z = chunkZ * 16;
		Biome biome = this.wcm.getBiome(x + 16, z + 16);
		rand.setSeed(world.getSeed());
		long rand1 = rand.nextLong() / 2L * 2L + 1L;
		long rand2 = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed((long)chunkX * rand1 + (long)chunkZ * rand2 ^ world.getSeed());

		if (rand.nextInt(4) == 0) {
			int X = x + rand.nextInt(16) + 8;
			int Y = rand.nextInt(128);
			int Z = z + rand.nextInt(16) + 8;
			waterLakeGen.generate(world, rand, X, Y, Z);
		}
		if (rand.nextInt(8) == 0) {
			int X = x + rand.nextInt(16) + 8;
			int Y = rand.nextInt(rand.nextInt(120) + 8);
			int Z = z + rand.nextInt(16) + 8;
			if (Y < 64 || rand.nextInt(10) == 0) {
				lavaLakeGen.generate(world, rand, X, Y, Z);
			}
		}
		for (int j = 0; j < 8; j++) {
			int X = x + rand.nextInt(16) + 8;
			int Y = rand.nextInt(128);
			int Z = z + rand.nextInt(16) + 8;
			dungeonGen.generate(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 10; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(128);
			int Z = z + rand.nextInt(16);
			clayGen.generate(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 20; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(128);
			int Z = z + rand.nextInt(16);
			dirtGen.generate(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 10; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(128);
			int Z = z + rand.nextInt(16);
			gravelGen.generate(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 20; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(128);
			int Z = z + rand.nextInt(16);
			coalGen.generate(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 20; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(64);
			int Z = z + rand.nextInt(16);
			ironGen.generate(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 2; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(32);
			int Z = z + rand.nextInt(16);
			goldGen.generate(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 8; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(16);
			int Z = z + rand.nextInt(16);
			redstoneGen.generate(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 1; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(16);
			int Z = z + rand.nextInt(16);
			diamondGen.generate(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 1; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(16) + rand.nextInt(16);
			int Z = z + rand.nextInt(16);
			lapisGen.generate(world, rand, X, Y, Z);
		}

		int treesRand = (int)((treeNoise.generateNoise(x * 0.5D, z * 0.5D) / 8D +
				rand.nextDouble() * 4D + 4D) / 3D);
		int trees = 0;
		if (rand.nextInt(10) == 0) {
			trees++;
		}
		if (biome == Biome.FOREST) {
			trees += treesRand + 5;
		}
		if (biome == Biome.JUNGLE) {
			trees += treesRand + 5;
		}
		if (biome == Biome.FOREST_HILLS) {
			trees += treesRand + 2;
		}
		if (biome == Biome.TAIGA) {
			trees += treesRand + 5;
		}
		if (biome == Biome.DESERT) {
			trees -= 20;
		}
		if (biome == Biome.ICE_PLAINS) {
			trees -= 20;
		}
		if (biome == Biome.PLAINS) {
			trees -= 20;
		}
		for (int i11 = 0; i11 < trees; i11++) {
			int l13 = x + rand.nextInt(16) + 8;
			int j14 = z + rand.nextInt(16) + 8;
			WorldGenerator173 worldgenerator = BiomeOld.getRandomTreeGen(rand, biome);
			worldgenerator.a(1.0D, 1.0D, 1.0D);
			worldgenerator.generate(world, rand, l13, world.getHighestBlockYAt(l13, j14), j14);
		}

		byte flowers = 0;
		if (biome == Biome.FOREST) {
			flowers = 2;
		}
		if (biome == Biome.FOREST_HILLS) {
			flowers = 4;
		}
		if (biome == Biome.TAIGA) {
			flowers = 2;
		}
		if (biome == Biome.PLAINS) {
			flowers = 3;
		}
		for (int i14 = 0; i14 < flowers; i14++) {
			int k14 = x + rand.nextInt(16) + 8;
			int l16 = rand.nextInt(128);
			int k19 = z + rand.nextInt(16) + 8;
			yellowFlowerGen.generate(world, rand, k14, l16, k19);
		}

		byte byte1 = 0;
		if (biome == Biome.FOREST) {
			byte1 = 2;
		}
		if (biome == Biome.JUNGLE) {
			byte1 = 10;
		}
		if (biome == Biome.FOREST_HILLS) {
			byte1 = 2;
		}
		if (biome == Biome.TAIGA) {
			byte1 = 1;
		}
		if (biome == Biome.PLAINS) {
			byte1 = 10;
		}
		for (int l14 = 0; l14 < byte1; l14++) {
			boolean flag = (biome == Biome.JUNGLE && rand.nextInt(3) != 0);
			int l19 = x + rand.nextInt(16) + 8;
			int k22 = rand.nextInt(128);
			int j24 = z + rand.nextInt(16) + 8;
			if (flag) {
				longGrassGenRainforest.generate(world, rand, l19, k22, j24);
			} else {
				longGrassGenNormal.generate(world, rand, l19, k22, j24);
			}
		}

		byte1 = 0;
		if (biome == Biome.DESERT) {
			byte1 = 2;
		}
		for (int i15 = 0; i15 < byte1; i15++) {
			int i17 = x + rand.nextInt(16) + 8;
			int i20 = rand.nextInt(128);
			int l22 = z + rand.nextInt(16) + 8;
			deadBushGen.generate(world, rand, i17, i20, l22);
		}

		if (rand.nextInt(2) == 0) {
			int j15 = x + rand.nextInt(16) + 8;
			int j17 = rand.nextInt(128);
			int j20 = z + rand.nextInt(16) + 8;
			redFlowerGen.generate(world, rand, j15, j17, j20);
		}
		if (rand.nextInt(4) == 0) {
			int k15 = x + rand.nextInt(16) + 8;
			int k17 = rand.nextInt(128);
			int k20 = z + rand.nextInt(16) + 8;
			brownMushroomGen.generate(world, rand, k15, k17, k20);
		}
		if (rand.nextInt(8) == 0) {
			int l15 = x + rand.nextInt(16) + 8;
			int l17 = rand.nextInt(128);
			int l20 = z + rand.nextInt(16) + 8;
			redMushroomGen.generate(world, rand, l15, l17, l20);
		}
		for (int i16 = 0; i16 < 10; i16++) {
			int i18 = x + rand.nextInt(16) + 8;
			int i21 = rand.nextInt(128);
			int i23 = z + rand.nextInt(16) + 8;
			reedGen.generate(world, rand, i18, i21, i23);
		}

		if (rand.nextInt(32) == 0) {
			int j16 = x + rand.nextInt(16) + 8;
			int j18 = rand.nextInt(128);
			int j21 = z + rand.nextInt(16) + 8;
			pumpkinGen.generate(world, rand, j16, j18, j21);
		}
		int k16 = 0;
		if (biome == Biome.DESERT) {
			k16 += 10;
		}
		for (int k18 = 0; k18 < k16; k18++) {
			int k21 = x + rand.nextInt(16) + 8;
			int j23 = rand.nextInt(128);
			int k24 = z + rand.nextInt(16) + 8;
			cactusGen.generate(world, rand, k21, j23, k24);
		}

		for (int l18 = 0; l18 < 50; l18++) {
			int l21 = x + rand.nextInt(16) + 8;
			int k23 = rand.nextInt(rand.nextInt(120) + 8);
			int l24 = z + rand.nextInt(16) + 8;
			liquidWaterGen.generate(world, rand, l21, k23, l24);
		}

		for (int i19 = 0; i19 < 20; i19++) {
			int i22 = x + rand.nextInt(16) + 8;
			int l23 = rand.nextInt(rand.nextInt(rand.nextInt(112) + 8) + 8);
			int i25 = z + rand.nextInt(16) + 8;
			liquidLavaGen.generate(world, rand, i22, l23, i25);
		}

		temperatures = this.wcm.getTemperatures(temperatures, x + 8, z + 8, 16, 16);
		for (int j19 = x + 8; j19 < x + 8 + 16; j19++) {
			for (int j22 = z + 8; j22 < z + 8 + 16; j22++) {
				int i24 = j19 - (x + 8);
				int j25 = j22 - (z + 8);
				int y = getHighestSolidOrLiquidBlock(j19, j22);
				double temp = temperatures[(i24 << 4) | j25] - (double)(y - 64) / 64D * 0.3D;
				Material m = world.getBlockAt(j19, y - 1, j22).getType();
				if ((temp < 0.5D) && y > 0 && y < 128 && world.getBlockTypeIdAt(j19, y, j22) ==
						0 && Util.isSolid(world, j19, y - 1, j22) && m != ICE) {
					world.getBlockAt(j19, y, j22).setTypeIdAndData(SNOW.getId(), (byte)0,
							false);
				}
			}

		}
		if (emeraldGen != null) {
			for (int j4 = 0; j4 < 5; j4++) {
				int k7 = x + rand.nextInt(16);
				int l10 = rand.nextInt(16) + rand.nextInt(16);
				int k13 = z + rand.nextInt(16);
				emeraldGen.generate(world, rand, k7, l10, k13);
			}
		}
		if (this.mineshaftGen != null) {
			this.mineshaftGen.generate(world, rand, chunkX, chunkZ);
		}
		if (this.villageGen != null) {
			this.villageGen.generate(world, rand, chunkX, chunkZ);
		}
		if (this.strongholdGen != null) {
			this.strongholdGen.generate(world, rand, chunkX, chunkZ);
		}
		if (this.largeFeatureGen != null) {
			this.largeFeatureGen.generate(world, rand, chunkX, chunkZ);
		}
	}

	public int getHighestSolidOrLiquidBlock(int i, int j) {
		Chunk chunk = world.getChunkAt(i >> 4, j >> 4);
		int k = 127;

		i &= 15;

		for (j &= 15; k > 0; --k) {
			int l = chunk.getBlock(i, k, j).getTypeId();
			Material material = l == 0 ? Material.AIR : Material.getMaterial(l);

			if (material.isSolid() || Util.isLiquid(material)) {
				return k + 1;
			}
		}

		return -1;
	}

	@Override
	public boolean canSpawn(World w, int x, int z) {
		this.plugin.initWorld(w);
		int y;

		for (y = 63; w.getBlockTypeIdAt(x, y + 1, z) != 0; ++y) {
		}

		return w.getBlockTypeIdAt(x, y, z) == Material.SAND.getId();

	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		int x = 0;
		int y = 0;

		for (; !canSpawn(world, x, y); y += random.nextInt(64) - random.nextInt(64)) {
			x += random.nextInt(64) - random.nextInt(64);
		}
		return new Location(world, x, world.getHighestBlockYAt(x, y), y);
	}

	@Override
	public String toString() {
		return new StringBuilder(plugin.getDescription().getName()).append(" ").append(
				plugin.getDescription().getVersion()).append(" world: ").append(world.getName()).
				toString();
	}
}

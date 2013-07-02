package com.github.barteks2x.b173gen.generator;

import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.generator.beta173.*;
import com.github.barteks2x.b173gen.plugin.Generator;
import java.util.*;
import net.minecraft.server.v1_6_R1.*;
import org.bukkit.Location;
import org.bukkit.generator.ChunkGenerator;

import static net.minecraft.server.v1_6_R1.Block.*;

public class ChunkProviderGenerate extends ChunkGenerator implements IChunkProvider {

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
	private WorldGenBase caves;
	private BiomeBase[] biomes;
	private double noise3[];
	private double noise1[];
	private double noise2[];
	private double noise6[];
	private double noise7[];
	private int i[][] = new int[32][32];
	private float[] temperatures;
	private WorldChunkManagerOld wcm;
	private List<org.bukkit.generator.BlockPopulator> populatorList;
	private World world;
	private WorldConfig config;
	private Generator plugin;
	private WorldGenerator dungeonGen,
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
			longGrassGen1,
			longGrassGen2,
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
	private WorldGenCanyon canyonGen;
	private WorldGenStronghold strongholdGen;
	private WorldGenMineshaft mineshaftGen;
	private WorldGenVillage villageGen;
	//temples
	private WorldGenLargeFeature largeFeatureGen;
	private boolean isInit = false;

	@Override
	public List<org.bukkit.generator.BlockPopulator> getDefaultPopulators(org.bukkit.World w) {
		plugin.initWorld(w);
		return populatorList;
	}

	@SuppressWarnings("unchecked")
	public void init(World workWorld, WorldChunkManagerOld wcm, long seed) {
		isInit = true;
		world = workWorld;
		this.wcm = wcm;

		rand = new Random(seed);
		noiseGen1 = new NoiseGeneratorOctaves3D(rand, 16);
		noiseGen2 = new NoiseGeneratorOctaves3D(rand, 16);
		noiseGen3 = new NoiseGeneratorOctaves3D(rand, 8);
		noiseGen4 = new NoiseGeneratorOctaves3D(rand, 4);
		noiseGen5 = new NoiseGeneratorOctaves3D(rand, 4);
		noiseGen6 = new NoiseGeneratorOctaves3D(rand, 10);
		noiseGen7 = new NoiseGeneratorOctaves3D(rand, 16);
		treeNoise = new NoiseGeneratorOctaves3D(rand, 8);

		i = new int[32][32];

		clayGen = config.newClayGen ? new WorldGenClay(32) :
			new WorldGenClayOld(32);
		waterLakeGen = config.newLakeGen ? new WorldGenLakes(WATER.id) :
				new WorldGenLakesOld(WATER.id);
		lavaLakeGen = config.newLakeGen ? new WorldGenLakes(LAVA.id) :
				new WorldGenLakesOld(LAVA.id);
		caves = config.newCaveGen ? new WorldGenCaves() : new WorldGenCavesOld();

		dungeonGen = new WorldGenDungeons();
		dirtGen = new WorldGenMinable(DIRT.id, 32);
		gravelGen = new WorldGenMinable(GRAVEL.id, 32);
		coalGen = new WorldGenMinable(COAL_ORE.id, 16);
		ironGen = new WorldGenMinable(IRON_ORE.id, 8);
		goldGen = new WorldGenMinable(GOLD_ORE.id, 8);
		redstoneGen = new WorldGenMinable(REDSTONE_ORE.id, 7);
		diamondGen = new WorldGenMinable(DIAMOND_ORE.id, 7);
		lapisGen = new WorldGenMinable(LAPIS_ORE.id, 6);
		yellowFlowerGen = new WorldGenFlowers(YELLOW_FLOWER.id);
		longGrassGen1 = new WorldGenGrass(LONG_GRASS.id, 1);
		longGrassGen2 = new WorldGenGrass(LONG_GRASS.id, 2);
		deadBushGen = new WorldGenDeadBush(DEAD_BUSH.id);
		redFlowerGen = new WorldGenFlowers(RED_ROSE.id);
		brownMushroomGen = new WorldGenFlowers(BROWN_MUSHROOM.id);
		redMushroomGen = new WorldGenFlowers(RED_MUSHROOM.id);
		reedGen = new WorldGenReed();
		pumpkinGen = new WorldGenPumpkin();
		cactusGen = new WorldGenCactus();
		liquidWaterGen = new WorldGenLiquids(WATER.id);
		liquidLavaGen = new WorldGenLiquids(LAVA.id);
		workWorld.worldData.setType(WorldType.FLAT);
		canyonGen = config.generateCanyons ? new WorldGenCanyon() : null;
		strongholdGen = config.generateStrongholds ? new WorldGenStronghold() : null;
		mineshaftGen = config.generateMineshafts ? new WorldGenMineshaft() : null;
		villageGen = config.generateVillages ? new WorldGenVillage() : null;
		largeFeatureGen = config.generateTemples ? new WorldGenLargeFeature173() : null;
		emeraldGen = config.generateEmerald ? new WorldGenMinable(EMERALD_ORE.id, 2) : null;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public ChunkProviderGenerate(WorldConfig config, Generator plugin) {
		this.plugin = plugin;
		this.config = config;
		this.populatorList = new ArrayList();
		this.populatorList.add(new BlockPopulator(this));
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
				for (int ySection = 0; ySection < 16; ySection++) {
					double d = 0.125D;
					double d1 = noise[((i1 + 0) * l + (j1 + 0)) * b2 + (ySection + 0)];
					double d2 = noise[((i1 + 0) * l + (j1 + 1)) * b2 + (ySection + 0)];
					double d3 = noise[((i1 + 1) * l + (j1 + 0)) * b2 + (ySection + 0)];
					double d4 = noise[((i1 + 1) * l + (j1 + 1)) * b2 + (ySection + 0)];
					double d5 =
							(noise[((i1 + 0) * l + (j1 + 0)) * b2 + (ySection + 1)] -
							d1) * d;
					double d6 =
							(noise[((i1 + 0) * l + (j1 + 1)) * b2 + (ySection + 1)] -
							d2) * d;
					double d7 =
							(noise[((i1 + 1) * l + (j1 + 0)) * b2 + (ySection + 1)] -
							d3) * d;
					double d8 =
							(noise[((i1 + 1) * l + (j1 + 1)) * b2 + (ySection + 1)] -
							d4) * d;
					for (int l1 = 0; l1 < 8; l1++) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						for (int i2 = 0; i2 < 4; i2++) {
							int blockLoc = i2 + i1 * 4 << 11 |
									0 + j1 * 4 << 7 | ySection * 8 + l1;
							char c = '\200';
							double d14 = 0.25D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;
							for (int k2 = 0; k2 < 4; k2++) {
								double d17 = temperatures[(i1 * 4 + i2) * 16 +
										(j1 * 4 + k2)];
								int block = 0;
								if (ySection * 8 + l1 < oceanHeight) {
									if (d17 < 0.5D && ySection * 8 +
											l1 >=
											oceanHeight - 1) {
										block = ICE.id;
									} else {
										block =
												STATIONARY_WATER.id;
									}
								}
								if (d15 > 0.0D) {
									block = STONE.id;
								}
								terrain[blockLoc] = (byte)block;
								blockLoc += c;
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
	@SuppressWarnings("deprecation")
	public byte[] generate(org.bukkit.World w, Random random, int x, int z) {
		this.rand.setSeed(x * 341873128712L + z * 132897987541L);
		byte terrain[] = new byte[32768];
		biomes = wcm.getBiomeBlock(biomes, x * 16, z * 16, 16, 16);
		double temp[] = this.wcm.temperatures;
		generateTerrain(x, z, terrain, temp);
		replaceBlocksForBiome(x, z, terrain, biomes);
		caves.a(this, world, x, z, terrain);
		if (canyonGen != null) {
			canyonGen.a(this, world, x, z, terrain);
		}
		if (strongholdGen != null) {
			strongholdGen.a(this, world, x, z, terrain);
		}
		if (mineshaftGen != null) {
			mineshaftGen.a(this, world, x, z, terrain);
		}
		if (villageGen != null) {
			villageGen.a(this, world, x, z, terrain);
		}
		if (largeFeatureGen != null) {
			largeFeatureGen.a(this, world, x, z, terrain);
		}
		return terrain;
	}

	public void replaceBlocksForBiome(int xPos, int zPos, byte terrain[], BiomeBase biomes[]) {
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
				BiomeBase biome = biomes[x + z * 16];
				boolean sand = sandNoise[x + z * 16] + rand.nextDouble() * 0.2D >
						0.0D;
				boolean gravel =
						gravelNoise[x + z * 16] + rand.nextDouble() * 0.2D > 3D;
				int depth = (int)(stoneNoise[x + z * 16] / 3D + 3D + rand.
						nextDouble() * 0.25D);
				int prevDepth = -1;
				byte topBlock = biome.A;
				byte fillerBlock = biome.B;
				for (int y = 127; y >= 0; y--) {
					int blockIndex = (((z << 4) + x) << 7) + y;
					if (y <= 0 + rand.nextInt(5)) {
						terrain[blockIndex] = (byte)BEDROCK.id;
						continue;
					}
					byte blockID = terrain[blockIndex];
					if (blockID == 0) {
						prevDepth = -1;
						continue;
					}
					if (blockID != STONE.id) {
						continue;
					}
					if (prevDepth == -1) {
						if (depth <= 0) {
							topBlock = 0;
							fillerBlock =
									(byte)STONE.id;
						} else if (y >= oceanHeight - 4 &&
								y <= oceanHeight + 1) {
							topBlock = biome.A;
							fillerBlock = biome.B;
							if (gravel) {
								topBlock = 0;
								fillerBlock = (byte)GRAVEL.id;
							}
							if (sand) {
								topBlock = (byte)SAND.id;
								fillerBlock = (byte)SAND.id;
							}
						}
						if (y < oceanHeight && topBlock == 0) {
							topBlock = (byte)STATIONARY_WATER.id;
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
					if (prevDepth == 0 && fillerBlock == SAND.id) {
						prevDepth = rand.nextInt(4);
						fillerBlock = (byte)SANDSTONE.id;
					}
				}

			}

		}
	}

	public Chunk getChunkAt(int i, int j) {
		return getOrCreateChunk(i, j);
	}

	public Chunk getOrCreateChunk(int i, int i1) {
		byte terrain[] = generate(world.getWorld(), rand, i, i1);

		Chunk chunk = new Chunk(world, terrain, i, i1);
		chunk.initLighting();
		return chunk;
	}

	public void b() {
		//TODO ???
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
		noise3 = noiseGen3.generateNoiseArray(noise3, xPos, yPos, zPos, xSize, ySize, zSize, d0 /
				80D, d1 /
				160D, d0 / 80D);
		noise1 = noiseGen1.generateNoiseArray(noise1, xPos, yPos, zPos, xSize, ySize, zSize, d0, d1,
				d0);
		noise2 = noiseGen2.generateNoiseArray(noise2, xPos, yPos, zPos, xSize, ySize, zSize, d0, d1,
				d0);
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
						double d13 = (float)(y - (ySize - 4)) / 3F;
						d8 = d8 * (1.0D - d13) + -10D * d13;
					}
					array[k1] = d8;
					k1++;
				}
			}
		}
		return array;
	}

	public void getChunkAt(IChunkProvider c, int chunkX, int chunkZ) {
		BlockSand.instaFall = true;
		int x = chunkX * 16;
		int z = chunkZ * 16;
		BiomeGenBase biome = this.wcm.getBiome(x + 16, z + 16);
		rand.setSeed(world.getSeed());
		long rand1 = (rand.nextLong() / 2L) * 2L + 1L;
		long rand2 = (rand.nextLong() / 2L) * 2L + 1L;
		rand.setSeed((long)chunkX * rand1 + (long)chunkZ * rand2 ^ world.getSeed());
		boolean villageFlag = false;
		if (this.mineshaftGen != null) {
			this.mineshaftGen.a(world, rand, chunkX, chunkZ);
		}
		if (this.villageGen != null) {
			villageFlag = this.villageGen.a(world, rand, chunkX, chunkZ);
		}
		if (this.strongholdGen != null) {
			this.strongholdGen.a(world, rand, chunkX, chunkZ);
		}
		if (this.largeFeatureGen != null) {
			this.largeFeatureGen.a(world, rand, chunkX, chunkZ);
		}
		if (!villageFlag && rand.nextInt(4) == 0) {
			int X = x + rand.nextInt(16) + 8;
			int Y = rand.nextInt(128);
			int Z = z + rand.nextInt(16) + 8;
			waterLakeGen.a(world, rand, X, Y, Z);
		}
		if (!villageFlag && rand.nextInt(8) == 0) {
			int X = x + rand.nextInt(16) + 8;
			int Y = rand.nextInt(rand.nextInt(120) + 8);
			int Z = z + rand.nextInt(16) + 8;
			if (Y < 64 || rand.nextInt(10) == 0) {
				lavaLakeGen.a(world, rand, X, Y, Z);
			}
		}
		for (int j = 0; j < 8; j++) {
			int X = x + rand.nextInt(16) + 8;
			int Y = rand.nextInt(128);
			int Z = z + rand.nextInt(16) + 8;
			dungeonGen.a(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 10; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(128);
			int Z = z + rand.nextInt(16);
			clayGen.a(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 20; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(128);
			int Z = z + rand.nextInt(16);
			dirtGen.a(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 10; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(128);
			int Z = z + rand.nextInt(16);
			gravelGen.a(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 20; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(128);
			int Z = z + rand.nextInt(16);
			coalGen.a(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 20; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(64);
			int Z = z + rand.nextInt(16);
			ironGen.a(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 2; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(32);
			int Z = z + rand.nextInt(16);
			goldGen.a(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 8; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(16);
			int Z = z + rand.nextInt(16);
			redstoneGen.a(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 1; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(16);
			int Z = z + rand.nextInt(16);
			diamondGen.a(world, rand, X, Y, Z);
		}

		for (int j = 0; j < 1; j++) {
			int X = x + rand.nextInt(16);
			int Y = rand.nextInt(16) + rand.nextInt(16);
			int Z = z + rand.nextInt(16);
			lapisGen.a(world, rand, X, Y, Z);
		}

		int treesRand = (int)((treeNoise.generateNoise(x * 0.5D, z * 0.5D) / 8D +
				rand.nextDouble() * 4D + 4D) / 3D);
		int trees = 0;
		if (rand.nextInt(10) == 0) {
			trees++;
		}
		if (biome == BiomeGenBase.forest) {
			trees += treesRand + 5;
		}
		if (biome == BiomeGenBase.rainforest) {
			trees += treesRand + 5;
		}
		if (biome == BiomeGenBase.seasonalForest) {
			trees += treesRand + 2;
		}
		if (biome == BiomeGenBase.taiga) {
			trees += treesRand + 5;
		}
		if (biome == BiomeGenBase.desert) {
			trees -= 20;
		}
		if (biome == BiomeGenBase.tundra) {
			trees -= 20;
		}
		if (biome == BiomeGenBase.plains) {
			trees -= 20;
		}
		for (int i11 = 0; i11 < trees; i11++) {
			int l13 = x + rand.nextInt(16) + 8;
			int j14 = z + rand.nextInt(16) + 8;
			WorldGenerator worldgenerator = biome.a(rand);
			worldgenerator.a(1.0D, 1.0D, 1.0D);
			worldgenerator.a(world, rand, l13, world.getHighestBlockYAt(l13, j14), j14);
		}

		byte flowers = 0;
		if (biome == BiomeGenBase.forest) {
			flowers = 2;
		}
		if (biome == BiomeGenBase.seasonalForest) {
			flowers = 4;
		}
		if (biome == BiomeGenBase.taiga) {
			flowers = 2;
		}
		if (biome == BiomeGenBase.plains) {
			flowers = 3;
		}
		for (int i14 = 0; i14 < flowers; i14++) {
			int k14 = x + rand.nextInt(16) + 8;
			int l16 = rand.nextInt(128);
			int k19 = z + rand.nextInt(16) + 8;
			yellowFlowerGen.a(world, rand, k14, l16, k19);
		}

		byte byte1 = 0;
		if (biome == BiomeGenBase.forest) {
			byte1 = 2;
		}
		if (biome == BiomeGenBase.rainforest) {
			byte1 = 10;
		}
		if (biome == BiomeGenBase.seasonalForest) {
			byte1 = 2;
		}
		if (biome == BiomeGenBase.taiga) {
			byte1 = 1;
		}
		if (biome == BiomeGenBase.plains) {
			byte1 = 10;
		}
		for (int l14 = 0; l14 < byte1; l14++) {
			boolean flag = (biome == BiomeGenBase.rainforest && rand.nextInt(3) != 0);
			int l19 = x + rand.nextInt(16) + 8;
			int k22 = rand.nextInt(128);
			int j24 = z + rand.nextInt(16) + 8;
			if (flag) {
				longGrassGen2.a(world, rand, l19, k22, j24);
			} else {
				longGrassGen1.a(world, rand, l19, k22, j24);
			}
		}

		byte1 = 0;
		if (biome == BiomeGenBase.desert) {
			byte1 = 2;
		}
		for (int i15 = 0; i15 < byte1; i15++) {
			int i17 = x + rand.nextInt(16) + 8;
			int i20 = rand.nextInt(128);
			int l22 = z + rand.nextInt(16) + 8;
			deadBushGen.a(world, rand, i17, i20, l22);
		}

		if (rand.nextInt(2) == 0) {
			int j15 = x + rand.nextInt(16) + 8;
			int j17 = rand.nextInt(128);
			int j20 = z + rand.nextInt(16) + 8;
			redFlowerGen.a(world, rand, j15, j17, j20);
		}
		if (rand.nextInt(4) == 0) {
			int k15 = x + rand.nextInt(16) + 8;
			int k17 = rand.nextInt(128);
			int k20 = z + rand.nextInt(16) + 8;
			brownMushroomGen.a(world, rand, k15, k17, k20);
		}
		if (rand.nextInt(8) == 0) {
			int l15 = x + rand.nextInt(16) + 8;
			int l17 = rand.nextInt(128);
			int l20 = z + rand.nextInt(16) + 8;
			redMushroomGen.a(world, rand, l15, l17, l20);
		}
		for (int i16 = 0; i16 < 10; i16++) {
			int i18 = x + rand.nextInt(16) + 8;
			int i21 = rand.nextInt(128);
			int i23 = z + rand.nextInt(16) + 8;
			reedGen.a(world, rand, i18, i21, i23);
		}

		if (rand.nextInt(32) == 0) {
			int j16 = x + rand.nextInt(16) + 8;
			int j18 = rand.nextInt(128);
			int j21 = z + rand.nextInt(16) + 8;
			pumpkinGen.a(world, rand, j16, j18, j21);
		}
		int k16 = 0;
		if (biome == BiomeGenBase.desert) {
			k16 += 10;
		}
		for (int k18 = 0; k18 < k16; k18++) {
			int k21 = x + rand.nextInt(16) + 8;
			int j23 = rand.nextInt(128);
			int k24 = z + rand.nextInt(16) + 8;
			cactusGen.a(world, rand, k21, j23, k24);
		}

		for (int l18 = 0; l18 < 50; l18++) {
			int l21 = x + rand.nextInt(16) + 8;
			int k23 = rand.nextInt(rand.nextInt(120) + 8);
			int l24 = z + rand.nextInt(16) + 8;
			liquidWaterGen.a(world, rand, l21, k23, l24);
		}

		for (int i19 = 0; i19 < 20; i19++) {
			int i22 = x + rand.nextInt(16) + 8;
			int l23 = rand.nextInt(rand.nextInt(rand.nextInt(112) + 8) + 8);
			int i25 = z + rand.nextInt(16) + 8;
			liquidLavaGen.a(world, rand, i22, l23, i25);
		}

		temperatures = this.wcm.getTemperatures(temperatures, x + 8, z + 8, 16, 16);
		for (int j19 = x + 8; j19 < x + 8 + 16; j19++) {
			for (int j22 = z + 8; j22 < z + 8 + 16; j22++) {
				int i24 = j19 - (x + 8);
				int j25 = j22 - (z + 8);
				int k25 = world.getHighestBlockYAt(j19, j22);
				double d1 = temperatures[i24 * 16 + j25] -
						((double)(k25 - 64) / 64D) * 0.29999999999999999D;
				Material m = world.getMaterial(j19, k25 - 1, j22);
				if (d1 < 0.5D && k25 > 0 && k25 < 128 && world.
						isEmpty(j19, k25, j22) && m.isSolid() && m != Material.ICE) {
					world.setTypeIdAndData(j19, k25, j22, SNOW.id, 0, 2);
				}
			}

		}
		if (emeraldGen != null) {
			for (int j4 = 0; j4 < 5; j4++) {
				int k7 = x + rand.nextInt(16);
				int l10 = rand.nextInt(16) + rand.nextInt(16);
				int k13 = z + rand.nextInt(16);
				emeraldGen.a(world, rand, k7, l10, k13);
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
		return this.toString();
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

	public ChunkPosition findNearestMapFeature(World world, String type, int x, int y, int z) {
		return ((("Stronghold".equals(type)) && (this.strongholdGen != null)) ?
				this.strongholdGen.getNearestGeneratedFeature(world, x, y, z) : null);
	}

	public int getLoadedChunks() {
		return 0;
	}

	public String getName() {
		return plugin.getDescription().getName();
	}

	public void recreateStructures(int arg0, int arg1) {
		if (strongholdGen != null) {
			strongholdGen.a(this, world, arg0, arg1, (byte[])null);
		}

		if (largeFeatureGen != null) {
			largeFeatureGen.a(this, world, arg0, arg1, (byte[])null);
		}
	}

	@Override
	public boolean canSpawn(org.bukkit.World w, int x, int z) {
		this.plugin.initWorld(w);
		if (w != null) {
			int id = w.getHighestBlockAt(x, z).getTypeId();
			Material material;
			if (byId[id] != null && (material = byId[id].material) != null) {
				return material.isSolid();
			} else {
				return false;
			}

		} else {
			return false;
		}

	}

	@Override
	public Location getFixedSpawnLocation(org.bukkit.World world, Random random) {
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
				plugin.getDescription().getVersion()).append(" world: ").append(world.
				getWorld().getName()).toString();
	}
}

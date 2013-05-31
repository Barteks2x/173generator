package org.Barteks2x.b173gen.generator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.server.v1_5_R3.*;

import org.Barteks2x.b173gen.generator.beta173.*;
import org.Barteks2x.b173gen.plugin.Generator;
import org.Barteks2x.b173gen.config.WorldConfig;
import org.bukkit.generator.ChunkGenerator;

import static net.minecraft.server.v1_5_R3.Block.*;

public class ChunkProviderGenerate extends ChunkGenerator implements IChunkProvider {

	private Random rand;
	private NoiseGeneratorOctavesOld noiseOctaves1;
	private NoiseGeneratorOctavesOld noiseOctaves2;
	private NoiseGeneratorOctavesOld noiseOctaves3;
	private NoiseGeneratorOctavesOld noiseOctaves4;
	private NoiseGeneratorOctavesOld noiseOctaves5;
	private NoiseGeneratorOctavesOld noiseOctaves6;
	private NoiseGeneratorOctavesOld noiseOctaves7;
	private NoiseGeneratorOctavesOld mobSpawnerNoise;
	private double noise[];
	private double sandNoise[] = new double[256];
	private double gravelNoise[] = new double[256];
	private double stoneNoise[] = new double[256];
	private WorldGenBase caves;
	private BiomeBase[] biomes;
	private double d[];
	private double e[];
	private double f[];
	private double g[];
	private double h[];
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
	//desert temples
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
		noiseOctaves1 = new NoiseGeneratorOctavesOld(rand, 16);
		noiseOctaves2 = new NoiseGeneratorOctavesOld(rand, 16);
		noiseOctaves3 = new NoiseGeneratorOctavesOld(rand, 8);
		noiseOctaves4 = new NoiseGeneratorOctavesOld(rand, 4);
		noiseOctaves5 = new NoiseGeneratorOctavesOld(rand, 4);
		noiseOctaves6 = new NoiseGeneratorOctavesOld(rand, 10);
		noiseOctaves7 = new NoiseGeneratorOctavesOld(rand, 16);
		mobSpawnerNoise = new NoiseGeneratorOctavesOld(rand, 8);

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
		/**if(largeFeatureGen!=null){
			try {
				Field field = largeFeatureGen.getClass().getDeclaredField("e");
				field.setAccessible(true);
				List<BiomeBase> l = (List<BiomeBase>) field.get(null);
				List<BiomeBase> l2 = new ArrayList<BiomeBase>(l.size()+8);
				l2.addAll(l);
				l2.add((BiomeBase)BiomeGenBase.desert);
				l2.add((BiomeBase)BiomeGenBase.rainforest);
				l2.add((BiomeBase)BiomeGenBase.swampland);
				field.set(null, l2);
				field.setAccessible(false);
				ChunkPosition pos = largeFeatureGen.getNearestGeneratedFeature(world, 200, 64, 394);
				plugin.getLogger().info(pos.x+", "+pos.y+", "+pos.z);
			} catch (NoSuchFieldException ex) {
				Logger.getLogger(ChunkProviderGenerate.class.getName()).
					log(Level.SEVERE, null, ex);
			} catch (SecurityException ex) {
				Logger.getLogger(ChunkProviderGenerate.class.getName()).
					log(Level.SEVERE, null, ex);
			} catch (IllegalArgumentException ex) {
				Logger.getLogger(ChunkProviderGenerate.class.getName()).
					log(Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(ChunkProviderGenerate.class.getName()).
					log(Level.SEVERE, null, ex);
			} catch (ClassCastException ex){
				Logger.getLogger(ChunkProviderGenerate.class.getName()).
					log(Level.SEVERE, null, ex);
			}
			
		}**/
		emeraldGen = config.generateEmerald ? new WorldGenMinable(EMERALD_ORE.id, 2) : null;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public ChunkProviderGenerate(WorldConfig config, Generator plugin) {
		this.plugin = plugin;
		this.config = config;
		this.populatorList = new ArrayList();
		this.populatorList.add(new BlockPopulator(this));
	}

	public void generateTerrain(int i, int j, byte terrain[], double ad[]) {
		//plugin.getLogger().log(Level.INFO, "genChunkAt "+i+", "+j);
		byte byte0 = 4;
		byte byte1 = 64;
		int k = byte0 + 1;
		byte b2 = 17;
		int l = byte0 + 1;
		noise = initNoiseField(noise, i * byte0, 0, j * byte0, k, b2, l);
		for (int i1 = 0; i1 < byte0; i1++) {
			for (int j1 = 0; j1 < byte0; j1++) {
				for (int k1 = 0; k1 < 16; k1++) {
					double d = 0.125D;
					double d1 = noise[((i1 + 0) * l + (j1 + 0)) * b2 + (k1 + 0)];
					double d2 = noise[((i1 + 0) * l + (j1 + 1)) * b2 + (k1 + 0)];
					double d3 = noise[((i1 + 1) * l + (j1 + 0)) * b2 + (k1 + 0)];
					double d4 = noise[((i1 + 1) * l + (j1 + 1)) * b2 + (k1 + 0)];
					double d5 =
						(noise[((i1 + 0) * l + (j1 + 0)) * b2 + (k1 + 1)] -
						d1) * d;
					double d6 =
						(noise[((i1 + 0) * l + (j1 + 1)) * b2 + (k1 + 1)] -
						d2) * d;
					double d7 =
						(noise[((i1 + 1) * l + (j1 + 0)) * b2 + (k1 + 1)] -
						d3) * d;
					double d8 =
						(noise[((i1 + 1) * l + (j1 + 1)) * b2 + (k1 + 1)] -
						d4) * d;
					for (int l1 = 0; l1 < 8; l1++) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						for (int i2 = 0; i2 < 4; i2++) {
							int blockLoc = i2 + i1 * 4 << 11 |
								0 + j1 * 4 << 7 | k1 * 8 + l1;
							char c = '\200';
							double d14 = 0.25D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;
							for (int k2 = 0; k2 < 4; k2++) {
								double d17 = ad[(i1 * 4 + i2) * 16 +
									(j1 * 4 + k2)];
								int block = 0;
								if (k1 * 8 + l1 < byte1) {
									if (d17 < 0.5D && k1 * 8 +
										l1 >=
										byte1 - 1) {
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
		double ad[] = this.wcm.temperature;
		generateTerrain(x, z, terrain, ad);
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

	public void replaceBlocksForBiome(int i, int j, byte terrainBlockArray[],
		BiomeBase abiomebase[]) {
		byte byte0 = 64;
		double d = 0.03125D;
		sandNoise = noiseOctaves4.
			gen(sandNoise, i * 16, j * 16, 0.0D, 16, 16, 1, d, d, 1.0D);
		gravelNoise = noiseOctaves4.
			gen(gravelNoise, i * 16, 109.0134D, j * 16, 16, 1, 16, d, 1.0D, d);
		stoneNoise = noiseOctaves5.gen(stoneNoise, i * 16, j * 16, 0.0D, 16, 16, 1, d * 2D,
			d * 2D, d * 2D);
		for (int k = 0; k < 16; k++) {
			for (int l = 0; l < 16; l++) {
				BiomeBase biomegenbase = abiomebase[k + l * 16];
				boolean randomSand = sandNoise[k + l * 16] + rand.nextDouble() *
					0.20000000000000001D > 0.0D;
				boolean randomGravel = gravelNoise[k + l * 16] + rand.nextDouble() *
					0.20000000000000001D > 3D;
				int i1 = (int)(stoneNoise[k + l * 16] / 3D + 3D +
					rand.nextDouble() * 0.25D);
				int j1 = -1;
				byte topBlock = biomegenbase.A;
				byte fillerBlock = biomegenbase.B;
				for (int k1 = 127; k1 >= 0; k1--) {
					int currentBlockInArrayNum = (l * 16 + k) * 128 + k1;
					if (k1 <= 0 + rand.nextInt(5)) {
						terrainBlockArray[currentBlockInArrayNum] =
							(byte)BEDROCK.id;
						continue;
					}
					byte currentBlock =
						terrainBlockArray[currentBlockInArrayNum];
					if (currentBlock == 0) {
						j1 = -1;
						continue;
					}
					if (currentBlock != STONE.id) {
						continue;
					}
					if (j1 == -1) {
						if (i1 <= 0) {
							topBlock = 0;
							fillerBlock =
								(byte)STONE.id;
						} else if (k1 >= byte0 - 4 &&
							k1 <= byte0 + 1) {
							topBlock = biomegenbase.A;
							fillerBlock = biomegenbase.B;
							if (randomGravel) {
								topBlock = 0;
							}
							if (randomGravel) {
								fillerBlock = (byte)GRAVEL.id;
							}
							if (randomSand) {
								topBlock = (byte)SAND.id;
							}
							if (randomSand) {
								fillerBlock = (byte)SAND.id;
							}
						}
						if (k1 < byte0 && topBlock == 0) {
							topBlock =
								(byte)STATIONARY_WATER.id;
						}
						j1 = i1;
						if (k1 >= byte0 - 1) {
							terrainBlockArray[currentBlockInArrayNum] =
								topBlock;
						} else {
							terrainBlockArray[currentBlockInArrayNum] =
								fillerBlock;
						}
						continue;
					}
					if (j1 <= 0) {
						continue;
					}
					j1--;
					terrainBlockArray[currentBlockInArrayNum] =
						fillerBlock;
					if (j1 == 0 && fillerBlock == SAND.id) {
						j1 = rand.nextInt(4);
						fillerBlock = (byte)SANDSTONE.id;
					}
				}

			}

		}
	}

	public Chunk getChunkAt(int i, int j) {
		return getOrCreateChunk(i, j);
	}

	@SuppressWarnings("cast")
	public Chunk getOrCreateChunk(int i, int i1) {
		rand.setSeed((long)i * 0x4f9939f508L + (long)i1 * 0x1ef1565bd5L);
		byte terrain[] = new byte[32768];
		Chunk chunk = new Chunk(world, terrain, i, i1);
		biomes = this.wcm.getBiomeBlock(biomes, i * 16, i1 * 16, 16, 16);
		double temp[] = this.wcm.temperature;
		generateTerrain(i, i1, terrain, temp);
		replaceBlocksForBiome(i, i1, terrain, biomes);
		caves.a(this, world, i, i1, terrain);
		if (canyonGen != null) {
			canyonGen.a(this, world, i, i1, terrain);
		}
		if (strongholdGen != null) {
			strongholdGen.a(this, world, i, i1, terrain);
		}
		if (mineshaftGen != null) {
			mineshaftGen.a(this, world, i, i1, terrain);
		}
		if (villageGen != null) {
			villageGen.a(this, world, i, i1, terrain);
		}
		if (largeFeatureGen != null) {
			largeFeatureGen.a(this, world, i, i1, terrain);
		}
		chunk.initLighting();
		return chunk;
	}

	public void b() {
		//TODO ???
	}

	@SuppressWarnings("cast")
	private double[] initNoiseField(double ad[], int i, int j, int k,
		int l, int i1, int j1) {
		if (ad == null) {
			ad = new double[l * i1 * j1];
		}
		double d = 684.41200000000003D;
		double d1 = 684.41200000000003D;
		double ad1[] = this.wcm.temperature;
		double ad2[] = this.wcm.rain;
		g = noiseOctaves6.func_4109_a(g, i, k, l, j1, 1.121D, 1.121D, 0.5D);
		h = noiseOctaves7.func_4109_a(h, i, k, l, j1, 200D, 200D, 0.5D);
		this.d = noiseOctaves3.gen(this.d, i, j, k, l, i1, j1, d / 80D, d1 / 160D, d / 80D);
		e = noiseOctaves1.gen(e, i, j, k, l, i1, j1, d, d1, d);
		f = noiseOctaves2.gen(f, i, j, k, l, i1, j1, d, d1, d);
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
				double d5 = (g[l1] + 256D) / 512D;
				d5 *= d4;
				if (d5 > 1.0D) {
					d5 = 1.0D;
				}
				double d6 = h[l1] / 8000D;
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
				d6 = (d6 * (double)i1) / 16D;
				double d7 = (double)i1 / 2D + d6 * 4D;
				l1++;
				for (int j3 = 0; j3 < i1; j3++) {
					double d8 = 0.0D;
					double d9 = (((double)j3 - d7) * 12D) /
						d5;
					if (d9 < 0.0D) {
						d9 *= 4D;
					}
					double d10 = e[k1] / 512D;
					double d11 = f[k1] / 512D;
					double d12 = (this.d[k1] / 10D +
						1.0D) / 2D;
					if (d12 < 0.0D) {
						d8 = d10;
					} else if (d12 > 1.0D) {
						d8 = d11;
					} else {
						d8 = d10 + (d11 - d10) * d12;
					}
					d8 -= d9;
					if (j3 > i1 - 4) {
						double d13 = (float)(j3 - (i1 - 4)) / 3F;
						d8 = d8 * (1.0D - d13) + -10D * d13;
					}
					ad[k1] = d8;
					k1++;
				}
			}
		}
		return ad;
	}

	@SuppressWarnings("cast")
	public void getChunkAt(IChunkProvider chunkprovider, int i, int j) {
		BlockSand.instaFall = true;
		int k = i * 16;
		int l = j * 16;
		BiomeGenBase biome = this.wcm.getBiome(k + 16, l + 16);
		rand.setSeed(world.getSeed());
		long l1 = (rand.nextLong() / 2L) * 2L + 1L;
		long l2 = (rand.nextLong() / 2L) * 2L + 1L;
		rand.setSeed((long)i * l1 + (long)j * l2 ^ world.getSeed());
		double d = 0.25D;
		boolean villageFlag = false;
		if(this.mineshaftGen!=null){
			this.mineshaftGen.a(world, rand, i, j);
		}
		if(this.villageGen!=null){
			villageFlag = this.villageGen.a(world, rand, i, j);
		}
		if(this.strongholdGen!=null){
			this.strongholdGen.a(world, rand, i, j);
		}
		if(this.largeFeatureGen!=null){
			this.largeFeatureGen.a(world, rand, i, j);
		}
		if (!villageFlag && rand.nextInt(4) == 0) {
			int i1 = k + rand.nextInt(16) + 8;
			int l4 = rand.nextInt(128);
			int i8 = l + rand.nextInt(16) + 8;
			waterLakeGen.a(world, rand, i1, l4, i8);
		}
		if (!villageFlag && rand.nextInt(8) == 0) {
			int j1 = k + rand.nextInt(16) + 8;
			int i5 = rand.nextInt(rand.nextInt(120) + 8);
			int j8 = l + rand.nextInt(16) + 8;
			if (i5 < 64 || rand.nextInt(10) == 0) {
				lavaLakeGen.a(world, rand, j1, i5, j8);
			}
		}
		for (int k1 = 0; k1 < 8; k1++) {
			int j5 = k + rand.nextInt(16) + 8;
			int k8 = rand.nextInt(128);
			int j11 = l + rand.nextInt(16) + 8;
			dungeonGen.a(world, rand, j5, k8, j11);
		}

		for (int i2 = 0; i2 < 10; i2++) {
			int k5 = k + rand.nextInt(16);
			int l8 = rand.nextInt(128);
			int k11 = l + rand.nextInt(16);
			clayGen.a(world, rand, k5, l8, k11);
		}

		for (int j2 = 0; j2 < 20; j2++) {
			int l5 = k + rand.nextInt(16);
			int i9 = rand.nextInt(128);
			int l11 = l + rand.nextInt(16);
			dirtGen.a(world, rand, l5, i9, l11);
		}

		for (int k2 = 0; k2 < 10; k2++) {
			int i6 = k + rand.nextInt(16);
			int j9 = rand.nextInt(128);
			int i12 = l + rand.nextInt(16);
			gravelGen.a(world, rand, i6, j9, i12);
		}

		for (int i3 = 0; i3 < 20; i3++) {
			int j6 = k + rand.nextInt(16);
			int k9 = rand.nextInt(128);
			int j12 = l + rand.nextInt(16);
			coalGen.a(world, rand, j6, k9, j12);
		}

		for (int j3 = 0; j3 < 20; j3++) {
			int k6 = k + rand.nextInt(16);
			int l9 = rand.nextInt(64);
			int k12 = l + rand.nextInt(16);
			ironGen.a(world, rand, k6, l9, k12);
		}

		for (int k3 = 0; k3 < 2; k3++) {
			int l6 = k + rand.nextInt(16);
			int i10 = rand.nextInt(32);
			int l12 = l + rand.nextInt(16);
			goldGen.a(world, rand, l6, i10, l12);
		}

		for (int l3 = 0; l3 < 8; l3++) {
			int i7 = k + rand.nextInt(16);
			int j10 = rand.nextInt(16);
			int i13 = l + rand.nextInt(16);
			redstoneGen.a(world, rand, i7, j10, i13);
		}

		for (int i4 = 0; i4 < 1; i4++) {
			int j7 = k + rand.nextInt(16);
			int k10 = rand.nextInt(16);
			int j13 = l + rand.nextInt(16);
			diamondGen.a(world, rand, j7, k10, j13);
		}

		for (int j4 = 0; j4 < 1; j4++) {
			int k7 = k + rand.nextInt(16);
			int l10 = rand.nextInt(16) + rand.nextInt(16);
			int k13 = l + rand.nextInt(16);
			lapisGen.a(world, rand, k7, l10, k13);
		}

		d = 0.5D;
		int k4 = (int)((mobSpawnerNoise.a((double)k * d, (double)l * d) / 8D +
			rand.nextDouble() * 4D + 4D) / 3D);
		int l7 = 0;
		if (rand.nextInt(10) == 0) {
			l7++;
		}
		if (biome == BiomeGenBase.forest) {
			l7 += k4 + 5;
		}
		if (biome == BiomeGenBase.rainforest) {
			l7 += k4 + 5;
		}
		if (biome == BiomeGenBase.seasonalForest) {
			l7 += k4 + 2;
		}
		if (biome == BiomeGenBase.taiga) {
			l7 += k4 + 5;
		}
		if (biome == BiomeGenBase.desert) {
			l7 -= 20;
		}
		if (biome == BiomeGenBase.tundra) {
			l7 -= 20;
		}
		if (biome == BiomeGenBase.plains) {
			l7 -= 20;
		}
		for (int i11 = 0; i11 < l7; i11++) {
			int l13 = k + rand.nextInt(16) + 8;
			int j14 = l + rand.nextInt(16) + 8;
			WorldGenerator worldgenerator = biome.a(rand);
			worldgenerator.a(1.0D, 1.0D, 1.0D);
			worldgenerator.a(world, rand, l13, world.getHighestBlockYAt(l13, j14), j14);
		}

		byte byte0 = 0;
		if (biome == BiomeGenBase.forest) {
			byte0 = 2;
		}
		if (biome == BiomeGenBase.seasonalForest) {
			byte0 = 4;
		}
		if (biome == BiomeGenBase.taiga) {
			byte0 = 2;
		}
		if (biome == BiomeGenBase.plains) {
			byte0 = 3;
		}
		for (int i14 = 0; i14 < byte0; i14++) {
			int k14 = k + rand.nextInt(16) + 8;
			int l16 = rand.nextInt(128);
			int k19 = l + rand.nextInt(16) + 8;
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
			int l19 = k + rand.nextInt(16) + 8;
			int k22 = rand.nextInt(128);
			int j24 = l + rand.nextInt(16) + 8;
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
			int i17 = k + rand.nextInt(16) + 8;
			int i20 = rand.nextInt(128);
			int l22 = l + rand.nextInt(16) + 8;
			deadBushGen.a(world, rand, i17, i20, l22);
		}

		if (rand.nextInt(2) == 0) {
			int j15 = k + rand.nextInt(16) + 8;
			int j17 = rand.nextInt(128);
			int j20 = l + rand.nextInt(16) + 8;
			redFlowerGen.a(world, rand, j15, j17, j20);
		}
		if (rand.nextInt(4) == 0) {
			int k15 = k + rand.nextInt(16) + 8;
			int k17 = rand.nextInt(128);
			int k20 = l + rand.nextInt(16) + 8;
			brownMushroomGen.a(world, rand, k15, k17, k20);
		}
		if (rand.nextInt(8) == 0) {
			int l15 = k + rand.nextInt(16) + 8;
			int l17 = rand.nextInt(128);
			int l20 = l + rand.nextInt(16) + 8;
			redMushroomGen.a(world, rand, l15, l17, l20);
		}
		for (int i16 = 0; i16 < 10; i16++) {
			int i18 = k + rand.nextInt(16) + 8;
			int i21 = rand.nextInt(128);
			int i23 = l + rand.nextInt(16) + 8;
			reedGen.a(world, rand, i18, i21, i23);
		}

		if (rand.nextInt(32) == 0) {
			int j16 = k + rand.nextInt(16) + 8;
			int j18 = rand.nextInt(128);
			int j21 = l + rand.nextInt(16) + 8;
			pumpkinGen.a(world, rand, j16, j18, j21);
		}
		int k16 = 0;
		if (biome == BiomeGenBase.desert) {
			k16 += 10;
		}
		for (int k18 = 0; k18 < k16; k18++) {
			int k21 = k + rand.nextInt(16) + 8;
			int j23 = rand.nextInt(128);
			int k24 = l + rand.nextInt(16) + 8;
			cactusGen.a(world, rand, k21, j23, k24);
		}

		for (int l18 = 0; l18 < 50; l18++) {
			int l21 = k + rand.nextInt(16) + 8;
			int k23 = rand.nextInt(rand.nextInt(120) + 8);
			int l24 = l + rand.nextInt(16) + 8;
			liquidWaterGen.a(world, rand, l21, k23, l24);
		}

		for (int i19 = 0; i19 < 20; i19++) {
			int i22 = k + rand.nextInt(16) + 8;
			int l23 = rand.nextInt(rand.nextInt(rand.nextInt(112) + 8) + 8);
			int i25 = l + rand.nextInt(16) + 8;
			liquidLavaGen.a(world, rand, i22, l23, i25);
		}

		temperatures = this.wcm.getTemperatures(temperatures, k + 8, l + 8, 16, 16);
		for (int j19 = k + 8; j19 < k + 8 + 16; j19++) {
			for (int j22 = l + 8; j22 < l + 8 + 16; j22++) {
				int i24 = j19 - (k + 8);
				int j25 = j22 - (l + 8);
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
				int k7 = k + rand.nextInt(16);
				int l10 = rand.nextInt(16) + rand.nextInt(16);
				int k13 = l + rand.nextInt(16);
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
			if (byId[id]!=null && (material = byId[id].material) != null) {
				return material.isSolid();
			} else {
				return false;
			}

		} else {
			return false;
		}

	}
	@Override
	public String toString(){
		return new StringBuilder(plugin.getDescription().getName()).append(" ").append(plugin.getDescription().getVersion()).toString();
	}
}

package com.github.barteks2x.b173gen.generator;

import com.github.barteks2x.b173gen.Generator;
import com.github.barteks2x.b173gen.WorldGenBaseOld;
import com.github.barteks2x.b173gen.biome.BetaBiome;
import com.github.barteks2x.b173gen.biome.BiomeOld;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.oldgen.WorldChunkManagerOld;
import com.github.barteks2x.b173gen.oldgen.WorldGenCavesOld;
import com.github.barteks2x.b173gen.oldnoisegen.NoiseGeneratorOctaves3D;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    private double noise3[];
    private double noise1[];
    private double noise2[];
    private double noise6[];
    private double noise7[];

    public WorldChunkManagerOld wcm;
    private final List<org.bukkit.generator.BlockPopulator> populatorList;
    private World world;
    private final WorldConfig config;
    private BlockPopulator populator;
    private final Generator plugin;


    public ChunkProviderGenerate(WorldConfig config, Generator plugin) {
        this.plugin = plugin;
        this.config = config;
        this.populatorList = new ArrayList<org.bukkit.generator.BlockPopulator>();
    }

    @Override
    public List<org.bukkit.generator.BlockPopulator> getDefaultPopulators(org.bukkit.World w) {
        plugin.initWorld(w);
        return Collections.unmodifiableList(populatorList);
    }

    @SuppressWarnings("unchecked")
    public void init(World world, WorldChunkManagerOld wcm) {
        this.world = world;
        this.wcm = wcm;

        rand = new Random(world.getSeed());
        noiseGen1 = new NoiseGeneratorOctaves3D(rand, 16, this.config.nofarlands);
        noiseGen2 = new NoiseGeneratorOctaves3D(rand, 16, this.config.nofarlands);
        noiseGen3 = new NoiseGeneratorOctaves3D(rand, 8, this.config.nofarlands);
        noiseGen4 = new NoiseGeneratorOctaves3D(rand, 4, this.config.nofarlands);
        noiseGen5 = new NoiseGeneratorOctaves3D(rand, 4, this.config.nofarlands);
        noiseGen6 = new NoiseGeneratorOctaves3D(rand, 10, this.config.nofarlands);
        noiseGen7 = new NoiseGeneratorOctaves3D(rand, 16, this.config.nofarlands);
        treeNoise = new NoiseGeneratorOctaves3D(rand, 8, this.config.nofarlands);

        caves = new WorldGenCavesOld();

        this.populator = new BlockPopulator(world, wcm, config);
        this.populatorList.add(this.populator);

        populator.setTreeNoise(treeNoise);
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
        initRand(chunkX, chunkZ);

        ChunkData terrain = this.createChunkData(world);
        BetaBiome[] biomes = wcm.getBiomeBlock(null, chunkX * 16, chunkZ * 16, 16, 16);

        generateTerrain(chunkX, chunkZ, terrain);
        replaceBlocksForBiome(chunkX, chunkZ, terrain, biomes);
        generateCaves(chunkX, chunkZ, terrain);

        int n = 0;
        for(int localX = 0; localX < 16; ++localX) {
            for(int localZ = 0; localZ < 16; ++localZ) {
                Biome biome = biomes[n].getBiome(this.config);
                biomeGrid.setBiome(localX, localZ, biome);
                ++n;
            }
        }
        return terrain;
    }

    public void initRand(int chunkX, int chunkZ) {
        this.rand.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
    }

    public void generateTerrain(int x, int z, ChunkData terrain) {
        double temperatures[] = this.wcm.temperatures;
        byte byte0 = 4;
        byte oceanHeight = 64;
        int k = byte0 + 1;
        byte b2 = 17;
        int l = byte0 + 1;
        noise = initNoiseField(noise, x * byte0, 0, z * byte0, k, b2, l);
        for(int xPiece = 0; xPiece < byte0; xPiece++) {
            for(int zPiece = 0; zPiece < byte0; zPiece++) {
                for(int yPiece = 0; yPiece < 16; yPiece++) {
                    double d = 0.125D;
                    double d1 = noise[((xPiece + 0) * l + (zPiece + 0)) * b2 + (yPiece + 0)];
                    double d2 = noise[((xPiece + 0) * l + (zPiece + 1)) * b2 + (yPiece + 0)];
                    double d3 = noise[((xPiece + 1) * l + (zPiece + 0)) * b2 + (yPiece + 0)];
                    double d4 = noise[((xPiece + 1) * l + (zPiece + 1)) * b2 + (yPiece + 0)];
                    double d5 = (noise[((xPiece + 0) * l + (zPiece + 0)) * b2 + (yPiece + 1)] - d1) * d;
                    double d6 = (noise[((xPiece + 0) * l + (zPiece + 1)) * b2 + (yPiece + 1)] - d2) * d;
                    double d7 = (noise[((xPiece + 1) * l + (zPiece + 0)) * b2 + (yPiece + 1)] - d3) * d;
                    double d8 = (noise[((xPiece + 1) * l + (zPiece + 1)) * b2 + (yPiece + 1)] - d4) * d;
                    for(int l1 = 0; l1 < 8; l1++) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for(int i2 = 0; i2 < 4; i2++) {
                            int xLoc = i2 + xPiece * 4;
                            int yLoc = yPiece * 8 + l1;
                            int zLoc = 0 + zPiece * 4;
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for(int k2 = 0; k2 < 4; k2++) {
                                double d17 = temperatures[(xPiece * 4 + i2) * 16 + (zPiece * 4 + k2)];
                                Material block = AIR;
                                if(yPiece * 8 + l1 < oceanHeight) {
                                    if(d17 < 0.5D && yPiece * 8 + l1 >= oceanHeight - 1) {
                                        block = ICE;
                                    } else {
                                        block = STATIONARY_WATER;
                                    }
                                }
                                if(d15 > 0.0D) {
                                    block = STONE;
                                }
                                terrain.setBlock(xLoc, yLoc, zLoc, block);
                                zLoc++;
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

    public void replaceBlocksForBiome(int xPos, int zPos, ChunkData terrain, BetaBiome biomes[]) {
        //NOTE: in get/set block X and Z coordinates are swapped, THIS IS CORRECT
        byte oceanHeight = 64;
        double d = 0.03125D;
        sandNoise = noiseGen4.generateNoiseArray(sandNoise, xPos * 16, zPos * 16, 0.0D, 16, 16, 1, d, d, 1.0D);
        gravelNoise = noiseGen4.generateNoiseArray(gravelNoise, xPos * 16, 109.0134D, zPos * 16, 16, 1, 16, d, 1.0D, d);
        stoneNoise = noiseGen5.generateNoiseArray(stoneNoise, xPos * 16, zPos * 16, 0.0D, 16, 16, 1, d * 2D, d * 2D, d * 2D);
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                BetaBiome biome = biomes[x + z * 16];
                boolean sand = sandNoise[x + z * 16] + rand.nextDouble() * 0.2D > 0.0D;
                boolean gravel = gravelNoise[x + z * 16] + rand.nextDouble() * 0.2D > 3D;
                int depth = (int)(stoneNoise[x + z * 16] / 3D + 3D + rand.nextDouble() * 0.25D);
                int prevDepth = -1;
                Material topBlock = BiomeOld.top(biome);
                Material fillerBlock = BiomeOld.filler(biome);
                for(int y = 127; y >= 0; y--) {
                    if(y <= 0 + rand.nextInt(5)) {
                        terrain.setBlock(z, y, x, BEDROCK);
                        continue;
                    }
                    Material block = terrain.getType(z, y, x);
                    if(block == AIR) {
                        prevDepth = -1;
                        continue;
                    }
                    if(block != STONE) {
                        continue;
                    }
                    if(prevDepth == -1) {
                        if(depth <= 0) {
                            topBlock = AIR;
                            fillerBlock = STONE;
                        } else if(y >= oceanHeight - 4 && y <= oceanHeight + 1) {
                            topBlock = BiomeOld.top(biome);
                            fillerBlock = BiomeOld.filler(biome);
                            if(gravel) {
                                topBlock = AIR;
                                fillerBlock = GRAVEL;
                            }
                            if(sand) {
                                topBlock = SAND;
                                fillerBlock = SAND;
                            }
                        }
                        if(y < oceanHeight && topBlock == AIR) {
                            topBlock = STATIONARY_WATER;
                        }
                        prevDepth = depth;
                        if(y >= oceanHeight - 1) {
                            terrain.setBlock(z, y, x, topBlock);
                        } else {
                            terrain.setBlock(z, y, x, fillerBlock);
                        }
                        continue;
                    }
                    if(prevDepth <= 0) {
                        continue;
                    }
                    prevDepth--;
                    terrain.setBlock(z, y, x, fillerBlock);
                    if(prevDepth == 0 && fillerBlock == SAND) {
                        prevDepth = rand.nextInt(4);
                        fillerBlock = SANDSTONE;
                    }
                }

            }

        }
    }

    public void generateCaves(int x, int z, ChunkData data) {
        caves.generate(world, x, z, data);
    }
    @Override
    public boolean canSpawn(World w, int x, int z) {
        this.plugin.initWorld(w);
        int y;

        for(y = 63; !w.getBlockAt(x, y + 1, z).isEmpty(); ++y) {
        }

        return w.getBlockAt(x, y, z).getType() == Material.SAND;

    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        int x = 0;
        int z = 0;

        while (!canSpawn(world, x, z)) {
            x += random.nextInt(64) - random.nextInt(64);
            z += random.nextInt(64) - random.nextInt(64);
        }
        return new Location(world, x, world.getHighestBlockYAt(x, z), z);
    }

    @Override
    public String toString() {
        return new StringBuilder(plugin.getDescription().getName()).append(" ").append(
                plugin.getDescription().getVersion()).append(" world: ").append(world.getName()).
                toString();
    }

    private double[] initNoiseField(double array[], int xPos, int yPos, int zPos, int xSize, int ySize, int zSize) {
        if(array == null) {
            array = new double[xSize * ySize * zSize];
        }
        double d0 = 684.412D;
        double d1 = 684.412D;
        double temp[] = this.wcm.temperatures;
        double rain[] = this.wcm.rain;
        noise6 = noiseGen6.generateNoiseArray(noise6, xPos, zPos, xSize, zSize, 1.121D, 1.121D, 0.5D);
        noise7 = noiseGen7.generateNoiseArray(noise7, xPos, zPos, xSize, zSize, 200D, 200D, 0.5D);
        noise3 = noiseGen3.generateNoiseArray(noise3, xPos, yPos, zPos, xSize, ySize, zSize,
                d0 / 80D, d1 / 160D, d0 / 80D);
        noise1 = noiseGen1.generateNoiseArray(noise1, xPos, yPos, zPos, xSize, ySize, zSize,
                d0, d1, d0);
        noise2 = noiseGen2.generateNoiseArray(noise2, xPos, yPos, zPos, xSize, ySize, zSize,
                d0, d1, d0);
        int k1 = 0;
        int l1 = 0;
        int i2 = 16 / xSize;
        for(int x = 0; x < xSize; x++) {
            int k2 = x * i2 + i2 / 2;
            for(int z = 0; z < zSize; z++) {
                int i3 = z * i2 + i2 / 2;
                double d2 = temp[k2 * 16 + i3];
                double d3 = rain[k2 * 16 + i3] * d2;
                double d4 = 1.0D - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1.0D - d4;
                double d5 = (noise6[l1] + 256D) / 512D;
                d5 *= d4;
                if(d5 > 1.0D) {
                    d5 = 1.0D;
                }
                double d6 = noise7[l1] / 8000D;
                if(d6 < 0.0D) {
                    d6 = -d6 * 0.3D;
                }
                d6 = d6 * 3D - 2D;
                if(d6 < 0.0D) {
                    d6 /= 2D;
                    if(d6 < -1D) {
                        d6 = -1D;
                    }
                    d6 /= 1.4D;
                    d6 /= 2D;
                    d5 = 0.0D;
                } else {
                    if(d6 > 1.0D) {
                        d6 = 1.0D;
                    }
                    d6 /= 8D;
                }
                if(d5 < 0.0D) {
                    d5 = 0.0D;
                }
                d5 += 0.5D;
                d6 = (d6 * (double)ySize) / 16D;
                double d7 = (double)ySize / 2D + d6 * 4D;
                l1++;
                for(int y = 0; y < ySize; y++) {
                    double d8 = 0.0D;
                    double d9 = (((double)y - d7) * 12D)
                            / d5;
                    if(d9 < 0.0D) {
                        d9 *= 4D;
                    }
                    double d10 = noise1[k1] / 512D;
                    double d11 = noise2[k1] / 512D;
                    double d12 = (this.noise3[k1] / 10D + 1.0D) / 2D;
                    if(d12 < 0.0D) {
                        d8 = d10;
                    } else if(d12 > 1.0D) {
                        d8 = d11;
                    } else {
                        d8 = d10 + (d11 - d10) * d12;
                    }
                    d8 -= d9;
                    if(y > ySize - 4) {
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

    public WorldConfig getConfig() {
        return this.config;
    }
}

package com.github.barteks2x.b173gen.generator;

import com.github.barteks2x.b173gen.biome.BetaBiome;
import com.github.barteks2x.b173gen.biome.BiomeOld;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.oldgen.*;
import com.github.barteks2x.b173gen.oldnoisegen.NoiseGeneratorOctaves3D;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Random;

import static com.github.barteks2x.b173gen.biome.BetaBiome.RAINFOREST;
import static org.bukkit.Material.*;

public class BlockPopulator extends org.bukkit.generator.BlockPopulator {

    private static final Material EMERALD_ORE = Material.getMaterial("EMERALD_ORE");

    private final World world;
    private final WorldChunkManagerOld wcm;
    private final Random rand;
    private WorldConfig config;

    private final WorldGenerator173 dungeonGen,
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

    private double[] temperatures;

    private NoiseGeneratorOctaves3D treeNoise;

    public BlockPopulator(World world, WorldChunkManagerOld wcm, WorldConfig config) {

        this.world = world;
        this.wcm = wcm;
        rand = new Random(world.getSeed());
        this.config = config;

        clayGen = new WorldGenClayOld(32);
        waterLakeGen = new WorldGenLakesOld(STATIONARY_WATER);
        lavaLakeGen = new WorldGenLakesOld(STATIONARY_LAVA);

        dungeonGen = new WorldGenDungeonOld();
        dirtGen = new WorldGenMinableOld(DIRT, 32);
        gravelGen = new WorldGenMinableOld(GRAVEL, 32);
        coalGen = new WorldGenMinableOld(COAL_ORE, 16);
        ironGen = new WorldGenMinableOld(IRON_ORE, 8);
        goldGen = new WorldGenMinableOld(GOLD_ORE, 8);
        redstoneGen = new WorldGenMinableOld(REDSTONE_ORE, 7);
        diamondGen = new WorldGenMinableOld(DIAMOND_ORE, 7);
        lapisGen = new WorldGenMinableOld(LAPIS_ORE, 6);
        yellowFlowerGen = new WorldGenFlowersOld(YELLOW_FLOWER);
        longGrassGenNormal = new WorldGenGrassOld(LONG_GRASS, (byte) 1);
        longGrassGenRainforest = new WorldGenGrassOld(LONG_GRASS, (byte) 2);
        deadBushGen = new WorldGenDeadBushOld(DEAD_BUSH);
        redFlowerGen = new WorldGenFlowersOld(RED_ROSE);
        brownMushroomGen = new WorldGenFlowersOld(BROWN_MUSHROOM);
        redMushroomGen = new WorldGenFlowersOld(RED_MUSHROOM);
        reedGen = new WorldGenReedOld();
        pumpkinGen = new WorldGenPumpkinOld();
        cactusGen = new WorldGenCactusOld();
        liquidWaterGen = new WorldGenLiquidsOld(WATER);
        liquidLavaGen = new WorldGenLiquidsOld(LAVA);
        emeraldGen = new WorldGenMinableOld(EMERALD_ORE, 2);
    }

    public void setTreeNoise(NoiseGeneratorOctaves3D treeNoise) {
        this.treeNoise = treeNoise;
    }

    @Override
    public void populate(World world, Random rnd, Chunk chunk) {
        this.populate(chunk.getX(), chunk.getZ());
    }

    public void populate(int chunkX, int chunkZ) {
        int x = chunkX * 16;
        int z = chunkZ * 16;
        BetaBiome biome = this.wcm.getBiome(x + 16, z + 16);

        initSeed(chunkX, chunkZ);

        generateRare(x, z, this.waterLakeGen, 4, 128);
        generateLavaLakes(x, z);
        generate(x, z, this.dungeonGen, 8, 128);

        generate(x, z, 10, this.clayGen, 128);
        generate(x, z, 20, this.dirtGen, 128);
        generate(x, z, 10, this.gravelGen, 128);
        generate(x, z, 20, this.coalGen, 128);
        generate(x, z, 20, this.ironGen, 64);
        generate(x, z, 2, this.goldGen, 32);
        generate(x, z, 8, this.redstoneGen, 16);
        generate(x, z, 1, this.diamondGen, 16);

        generateCentered(x, z, 1, this.lapisGen, 16);

        generateTrees(x, z, biome, biome.getTreesForBiome(this.rand, (int) ((this.treeNoise.generateNoise(x * 0.5D, z * 0.5D) / 8D + this.rand.nextDouble() * 4D + 4D) / 3D)));
        generate(x, z, biome.getFlowersForBiome(), this.yellowFlowerGen, 128);
        generateTallGrass(x, z, biome, biome.getTallGrassForBiome());
        generate(x, z, biome.getDeadBushForBiome(), this.deadBushGen, 128);
        generateRare(x, z, this.redFlowerGen, 2, 128);
        generateRare(x, z, this.brownMushroomGen, 4, 128);
        generateRare(x, z, this.redMushroomGen, 8, 128);
        generate(x, z, this.reedGen, 10, 128);
        generateRare(x, z, this.pumpkinGen, 32, 128);
        generate(x, z, biome.getCactusForBiome(), this.cactusGen, 128);

        generateLiquidWater(x, z);
        generateLiquidLava(x, z);

        generateSnow(x, z);
        if (this.emeraldGen != null) {
            generateCentered(x, z, 5, this.emeraldGen, 16);
        }
    }

    private void initSeed(int chunkX, int chunkZ) {
        this.rand.setSeed(this.world.getSeed());
        long rand1 = (this.rand.nextLong() / 2L) * 2L + 1L;
        long rand2 = (this.rand.nextLong() / 2L) * 2L + 1L;
        this.rand.setSeed((((long) chunkX * rand1) + ((long) chunkZ * rand2)) ^ this.world.getSeed());
    }

    private void generateSnow(int chunkStartX, int chunkStartZ) {
        this.temperatures = this.wcm.getTemperatures(this.temperatures, chunkStartX + 8, chunkStartZ + 8, 16, 16);
        for (int blockX = chunkStartX + 8; blockX < chunkStartX + 8 + 16; blockX++) {
            for (int blockZ = chunkStartZ + 8; blockZ < chunkStartZ + 8 + 16; blockZ++) {
                int localX = blockX - (chunkStartX + 8);
                int localZ = blockZ - (chunkStartZ + 8);
                int blockY = this.getHighestSolidOrLiquidBlock(blockX, blockZ);
                double temp = this.temperatures[(localX << 4) | localZ] - (blockY - 64) / 64D * 0.3D;
                Material blockBelow = this.world.getBlockAt(blockX, blockY - 1, blockZ).getType();
                if ((temp < 0.5D) &&
                        blockY > 0 && blockY < 128 &&
                        this.world.getBlockAt(blockX, blockY, blockZ).isEmpty() &&
                        blockBelow.isSolid() && blockBelow != ICE) {
                    this.world.getBlockAt(blockX, blockY, blockZ).setType(SNOW);
                }
            }

        }
    }

    private void generateLiquidLava(int x, int z) {
        for (int i19 = 0; i19 < 20; i19++) {
            int i22 = x + this.rand.nextInt(16) + 8;
            int l23 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
            int i25 = z + this.rand.nextInt(16) + 8;
            this.liquidLavaGen.generate(this.world, this.rand, i22, l23, i25);
        }
    }

    private void generateLiquidWater(int x, int z) {
        for (int l18 = 0; l18 < 50; l18++) {
            int l21 = x + this.rand.nextInt(16) + 8;
            int k23 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            int l24 = z + this.rand.nextInt(16) + 8;
            this.liquidWaterGen.generate(this.world, this.rand, l21, k23, l24);
        }
    }

    private void generate(int x, int z, WorldGenerator173 generator, int attempts, int maxHeight) {
        for (int i16 = 0; i16 < attempts; i16++) {
            int i18 = x + this.rand.nextInt(16) + 8;
            int i21 = this.rand.nextInt(maxHeight);
            int i23 = z + this.rand.nextInt(16) + 8;
            generator.generate(this.world, this.rand, i18, i21, i23);
        }
    }

    private void generateRare(int x, int z, WorldGenerator173 generator, int rarity, int maxHeight) {
        if (this.rand.nextInt(rarity) == 0) {
            int l15 = x + this.rand.nextInt(16) + 8;
            int l17 = this.rand.nextInt(maxHeight);
            int l20 = z + this.rand.nextInt(16) + 8;
            generator.generate(this.world, this.rand, l15, l17, l20);
        }
    }

    private void generateTallGrass(int x, int z, BetaBiome biome, int atempts) {
        for (int l14 = 0; l14 < atempts; l14++) {
            boolean flag = (biome.equals(RAINFOREST) && this.rand.nextInt(3) != 0);
            int l19 = x + this.rand.nextInt(16) + 8;
            int k22 = this.rand.nextInt(128);
            int j24 = z + this.rand.nextInt(16) + 8;
            if (flag) {
                this.longGrassGenRainforest.generate(this.world, this.rand, l19, k22, j24);
            } else {
                this.longGrassGenNormal.generate(this.world, this.rand, l19, k22, j24);
            }
        }
    }

    private void generateTrees(int x, int z, BetaBiome biome, int trees) {
        for (int i11 = 0; i11 < trees; i11++) {
            int l13 = x + this.rand.nextInt(16) + 8;
            int j14 = z + this.rand.nextInt(16) + 8;
            WorldGenerator173 worldgenerator = BiomeOld.getRandomTreeGen(this.rand, biome);
            worldgenerator.scale(1.0D, 1.0D, 1.0D);
            worldgenerator.generate(this.world, this.rand, l13, this.world.getHighestBlockYAt(l13, j14), j14);
        }
    }

    private void generateCentered(int x, int z, int attempts, WorldGenerator173 generator, int centerHeight) {
        for (int j = 0; j < attempts; j++) {
            int X = x + this.rand.nextInt(16);
            int Y = this.rand.nextInt(centerHeight) + this.rand.nextInt(centerHeight);
            int Z = z + this.rand.nextInt(16);
            generator.generate(this.world, this.rand, X, Y, Z);
        }
    }

    private void generate(int x, int z, int attempts, WorldGenerator173 generator, int maxHeight) {
        for (int j = 0; j < attempts; j++) {
            int X = x + this.rand.nextInt(16);
            int Y = this.rand.nextInt(maxHeight);
            int Z = z + this.rand.nextInt(16);
            generator.generate(this.world, this.rand, X, Y, Z);
        }
    }

    private void generateLavaLakes(int x, int z) {
        if (this.rand.nextInt(8) == 0) {
            int X = x + this.rand.nextInt(16) + 8;
            int Y = this.rand.nextInt(this.rand.nextInt(120) + 8);
            int Z = z + this.rand.nextInt(16) + 8;
            if (Y < 64 || this.rand.nextInt(10) == 0) {
                this.lavaLakeGen.generate(this.world, this.rand, X, Y, Z);
            }
        }
    }

    public int getHighestSolidOrLiquidBlock(int i, int j) {
        Chunk chunk = world.getChunkAt(i >> 4, j >> 4);
        int k = 127;

        i &= 15;

        for (j &= 15; k > 0; --k) {
            Material material = chunk.getBlock(i, k, j).getType();

            if (material != Material.AIR || MinecraftMethods.isLiquid(material)) {
                return k + 1;
            }

        }

        return -1;
    }
}

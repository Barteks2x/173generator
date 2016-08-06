package com.github.barteks2x.b173gen.generator;

import java.util.Random;

import com.github.barteks2x.b173gen.biome.BetaBiome;
import com.github.barteks2x.b173gen.biome.BiomeOld;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.oldgen.*;
import com.github.barteks2x.b173gen.oldnoisegen.NoiseGeneratorOctaves3D;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import static com.github.barteks2x.b173gen.biome.BetaBiome.*;
import static org.bukkit.Material.*;
import static org.bukkit.Material.LAVA;

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
        waterLakeGen = new WorldGenLakesOld(WATER);
        lavaLakeGen = new WorldGenLakesOld(LAVA);

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
        longGrassGenNormal = new WorldGenGrassOld(LONG_GRASS, (byte)1);
        longGrassGenRainforest = new WorldGenGrassOld(LONG_GRASS, (byte)2);
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
        this.rand.setSeed(this.world.getSeed());
        long rand1 = this.rand.nextLong() / 2L * 2L + 1L;
        long rand2 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(chunkX * rand1 + chunkZ * rand2 ^ this.world.getSeed());

        if(this.rand.nextInt(4) == 0) {
            int X = x + this.rand.nextInt(16) + 8;
            int Y = this.rand.nextInt(128);
            int Z = z + this.rand.nextInt(16) + 8;
            this.waterLakeGen.generate(this.world, this.rand, X, Y, Z);
        }
        if(this.rand.nextInt(8) == 0) {
            int X = x + this.rand.nextInt(16) + 8;
            int Y = this.rand.nextInt(this.rand.nextInt(120) + 8);
            int Z = z + this.rand.nextInt(16) + 8;
            if(Y < 64 || this.rand.nextInt(10) == 0) {
                this.lavaLakeGen.generate(this.world, this.rand, X, Y, Z);
            }
        }
        for(int j = 0; j < 8; j++) {
            int X = x + this.rand.nextInt(16) + 8;
            int Y = this.rand.nextInt(128);
            int Z = z + this.rand.nextInt(16) + 8;
            this.dungeonGen.generate(this.world, this.rand, X, Y, Z);
        }

        for(int j = 0; j < 10; j++) {
            int X = x + this.rand.nextInt(16);
            int Y = this.rand.nextInt(128);
            int Z = z + this.rand.nextInt(16);
            this.clayGen.generate(this.world, this.rand, X, Y, Z);
        }

        for(int j = 0; j < 20; j++) {
            int X = x + this.rand.nextInt(16);
            int Y = this.rand.nextInt(128);
            int Z = z + this.rand.nextInt(16);
            this.dirtGen.generate(this.world, this.rand, X, Y, Z);
        }

        for(int j = 0; j < 10; j++) {
            int X = x + this.rand.nextInt(16);
            int Y = this.rand.nextInt(128);
            int Z = z + this.rand.nextInt(16);
            this.gravelGen.generate(this.world, this.rand, X, Y, Z);
        }

        for(int j = 0; j < 20; j++) {
            int X = x + this.rand.nextInt(16);
            int Y = this.rand.nextInt(128);
            int Z = z + this.rand.nextInt(16);
            this.coalGen.generate(this.world, this.rand, X, Y, Z);
        }

        for(int j = 0; j < 20; j++) {
            int X = x + this.rand.nextInt(16);
            int Y = this.rand.nextInt(64);
            int Z = z + this.rand.nextInt(16);
            this.ironGen.generate(this.world, this.rand, X, Y, Z);
        }

        for(int j = 0; j < 2; j++) {
            int X = x + this.rand.nextInt(16);
            int Y = this.rand.nextInt(32);
            int Z = z + this.rand.nextInt(16);
            this.goldGen.generate(this.world, this.rand, X, Y, Z);
        }

        for(int j = 0; j < 8; j++) {
            int X = x + this.rand.nextInt(16);
            int Y = this.rand.nextInt(16);
            int Z = z + this.rand.nextInt(16);
            this.redstoneGen.generate(this.world, this.rand, X, Y, Z);
        }

        for(int j = 0; j < 1; j++) {
            int X = x + this.rand.nextInt(16);
            int Y = this.rand.nextInt(16);
            int Z = z + this.rand.nextInt(16);
            this.diamondGen.generate(this.world, this.rand, X, Y, Z);
        }

        for(int j = 0; j < 1; j++) {
            int X = x + this.rand.nextInt(16);
            int Y = this.rand.nextInt(16) + this.rand.nextInt(16);
            int Z = z + this.rand.nextInt(16);
            this.lapisGen.generate(this.world, this.rand, X, Y, Z);
        }

        int treesRand = (int)((this.treeNoise.generateNoise(x * 0.5D, z * 0.5D) / 8D
                + this.rand.nextDouble() * 4D + 4D) / 3D);
        int trees = 0;
        if(this.rand.nextInt(10) == 0) {
            trees++;
        }
        if(biome.equals(FOREST)) {
            trees += treesRand + 5;
        }

        if(biome.equals(RAINFOREST)) {
            trees += treesRand + 5;
        }

        if(biome.equals(SEASONAL_FOREST)) {
            trees += treesRand + 2;
        }

        if(biome.equals(TAIGA)) {
            trees += treesRand + 5;
        }

        if(biome.equals(DESERT)) {
            trees -= 20;
        }

        if(biome.equals(TUNDRA)) {
            trees -= 20;
        }

        if(biome.equals(PLAINS)) {
            trees -= 20;
        }
        for(int i11 = 0; i11 < trees; i11++) {
            int l13 = x + this.rand.nextInt(16) + 8;
            int j14 = z + this.rand.nextInt(16) + 8;
            WorldGenerator173 worldgenerator = BiomeOld.getRandomTreeGen(this.rand, biome);
            worldgenerator.scale(1.0D, 1.0D, 1.0D);
            worldgenerator.generate(this.world, this.rand, l13, this.world.getHighestBlockYAt(l13, j14), j14);
        }

        byte flowers = 0;
        if(biome.equals(FOREST)) {
            flowers = 2;
        }

        if(biome.equals(SEASONAL_FOREST)) {
            flowers = 4;
        }

        if(biome.equals(TAIGA)) {
            flowers = 2;
        }

        if(biome.equals(PLAINS)) {
            flowers = 3;
        }
        for(int i14 = 0; i14 < flowers; i14++) {
            int k14 = x + this.rand.nextInt(16) + 8;
            int l16 = this.rand.nextInt(128);
            int k19 = z + this.rand.nextInt(16) + 8;
            this.yellowFlowerGen.generate(this.world, this.rand, k14, l16, k19);
        }

        byte byte1 = 0;
        if(biome.equals(FOREST)) {
            byte1 = 2;
        }

        if(biome.equals(RAINFOREST)) {
            byte1 = 10;
        }

        if(biome.equals(SEASONAL_FOREST)) {
            byte1 = 2;
        }

        if(biome.equals(TAIGA)) {
            byte1 = 1;
        }

        if(biome.equals(PLAINS)) {
            byte1 = 10;
        }
        for(int l14 = 0; l14 < byte1; l14++) {
            boolean flag = (biome.equals(RAINFOREST) && this.rand.nextInt(3) != 0);
            int l19 = x + this.rand.nextInt(16) + 8;
            int k22 = this.rand.nextInt(128);
            int j24 = z + this.rand.nextInt(16) + 8;
            if(flag) {
                this.longGrassGenRainforest.generate(this.world, this.rand, l19, k22, j24);
            } else {
                this.longGrassGenNormal.generate(this.world, this.rand, l19, k22, j24);
            }
        }

        byte1 = 0;
        if(biome.equals(DESERT)) {
            byte1 = 2;
        }
        for(int i15 = 0; i15 < byte1; i15++) {
            int i17 = x + this.rand.nextInt(16) + 8;
            int i20 = this.rand.nextInt(128);
            int l22 = z + this.rand.nextInt(16) + 8;
            this.deadBushGen.generate(this.world, this.rand, i17, i20, l22);
        }

        if(this.rand.nextInt(2) == 0) {
            int j15 = x + this.rand.nextInt(16) + 8;
            int j17 = this.rand.nextInt(128);
            int j20 = z + this.rand.nextInt(16) + 8;
            this.redFlowerGen.generate(this.world, this.rand, j15, j17, j20);
        }
        if(this.rand.nextInt(4) == 0) {
            int k15 = x + this.rand.nextInt(16) + 8;
            int k17 = this.rand.nextInt(128);
            int k20 = z + this.rand.nextInt(16) + 8;
            this.brownMushroomGen.generate(this.world, this.rand, k15, k17, k20);
        }
        if(this.rand.nextInt(8) == 0) {
            int l15 = x + this.rand.nextInt(16) + 8;
            int l17 = this.rand.nextInt(128);
            int l20 = z + this.rand.nextInt(16) + 8;
            this.redMushroomGen.generate(this.world, this.rand, l15, l17, l20);
        }
        for(int i16 = 0; i16 < 10; i16++) {
            int i18 = x + this.rand.nextInt(16) + 8;
            int i21 = this.rand.nextInt(128);
            int i23 = z + this.rand.nextInt(16) + 8;
            this.reedGen.generate(this.world, this.rand, i18, i21, i23);
        }

        if(this.rand.nextInt(32) == 0) {
            int j16 = x + this.rand.nextInt(16) + 8;
            int j18 = this.rand.nextInt(128);
            int j21 = z + this.rand.nextInt(16) + 8;
            this.pumpkinGen.generate(this.world, this.rand, j16, j18, j21);
        }
        int k16 = 0;
        if(biome.equals(DESERT)) {
            k16 += 10;
        }
        for(int k18 = 0; k18 < k16; k18++) {
            int k21 = x + this.rand.nextInt(16) + 8;
            int j23 = this.rand.nextInt(128);
            int k24 = z + this.rand.nextInt(16) + 8;
            this.cactusGen.generate(this.world, this.rand, k21, j23, k24);
        }

        for(int l18 = 0; l18 < 50; l18++) {
            int l21 = x + this.rand.nextInt(16) + 8;
            int k23 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            int l24 = z + this.rand.nextInt(16) + 8;
            this.liquidWaterGen.generate(this.world, this.rand, l21, k23, l24);
        }

        for(int i19 = 0; i19 < 20; i19++) {
            int i22 = x + this.rand.nextInt(16) + 8;
            int l23 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
            int i25 = z + this.rand.nextInt(16) + 8;
            this.liquidLavaGen.generate(this.world, this.rand, i22, l23, i25);
        }

        this.temperatures = this.wcm.getTemperatures(this.temperatures, x + 8, z + 8, 16, 16);
        for(int j19 = x + 8; j19 < x + 8 + 16; j19++) {
            for(int j22 = z + 8; j22 < z + 8 + 16; j22++) {
                int i24 = j19 - (x + 8);
                int j25 = j22 - (z + 8);
                int y = this.getHighestSolidOrLiquidBlock(j19, j22);
                double temp = this.temperatures[(i24 << 4) | j25] - (y - 64) / 64D * 0.3D;
                Material m = this.world.getBlockAt(j19, y - 1, j22).getType();
                if((temp < 0.5D) && y > 0 && y < 128 && this.world.getBlockAt(j19, y, j22).isEmpty()
                        && !this.world.getBlockAt(j19, y - 1, j22).isLiquid() && m != ICE) {
                    this.world.getBlockAt(j19, y, j22).setType(SNOW);
                }
            }

        }
        if(this.emeraldGen != null) {
            for(int j4 = 0; j4 < 5; j4++) {
                int k7 = x + this.rand.nextInt(16);
                int l10 = this.rand.nextInt(16) + this.rand.nextInt(16);
                int k13 = z + this.rand.nextInt(16);
                this.emeraldGen.generate(this.world, this.rand, k7, l10, k13);
            }
        }
    }

    public int getHighestSolidOrLiquidBlock(int i, int j) {
        Chunk chunk = world.getChunkAt(i >> 4, j >> 4);
        int k = 127;

        i &= 15;

        for(j &= 15; k > 0; --k) {
            Material material = chunk.getBlock(i, k, j).getType();

            if(material != Material.AIR || MinecraftMethods.isLiquid(material)) {
                return k + 1;
            }

        }

        return -1;
    }
}

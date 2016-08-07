package com.github.barteks2x.b173gen.generator;

import com.github.barteks2x.b173gen.Generator;
import com.github.barteks2x.b173gen.biome.BiomeOld;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.generator.populator.*;
import com.github.barteks2x.b173gen.oldgen.*;
import com.github.barteks2x.b173gen.oldnoisegen.NoiseGeneratorOctaves3D;
import com.github.barteks2x.b173gen.util.CenteredHeightDistribution;
import com.github.barteks2x.b173gen.util.DepthPackedHeightDistribution;
import com.github.barteks2x.b173gen.util.HeightDistrubution;
import com.github.barteks2x.b173gen.util.LinearHeightDistrubution;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.*;

import static org.bukkit.Material.*;

/**
 * Creates and destroys chunk populator states.
 * <p>
 * Currently chunk populator state contains Random number generator for that chunk. Bukkit already gives RNG to work with,
 * but it doesn't guarantee that the initial seed won't change in future versions.
 */
public class PopulatorManager {

    private static final Material EMERALD_ORE = Material.getMaterial("EMERALD_ORE");

    private final World world;
    private final WorldChunkManagerOld wcm;

    private NoiseGeneratorOctaves3D treeNoise;

    private List<BlockPopulator> populators = new ArrayList<>();

    public PopulatorManager(World world, WorldChunkManagerOld wcm, WorldConfig config) {

        this.world = world;
        this.wcm = wcm;

        //hack, init is the first populator
        add(new PopulatorStateInit(this));

        add("WaterLakes", new RareResource(new WorldGenLakesOld(STATIONARY_WATER), HeightDistrubution.DEFAULT, 4));
        add("LavaLakes", new RareUndergroundResource(new WorldGenLakesOld(STATIONARY_LAVA), new DepthPackedHeightDistribution(0, 8, 126), 4, 64, 10));
        add("Dungeons", new CommonResource(new WorldGenDungeonOld(), HeightDistrubution.DEFAULT, 8));

        add("Clay", new CommonResource(new WorldGenClayOld(32), HeightDistrubution.DEFAULT, 10));
        add("Dirt", new CommonResource(new WorldGenMinableOld(DIRT, 32), HeightDistrubution.DEFAULT, 20));
        add("Gravel", new CommonResource(new WorldGenMinableOld(GRAVEL, 32), HeightDistrubution.DEFAULT, 10));

        add("Coal", new CommonResource(new WorldGenMinableOld(COAL_ORE, 16), HeightDistrubution.DEFAULT, 20));
        add("Iron", new CommonResource(new WorldGenMinableOld(IRON_ORE, 8), new LinearHeightDistrubution(0, 63), 20));
        add("Gold", new CommonResource(new WorldGenMinableOld(GOLD_ORE, 8), new LinearHeightDistrubution(0, 31), 2));
        add("Redstone", new CommonResource(new WorldGenMinableOld(REDSTONE_ORE, 7), new LinearHeightDistrubution(0, 15), 8));
        add("Diamond", new CommonResource(new WorldGenMinableOld(DIAMOND_ORE, 7), new LinearHeightDistrubution(0, 15), 1));

        add("Lapis", new CommonResource(new WorldGenMinableOld(LAPIS_ORE, 6), new CenteredHeightDistribution(16, 16), 1));

        add("Trees", new CommonSurfaceResource(BiomeOld::getBiomeTreeGenerator, (rand, biome, x, z) -> biome.getTreesPerChunk(rand, treeNoise, x, z)));

        add("YellowFlowers", new CommonResource(new WorldGenFlowersOld(YELLOW_FLOWER), HeightDistrubution.DEFAULT, (rand, biome, x, z) -> biome.getFlowersPerChunk()));
        add("TallGrass", new CommonResource(BiomeOld::getRandomGrassGenerator, HeightDistrubution.DEFAULT, (rand, biome, x, z) -> biome.getGrassPerChunk()));
        add("DeadBush", new CommonResource(new WorldGenDeadBushOld(DEAD_BUSH), HeightDistrubution.DEFAULT, (rand, biome, x, z) -> biome.getDeadBushPerChunk()));

        add("RedFlowers", new RareResource(new WorldGenFlowersOld(RED_ROSE), HeightDistrubution.DEFAULT, 2));
        add("BrownMushrooms", new RareResource(new WorldGenFlowersOld(BROWN_MUSHROOM), HeightDistrubution.DEFAULT, 4));
        add("RedMushrooms", new RareResource(new WorldGenFlowersOld(RED_MUSHROOM), HeightDistrubution.DEFAULT, 8));
        add("Reed", new CommonResource(new WorldGenReedOld(), HeightDistrubution.DEFAULT, 10));
        add("Pumpkins", new RareResource(new WorldGenPumpkinOld(), HeightDistrubution.DEFAULT, 32));

        add("Cacti", new CommonResource(new WorldGenCactusOld(), HeightDistrubution.DEFAULT, (rand, biome, x, z) -> biome.getCactusForBiome()));

        add("RandomWater", new CommonResource(new WorldGenLiquidsOld(STATIONARY_WATER), new DepthPackedHeightDistribution(0, 8, 126), 50));
        add("RandomLava", new CommonResource(new WorldGenLiquidsOld(STATIONARY_LAVA), (rand) -> rand.nextInt(rand.nextInt(rand.nextInt(112) + 8) + 8), 20));

        add("Snow", new SnowPopulator());

        if (config.generateEmerald) {
            add("Emerald", new CommonResource(new WorldGenMinableOld(EMERALD_ORE, 2), new CenteredHeightDistribution(16, 16), 1));
        }
        //hack, ending populator:
        add(new PopulatorStateExit(this));
    }

    private void add(String name, IPopulator populator) {
        add(new PopulatorProxy(this, populator, name));
    }

    private void add(BlockPopulator populator) {
        populators.add(populator);
    }

    public void setTreeNoise(NoiseGeneratorOctaves3D treeNoise) {
        this.treeNoise = treeNoise;
    }

    public List<BlockPopulator> getPopulators() {
        return populators;
    }

    private static class PopulatorStateInit extends PopulatorProxy {

        PopulatorStateInit(PopulatorManager stateManager) {
            super(stateManager, (world, state) -> {
            }, "B173GenPopulatorChainInit");
        }

        @Override
        public void populate(World world, Random random, Chunk chunk) {
            this.stateManager.createStateFor(chunk.getX(), chunk.getZ());
        }
    }

    private static class PopulatorStateExit extends PopulatorProxy {

        PopulatorStateExit(PopulatorManager stateManager) {
            super(stateManager, (world, state) -> {
            }, "B173GenPopulatorChainEnd");
        }


        @Override
        public void populate(World world, Random random, Chunk chunk) {
            this.stateManager.destroyStateFor(chunk.getX(), chunk.getZ());
        }
    }

    private PopulatorState primaryState = null;
    private Set<PopulatorState> allStates = new HashSet<PopulatorState>();

    void createStateFor(int chunkX, int chunkZ) {
        this.primaryState = PopulatorState.forChunk(this.world, this.wcm, chunkX, chunkZ);
        this.allStates.add(primaryState);
        if (allStates.size() > 1) {
            Generator.logger().warning("Already populating a chunk. Recursive chunk generation may cause issues.");
        }
    }

    void destroyStateFor(int chunkX, int chunkZ) {
        if (primaryState.isChunk(chunkX, chunkZ)) {
            allStates.remove(primaryState);
            return;
        }
        Iterator<PopulatorState> it = allStates.iterator();
        while (it.hasNext()) {
            if (it.next().isChunk(chunkX, chunkZ)) {
                it.remove();
                return;
            }
        }
        throw new IllegalStateException("No populator state for chunk " + chunkX + ", " + chunkZ);
    }

    PopulatorState getStateFor(int chunkX, int chunkZ) {
        if (primaryState.isChunk(chunkX, chunkZ)) {
            return primaryState;
        }
        for (PopulatorState state : allStates) {
            if (state.isChunk(chunkX, chunkZ)) {
                return primaryState;
            }
        }
        throw new IllegalStateException("No populator state for chunk " + chunkX + ", " + chunkZ);
    }
}

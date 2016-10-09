package com.github.barteks2x.b173gen.generator.populator;

import com.github.barteks2x.b173gen.ISimpleWorld;
import com.github.barteks2x.b173gen.generator.FeatureDensityGenerator;
import com.github.barteks2x.b173gen.generator.GeneratorSelector;
import com.github.barteks2x.b173gen.generator.IPopulator;
import com.github.barteks2x.b173gen.generator.PopulatorState;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import com.github.barteks2x.b173gen.util.HeightDistrubution;

import java.util.Random;

public class CommonResource implements IPopulator {
    private final GeneratorSelector generatorSelector;
    private final HeightDistrubution heightDistrubution;
    private final FeatureDensityGenerator attemptsPerChunk;

    public CommonResource(WorldGenerator173 generator, HeightDistrubution heightDistrubution, int attemptsPerChunk) {
        this(generator, heightDistrubution, (rand, biome, x, z) -> attemptsPerChunk);
    }

    public CommonResource(WorldGenerator173 generator, HeightDistrubution heightDistrubution, FeatureDensityGenerator attemptsPerChunk) {
        this((rand, biome) -> generator, heightDistrubution, attemptsPerChunk);
    }

    public CommonResource(GeneratorSelector generator, HeightDistrubution heightDistrubution, FeatureDensityGenerator attemptsPerChunk) {
        this.generatorSelector = generator;
        this.heightDistrubution = heightDistrubution;
        this.attemptsPerChunk = attemptsPerChunk;
    }

    @Override
    public void populate(ISimpleWorld world, PopulatorState state) {
        Random rand = state.getRng();
        int attempts = attemptsPerChunk.get(rand, state.getBiome(), state.getChunkX(), state.getChunkZ());

        for (int i = 0; i < attempts; i++) {
            //NOTE: Because of how beta 1.7.3 grass generator worked, it needs to be here instead of after selecting coordinates
            WorldGenerator173 generator = generatorSelector.get(rand, state.getBiome());
            int blockX = state.getBlockX() + rand.nextInt(16);
            int blockY = heightDistrubution.randomHeight(rand);
            int blockZ = state.getBlockZ() + rand.nextInt(16);
            generator.scale(1.0D, 1.0D, 1.0D);
            generator.generate(world, rand, blockX, blockY, blockZ);
        }
    }
}

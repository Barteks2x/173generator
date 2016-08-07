package com.github.barteks2x.b173gen.generator.populator;

import com.github.barteks2x.b173gen.generator.*;
import org.bukkit.World;

import java.util.Random;

public class CommonSurfaceResource implements IPopulator  {
    private final FeatureDensityGenerator attemptsPerChunk;
    private final GeneratorSelector generatorSelector;

    public CommonSurfaceResource(WorldGenerator173 generator, int attemptsPerChunk) {
        this(generator, (rand, biome, x, z) -> attemptsPerChunk);
    }

    public CommonSurfaceResource(WorldGenerator173 generator, FeatureDensityGenerator attemptsPerChunk) {
        this((rand, biome) -> generator, attemptsPerChunk);
    }

    public CommonSurfaceResource(GeneratorSelector generatorSelector, FeatureDensityGenerator attemptsPerChunk) {
        this.generatorSelector = generatorSelector;
        this.attemptsPerChunk = attemptsPerChunk;
    }

    @Override
    public void populate(World world, PopulatorState state) {
        Random rand = state.getRng();
        int attempts = attemptsPerChunk.get(rand, state.getBiome(), state.getChunkX(), state.getChunkZ());
        for (int i = 0; i < attempts; i++) {
            int blockX = state.getBlockX() + rand.nextInt(16);
            int blockZ = state.getBlockZ() + rand.nextInt(16);
            int blockY = world.getHighestBlockYAt(blockX, blockZ);

            //NOTE: Because of how beta 1.7.3 tree generator worked ,it needs to be here, and not before selecting coordinates
            WorldGenerator173 generator = generatorSelector.get(rand, state.getBiome());
            generator.scale(1.0D, 1.0D, 1.0D);
            generator.generate(world, rand, blockX, blockY, blockZ);
        }
    }
}
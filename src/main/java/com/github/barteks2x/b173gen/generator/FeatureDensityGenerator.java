package com.github.barteks2x.b173gen.generator;

import com.github.barteks2x.b173gen.biome.BetaBiome;

import java.util.Random;

public interface FeatureDensityGenerator {
    int get(Random rand, BetaBiome biome, int chunkX, int chunkZ);
}

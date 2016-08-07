package com.github.barteks2x.b173gen.generator;

import com.github.barteks2x.b173gen.biome.BetaBiome;

import java.util.Random;

public interface GeneratorSelector {
    WorldGenerator173 get(Random rand, BetaBiome biome);
}

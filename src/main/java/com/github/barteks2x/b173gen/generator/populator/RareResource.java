package com.github.barteks2x.b173gen.generator.populator;

import com.github.barteks2x.b173gen.ISimpleWorld;
import com.github.barteks2x.b173gen.generator.IPopulator;
import com.github.barteks2x.b173gen.generator.PopulatorState;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import com.github.barteks2x.b173gen.util.HeightDistrubution;

import java.util.Random;

public class RareResource  implements IPopulator {
    private final WorldGenerator173 generator;
    private final HeightDistrubution heightDistrubution;
    private final int rarity;

    public RareResource(WorldGenerator173 generator, HeightDistrubution heightDistrubution, int rarity) {
        this.generator = generator;
        this.heightDistrubution = heightDistrubution;
        this.rarity = rarity;
    }

    @Override
    public void populate(ISimpleWorld world, PopulatorState state) {
        Random rand = state.getRng();
        if (rand.nextInt(rarity) == 0) {
            int blockX = state.getBlockX() + rand.nextInt(16);
            int blockY = heightDistrubution.randomHeight(rand);
            int blockZ = state.getBlockZ() + rand.nextInt(16);
            generator.scale(1.0D, 1.0D, 1.0D);
            generator.generate(world, rand, blockX, blockY, blockZ);
        }
    }
}

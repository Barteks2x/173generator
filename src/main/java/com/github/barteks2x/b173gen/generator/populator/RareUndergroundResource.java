package com.github.barteks2x.b173gen.generator.populator;

import com.github.barteks2x.b173gen.ISimpleWorld;
import com.github.barteks2x.b173gen.generator.*;
import com.github.barteks2x.b173gen.util.HeightDistrubution;
import org.bukkit.World;

import java.util.Random;

public class RareUndergroundResource implements IPopulator {
    private final WorldGenerator173 generator;
    private final HeightDistrubution heightDistrubution;
    private final int rarity;
    private final int surfaceHeight;
    private final int surfaceRarity;

    public RareUndergroundResource(WorldGenerator173 generator, HeightDistrubution heightDistrubution, int rarity, int surfaceHeight, int surfaceRarity) {
        this.generator = generator;
        this.heightDistrubution = heightDistrubution;
        this.rarity = rarity;
        this.surfaceHeight = surfaceHeight;
        this.surfaceRarity = surfaceRarity;
    }

    @Override
    public void populate(ISimpleWorld world, PopulatorState state) {
        Random rand = state.getRng();
        if (rand.nextInt(rarity) == 0) {
            int blockX = state.getBlockX() + rand.nextInt(16);
            int blockY = heightDistrubution.randomHeight(rand);
            int blockZ = state.getBlockZ() + rand.nextInt(16);
            if (blockY < surfaceHeight || rand.nextInt(surfaceRarity) == 0) {
                generator.scale(1.0D, 1.0D, 1.0D);
                generator.generate(world, rand, blockX, blockY, blockZ);
            }
        }
    }
}

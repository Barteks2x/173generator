package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;

public class WorldGenClayOld implements WorldGenerator173 {

    private final Material clayBlock;
    private final int numberOfBlocks;

    public WorldGenClayOld(int i) {
        clayBlock = Material.CLAY;
        numberOfBlocks = i;
    }

    @Override
    public boolean generate(World world, Random random, int blockX, int blockY, int blockZ) {
        if(world.getBlockAt(blockX, blockY, blockZ).getType() != Material.WATER && world.
                getBlockAt(blockX, blockY, blockZ).getType() != Material.STATIONARY_WATER) {
            return false;
        }
        float f = random.nextFloat() * 3.141593F;
        double d = blockX + (MathHelper.sin(f) * numberOfBlocks) / 8F;
        double d1 = blockX - (MathHelper.sin(f) * numberOfBlocks) / 8F;
        double d2 = blockZ + (MathHelper.cos(f) * numberOfBlocks) / 8F;
        double d3 = blockZ - (MathHelper.cos(f) * numberOfBlocks) / 8F;
        double d4 = blockY + random.nextInt(3) + 2;
        double d5 = blockY + random.nextInt(3) + 2;
        for(int l = 0; l <= numberOfBlocks; l++) {
            double d6 = d + ((d1 - d) * l) / numberOfBlocks;
            double d7 = d4 + ((d5 - d4) * l) / numberOfBlocks;
            double d8 = d2 + ((d3 - d2) * l) / numberOfBlocks;
            double d9 = (random.nextDouble() * numberOfBlocks) / 16D;
            double d10 = (MathHelper.sin((l * 3.141593F) / numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (MathHelper.sin((l * 3.141593F) / numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor(d6 - d10 / 2D);
            int j1 = MathHelper.floor(d6 + d10 / 2D);
            int k1 = MathHelper.floor(d7 - d11 / 2D);
            int l1 = MathHelper.floor(d7 + d11 / 2D);
            int i2 = MathHelper.floor(d8 - d10 / 2D);
            int j2 = MathHelper.floor(d8 + d10 / 2D);
            for(int k2 = i1; k2 <= j1; k2++) {
                for(int l2 = k1; l2 <= l1; l2++) {
                    for(int i3 = i2; i3 <= j2; i3++) {
                        double d12 = ((k2 + 0.5D) - d6) / (d10 / 2D);
                        double d13 = ((l2 + 0.5D) - d7) / (d11 / 2D);
                        double d14 = ((i3 + 0.5D) - d8) / (d10 / 2D);
                        if(d12 * d12 + d13 * d13 + d14 * d14 >= 1.0D) {
                            continue;
                        }
                        Material j3 = world.getBlockAt(k2, l2, i3).getType();
                        if(j3 == Material.SAND) {
                            world.getBlockAt(k2, l2, i3).setType(clayBlock);
                        }
                    }

                }

            }

        }

        return true;
    }

    public void scale(double d0, double d1, double d2) {
    }
}

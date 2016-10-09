package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.ISimpleWorld;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import org.bukkit.Material;

import java.util.Random;

public class WorldGenFlowersOld implements WorldGenerator173 {

    private final Material block;

    public WorldGenFlowersOld(Material i) {
        this.block = i;
    }

    public boolean generate(ISimpleWorld world, Random random, int i, int j, int k) {
        for(int l = 0; l < 64; ++l) {
            int i1 = i + random.nextInt(8) - random.nextInt(8);
            int j1 = j + random.nextInt(4) - random.nextInt(4);
            int k1 = k + random.nextInt(8) - random.nextInt(8);

            if(world.isEmpty(i1, j1, k1)
                    && MinecraftMethods.Block_canPlace(block, world, i1, j1, k1)) {
                world.setType(i1, j1, k1, this.block);
            }
        }

        return true;
    }

    public void scale(double d0, double d1, double d2) {
    }
}

package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.ISimpleWorld;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import org.bukkit.Material;

import java.util.Random;

public class WorldGenReedOld implements WorldGenerator173 {

    public WorldGenReedOld() {
    }

    public boolean generate(ISimpleWorld world, Random random, int i, int j, int k) {
        for(int l = 0; l < 20; ++l) {
            int i1 = i + random.nextInt(4) - random.nextInt(4);
            int j1 = j;
            int k1 = k + random.nextInt(4) - random.nextInt(4);

            if(world.isEmpty(i1, j, k1)
                    && MinecraftMethods.Block_canPlace(Material.SUGAR_CANE_BLOCK, world, i, j, k)) {
                int l1 = 2 + random.nextInt(random.nextInt(3) + 1);

                for(int i2 = 0; i2 < l1; ++i2) {
                    if(MinecraftMethods.Block_canPlace(Material.SUGAR_CANE_BLOCK, world, i1, j1 + i2, k1)) {
                        world.setType(i1, j1 + i2, k1, Material.SUGAR_CANE_BLOCK);
                    }
                }
            }
        }

        return true;
    }

    public void scale(double d0, double d1, double d2) {
    }
}

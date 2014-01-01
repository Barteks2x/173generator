package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;

public class WorldGenFlowersOld implements WorldGenerator173 {

    private final Material block;

    public WorldGenFlowersOld(Material i) {
        this.block = i;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        for(int l = 0; l < 64; ++l) {
            int i1 = i + random.nextInt(8) - random.nextInt(8);
            int j1 = j + random.nextInt(4) - random.nextInt(4);
            int k1 = k + random.nextInt(8) - random.nextInt(8);

            if(world.getBlockAt(i1, j1, k1).isEmpty()
                    && MinecraftMethods.Block_canPlace(block, world, i1, j1, k1)) {
                world.getBlockAt(i1, j1, k1).setType(this.block);
            }
        }

        return true;
    }

    public void scale(double d0, double d1, double d2) {}
}

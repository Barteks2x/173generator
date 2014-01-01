package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;

public class WorldGenDeadBushOld implements WorldGenerator173 {

    private final Material block;

    public WorldGenDeadBushOld(Material block) {
        this.block = block;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {

        for(Material l;
                ((l = world.getBlockAt(i, j, k).getType()) == Material.AIR
                || l == Material.LEAVES) && j > 0; --j) {
        }

        for(int i1 = 0; i1 < 4; ++i1) {
            int j1 = i + random.nextInt(8) - random.nextInt(8);
            int k1 = j + random.nextInt(4) - random.nextInt(4);
            int l1 = k + random.nextInt(8) - random.nextInt(8);

            if(world.getBlockAt(j1, k1, l1).isEmpty() && MinecraftMethods.Block_canPlace(this.block, world, i1, k1, l1)) {
                world.getBlockAt(j1, k1, l1).setType(this.block);
            }
        }

        return true;
    }

    public void scale(double d0, double d1, double d2) {}
}

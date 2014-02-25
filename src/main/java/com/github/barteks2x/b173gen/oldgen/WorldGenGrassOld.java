package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class WorldGenGrassOld implements WorldGenerator173 {

    private final Material block;
    private final byte data;

    public WorldGenGrassOld(Material i, byte j) {
        this.block = i;
        this.data = j;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        Block l;

        for(; ((l = world.getBlockAt(i, j, k)).isEmpty()
                || l.getType() == Material.LEAVES) && j > 0; --j) {
        }

        for(int i1 = 0; i1 < 128; ++i1) {
            int j1 = i + random.nextInt(8) - random.nextInt(8);
            int k1 = j + random.nextInt(4) - random.nextInt(4);
            int l1 = k + random.nextInt(8) - random.nextInt(8);

            if(world.getBlockAt(j1, k1, l1).isEmpty()
                    && MinecraftMethods.Block_canPlace(this.block, world, j1, k1, l1)) {
                Block b = world.getBlockAt(j1, k1, l1);
                b.setType(this.block);
                b.setData(this.data);
            }
        }

        return true;
    }

    public void scale(double d0, double d1, double d2) {
    }
}

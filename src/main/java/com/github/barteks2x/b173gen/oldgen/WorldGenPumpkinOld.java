package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class WorldGenPumpkinOld implements WorldGenerator173 {

    public WorldGenPumpkinOld() {
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        for(int l = 0; l < 64; ++l) {
            int x = i + random.nextInt(8) - random.nextInt(8);
            int y = j + random.nextInt(4) - random.nextInt(4);
            int z = k + random.nextInt(8) - random.nextInt(8);

            if(world.getBlockAt(x, y, z).isEmpty()
                    && world.getBlockAt(x, y - 1, z).getType() == Material.GRASS
                    && MinecraftMethods.Block_canPlace(Material.PUMPKIN, world, x, y, z)) {
                Block block = world.getBlockAt(x, y, z);
                block.setType(Material.PUMPKIN);
                block.setData((byte)random.nextInt(4));//random rotation
            }
        }

        return true;
    }

    public void scale(double d0, double d1, double d2) {
    }
}

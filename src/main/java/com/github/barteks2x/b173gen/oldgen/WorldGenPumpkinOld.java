package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.ISimpleWorld;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Pumpkin;

import java.util.Random;

public class WorldGenPumpkinOld implements WorldGenerator173 {

    private static final BlockFace[] facings = new BlockFace[] {
        BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST
    };

    public WorldGenPumpkinOld() {
    }

    public boolean generate(ISimpleWorld world, Random random, int i, int j, int k) {
        for(int l = 0; l < 64; ++l) {
            int x = i + random.nextInt(8) - random.nextInt(8);
            int y = j + random.nextInt(4) - random.nextInt(4);
            int z = k + random.nextInt(8) - random.nextInt(8);

            if(world.isEmpty(x, y, z)
                    && world.getType(x, y - 1, z) == Material.GRASS
                    && MinecraftMethods.Block_canPlace(Material.PUMPKIN, world, x, y, z)) {
                world.setType(x, y, z, Material.PUMPKIN, new Pumpkin(randomFacing(random)));
            }
        }

        return true;
    }

    private static BlockFace randomFacing(Random rand) {
        return facings[rand.nextInt(facings.length)];
    }

    public void scale(double d0, double d1, double d2) {
    }
}

package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.v1_4_6.Block;
import net.minecraft.server.v1_4_6.Material;
import net.minecraft.server.v1_4_6.World;
import net.minecraft.server.v1_4_6.WorldGenerator;

public class WorldGenReed extends WorldGenerator
{

    public WorldGenReed()
    {
    }

    @Override
	public boolean a(World world, Random random, int i, int j, int k)
    {
        for(int l = 0; l < 20; l++)
        {
            int i1 = (i + random.nextInt(4)) - random.nextInt(4);
            int j1 = j;
            int k1 = (k + random.nextInt(4)) - random.nextInt(4);
            if(!(world.getMaterial(i1, j1, k1) == Material.AIR) || world.getMaterial(i1 - 1, j1 - 1, k1) != Material.WATER && world.getMaterial(i1 + 1, j1 - 1, k1) != Material.WATER && world.getMaterial(i1, j1 - 1, k1 - 1) != Material.WATER && world.getMaterial(i1, j1 - 1, k1 + 1) != Material.WATER)
            {
                continue;
            }
            int l1 = 2 + random.nextInt(random.nextInt(3) + 1);
            for(int i2 = 0; i2 < l1; i2++)
            {
                if(Block.SUGAR_CANE_BLOCK.canPlace(world, i1, j1 + i2, k1))
                {
                    world.setRawTypeId(i1, j1 + i2, k1, Block.SUGAR_CANE_BLOCK.id);
                }
            }

        }

        return true;
    }
}

package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;


import net.minecraft.server.v1_4_6.Block;
import net.minecraft.server.v1_4_6.World;
import net.minecraft.server.v1_4_6.WorldGenerator;

public class WorldGenTaiga2 extends WorldGenerator
{

    public WorldGenTaiga2()
    {
    }

    @Override
	public boolean a(World world, Random random, int i, int j, int k)
    {
        int l = random.nextInt(4) + 6;
        int i1 = 1 + random.nextInt(2);
        int j1 = l - i1;
        int k1 = 2 + random.nextInt(2);
        boolean flag = true;
        if(j < 1 || j + l + 1 > 128)
        {
            return false;
        }
        for(int l1 = j; l1 <= j + 1 + l && flag; l1++)
        {
            int j2 = 1;
            if(l1 - j < i1)
            {
                j2 = 0;
            } else
            {
                j2 = k1;
            }
            for(int l2 = i - j2; l2 <= i + j2 && flag; l2++)
            {
                for(int j3 = k - j2; j3 <= k + j2 && flag; j3++)
                {
                    if(l1 >= 0 && l1 < 128)
                    {
                        int k3 = world.getTypeId(l2, l1, j3);
                        if(k3 != 0 && k3 != Block.LEAVES.id)
                        {
                            flag = false;
                        }
                    } else
                    {
                        flag = false;
                    }
                }

            }

        }

        if(!flag)
        {
            return false;
        }
        int i2 = world.getTypeId(i, j - 1, k);
        if(i2 != Block.GRASS.id && i2 != Block.DIRT.id || j >= 128 - l - 1)
        {
            return false;
        }
        world.setRawTypeId(i, j - 1, k, Block.DIRT.id);
        int k2 = random.nextInt(2);
        int i3 = 1;
        boolean flag1 = false;
        for(int l3 = 0; l3 <= j1; l3++)
        {
            int j4 = (j + l) - l3;
            for(int l4 = i - k2; l4 <= i + k2; l4++)
            {
                int j5 = l4 - i;
                for(int k5 = k - k2; k5 <= k + k2; k5++)
                {
                    int l5 = k5 - k;
                    if((Math.abs(j5) != k2 || Math.abs(l5) != k2 || k2 <= 0) && !Block.q[world.getTypeId(l4, j4, k5)])
                    {
                        world.setRawTypeIdAndData(l4, j4, k5, Block.LEAVES.id, 1);
                    }
                }

            }

            if(k2 >= i3)
            {
                k2 = ((flag1) ? 1 : 0);
                flag1 = true;
                if(++i3 > k1)
                {
                    i3 = k1;
                }
            } else
            {
                k2++;
            }
        }

        int i4 = random.nextInt(3);
        for(int k4 = 0; k4 < l - i4; k4++)
        {
            int i5 = world.getTypeId(i, j + k4, k);
            if(i5 == 0 || i5 == Block.LEAVES.id)
            {
                world.setRawTypeIdAndData(i, j + k4, k, Block.LOG.id, 1);
            }
        }

        return true;
    }
}

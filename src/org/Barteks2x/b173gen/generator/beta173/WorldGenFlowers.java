// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.Block;
import net.minecraft.server.BlockFlower;
import net.minecraft.server.Material;
import net.minecraft.server.World;
import net.minecraft.server.WorldGenerator;


// Referenced classes of package net.minecraft.src:
//            WorldGenerator, World, Block, BlockFlower

public class WorldGenFlowers extends WorldGenerator
{

    public WorldGenFlowers(int i)
    {
        plantBlockId = i;
    }

    @Override
	public boolean a(World world, Random random, int i, int j, int k)
    {
        for(int l = 0; l < 64; l++)
        {
            int i1 = (i + random.nextInt(8)) - random.nextInt(8);
            int j1 = (j + random.nextInt(4)) - random.nextInt(4);
            int k1 = (k + random.nextInt(8)) - random.nextInt(8);
            if(world.getMaterial(i1, j1, k1)==Material.AIR && ((BlockFlower)Block.byId[plantBlockId]).canPlace(world, i1, j1, k1))
            {
                world.setRawTypeId(i1, j1, k1, plantBlockId);
            }
        }

        return true;
    }

    private int plantBlockId;
}

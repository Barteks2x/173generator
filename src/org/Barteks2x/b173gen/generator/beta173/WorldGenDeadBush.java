// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.Block;
import net.minecraft.server.BlockFlower;
import net.minecraft.server.World;
import net.minecraft.server.WorldGenerator;


// Referenced classes of package net.minecraft.src:
//            WorldGenerator, World, Block, BlockLeaves, 
//            BlockFlower

public class WorldGenDeadBush extends WorldGenerator
{

    public WorldGenDeadBush(int i)
    {
        field_28058_a = i;
    }
    


    @Override
    public boolean a(World world, Random random, int i, int j, int k)
    {
        for(int l = 0; ((l = world.getTypeId(i, j, k)) == 0 || l == Block.LEAVES.id) && j > 0; j--) { }
        for(int i1 = 0; i1 < 4; i1++)
        {
            int j1 = (i + random.nextInt(8)) - random.nextInt(8);
            int k1 = (j + random.nextInt(4)) - random.nextInt(4);
            int l1 = (k + random.nextInt(8)) - random.nextInt(8);
	    if (world.isEmpty(j1, k1, l1) && ((BlockFlower)Block.byId[field_28058_a]).d(world, j1, k1, l1))
            {
                world.setRawTypeId(j1, k1, l1, field_28058_a);
            }
        }

        return true;
    }

    private int field_28058_a;
}

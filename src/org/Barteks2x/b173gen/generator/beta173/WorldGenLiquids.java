// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.Block;
import net.minecraft.server.Material;
import net.minecraft.server.World;
import net.minecraft.server.WorldGenerator;

// Referenced classes of package net.minecraft.src:
//            WorldGenerator, World, Block

public class WorldGenLiquids extends WorldGenerator
{

    public WorldGenLiquids(int i)
    {
	liquidBlockId = i;
    }

    @Override
    public boolean a(World world, Random random, int i, int j, int k)
    {
	if (world.getTypeId(i, j + 1, k) != Block.STONE.id)
	{
	    return false;
	}
	if (world.getTypeId(i, j - 1, k) != Block.STONE.id)
	{
	    return false;
	}
	if (world.getTypeId(i, j, k) != 0 && world.getTypeId(i, j, k) != Block.STONE.id)
	{
	    return false;
	}
	int l = 0;
	if (world.getTypeId(i - 1, j, k) == Block.STONE.id)
	{
	    l++;
	}
	if (world.getTypeId(i + 1, j, k) == Block.STONE.id)
	{
	    l++;
	}
	if (world.getTypeId(i, j, k - 1) == Block.STONE.id)
	{
	    l++;
	}
	if (world.getTypeId(i, j, k + 1) == Block.STONE.id)
	{
	    l++;
	}
	int i1 = 0;
	if (world.getMaterial(i - 1, j, k) == Material.AIR)
	{
	    i1++;
	}
	if (world.getMaterial(i + 1, j, k) == Material.AIR)
	{
	    i1++;
	}
	if (world.getMaterial(i, j, k - 1) == Material.AIR)
	{
	    i1++;
	}
	if (world.getMaterial(i, j, k + 1) == Material.AIR)
	{
	    i1++;
	}
	if (l == 3 && i1 == 1)
	{
	    world.setTypeId(i, j, k, liquidBlockId);
	    world.e = true;
	    Block.byId[liquidBlockId].b(world, i, j, k, random);
	    world.e = false;
	}
	return true;
    }

    private int liquidBlockId;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.WorldGenerator;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, WorldGenBigTree, WorldGenTrees, WorldGenerator

public class BiomeGenRainforest extends BiomeGenBase
{

    public BiomeGenRainforest(int i)
    {
	super(i);
    }

    @Override
    public WorldGenerator a(Random random)
    {
	if (random.nextInt(3) == 0)
	{
	    return new WorldGenBigTree();
	}
	return new WorldGenTrees();
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.BiomeMeta;
import net.minecraft.server.WorldGenerator;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, WorldGenForest, 
//            WorldGenBigTree, WorldGenTrees, WorldGenerator

public class BiomeGenForest extends BiomeGenBase {

    @SuppressWarnings("unchecked")
    public BiomeGenForest(int i) {
	super(i);
	K.add(new BiomeMeta(net.minecraft.server.EntityWolf.class, 2, 4, 4));
    }

    @Override
    public WorldGenerator a(Random random) {
	if (random.nextInt(5) == 0) {
	    return new WorldGenForest();
	}
	if (random.nextInt(3) == 0) {
	    return new WorldGenBigTree();
	}
	return new WorldGenTrees();
    }
}

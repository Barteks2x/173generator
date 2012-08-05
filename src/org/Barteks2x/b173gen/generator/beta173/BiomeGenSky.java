// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package org.Barteks2x.b173gen.generator.beta173;

import net.minecraft.server.BiomeMeta;


// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityChicken

public class BiomeGenSky extends BiomeGenBase
{

    @SuppressWarnings("unchecked")
    public BiomeGenSky(int i)
    {
	super(i);
        J.clear();
        K.clear();
        L.clear();
        K.add(new BiomeMeta(net.minecraft.server.EntityChicken.class, 10, 4, 4));
    }
}

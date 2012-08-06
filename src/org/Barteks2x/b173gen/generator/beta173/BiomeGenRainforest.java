package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.WorldGenerator;

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

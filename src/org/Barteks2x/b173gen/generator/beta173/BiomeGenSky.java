package org.Barteks2x.b173gen.generator.beta173;

import net.minecraft.server.BiomeMeta;

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

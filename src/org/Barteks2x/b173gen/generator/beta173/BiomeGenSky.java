package org.Barteks2x.b173gen.generator.beta173;

import net.minecraft.server.v1_4_6.BiomeMeta;

public class BiomeGenSky extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenSky(int i) {
		super(i);
		J.clear();
		K.clear();
		L.clear();
		K.add(new BiomeMeta(net.minecraft.server.v1_4_6.EntityChicken.class,
				10, 4, 4));
	}
}

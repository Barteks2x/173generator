package org.Barteks2x.b173gen.generator.beta173;

import net.minecraft.server.v1_4_R1.BiomeMeta;

public class BiomeGenHell extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenHell(int i) {
		super(i);
		J.clear();
		K.clear();
		L.clear();
		J.add(new BiomeMeta(net.minecraft.server.v1_4_R1.EntityGhast.class, 10,
				4, 4));
		J.add(new BiomeMeta(net.minecraft.server.v1_4_R1.EntityPigZombie.class,
				10, 4, 4));
	}
}

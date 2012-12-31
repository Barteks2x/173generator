package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.v1_4_6.BiomeMeta;
import net.minecraft.server.v1_4_6.WorldGenerator;

public class BiomeGenTaiga extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenTaiga(int i) {
		super(i);
		K.add(new BiomeMeta(net.minecraft.server.v1_4_6.EntityWolf.class, 2, 4,
				4));
	}

	@Override
	public WorldGenerator a(Random random) {
		if (random.nextInt(3) == 0) {
			return new WorldGenTaiga1();
		}
		return new WorldGenTaiga2();
	}
}

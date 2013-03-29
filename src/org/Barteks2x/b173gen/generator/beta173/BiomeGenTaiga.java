package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.v1_5_R2.BiomeMeta;
import net.minecraft.server.v1_5_R2.EntityWolf;
import net.minecraft.server.v1_5_R2.WorldGenTaiga1;
import net.minecraft.server.v1_5_R2.WorldGenTaiga2;
import net.minecraft.server.v1_5_R2.WorldGenerator;

public class BiomeGenTaiga extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenTaiga(int i) {
		super(i);
		K.add(new BiomeMeta(EntityWolf.class, 2, 4, 4));
	}

	@Override
	public WorldGenerator a(Random random) {
		if (random.nextInt(3) == 0) {
			return new WorldGenTaiga1();
		}
		return new WorldGenTaiga2(true);
	}
}

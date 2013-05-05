package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.v1_5_R2.BiomeMeta;
import net.minecraft.server.v1_5_R2.EntityWolf;
import org.Barteks2x.b173gen.generator.WorldGenerator173;

public class BiomeGenForest extends BiomeGenBase {

        @SuppressWarnings("unchecked")
	public BiomeGenForest(int i) {
		super(i);
		K.add(new BiomeMeta(EntityWolf.class, 2, 4, 4));
	}

	@Override
	public WorldGenerator173 a(Random random) {
		if (random.nextInt(5) == 0) {
			return new WorldGenForestOld();
		}
		if (random.nextInt(3) == 0) {
			return new WorldGenBigTreeOld();
		}
		return new WorldGenTreeOld();
	}
}

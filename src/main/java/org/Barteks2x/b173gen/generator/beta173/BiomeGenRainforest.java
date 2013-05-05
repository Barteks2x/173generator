package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import org.Barteks2x.b173gen.generator.WorldGenerator173;

public class BiomeGenRainforest extends BiomeGenBase {

	public BiomeGenRainforest(int i) {
		super(i);
	}

	@Override
	public WorldGenerator173 a(Random random) {
		if (random.nextInt(3) == 0) {
			return new WorldGenBigTreeOld();
		}
		return new WorldGenTreeOld();
	}
}

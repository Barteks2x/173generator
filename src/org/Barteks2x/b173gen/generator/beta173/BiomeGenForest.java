package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.BiomeMeta;
import net.minecraft.server.WorldGenerator;


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

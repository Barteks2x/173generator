package com.github.barteks2x.b173gen.generator;

import com.github.barteks2x.b173gen.ISimpleWorld;

import java.util.Random;

public interface WorldGenerator173 {

    boolean generate(ISimpleWorld world, Random rand, int x, int y, int z);

    default void scale(double d0, double d1, double d2){}
}

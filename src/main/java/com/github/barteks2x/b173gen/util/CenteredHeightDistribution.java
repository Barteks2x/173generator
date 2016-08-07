package com.github.barteks2x.b173gen.util;

import java.util.Random;

public class CenteredHeightDistribution implements HeightDistrubution {

    private int centerHeight;
    private int radius;

    public CenteredHeightDistribution(int centerHeight, int maxHeight) {
        this.centerHeight = centerHeight;
        this.radius = maxHeight;
    }

    @Override
    public int randomHeight(Random rng) {
        return rng.nextInt(radius) + rng.nextInt(radius) + (centerHeight - radius);
    }
}

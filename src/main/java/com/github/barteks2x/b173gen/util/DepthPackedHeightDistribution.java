package com.github.barteks2x.b173gen.util;

import java.util.Random;

public class DepthPackedHeightDistribution implements HeightDistrubution {

    private int minHeight;
    private int linearStartDepth;
    private int maxHeight;

    public DepthPackedHeightDistribution(int minHeight, int linearStartDepth, int maxHeight) {
        this.minHeight = minHeight;
        this.linearStartDepth = linearStartDepth;
        this.maxHeight = maxHeight;
    }

    @Override
    public int randomHeight(Random rng) {
        return rng.nextInt(rng.nextInt(maxHeight - linearStartDepth + 2) + linearStartDepth - minHeight) + minHeight;
    }
}

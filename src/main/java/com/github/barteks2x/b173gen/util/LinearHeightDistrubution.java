package com.github.barteks2x.b173gen.util;

import java.util.Random;

public class LinearHeightDistrubution implements HeightDistrubution {

    private int minHeight;
    private int maxHeight;

    public LinearHeightDistrubution(int minHeight, int maxHeight) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    public int randomHeight(Random rng) {
        return rng.nextInt(maxHeight + 1 - minHeight) + minHeight;
    }
}

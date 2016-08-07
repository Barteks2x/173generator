package com.github.barteks2x.b173gen.util;

import java.util.Random;

public interface HeightDistrubution {
    HeightDistrubution DEFAULT = new LinearHeightDistrubution(0, 127);

    int randomHeight(Random rng);
}

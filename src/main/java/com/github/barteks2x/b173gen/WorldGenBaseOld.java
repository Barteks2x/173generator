package com.github.barteks2x.b173gen;

import java.util.Random;
import org.bukkit.World;

public abstract class WorldGenBaseOld {
    protected int blockShift;
    protected Random rand;

    public WorldGenBaseOld() {
        blockShift = 8;
        rand = new Random();
    }

    @SuppressWarnings("cast")
    public void generate(World world, int i, int j, byte abyte0[]) {
        int k = blockShift;
        rand.setSeed(world.getSeed());
        long l = (rand.nextLong() / 2L) * 2L + 1L;
        long l1 = (rand.nextLong() / 2L) * 2L + 1L;
        for(int i1 = i - k; i1 <= i + k; i1++) {
            for(int j1 = j - k; j1 <= j + k; j1++) {
                rand.setSeed((long)i1 * l + (long)j1 * l1 ^ world.getSeed());
                generate(world, i1, j1, i, j, abyte0);
            }
        }
    }

    protected abstract void generate(World world, int i1, int j1, int i, int j, byte[] abyte0);
}

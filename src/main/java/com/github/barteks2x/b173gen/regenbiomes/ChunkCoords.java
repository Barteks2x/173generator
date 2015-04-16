package com.github.barteks2x.b173gen.regenbiomes;

class ChunkCoords {
    final int x;
    private final int z;

    ChunkCoords(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

}

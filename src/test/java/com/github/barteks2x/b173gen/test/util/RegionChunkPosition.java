package com.github.barteks2x.b173gen.test.util;

public class RegionChunkPosition {
    private final RegionPosition pos;
    private final int chunkLocalX;
    private final int chunkLocalZ;

    public RegionChunkPosition(RegionPosition pos, int chunkLocalX, int chunkLocalZ) {
        this.pos = pos;
        this.chunkLocalX = chunkLocalX;
        this.chunkLocalZ = chunkLocalZ;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionChunkPosition that = (RegionChunkPosition) o;

        if (chunkLocalX != that.chunkLocalX) return false;
        if (chunkLocalZ != that.chunkLocalZ) return false;
        return pos != null ? pos.equals(that.pos) : that.pos == null;

    }

    @Override
    public int hashCode() {
        int result = pos != null ? pos.hashCode() : 0;
        result = 31 * result + chunkLocalX;
        result = 31 * result + chunkLocalZ;
        return result;
    }

    public String toString() {
        return String.format("(%d, %d)", getChunkX(), getChunkZ());
    }

    public int getChunkZ() {
        return pos.getZ() * 32 + chunkLocalZ;
    }

    public int getChunkX() {
        return pos.getX() * 32 + chunkLocalX;
    }

    public static RegionChunkPosition fromChunkPos(int posX, int posZ) {
        return new RegionChunkPosition(new RegionPosition(posX >> 5, posZ >> 5), posX & 31, posZ & 31);
    }
}

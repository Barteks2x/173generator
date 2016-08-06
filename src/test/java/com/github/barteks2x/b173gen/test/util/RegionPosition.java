package com.github.barteks2x.b173gen.test.util;

public class RegionPosition {
    private final int x, z;

    public RegionPosition(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }
    public int getZ() {
        return z;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionPosition that = (RegionPosition) o;

        if (x != that.x) return false;
        return z == that.z;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, z);
    }
}

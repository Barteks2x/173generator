package com.github.barteks2x.b173gen.regenbiomes;

import java.io.File;

class RegionInfo {
    private final File file;
    private final int x;
    private final int z;

    RegionInfo(File file, int x, int z) {
        this.file = file;
        this.x = x;
        this.z = z;
    }

    public File getFile() {
        return file;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

}

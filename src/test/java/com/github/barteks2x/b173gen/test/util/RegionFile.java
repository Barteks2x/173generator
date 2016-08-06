package com.github.barteks2x.b173gen.test.util;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RegionFile {
    private final File region;
    private final RegionPosition pos;

    public RegionFile(File region) {
        this.region = region;
        pos = parseRegionPos(region.getName());
    }

    public Collection<RegionChunk> findChunks() throws IOException {
        Set<RegionChunk> chunks = new HashSet<RegionChunk>();
        int[] chunkLocations = readChunkLocations(region);
        findChunks(chunks, chunkLocations);
        return chunks;
    }

    private void findChunks(Set<RegionChunk> chunks, int[] locations) {
        for (int chunkLocalX = 0; chunkLocalX < 32; chunkLocalX++) {
            for (int chunkLocalZ = 0; chunkLocalZ < 32; chunkLocalZ++) {
                int index = chunkLocalZ * 32 + chunkLocalX;
                int location = locations[index];
                if (location != 0) {
                    int offset = location >>> 8;
                    int count = location & 0xFF;
                    RegionChunk chunk = new RegionChunk(this, new RegionChunkPosition(pos, chunkLocalX, chunkLocalZ), offset, count);
                    chunks.add(chunk);
                }
            }
        }
    }

    private int[] readChunkLocations(File region) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(region)));
        int[] locations = new int[1024];
        for (int i = 0; i < 1024; i++) {
            locations[i] = in.readInt();
        }
        return locations;
    }

    private RegionPosition parseRegionPos(String filename) {
        //name format: r.X.Y.mcr
        String[] split = filename.split("\\.");
        int regionX = Integer.parseInt(split[1]);
        int regionZ = Integer.parseInt(split[2]);
        return new RegionPosition(regionX, regionZ);
    }

    @Override
    public String toString() {
        return pos.toString();
    }

    public RandomAccessFile getRandomAccessFile(String flags) throws FileNotFoundException {
        return new RandomAccessFile(this.region, flags);
    }
}

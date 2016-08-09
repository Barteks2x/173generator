package com.github.barteks2x.b173gen.test.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.DataFormatException;

public class PreloadedChunkSource implements IChunkSource {
    private static final String REGION_FILE_REGEX =
                    "r\\." + //r.
                    "-?\\d+" + //number
                    "\\." + //dot
                    "-?\\d+" +//number
                    "\\.mcr";//.mcr

    private Map<RegionChunkPosition, ChunkData> chunks = new HashMap<>();

    private PreloadedChunkSource(Set<ChunkData> loadedChunks) {
        for(ChunkData data : loadedChunks) {
            chunks.put(data.getPosition(), data);
        }
    }

    public static PreloadedChunkSource createPreloadedChunksFromMcRegions(File regionDir) throws IOException, DataFormatException {
        Set<RegionChunk> regionChunks = regionDir == null ? new HashSet<>() : listChunksInRegions(regionDir);
        Set<ChunkData> loadedChunks = loadChunks(regionChunks);
        return new PreloadedChunkSource(loadedChunks);
    }

    private static Set<RegionChunk> listChunksInRegions(File regionDir) throws IOException {
        Set<RegionFile> regions = new HashSet<RegionFile>();
        for (File region : regionDir.listFiles()) {
            if (!region.getName().matches(REGION_FILE_REGEX)) {
                continue;
            }
            regions.add(new RegionFile(region));
        }
        Set<RegionChunk> regionChunks = new HashSet<RegionChunk>();
        for (RegionFile region : regions) {
            regionChunks.addAll(region.findChunks());
        }
        return regionChunks;
    }

    private static Set<ChunkData> loadChunks(Set<RegionChunk> toLoad) throws IOException, DataFormatException {
        Set<ChunkData> chunks = new HashSet<ChunkData>();
        for (RegionChunk regionChunk : toLoad) {
            chunks.add(regionChunk.loadData());
        }
        return chunks;
    }

    @Override
    public void loadChunkData(int x, int z) {
        ChunkData data = getChunkData(x, z);
        if(data == null) {
            this.chunks.put(RegionChunkPosition.fromChunkPos(x, z), ChunkData.empty(x, z));
        }
    }

    @Override
    public ChunkData getChunkData(int x, int z) {
        return chunks.get(RegionChunkPosition.fromChunkPos(x, z));
    }
}

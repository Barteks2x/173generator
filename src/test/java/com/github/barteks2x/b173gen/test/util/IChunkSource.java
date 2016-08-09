package com.github.barteks2x.b173gen.test.util;

public interface IChunkSource {
    void loadChunkData(int x, int z);
    ChunkData getChunkData(int x, int z);
}

package com.github.barteks2x.b173gen.test.util;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;

public class ChunkData implements ChunkGenerator.ChunkData{
    private final RegionChunkPosition position;
    private final byte[] blockIds;
    private final byte[] metadata;
    private boolean populated;

    private ChunkData(Builder builder) {
        this.blockIds = builder.blockIds;
        this.metadata = builder.metadata;
        this.position = builder.regionChunkPosition;
    }

    public static ChunkData.Builder builder() {
        return new Builder();
    }

    public static ChunkData empty(int x, int z) {
        return builder().setData(new byte[16*16*128], new byte[16*16*128/2]).setPosition(RegionChunkPosition.fromChunkPos(x, z)).build();
    }

    public int getX() {
        return position.getChunkX();
    }

    public int getZ() {
        return position.getChunkZ();
    }

    public Material getBlock(int x, int y, int z) {
        if(y >= 128) {
            return Material.AIR;
        }
        int pos = y | z << 7 | x << 11;
        return Material.getMaterial(blockIds[pos] & 0xFF);
    }

    public RegionChunkPosition getPosition() {
        return position;
    }

    @Override
    public int getMaxHeight() {
        return 128;
    }

    @Override
    public void setBlock(int x, int y, int z, Material material) {
        if(y < 0 || y >= 128) {
            return;
        }
        int pos = y | z << 7 | x << 11;
        blockIds[pos] = (byte) material.getId();
    }

    @Override
    public void setBlock(int x, int y, int z, MaterialData materialData) {
        setBlock(x, y, z, materialData.getItemType());
    }

    @Override
    public void setRegion(int i, int i1, int i2, int i3, int i4, int i5, Material material) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setRegion(int i, int i1, int i2, int i3, int i4, int i5, MaterialData materialData) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Material getType(int i, int i1, int i2) {
        return getBlock(i, i1, i2);
    }

    @Override
    public MaterialData getTypeAndData(int i, int i1, int i2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setRegion(int i, int i1, int i2, int i3, int i4, int i5, int i6) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setRegion(int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setBlock(int i, int i1, int i2, int i3) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setBlock(int i, int i1, int i2, int i3, byte b) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int getTypeId(int i, int i1, int i2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public byte getData(int i, int i1, int i2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public byte[] getIdrray() {
        return this.blockIds;
    }

    public boolean isPopulated() {
        return populated;
    }

    public void setPopulated(boolean populated) {
        this.populated = populated;
    }

    public static class Builder {
        private RegionChunkPosition regionChunkPosition;
        private byte[] blockIds;
        private byte[] metadata;

        public Builder setPosition(RegionChunkPosition regionChunkPosition) {
            this.regionChunkPosition = regionChunkPosition;
            return this;
        }
        public ChunkData build() {
            return new ChunkData(this);
        }

        public Builder setData(byte[] blockIds, byte[] metadata) {
            this.blockIds = blockIds;
            this.metadata = metadata;
            return this;
        }
    }
}

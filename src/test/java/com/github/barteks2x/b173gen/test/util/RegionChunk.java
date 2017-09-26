package com.github.barteks2x.b173gen.test.util;

import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.stream.NBTInputStream;

import java.io.*;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class RegionChunk {
    private static final int SECTOR_BYTES = 4096;
    private final RegionFile regionFile;
    private final RegionChunkPosition regionChunkPosition;
    private final int offset;
    private final int count;

    public RegionChunk(RegionFile regionFile, RegionChunkPosition regionChunkPosition, int offset, int count) {

        this.regionFile = regionFile;
        this.regionChunkPosition = regionChunkPosition;
        this.offset = offset;
        this.count = count;
    }

    public ChunkData loadData() throws IOException, DataFormatException {
            RandomAccessFile raf = regionFile.getRandomAccessFile("r");
            int offsetBytes = offset * SECTOR_BYTES;
            int sizeBytes = count * SECTOR_BYTES;
            raf.seek(offsetBytes);
            byte[] data = new byte[sizeBytes];
            raf.read(data);
            raf.close();
            int dataLength =
                    ((data[0] & 0xFF) << 24) |
                            ((data[1] & 0xFF) << 16) |
                            ((data[2] & 0xFF) << 8) |
                            ((data[3] & 0xFF) << 0);
            byte[] compressedData = new byte[dataLength - 1];
            System.arraycopy(data, 5, compressedData, 0, dataLength - 1);
            byte[] uncompressed = decompress(compressedData);
            NBTInputStream nbtIn = new NBTInputStream(new ByteArrayInputStream(uncompressed), false);
            CompoundTag tag = (CompoundTag) nbtIn.readTag();
            nbtIn.close();
            CompoundTag level = (CompoundTag) tag.getValue().get("Level");

            ChunkData.Builder builder = ChunkData.builder();
            builder.setPosition(regionChunkPosition);
            byte[] blockIdsLsb = (byte[]) level.getValue().get("Blocks").getValue();
            byte[] metadata = (byte[]) level.getValue().get("Data").getValue();
            builder.setData(blockIdsLsb, metadata);

            return builder.build();

    }

    private byte[] decompress(byte[] compressedData) throws DataFormatException, IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(compressedData);
        byte[] cache = new byte[4096];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (!inflater.finished()) {
            inflater.inflate(cache);
            out.write(cache);
        }
        return out.toByteArray();
    }
}

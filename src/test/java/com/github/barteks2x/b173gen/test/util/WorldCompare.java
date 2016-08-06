package com.github.barteks2x.b173gen.test.util;

import org.bukkit.Material;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.zip.DataFormatException;

public class WorldCompare {

    private static final String REGION_FILE_REGEX =
            "r\\." + //r.
                    "-?\\d+" + //number
                    "\\." + //dot
                    "-?\\d+" +//number
                    "\\.mcr";//.mcr

    //these are impossible to get right due to differences in the way MC works
    private static final boolean IGNORE_LIQUID_TYPE_DIFFERENCE = true;

    private IGeneratorChunkSource source;
    private File regionFilesDir;
    private File chunksToGenerateList;
    private int maxDiff;

    public WorldCompare(IGeneratorChunkSource source, File regionFilesDir, File chunksToGenerateList, int maxDiff) {
        this.source = source;
        this.regionFilesDir = regionFilesDir;
        this.chunksToGenerateList = chunksToGenerateList;
        this.maxDiff = maxDiff;
    }

    public CompareResults start() throws IOException, DataFormatException {
        File regionDir = regionFilesDir;
        Set<RegionFile> regions = new HashSet<RegionFile>();
        for (File region : regionDir.listFiles()) {
            if (!region.getName().matches(REGION_FILE_REGEX)) {
                continue;
            }
            regions.add(new RegionFile(region));
        }
        Set<RegionChunk> chunks = new HashSet<RegionChunk>();
        for (RegionFile region : regions) {
            chunks.addAll(region.findChunks());
        }
        return loadAndCompareChunks(chunks);
    }

    private CompareResults loadAndCompareChunks(Set<RegionChunk> toLoad) throws IOException, DataFormatException {
        Set<ChunkData> chunks = new HashSet<ChunkData>();
        for (RegionChunk regionChunk : toLoad) {
            chunks.add(regionChunk.loadData());
        }
        Map<ChunkData, ChunkData> savedToGeneratedMapping = new HashMap<ChunkData, ChunkData>();

        generateChunks(this.chunksToGenerateList, chunks, savedToGeneratedMapping);
        return compareChunks(savedToGeneratedMapping);
    }

    public void generateChunks(File chunkList, Set<ChunkData> savedChunks, Map<ChunkData, ChunkData> savedToGeneratedOut) throws FileNotFoundException {
        Map<RegionChunkPosition, ChunkData> posToSavedMap = new HashMap<RegionChunkPosition, ChunkData>();
        for(ChunkData data : savedChunks) {
            posToSavedMap.put(data.getPosition(), data);
        }
        //file format:
        //text file, pairs of x/z coordinates separated with whitespace
        Scanner scanner = new Scanner(chunkList);
        while(scanner.hasNext()) {
            int posX = scanner.nextInt();
            int posZ = scanner.nextInt();
            RegionChunkPosition regionChunkPos = RegionChunkPosition.fromChunkPos(posX, posZ);
            source.loadChunkData(posX, posZ);
            ChunkData generatedChunk = source.getChunkData(posX, posZ);
            savedToGeneratedOut.put(posToSavedMap.get(regionChunkPos), generatedChunk);
        }
    }

    private CompareResults compareChunks(Map<ChunkData, ChunkData> savedToGenerated) {
        CompareResults results = new CompareResults(this.maxDiff);

        for (ChunkData data : savedToGenerated.keySet()) {
            ChunkData generatedChunk = savedToGenerated.get(data);
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 128; y++) {
                    for (int z = 0; z < 16; z++) {
                        Material generatedBlock = generatedChunk.getBlock(x, y, z);
                        Material expectedBlock = data.getBlock(x, y, z);
                        if (IGNORE_LIQUID_TYPE_DIFFERENCE) {
                            if (generatedBlock == Material.STATIONARY_WATER) {
                                generatedBlock = Material.WATER;
                            }
                            if (generatedBlock == Material.STATIONARY_LAVA) {
                                generatedBlock = Material.LAVA;
                            }
                            if (expectedBlock == Material.STATIONARY_WATER) {
                                expectedBlock = Material.WATER;
                            }
                            if (expectedBlock == Material.STATIONARY_LAVA) {
                                expectedBlock = Material.LAVA;
                            }
                        }
                        if (generatedBlock != expectedBlock) {
                            results.addDifference(x + data.getX() * 16, y, z + data.getZ() * 16, expectedBlock, generatedBlock);
                        }
                    }
                }
            }
        }
        return results;
    }

    public static class CompareResults {
        private Difference[] differences;
        private int nextIndex = 0;
        private boolean overflow = false;

        public CompareResults(int maxDifferences) {
            this.differences = new Difference[maxDifferences];
        }

        public void addDifference(int blockX, int blockY, int blockZ, Material expected, Material found) {
            if (nextIndex >= differences.length) {
                overflow = true;
                nextIndex++;
                return;
            }
            differences[nextIndex] = new Difference(blockX, blockY, blockZ, expected, found);
            nextIndex++;
        }

        @Override
        public int hashCode() {
            final int prime = 11;
            int hash = 11;
            for (Difference diff : differences) {
                hash += diff == null ? 0 : diff.hashCode();
                hash *= prime;
            }
            hash += overflow ? 1 : 0;
            hash *= prime;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof CompareResults)) {
                return false;
            }
            CompareResults other = (CompareResults) obj;
            int maxDiffLength = Math.max(this.differences.length, other.differences.length);
            for (int i = 0; i < maxDiffLength; i++) {
                Difference diffThis = i >= differences.length ? null : differences[i];
                Difference diffOther = i >= other.differences.length ? null : other.differences[i];
                //both are null --> both are out of range
                if (diffThis == null && diffOther == null) {
                    return this.nextIndex == other.nextIndex;
                }
                if (diffThis == null) {
                    return false;
                }
                if (!diffThis.equals(diffOther)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(100 * differences.length);
            boolean first = true;
            for (Difference diff : differences) {
                if (diff == null) {
                    return sb.toString();
                }
                if (!first) {
                    sb.append("\n");
                }
                first = false;
                sb.append(diff.toString());
            }
            if (this.overflow) {
                sb.append("\nAnd ").append(this.nextIndex - differences.length).append(" more...");
            }
            return sb.toString();
        }

        public static class Difference {
            private final int blockX;
            private final int blockY;
            private final int blockZ;
            private final Material expected;
            private final Material found;

            public Difference(int blockX, int blockY, int blockZ, Material expected, Material found) {
                if (expected == null || found == null) {
                    throw new NullPointerException();
                }
                this.blockX = blockX;
                this.blockY = blockY;
                this.blockZ = blockZ;
                this.expected = expected;
                this.found = found;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Difference that = (Difference) o;

                if (blockX != that.blockX) return false;
                if (blockY != that.blockY) return false;
                if (blockZ != that.blockZ) return false;
                if (expected != that.expected) return false;
                return found == that.found;

            }

            @Override
            public int hashCode() {
                int result = blockX;
                result = 31 * result + blockY;
                result = 31 * result + blockZ;
                result = 31 * result + expected.hashCode();
                result = 31 * result + found.hashCode();
                return result;
            }

            @Override
            public String toString() {
                return String.format("Diff(x=%d, y=%d, z=%d, expected=%s, found=%s)", blockX, blockY, blockZ, expected.toString(), found.toString());
            }
        }
    }
}

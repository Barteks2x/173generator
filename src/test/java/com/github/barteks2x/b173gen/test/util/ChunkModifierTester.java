package com.github.barteks2x.b173gen.test.util;

import com.github.barteks2x.b173gen.test.fakeimpl.BukkitWorldStub;
import org.bukkit.Material;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class ChunkModifierTester {

    //these are impossible to get right due to differences in the way MC works
    private static final boolean IGNORE_LIQUID_TYPE_DIFFERENCE = true;
    private final Set<TestResults.Difference> expectedDiff;

    private IChunkSource chunksToTest;
    private IChunkSource pregeneratedCorrectChunks;
    private List<RegionChunkPosition> chunksToGenerateList;
    private int maxDiff;

    private ChunkModifierTester(IChunkSource toTest, IChunkSource pregenerated, List<RegionChunkPosition> toGenerate, Set<TestResults.Difference> expectedDiff, int maxDiff) {
        this.chunksToTest = toTest;
        this.pregeneratedCorrectChunks = pregenerated;
        this.chunksToGenerateList = toGenerate;
        this.maxDiff = maxDiff;
        this.expectedDiff = expectedDiff;
    }

    public static ChunkModifierTester create(final IChunkModifier chunkModifier, File pregeneratedRegionsToModify, File correctRegions, File chunksList, File expectedDiff, int maxDiff) throws IOException, DataFormatException {
        if(correctRegions == null || !correctRegions.exists()) {
            throw new IllegalArgumentException("Correct regions directory " + correctRegions + " doesn't exist");
        }
        IChunkSource correctChunks = PreloadedChunkSource.createPreloadedChunksFromMcRegions(correctRegions);
        final IChunkSource pregenerated = PreloadedChunkSource.createPreloadedChunksFromMcRegions(pregeneratedRegionsToModify);
        IChunkSource modifiedSource = new IChunkSource() {
            @Override
            public void loadChunkData(int x, int z) {
                pregenerated.loadChunkData(x, z);
                chunkModifier.modifyChunk(getChunkData(x, z));
            }

            @Override
            public ChunkData getChunkData(int x, int z) {
                return pregenerated.getChunkData(x, z);
            }
        };
        List<RegionChunkPosition> chunkPosList = readChunkList(chunksList);
        Set<TestResults.Difference> expectedDiffSet = readExpectedDiff(expectedDiff);
        return new ChunkModifierTester(modifiedSource, correctChunks, chunkPosList, expectedDiffSet, maxDiff);
    }

    private static Set<TestResults.Difference> readExpectedDiff(File file) throws FileNotFoundException {
        if(file == null || !file.exists()) {
            return new HashSet<>();
        }
        Set<TestResults.Difference> diffList = new HashSet<>();
        Scanner scanner = new Scanner(file);
        //               x  =  - digits
        Pattern xRegex = Pattern.compile("x\\=\\-?\\d+");
        Pattern yRegex = Pattern.compile("y\\=\\-?\\d+");
        Pattern zRegex = Pattern.compile("z\\=\\-?\\d+");
        Pattern expectedRegex = Pattern.compile("expected\\=\\w+,");
        Pattern foundRegex = Pattern.compile("found\\=\\w+\\)");
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.startsWith("File ")) {
                File toRead = new File(file.getParentFile(), line.substring("File ".length()));
                Set<TestResults.Difference> fromFile = readExpectedDiff(toRead);
                diffList.addAll(fromFile);
                continue;
            }
            Matcher matcher = xRegex.matcher(line);
            if(!matcher.find()) {
                throw new RuntimeException("Invalid data: " + line);
            }
            String x = matcher.group().substring(2);

            matcher = yRegex.matcher(line);
            if(!matcher.find()) {
                throw new RuntimeException("Invalid data: " + line);
            }
            String y = matcher.group().substring(2);

            matcher = zRegex.matcher(line);
            if(!matcher.find()) {
                throw new RuntimeException("Invalid data: " + line);
            }
            String z = matcher.group().substring(2);

            matcher = expectedRegex.matcher(line);
            if(!matcher.find()) {
                throw new RuntimeException("Invalid data: " + line);
            }
            String expected = matcher.group();
            expected = expected.substring("expected=".length(), expected.length() - 1);

            matcher = foundRegex.matcher(line);
            if(!matcher.find()) {
                throw new RuntimeException("Invalid data: " + line);
            }
            String found = matcher.group();
            found = found.substring("found=".length(), found.length() - 1);

            diffList.add(new ChunkModifierTester.TestResults.Difference(
                    Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z), Material.getMaterial(expected), Material.getMaterial(found)));
        }
        scanner.close();
        return diffList;
    }

    private static List<RegionChunkPosition> readChunkList(File chunksList) throws FileNotFoundException {
        List<RegionChunkPosition> list = new ArrayList<>();
        Scanner scanner = new Scanner(chunksList);
        while(scanner.hasNext()) {
            int x = scanner.nextInt();
            int z = scanner.nextInt();
            list.add(RegionChunkPosition.fromChunkPos(x, z));
        }
        scanner.close();
        return list;
    }

    public TestResults start() throws IOException, DataFormatException {
        Map<ChunkData, ChunkData> savedToGeneratedMapping = generateChunks(this.chunksToGenerateList, this.pregeneratedCorrectChunks, chunksToTest);
        return compareChunks(savedToGeneratedMapping);
    }

    public Map<ChunkData, ChunkData> generateChunks(List<RegionChunkPosition> chunkList, IChunkSource pregeneratedCorrectChunks, IChunkSource chunksToTest) throws FileNotFoundException {
        Map<ChunkData, ChunkData> savedToGeneratedOut = new HashMap<ChunkData, ChunkData>();
        for(RegionChunkPosition pos : chunkList) {
            chunksToTest.loadChunkData(pos.getChunkX(), pos.getChunkZ());
            ChunkData generatedChunk = chunksToTest.getChunkData(pos.getChunkX(), pos.getChunkZ());

            pregeneratedCorrectChunks.loadChunkData(pos.getChunkX(), pos.getChunkZ());
            ChunkData correctChunk = pregeneratedCorrectChunks.getChunkData(pos.getChunkX(), pos.getChunkZ());

            savedToGeneratedOut.put(correctChunk, generatedChunk);
        }
        return savedToGeneratedOut;
    }

    private TestResults compareChunks(Map<ChunkData, ChunkData> savedToGenerated) {
        TestResults results = new TestResults(this.maxDiff);
        results.setExpected(expectedDiff);

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

    public void setupWorld(BukkitWorldStub world) {
        world.setChunkSource(this.chunksToTest);
    }

    public static class TestResults {
        private Difference[] differences;
        private int nextIndex = 0;
        private boolean overflow = false;
        private Set<Difference> expected;

        public TestResults(int maxDifferences) {
            this.differences = new Difference[maxDifferences];
        }

        public void addDifference(int blockX, int blockY, int blockZ, Material expected, Material found) {
            Difference diff = new Difference(blockX, blockY, blockZ, expected, found);
            if(this.expected.contains(diff)) {
                return;
            }
            if (nextIndex >= differences.length) {
                overflow = true;
                nextIndex++;
                return;
            }
            differences[nextIndex] = diff;
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
            if (!(obj instanceof TestResults)) {
                return false;
            }
            TestResults other = (TestResults) obj;
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

        public void setExpected(Set<Difference> expected) {
            this.expected = expected;
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

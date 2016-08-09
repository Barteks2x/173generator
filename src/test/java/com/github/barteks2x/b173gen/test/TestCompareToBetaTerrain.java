package com.github.barteks2x.b173gen.test;

import com.github.barteks2x.b173gen.Generator;
import com.github.barteks2x.b173gen.biome.BiomeOld;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.generator.ChunkProviderGenerate;
import com.github.barteks2x.b173gen.oldgen.WorldChunkManagerOld;
import com.github.barteks2x.b173gen.test.fakeimpl.BukkitWorldStub;
import com.github.barteks2x.b173gen.test.util.ChunkData;
import com.github.barteks2x.b173gen.test.util.ChunkModifierTester;
import com.github.barteks2x.b173gen.test.util.IChunkModifier;
import org.bukkit.Chunk;
import org.bukkit.configuration.Configuration;
import org.bukkit.generator.BlockPopulator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCompareToBetaTerrain {

    private static final int MAX_DIFF = 1000;
    private ChunkProviderGenerate generator;
    private BukkitWorldStub world;
    private File chunksFile;
    private WorldChunkManagerOld wcm;
    private List<BlockPopulator> populators;

    @Before
    public void init() {
        Generator plugin = mock(Generator.class);
        generator = new ChunkProviderGenerate(WorldConfig.emptyConfig("world"), plugin);
        world = new BukkitWorldStub();
        world.setSeed("test".hashCode());
        world.setName("world");
        wcm = new WorldChunkManagerOld(this.world.getSeed());
        generator.init(this.world, wcm);
        populators = generator.getDefaultPopulators(world);
        chunksFile = res("chunks.txt");

        Configuration cfg = mock(Configuration.class);
        when(cfg.getBoolean("global.experimental.biomesExperimental", true)).thenReturn(false);
        Generator.log = mock(Logger.class);
        BiomeOld.init(cfg);
    }

    @Test
    public void test01Terrain() throws IOException, DataFormatException {
        doTest(new ChunkSourceTerrain(), "01_terrain", null);
    }

    @Test
    public void test02BiomeSurface() throws IOException, DataFormatException {
        doTest(new ChunkSourceBiomes(), "02_biome_surface", "01_terrain");
    }

    @Test
    public void test03Caves() throws IOException, DataFormatException {
        doTest(new ChunkSourceCaves(), "03_caves", "02_biome_surface");
    }

    @Test
    public void test04PopulationWaterLakes() throws IOException, DataFormatException {
        doTest(new ChunkSourcePopulation(getPopulatorsFor(populators, "WaterLakes")), "04_population_water_lakes", "03_caves");
    }

    @Test
    public void test05PopulationLavaLakes() throws IOException, DataFormatException {
        doTest(new ChunkSourcePopulation(getPopulatorsFor(populators, "LavaLakes")), "05_population_lava_lakes", "03_caves");
    }

    @Test
    public void test06PupulationDungeons() throws IOException, DataFormatException {
        doTest(new ChunkSourcePopulation(getPopulatorsFor(populators, "Dungeons")), "06_population_dungeons", "03_caves");
    }

    private void doTest(IChunkModifier chunkModifier, String name, String dependency) throws IOException, DataFormatException {
        IChunkModifier chunkSource = chunkModifier;
        ChunkModifierTester compare = ChunkModifierTester.create(chunkSource, dependency == null ? null : res(dependency), res(name), chunksFile, expectedDiffFile(name), MAX_DIFF);
        compare.setupWorld(world);

        ChunkModifierTester.TestResults actualResults = compare.start();
        ChunkModifierTester.TestResults expectedRegults = new ChunkModifierTester.TestResults(MAX_DIFF);

        assertEquals("The generated world must be equal to saved world", expectedRegults, actualResults);
    }

    private static File expectedDiffFile(String testName) {
        return res(testName + "_expected_diff.txt");
    }

    private static List<BlockPopulator> getPopulatorsFor(List<BlockPopulator> allPopulators, String lastPopulator) {
        List<BlockPopulator> newList = new ArrayList<>();
        for (BlockPopulator populator : allPopulators) {
            newList.add(populator);
            if (populator.toString().equals(lastPopulator)) {
                //add the last exit populator
                newList.add(allPopulators.get(allPopulators.size() - 1));
                return newList;
            }
        }
        throw new IllegalArgumentException("Populator " + lastPopulator + " not found.");
    }

    private static File res(String name) {
        URL url = TestCompareToBetaTerrain.class.getResource("/" + name);
        if(url == null) {
            return null;
        }
        return new File(url.getFile());
    }

    private class ChunkSourceTerrain implements IChunkModifier {
        @Override
        public void modifyChunk(ChunkData existingChunk) {
            int x = existingChunk.getX();
            int z = existingChunk.getZ();
            wcm.getBiomeBlock(null, x * 16, z * 16, 16, 16);
            generator.initRand(x, z);
            generator.generateTerrain(x, z, existingChunk);
        }
    }

    private class ChunkSourceBiomes implements IChunkModifier {
        @Override
        public void modifyChunk(ChunkData existingChunk) {
            int x = existingChunk.getX();
            int z = existingChunk.getZ();
            generator.initRand(x, z);
            generator.replaceBlocksForBiome(x, z, existingChunk, wcm.getBiomeBlock(null, x * 16, z * 16, 16, 16));
        }
    }

    private class ChunkSourceCaves implements IChunkModifier {
        @Override
        public void modifyChunk(ChunkData existingChunk) {
            int x = existingChunk.getX();
            int z = existingChunk.getZ();
            generator.generateCaves(x, z, existingChunk);
        }
    }

    private class ChunkSourcePopulation implements IChunkModifier {
        private List<BlockPopulator> populators;

        public ChunkSourcePopulation(List<BlockPopulator> populators) {
            this.populators = populators;
        }

        @Override
        public void modifyChunk(ChunkData existingChunk) {
            int x = existingChunk.getX();
            int z = existingChunk.getZ();
            int dx, dz;
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0:
                        dx = 0;
                        dz = 0;
                        break;
                    case 1:
                        dx = -1;
                        dz = 0;
                        break;
                    case 2:
                        dx = -1;
                        dz = -1;
                        break;
                    case 3:
                        dx = 0;
                        dz = -1;
                        break;
                    default:
                        throw new AssertionError();
                }
                if (getChunkData(x + dx, z + dz) != null && !getChunkData(x + dx, z + dz).isPopulated() &&
                        getChunkData(x + dx, z + dz + 1) != null &&
                        getChunkData(x + dx + 1, z + dz + 1) != null &&
                        getChunkData(x + dx + 1, z + dz) != null) {

                    Chunk fakeChunk = mock(Chunk.class);
                    when(fakeChunk.getX()).thenReturn(x + dx);
                    when(fakeChunk.getZ()).thenReturn(z + dz);
                    for (BlockPopulator populator : populators) {
                        populator.populate(world, null, fakeChunk);
                    }
                    getChunkData(x + dx, z + dz).setPopulated(true);
                }
            }
        }

        private ChunkData getChunkData(int x, int z) {
            return world.getChunkDataAt(x, z);
        }
    }
}

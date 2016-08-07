package com.github.barteks2x.b173gen.test;

import com.github.barteks2x.b173gen.Generator;
import com.github.barteks2x.b173gen.biome.BiomeOld;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.generator.ChunkProviderGenerate;
import com.github.barteks2x.b173gen.oldgen.WorldChunkManagerOld;
import com.github.barteks2x.b173gen.test.fakeimpl.BukkitWorldStub;
import com.github.barteks2x.b173gen.test.util.ChunkData;
import com.github.barteks2x.b173gen.test.util.IGeneratorChunkSource;
import com.github.barteks2x.b173gen.test.util.RegionChunkPosition;
import com.github.barteks2x.b173gen.test.util.WorldCompare;
import org.bukkit.Chunk;
import org.bukkit.configuration.Configuration;
import org.bukkit.generator.BlockPopulator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCompareToBetaTerrain {

    private static final int MAX_DIFF = 100;
    private ChunkProviderGenerate generator;
    private BukkitWorldStub world;
    private File chunksFile;
    private WorldChunkManagerOld wcm;
    private BlockPopulator populator;

    @Before
    public void init() {
        Generator plugin = mock(Generator.class);
        generator = new ChunkProviderGenerate(WorldConfig.emptyConfig("world"), plugin);
        world = new BukkitWorldStub();
        world.setSeed("test".hashCode());
        world.setName("world");
        wcm = new WorldChunkManagerOld(this.world.getSeed());
        generator.init(this.world, wcm);
        populator = generator.getDefaultPopulators(world).get(0);
        chunksFile = new File(this.getClass().getResource("/CHUNKS.txt").getFile());

        Configuration cfg = mock(Configuration.class);
        when(cfg.getBoolean("global.experimental.biomesExperimental", true)).thenReturn(false);
        Generator.log = mock(Logger.class);
        BiomeOld.init(cfg);
    }

    @Test
    public void test01Terrain() throws IOException, DataFormatException {
        File regionDir = new File(this.getClass().getResource("/01_TERRAIN").getFile());
        IGeneratorChunkSource chunkSource = new ChunkSourceTerrain();
        WorldCompare compare = new WorldCompare(chunkSource, regionDir, chunksFile, MAX_DIFF);

        WorldCompare.CompareResults actualResults = compare.start();
        WorldCompare.CompareResults expectedRegults = new WorldCompare.CompareResults(MAX_DIFF);

        assertEquals("The generated world must be equal to saved world", expectedRegults, actualResults);
    }

    @Test
    public void test02BiomeSurface() throws IOException, DataFormatException {
        File regionDir = new File(this.getClass().getResource("/02_BIOME_SURFACE").getFile());
        IGeneratorChunkSource chunkSource = new ChunkSourceBiomes();
        WorldCompare compare = new WorldCompare(chunkSource, regionDir, chunksFile, MAX_DIFF);

        WorldCompare.CompareResults actualResults = compare.start();
        WorldCompare.CompareResults expectedRegults = new WorldCompare.CompareResults(MAX_DIFF);

        assertEquals("The generated world must be equal to saved world", expectedRegults, actualResults);
    }

    @Test
    public void test03Caves() throws IOException, DataFormatException {
        File regionDir = new File(this.getClass().getResource("/03_CAVES").getFile());
        IGeneratorChunkSource chunkSource = new ChunkSourceCaves();
        WorldCompare compare = new WorldCompare(chunkSource, regionDir, chunksFile, MAX_DIFF);

        WorldCompare.CompareResults actualResults = compare.start();
        WorldCompare.CompareResults expectedRegults = new WorldCompare.CompareResults(MAX_DIFF);

        assertEquals("The generated world must be equal to saved world", expectedRegults, actualResults);
    }

    @Test
    public void test04Population() throws IOException, DataFormatException {
        File regionDir = new File(this.getClass().getResource("/04_POPULATION").getFile());
        IGeneratorChunkSource chunkSource = new ChunkSourcePopulation();
        world.setChunkSource(chunkSource);
        WorldCompare compare = new WorldCompare(chunkSource, regionDir, chunksFile, MAX_DIFF);

        WorldCompare.CompareResults actualResults = compare.start();
        WorldCompare.CompareResults expectedRegults = new WorldCompare.CompareResults(MAX_DIFF);

        assertEquals("The generated world must be equal to saved world", expectedRegults, actualResults);
    }

    private class ChunkSourceTerrain implements IGeneratorChunkSource {
        private Map<RegionChunkPosition, ChunkData> map = new HashMap<RegionChunkPosition, ChunkData>();

        @Override
        public void loadChunkData(int x, int z) {
            ChunkData data = ChunkData.empty(x, z);
            wcm.getBiomeBlock(null, x * 16, z * 16, 16, 16);
            generator.initRand(x, z);
            generator.generateTerrain(x, z, data);
            map.put(RegionChunkPosition.fromChunkPos(x, z), data);
        }

        @Override
        public ChunkData getChunkData(int x, int z) {
            return map.get(RegionChunkPosition.fromChunkPos(x, z));
        }
    }

    private class ChunkSourceBiomes extends ChunkSourceTerrain {
        @Override
        public void loadChunkData(int x, int z) {
            super.loadChunkData(x, z);
            ChunkData data = getChunkData(x, z);
            generator.replaceBlocksForBiome(x, z, data, wcm.getBiomeBlock(null, x * 16, z * 16, 16, 16));
        }
    }

    private class ChunkSourceCaves extends ChunkSourceBiomes {
        @Override
        public void loadChunkData(int x, int z) {
            super.loadChunkData(x, z);
            ChunkData data = getChunkData(x, z);
            generator.generateCaves(x, z, data);
        }
    }

    private class ChunkSourcePopulation extends ChunkSourceCaves {
        @Override
        public void loadChunkData(int x, int z) {
            super.loadChunkData(x, z);
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
                    populator.populate(world, null, fakeChunk);
                    getChunkData(x + dx, z + dz).setPopulated(true);
                }
            }
        }
    }
}

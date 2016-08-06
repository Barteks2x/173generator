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
import org.bukkit.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

public class TestCompareToBetaTerrain {

    private static final int MAX_DIFF = 100;
    private ChunkProviderGenerate generator;
    private BukkitWorldStub world;
    private File chunksFile;
    private WorldChunkManagerOld wcm;

    @Before public void init() {
        generator = new ChunkProviderGenerate(WorldConfig.emptyConfig("world"), null);
        world = new BukkitWorldStub();
        world.setSeed("test".hashCode());
        world.setName("world");
        wcm = new WorldChunkManagerOld(this.world.getSeed());
        generator.init(this.world, wcm);
        chunksFile = new File(this.getClass().getResource("/CHUNKS.txt").getFile());

        Configuration cfg = Mockito.mock(Configuration.class);
        when(cfg.getBoolean("global.experimental.biomesExperimental", true)).thenReturn(false);
        Generator.log = Mockito.mock(Logger.class);
        BiomeOld.init(cfg);
    }

    @Test public void test01Terrain() throws IOException, DataFormatException {
        File regionDir = new File(this.getClass().getResource("/01_TERRAIN").getFile());
        IGeneratorChunkSource chunkSource = new ChunkSourceTerrain();
        WorldCompare compare = new WorldCompare(chunkSource, regionDir, chunksFile, MAX_DIFF);

        WorldCompare.CompareResults actualResults = compare.start();
        WorldCompare.CompareResults expectedRegults = new WorldCompare.CompareResults(MAX_DIFF);

        assertEquals("The generated world must be equal to saved world", expectedRegults, actualResults);
    }

    @Test public void test02BiomeSurface() throws IOException, DataFormatException {
        File regionDir = new File(this.getClass().getResource("/02_BIOME_SURFACE").getFile());
        IGeneratorChunkSource chunkSource = new ChunkSourceBiomes();
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
            generator.replaceBlocksForBiome(x, z, data, wcm.getBiomeBlock(null, x*16, z*16, 16, 16));
        }
    }
}

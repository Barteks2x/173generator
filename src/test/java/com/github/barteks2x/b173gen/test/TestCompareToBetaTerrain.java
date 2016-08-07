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
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.Blocks;
import net.minecraft.server.v1_10_R1.DispenserRegistry;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.generator.BlockPopulator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        chunksFile = new File(this.getClass().getResource("/chunks.txt").getFile());

        Configuration cfg = mock(Configuration.class);
        when(cfg.getBoolean("global.experimental.biomesExperimental", true)).thenReturn(false);
        Generator.log = mock(Logger.class);
        BiomeOld.init(cfg);
    }

    @Test
    public void test01Terrain() throws IOException, DataFormatException {
        if(true) return;
        File regionDir = new File(this.getClass().getResource("/01_terrain").getFile());
        IGeneratorChunkSource chunkSource = new ChunkSourceTerrain();
        WorldCompare compare = new WorldCompare(chunkSource, regionDir, chunksFile, MAX_DIFF);

        WorldCompare.CompareResults actualResults = compare.start();
        WorldCompare.CompareResults expectedRegults = new WorldCompare.CompareResults(MAX_DIFF);

        assertEquals("The generated world must be equal to saved world", expectedRegults, actualResults);
    }

    @Test
    public void test02BiomeSurface() throws IOException, DataFormatException {if(true) return;
        File regionDir = new File(this.getClass().getResource("/02_biome_surface").getFile());
        IGeneratorChunkSource chunkSource = new ChunkSourceBiomes();
        WorldCompare compare = new WorldCompare(chunkSource, regionDir, chunksFile, MAX_DIFF);

        WorldCompare.CompareResults actualResults = compare.start();
        WorldCompare.CompareResults expectedRegults = new WorldCompare.CompareResults(MAX_DIFF);

        assertEquals("The generated world must be equal to saved world", expectedRegults, actualResults);
    }

    @Test
    public void test03Caves() throws IOException, DataFormatException {if(true) return;
        File regionDir = new File(this.getClass().getResource("/03_caves").getFile());
        IGeneratorChunkSource chunkSource = new ChunkSourceCaves();
        WorldCompare compare = new WorldCompare(chunkSource, regionDir, chunksFile, MAX_DIFF);

        WorldCompare.CompareResults actualResults = compare.start();
        WorldCompare.CompareResults expectedRegults = new WorldCompare.CompareResults(MAX_DIFF);

        assertEquals("The generated world must be equal to saved world", expectedRegults, actualResults);
    }

    @Test
    public void test04PopulationWaterLakes() throws IOException, DataFormatException {
        File regionDir = new File(this.getClass().getResource("/04_population_water_lakes").getFile());
        IGeneratorChunkSource chunkSource = new ChunkSourcePopulation(getPopulatorsFor(populators, "WaterLakes"));
        world.setChunkSource(chunkSource);
        WorldCompare compare = new WorldCompare(chunkSource, regionDir, chunksFile, MAX_DIFF);

        WorldCompare.CompareResults expectedRegults = readExpectedResults(4);
        WorldCompare.CompareResults actualResults = compare.start();

        assertEquals("The generated world must be equal to saved world", expectedRegults, actualResults);
    }

    @Test
    public void testXXPopulation() throws IOException, DataFormatException {
        if(true) {
            //remove it for now, world not fully implemented
            return;
        }
        File regionDir = new File(this.getClass().getResource("/XX_population").getFile());
        IGeneratorChunkSource chunkSource = new ChunkSourcePopulation(populators);
        world.setChunkSource(chunkSource);
        WorldCompare compare = new WorldCompare(chunkSource, regionDir, chunksFile, MAX_DIFF);

        WorldCompare.CompareResults actualResults = compare.start();
        WorldCompare.CompareResults expectedRegults = new WorldCompare.CompareResults(MAX_DIFF);

        assertEquals("The generated world must be equal to saved world", expectedRegults, actualResults);
    }

    private WorldCompare.CompareResults readExpectedResults(int test) throws FileNotFoundException {
        if(test <= 0 || test >= 100) {
            throw new IllegalArgumentException("Test number must be between 1 and 99");
        }
        String filename = String.format("/%02d_expected_diff.txt", test);
        WorldCompare.CompareResults diffList = new WorldCompare.CompareResults(MAX_DIFF);
        Scanner scanner = new Scanner(new File(this.getClass().getResource(filename).getFile()));
        //               x  =  - digits
        Pattern xRegex = Pattern.compile("x\\=\\-?\\d+");
        Pattern yRegex = Pattern.compile("y\\=\\-?\\d+");
        Pattern zRegex = Pattern.compile("z\\=\\-?\\d+");
        Pattern expectedRegex = Pattern.compile("expected\\=\\w+,");
        Pattern foundRegex = Pattern.compile("found\\=\\w+\\)");
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
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

            diffList.addDifference(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z), Material.getMaterial(expected), Material.getMaterial(found));
        }
        scanner.close();
        return diffList;
    }

    private static List<BlockPopulator> getPopulatorsFor(List<BlockPopulator> allPopulators, String lastPopulator) {
        List<BlockPopulator> newList = new ArrayList<>();
        for(BlockPopulator populator : allPopulators) {
            newList.add(populator);
            if(populator.toString().equals(lastPopulator)) {
                //add the last exit populator
                newList.add(allPopulators.get(allPopulators.size() - 1));
                return newList;
            }
        }
        throw new IllegalArgumentException("Populator " + lastPopulator  + " not found.");
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
        private List<BlockPopulator> populators;

        public ChunkSourcePopulation(List<BlockPopulator> populators) {
            this.populators = populators;
        }

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
                    for(BlockPopulator populator: populators) {
                        populator.populate(world, null, fakeChunk);
                    }
                    getChunkData(x + dx, z + dz).setPopulated(true);
                }
            }
        }
    }
}

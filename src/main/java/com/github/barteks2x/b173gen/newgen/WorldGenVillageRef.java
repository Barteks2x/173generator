package com.github.barteks2x.b173gen.newgen;

import static com.github.barteks2x.b173gen.reflection.ReflectionHelper.*;
import static com.github.barteks2x.b173gen.reflection.Util.NMSClassHelper.*;
import java.util.Random;
import org.bukkit.World;

public class WorldGenVillageRef extends StructureGeneratorRef {

    protected final Object WORLD_GEN_VILLAGE;
    public WorldGenVillageRef() {
        this.WORLD_GEN_VILLAGE = newInstance(WorldGenVillage, null, null);
    }

    @Override
    public boolean generate(World world, Random random, int i, int j) {
        return WorldGenVillage_generate_populator(WORLD_GEN_VILLAGE, world, rand, i, j);
    }

    @Override
    public void generate(World world, int x, int z, Object[] blocks) {
        WorldGenVillage_generate_generator(WORLD_GEN_VILLAGE, world, x, z, blocks);
    }

    @Override
    public void generate(World world, int x, int z, byte[] blocks) {
        WorldGenVillage_generate_generator(WORLD_GEN_VILLAGE, world, x, z, blocks);
    }

    @Override
    protected void generate(World world, int i1, int j1, int i, int j, byte[] abyte0) {}
}

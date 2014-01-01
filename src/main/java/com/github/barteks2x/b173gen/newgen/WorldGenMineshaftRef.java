package com.github.barteks2x.b173gen.newgen;

import static com.github.barteks2x.b173gen.reflection.ReflectionHelper.*;
import static com.github.barteks2x.b173gen.reflection.Util.NMSClassHelper.*;
import java.util.Random;
import org.bukkit.World;

public class WorldGenMineshaftRef extends StructureGeneratorRef {

    protected final Object WORLD_GEN_MINESHAFT;
    public WorldGenMineshaftRef() {
        this.WORLD_GEN_MINESHAFT = newInstance(WorldGenMineshaft, null, null);
    }

    public boolean generate(World world, Random random, int i, int j) {
        return WorldGenMineshaft_generate_populator(WORLD_GEN_MINESHAFT, world, random, i, j);
    }

    public void generate(World world, int x, int z, Object[] blocks) {
        WorldGenMineshaft_generate_generator(WORLD_GEN_MINESHAFT, world, x, z, blocks);
    }

    public void generate(World world, int x, int z, byte[] blocks) {
        WorldGenMineshaft_generate_generator(WORLD_GEN_MINESHAFT, world, x, z, blocks);
    }

    @Override
    protected void generate(World world, int i1, int j1, int i, int j, byte[] abyte0) {}
}

package com.github.barteks2x.b173gen.newgen;

import static com.github.barteks2x.b173gen.reflection.ReflectionHelper.*;
import static com.github.barteks2x.b173gen.reflection.Util.NMSClassHelper.*;
import java.util.Random;
import org.bukkit.World;

public class WorldGenStrongholdRef extends StructureGeneratorRef {

    protected final Object WORLD_GEN_STRONGHOLD;
    public WorldGenStrongholdRef() {
        this.WORLD_GEN_STRONGHOLD = newInstance(WorldGenStronghold, null, null);
    }

    public boolean generate(World world, Random random, int i, int j) {
        return WorldGenStronghold_generate_populator(WORLD_GEN_STRONGHOLD, world, random, i, j);
    }

    public void generate(World world, int x, int z, Object[] blocks) {
        WorldGenStronghold_generate_generator(WORLD_GEN_STRONGHOLD, world, x, z, blocks);
    }

    public void generate(World world, int x, int z, byte[] blocks) {
        WorldGenStronghold_generate_generator(WORLD_GEN_STRONGHOLD, world, x, z, blocks);
    }

    @Override
    protected void generate(World world, int i1, int j1, int i, int j, byte[] abyte0) {}
}

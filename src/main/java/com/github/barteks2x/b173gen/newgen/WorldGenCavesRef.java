package com.github.barteks2x.b173gen.newgen;

import static com.github.barteks2x.b173gen.reflection.ReflectionHelper.*;
import static com.github.barteks2x.b173gen.reflection.Util.NMSClassHelper.*;
import org.bukkit.World;

public class WorldGenCavesRef extends WorldGenBaseRef {

    protected final Object WORLD_GEN_CAVES;
    public WorldGenCavesRef() {
        this.WORLD_GEN_CAVES = newInstance(WorldGenCaves, null, null);
    }

    public void generate(World world, int x, int z, Object[] blocks) {
        WorldGenCaves_generate(WORLD_GEN_CAVES, world, x, z, blocks);
    }

    public void generate(World world, int x, int z, byte[] blocks) {
        WorldGenCaves_generate(WORLD_GEN_CAVES, world, x, z, blocks);
    }

    @Override
    protected void generate(World world, int i1, int j1, int i, int j, byte[] abyte0) {}
}

package com.github.barteks2x.b173gen.newgen;

import static com.github.barteks2x.b173gen.reflection.Util.NMSClassHelper.*;
import static com.github.barteks2x.b173gen.reflection.ReflectionHelper.*;
import org.bukkit.World;

public class WorldGenCanyonRef extends WorldGenBaseRef {

    protected final Object WORLD_GEN_CANYON;
    public WorldGenCanyonRef() {
        this.WORLD_GEN_CANYON = newInstance(WorldGenCanyon, null, null);
    }

    public void generate(World world, int x, int z, Object[] blocks) {
        WorldGenCanyon_generate(WORLD_GEN_CANYON, world, x, z, blocks);
    }

    public void generate(World world, int x, int z, byte[] blocks) {
        WorldGenCanyon_generate(WORLD_GEN_CANYON, world, x, z, blocks);
    }
    
    protected void generate(World world, int i1, int j1, int i, int j, byte[] abyte0) {}
}

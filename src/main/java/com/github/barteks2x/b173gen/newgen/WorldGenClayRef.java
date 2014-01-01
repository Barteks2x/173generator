package com.github.barteks2x.b173gen.newgen;

import static com.github.barteks2x.b173gen.reflection.ReflectionHelper.*;
import static com.github.barteks2x.b173gen.reflection.Util.NMSClassHelper.*;
import java.util.Random;
import org.bukkit.World;

public class WorldGenClayRef implements WorldGeneratorRef {

    protected final Object WORLD_GEN_CLAY;
    public WorldGenClayRef(int size) {
        this.WORLD_GEN_CLAY = newInstance(WorldGenClay, new Object[]{size}, new Class<?>[]{int.class});
    }

    public boolean generate(World world, Random rand, int x, int y, int z) {
        return WorldGenClay_generate(WORLD_GEN_CLAY, world, rand, x, y, z);
    }

    public void scale(double d0, double d1, double d2) {}
}

package com.github.barteks2x.b173gen.newgen;

import static com.github.barteks2x.b173gen.reflection.ReflectionHelper.*;
import static com.github.barteks2x.b173gen.reflection.Util.NMSClassHelper.*;
import java.util.Random;
import org.bukkit.World;

public class WorldGenDungeonRef implements WorldGeneratorRef {

    protected final Object WORLD_GEN_DUNGEON;
    public WorldGenDungeonRef() {
        this.WORLD_GEN_DUNGEON = newInstance(WorldGenDungeons, null, null);
    }

    public boolean generate(World world, Random rand, int x, int y, int z) {
        return WorldGenDungeons_generate(WORLD_GEN_DUNGEON, world, rand, x, y, z);
    }

    public void scale(double d0, double d1, double d2) {}
}

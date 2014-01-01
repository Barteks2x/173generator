package com.github.barteks2x.b173gen.newgen;

import static com.github.barteks2x.b173gen.reflection.ReflectionHelper.*;
import static com.github.barteks2x.b173gen.reflection.Util.NMSClassHelper.*;
import java.util.Random;
import org.bukkit.World;

public class WorldGenLakesRef implements WorldGeneratorRef {

    protected final Object WORLD_GEN_LAKES;
    public WorldGenLakesRef(int id) {
        this.WORLD_GEN_LAKES = newInstance(WorldGenLakes, new Object[]{id}, new Class<?>[]{int.class});
        if(this.WORLD_GEN_LAKES == null){
            //this.WORLD_GEN_LAKES = newInstance(WorldGenLakes, new Object[]{id}, new Class<?>[]{int.class});
        }
    }

    public boolean generate(World world, Random rand, int x, int y, int z) {
        return WorldGenLakes_generate(WORLD_GEN_LAKES, world, rand, x, y, z);
    }

    public void scale(double d0, double d1, double d2) {}
}

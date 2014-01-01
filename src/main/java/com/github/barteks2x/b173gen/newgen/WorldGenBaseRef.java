package com.github.barteks2x.b173gen.newgen;

import com.github.barteks2x.b173gen.WorldGenBaseOld;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import org.bukkit.World;

public abstract class WorldGenBaseRef extends WorldGenBaseOld {
    public abstract void generate(World world, int x, int z, Object[] blocks);
    @Override
    public abstract void generate(World world, int x, int z, byte[] blocks);
}

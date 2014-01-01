package com.github.barteks2x.b173gen.newgen;

import java.util.Random;
import org.bukkit.World;

public abstract class StructureGeneratorRef extends WorldGenBaseRef {
    public abstract boolean generate(World world, Random random, int i, int j);
}

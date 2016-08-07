package com.github.barteks2x.b173gen.generator;

import org.bukkit.World;

public interface IPopulator {
    void populate(World world, PopulatorState state);
}

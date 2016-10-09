package com.github.barteks2x.b173gen.generator;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class PopulatorProxy extends BlockPopulator {

    private int lastKnownX = Integer.MIN_VALUE, lastKnownZ = Integer.MAX_VALUE;

    protected final PopulatorManager stateManager;
    private IPopulator populator;
    private String name;

    public PopulatorProxy(PopulatorManager stateManager, IPopulator populator, String name) {
        this.stateManager = stateManager;
        this.populator = populator;
        this.name = name;
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        if (chunk.getX() == lastKnownX && chunk.getZ() == lastKnownZ) {
            return;
        }
        lastKnownX = chunk.getX();
        lastKnownZ = chunk.getZ();
        populator.populate(stateManager.getWorld(), stateManager.getStateFor(chunk.getX(), chunk.getZ()));
    }

    @Override
    public String toString() {
        return this.name;
    }
}

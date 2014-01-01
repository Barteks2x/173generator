package com.github.barteks2x.b173gen.generator;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;

public class BlockPopulator extends org.bukkit.generator.BlockPopulator {

    private ChunkProviderGenerate cpg;

    public BlockPopulator(ChunkProviderGenerate cpg) {
        this.cpg = cpg;
    }

    @Override
    public void populate(World world, Random rnd, Chunk chunk) {
        this.cpg.populate(chunk.getX(), chunk.getZ());
    }
}

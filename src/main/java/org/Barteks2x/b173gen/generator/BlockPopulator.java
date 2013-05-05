package org.Barteks2x.b173gen.generator;

import java.util.Random;

import org.bukkit.World;

public class BlockPopulator extends org.bukkit.generator.BlockPopulator {
	private ChunkProviderGenerate cpg;

	public BlockPopulator(ChunkProviderGenerate cpg) {
		this.cpg = cpg;
	}

	@Override
	public void populate(World world, Random rnd, org.bukkit.Chunk chunk) {
		this.cpg.getChunkAt(cpg, chunk.getX(), chunk.getZ());
	}
}
package com.github.barteks2x.b173gen;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;

/**
 * Simple world interface for setting and getting blocks
 */
public interface ISimpleWorld {
	Material getType(int x, int y, int z);

	void setType(int x, int y, int z, Material material);

	boolean isEmpty(int x, int y, int z);

	int getBlockLight(int x, int y, int z);

	int getSkyLight(int x, int y, int z);

	BlockState getBlockState(int x, int y, int z);

	void setType(int x, int y, int z, Material type, MaterialData data);

	int getHighestBlockYAt(int x, int z);
}

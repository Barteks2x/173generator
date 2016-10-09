package com.github.barteks2x.b173gen;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;

public class BukkitSimpleWorldImpl implements ISimpleWorld {
	private final World world;

	public BukkitSimpleWorldImpl(World world) {
		this.world = world;
	}

	@Override public Material getType(int x, int y, int z) {
		return world.getBlockAt(x, y, z).getType();
	}

	@Override public void setType(int x, int y, int z, Material material) {
		world.getBlockAt(x, y, z).setType(material);
	}

	@Override public boolean isEmpty(int x, int y, int z) {
		return world.getBlockAt(x, y, z).isEmpty();
	}

	@Override public int getBlockLight(int x, int y, int z) {
		if(y < 0 || y > 255) {
			return 0;
		}
		return world.getBlockAt(x, y, z).getLightFromBlocks();
	}

	@Override public int getSkyLight(int x, int y, int z) {
		if(y < 0) {
			return 0;
		}
		if(y > 255) {
			return 15;
		}
		return world.getBlockAt(x, y, z).getLightFromSky();
	}

	@Override public BlockState getBlockState(int x, int y, int z) {
		return world.getBlockAt(x, y, z).getState();
	}

	@Override public void setType(int x, int y, int z, Material type, MaterialData data) {
		BlockState block = world.getBlockAt(x, y, z).getState();
		block.setType(type);
		block.setData(data);
		block.update(true);
	}

	@Override public int getHighestBlockYAt(int x, int z) {
		return world.getHighestBlockYAt(x, z);
	}
}

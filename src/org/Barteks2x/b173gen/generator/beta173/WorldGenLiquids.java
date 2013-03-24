package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.v1_5_R1.Block;
import net.minecraft.server.v1_5_R1.Material;
import net.minecraft.server.v1_5_R1.World;
import org.Barteks2x.b173gen.generator.WorldGenerator173;

public class WorldGenLiquids extends WorldGenerator173 {

	public WorldGenLiquids(int i) {
		liquidBlockId = i;
	}

	@Override
	public boolean a(World world, Random random, int i, int j, int k) {
		if (world.getTypeId(i, j + 1, k) != Block.STONE.id) {
			return false;
		}
		if (world.getTypeId(i, j - 1, k) != Block.STONE.id) {
			return false;
		}
		if (world.getTypeId(i, j, k) != 0
				&& world.getTypeId(i, j, k) != Block.STONE.id) {
			return false;
		}
		int l = 0;
		if (world.getTypeId(i - 1, j, k) == Block.STONE.id) {
			l++;
		}
		if (world.getTypeId(i + 1, j, k) == Block.STONE.id) {
			l++;
		}
		if (world.getTypeId(i, j, k - 1) == Block.STONE.id) {
			l++;
		}
		if (world.getTypeId(i, j, k + 1) == Block.STONE.id) {
			l++;
		}
		int i1 = 0;
		if (world.getMaterial(i - 1, j, k) == Material.AIR) {
			i1++;
		}
		if (world.getMaterial(i + 1, j, k) == Material.AIR) {
			i1++;
		}
		if (world.getMaterial(i, j, k - 1) == Material.AIR) {
			i1++;
		}
		if (world.getMaterial(i, j, k + 1) == Material.AIR) {
			i1++;
		}
		if (l == 3 && i1 == 1) {
			world.setTypeIdAndData(i, j, k, liquidBlockId, 0, 2);
			world.d = true;
			Block.byId[liquidBlockId].a(world, i, j, k, random);//TODO WorldGenLiquids
			world.d = false;
		}
		return true;
	}

	private int liquidBlockId;
}

package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.v1_4_6.Block;
import net.minecraft.server.v1_4_6.BlockFlower;
import net.minecraft.server.v1_4_6.World;
import net.minecraft.server.v1_4_6.WorldGenerator;

public class WorldGenTallGrass extends WorldGenerator {

	public WorldGenTallGrass(int i, int j) {
		id = i;
		dataValue = j;
	}

	@Override
	public boolean a(World world, Random random, int i, int j, int k) {
		for (int l = 0; ((l = world.getTypeId(i, j, k)) == 0 || l == Block.LEAVES.id)
				&& j > 0; j--) {
		}
		for (int i1 = 0; i1 < 128; i1++) {
			int j1 = (i + random.nextInt(8)) - random.nextInt(8);
			int k1 = (j + random.nextInt(4)) - random.nextInt(4);
			int l1 = (k + random.nextInt(8)) - random.nextInt(8);
			if (world.isEmpty(j1, k1, l1)
					&& ((BlockFlower) Block.byId[id]).canPlace(world, j1, k1,
							l1)) {
				world.setRawTypeIdAndData(j1, k1, l1, id, dataValue);
			}
		}

		return true;
	}

	private int id;
	private int dataValue;
}

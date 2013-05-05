package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.v1_5_R3.Block;
import net.minecraft.server.v1_5_R3.World;
import org.Barteks2x.b173gen.generator.WorldGenerator173;

public class WorldGenForestOld extends WorldGenerator173 {

	public WorldGenForestOld() {
	}

	@Override
	public boolean a(World world, Random random, int i, int j, int k) {
		int l = random.nextInt(3) + 5;
		boolean flag = true;
		if (j < 1 || j + l + 1 > 256) {
			return false;
		}
		for (int i1 = j; i1 <= j + 1 + l; i1++) {
			byte byte0 = 1;
			if (i1 == j) {
				byte0 = 0;
			}
			if (i1 >= (j + 1 + l) - 2) {
				byte0 = 2;
			}
			for (int i2 = i - byte0; i2 <= i + byte0 && flag; i2++) {
				for (int l2 = k - byte0; l2 <= k + byte0 && flag; l2++) {
					if (i1 >= 0 && i1 < 128) {
						int j3 = world.getTypeId(i2, i1, l2);
						if (j3 != 0 && j3 != Block.LEAVES.id) {
							flag = false;
						}
					} else {
						flag = false;
					}
				}

			}

		}

		if (!flag) {
			return false;
		}
		int j1 = world.getTypeId(i, j - 1, k);
		if (j1 != Block.GRASS.id && j1 != Block.DIRT.id || j >= 128 - l - 1) {
			return false;
		}
		world.setTypeIdAndData(i, j - 1, k, Block.DIRT.id, 0, 2);
		for (int k1 = (j - 3) + l; k1 <= j + l; k1++) {
			int j2 = k1 - (j + l);
			int i3 = 1 - j2 / 2;
			for (int k3 = i - i3; k3 <= i + i3; k3++) {
				int l3 = k3 - i;
				for (int i4 = k - i3; i4 <= k + i3; i4++) {
					int j4 = i4 - k;
					if ((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.
						nextInt(2) != 0 && j2 != 0)) {
						world.setTypeIdAndData(k3, k1, i4, Block.LEAVES.id,
							2, 2);
					}
				}

			}

		}

		for (int l1 = 0; l1 < l; l1++) {
			int k2 = world.getTypeId(i, j + l1, k);
			if (k2 == 0 || k2 == Block.LEAVES.id) {
				world.setTypeIdAndData(i, j + l1, k, Block.LOG.id, 2, 2);
			}
		}

		return true;
	}
}

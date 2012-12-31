package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.v1_4_6.Block;
import net.minecraft.server.v1_4_6.World;
import net.minecraft.server.v1_4_6.WorldGenerator;

public class WorldGenTaiga1 extends WorldGenerator {

	public WorldGenTaiga1() {
	}

	@Override
	public boolean a(World world, Random random, int i, int j, int k) {
		int l = random.nextInt(5) + 7;
		int i1 = l - random.nextInt(2) - 3;
		int j1 = l - i1;
		int k1 = 1 + random.nextInt(j1 + 1);
		boolean flag = true;
		if (j < 1 || j + l + 1 > 128) {
			return false;
		}
		for (int l1 = j; l1 <= j + 1 + l && flag; l1++) {
			int j2 = 1;
			if (l1 - j < i1) {
				j2 = 0;
			} else {
				j2 = k1;
			}
			for (int l2 = i - j2; l2 <= i + j2 && flag; l2++) {
				for (int k3 = k - j2; k3 <= k + j2 && flag; k3++) {
					if (l1 >= 0 && l1 < 128) {
						int j4 = world.getTypeId(l2, l1, k3);
						if (j4 != 0 && j4 != Block.LEAVES.id) {
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
		int i2 = world.getTypeId(i, j - 1, k);
		if (i2 != Block.GRASS.id && i2 != Block.DIRT.id || j >= 128 - l - 1) {
			return false;
		}
		world.setRawTypeId(i, j - 1, k, Block.DIRT.id);
		int k2 = 0;
		for (int i3 = j + l; i3 >= j + i1; i3--) {
			for (int l3 = i - k2; l3 <= i + k2; l3++) {
				int k4 = l3 - i;
				for (int l4 = k - k2; l4 <= k + k2; l4++) {
					int i5 = l4 - k;
					if ((Math.abs(k4) != k2 || Math.abs(i5) != k2 || k2 <= 0)
							&& !Block.q[world.getTypeId(l3, i3, l4)]) {
						world.setRawTypeIdAndData(l3, i3, l4, Block.LEAVES.id,
								1);
					}
				}

			}

			if (k2 >= 1 && i3 == j + i1 + 1) {
				k2--;
				continue;
			}
			if (k2 < k1) {
				k2++;
			}
		}

		for (int j3 = 0; j3 < l - 1; j3++) {
			int i4 = world.getTypeId(i, j + j3, k);
			if (i4 == 0 || i4 == Block.LEAVES.id) {
				world.setRawTypeIdAndData(i, j + j3, k, Block.LOG.id, 1);
			}
		}

		return true;
	}
}

package org.Barteks2x.b173gen.generator.beta173;

import java.util.Random;

import net.minecraft.server.v1_5_R1.Block;
import net.minecraft.server.v1_5_R1.BlockFlower;
import net.minecraft.server.v1_5_R1.Material;
import net.minecraft.server.v1_5_R1.World;
import org.Barteks2x.b173gen.generator.WorldGenerator173;

public class WorldGenFlowers extends WorldGenerator173 {

	public WorldGenFlowers(int i) {
		plantBlockId = i;
	}

	@Override
	public boolean a(World world, Random random, int i, int j, int k) {
		for (int l = 0; l < 64; l++) {
			int i1 = (i + random.nextInt(8)) - random.nextInt(8);
			int j1 = (j + random.nextInt(4)) - random.nextInt(4);
			int k1 = (k + random.nextInt(8)) - random.nextInt(8);
			if (world.getMaterial(i1, j1, k1) == Material.AIR
					&& ((BlockFlower) Block.byId[plantBlockId]).canPlace(world,
							i1, j1, k1)) {
				world.setTypeIdAndData(i1, j1, k1, plantBlockId, 0, 2);
			}
		}

		return true;
	}

	private int plantBlockId;
}

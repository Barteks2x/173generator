package com.github.barteks2x.b173gen.generator.beta173;

import java.util.Random;
import net.minecraft.server.v1_6_R1.*;

/**
 *
 * @author Barteks2x
 */
public class WorldGenDungeonOld extends WorldGenDungeons {

	@Override
	public boolean a(World world, Random random, int xPos, int yPos, int zPos) {
		byte ry = 3;
		int rx = random.nextInt(2) + 2;
		int rz = random.nextInt(2) + 2;
		int j1 = 0;

		int x;
		int y;
		int z;

		for (x = xPos - rx - 1; x <= xPos + rx + 1; ++x) {
			for (y = yPos - 1; y <= yPos + ry + 1; ++y) {
				for (z = zPos - rz - 1; z <= zPos + rz + 1; ++z) {
					Material material = world.getMaterial(x, y, z);

					if (y == yPos - 1 && !material.isBuildable()) {
						return false;
					}

					if (y == yPos + ry + 1 && !material.isBuildable()) {
						return false;
					}

					if ((x == xPos - rx - 1 || x == xPos + rx + 1 || z == zPos - rz - 1 ||
							z == zPos + rz + 1) && y == yPos && world.
							isEmpty(x, y, z) && world.isEmpty(x, y + 1, z)) {
						++j1;
					}
				}
			}
		}

		if (j1 >= 1 && j1 <= 5) {
			for (x = xPos - rx - 1; x <= xPos + rx + 1; ++x) {
				for (y = yPos + ry; y >= yPos - 1; --y) {
					for (z = zPos - rz - 1; z <= zPos + rz + 1; ++z) {
						if (x != xPos - rx - 1 && y != yPos - 1 && z != zPos - rz -
								1 && x != xPos + rx + 1 && y != yPos + ry + 1 &&
								z != zPos + rz + 1) {
							world.setTypeIdAndData(x, y, z, 0, 0, 2);
						} else if (y >= 0 && !world.getMaterial(x, y - 1,
								z).isBuildable()) {
							world.setTypeIdAndData(x, y, z, 0, 0, 2);
						} else if (world.getMaterial(x, y, z).
								isBuildable()) {
							if (y == yPos - 1 && random.nextInt(4) != 0) {
								world.setTypeIdAndData(x, y, z,
										Block.MOSSY_COBBLESTONE.id, 0, 2);
							} else {
								world.setTypeIdAndData(x, y, z,
										Block.COBBLESTONE.id, 0, 2);
							}
						}
					}
				}
			}

			x = 0;

			while (x < 2) {
				y = 0;
				while (true) {
					if (y < 3) {
						label204:
						{
							z = xPos + random.nextInt(rx * 2 + 1) - rx;
							int j2 = zPos + random.nextInt(rz * 2 + 1) - rz;

							if (world.isEmpty(z, yPos, j2)) {
								int k2 = 0;

								if (world.getMaterial(z - 1, yPos, j2).
										isBuildable()) {
									++k2;
								}

								if (world.getMaterial(z + 1, yPos, j2).
										isBuildable()) {
									++k2;
								}

								if (world.getMaterial(z, yPos, j2 - 1).
										isBuildable()) {
									++k2;
								}

								if (world.getMaterial(z, yPos, j2 + 1).
										isBuildable()) {
									++k2;
								}

								if (k2 == 1) {
									world.setTypeIdAndData(z, yPos, j2,
											Block.CHEST.id, 0, 2);
									TileEntityChest tileentitychest =
											(TileEntityChest)world.
											getTileEntity(z, yPos,
											j2);

									for (int l2 = 0; l2 < 8;
											++l2) {
										ItemStack itemstack =
												this.a(
												random);

										if (itemstack !=
												null) {
											tileentitychest.
													setItem(
													random.
													nextInt(
													tileentitychest.
													getSize()),
													itemstack);
										}
									}
									break label204;
								}
							}

							++y;
							continue;
						}
					}

					++x;
					break;
				}
			}

			world.setTypeIdAndData(xPos, yPos, zPos, Block.MOB_SPAWNER.id, 0, 2);
			TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.
					getTileEntity(xPos, yPos, zPos);

			tileentitymobspawner.a().a(this.b(random));
			return true;
		} else {
			return false;
		}
	}

	private ItemStack a(Random random) {
		int i = random.nextInt(11);

		return i == 0 ? new ItemStack(Item.SADDLE) : (i == 1 ?
				new ItemStack(Item.IRON_INGOT, random.nextInt(4) + 1) : (i == 2 ?
				new ItemStack(Item.BREAD) : (i == 3 ? new ItemStack(Item.WHEAT, random.
				nextInt(4) + 1) : (i == 4 ? new ItemStack(Item.SULPHUR, random.nextInt(4) +
				1) : (i == 5 ? new ItemStack(Item.STRING, random.nextInt(4) + 1) : (i == 6 ?
				new ItemStack(Item.BUCKET) : (i == 7 && random.nextInt(100) == 0 ?
				new ItemStack(Item.GOLDEN_APPLE) : (i == 8 && random.nextInt(2) == 0 ?
				new ItemStack(Item.REDSTONE, random.nextInt(4) + 1) : (i == 9 && random.
				nextInt(10) == 0 ? new ItemStack(Item.byId[Item.RECORD_1.id + random.
				nextInt(2)]) : (i == 10 ? new ItemStack(Item.INK_SACK, 1, 3) : null))))))))));
	}

	private String b(Random random) {
		int i = random.nextInt(4);

		return i == 0 ? "Skeleton" : (i == 1 ? "Zombie" : (i == 2 ? "Zombie" : (i == 3 ?
				"Spider" : "")));
	}
}

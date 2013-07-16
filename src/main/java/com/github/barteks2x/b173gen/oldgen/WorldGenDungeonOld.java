package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import com.github.barteks2x.b173gen.reflection.Util;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Barteks2x
 */
public class WorldGenDungeonOld extends WorldGenerator173 {

	@Override
	public boolean generate(World w, Random random, int xPos, int yPos, int zPos) {
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
					if (y == yPos - 1 && !isBlockBuildable(w, x, y, z)) {
						return false;
					}

					if (y == yPos + ry + 1 && !isBlockBuildable(w, x, y, z)) {
						return false;
					}

					if ((x == xPos - rx - 1 || x == xPos + rx + 1 || z == zPos - rz - 1 ||
							z == zPos + rz + 1) && y == yPos && w.getBlockAt(x, y, z).
							getTypeId() == 0 && w.getBlockAt(x, y + 1, z).getTypeId() == 0) {
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
							w.getBlockAt(x, y, z).setTypeIdAndData(0, (byte)0, false);
						} else if (y >= 0 && !isBlockBuildable(w, x, y - 1, z)) {
							w.getBlockAt(x, y, z).setTypeIdAndData(0, (byte)0, false);
						} else if (isBlockBuildable(w, x, y, z)) {
							if (y == yPos - 1 && random.nextInt(4) != 0) {
								w.getBlockAt(x, y, z).setTypeIdAndData(
										Material.MOSSY_COBBLESTONE.getId(), (byte)0, false);
							} else {
								w.getBlockAt(x, y, z).setTypeIdAndData(Material.COBBLESTONE.
										getId(), (byte)0, false);
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

							if (w.getBlockTypeIdAt(z, yPos, j2) == 0) {
								int k2 = 0;

								if (isBlockBuildable(w, z - 1, yPos, j2)) {
									++k2;
								}

								if (isBlockBuildable(w, z + 1, yPos, j2)) {
									++k2;
								}

								if (isBlockBuildable(w, z, yPos, j2 - 1)) {
									++k2;
								}

								if (isBlockBuildable(w, z, yPos, j2 + 1)) {
									++k2;
								}

								if (k2 == 1) {
									w.getBlockAt(z, yPos, j2).setTypeIdAndData(Material.CHEST.
											getId(), (byte)0, false);
									Chest chest = (Chest)w.getBlockAt(z, yPos, j2).getState();
									Inventory inventory = chest.getInventory();
									for (int l2 = 0; l2 < 8;
											++l2) {
										ItemStack itemstack = this.getRandomItem(random);

										if (itemstack != null) {
											inventory.setItem(random.nextInt(inventory.getSize()),
													itemstack);
										}
									}
									chest.update();
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

			w.getBlockAt(xPos, yPos, zPos).setTypeIdAndData(Material.MOB_SPAWNER.getId(),
					(byte)0, false);
			CreatureSpawner tileentitymobspawner = (CreatureSpawner)w.getBlockAt(xPos, yPos,
					zPos).getState();

			tileentitymobspawner.setCreatureTypeByName(this.getRandomMob(random));
			return true;
		} else {
			return false;
		}
	}

	private ItemStack getRandomItem(Random random) {
		int i = random.nextInt(11);

		return i == 0 ? new ItemStack(Material.SADDLE) : (i == 1 ?
				new ItemStack(Material.IRON_INGOT, random.nextInt(4) + 1) : (i == 2 ?
				new ItemStack(Material.BREAD) : (i == 3 ? new ItemStack(Material.WHEAT, random.
				nextInt(4) + 1) : (i == 4 ? new ItemStack(Material.SULPHUR, random.nextInt(4) +
				1) : (i == 5 ? new ItemStack(Material.STRING, random.nextInt(4) + 1) : (i == 6 ?
				new ItemStack(Material.BUCKET) : (i == 7 && random.nextInt(100) == 0 ?
				new ItemStack(Material.GOLDEN_APPLE) : (i == 8 && random.nextInt(2) == 0 ?
				new ItemStack(Material.REDSTONE, random.nextInt(4) + 1) : (i == 9 && random.
				nextInt(10) == 0 ? new ItemStack(Material.getMaterial(Material.GOLD_RECORD.getId() +
				random.nextInt(2))) : (i == 10 ? new ItemStack(Material.INK_SACK, 1, (short)3) :
				null))))))))));
	}

	private String getRandomMob(Random random) {
		int i = random.nextInt(4);

		return i == 0 ? "Skeleton" : (i == 1 ? "Zombie" : (i == 2 ? "Zombie" : (i == 3 ?
				"Spider" : "")));
	}

	private boolean isBlockBuildable(World w, int x, int y, int z) {
		try {
			Object nmsWorldObj = Util.getMethodByName("getHandle", w.getClass()).invoke(w);
			Object nmsMaterial = Util.getMethodByName("getMaterial", Util.NMS_WORLD_CLASS,
					int.class, int.class, int.class).invoke(nmsWorldObj, x, y, z);
			return (Boolean)Util.getMethodByName("isBuildable", nmsMaterial.getClass()).invoke(
					nmsMaterial);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(WorldGenDungeonOld.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(WorldGenDungeonOld.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(WorldGenDungeonOld.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}
}

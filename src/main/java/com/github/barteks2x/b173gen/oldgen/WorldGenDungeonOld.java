package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 <p>
 @author Barteks2x
 */
public class WorldGenDungeonOld implements WorldGenerator173 {

    @Override
    public boolean generate(World w, Random random, int xPos, int yPos, int zPos) {
        byte ry = 3;
        int rx = random.nextInt(2) + 2;
        int rz = random.nextInt(2) + 2;
        int j1 = 0;

        int x;
        int y;
        int z;

        for(x = xPos - rx - 1; x <= xPos + rx + 1; ++x) {
            for(y = yPos - 1; y <= yPos + ry + 1; ++y) {
                for(z = zPos - rz - 1; z <= zPos + rz + 1; ++z) {
                    if(y == yPos - 1 && !MinecraftMethods.Material_isBuildable(w, x, y, z)) {
                        return false;
                    }

                    if(y == yPos + ry + 1 && !MinecraftMethods.Material_isBuildable(w, x, y, z)) {
                        return false;
                    }

                    if((x == xPos - rx - 1 || x == xPos + rx + 1 || z == zPos - rz - 1
                            || z == zPos + rz + 1) && y == yPos && w.getBlockAt(x, y, z).isEmpty()
                            && w.getBlockAt(x, y + 1, z).isEmpty()) {
                        ++j1;
                    }
                }
            }
        }

        if(j1 >= 1 && j1 <= 5) {
            for(x = xPos - rx - 1; x <= xPos + rx + 1; ++x) {
                for(y = yPos + ry; y >= yPos - 1; --y) {
                    for(z = zPos - rz - 1; z <= zPos + rz + 1; ++z) {
                        if(x != xPos - rx - 1 && y != yPos - 1 && z != zPos - rz
                                - 1 && x != xPos + rx + 1 && y != yPos + ry + 1
                                && z != zPos + rz + 1) {
                            w.getBlockAt(x, y, z).setType(Material.AIR);
                        } else if(y >= 0 && !MinecraftMethods.Material_isBuildable(w, x, y - 1, z)) {
                            w.getBlockAt(x, y, z).setType(Material.AIR);
                        } else if(MinecraftMethods.Material_isBuildable(w, x, y, z)) {
                            if(y == yPos - 1 && random.nextInt(4) != 0) {
                                w.getBlockAt(x, y, z).setType(Material.MOSSY_COBBLESTONE);
                            } else {
                                w.getBlockAt(x, y, z).setType(Material.COBBLESTONE);
                            }
                        }
                    }
                }
            }

            x = 0;

            while(x < 2) {
                y = 0;
                while(true) {
                    if(y < 3) {
                        label204:
                        {
                            z = xPos + random.nextInt(rx * 2 + 1) - rx;
                            int j2 = zPos + random.nextInt(rz * 2 + 1) - rz;

                            if(w.getBlockAt(z, yPos, j2).isEmpty()) {
                                int k2 = 0;

                                if(MinecraftMethods.Material_isBuildable(w, z - 1, yPos, j2)) {
                                    ++k2;
                                }

                                if(MinecraftMethods.Material_isBuildable(w, z + 1, yPos, j2)) {
                                    ++k2;
                                }

                                if(MinecraftMethods.Material_isBuildable(w, z, yPos, j2 - 1)) {
                                    ++k2;
                                }

                                if(MinecraftMethods.Material_isBuildable(w, z, yPos, j2 + 1)) {
                                    ++k2;
                                }

                                if(k2 == 1) {
                                    w.getBlockAt(z, yPos, j2).setType(Material.CHEST);
                                    Chest chest = (Chest)w.getBlockAt(z, yPos, j2).getState();
                                    Inventory inventory = chest.getInventory();
                                    for(int l2 = 0; l2 < 8; ++l2) {
                                        ItemStack itemstack = this.getRandomItem(random);

                                        if(itemstack != null) {
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

            w.getBlockAt(xPos, yPos, zPos).setType(Material.MOB_SPAWNER);
            CreatureSpawner tileentitymobspawner = (CreatureSpawner)w.getBlockAt(xPos, yPos,
                    zPos).getState();

            tileentitymobspawner.setCreatureTypeByName(this.getRandomMob(random));
            return true;
        } else {
            return false;
        }
    }

    public void scale(double d0, double d1, double d2) {
    }

    private ItemStack getRandomItem(Random random) {
        int i = random.nextInt(11);

        return i == 0 ? new ItemStack(Material.SADDLE) : (i == 1
                ? new ItemStack(Material.IRON_INGOT, random.nextInt(4) + 1) : (i == 2
                ? new ItemStack(Material.BREAD) : (i == 3 ? new ItemStack(Material.WHEAT, random.
                        nextInt(4) + 1) : (i == 4 ? new ItemStack(Material.SULPHUR, random.nextInt(4)
                        + 1) : (i == 5 ? new ItemStack(Material.STRING, random.nextInt(4) + 1) : (i == 6
                ? new ItemStack(Material.BUCKET) : (i == 7 && random.nextInt(100) == 0
                ? new ItemStack(Material.GOLDEN_APPLE) : (i == 8 && random.nextInt(2) == 0
                ? new ItemStack(Material.REDSTONE, random.nextInt(4) + 1) : (i == 9 && random.
                nextInt(10) == 0 ? new ItemStack(Material.getMaterial(Material.GOLD_RECORD.getId()
                                + random.nextInt(2))) : (i == 10 ? new ItemStack(Material.INK_SACK, 1, (short)3)
                : null))))))))));
    }

    private String getRandomMob(Random random) {
        int i = random.nextInt(4);

        return i == 0 ? "Skeleton" : (i == 1 ? "Zombie" : (i == 2 ? "Zombie" : (i == 3
                ? "Spider" : "")));
    }
}

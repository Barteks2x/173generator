package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.ISimpleWorld;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.material.Leaves;
import org.bukkit.material.Tree;

import java.util.Random;

import static java.lang.Math.abs;
import static org.bukkit.Material.AIR;
import static org.bukkit.Material.DIRT;
import static org.bukkit.Material.GRASS;
import static org.bukkit.Material.LEAVES;
import static org.bukkit.Material.LOG;

public class WorldGenTaiga1Old implements WorldGenerator173 {

    public WorldGenTaiga1Old() {
    }

    public boolean generate(ISimpleWorld world, Random random, int xBase, int yBase, int zBase) {
        int l = random.nextInt(5) + 7;
        int i1 = l - random.nextInt(2) - 3;
        int j1 = l - i1;
        int k1 = 1 + random.nextInt(j1 + 1);
        boolean flag = true;

        if(yBase >= 1 && yBase + l + 1 <= WorldConfig.heightLimit) {
            int y;
            int x;
            int z;
            Material block;
            int val1;

            for(y = yBase; y <= yBase + 1 + l && flag; ++y) {
                if(y - yBase < i1) {
                    val1 = 0;
                } else {
                    val1 = k1;
                }

                for(x = xBase - val1; x <= xBase + val1 && flag; ++x) {
                    for(z = zBase - val1; z <= zBase + val1 && flag; ++z) {
                        if(y >= 0 && y < WorldConfig.heightLimit) {
                            block = world.getType(x, y, z);
                            if(block != AIR && block != LEAVES) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if(!flag) {
                return false;
            } else {
                block = world.getType(xBase, yBase - 1, zBase);
                if((block == GRASS || block == DIRT) && yBase < WorldConfig.heightLimit - l - 1) {
                    world.setType(xBase, yBase - 1, zBase, DIRT);
                    val1 = 0;

                    for(y = yBase + l; y >= yBase + i1; --y) {
                        for(x = xBase - val1; x <= xBase + val1; ++x) {
                            int dx = x - xBase;

                            for(z = zBase - val1; z <= zBase + val1; ++z) {
                                int dz = z - zBase;
                                if((abs(dx) != val1 || abs(dz) != val1 || val1 <= 0)
                                        && world.isEmpty(x, y, z)) {
                                    world.setType(x, y, z, LEAVES, new Leaves(TreeSpecies.REDWOOD));
                                }
                            }
                        }

                        if(val1 >= 1 && y == yBase + i1 + 1) {
                            --val1;
                        } else if(val1 < k1) {
                            ++val1;
                        }
                    }

                    for(x = 0; x < l - 1; ++x) {
                        block = world.getType(xBase, yBase + x, zBase);
                        if(block == AIR || block == LEAVES) {
                            world.setType(xBase, yBase + x, zBase, LOG, new Tree(TreeSpecies.REDWOOD));
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public void scale(double d0, double d1, double d2) {
    }
}

package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.ISimpleWorld;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.material.Leaves;
import org.bukkit.material.Tree;

import java.util.Random;

import static org.bukkit.Material.AIR;
import static org.bukkit.Material.DIRT;
import static org.bukkit.Material.GRASS;
import static org.bukkit.Material.LEAVES;
import static org.bukkit.Material.LOG;

public class WorldGenTaiga2Old implements WorldGenerator173 {

    public WorldGenTaiga2Old() {
    }

    public boolean generate(ISimpleWorld world, Random random, int i, int j, int k) {
        int l = random.nextInt(4) + 6;
        int i1 = 1 + random.nextInt(2);
        int j1 = l - i1;
        int k1 = 2 + random.nextInt(2);
        boolean flag = true;

        if(j >= 1 && j + l + 1 <= WorldConfig.heightLimit) {
            int l1;
            int i2;
            Material block;
            int k2;

            for(l1 = j; l1 <= j + 1 + l && flag; ++l1) {
                //boolean flag1 = true;

                if(l1 - j < i1) {
                    k2 = 0;
                } else {
                    k2 = k1;
                }

                for(i2 = i - k2; i2 <= i + k2 && flag; ++i2) {
                    for(int l2 = k - k2; l2 <= k + k2 && flag; ++l2) {
                        if(l1 >= 0 && l1 < WorldConfig.heightLimit) {
                            block = world.getType(i2, l1, l2);
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
                block = world.getType(i, j - 1, k);
                if((block == GRASS || block == DIRT) && j < WorldConfig.heightLimit - l - 1) {
                    world.setType(i, j - 1, k, DIRT);
                    k2 = random.nextInt(2);
                    i2 = 1;
                    byte b0 = 0;

                    int i3;
                    int j3;

                    for(int a = 0; a <= j1; ++a) {
                        j3 = j + l - a;

                        for(i3 = i - k2; i3 <= i + k2; ++i3) {
                            int k3 = i3 - i;

                            for(int l3 = k - k2; l3 <= k + k2; ++l3) {
                                int i4 = l3 - k;

                                if((Math.abs(k3) != k2 || Math.abs(i4) != k2 || k2 <= 0)
                                        && world.isEmpty(i3, j3, l3)) {
                                    world.setType(i3, j3, l3, LEAVES, new Leaves(TreeSpecies.REDWOOD));
                                }
                            }
                        }

                        if(k2 >= i2) {
                            k2 = b0;
                            b0 = 1;
                            ++i2;
                            if(i2 > k1) {
                                i2 = k1;
                            }
                        } else {
                            ++k2;
                        }
                    }

                    int a = random.nextInt(3);

                    for(j3 = 0; j3 < l - a; ++j3) {
                        Material mat = world.getType(i, j + j3, k);
                        if(mat == AIR || mat == LEAVES) {
                            world.setType(i, j + j3, k, LOG, new Tree(TreeSpecies.REDWOOD));
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

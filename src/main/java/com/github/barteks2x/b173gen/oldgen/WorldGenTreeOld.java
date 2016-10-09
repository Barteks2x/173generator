package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.ISimpleWorld;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import org.bukkit.Material;

import java.util.Random;

import static org.bukkit.Material.AIR;
import static org.bukkit.Material.DIRT;
import static org.bukkit.Material.GRASS;
import static org.bukkit.Material.LEAVES;
import static org.bukkit.Material.LOG;

public class WorldGenTreeOld implements WorldGenerator173 {

    public WorldGenTreeOld() {
    }

    @Override
    public boolean generate(ISimpleWorld world, Random random, int i, int j, int k) {
        int l = random.nextInt(3) + 4;
        boolean flag = true;
        if(j < 1 || j + l + 1 > WorldConfig.heightLimit) {
            return false;
        }
        for(int i1 = j; i1 <= j + 1 + l; i1++) {
            byte byte0 = 1;
            if(i1 == j) {
                byte0 = 0;
            }
            if(i1 >= (j + 1 + l) - 2) {
                byte0 = 2;
            }
            for(int i2 = i - byte0; i2 <= i + byte0 && flag; i2++) {
                for(int l2 = k - byte0; l2 <= k + byte0 && flag; l2++) {
                    if(i1 >= 0 && i1 < WorldConfig.heightLimit) {
                        Material j3 = world.getType(i2, i1, l2);
                        if(j3 != AIR && j3 != LEAVES) {
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
        }
        Material j1 = world.getType(i, j - 1, k);
        if(j1 != GRASS && j1 != DIRT || j >= WorldConfig.heightLimit - l - 1) {
            return false;
        }
        world.setType(i, j - 1, k, DIRT);
        for(int k1 = (j - 3) + l; k1 <= j + l; k1++) {
            int j2 = k1 - (j + l);
            int i3 = 1 - j2 / 2;
            for(int k3 = i - i3; k3 <= i + i3; k3++) {
                int l3 = k3 - i;
                for(int i4 = k - i3; i4 <= k + i3; i4++) {
                    int j4 = i4 - k;
                    if((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.
                            nextInt(2) != 0 && j2 != 0)) {
                        world.setType(k3, k1, i4, LEAVES);
                    }
                }

            }

        }

        for(int l1 = 0; l1 < l; l1++) {
            Material k2 = world.getType(i, j + l1, k);
            if(k2 == AIR || k2 == LEAVES) {
                world.setType(i, j + l1, k, LOG);
            }
        }

        return true;
    }

    public void scale(double d0, double d1, double d2) {
    }
}

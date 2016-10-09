package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.ISimpleWorld;
import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import org.bukkit.Material;

import java.util.Random;

public class WorldGenLiquidsOld implements WorldGenerator173 {

    private Material a;

    public WorldGenLiquidsOld(Material i) {
        this.a = i;
    }

    public boolean generate(ISimpleWorld world, Random random, int i, int j, int k) {
        if(world.getType(i, j + 1, k) != Material.STONE) {
            return false;
        } else if(world.getType(i, j - 1, k) != Material.STONE) {
            return false;
        } else if(world.isEmpty(i, j, k)
                && world.getType(i, j, k) != Material.STONE) {
            return false;
        } else {
            int l = 0;

            if(world.getType(i - 1, j, k) == Material.STONE) {
                ++l;
            }

            if(world.getType(i + 1, j, k) == Material.STONE) {
                ++l;
            }

            if(world.getType(i, j, k - 1) == Material.STONE) {
                ++l;
            }

            if(world.getType(i, j, k + 1) == Material.STONE) {
                ++l;
            }

            int i1 = 0;

            if(world.isEmpty(i - 1, j, k)) {
                ++i1;
            }

            if(world.isEmpty(i + 1, j, k)) {
                ++i1;
            }

            if(world.isEmpty(i, j, k - 1)) {
                ++i1;
            }

            if(world.isEmpty(i, j, k + 1)) {
                ++i1;
            }

            if(l == 3 && i1 == 1) {
                world.setType(i, j, k, this.a);
            }

            return true;
        }
    }

    public void scale(double d0, double d1, double d2) {
    }
}

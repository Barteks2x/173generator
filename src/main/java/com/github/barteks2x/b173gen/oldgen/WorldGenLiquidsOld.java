package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;

public class WorldGenLiquidsOld implements WorldGenerator173 {

    private Material a;

    public WorldGenLiquidsOld(Material i) {
        this.a = i;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        if(world.getBlockAt(i, j + 1, k).getType() != Material.STONE) {
            return false;
        } else if(world.getBlockAt(i, j - 1, k).getType() != Material.STONE) {
            return false;
        } else if(world.getBlockAt(i, j, k).isEmpty()
                && world.getBlockAt(i, j, k).getType() != Material.STONE) {
            return false;
        } else {
            int l = 0;

            if(world.getBlockAt(i - 1, j, k).getType() == Material.STONE) {
                ++l;
            }

            if(world.getBlockAt(i + 1, j, k).getType() == Material.STONE) {
                ++l;
            }

            if(world.getBlockAt(i, j, k - 1).getType() == Material.STONE) {
                ++l;
            }

            if(world.getBlockAt(i, j, k + 1).getType() == Material.STONE) {
                ++l;
            }

            int i1 = 0;

            if(world.getBlockAt(i - 1, j, k).isEmpty()) {
                ++i1;
            }

            if(world.getBlockAt(i + 1, j, k).isEmpty()) {
                ++i1;
            }

            if(world.getBlockAt(i, j, k - 1).isEmpty()) {
                ++i1;
            }

            if(world.getBlockAt(i, j, k + 1).isEmpty()) {
                ++i1;
            }

            if(l == 3 && i1 == 1) {
                world.getBlockAt(i, j, k).setType(this.a);
                //world.generate = true; //I have no idea what it is supposed to do
                //Block.byId[this.generate].generate(world, i, j, k, random);//Does nothing in beta 1.7.*
                //world.generate = false;
            }

            return true;
        }
    }

    public void scale(double d0, double d1, double d2) {
    }
}

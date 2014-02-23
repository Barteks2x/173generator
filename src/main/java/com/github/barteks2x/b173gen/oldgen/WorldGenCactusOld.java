package com.github.barteks2x.b173gen.oldgen;


import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;

public class WorldGenCactusOld implements WorldGenerator173 {

    public WorldGenCactusOld() {
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        for(int l = 0; l < 10; ++l) {
            int i1 = i + random.nextInt(8) - random.nextInt(8);
            int j1 = j + random.nextInt(4) - random.nextInt(4);
            int k1 = k + random.nextInt(8) - random.nextInt(8);

            if(world.getBlockAt(i1, j1, k1).isEmpty()) {
                int l1 = 1 + random.nextInt(random.nextInt(3) + 1);

                for(int i2 = 0; i2 < l1; ++i2) {
                    if(canPlaceCactusAt(world, i1, j1 + i2, k1)) {
                        world.getBlockAt(i1, j1 + i2, k1).setType(Material.CACTUS);
                    }
                }
            }
        }

        return true;
    }

    public void scale(double d0, double d1, double d2) {}

    protected boolean canPlaceCactusAt(World world, int i, int j, int k) {
        if(MinecraftMethods.Material_isBuildable(world, i - 1, j, k)) {//TODO: isSolid --> isBuildable
            return false;
        } else if(MinecraftMethods.Material_isBuildable(world, i + 1, j, k)) {
            return false;
        } else if(MinecraftMethods.Material_isBuildable(world, i, j, k - 1)) {
            return false;
        } else if(MinecraftMethods.Material_isBuildable(world, i, j, k + 1)) {
            return false;
        } else {
            Material l = world.getBlockAt(i, j - 1, k).getType();
            return l == Material.CACTUS || l == Material.SAND;
        }
    }
}

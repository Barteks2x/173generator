package com.github.barteks2x.b173gen.oldgen;

import org.bukkit.*;
import org.bukkit.block.Block;

public class MinecraftMethods {

    public static boolean isLiquid(Material m) {
        return m.toString().toLowerCase().contains("water") || m.toString().toLowerCase().contains("lava");
    }

    public static int World_getlightValue(World w, int x, int y, int z, LightType type) {
        ChunkSnapshot snapshot = w.getChunkAt(x >> 4, z >> 4).getChunkSnapshot();
        x &= 15;
        y &= 255;
        z &= 15;
        return type == LightType.SKY ? snapshot.getBlockSkyLight(x, y, z) : snapshot.getBlockEmittedLight(x, y, z);
    }

    public static boolean Block_canPlace(Material block, World w, int x, int y, int z) {
        boolean ret = false;
        Material below = w.getBlockAt(x, y - 1, z).getType();
        if(block == Material.BROWN_MUSHROOM
                || block == Material.YELLOW_FLOWER
                || block == Material.RED_ROSE
                || block == Material.RED_MUSHROOM
                || block == Material.DEAD_BUSH
                || block == Material.LONG_GRASS
                || block == Material.PUMPKIN
                || block == Material.SUGAR_CANE_BLOCK) {
            ret |= below == Material.DIRT || below == Material.GRASS;
            if(block == Material.SUGAR_CANE_BLOCK) {
                ret |= below == Material.SUGAR_CANE_BLOCK;
            }
        } else {
            assert false;
            return false;
        }
        return ret;
    }

    public static boolean Material_isBuildable(World w, int x, int y, int z) {
        Block m = w.getBlockAt(x, y, z);
        return m.isLiquid() || m.isEmpty();
    }

    public enum LightType {

        SKY,
        BLOCK;
    }

}

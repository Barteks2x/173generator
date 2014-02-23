package com.github.barteks2x.b173gen.oldgen;

import static com.github.barteks2x.b173gen.oldgen.LightType.*;
import org.bukkit.*;
import org.bukkit.block.Block;

public class MinecraftMethods {

    public static boolean isLiquid(Material m) {
        return m.toString().toLowerCase().contains("water") || m.toString().toLowerCase().contains("lava");
    }

    public static int World_getlightValue(World w, int x, int y, int z, LightType type) {
        return type ==BLOCK ? w.getChunkAt(x >> 4, z >> 4).getChunkSnapshot().getBlockEmittedLight(x & 0xF, y & 0xFF, z & 0xF) : w.getChunkAt(x >> 4, z >> 4).getChunkSnapshot().getBlockSkyLight(x & 0xF, y & 0xFF, z & 0xF);
    }

    public static boolean Block_canPlace(Material block, World w, int x, int y, int z) {
        if(!w.getBlockAt(x, y, z).isEmpty()){
            return false;
        }
        Material below = w.getBlockAt(x, y - 1, z).getType();
        if(block == Material.YELLOW_FLOWER
                || block == Material.RED_ROSE) {
            return below == Material.DIRT || below == Material.GRASS || below == Material.SOIL;
        }
        if(block == Material.DEAD_BUSH){
            return below == Material.SAND;
        }
        if(block == Material.PUMPKIN){
            return below.isSolid();
        }
        if(block == Material.LONG_GRASS){
            return below == Material.DIRT || below == Material.GRASS;
        }
        if(block == Material.BROWN_MUSHROOM || block == Material.RED_MUSHROOM) {
            ChunkSnapshot s = w.getChunkAt(x >> 4, z >> 4).getChunkSnapshot();
            int light = Math.max(s.getBlockEmittedLight(x & 0xF, y & 0xFF, z & 0xF), s.getBlockSkyLight(x & 0xF, y & 0xFF, z & 0xF));
            return (below == Material.DIRT || below == Material.GRASS) && light < 13;
        }
        if(block == Material.SUGAR_CANE_BLOCK) {
            Material x0z1 = w.getBlockAt(x, y - 1, z + 1).getType();
            Material x0z_1 = w.getBlockAt(x, y - 1, z - 1).getType();
            Material x1z0 = w.getBlockAt(x + 1, y - 1, z).getType();
            Material x_1z0 = w.getBlockAt(x - 1, y - 1, z).getType();
            return below == Material.SUGAR_CANE_BLOCK
                    || ((below == Material.DIRT || below == Material.GRASS || below == Material.SAND)
                    && (isLiquid(x1z0) || isLiquid(x_1z0) || isLiquid(x0z1) || isLiquid(x0z_1)));
        }
        throw new IllegalArgumentException(block.name());
    }

    public static boolean Material_isBuildable(World w, int x, int y, int z) {
        Block m = w.getBlockAt(x, y, z);
        return !(m.isLiquid() || m.getType().isSolid());
    }

    private MinecraftMethods() {
        throw new IllegalStateException();
    }

}

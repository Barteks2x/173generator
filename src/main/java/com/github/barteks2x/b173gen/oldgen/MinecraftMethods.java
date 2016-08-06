package com.github.barteks2x.b173gen.oldgen;

import static com.github.barteks2x.b173gen.oldgen.LightType.*;
import java.util.HashMap;
import org.bukkit.*;
import static org.bukkit.Material.*;

public class MinecraftMethods {
    private static final HashMap<Material, Boolean> buildableMapping = new HashMap<Material, Boolean>(256);

    public static void init() {
        //buildable mapping, bukkit doesn't have Material.isBuildable() method
        //automatically generated, code:
        /*
         StringBuilder sb = new StringBuilder(1000);
         sb.append("\n");
         Class<?> clazz = Blocks.class;
         Field[] fields = clazz.getDeclaredFields();
         for(Field f : fields){
         try {
         net.minecraft.server.v1_7_R1.Block block = (net.minecraft.server.v1_7_R1.Block)f.get(null);
         sb.append("buildableMapping.put(").append(Material.getMaterial(net.minecraft.server.v1_7_R1.Block.b(block)).name()).append(", ").append(block.getMaterial().isBuildable()).append(");");
         sb.append("\n");
         } catch(IllegalArgumentException ex) {
         } catch(IllegalAccessException ex) {
         }

         }
         System.out.println(sb.toString());
         */
        buildableMapping.put(AIR, false);
        buildableMapping.put(STONE, true);
        buildableMapping.put(GRASS, true);
        buildableMapping.put(DIRT, true);
        buildableMapping.put(COBBLESTONE, true);
        buildableMapping.put(WOOD, true);
        buildableMapping.put(SAPLING, false);
        buildableMapping.put(BEDROCK, true);
        buildableMapping.put(WATER, false);
        buildableMapping.put(STATIONARY_WATER, false);
        buildableMapping.put(LAVA, false);
        buildableMapping.put(STATIONARY_LAVA, false);
        buildableMapping.put(SAND, true);
        buildableMapping.put(GRAVEL, true);
        buildableMapping.put(GOLD_ORE, true);
        buildableMapping.put(IRON_ORE, true);
        buildableMapping.put(COAL_ORE, true);
        buildableMapping.put(LOG, true);
        buildableMapping.put(LEAVES, true);
        buildableMapping.put(SPONGE, true);
        buildableMapping.put(GLASS, true);
        buildableMapping.put(LAPIS_ORE, true);
        buildableMapping.put(LAPIS_BLOCK, true);
        buildableMapping.put(DISPENSER, true);
        buildableMapping.put(SANDSTONE, true);
        buildableMapping.put(NOTE_BLOCK, true);
        buildableMapping.put(BED_BLOCK, true);
        buildableMapping.put(POWERED_RAIL, false);
        buildableMapping.put(DETECTOR_RAIL, false);
        buildableMapping.put(PISTON_STICKY_BASE, true);
        buildableMapping.put(WEB, true);
        buildableMapping.put(LONG_GRASS, false);
        buildableMapping.put(DEAD_BUSH, false);
        buildableMapping.put(PISTON_BASE, true);
        buildableMapping.put(PISTON_EXTENSION, true);
        buildableMapping.put(WOOL, true);
        buildableMapping.put(PISTON_MOVING_PIECE, true);
        buildableMapping.put(YELLOW_FLOWER, false);
        buildableMapping.put(RED_ROSE, false);
        buildableMapping.put(BROWN_MUSHROOM, false);
        buildableMapping.put(RED_MUSHROOM, false);
        buildableMapping.put(GOLD_BLOCK, true);
        buildableMapping.put(IRON_BLOCK, true);
        buildableMapping.put(DOUBLE_STEP, true);
        buildableMapping.put(STEP, true);
        buildableMapping.put(BRICK, true);
        buildableMapping.put(TNT, true);
        buildableMapping.put(BOOKSHELF, true);
        buildableMapping.put(MOSSY_COBBLESTONE, true);
        buildableMapping.put(OBSIDIAN, true);
        buildableMapping.put(TORCH, false);
        buildableMapping.put(FIRE, false);
        buildableMapping.put(MOB_SPAWNER, true);
        buildableMapping.put(WOOD_STAIRS, true);
        buildableMapping.put(CHEST, true);
        buildableMapping.put(REDSTONE_WIRE, false);
        buildableMapping.put(DIAMOND_ORE, true);
        buildableMapping.put(DIAMOND_BLOCK, true);
        buildableMapping.put(WORKBENCH, true);
        buildableMapping.put(CROPS, false);
        buildableMapping.put(SOIL, true);
        buildableMapping.put(FURNACE, true);
        buildableMapping.put(BURNING_FURNACE, true);
        buildableMapping.put(SIGN_POST, true);
        buildableMapping.put(WOODEN_DOOR, true);
        buildableMapping.put(LADDER, false);
        buildableMapping.put(RAILS, false);
        buildableMapping.put(COBBLESTONE_STAIRS, true);
        buildableMapping.put(WALL_SIGN, true);
        buildableMapping.put(LEVER, false);
        buildableMapping.put(STONE_PLATE, true);
        buildableMapping.put(IRON_DOOR_BLOCK, true);
        buildableMapping.put(WOOD_PLATE, true);
        buildableMapping.put(REDSTONE_ORE, true);
        buildableMapping.put(GLOWING_REDSTONE_ORE, true);
        buildableMapping.put(REDSTONE_TORCH_OFF, false);
        buildableMapping.put(REDSTONE_TORCH_ON, false);
        buildableMapping.put(STONE_BUTTON, false);
        buildableMapping.put(SNOW, false);
        buildableMapping.put(ICE, true);
        buildableMapping.put(SNOW_BLOCK, true);
        buildableMapping.put(CACTUS, true);
        buildableMapping.put(CLAY, true);
        buildableMapping.put(SUGAR_CANE_BLOCK, false);
        buildableMapping.put(JUKEBOX, true);
        buildableMapping.put(FENCE, true);
        buildableMapping.put(PUMPKIN, true);
        buildableMapping.put(NETHERRACK, true);
        buildableMapping.put(SOUL_SAND, true);
        buildableMapping.put(GLOWSTONE, true);
        buildableMapping.put(PORTAL, false);
        buildableMapping.put(JACK_O_LANTERN, true);
        buildableMapping.put(CAKE_BLOCK, true);
        buildableMapping.put(DIODE_BLOCK_OFF, false);
        buildableMapping.put(DIODE_BLOCK_ON, false);
        buildableMapping.put(TRAP_DOOR, true);
        buildableMapping.put(MONSTER_EGGS, true);
        buildableMapping.put(SMOOTH_BRICK, true);
        buildableMapping.put(HUGE_MUSHROOM_1, true);
        buildableMapping.put(HUGE_MUSHROOM_2, true);
        buildableMapping.put(IRON_FENCE, true);
        buildableMapping.put(THIN_GLASS, true);
        buildableMapping.put(MELON_BLOCK, true);
        buildableMapping.put(PUMPKIN_STEM, false);
        buildableMapping.put(MELON_STEM, false);
        buildableMapping.put(VINE, false);
        buildableMapping.put(FENCE_GATE, true);
        buildableMapping.put(BRICK_STAIRS, true);
        buildableMapping.put(SMOOTH_STAIRS, true);
        buildableMapping.put(MYCEL, true);
        buildableMapping.put(WATER_LILY, false);
        buildableMapping.put(NETHER_BRICK, true);
        buildableMapping.put(NETHER_FENCE, true);
        buildableMapping.put(NETHER_BRICK_STAIRS, true);
        buildableMapping.put(NETHER_WARTS, false);
        buildableMapping.put(ENCHANTMENT_TABLE, true);
        buildableMapping.put(BREWING_STAND, true);
        buildableMapping.put(CAULDRON, true);
        buildableMapping.put(ENDER_PORTAL, false);
        buildableMapping.put(ENDER_PORTAL_FRAME, true);
        buildableMapping.put(ENDER_STONE, true);
        buildableMapping.put(DRAGON_EGG, true);
        buildableMapping.put(REDSTONE_LAMP_OFF, true);
        buildableMapping.put(REDSTONE_LAMP_ON, true);
    }

    public static boolean isLiquid(Material m) {
        return m.toString().toLowerCase().contains("water") || m.toString().toLowerCase().contains("lava");
    }

    public static int World_getlightValue(World w, int x, int y, int z, LightType type) {
        return type == BLOCK ?
                w.getBlockAt(x, y, z).getLightFromBlocks() :
                w.getBlockAt(x, y, z).getLightFromSky();
    }

    public static boolean Block_canPlace(Material block, World w, int x, int y, int z) {
        if(!w.getBlockAt(x, y, z).isEmpty()) {
            return false;
        }
        Material below = w.getBlockAt(x, y - 1, z).getType();
        if(block == YELLOW_FLOWER
                || block == RED_ROSE) {
            return below == DIRT || below == GRASS || below == SOIL;
        }
        if(block == DEAD_BUSH) {
            return below == SAND;
        }
        if(block == PUMPKIN) {
            return below != AIR;
        }
        if(block == LONG_GRASS) {
            return below == DIRT || below == GRASS;
        }
        if(block == BROWN_MUSHROOM || block == RED_MUSHROOM) {
            ChunkSnapshot s = w.getChunkAt(x >> 4, z >> 4).getChunkSnapshot();
            int light = Math.max(s.getBlockEmittedLight(x & 0xF, y & 0xFF, z & 0xF), s.getBlockSkyLight(x & 0xF, y & 0xFF, z & 0xF));
            return (below == DIRT || below == GRASS) && light < 13;
        }
        if(block == SUGAR_CANE_BLOCK) {
            Material x0z1 = w.getBlockAt(x, y - 1, z + 1).getType();
            Material x0z_1 = w.getBlockAt(x, y - 1, z - 1).getType();
            Material x1z0 = w.getBlockAt(x + 1, y - 1, z).getType();
            Material x_1z0 = w.getBlockAt(x - 1, y - 1, z).getType();
            return below == SUGAR_CANE_BLOCK
                    || ((below == DIRT || below == GRASS || below == SAND)
                    && (isLiquid(x1z0) || isLiquid(x_1z0) || isLiquid(x0z1) || isLiquid(x0z_1)));
        }
        throw new IllegalArgumentException(block.name());
    }

    public static boolean Material_isBuildable(World w, int x, int y, int z) {
        Boolean s = buildableMapping.get(w.getBlockAt(x, y, z).getType());
        return s == null ? false : s;
    }

    private MinecraftMethods() {
        throw new IllegalStateException();
    }
}

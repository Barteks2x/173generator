package com.github.barteks2x.b173gen.oldgen;

import static com.github.barteks2x.b173gen.oldgen.LightType.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.v1_7_R1.Blocks;
import org.bukkit.*;
import org.bukkit.block.Block;
import static org.bukkit.Material.*;

public class MinecraftMethods {

    private static final HashMap<Material, Boolean> solidMapping = new HashMap<Material, Boolean>(256);
    private static final HashMap<Material, Boolean> buildableMapping = new HashMap<Material, Boolean>(256);

    public static void init() {
        //pre-1.4.5 support
        //automatically generated, code:
        /*
         Material[] array = values();
         StringBuilder sb = new StringBuilder(1000);
         sb.append("\n");
         for(Material mat : array){
         if(!mat.isBlock()){
         continue;
         }
         sb.append("solidMapping.put(").append(mat.name()).append(", ").append(mat.isSolid()).append(");").append("\n");
         }
         System.out.println(sb.toString());
         */

        solidMapping.put(AIR, false);
        solidMapping.put(STONE, true);
        solidMapping.put(GRASS, true);
        solidMapping.put(DIRT, true);
        solidMapping.put(COBBLESTONE, true);
        solidMapping.put(WOOD, true);
        solidMapping.put(SAPLING, false);
        solidMapping.put(BEDROCK, true);
        solidMapping.put(WATER, false);
        solidMapping.put(STATIONARY_WATER, false);
        solidMapping.put(LAVA, false);
        solidMapping.put(STATIONARY_LAVA, false);
        solidMapping.put(SAND, true);
        solidMapping.put(GRAVEL, true);
        solidMapping.put(GOLD_ORE, true);
        solidMapping.put(IRON_ORE, true);
        solidMapping.put(COAL_ORE, true);
        solidMapping.put(LOG, true);
        solidMapping.put(LEAVES, true);
        solidMapping.put(SPONGE, true);
        solidMapping.put(GLASS, true);
        solidMapping.put(LAPIS_ORE, true);
        solidMapping.put(LAPIS_BLOCK, true);
        solidMapping.put(DISPENSER, true);
        solidMapping.put(SANDSTONE, true);
        solidMapping.put(NOTE_BLOCK, true);
        solidMapping.put(BED_BLOCK, true);
        solidMapping.put(POWERED_RAIL, false);
        solidMapping.put(DETECTOR_RAIL, false);
        solidMapping.put(PISTON_STICKY_BASE, true);
        solidMapping.put(WEB, false);
        solidMapping.put(LONG_GRASS, false);
        solidMapping.put(DEAD_BUSH, false);
        solidMapping.put(PISTON_BASE, true);
        solidMapping.put(PISTON_EXTENSION, true);
        solidMapping.put(WOOL, true);
        solidMapping.put(PISTON_MOVING_PIECE, true);
        solidMapping.put(YELLOW_FLOWER, false);
        solidMapping.put(RED_ROSE, false);
        solidMapping.put(BROWN_MUSHROOM, false);
        solidMapping.put(RED_MUSHROOM, false);
        solidMapping.put(GOLD_BLOCK, true);
        solidMapping.put(IRON_BLOCK, true);
        solidMapping.put(DOUBLE_STEP, true);
        solidMapping.put(STEP, true);
        solidMapping.put(BRICK, true);
        solidMapping.put(TNT, true);
        solidMapping.put(BOOKSHELF, true);
        solidMapping.put(MOSSY_COBBLESTONE, true);
        solidMapping.put(OBSIDIAN, true);
        solidMapping.put(TORCH, false);
        solidMapping.put(FIRE, false);
        solidMapping.put(MOB_SPAWNER, true);
        solidMapping.put(WOOD_STAIRS, true);
        solidMapping.put(CHEST, true);
        solidMapping.put(REDSTONE_WIRE, false);
        solidMapping.put(DIAMOND_ORE, true);
        solidMapping.put(DIAMOND_BLOCK, true);
        solidMapping.put(WORKBENCH, true);
        solidMapping.put(CROPS, false);
        solidMapping.put(SOIL, true);
        solidMapping.put(FURNACE, true);
        solidMapping.put(BURNING_FURNACE, true);
        solidMapping.put(SIGN_POST, true);
        solidMapping.put(WOODEN_DOOR, true);
        solidMapping.put(LADDER, false);
        solidMapping.put(RAILS, false);
        solidMapping.put(COBBLESTONE_STAIRS, true);
        solidMapping.put(WALL_SIGN, true);
        solidMapping.put(LEVER, false);
        solidMapping.put(STONE_PLATE, true);
        solidMapping.put(IRON_DOOR_BLOCK, true);
        solidMapping.put(WOOD_PLATE, true);
        solidMapping.put(REDSTONE_ORE, true);
        solidMapping.put(GLOWING_REDSTONE_ORE, true);
        solidMapping.put(REDSTONE_TORCH_OFF, false);
        solidMapping.put(REDSTONE_TORCH_ON, false);
        solidMapping.put(STONE_BUTTON, false);
        solidMapping.put(SNOW, false);
        solidMapping.put(ICE, true);
        solidMapping.put(SNOW_BLOCK, true);
        solidMapping.put(CACTUS, true);
        solidMapping.put(CLAY, true);
        solidMapping.put(SUGAR_CANE_BLOCK, false);
        solidMapping.put(JUKEBOX, true);
        solidMapping.put(FENCE, true);
        solidMapping.put(PUMPKIN, true);
        solidMapping.put(NETHERRACK, true);
        solidMapping.put(SOUL_SAND, true);
        solidMapping.put(GLOWSTONE, true);
        solidMapping.put(PORTAL, false);
        solidMapping.put(JACK_O_LANTERN, true);
        solidMapping.put(CAKE_BLOCK, true);
        solidMapping.put(DIODE_BLOCK_OFF, false);
        solidMapping.put(DIODE_BLOCK_ON, false);
        solidMapping.put(LOCKED_CHEST, true);
        solidMapping.put(STAINED_GLASS, true);
        solidMapping.put(TRAP_DOOR, true);
        solidMapping.put(MONSTER_EGGS, true);
        solidMapping.put(SMOOTH_BRICK, true);
        solidMapping.put(HUGE_MUSHROOM_1, true);
        solidMapping.put(HUGE_MUSHROOM_2, true);
        solidMapping.put(IRON_FENCE, true);
        solidMapping.put(THIN_GLASS, true);
        solidMapping.put(MELON_BLOCK, true);
        solidMapping.put(PUMPKIN_STEM, false);
        solidMapping.put(MELON_STEM, false);
        solidMapping.put(VINE, false);
        solidMapping.put(FENCE_GATE, true);
        solidMapping.put(BRICK_STAIRS, true);
        solidMapping.put(SMOOTH_STAIRS, true);
        solidMapping.put(MYCEL, true);
        solidMapping.put(WATER_LILY, false);
        solidMapping.put(NETHER_BRICK, true);
        solidMapping.put(NETHER_FENCE, true);
        solidMapping.put(NETHER_BRICK_STAIRS, true);
        solidMapping.put(NETHER_WARTS, false);
        solidMapping.put(ENCHANTMENT_TABLE, true);
        solidMapping.put(BREWING_STAND, true);
        solidMapping.put(CAULDRON, true);
        solidMapping.put(ENDER_PORTAL, false);
        solidMapping.put(ENDER_PORTAL_FRAME, true);
        solidMapping.put(ENDER_STONE, true);
        solidMapping.put(DRAGON_EGG, true);
        solidMapping.put(REDSTONE_LAMP_OFF, true);
        solidMapping.put(REDSTONE_LAMP_ON, true);
        solidMapping.put(WOOD_DOUBLE_STEP, true);
        solidMapping.put(WOOD_STEP, true);
        solidMapping.put(COCOA, false);
        solidMapping.put(SANDSTONE_STAIRS, true);
        solidMapping.put(EMERALD_ORE, true);
        solidMapping.put(ENDER_CHEST, true);
        solidMapping.put(TRIPWIRE_HOOK, false);
        solidMapping.put(TRIPWIRE, false);
        solidMapping.put(EMERALD_BLOCK, true);
        solidMapping.put(SPRUCE_WOOD_STAIRS, true);
        solidMapping.put(BIRCH_WOOD_STAIRS, true);
        solidMapping.put(JUNGLE_WOOD_STAIRS, true);
        solidMapping.put(COMMAND, true);
        solidMapping.put(BEACON, true);
        solidMapping.put(COBBLE_WALL, true);
        solidMapping.put(FLOWER_POT, false);
        solidMapping.put(CARROT, false);
        solidMapping.put(POTATO, false);
        solidMapping.put(WOOD_BUTTON, false);
        solidMapping.put(SKULL, false);
        solidMapping.put(ANVIL, true);
        solidMapping.put(TRAPPED_CHEST, true);
        solidMapping.put(GOLD_PLATE, true);
        solidMapping.put(IRON_PLATE, true);
        solidMapping.put(REDSTONE_COMPARATOR_OFF, false);
        solidMapping.put(REDSTONE_COMPARATOR_ON, false);
        solidMapping.put(DAYLIGHT_DETECTOR, true);
        solidMapping.put(REDSTONE_BLOCK, true);
        solidMapping.put(QUARTZ_ORE, true);
        solidMapping.put(HOPPER, true);
        solidMapping.put(QUARTZ_BLOCK, true);
        solidMapping.put(QUARTZ_STAIRS, true);
        solidMapping.put(ACTIVATOR_RAIL, false);
        solidMapping.put(DROPPER, true);
        solidMapping.put(STAINED_CLAY, true);
        solidMapping.put(STAINED_GLASS_PANE, true);
        solidMapping.put(LEAVES_2, true);
        solidMapping.put(LOG_2, true);
        solidMapping.put(ACACIA_STAIRS, true);
        solidMapping.put(DARK_OAK_STAIRS, true);
        solidMapping.put(HAY_BLOCK, true);
        solidMapping.put(CARPET, false);
        solidMapping.put(HARD_CLAY, true);
        solidMapping.put(COAL_BLOCK, true);
        solidMapping.put(PACKED_ICE, true);
        solidMapping.put(DOUBLE_PLANT, false);

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
        buildableMapping.put(LOG_2, true);
        buildableMapping.put(LEAVES, true);
        buildableMapping.put(LEAVES_2, true);
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
        buildableMapping.put(WOOD_DOUBLE_STEP, true);
        buildableMapping.put(WOOD_STEP, true);
        buildableMapping.put(COCOA, false);
        buildableMapping.put(SANDSTONE_STAIRS, true);
        buildableMapping.put(EMERALD_ORE, true);
        buildableMapping.put(ENDER_CHEST, true);
        buildableMapping.put(TRIPWIRE_HOOK, false);
        buildableMapping.put(TRIPWIRE, false);
        buildableMapping.put(EMERALD_BLOCK, true);
        buildableMapping.put(SPRUCE_WOOD_STAIRS, true);
        buildableMapping.put(BIRCH_WOOD_STAIRS, true);
        buildableMapping.put(JUNGLE_WOOD_STAIRS, true);
        buildableMapping.put(COMMAND, true);
        buildableMapping.put(BEACON, true);
        buildableMapping.put(COBBLE_WALL, true);
        buildableMapping.put(FLOWER_POT, false);
        buildableMapping.put(CARROT, false);
        buildableMapping.put(POTATO, false);
        buildableMapping.put(WOOD_BUTTON, false);
        buildableMapping.put(SKULL, false);
        buildableMapping.put(ANVIL, true);
        buildableMapping.put(TRAPPED_CHEST, true);
        buildableMapping.put(GOLD_PLATE, true);
        buildableMapping.put(IRON_PLATE, true);
        buildableMapping.put(REDSTONE_COMPARATOR_OFF, false);
        buildableMapping.put(REDSTONE_COMPARATOR_ON, false);
        buildableMapping.put(DAYLIGHT_DETECTOR, true);
        buildableMapping.put(REDSTONE_BLOCK, true);
        buildableMapping.put(QUARTZ_ORE, true);
        buildableMapping.put(HOPPER, true);
        buildableMapping.put(QUARTZ_BLOCK, true);
        buildableMapping.put(QUARTZ_STAIRS, true);
        buildableMapping.put(ACTIVATOR_RAIL, false);
        buildableMapping.put(DROPPER, true);
        buildableMapping.put(STAINED_CLAY, true);
        buildableMapping.put(HAY_BLOCK, true);
        buildableMapping.put(CARPET, false);
        buildableMapping.put(HARD_CLAY, true);
        buildableMapping.put(COAL_BLOCK, true);
        buildableMapping.put(PACKED_ICE, true);
        buildableMapping.put(ACACIA_STAIRS, true);
        buildableMapping.put(DARK_OAK_STAIRS, true);
        buildableMapping.put(DOUBLE_PLANT, false);
        buildableMapping.put(STAINED_GLASS, true);
        buildableMapping.put(STAINED_GLASS_PANE, true);
    }

    public static boolean isLiquid(Material m) {
        return m.toString().toLowerCase().contains("water") || m.toString().toLowerCase().contains("lava");
    }

    public static int World_getlightValue(World w, int x, int y, int z, LightType type) {
        return type == BLOCK ? w.getChunkAt(x >> 4, z >> 4).getChunkSnapshot().getBlockEmittedLight(x & 0xF, y & 0xFF, z & 0xF) : w.getChunkAt(x >> 4, z >> 4).getChunkSnapshot().getBlockSkyLight(x & 0xF, y & 0xFF, z & 0xF);
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

    public static boolean isSolid(Material mat) {
        Boolean s = solidMapping.get(mat);
        return s == null ? false : s;
    }

    private MinecraftMethods() {
        throw new IllegalStateException();
    }
}

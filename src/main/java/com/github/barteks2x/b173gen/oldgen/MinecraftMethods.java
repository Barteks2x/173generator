package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.ISimpleWorld;
import org.bukkit.Material;

import java.util.HashMap;

import static com.github.barteks2x.b173gen.oldgen.LightType.BLOCK;
import static org.bukkit.Material.AIR;
import static org.bukkit.Material.BEDROCK;
import static org.bukkit.Material.BED_BLOCK;
import static org.bukkit.Material.BOOKSHELF;
import static org.bukkit.Material.BREWING_STAND;
import static org.bukkit.Material.BRICK;
import static org.bukkit.Material.BRICK_STAIRS;
import static org.bukkit.Material.BROWN_MUSHROOM;
import static org.bukkit.Material.BURNING_FURNACE;
import static org.bukkit.Material.CACTUS;
import static org.bukkit.Material.CAKE_BLOCK;
import static org.bukkit.Material.CAULDRON;
import static org.bukkit.Material.CHEST;
import static org.bukkit.Material.CLAY;
import static org.bukkit.Material.COAL_ORE;
import static org.bukkit.Material.COBBLESTONE;
import static org.bukkit.Material.COBBLESTONE_STAIRS;
import static org.bukkit.Material.CROPS;
import static org.bukkit.Material.DEAD_BUSH;
import static org.bukkit.Material.DETECTOR_RAIL;
import static org.bukkit.Material.DIAMOND_BLOCK;
import static org.bukkit.Material.DIAMOND_ORE;
import static org.bukkit.Material.DIODE_BLOCK_OFF;
import static org.bukkit.Material.DIODE_BLOCK_ON;
import static org.bukkit.Material.DIRT;
import static org.bukkit.Material.DISPENSER;
import static org.bukkit.Material.DOUBLE_STEP;
import static org.bukkit.Material.DRAGON_EGG;
import static org.bukkit.Material.ENCHANTMENT_TABLE;
import static org.bukkit.Material.ENDER_PORTAL;
import static org.bukkit.Material.ENDER_PORTAL_FRAME;
import static org.bukkit.Material.ENDER_STONE;
import static org.bukkit.Material.FENCE;
import static org.bukkit.Material.FENCE_GATE;
import static org.bukkit.Material.FIRE;
import static org.bukkit.Material.FURNACE;
import static org.bukkit.Material.GLASS;
import static org.bukkit.Material.GLOWING_REDSTONE_ORE;
import static org.bukkit.Material.GLOWSTONE;
import static org.bukkit.Material.GOLD_BLOCK;
import static org.bukkit.Material.GOLD_ORE;
import static org.bukkit.Material.GRASS;
import static org.bukkit.Material.GRAVEL;
import static org.bukkit.Material.HUGE_MUSHROOM_1;
import static org.bukkit.Material.HUGE_MUSHROOM_2;
import static org.bukkit.Material.ICE;
import static org.bukkit.Material.IRON_BLOCK;
import static org.bukkit.Material.IRON_DOOR_BLOCK;
import static org.bukkit.Material.IRON_FENCE;
import static org.bukkit.Material.IRON_ORE;
import static org.bukkit.Material.JACK_O_LANTERN;
import static org.bukkit.Material.JUKEBOX;
import static org.bukkit.Material.LADDER;
import static org.bukkit.Material.LAPIS_BLOCK;
import static org.bukkit.Material.LAPIS_ORE;
import static org.bukkit.Material.LAVA;
import static org.bukkit.Material.LEAVES;
import static org.bukkit.Material.LEVER;
import static org.bukkit.Material.LOG;
import static org.bukkit.Material.LONG_GRASS;
import static org.bukkit.Material.MELON_BLOCK;
import static org.bukkit.Material.MELON_STEM;
import static org.bukkit.Material.MOB_SPAWNER;
import static org.bukkit.Material.MONSTER_EGGS;
import static org.bukkit.Material.MOSSY_COBBLESTONE;
import static org.bukkit.Material.MYCEL;
import static org.bukkit.Material.NETHERRACK;
import static org.bukkit.Material.NETHER_BRICK;
import static org.bukkit.Material.NETHER_BRICK_STAIRS;
import static org.bukkit.Material.NETHER_FENCE;
import static org.bukkit.Material.NETHER_WARTS;
import static org.bukkit.Material.NOTE_BLOCK;
import static org.bukkit.Material.OBSIDIAN;
import static org.bukkit.Material.PISTON_BASE;
import static org.bukkit.Material.PISTON_EXTENSION;
import static org.bukkit.Material.PISTON_MOVING_PIECE;
import static org.bukkit.Material.PISTON_STICKY_BASE;
import static org.bukkit.Material.PORTAL;
import static org.bukkit.Material.POWERED_RAIL;
import static org.bukkit.Material.PUMPKIN;
import static org.bukkit.Material.PUMPKIN_STEM;
import static org.bukkit.Material.RAILS;
import static org.bukkit.Material.REDSTONE_LAMP_OFF;
import static org.bukkit.Material.REDSTONE_LAMP_ON;
import static org.bukkit.Material.REDSTONE_ORE;
import static org.bukkit.Material.REDSTONE_TORCH_OFF;
import static org.bukkit.Material.REDSTONE_TORCH_ON;
import static org.bukkit.Material.REDSTONE_WIRE;
import static org.bukkit.Material.RED_MUSHROOM;
import static org.bukkit.Material.RED_ROSE;
import static org.bukkit.Material.SAND;
import static org.bukkit.Material.SANDSTONE;
import static org.bukkit.Material.SAPLING;
import static org.bukkit.Material.SIGN_POST;
import static org.bukkit.Material.SMOOTH_BRICK;
import static org.bukkit.Material.SMOOTH_STAIRS;
import static org.bukkit.Material.SNOW;
import static org.bukkit.Material.SNOW_BLOCK;
import static org.bukkit.Material.SOIL;
import static org.bukkit.Material.SOUL_SAND;
import static org.bukkit.Material.SPONGE;
import static org.bukkit.Material.STATIONARY_LAVA;
import static org.bukkit.Material.STATIONARY_WATER;
import static org.bukkit.Material.STEP;
import static org.bukkit.Material.STONE;
import static org.bukkit.Material.STONE_BUTTON;
import static org.bukkit.Material.STONE_PLATE;
import static org.bukkit.Material.SUGAR_CANE_BLOCK;
import static org.bukkit.Material.THIN_GLASS;
import static org.bukkit.Material.TNT;
import static org.bukkit.Material.TORCH;
import static org.bukkit.Material.TRAP_DOOR;
import static org.bukkit.Material.VINE;
import static org.bukkit.Material.WALL_SIGN;
import static org.bukkit.Material.WATER;
import static org.bukkit.Material.WATER_LILY;
import static org.bukkit.Material.WEB;
import static org.bukkit.Material.WOOD;
import static org.bukkit.Material.WOODEN_DOOR;
import static org.bukkit.Material.WOOD_PLATE;
import static org.bukkit.Material.WOOD_STAIRS;
import static org.bukkit.Material.WOOL;
import static org.bukkit.Material.WORKBENCH;
import static org.bukkit.Material.YELLOW_FLOWER;

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

    public static int World_getlightValue(ISimpleWorld w, int x, int y, int z, LightType type) {
        return type == BLOCK ?
                w.getBlockLight(x, y, z) :
                w.getSkyLight(x, y, z);
    }

    public static boolean Block_canPlace(Material block, ISimpleWorld w, int x, int y, int z) {
        if(!w.isEmpty(x, y, z)) {
            return false;
        }
        Material below = w.getType(x, y - 1, z);
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
            int light = Math.max(w.getBlockLight(x, y, z), w.getSkyLight(x, y, z));
            return (below == DIRT || below == GRASS) && light < 13;
        }
        if(block == SUGAR_CANE_BLOCK) {
            Material x0z1 = w.getType(x, y - 1, z + 1);
            Material x0z_1 = w.getType(x, y - 1, z - 1);
            Material x1z0 = w.getType(x + 1, y - 1, z);
            Material x_1z0 = w.getType(x - 1, y - 1, z);
            return below == SUGAR_CANE_BLOCK
                    || ((below == DIRT || below == GRASS || below == SAND)
                    && (isLiquid(x1z0) || isLiquid(x_1z0) || isLiquid(x0z1) || isLiquid(x0z_1)));
        }
        throw new IllegalArgumentException(block.name());
    }

    public static boolean Material_isBuildable(ISimpleWorld w, int x, int y, int z) {
        Boolean s = buildableMapping.get(w.getType(x, y, z));
        return s == null ? false : s;
    }

    private MinecraftMethods() {
        throw new IllegalStateException();
    }
}

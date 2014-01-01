package com.github.barteks2x.b173gen.biome;

import org.bukkit.block.Biome;

public enum BetaBiomeEnum {

    RAINFOREST("RAINFOREST", "JUNGLE"),
    SWAMPLAND("SWAMPLAND"),
    SEASONAL_FOREST("SEASONAL_FOREST", "SEASONALFOREST", "FOREST"),
    FOREST("FOREST"),
    SAVANNA("SAVANNA", "PLAINS"),
    SHRUBLAND("SHRUBLAND", "PLAINS"),
    TAIGA("TAIGA"),
    DESERT("DESERT"),
    PLAINS("PLAINS"),
    ICE_DESERT("ICE_DESERT", "COLD_BEACH", "DESERT"),
    TUNDRA("TUNDRA", "ICE_PLAINS", "ICEPLAINS", "TAIGA");

    private Biome bukkitBiome;

    private BetaBiomeEnum(String... bukkitNames) {
        for(String name: bukkitNames) {
            try {
                bukkitBiome = Biome.valueOf(name);
                break;//break if no exception (value exists)
            } catch(Exception ex) {
            }
        }
    }

    public Biome getBiome() {
        return bukkitBiome;
    }
}

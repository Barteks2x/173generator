package com.github.barteks2x.b173gen.biome;

import org.bukkit.block.Biome;

public class BetaBiome {

    public static final BetaBiome RAINFOREST = new BetaBiome("Rainforest", "RAINFOREST", "JUNGLE"),
            SWAMPLAND = new BetaBiome("Swampland", "SWAMPLAND"),
            SEASONAL_FOREST = new BetaBiome("Seasonal forest", "SEASONAL_FOREST", "SEASONALFOREST", "FOREST"),
            FOREST = new BetaBiome("Forest", "FOREST"),
            SAVANNA = new BetaBiome("Savanna", "SAVANNA", "PLAINS"),
            SHRUBLAND = new BetaBiome("Shrubland", "SHRUBLAND", "PLAINS"),
            TAIGA = new BetaBiome("Taiga", "TAIGA"),
            DESERT = new BetaBiome("Desert", "DESERT"),
            PLAINS = new BetaBiome("Plains", "PLAINS"),
            ICE_DESERT = new BetaBiome("Ice desert", "ICE_DESERT", "COLD_BEACH", "DESERT"),
            TUNDRA = new BetaBiome("Tundra", "TUNDRA", "ICE_PLAINS", "ICEPLAINS", "TAIGA");

    private final Biome bukkitBiome;
    private final String name;

    BetaBiome(String name, String... bukkitNames) {
        this.name = name;
        Biome temp = null;
        for(String n: bukkitNames) {
            try {
                temp = Biome.valueOf(n);
                break;//break if no exception (value exists)
            } catch(Exception ex) {
            }
        }
        this.bukkitBiome = temp != null ? temp : Biome.PLAINS;
    }

    public Biome getBiome() {
        return bukkitBiome;
    }
    
    public String getName(){
        return this.name;
    }
    
    @Override
    public boolean equals(Object other){
        if(this == other) return true;
        if(other == null) return false;
        if(!(other instanceof BetaBiome)) return false;
        return ((BetaBiome)other).getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}

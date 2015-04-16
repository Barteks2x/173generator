package com.github.barteks2x.b173gen.config;

import com.github.barteks2x.b173gen.generator.ChunkProviderGenerate;
import java.io.File;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldConfig {
    public static int heightLimit = 128;

    public JavaPlugin plugin;
    public ChunkProviderGenerate chunkProvider;
    public boolean isInit = false;
    public String eyeOfEnderMsg;
    public boolean 
            oldTreeGrowing = true,
            generateEmerald = false, 
            noswamps = true;
    private final CustomConfigLoader cfgLoader;
    private final String worldName;

    public WorldConfig(JavaPlugin plug, String worldName) {
        this.plugin = plug;
        this.worldName = worldName;
        cfgLoader = new CustomConfigLoader(plugin, new File(plugin.getDataFolder(),
                worldName), worldName + ".yml", "world.yml");

    }

    public boolean loadConfig() {
        try {
            FileConfiguration config = cfgLoader.getConfig();
            cfgLoader.saveDefaultConfig();
            generateEmerald = config.getBoolean("newFeatures.ores.emeralds");
            oldTreeGrowing = config.getBoolean("other.oldTreeGrowing");
            eyeOfEnderMsg = config.getString("messages.eyeOfEnderMsg");
            noswamps = config.getBoolean("biomes.noswamps");
            cfgLoader.saveConfig();
            return true;
        } catch(Exception ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to load config for world: "
                    + worldName + "\n" + ex.getMessage(), ex);
            return false;
        }
    }
}

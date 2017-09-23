package com.github.barteks2x.b173gen.config;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.barteks2x.b173gen.Generator;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomConfigLoader {

    private final String filename;
    private final JavaPlugin plugin;
    private File file = null;
    private String defaultConfigFilename = null;
    private FileConfiguration configFile = null;
    private File dataFolder = null;

    public CustomConfigLoader(JavaPlugin plugin, File dataFolder, String filename,
            String defConfigFilename) {
        if(plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null.");
        }
        this.plugin = plugin;
        this.filename = filename + ".yml";
        this.defaultConfigFilename = defConfigFilename;
        this.dataFolder = dataFolder;
        if(dataFolder == null) {
            throw new IllegalStateException();
        }
        file = new File(dataFolder, filename);
    }

    public FileConfiguration getConfig() {
        if(configFile == null) {
            reloadConfigFile();
        }
        return configFile;
    }

    public void reloadConfigFile() {
        if(file == null) {
            file = new File(dataFolder, filename);
        }
        configFile = YamlConfiguration.loadConfiguration(file);
        configFile.options().copyDefaults(true);
        configFile.options().copyHeader(true);
        configFile.options().header(getHeader());
        Reader defaultcfg = new InputStreamReader(plugin.getResource(defaultConfigFilename));
        if(defaultcfg != null) {
            YamlConfiguration defConfig = YamlConfiguration.
                    loadConfiguration(defaultcfg);
            configFile.setDefaults(defConfig);
        }
    }

    public void saveDefaultConfig() {
        if(file == null) {
            file = new File(plugin.getDataFolder(), filename);
        }
        if(!file.exists()) {
            if(configFile == null || file == null) {
                return;
            }
            try {
                configFile.options().copyHeader(true);
                configFile.load(new InputStreamReader(plugin.getResource(defaultConfigFilename)));
                //plugin.getLogger().log(Level.INFO, configFile.options().header());
                configFile.save(file);
            } catch(FileNotFoundException ex) {
                Logger.getLogger(CustomConfigLoader.class.getName()).
                        log(Level.SEVERE, "Unable to save world config", ex);
            } catch(IOException ex) {
                Logger.getLogger(CustomConfigLoader.class.getName()).
                        log(Level.SEVERE, "Unable to save world config", ex);
            } catch(InvalidConfigurationException ex) {
                Logger.getLogger(CustomConfigLoader.class.getName()).
                        log(Level.SEVERE, "Unable to save world config", ex);
            }
        }
    }

    public void saveConfig() {
        if(configFile == null || file == null) {
            return;
        }
        try {
            getConfig().save(file);
        } catch(IOException ex) {
            Generator.logger().log(Level.SEVERE, "Could not save config to " + file, ex);
        }
    }

    private String getHeader() {
        BufferedReader r = new BufferedReader(new InputStreamReader(plugin.getResource(defaultConfigFilename)));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while((line = r.readLine()) != null) {
                if(line.startsWith("#")) {
                    sb.append(line.replace('#', ' ')).append("\n");
                } else {
                    return sb.toString();
                }

            }
        } catch(IOException ex) {
            Logger.getLogger(CustomConfigLoader.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        return sb.toString();
    }
}

package org.Barteks2x.b173gen.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
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
		if (plugin == null) {
			throw new IllegalArgumentException("Plugin cannot be null.");
		}
		if (!plugin.isInitialized()) {
			throw new IllegalArgumentException("Plugin must be initialized.");
		}
		this.plugin = plugin;
		this.filename = filename + ".yml";
		this.defaultConfigFilename = defConfigFilename;
		this.dataFolder = dataFolder;
		if (dataFolder == null) {
			throw new IllegalStateException();
		}
		file = new File(dataFolder, filename);
	}

	public FileConfiguration getConfig() {
		if (configFile == null) {
			reloadConfigFile();
		}
		return configFile;
	}

	public void reloadConfigFile() {
		if (file == null) {
			file = new File(dataFolder, filename);
		}
		configFile = YamlConfiguration.loadConfiguration(file);
		InputStream defaultcfg = plugin.getResource(defaultConfigFilename);
		if (defaultcfg != null) {
			YamlConfiguration defConfig = YamlConfiguration.
				loadConfiguration(defaultcfg);
			configFile.setDefaults(defConfig);
			configFile.options().copyDefaults(true);
		}
	}

	public void saveDefaultConfig() {
		if (file == null) {
			file = new File(plugin.getDataFolder(), filename);
		}
		if (!file.exists()) {
			if (configFile == null || file == null) {
				return;
			}
			saveConfig();
		}
	}

	

	public void saveConfig() {
		if (configFile == null || file == null) {
			return;
		}
		try {
			getConfig().save(file);
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + file, ex);
		}
	}
}

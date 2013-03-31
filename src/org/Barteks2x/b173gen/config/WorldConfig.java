package org.Barteks2x.b173gen.config;

import java.io.File;
import java.util.logging.Level;
import net.minecraft.server.v1_5_R2.World;
import org.Barteks2x.b173gen.generator.ChunkProviderGenerate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldConfig {
	public JavaPlugin plugin;
	public ChunkProviderGenerate chunkProvider;
	public boolean isInit = false;
	public String cantUseEnderEyeMsg;
	private CustomConfigLoader cfgLoader;
	private String worldName;
	public boolean generateCanyons = false,
		generateStrongholds = false,
		generateVillages = false,
		generateTemples = false,
		generateMineshafts = false,
		newCaveGen = false,
		newClayGen = false,
		newLakeGen = false,
		newDungeonGen = false,
		oldTreeGrowing = true, 
		generateEmerald = false;

	public WorldConfig(JavaPlugin plug, String worldName) {
		this.plugin = plug;
		this.worldName = worldName;
		cfgLoader = new CustomConfigLoader(plugin, new File(plugin.getDataFolder(),
			worldName), worldName+".yml", "world.yml");
		
	}

	public boolean loadConfig() {
		try {
			FileConfiguration config = cfgLoader.getConfig();
			cfgLoader.saveDefaultConfig();
			generateCanyons = config.getBoolean("structures.canyons");
			generateStrongholds = config.getBoolean("structures.strongholds");
			generateVillages = config.getBoolean("structures.villages");
			generateTemples = config.getBoolean("structures.temples");
			newCaveGen = config.getBoolean("newFeatures.newCaves");
			newClayGen = config.getBoolean("newFeatures.newClayGenerator");
			newLakeGen = config.getBoolean("newFeatures.newLakeGenerator");
			generateEmerald = config.getBoolean("newFeatures.ores.emerald");
			newDungeonGen = config.getBoolean("newFeatures.newDungeonGenerator");
			oldTreeGrowing = config.getBoolean("other.oldTreeGrowing");
			cantUseEnderEyeMsg = config.getString("messages.cantUseEnderEyeMsg");
			cfgLoader.saveConfig();
			return true;
		} catch (Exception ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to load config for world: " +
				worldName + "\n" + ex.getMessage(), ex);
			return false;
		}
	}
}
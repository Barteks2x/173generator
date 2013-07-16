package com.github.barteks2x.b173gen;

import com.github.barteks2x.b173gen.config.VersionTracker;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.generator.ChunkProviderGenerate;
import com.github.barteks2x.b173gen.listener.Beta173GenListener;
import com.github.barteks2x.b173gen.oldgen.WorldChunkManagerOld;
import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Generator extends JavaPlugin {

	private final HashMap<String, WorldConfig> worlds = new HashMap<String, WorldConfig>();
	private Beta173GenListener listener;
	private VersionTracker vTracker;

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		listener = new Beta173GenListener(this);
		this.registerEvents();
		vTracker = new VersionTracker(this);
		vTracker.init();
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		if (!worlds.containsKey(worldName.trim())) {
			loadWorldConfig(worldName.trim());
		}
		return worlds.get(worldName.trim()).chunkProvider;
	}

	public void initWorld(World world) {
		if (!(this.worlds.containsKey(world.getName().trim()))) {
			return;
		}

		WorldConfig worldSetting = getOrCreateWorldConfig(world.getName());
		if (worldSetting.isInit) {
			return;
		}
		worldSetting.chunkProvider.init(world, new WorldChunkManagerOld(world.getSeed()));
		worldSetting.isInit = true;

		this.getLogger().log(Level.INFO,
				"{0} enabled for {1}, world seed: {2}", new Object[]{this.getDescription().
			getName(), world.getName(), String.valueOf(world.getSeed())});

	}

	private void registerEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(listener, this);
	}

	public static void main(String args[]) {
		System.out.println("This is bukkit plugin.");
	}

	private WorldConfig loadWorldConfig(String name) {
		name = name.trim();
		WorldConfig config = new WorldConfig(this, name);
		worlds.put(name, config);
		config.loadConfig();
		config.chunkProvider = new ChunkProviderGenerate(config, this);
		return config;
	}

	public WorldConfig getOrCreateWorldConfig(String name) {
		name = name.trim();
		if (!worlds.containsKey(name)) {
			return loadWorldConfig(name);
		}
		return worlds.get(name);
	}
}

package org.Barteks2x.b173gen.plugin;

import org.Barteks2x.b173gen.config.WorldConfig;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

import org.Barteks2x.b173gen.config.VersionTracker;
import org.Barteks2x.b173gen.generator.ChunkProviderGenerate;
import org.Barteks2x.b173gen.generator.beta173.WorldChunkManagerOld;
import org.Barteks2x.b173gen.listener.Beta173GenListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_5_R2.CraftWorld;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Generator extends JavaPlugin {

	private final HashMap<String, WorldConfig> worlds = new HashMap<String, WorldConfig>();
	private Beta173GenListener listener = new Beta173GenListener(this);
	private VersionTracker vTracker;

	@Override
	public void onDisable() {
		this.getLogger().log(Level.INFO, "{0} is now disabled", getDescription().
			getFullName());
	}

	@Override
	public void onEnable() {
		this.RegisterEvents();
		this.getLogger().log(Level.INFO, "{0} is now enabled", getDescription().
			getFullName());
		vTracker = new VersionTracker(this);
		vTracker.init();
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		//this.getLogger().log(Level.INFO, "id: " + id + " name: " + worldName);
		if (!worlds.containsKey(worldName.trim())) {
			loadWorldConfig(worldName.trim());
		}
		return worlds.get(worldName.trim()).chunkProvider;
	}

	public void initWorld(World world) {
		if (!(this.worlds.containsKey(world.getName().trim()))) {
			return;
		}

		WorldConfig worldSetting = this.worlds.get(world.getName().trim());
		if (worldSetting.isInit) {
			return;
		}
		net.minecraft.server.v1_5_R2.World workWorld = ((CraftWorld) world).getHandle();

		WorldChunkManagerOld wcm = new WorldChunkManagerOld(workWorld.getSeed());
		workWorld.worldProvider.d = wcm;
		worldSetting.chunkProvider.init(workWorld, wcm, workWorld.getSeed());
		worldSetting.isInit = true;

		this.getLogger().log(Level.INFO,
			"Beta 173 world generator enabled for {0}, world seed: {1}", new Object[]{
			world.getName(), workWorld.getSeed()});
	}

	private void RegisterEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(listener, this);
	}

	public static void main(String args[]) {
		System.out.println("This is bukkit plugin.");
	}

	private void loadWorldConfig(String world) {
		WorldConfig config = new WorldConfig(this, world);
		worlds.put(world, config);
		config.loadConfig();
		config.chunkProvider = new ChunkProviderGenerate(config, this);
	}
	public WorldConfig getWorldConfig(org.bukkit.World world){
		return worlds.get(world.getName().trim());
	}
}

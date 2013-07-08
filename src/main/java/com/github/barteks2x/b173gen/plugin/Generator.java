package com.github.barteks2x.b173gen.plugin;

import com.github.barteks2x.b173gen.config.VersionTracker;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.generator.ChunkProviderGenerate;
import com.github.barteks2x.b173gen.generator.beta173.WorldChunkManagerOld;
import com.github.barteks2x.b173gen.listener.Beta173GenListener;
import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_6_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R1.block.CraftBlock;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Generator extends JavaPlugin {

	private final HashMap<String, WorldConfig> worlds = new HashMap<String, WorldConfig>();
	private Beta173GenListener listener = new Beta173GenListener(this);
	private VersionTracker vTracker;

	static {
		CraftBlock.biomeToBiomeBase(Biome.SKY);//Initialize CraftBlock class before BiomeGenBase to avoid IllegalArgumentException
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {

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
		net.minecraft.server.v1_6_R1.World workWorld = ((CraftWorld)world).getHandle();

		WorldChunkManagerOld wcm = new WorldChunkManagerOld(workWorld.getSeed());
		workWorld.worldProvider.e = wcm;
		worldSetting.chunkProvider.init(workWorld, wcm, workWorld.getSeed());
		worldSetting.isInit = true;

		this.getLogger().log(Level.INFO,
				"{0} enabled for {1}, world seed: {2}", new Object[]{this.getDescription().
			getName(), world.getName(), workWorld.getSeed()});
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

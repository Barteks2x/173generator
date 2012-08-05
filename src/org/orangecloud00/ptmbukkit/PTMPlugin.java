package org.orangecloud00.ptmbukkit;

import java.util.HashMap;

import org.Barteks2x.b173gen.config.VersionTracker;
import org.Barteks2x.b173gen.generator.ChunkProviderGenerate;
import org.Barteks2x.b173gen.generator.beta173.Wcm;
import org.Barteks2x.b173gen.listener.Beta173GenListener;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PTMPlugin extends JavaPlugin {
    private final HashMap<String, WorldConfig> worldsSettings = new HashMap<String, WorldConfig>();
    private Beta173GenListener	     listener  = new Beta173GenListener(this);
    private VersionTracker vTracker;

    @Override
    public void onDisable() {
	this.getLogger().info(getDescription().getFullName() + " is now disabled");
    }

    @Override
    public void onEnable() {
	this.RegisterEvents();
	this.getLogger().info(getDescription().getFullName() + " is now enabled");
	vTracker = new VersionTracker(this);
	vTracker.init();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
	if (worldsSettings.containsKey(worldName)) {
	    return worldsSettings.get(worldName).chunkProvider;
	}

	WorldConfig worldSetting = new WorldConfig(this);
	worldsSettings.put(worldName, worldSetting);

	ChunkProviderGenerate prov = new ChunkProviderGenerate(worldSetting, this);

	return prov;

    }

    public void WorldInit(World world) {
	if (!(this.worldsSettings.containsKey(world.getName()))){
	    return;
	}

	WorldConfig worldSetting = this.worldsSettings.get(world.getName());
	if (worldSetting.isInit) {
	    return;
	}
	net.minecraft.server.World workWorld = ((CraftWorld) world).getHandle();

	Wcm wcm = new Wcm(workWorld.getSeed());
	workWorld.worldProvider.c = wcm;
	worldSetting.chunkProvider.init(workWorld, wcm, workWorld.getSeed());
	worldSetting.isInit = true;

	this.getLogger().info("Beta 173 world generator enabled for " + world.getName() + ", world seed: " + workWorld.getSeed());
    }

    private void RegisterEvents() {
	PluginManager pm = this.getServer().getPluginManager();
	pm.registerEvents(listener, this);
    }
}

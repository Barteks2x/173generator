package com.github.barteks2x.b173gen;

import com.github.barteks2x.b173gen.biome.BiomeOld;
import com.github.barteks2x.b173gen.config.VersionTracker;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.generator.ChunkProviderGenerate;
import com.github.barteks2x.b173gen.listener.Beta173GenListener;
import com.github.barteks2x.b173gen.metrics.Metrics;
import com.github.barteks2x.b173gen.oldgen.MinecraftMethods;
import com.github.barteks2x.b173gen.oldgen.WorldChunkManagerOld;
import com.github.barteks2x.b173gen.regenbiomes.BiomeRegen;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Generator extends JavaPlugin {

	public static Logger log;

	private HashMap<String, WorldConfig> worlds;
	private Beta173GenListener listener;
	private VersionTracker vTracker;

	@Override
	public void onDisable() {
	}

	@Override
	public void onLoad() {
		this.log = this.getLogger();
		VersionChecker.checkServerVersion(this);
		MinecraftMethods.init();
	}

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
        this.worlds = new HashMap<String, WorldConfig>(2);
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException ex) {
			logger().log(Level.WARNING, "Couldn't enable metrics!", ex);
		}
		BiomeOld.init(this.getConfig());
		listener = new Beta173GenListener(this);
		this.registerEvents();
		this.getCommand("173generator").setExecutor(this);
		vTracker = new VersionTracker(this);
		vTracker.init();
	}

	@Override
	public ChunkProviderGenerate getDefaultWorldGenerator(String worldName, String id) {
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

		logger().log(Level.INFO, "{0} enabled for {1}, world seed: {2}",
				new Object[] { this.getDescription().getName(), world.getName(), world.getSeed() });

	}

	public WorldConfig getOrCreateWorldConfig(String name) {
		name = name.trim();
		if (!worlds.containsKey(name)) {
			return loadWorldConfig(name);
		}
		return worlds.get(name);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			return false;
		}
		if ("regenbiomes".equalsIgnoreCase(args[0])) {
			if (!sender.hasPermission("b173gen.regenbiomes")) {
				sender.sendMessage("You don't have permission to use this command");
				return true;
			}
			World world = null;
			String tickMillis = "10";
			for (int i = 1; i < args.length; i++) {
				if (args[i].toLowerCase().startsWith("world=")) {
					world = this.getServer().getWorld(args[i].substring("world=".length()));
				} else if (args[i].toLowerCase().startsWith("maxtickmillis=")) {
					tickMillis = args[i].substring("maxtickmillis=".length());
				} else {
					sender.sendMessage("Unknown command argument: " + args[i]);
					return true;
				}
			}
			if (world == null && sender instanceof Entity) {
				world = ((Entity) sender).getWorld();
			}
			if (world == null) {
				sender.sendMessage("No world specified or specified world doesn't exist! ");
				return true;
			}
            if (!this.worlds.containsKey(world.getName().trim())) {
				sender.sendMessage("Specified world isnt 173generator world!");
				return true;
			}
            if(this.worlds.get(world.getName().trim()).chunkProvider.wcm == null) {
                sender.sendMessage("World \""+ world.getName()+"\" is not initialized. It's most likely result of using /reload command or an error during initialization.");
                return true;
            }
			int tickMillisInt;
			try {
				tickMillisInt = Integer.parseInt(tickMillis);
			} catch (NumberFormatException ex) {
				sender.sendMessage(tickMillis + " is not a number, is too big or too small.");
				return true;
			}
			if (tickMillisInt <= 0) {
				sender.sendMessage("maxTickMillis must be positive!");
				return true;
			}
            
			BiomeRegen.regenBiomes(world, sender, this, tickMillisInt);
		}
		return false;
	}

	private void registerEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(listener, this);
	}

	private WorldConfig loadWorldConfig(String name) {
		name = name.trim();
		WorldConfig config = new WorldConfig(this, name);
		worlds.put(name, config);
		config.loadConfig();
		config.chunkProvider = new ChunkProviderGenerate(config, this);
		return config;
	}

	public static final Logger logger() {
		return log;
	}
}

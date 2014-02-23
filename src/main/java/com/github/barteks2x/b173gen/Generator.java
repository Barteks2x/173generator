package com.github.barteks2x.b173gen;

import com.github.barteks2x.b173gen.biome.BiomeOld;
import com.github.barteks2x.b173gen.config.VersionTracker;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.exception.B173GenInitException;
import com.github.barteks2x.b173gen.exception.B173GenInitWarning;
import com.github.barteks2x.b173gen.generator.ChunkProviderGenerate;
import com.github.barteks2x.b173gen.listener.Beta173GenListener;
import com.github.barteks2x.b173gen.oldgen.WorldChunkManagerOld;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Generator extends JavaPlugin {

    private final HashMap<String, WorldConfig> worlds = new HashMap<String, WorldConfig>();
    private Beta173GenListener listener;
    private VersionTracker vTracker;
    private boolean isInit = false;
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onLoad(){
        VersionChecker.checkServerVersion(this);
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        BiomeOld.init(this);
        listener = new Beta173GenListener(this);
        this.registerEvents();
        this.getCommand("173generator").setExecutor(this);
        vTracker = new VersionTracker(this);
        vTracker.init();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        if(!worlds.containsKey(worldName.trim())) {
            loadWorldConfig(worldName.trim());
        }
        return worlds.get(worldName.trim()).chunkProvider;
    }

    public void initWorld(World world) {
        if(!(this.worlds.containsKey(world.getName().trim()))) {
            return;
        }
        
        WorldConfig worldSetting = getOrCreateWorldConfig(world.getName());
        if(worldSetting.isInit) {
            return;
        }
        worldSetting.chunkProvider.init(world, new WorldChunkManagerOld(world.getSeed()));
        worldSetting.isInit = true;
        
        this.getLogger().log(Level.INFO,
                "{0} enabled for {1}, world seed: {2}", new Object[] {this.getDescription().
                        getName(), world.getName(), String.valueOf(world.getSeed())});
        
        
    }

    public WorldConfig getOrCreateWorldConfig(String name) {
        name = name.trim();
        if(!worlds.containsKey(name)) {
            return loadWorldConfig(name);
        }
        return worlds.get(name);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            return false;
        }
        if("regenbiomes".equalsIgnoreCase(args[0])){
            if(!sender.hasPermission("b173gen.regenbiomes")){
                sender.sendMessage("You don't have permission to use this command");
                return true;
            }
            World world = null;
            if(args.length < 2){
                if(sender instanceof Entity){
                    world = ((Entity)sender).getWorld();
                }
            }else{
                world = this.getServer().getWorld(args[1]);
            }
            if(world == null){
                sender.sendMessage("No world specified or specified world doesn't exist! ");
                return true;
            }
            if(!this.worlds.containsKey(world.getName().trim())){
                sender.sendMessage("Specified world isnt 173generator world!");
            }
            File worldFolder = world.getWorldFolder();
            BiomeRegen.regenBiomes(world, sender, this, this.worlds.get(world.getName().trim()).chunkProvider.wcm);
        }
        return true;
    }

    private void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(listener, this);
    }
    
        private WorldConfig loadWorldConfig(String name){
            name = name.trim();
            WorldConfig config = new WorldConfig(this, name);
            worlds.put(name, config);
            config.loadConfig();
            config.chunkProvider = new ChunkProviderGenerate(config, this);
            return config;
        }
}

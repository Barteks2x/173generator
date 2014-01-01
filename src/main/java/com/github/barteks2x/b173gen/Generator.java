package com.github.barteks2x.b173gen;

import com.github.barteks2x.b173gen.config.VersionTracker;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.exception.B173GenInitException;
import com.github.barteks2x.b173gen.exception.B173GenInitWarning;
import com.github.barteks2x.b173gen.generator.ChunkProviderGenerate;
import com.github.barteks2x.b173gen.listener.Beta173GenListener;
import com.github.barteks2x.b173gen.oldgen.WorldChunkManagerOld;
import com.github.barteks2x.b173gen.reflection.Util;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.logging.Level;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Generator extends JavaPlugin {

    private final HashMap<String, WorldConfig> worlds = new HashMap<String, WorldConfig>();
    private Beta173GenListener listener;
    private VersionTracker vTracker;
    private boolean isInit = false;

    public static void main(String args[]) {
        System.out.println("This is bukkit plugin.");
    }

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
        if(!worlds.containsKey(worldName.trim())) {
            loadWorldConfig(worldName.trim());
        }
        return worlds.get(worldName.trim()).chunkProvider;
    }

    public void initWorld(World world) {
        init();
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

    private void init() {
        if(!isInit) {
            isInit = true;
            List<Exception> err = new LinkedList<Exception>();
            List<B173GenInitWarning> warn = new LinkedList<B173GenInitWarning>();
            Util.init(err, warn);
            boolean errors = false;

            if(!warn.isEmpty()) {
                for(B173GenInitWarning warning: warn) {
                    StringBuilder sb = new StringBuilder("173generator init: ").append("\n")
                            .append(warning.toString());
                    this.getLogger().log(Level.WARNING, sb.toString());
                }
            }
            for(Exception ex: err) {
                errors = true;
                if(ex instanceof B173GenInitException) {
                    B173GenInitException e = (B173GenInitException)ex;
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    StringBuilder sb = new StringBuilder("173generator init exception: ")
                            .append(e.getAdditionalInfo()).append("\n")
                            .append(sw.toString());
                    this.getLogger().log(Level.SEVERE, sb.toString());
                } else {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    StringBuilder sb
                            = new StringBuilder("173generator init exception: ")
                            .append("\n").append(sw.toString());
                    this.getLogger().log(Level.SEVERE, sb.toString());
                }
            }
            if(errors) {
                throw new B173GenInitException("Generator.init()", "Couldn't initialize 173generator!");
            }
        }
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
}

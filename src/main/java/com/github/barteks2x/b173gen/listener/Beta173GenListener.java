package com.github.barteks2x.b173gen.listener;

import com.github.barteks2x.b173gen.Generator;
import com.github.barteks2x.b173gen.config.WorldConfig;
import com.github.barteks2x.b173gen.oldgen.*;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Beta173GenListener implements Listener {

    private final Generator plugin;
    private final Random r = new Random();

    public Beta173GenListener(Generator plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onWorldInit(WorldInitEvent event) {
        this.plugin.initWorld(event.getWorld());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onStructureGrow(StructureGrowEvent event) {
        Location loc = event.getLocation();
        World world = loc.getWorld();
        WorldConfig cfg;
        if((cfg = plugin.getOrCreateWorldConfig(world.getName())) != null) {
            if(cfg.oldTreeGrowing) {
                if(growTree(event.getSpecies(), loc)) {
                    event.getBlocks().clear();
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event == null || event.getItem() == null) {
            return;
        }
        if(event.getItem().getType() == Material.EYE_OF_ENDER) {
            Player player = event.getPlayer();
            World world = player.getLocation().getWorld();
            WorldConfig cfg;
            if((cfg = plugin.getOrCreateWorldConfig(world.getName())) != null) {
                event.setCancelled(true);
                player.sendMessage(cfg.eyeOfEnderMsg);
            }
        }
    }

    private boolean growTree(TreeType type, Location loc) {
        boolean result = false;
        switch(type) {
            case TREE:
                result = new WorldGenTreeOld().generate(loc.getWorld(), new Random(), loc.
                        getBlockX(), loc.getBlockY(), loc.getBlockZ());
                break;
            case BIG_TREE:
                result = new WorldGenBigTreeOld().generate(loc.getWorld(), new Random(), loc.
                        getBlockX(), loc.getBlockY(), loc.getBlockZ());
                break;
            case BIRCH:
                result = new WorldGenForestOld().generate(loc.getWorld(), new Random(), loc.
                        getBlockX(), loc.getBlockY(), loc.getBlockZ());
                break;
        }
        return result;
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(ServerCommandEvent event){
        if(event.getCommand().equals("reload")){
            String color = ChatColor.RED.toString();
            event.getSender().sendMessage(color +"[173generator] detected using /reload command.");
            event.getSender().sendMessage(color + "/reload command is NOT supported by 173generator. It WILL cause issues. Restart your server.");
            event.getSender().sendMessage(color + "If you continue default world generator may be used");
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(PlayerCommandPreprocessEvent event){
        if(event.getMessage().startsWith("/reload")){
            String color = ChatColor.RED.toString();
            event.getPlayer().sendMessage(color +"[173generator] detected using /reload command.");
            event.getPlayer().sendMessage(color + "/reload command is NOT supported by 173generator. It WILL cause issues. Restart your server.");
            event.getPlayer().sendMessage(color + "If you continue default world generator may be used");
        }
    }
}

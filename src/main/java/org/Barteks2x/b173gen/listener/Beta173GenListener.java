package org.Barteks2x.b173gen.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import net.minecraft.server.v1_5_R2.Block;
import net.minecraft.server.v1_5_R2.EntityPlayer;
import net.minecraft.server.v1_5_R2.Item;
import net.minecraft.server.v1_5_R2.WorldGenTaiga1;
import net.minecraft.server.v1_5_R2.WorldGenTaiga2;
import org.Barteks2x.b173gen.config.WorldConfig;
import org.Barteks2x.b173gen.generator.beta173.WorldGenBigTreeOld;
import org.Barteks2x.b173gen.generator.beta173.WorldGenForestOld;
import org.Barteks2x.b173gen.generator.beta173.WorldGenTreeOld;
import org.Barteks2x.b173gen.plugin.Generator;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_5_R2.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldInitEvent;

public class Beta173GenListener implements Listener {

	private Generator plugin;

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
		WorldConfig cfg = null;
		if ((cfg = plugin.getWorldConfig(world)) != null) {
			if (cfg.oldTreeGrowing) {
				if (growTree(event.getSpecies(), loc)) {
					event.getBlocks().clear();
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getItem().getTypeId() == Item.EYE_OF_ENDER.id) {
			Player player = event.getPlayer();
			World world = player.getLocation().getWorld();
			WorldConfig cfg = null;
			if ((cfg = plugin.getWorldConfig(world)) != null) {
				if(!cfg.generateStrongholds){
					event.setCancelled(true);
					player.sendMessage(cfg.eyeOfEnderMsg);
				}
			}
		}
	}

	private boolean growTree(TreeType type, Location loc) {
		boolean result = false;
		switch (type) {
			case TREE:
				result = new WorldGenTreeOld().a(((CraftWorld)loc.getWorld()).
					getHandle(), new Random(), loc.getBlockX(), loc.getBlockY(),
					loc.getBlockZ());
				break;
			case BIG_TREE:
				result = new WorldGenBigTreeOld().a(((CraftWorld)loc.getWorld()).
					getHandle(), new Random(), loc.getBlockX(), loc.getBlockY(),
					loc.getBlockZ());
				break;
			case BIRCH:
				result = new WorldGenForestOld().a(((CraftWorld)loc.getWorld()).
					getHandle(), new Random(), loc.getBlockX(), loc.getBlockY(),
					loc.getBlockZ());
				break;
		}
		return result;
	}
}
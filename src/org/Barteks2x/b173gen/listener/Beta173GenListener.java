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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_5_R2.CraftWorld;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
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
		plugin.getLogger().log(Level.INFO, "initWorld" + event.getWorld().getName());
	}

	/*@EventHandler(priority = EventPriority.NORMAL)
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		if (event.getEntity() instanceof EnderSignal) {
			plugin.getLogger().log(Level.INFO, "ender eye!");
			event.setCancelled(true);
			LivingEntity shooter = event.getEntity().getShooter();
			if (shooter instanceof Player &&
				plugin.getWorldConfig(shooter.getWorld()).generateStrongholds) {
				((Player)shooter).sendMessage(plugin.getWorldConfig(shooter.
					getWorld()).cantUseEnderEyeMsg);
			}
		}
	}*/

	@EventHandler(priority = EventPriority.NORMAL)
	public void onStructureGrow(StructureGrowEvent event) {
		/*List<BlockState> l = event.getBlocks();
		BlockState[] b = new BlockState[l.size()];
		l.toArray(b);
		for (int i = 0; i < b.length; i++) {
			plugin.getLogger().log(Level.INFO, "{0}, {1}, {2}, {3}, {4}",
				new Object[]{i, Block.byId[b[i].getTypeId()].getName(), b[i].getX(),
					b[i].getY(), b[i].getZ()});
		}*/
		Location loc = event.getLocation();
		World world = loc.getWorld();
		WorldConfig cfg;
		if ((cfg = plugin.getWorldConfig(world)) != null) {
			if (cfg.oldTreeGrowing) {
				if (growTree(event.getSpecies(), loc)) {
					event.getBlocks().clear();
				}
			}
		}
	}

	private boolean growTree(TreeType type, Location loc) {
		//plugin.getLogger().log(Level.INFO, "TreeGrow: {0}, {1}, {2}, {3}", new Object[]{
		//		type.name(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()});
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
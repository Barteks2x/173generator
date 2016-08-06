package com.github.barteks2x.b173gen.regenbiomes;

import com.github.barteks2x.b173gen.Generator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class BiomeRegen {

	public static void regenBiomes(final World world, final CommandSender sender, final Generator plugin, int maxMillis) {
		BukkitRunnable runnable = 
                new RegenBiomeControllerSyncTask(sender, world.getWorldFolder(), world.getUID(), plugin, maxMillis);
		Generator.logger().fine("Sheduling task for biome regen.");
		runnable.runTaskLater(plugin, 1);
	}
}
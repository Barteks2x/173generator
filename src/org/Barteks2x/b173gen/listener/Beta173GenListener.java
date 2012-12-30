package org.Barteks2x.b173gen.listener;

import org.Barteks2x.b173gen.plugin.Generator;
import org.bukkit.entity.EnderSignal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.world.WorldInitEvent;

public class Beta173GenListener implements Listener {
	private Generator plugin;

	public Beta173GenListener(Generator plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onWorldInit(WorldInitEvent event) {
		this.plugin.WorldInit(event.getWorld());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPrijectilaLainch(ProjectileLaunchEvent event) {
		if (event.getEntity() instanceof EnderSignal) {
			event.setCancelled(true);
		}
	}
}

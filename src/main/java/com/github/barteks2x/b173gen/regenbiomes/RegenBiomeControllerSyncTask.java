package com.github.barteks2x.b173gen.regenbiomes;

import com.github.barteks2x.b173gen.Generator;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

class RegenBiomeControllerSyncTask extends BukkitRunnable {
	private final CommandSender sender;
	private final File worldDir;
	private final UUID worldUuid;
	private final Generator plugin;
	private final int stage;
	private final List<RegionInfo> regionFiles;
    private TaskStatus currentTaskStatus;
	private final int maxMillis;
    
    //TODO: IMPORTANT - REFACTOR IT
    private RegenBiomeControllerSyncTask(CommandSender sender, File worldFolder, UUID worldUuid, Generator plugin, int maxMillis, int stage, TaskStatus currentTaskStatus, List<RegionInfo> regionFiles) {
		this.sender = sender;
		this.worldDir = worldFolder;
		this.worldUuid = worldUuid;
		this.plugin = plugin;
		this.maxMillis = maxMillis;
        this.stage = stage;
        this.currentTaskStatus = currentTaskStatus;
        this.regionFiles = regionFiles;
	}
    
    RegenBiomeControllerSyncTask(CommandSender sender, File worldFolder, UUID worldUuid, Generator plugin, int maxMillis) {
		this(sender, worldFolder, worldUuid, plugin, maxMillis, 0, null, new ArrayList<RegionInfo>(100));
	}

	@Override
	public void run() {
        this.plugin.getLogger().finest("RegenBiomeControllerSyncTask tick");
		if (currentTaskStatus != null && !currentTaskStatus.finished) {
            this.plugin.getLogger().finest("RegenBiomeControllerSyncTask - current task not finished yet");
            this.scheduleItself(stage);
			return;
		}
		if (currentTaskStatus != null && !currentTaskStatus.successful) {
			this.sendErrorMessage();
			return;
		}
		switch (stage) {
		case 0:
            this.plugin.getLogger().finest("RegenBiomeControllerSyncTask - stage 0");
            this.currentTaskStatus = new TaskStatus();
			FindRegionFilesTask findRegions = new FindRegionFilesTask(this.plugin, this.worldDir, this.regionFiles, this.currentTaskStatus);
			findRegions.runTaskAsynchronously(plugin);
			sender.sendMessage("Stage 1/2. Reading world region data...");
			break;
		case 1:
            this.plugin.getLogger().finest("RegenBiomeControllerSyncTask - stage 1");
			sender.sendMessage("Stage 2/2. Regenerating biomes...");
            this.currentTaskStatus = new TaskStatus();
			FindChunksAndRegenBiomesSyncTask findChunks = 
                    new FindChunksAndRegenBiomesSyncTask(
                            this.plugin, this.sender, this.worldUuid,
					this.regionFiles, this.maxMillis, this.currentTaskStatus);
			findChunks.runTaskLater(plugin, 1);
			break;
		default:
            this.plugin.getLogger().finest("RegenBiomeControllerSyncTask - finished");
			sender.sendMessage("Biomes regenerated successfully.");
			return;
		}
        this.scheduleItself(stage+1);
	}

	private void sendErrorMessage() {
		this.sender.sendMessage("===Error occurred while regenerating biomes.===\n===See error log for details.===");
	}

    private void scheduleItself(int stage) {
        new RegenBiomeControllerSyncTask(sender, worldDir, worldUuid, plugin, maxMillis, stage, currentTaskStatus, regionFiles).runTaskLater(plugin, 1);
    }

}

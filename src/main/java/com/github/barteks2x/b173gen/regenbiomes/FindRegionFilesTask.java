package com.github.barteks2x.b173gen.regenbiomes;

import com.github.barteks2x.b173gen.Generator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.scheduler.BukkitRunnable;

class FindRegionFilesTask extends BukkitRunnable {
    private final Generator plugin;
    private final File worldFolder;
    private final Collection<RegionInfo> regionFiles;
    private final TaskStatus status;

    FindRegionFilesTask(Generator plugin, File worldFolder, Collection<RegionInfo> regionFiles, TaskStatus status) {
        this.plugin = plugin;
        this.worldFolder = worldFolder;
        this.regionFiles = regionFiles;
        this.status = status;
    }

    @Override
    public void run() {
        File regionFolder = new File(worldFolder, "region");
        if(!regionFolder.exists() || !regionFolder.isDirectory()) {
            this.status.successful = false;
            throw new RuntimeException(new FileNotFoundException(regionFolder.getAbsolutePath()));
        }
        File[] files = regionFolder.listFiles();
        this.findRegions(files);
        Generator.logger().log(Level.FINE, "Finished finding region files. {0} region files found.", this.regionFiles.size());
        this.status.finished = true;
    }

    void findRegions(File[] files) {
        for (File regionFile : files) {
            Generator.logger().log(Level.FINE, "Found file: {0}", regionFile.getName());
            String name = regionFile.getName();
            if (!name.startsWith("r.") || !name.endsWith(".mca")) {
                Generator.logger().log(Level.WARNING, "non-region file: {0}", name);
                continue;
            }
            String[] s = name.split("\\."); // "." is generate reserved character in
            // regular expression (use "\."
            // instead)
            if (s.length != 4) {
                Generator.logger().log(Level.WARNING, "incorrect region file name: {0} length: {1}", new Object[]{name, s.length});
                continue;
            }
            int x;
            int z;
            try {
                x = Integer.parseInt(s[1]);
                z = Integer.parseInt(s[2]);
            } catch (NumberFormatException ex) {
                Generator.logger().log(Level.WARNING, "Couldn't parse region position: {0}", name);
                continue;
            } catch (NullPointerException ex) {
                Generator.logger().log(Level.WARNING, "Couldn't parse region position: {0}", name);
                continue;
            }
            Generator.logger().log(Level.FINE, "Region position: {0}, {1}", new Object[]{x + "", z + ""});
            this.regionFiles.add(new RegionInfo(regionFile, x, z));
        }
    }
}

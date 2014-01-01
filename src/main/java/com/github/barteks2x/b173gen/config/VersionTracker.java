package com.github.barteks2x.b173gen.config;

import com.github.barteks2x.b173gen.Generator;
import java.io.*;

public class VersionTracker {

    private File versionFile;
    private Generator plugin;

    public VersionTracker(Generator plugin) {
        this.plugin = plugin;
        this.versionFile = new File(plugin.getDataFolder() + File.separator
                + "VERSION");
    }

    public void init() {
        if(!versionFile.exists()) {
            writeVersion();
        }
    }

    public void writeVersion() {
        try {
            plugin.getDataFolder().mkdirs();
            versionFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(
                    versionFile));
            writer.write(plugin.getDescription().getVersion());
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(SecurityException e) {
            e.printStackTrace();
        }

    }

    public String readVersion() {
        byte[] version = new byte[(byte)versionFile.length()];
        BufferedInputStream f = null;
        try {
            f = new BufferedInputStream(new FileInputStream(versionFile));
            f.read(version);
            f.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return new String(version);

    }

    public void updateFromLevel(int versionLevel) {
        if(versionLevel == -1) {
            writeVersion();
        }
    }
}

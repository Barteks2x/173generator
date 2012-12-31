package org.Barteks2x.b173gen.config;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.Barteks2x.b173gen.plugin.Generator;

public class VersionTracker {
	private File versionFile;
	private Generator plugin;

	public VersionTracker(Generator plugin) {
		this.plugin = plugin;
		this.versionFile = new File(plugin.getDataFolder() + File.separator
				+ "VERSION");
	}

	public void init() {
		if (!versionFile.exists()) {
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

	}

	public String readVersion() {
		byte[] version = new byte[(byte) versionFile.length()];
		BufferedInputStream f = null;
		try {
			f = new BufferedInputStream(new FileInputStream(versionFile));
			f.read(version);
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(version);

	}

	public void updateFromLevel(int versionLevel) {
		if (versionLevel == -1) {
			writeVersion();
		}
	}
}

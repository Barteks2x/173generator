package org.Barteks2x.b173gen.config;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.orangecloud00.ptmbukkit.PTMPlugin;

public class VersionTracker {
    private File versionFile;
    private PTMPlugin plugin;
    
    
    public VersionTracker(PTMPlugin plugin){
	this.plugin = plugin;
	this.versionFile = new File(plugin.getDataFolder() + File.separator + "VERSION");
    }
    public void init(){
	if(!versionFile.exists()) {
		writeVersion();
	}
    }
    public void writeVersion() {
	try {
	    versionFile.createNewFile();
	    BufferedWriter writer = new BufferedWriter(new FileWriter(versionFile));
	    writer.write(plugin.getDescription().getVersion());
	    writer.close();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (SecurityException e) {
	    e.printStackTrace();
	}
	
    }
    
    public String readVersion(){
	byte[] version = new byte[(byte) versionFile.length()];
	BufferedInputStream f = null;
	try{
	    f = new BufferedInputStream(new FileInputStream(versionFile));
	    f.read(version);
	} catch(FileNotFoundException e) {
	    e.printStackTrace();
	} catch(IOException e) {
	    e.printStackTrace();
	}
	return new String(version);
	
    }
    public void updateFromLevel(int versionLevel){
	if(versionLevel == -1){
	    writeVersion();
	}
    }
}

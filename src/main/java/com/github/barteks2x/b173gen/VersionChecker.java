/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.barteks2x.b173gen;

import static com.comphenix.protocol.utility.MinecraftVersion.*;
import com.comphenix.protocol.utility.MinecraftVersion;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Server;

/**
 <p>
 @author kuba
 */
public class VersionChecker {
    private static final String[] KNOWN_VERSIONS = new String[]{
        "1.2.5-R5", 
        "1.3.1-R", 
        "1.3.2-R", 
        "1.4.2-R", 
        "1.4.4-R", 
        "1.4.5-R", 
        "1.4.6-R", 
        "1.4.7-R", 
        "1.5-R", 
        "1.5.1-R", 
        "1.5.2-R", 
        "1.6.1-R", 
        "1.6.2-R", 
        "1.6.4-R", 
        "1.7.2-R"
    };

    public static void checkServerVersion(Generator plugin) {
        if(plugin.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
            MinecraftVersion version = new MinecraftVersion(plugin.getServer());
            MinecraftVersion v1_2_5 = new MinecraftVersion("1.2.5");
            //versionA.compare(versionB) > 0 --> versionA is newer than versionB
            if(version.compareTo(v1_2_5) < 0){
                throw new UnsupportedVersionException(plugin.getServer().getVersion());
            }
            if(version.compareTo(WORLD_UPDATE) > 0){
                plugin.getLogger().log(Level.WARNING, "Unknown server version: {0}, plugin may work incorrectly or crash!", plugin.getServer().getVersion());
            }
        } else {
            String version = plugin.getServer().getVersion();
            for(String s : KNOWN_VERSIONS){
                if(version.contains(s)){
                    return;
                }
            }
            plugin.getLogger().log(Level.WARNING, "Unknown server version: {0}, plugin may work incorrectly or crash!", plugin.getServer().getVersion());
        }
    }

    private VersionChecker() {
        throw new RuntimeException("You can't instantiate this class!");
    }
}

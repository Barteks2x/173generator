package com.github.barteks2x.b173gen;

import static com.comphenix.protocol.utility.MinecraftVersion.*;
import com.comphenix.protocol.utility.MinecraftVersion;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.Server;

public class VersionChecker {
    private static final String[] KNOWN_VERSIONS = new String[] {
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
        "1.7.2-R", 
        "1.7.5-R", 
        "1.7.9-R", 
        "1.7.10-R", 
        "1.8-R", 
        "1.8.3-R"
    };

    public static void checkServerVersion(Generator plugin) {
        String version = Bukkit.getBukkitVersion();
        String version2 = plugin.getServer().getVersion();
        System.out.println(version+"\n"+version2);
        for(String s: KNOWN_VERSIONS) {
            if(version.contains(s) || version2.contains(s)) {
                return;
            }
        }
        Generator.logger().log(Level.WARNING, "Unknown server version: {0}, plugin may work incorrectly or crash!", plugin.getServer().getVersion());
    }

    private VersionChecker() {
        throw new RuntimeException("You can't instantiate this class!");
    }
}

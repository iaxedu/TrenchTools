package io.chazza.trenchtools.hook;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import io.chazza.trenchtools.api.TrenchToolAPI;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class WorldGuardHook {
    public static WorldGuardPlugin getWorldGuard() {
        Plugin wgplugin = TrenchToolAPI.getCore().getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (wgplugin == null || !(wgplugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }
        return (WorldGuardPlugin) wgplugin;
    }

    public static boolean isBlacklisted(Block b) {
        List<String> blacklisted = TrenchToolAPI.getCore().getConfig().getStringList("blacklist.region");

        Vector v = new Vector(b.getX(), b.getY(), b.getZ());

        for(String region : blacklisted){
            if(getWorldGuard().getRegionManager(b.getWorld()).getApplicableRegionsIDs(v).contains(region)){
                return true;
            }
        }

        return false;
    }
}

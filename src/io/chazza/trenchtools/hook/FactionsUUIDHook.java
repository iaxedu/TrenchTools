package io.chazza.trenchtools.hook;

import com.massivecraft.factions.*;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import io.chazza.trenchtools.api.TrenchToolAPI;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

import static io.chazza.trenchtools.hook.WorldGuardHook.getWorldGuard;

public class FactionsUUIDHook {
    public static Factions getFactionsUUID() {
        Plugin wgplugin = TrenchToolAPI.getCore().getServer().getPluginManager().getPlugin("FactionsUUID");

        // WorldGuard may not be loaded
        if (wgplugin == null || !(wgplugin instanceof Factions)) {
            return null; // Maybe you want throw an exception instead
        }
        return (Factions) wgplugin;
    }

    public static boolean isSafe(Player p, Block b) {
        if(getFactionsUUID() == null) return false;

        final FPlayer fPlayer = FPlayers.getInstance().getByPlayer(p);
        final Faction faction = Board.getInstance().getFactionAt(new FLocation(b.getLocation()));

        return faction.getId().equals(fPlayer.getFactionId()) || faction.isWilderness() ||
            (faction.getRelationTo(fPlayer) == Relation.ALLY) ||
            (faction.getRelationTo(fPlayer) == Relation.NEUTRAL) ||
            (faction.getRelationTo(fPlayer) == Relation.ENEMY);
    }
}

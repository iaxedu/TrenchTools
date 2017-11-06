package io.chazza.trenchtools.event;


import io.chazza.trenchtools.TrenchTools;
import io.chazza.trenchtools.api.MessageSystem;
import io.chazza.trenchtools.api.TrenchTool;
import io.chazza.trenchtools.api.TrenchToolAPI;
import io.chazza.trenchtools.event.custom.TPMineEvent;
import io.chazza.trenchtools.hook.FactionsUUIDHook;
import io.chazza.trenchtools.hook.WorldGuardHook;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Chazmondo
 */
public class TrenchMineEvent implements Listener {

    public TrenchMineEvent(TrenchTools tt) {
        Bukkit.getPluginManager().registerEvents(this, tt);
    }


    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (TrenchToolAPI.getTool(p.getItemInHand()) != null) {
            TrenchTool tool = TrenchToolAPI.getTool(p.getItemInHand());

            if(tool.requirePerm() && !p.hasPermission("trenchtools.use."+tool.getId().toLowerCase())){
                HashMap<String, String> replace = new HashMap<>();
                replace.put("%player%", p.getName());
                replace.put("%tier%", tool.getId().toLowerCase());
                new MessageSystem("permission.trenchtool").show(p, replace);

                e.setCancelled(true);
                return;
            }

            for(String world : TrenchToolAPI.getCore().getConfig().getStringList("blacklist.world")){
                if(world.equalsIgnoreCase(b.getWorld().getName())){

                    HashMap<String, String> replace = new HashMap<>();
                    replace.put("%player%", p.getName());
                    replace.put("%tier%", tool.getId());
                    new MessageSystem("blacklisted").show(p, replace);
                    return;
                }
            }


            List<Block> blocks = new ArrayList<>();

            if (ThreadLocalRandom.current().nextInt(100) + 1 <= tool.getChance()) {
                Block block;

                for (int xOff = -1; xOff <= -2+tool.getRadius(); ++xOff) {
                    for (int yOff = -1; yOff <= -2+tool.getRadius(); ++yOff) {
                        for (int zOff = -1; zOff <= -2+tool.getRadius(); ++zOff) {
                            block = b.getRelative(xOff, yOff, zOff);

                            if(TrenchToolAPI.isBlacklisted(block.getType(), block.getData())) continue;
                            if(FactionsUUIDHook.isSafe(p, block))
                               continue;

                            if(!WorldGuardHook.isBlacklisted(block))
                                blocks.add(block);
                        }
                    }
                }

                TPMineEvent mineEvent = new TPMineEvent(p, tool, blocks);
                Bukkit.getPluginManager().callEvent(mineEvent);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onMine(TPMineEvent e){
        Player p = e.getPlayer();

        if(e.getTool().getSound() != null && !e.getTool().soundPerBlock()){
            p.playSound(p.getLocation(), e.getTool().getSound(), 1, 1);
        }

        for(Block b : e.getBlocks()){
            if(e.getTool().getSound() != null && e.getTool().soundPerBlock()){
                p.playSound(p.getLocation(), e.getTool().getSound(), 1, 1);
            }

            if(e.getTool().isItemDrop()) b.breakNaturally();
            else b.setType(Material.AIR);
        }
    }
}

package io.chazza.trenchtools.api;

import io.chazza.trenchtools.TrenchTools;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chazmondo
 */
public class TrenchToolAPI {

    public static TrenchTools getCore(){
        return TrenchTools.getCore();
    }

    public static List<TrenchTool> getTools(){
        return getCore().getTools();
    }

    public static TrenchTool getToolById(String id){
        for(TrenchTool tool : getTools()){
            if(tool.getId().equalsIgnoreCase(id)){
                return tool;
            }
        }
        return null;
    }
    public static TrenchTool getTool(ItemStack is){
        is.setDurability((short) 0);
        for(TrenchTool tool : getTools()){
            if(!tool.getTool().hasItemMeta()) return null;
            if(!tool.getTool().getItemMeta().hasDisplayName()) return null;
            if(!tool.getTool().getItemMeta().hasLore()) return null;

            if(tool.getTool().getItemMeta().getDisplayName().equalsIgnoreCase(is.getItemMeta().getDisplayName())
                && tool.getTool().getItemMeta().getLore().equals(is.getItemMeta().getLore())){
                return tool;
            }
        }
        return null;
    }

    public static void givePick(Player p, TrenchTool tool, int amount){
        ItemStack trenchTool = tool.getTool();

        for (int i = 0; i < amount; i++) {
            HashMap<Integer, ItemStack> items = p.getInventory().addItem(trenchTool);
            for (Map.Entry<Integer, ItemStack> item : items.entrySet()) {
                p.getWorld().dropItemNaturally(p.getLocation().add(0.5D, 0,0.5D), item.getValue());
            }
        }
    }

    public static boolean isBlacklisted(Material mat, int data){
        for(String item : getCore().getConfig().getStringList("blacklist.item")){
            String blacklistItem = item.contains(";") ? item.split(";")[0] : item;
            int blacklistData = item.contains(";") ? Integer.valueOf(item.split(";")[1]) : 0;

            if(Material.valueOf(blacklistItem) == mat){
                if(blacklistData == 0) return true;
                else if(blacklistData == data) return true;
            }


        }
        return false;
    }
}

package io.chazza.trenchtools;

import co.aikar.commands.ACF;
import co.aikar.commands.CommandManager;
import co.aikar.commands.CommandReplacements;
import io.chazza.trenchtools.api.TrenchTool;
import io.chazza.trenchtools.command.*;
import io.chazza.trenchtools.event.TrenchMineEvent;
import io.chazza.trenchtools.util.ColorUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chazmondo
 */
public class TrenchTools extends JavaPlugin {

    private static TrenchTools core;
    private List<TrenchTool> trenchToolList;

    public static TrenchTools getCore(){
        return core;
    }
    public List<TrenchTool> getTools(){
        return trenchToolList;
    }

    public void registerTools(){
        trenchToolList = new ArrayList<>();
        for(String trenchId : getConfig().getConfigurationSection("tool").getKeys(false)){
            ItemStack tool = new ItemStack(Material.valueOf(getConfig().getString("tool."+trenchId+".item.item")));
            ItemMeta toolMeta = tool.getItemMeta();

            toolMeta.setDisplayName(ColorUtil.translate(getConfig().getString("tool."+trenchId+".item.name")));
            toolMeta.setLore(ColorUtil.translate(getConfig().getStringList("tool."+trenchId+".item.lore")));

            tool.setItemMeta(toolMeta);

            int chance = getConfig().getInt("tool."+trenchId+".setting.chance");
            boolean perm = getConfig().getBoolean("tool."+trenchId+".setting.permission");
            boolean itemDrop = getConfig().getBoolean("tool."+trenchId+".setting.item-drop");
            int radius = getConfig().getInt("tool."+trenchId+".setting.radius");


            String sound = getConfig().getString("tool."+trenchId+".setting.sound");
            boolean soundPerBlock = getConfig().getBoolean("tool."+trenchId+".setting.sound-per-block");

            new TrenchTool(trenchId)
                .withItem(tool)
                .withChance(chance)
                .withPermission(perm)
                .withRadius(radius)
                .withItemDrop(itemDrop)
                .withSound(sound)
                .withSoundPerBlock(soundPerBlock)
                .build();
        }
    }

    public void onEnable(){
        saveDefaultConfig();
        this.core = this;

        CommandManager cm = ACF.createManager(this);
        CommandReplacements cr = cm.getCommandReplacements();
        cr.addReplacement("%command%", "trenchtool|trenchtools|ttool|ttools|tool|trenchpick");
        new MainCommand(cm);
        new HelpCommand(cm);
        new GiveCommand(cm);
        new GiveAllCommand(cm);
        new ReloadCommand(cm);
        new ListCommand(cm);

        new TrenchMineEvent(this);

        registerTools();


    }

    public void onDisable(){
        this.core = null;
        saveConfig();
    }
}

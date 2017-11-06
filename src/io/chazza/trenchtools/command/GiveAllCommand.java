package io.chazza.trenchtools.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandManager;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.contexts.OnlinePlayer;
import io.chazza.trenchtools.TrenchTools;
import io.chazza.trenchtools.api.MessageSystem;
import io.chazza.trenchtools.api.TrenchTool;
import io.chazza.trenchtools.api.TrenchToolAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Chazmondo
 */
@CommandAlias("%command%")
public class GiveAllCommand extends BaseCommand {

    public GiveAllCommand(CommandManager cm){
        cm.registerCommand(this);
    }

    // /ttools give <toolid> [amount]
    @Subcommand("giveall")
    public void onCommand(CommandSender cs, String toolId, @Default("1") Integer amount){
        if(cs.hasPermission("trenchtools.giveall")){

            TrenchTool tool = TrenchToolAPI.getToolById(toolId);

            if(tool == null){
                HashMap<String, String> replace = new HashMap<>();
                replace.put("%player%", cs.getName());
                replace.put("%tier%", toolId);
                new MessageSystem("invalid").show(cs, replace);
                return;
            }

            int online = 0;
            for(Player target : Bukkit.getOnlinePlayers()){
                online++;
                TrenchToolAPI.givePick(target.getPlayer(), tool, amount);

                if(cs != target.getPlayer()) {
                    HashMap<String, String> replace = new HashMap<>();
                    replace.put("%player%", target.getName());
                    replace.put("%amount%", amount+"");
                    replace.put("%tier%", tool.getId());
                    new MessageSystem("received").show(cs, replace);
                }
            }

            HashMap<String, String> replace = new HashMap<>();
            replace.put("%player%", cs.getName());
            replace.put("%amount%", amount+"");
            replace.put("%tier%", tool.getId());
            new MessageSystem("given-all").show(cs, replace);


        }else {
            HashMap<String, String> replace = new HashMap<>();
            replace.put("%player%", cs.getName());
            replace.put("%permission%", "trenchtools.giveall");
            new MessageSystem("permission.command").show(cs, replace);
        }
    }
}

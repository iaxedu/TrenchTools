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
import org.bukkit.command.CommandSender;

import java.util.HashMap;

/**
 * Created by Chazmondo
 */
@CommandAlias("%command%")
public class GiveCommand extends BaseCommand {

    public GiveCommand(CommandManager cm){
        cm.registerCommand(this);
    }

    // /ttools give <player> <toolid> [amount]
    @Subcommand("give")
    public void onCommand(CommandSender cs, OnlinePlayer target, String toolId, @Default("1") Integer amount){
        if(cs.hasPermission("trenchtools.give")){

            TrenchTool tool = TrenchToolAPI.getToolById(toolId);

            if(tool == null){
                new MessageSystem("invalid").show(cs, new HashMap<>());
                return;
            }

            HashMap<String, String> replace = new HashMap<>();
            replace.put("%player%", target.getPlayer().getName());
            replace.put("%amount%", amount+"");
            replace.put("%tier%", tool.getId());
            new MessageSystem("given").show(cs, replace);


            if(cs != target.getPlayer()) {
                HashMap<String, String> variable = new HashMap<>();
                variable.put("%player%", target.getPlayer().getName());
                variable.put("%amount%", amount+"");
                variable.put("%tier%", tool.getId());
                new MessageSystem("received").show(cs, variable);
            }

            TrenchToolAPI.givePick(target.getPlayer(), tool, amount);


        }else {
            HashMap<String, String> replace = new HashMap<>();
            replace.put("%player%", cs.getName());
            replace.put("%permission%", "trenchtools.give");
            new MessageSystem("permission.command").show(cs, replace);
        }
    }
}

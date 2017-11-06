package io.chazza.trenchtools.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandManager;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import io.chazza.trenchtools.TrenchTools;
import io.chazza.trenchtools.api.MessageSystem;
import io.chazza.trenchtools.api.TrenchToolAPI;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

/**
 * Created by Chazmondo
 */
@CommandAlias("%command%")
public class HelpCommand extends BaseCommand {

    public HelpCommand(CommandManager cm){
        cm.registerCommand(this);
    }

    private String help(String cmd, String info){
        return "§e"+cmd+" §8- §f"+info;
    }

    @Subcommand("help")
    public void onCommand(CommandSender cs){
        if(cs.hasPermission("trenchtools.help")){

            cs.sendMessage("");
            cs.sendMessage("§6[§eTrenchTools§6] §7Commands.");
            cs.sendMessage(help(" /ttools", "Main command."));
            cs.sendMessage(help(" /ttools reload", "Reload configuration."));
            cs.sendMessage(help(" /ttools list", "List Tools."));
            cs.sendMessage(help(" /ttools give <player> <id> [amount]", "Give a tool."));
            cs.sendMessage(help(" /ttools giveall <id> [amount]", "Give-all online a tool."));
            cs.sendMessage("");

        }else {
            HashMap<String, String> replace = new HashMap<>();
            replace.put("%player%", cs.getName());
            replace.put("%permission%", "trenchtools.help");
            new MessageSystem("permission.command").show(cs, replace);
        }
    }
}

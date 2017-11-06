package io.chazza.trenchtools.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandManager;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import io.chazza.trenchtools.api.MessageSystem;
import io.chazza.trenchtools.api.TrenchToolAPI;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

/**
 * Created by Chazmondo
 */
@CommandAlias("%command%")
public class ReloadCommand extends BaseCommand {

    public ReloadCommand(CommandManager cm){
        cm.registerCommand(this);
    }

    @Subcommand("reload")
    public void onCommand(CommandSender cs){
        if(cs.hasPermission("trenchtools.reload")){
            TrenchToolAPI.getCore().reloadConfig();
            TrenchToolAPI.getCore().registerTools();
            new MessageSystem("reloaded").show(cs, new HashMap<>());


        }else {
            HashMap<String, String> replace = new HashMap<>();
            replace.put("%player%", cs.getName());
            replace.put("%permission%", "trenchtools.reload");
            new MessageSystem("permission.command").show(cs, replace);
        }
    }
}

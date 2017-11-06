package io.chazza.trenchtools.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandManager;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import com.google.common.base.Joiner;
import io.chazza.trenchtools.api.MessageSystem;
import io.chazza.trenchtools.api.TrenchTool;
import io.chazza.trenchtools.api.TrenchToolAPI;
import io.chazza.trenchtools.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chazmondo
 */
@CommandAlias("%command%")
public class ListCommand extends BaseCommand {

    public ListCommand(CommandManager cm){
        cm.registerCommand(this);
    }


    private String getTools(){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < TrenchToolAPI.getTools().size(); i++) {
            TrenchTool tool = TrenchToolAPI.getTools().get(i);

            HashMap<String, String> replacement = new HashMap<>();
            replacement.put("%tier%", tool.getId());

            if(i == TrenchToolAPI.getTools().size()-1)
                sb.append(new MessageSystem("list.format-end").get(replacement));
            else
                sb.append(new MessageSystem("list.format").get(replacement));
        }

        return sb.toString();
    }


    @Subcommand("list")
    public void onCommand(CommandSender cs){
        if(cs.hasPermission("trenchtools.list")){
            HashMap<String, String> replacement = new HashMap<>();
            replacement.put("%list%", getTools());

            cs.sendMessage(new MessageSystem("list.message").get(replacement));

        }else {
            HashMap<String, String> replace = new HashMap<>();
            replace.put("%player%", cs.getName());
            replace.put("%permission%", "trenchtools.list");
            new MessageSystem("permission.command").show(cs, replace);
        }
    }
}

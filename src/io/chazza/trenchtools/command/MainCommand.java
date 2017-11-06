package io.chazza.trenchtools.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandManager;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import io.chazza.trenchtools.TrenchTools;
import io.chazza.trenchtools.api.TrenchToolAPI;
import org.bukkit.command.CommandSender;

/**
 * Created by Chazmondo
 */
public class MainCommand extends BaseCommand {

    public MainCommand(CommandManager cm){
        cm.registerCommand(this);
    }

    @CommandAlias("%command%")
    public void onCommand(CommandSender cs){
        cs.sendMessage("§6[§eTrenchTools§6] §7Running §ev%version% §7by §6fiver.io§7."
            .replace("%version%", TrenchToolAPI.getCore().getDescription().getVersion()));
        cs.sendMessage("§6[§eTrenchTools§6] §7Developer: §eChazmondo§7.");
        cs.sendMessage("§6[§eTrenchTools§6] §7Use §e/ttools help §7for commands.");
    }
}

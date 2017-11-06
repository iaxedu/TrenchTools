package io.chazza.trenchtools.event.custom;

import io.chazza.trenchtools.api.TrenchTool;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

/**
 * Created by Chazmondo
 */
public class TPMineEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;
    private Player player;
    private TrenchTool tool;
    private List<Block> blocks;

    public TPMineEvent(Player player, TrenchTool tool, List<Block> blocks) {
        this.player = player;
        this.tool = tool;
        this.blocks = blocks;
    }

    public Player getPlayer() {
        return player;
    }

    public TrenchTool getTool() {
        return tool;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

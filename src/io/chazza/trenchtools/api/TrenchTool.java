package io.chazza.trenchtools.api;

import io.chazza.trenchtools.TrenchTools;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chazmondo
 */
public class TrenchTool {

    private String id;
    private int chance, radius;
    private ItemStack tool;
    private boolean perm, itemDrop, soundPerBlock;
    private Sound sound;

    public TrenchTool(String id) {
        this.id = id;
        this.chance = 100;
        this.radius = 3;
        this.perm = true;
        this.itemDrop = true;
        this.soundPerBlock = false;
    }

    public TrenchTool withChance(int chance) {
        this.chance = chance;
        return this;
    }

    public TrenchTool withPermission(boolean perm) {
        this.perm = perm;
        return this;
    }

    public TrenchTool withItem(ItemStack tool) {
        this.tool = tool;
        return this;
    }

    public TrenchTool withRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public TrenchTool withItemDrop(boolean itemDrop) {
        this.itemDrop = itemDrop;
        return this;
    }

    public TrenchTool withSoundPerBlock(boolean soundPerBlock) {
        this.soundPerBlock = soundPerBlock;
        return this;
    }

    public TrenchTool withSound(String sound) {
        if (sound == null || sound.isEmpty()) this.sound = null;
        else this.sound = Sound.valueOf(sound);
        return this;
    }


    public String getId() {
        return id;
    }

    public ItemStack getTool() {
        return tool;
    }

    public int getChance() {
        return chance;
    }

    public int getRadius() {
        return radius;
    }

    public boolean requirePerm() {
        return perm;
    }

    public boolean isItemDrop() {
        return itemDrop;
    }

    public boolean soundPerBlock() {
        return soundPerBlock;
    }

    public Sound getSound() {
        return sound;
    }


    public void build() {
        TrenchTools.getCore().getTools().add(this);
    }
}

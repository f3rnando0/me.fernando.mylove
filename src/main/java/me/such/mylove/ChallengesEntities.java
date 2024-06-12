package me.such.mylove;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class ChallengesEntities {
    private static Entity minecart;
    private static ItemStack wrongBlock;
    private static ItemStack rightBlock;

    public final void setMinecart(Entity minecart) {
        if(minecart.getType() == EntityType.MINECART) {
            ChallengesEntities.minecart = minecart;
        }
    }

    public final Entity getMinecart() {
        return minecart;
    }

    public final void setWrongBLock(ItemStack block) {
        wrongBlock = block.clone();
    }

    public final ItemStack getWrongBlock() {
        return wrongBlock;
    }

    public final void setRightBlock(ItemStack block) {
        rightBlock = block.clone();
    }

    public final ItemStack getRightBlock() {
        return rightBlock;
    }
}

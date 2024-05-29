package me.such.mylove;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class ChallengesEntities {
    private static Entity minecart;

    public final void setMinecart(Entity minecart) {
        if(minecart.getType() == EntityType.MINECART) {
            ChallengesEntities.minecart = minecart;
        }
    }

    public final Entity getMinecart() {
        return minecart;
    }
}

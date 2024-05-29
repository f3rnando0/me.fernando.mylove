package me.such.mylove;

import org.bukkit.Location;
import org.bukkit.World;

public class ChallengesLocations {
    private static World currentWorld;
    private static Challegens challegens;
    ChallengesLocations(World currentWorld) {
        ChallengesLocations.currentWorld = currentWorld;
    }

    public final Location getFirst() {
        return new Location(currentWorld, -79, -60, -58);
    }

    public final Location getSecond() {
        Location loc = new Location(currentWorld, -221, -51, 53.4);
        loc.setYaw(-90);
        loc.setPitch(0.8F);
        return loc;
    }

    public final Location getThird() {
        Location loc = new Location(currentWorld, -134, -60, 139.5);
        loc.setYaw(180);
        loc.setPitch(0);
        return loc;
    }

    public final Location getSecondWhichPlate(int blockX, int blockZ, Challegens challegens) {
        Location first = new Location(currentWorld, -215.5F, -51, 53.522F);
        first.setYaw(-91);
        first.setPitch(1.2F);

        Location second = new Location(currentWorld, -198.48F, -48, 35.59F);
        second.setYaw(-180);
        second.setPitch(0.5F);

        Location third = new Location(currentWorld, -193.5F, -42, 19.54F);
        third.setYaw(-89);
        third.setPitch(0.7F);

        Location quarter = new Location(currentWorld, -173.5F, -45, 30.456F);
        quarter.setYaw(-0.6F);
        quarter.setPitch(26F);

        if(blockX == -217 && blockZ == 52) {
            return first;
        } else if(blockX == -217 && blockZ == 53) {
            return first;
        } else if(blockX == -216 && blockZ == 54) {
            return first;
        } else if(blockX == -215 && blockZ == 53) {
            return first;
        } else if(blockX == -216 && blockZ == 52) {
            return first;
        } else if(blockX == -216 && blockZ == 53) {
            return first;
        }

        if(blockX == -199 && blockZ == 36) {
            return second;
        } else if(blockX == -198 && blockZ == 35) {
            return second;
        } else if(blockX == -199 && blockZ == 34) {
            return second;
        } if(blockX == -200 && blockZ == 35) {
            return second;
        } if(blockX == -199 && blockZ == 35) {
            return second;
        } if(blockX == -198 && blockZ == 36) {
            return second;
        } if(blockX == -200 && blockZ == 36) {
            return second;
        }

        if(blockX == -194 && blockZ == 18) {
            return third;
        } else if(blockX == -195 && blockZ == 19) {
            return third;
        } else if(blockX == -194 && blockZ == 20) {
            return third;
        } else if(blockX == -193 && blockZ == 19) {
            return third;
        } else if(blockX == -194 && blockZ == 19) {
            return third;
        } else if(blockX == -195 && blockZ == 18) {
            return third;
        } else if(blockX == -195 && blockZ == 20) {
            return third;
        }

        if(blockX == -174 && blockZ == 30) {
            return quarter;
        } else if(blockX == -174 && blockZ == 31) {
            return quarter;
        } else if(blockX == -173 && blockZ == 30) {
            return quarter;
        } else if(blockX == -173 && blockZ == 29) {
            return quarter;
        } else if(blockX == -174 && blockZ == 29) {
            return quarter;
        } else if(blockX == -175 && blockZ == 30) {
            return quarter;
        } else if(blockX == -175 && blockZ == 31) {
            return quarter;
        } else if(blockX == -175 && blockZ == 29) {
            return quarter;
        }

        return challegens.getSecondLevelLastCheckpoint();
    }
}

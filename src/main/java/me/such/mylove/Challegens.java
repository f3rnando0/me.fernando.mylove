package me.such.mylove;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Challegens {
    private static int level;
    private static List<Entity> firstLevelCows;
    private static int firstLevelPoints;
    private static Location secondLevelLastCheckpoint;
    private static List<Location> secondLevelCheckpointsPressed;
    private static int thirdLevelAcertos;
    private static int thirdLevelQuestion;

    Challegens() {
        firstLevelCows = new ArrayList<>();
        firstLevelPoints = 0;
        secondLevelCheckpointsPressed =  new ArrayList<>();
        thirdLevelAcertos = 0;
        thirdLevelQuestion = 1;
    }

    public final void setLevel(int levelWhich) {
        level = levelWhich;
    }

    public final int getLevel() {
        return level;
    }

    public final List<Entity> getFirstChallengeCows() {
        return firstLevelCows;
    }

    public final void addFirstChallengeCows(Entity cow) {
        firstLevelCows.add(cow);
    }

    public final void removeFirstChallengeCows(Entity cow) {
        firstLevelPoints++;
        firstLevelCows.remove(cow);
    }

    public final int getFirstLevelPoints() {
        return firstLevelPoints;
    }

    public final void resetFirstLevelPoints() {
        firstLevelPoints = 0;
    }

    public final void setSecondLevelLastCheckpoint(Location checkpoint) {
        secondLevelLastCheckpoint = checkpoint;
    }

    public final Location getSecondLevelLastCheckpoint() {
        return secondLevelLastCheckpoint;
    }

    public final List<Location> addSecondLevelCheckpointsPressed(Location checkpoint) {
        secondLevelCheckpointsPressed.add(checkpoint);
        return secondLevelCheckpointsPressed;
    }

    public final List<Location> getSecondLevelCheckpointsPressed() {
        return secondLevelCheckpointsPressed;
    }

    public final void resetSecondLastCheckpoint() {
        secondLevelLastCheckpoint = null;
    }

    public final void resetSecondCheckpointsPressed() {
        secondLevelCheckpointsPressed.clear();
    }

    public final void resetCorrectThirdLevel() {}

    public final void increaseThirdLevelAcertos() {
        thirdLevelAcertos++;
    }

    public final int getThirdLevelAcertos() {
        return thirdLevelAcertos;
    }

    public final void increaseThirdLevelQuestion() {
        thirdLevelQuestion++;
    }

    public final int getCurrentQuestionThirdLevel() {
        return thirdLevelQuestion;
    }
}

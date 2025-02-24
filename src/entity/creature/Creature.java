package entity.creature;

import entity.Entity;
import worldmap.Coordinates;
import worldmap.WorldMap;

import java.util.List;

public abstract class Creature extends Entity {
    protected int speed;
    private int hp;

    public Creature(int speed, int hp) {
        this.speed = speed;
        this.hp = hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void makeMove(List<Coordinates> steps, WorldMap worldMap) {
        if (steps.isEmpty()) {
            return;
        }
        int possibleSteps = Math.min(speed, steps.size() - 1);
        worldMap.addEntity(steps.get(possibleSteps), this);
        worldMap.removeEntity(steps.getFirst());
    }
}


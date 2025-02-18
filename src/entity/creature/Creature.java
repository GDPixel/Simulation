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

    public abstract void makeMove(List<Coordinates> steps, WorldMap worldMap);
}


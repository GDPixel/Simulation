package entity.creature;

import entity.Entity;

public abstract class Creature extends Entity {
    private int speed;
    private int hp;

    public abstract void makeMove();
}


package entity.creature;

import worldmap.Coordinates;
import worldmap.WorldMap;

import java.util.List;

public class Predator extends Creature {
    private final int attackPower;

    public Predator() {
        this(2, 10, 3);
    }

    public Predator(int speed, int hp, int attackPower) {
        super(speed, hp);
        this.attackPower = attackPower;
    }

    public void makeMove(List<Coordinates> steps, WorldMap worldMap) {
        System.out.println("Predator is moving toward: " + steps.getLast());
        // TODO:DRY
        int possibleSteps = Math.min(speed, steps.size() - 1);
        worldMap.addEntity(steps.get(possibleSteps - 1), this);
        worldMap.removeEntity(steps.getFirst());
        // TODO: possible bug remove creature if it cant move

    }
}

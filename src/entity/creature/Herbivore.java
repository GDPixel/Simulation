package entity.creature;

import worldmap.Coordinates;
import worldmap.WorldMap;

import java.util.List;

public class Herbivore extends Creature {

    public Herbivore() {
        super(4, 10);
    }

    public Herbivore(int speed, int hp) {
        super(speed, hp);
    }

    @Override
    public void makeMove(List<Coordinates> steps, WorldMap worldMap) {
        System.out.println("Herbivore is moving toward: " + steps.getLast());
        // TODO:DRY
        int possibleSteps = Math.min(speed, steps.size() - 1);
        worldMap.addEntity(steps.get(possibleSteps - 1), this);
        worldMap.removeEntity(steps.getFirst());
        // TODO: possible bug remove creature if it cant move

    }
}


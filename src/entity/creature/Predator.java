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

    @Override
    public void makeMove(List<Coordinates> steps, WorldMap worldMap) {
        // TODO: DRY and possible null
        if(isFoodNearBy(steps.getFirst())) {
            eat();
        } else {
            super.makeMove(steps, worldMap);
            System.out.println("Predator is moving toward: " + steps.getLast());
            // TODO: possible bug remove creature if it cant move
        }
    }

    private boolean isFoodNearBy(Coordinates coordinates) {
        return false;
    }

    private void eat() {
        System.out.println("eating");
    }
}

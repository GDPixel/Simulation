package entity.creature;

import entity.Eatable;
import entity.Grass;
import worldmap.Coordinates;
import worldmap.WorldMap;
import java.util.List;

public class Herbivore extends Creature implements Eatable {

    private static final int DEFAULT_MAX_HEALTH = 15;
    private static final int DEFAULT_SPEED = 5;
    private static final int HEALTH_RESTORATION_VALUE = 5;
    private static final Class<? extends Eatable> DEFAULT_FOOD = Grass.class;

    public Herbivore() {
        super(DEFAULT_SPEED, DEFAULT_MAX_HEALTH, DEFAULT_FOOD);
    }

    @Override
    public int getHealthRestorationValue() {
        return HEALTH_RESTORATION_VALUE;
    }

    @Override
    public void makeMove(Coordinates position, WorldMap worldMap) {
        List<Coordinates> foodCells = checkFoodNearBy(position, worldMap);
        System.out.println("Herbivore's Food: " + foodCells);
        if (!foodCells.isEmpty()) {
            //TODO: choose random food if more than one nearby
            Coordinates foodCell = foodCells.getFirst();
            eat(position, foodCell, worldMap);
        } else {
            super.makeMove(position, worldMap);
            //System.out.println("Predator is moving toward: " + steps.getLast());
            // TODO: possible bug remove creature if it cant move
        }
        // TODO: if no food on the map move random some steps
    }
}


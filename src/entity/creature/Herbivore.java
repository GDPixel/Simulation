package entity.creature;

import entity.Entity;
import entity.Grass;
import worldmap.Coordinates;
import worldmap.WorldMap;
import java.util.List;

public class Herbivore extends Creature {
    //TODO DRY with Predator, mb not, guees we cannot inheritance static fields, check it
    public static final int MAX_HEALTH = 10;
    private static final int DEFAULT_SPEED = 4;
    private static final Class<? extends Entity> DEFAULT_FOOD = Grass.class;

    public Herbivore() {
        super(DEFAULT_SPEED, MAX_HEALTH, DEFAULT_FOOD);
    }

    @Override
    public void makeMove(List<Coordinates> steps, WorldMap worldMap) {
        // TODO: DRY and possible null
        if (!steps.isEmpty()) {
            List<Coordinates> foodCells = checkFoodNearBy(steps.getFirst(), worldMap);
            if (!foodCells.isEmpty()) {
                eat(steps.getFirst(), foodCells, worldMap);
            } else {
                super.makeMove(steps, worldMap);
                System.out.println("Herbivore is moving toward: " + steps.getLast());
                // TODO: possible bug remove creature if it cant move
            }
        } else {
            // TODO: if no food on the map move random some steps
        }
    }

    private void eat(Coordinates position, List<Coordinates> foodCells, WorldMap worldMap) {
        //super.makeMove(List.of(position, foodCells.getFirst()), worldMap);
        worldMap.addEntity(foodCells.getFirst(), this);
        worldMap.removeEntity(position);
        System.out.println("Herbivore is eating : " + foodCells.getFirst());
    }
}


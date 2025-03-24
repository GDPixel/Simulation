package main.entity.creature;

import main.pathfinder.AStarPathfinder;
import main.pathfinder.Pathfinder;
import main.entity.Eatable;
import main.entity.Grass;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;
import java.util.List;
import java.util.Random;

public class Herbivore extends Creature implements Eatable {
    private static final int DEFAULT_MAX_HEALTH = 15;
    private static final int DEFAULT_SPEED = 3;
    private static final int HEALTH_RESTORATION_VALUE = 5;
    private static final Class<? extends Eatable> DEFAULT_FOOD = Grass.class;

    public Herbivore() {
        this(DEFAULT_SPEED, DEFAULT_MAX_HEALTH, DEFAULT_FOOD, new AStarPathfinder());
    }

    public Herbivore(int speed, int maxHp, Class<? extends Eatable> food, Pathfinder pathfinder) {
        super(speed, maxHp, food, pathfinder);
    }

    @Override
    public int getHealthRestorationValue() {
        return HEALTH_RESTORATION_VALUE;
    }

    @Override
    public void makeMove(Coordinates position, WorldMap worldMap) {
        List<Coordinates> foodCells = checkFoodNearBy(position, worldMap);
        if (!foodCells.isEmpty()) {
            Random random = new Random();
            Coordinates foodCell = foodCells.get(random.nextInt(foodCells.size()));
            eat(position, foodCell, worldMap);
        } else {
            super.makeMove(position, worldMap);
        }
    }
}


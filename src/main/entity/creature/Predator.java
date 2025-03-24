package main.entity.creature;

import main.pathfinder.BFSPathfinder;
import main.pathfinder.Pathfinder;
import main.entity.Eatable;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;

import java.util.List;
import java.util.Random;

public class Predator extends Creature {
    private static final int DEFAULT_MAX_HEALTH = 10;
    private static final int DEFAULT_SPEED = 5;
    private static final int DEFAULT_ATTACK_POWER = 3;
    private static final Class<? extends Eatable> DEFAULT_FOOD = Herbivore.class;

    private final int attackPower;

    public Predator() {
        this(DEFAULT_ATTACK_POWER);
    }

    public Predator(int attackPower) {
        this(DEFAULT_SPEED, DEFAULT_MAX_HEALTH, DEFAULT_FOOD, new BFSPathfinder(), attackPower);
    }

    public Predator(int speed, int maxHp, Class<? extends Eatable> food, Pathfinder pathfinder, int attackPower) {
        super(speed, maxHp, food, pathfinder);
        this.attackPower = attackPower;
    }

    @Override
    public void makeMove(Coordinates position, WorldMap worldMap) {
        List<Coordinates> foodCells = checkFoodNearBy(position, worldMap);

        if (!foodCells.isEmpty()) {
            attack(position, foodCells, worldMap);
        } else {
            super.makeMove(position, worldMap);
        }
    }

    private void attack(Coordinates position, List<Coordinates> foodCells, WorldMap worldMap) {
        Creature creature = (Creature) worldMap.getEntity(foodCells.getFirst());
        creature.setHp(creature.getHp() - attackPower);
        Random random = new Random();
        Coordinates foodCell = foodCells.get(random.nextInt(foodCells.size()));

        if (creature.isDead()) {
            eat(position, foodCell, worldMap);
        }
    }
}

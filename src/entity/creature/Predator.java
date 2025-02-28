package entity.creature;

import entity.Eatable;
import worldmap.Coordinates;
import worldmap.WorldMap;
import java.util.List;

public class Predator extends Creature {
    public static final int MAX_HEALTH = 10;
    private static final int DEFAULT_SPEED = 4;
    private static final int DEFAULT_ATTACK_POWER = 3;
    private static final Class<? extends Eatable> DEFAULT_FOOD = Herbivore.class;
    private final int attackPower;

    public Predator() {
        this(DEFAULT_SPEED, MAX_HEALTH, DEFAULT_FOOD, DEFAULT_ATTACK_POWER);
    }

    private Predator(int speed, int hp, Class<? extends Eatable> typeOfFood, int attackPower) {
        super(speed, hp, typeOfFood);
        this.attackPower = attackPower;
    }

    @Override
    public void makeMove(List<Coordinates> steps, WorldMap worldMap) {
        // TODO: DRY and possible null
        if (!steps.isEmpty()) {
            List<Coordinates> foodCells = checkFoodNearBy(steps.getFirst(), worldMap);
            if (!foodCells.isEmpty()) {
                attack(steps.getFirst(), foodCells, worldMap);
            } else {
                super.makeMove(steps, worldMap);
                System.out.println("Predator is moving toward: " + steps.getLast());
                // TODO: possible bug remove creature if it cant move
            }
        } else {
            // TODO: if no food on the map move random some steps
        }
    }

    private void attack(Coordinates position, List<Coordinates> foodCells, WorldMap worldMap) {
        //super.makeMove(List.of(position, foodCells.getFirst()), worldMap);

        Creature creature = (Creature) worldMap.getEntity(foodCells.getFirst());
        creature.setHp(creature.getHp() - attackPower);
        // TODO: get random cell if more than one food near
        Coordinates foodCell = foodCells.getFirst();
        System.out.println("attack : " + foodCell + " hp left: " + creature.getHp());
        if (!creature.isAlive()) {
            eat(position, foodCell, worldMap);
        }
    }
}

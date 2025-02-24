package entity.creature;

import worldmap.Coordinates;
import worldmap.WorldMap;
import worldmap.WorldMapUtil;

import java.util.List;
import java.util.stream.Collectors;

public class Predator extends Creature {
    private final int attackPower;

    public Predator() {
        this(2, 10, 5);
    }

    public Predator(int speed, int hp, int attackPower) {
        super(speed, hp);
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

    private List<Coordinates> checkFoodNearBy(Coordinates coordinates, WorldMap worldMap) {
        List<Coordinates> cellsAround = WorldMapUtil.getCellsAroundTarget(coordinates, worldMap);
        return cellsAround.stream()
                .filter(x -> worldMap.getEntity(x) instanceof Herbivore)
                .collect(Collectors.toList());
//        List<Coordinates> foodCells = new ArrayList<>();
//        for (Coordinates cell : cellsAround) {
//            if (!worldMap.isCellFree(cell) && worldMap.getEntity(cell) instanceof Herbivore) {
//                foodCells.add(cell);
//            }
//        }
        //return foodCells;
    }

    private void attack(Coordinates position, List<Coordinates> foodCells, WorldMap worldMap) {
        //super.makeMove(List.of(position, foodCells.getFirst()), worldMap);
        Herbivore herbivore = (Herbivore) worldMap.getEntity(foodCells.getFirst());
        herbivore.setHp(herbivore.getHp() - attackPower);
        System.out.println("attack : " + foodCells.getFirst() + " hp left: " + herbivore.getHp());
        if (herbivore.getHp() <= 0) {
            worldMap.addEntity(foodCells.getFirst(), this);
            worldMap.removeEntity(position);
            System.out.println("Herbivore: " + position + " is dead");
            // TODO: refill hp
            System.out.println("Predator's hp increases");
        }
    }
}

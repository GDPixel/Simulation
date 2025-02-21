package entity.creature;

import entity.Grass;
import worldmap.Coordinates;
import worldmap.WorldMap;
import worldmap.WorldMapUtil;

import java.util.ArrayList;
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

    private List<Coordinates> checkFoodNearBy(Coordinates coordinates, WorldMap worldMap) {
        List<Coordinates> cellsAround = WorldMapUtil.getCellsAroundTarget(coordinates, worldMap);
        List<Coordinates> foodCells = new ArrayList<>();
        for (Coordinates cell : cellsAround) {
            if (!worldMap.isCellFree(cell) && worldMap.getEntity(cell) instanceof Grass) {
                foodCells.add(cell);
            }
        }
        return foodCells;
    }

    private void eat(Coordinates position, List<Coordinates> foodCells, WorldMap worldMap) {
        //super.makeMove(List.of(position, foodCells.getFirst()), worldMap);
        worldMap.addEntity(foodCells.getFirst(), this);
        worldMap.removeEntity(position);
        System.out.println("eating : " + foodCells.getFirst());
    }
}


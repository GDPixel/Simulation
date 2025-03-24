package main.entity.creature;

import main.pathfinder.Pathfinder;
import main.entity.Eatable;
import main.entity.Entity;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapUtil;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Math.min;

public abstract class Creature extends Entity {
    private static final int WITHOUT_POSITION_AND_TARGET = 2;
    private final int speed;
    private final int maxHp;
    private int hp;
    private final Class<? extends Eatable> typeOfFood;
    private final Pathfinder pathfinder;

    public Creature(int speed, int maxHp, Class<? extends Eatable> typeOfFood, Pathfinder pathfinder) {
        this.speed = speed;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.typeOfFood = typeOfFood;
        this.pathfinder = pathfinder;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
         return hp;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void makeMove(Coordinates position, WorldMap worldMap) {
        List<Coordinates> path = pathfinder.find(position, typeOfFood, worldMap);
        if (!path.isEmpty()) {
            int possibleSteps = Math.min(speed, path.size() - WITHOUT_POSITION_AND_TARGET);
            WorldMapUtil.moveEntity(path.getFirst(), path.get(possibleSteps), worldMap);
        } else {
            makeRandomMove(position, worldMap);
        }
    }

    private void makeRandomMove(Coordinates position, WorldMap worldMap) {
        List<Coordinates> cellsAround = WorldMapUtil.getValidCellsAroundTarget(position, worldMap);
        List<Coordinates> freeCellsAround = cellsAround.stream()
                .filter(worldMap::isCellFree)
                .toList();

        if (freeCellsAround.isEmpty()) {
            return;
        }

        Random random = new Random();
        Coordinates freeCell = freeCellsAround.get(random.nextInt(freeCellsAround.size()));

        WorldMapUtil.moveEntity(position, freeCell, worldMap);
    }

    protected List<Coordinates> checkFoodNearBy(Coordinates coordinates, WorldMap worldMap) {
        List<Coordinates> cellsAround = WorldMapUtil.getValidCellsAroundTarget(coordinates, worldMap);
        return cellsAround.stream()
                .filter(cell -> typeOfFood.isInstance(worldMap.getEntity(cell)))
                .toList();
    }

    protected void eat(Coordinates position, Coordinates foodCell, WorldMap worldMap) {
        Entity entity = worldMap.getEntity(foodCell);
        if (entity instanceof Eatable food) {
            setHp(min(getHp() + food.getHealthRestorationValue(), maxHp));
        }

        WorldMapUtil.moveEntity(position, foodCell, worldMap);
    }
}


package entity.creature;

import bfs.BFS;
import entity.Eatable;
import entity.Entity;
import worldmap.Coordinates;
import worldmap.WorldMap;
import worldmap.WorldMapUtil;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.min;

public abstract class Creature extends Entity {

    private final int speed;
    private final int maxHp;
    private int hp;
    protected Class<? extends Eatable> typeOfFood;

    public Creature(int speed, int maxHp, Class<? extends Eatable> typeOfFood) {
        this.speed = speed;
        this.maxHp = maxHp;
        this.hp = maxHp;

        this.typeOfFood = typeOfFood;
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

    public boolean isAlive() {
        return hp > 0;
    }

    public void makeMove(Coordinates position, WorldMap worldMap) {
        BFS bfs = new BFS(worldMap, position, typeOfFood);
        List<Coordinates> steps = bfs.findPath();
        if (!steps.isEmpty()) {
            System.out.println(this.getClass() + " is moving toward: " + steps.getLast());
            // TODO: think of moving toward food if food last step stay close to it (step.size() - 2)
            // TODO: if not, go further (step.size() - 1)
            int possibleSteps = Math.min(speed, steps.size() - 2);
            worldMap.addEntity(steps.get(possibleSteps), this);
            worldMap.removeEntity(steps.getFirst());
        }
    }

    protected List<Coordinates> checkFoodNearBy(Coordinates coordinates, WorldMap worldMap) {
        List<Coordinates> cellsAround = WorldMapUtil.getValidCellsAroundTarget(coordinates, worldMap);
        return cellsAround.stream()
                .filter(x -> {
                    Entity entity = worldMap.getEntity(x);
                    return typeOfFood.isInstance(entity);
                })
                .collect(Collectors.toList());
    }

    protected void eat(Coordinates position, Coordinates foodCell, WorldMap worldMap) {
        //TODO: check if more than one food near
        //super.makeMove(List.of(position, foodCells.getFirst()), worldMap);

        Entity entity = worldMap.getEntity(foodCell);
        if (entity instanceof Eatable food) {
            System.out.println(this.getClass() + " hp before eating is " + getHp());
            setHp(min(getHp() + food.getHealthRestorationValue(), maxHp));
            System.out.println(this.getClass() + " hp is " + getHp());
        }

        //TODO: move entity from, to
        worldMap.addEntity(foodCell, this);
        worldMap.removeEntity(position);

        System.out.println(this.getClass() + " is eating : " + foodCell);
    }
}


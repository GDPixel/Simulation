package entity.creature;

import bfs.AStar;
import bfs.BFS;
import bfs.Search;
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
    private final Class<? extends Eatable> typeOfFood;
    private final Search bfs = new AStar();

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

    public boolean isDead() {
        return hp <= 0;
    }

    public void makeMove(Coordinates position, WorldMap worldMap) {
        List<Coordinates> path = bfs.findPath(worldMap, position, typeOfFood);
        if (!path.isEmpty()) {
            System.out.println(this.getClass().getSimpleName() + " is moving toward: " + path.getLast());
            // TODO: think of moving toward food if food last step stay close to it (step.size() - 2)
            // TODO: if not, go further (step.size() - 1)
            System.out.println(path.size() + " : " + path);
            int possibleSteps = Math.min(speed, path.size() - 2);
            System.out.println(possibleSteps);
            System.out.println("Went to: " + path.get(possibleSteps));
            worldMap.addEntity(path.get(possibleSteps), this);
            worldMap.removeEntity(path.getFirst());
        }
    }

    protected List<Coordinates> checkFoodNearBy(Coordinates coordinates, WorldMap worldMap) {
        List<Coordinates> cellsAround = WorldMapUtil.getValidCellsAroundTarget(coordinates, worldMap);
        return cellsAround.stream()
                .filter(cell -> typeOfFood.isInstance(worldMap.getEntity(cell)))
                .collect(Collectors.toList());
    }

    protected void eat(Coordinates position, Coordinates foodCell, WorldMap worldMap) {
        //TODO: check if more than one food near
        //super.makeMove(List.of(position, foodCells.getFirst()), worldMap);

        Entity entity = worldMap.getEntity(foodCell);
        if (entity instanceof Eatable food) {
            //System.out.println(this.getClass().getSimpleName() + " hp before eating is " + getHp());
            setHp(min(getHp() + food.getHealthRestorationValue(), maxHp));
            //System.out.println(this.getClass().getSimpleName() + " hp is " + getHp());
        }

        //TODO: move entity from, to
        worldMap.addEntity(foodCell, this);
        worldMap.removeEntity(position);

        System.out.println(this.getClass().getSimpleName() + " is eating : " + foodCell);
    }
}


package entity.creature;

import entity.Entity;
import worldmap.Coordinates;
import worldmap.WorldMap;
import worldmap.WorldMapUtil;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Creature extends Entity {
    private final int speed;
    private int hp;
    protected Class<? extends Entity> typeOfFood;

    public Creature(int speed, int hp, Class<? extends Entity> typeOfFood) {
        this.speed = speed;
        this.hp = hp;
        this.typeOfFood = typeOfFood;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
         return hp;
    }

    public void makeMove(List<Coordinates> steps, WorldMap worldMap) {
        if (steps.isEmpty()) {
            return;
        }
        // TODO: think of moving toward food if food last step stay close to it (step.size() - 2)
        // TODO: if not, go further (step.size() - 1)
        int possibleSteps = Math.min(speed, steps.size() - 2);
        worldMap.addEntity(steps.get(possibleSteps), this);
        worldMap.removeEntity(steps.getFirst());
    }

    protected List<Coordinates> checkFoodNearBy(Coordinates coordinates, WorldMap worldMap) {
        List<Coordinates> cellsAround = WorldMapUtil.getCellsAroundTarget(coordinates, worldMap);
        return cellsAround.stream()
                .filter(x -> {
                    Entity entity = worldMap.getEntity(x);
                    return typeOfFood.isInstance(entity);
                })
                .collect(Collectors.toList());
    }
}


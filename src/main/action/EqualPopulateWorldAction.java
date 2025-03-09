package main.action;

import main.EntitySpawner;
import main.entity.*;
import main.entity.creature.*;
import main.worldmap.WorldMap;

import java.util.List;

public class EqualPopulateWorldAction extends Action {
    private final static int TOTAL_ENTITY_TYPES = 5;
    private final int fillPercentage;

    public EqualPopulateWorldAction(int fillPercentage) {
        validateFillPercentage(fillPercentage);
        this.fillPercentage = fillPercentage;
    }

    @Override
    public void execute(WorldMap worldMap) {
        int totalCoordinates = worldMap.getMaxRow() * worldMap.getMaxColumn();
        int eachEntityToAdd = (totalCoordinates * fillPercentage / 100) / TOTAL_ENTITY_TYPES;
        //System.out.println("How many of each main.entity to add: " + eachEntityToAdd);
        //System.out.println("Total entities to add: " + totalEntitiesToAdd);

        List<EntitySpawner> spawners = List.of(
                new EntitySpawner(Rock::new, eachEntityToAdd, worldMap),
                new EntitySpawner(Grass::new, eachEntityToAdd, worldMap),
                new EntitySpawner(Tree::new, eachEntityToAdd, worldMap),
                new EntitySpawner(Herbivore::new, eachEntityToAdd, worldMap),
                new EntitySpawner(Predator::new, eachEntityToAdd, worldMap)
        );

        spawners.forEach(EntitySpawner::create);
    }

    private void validateFillPercentage(int fillPercentage) {
        if (fillPercentage < 0 || fillPercentage > 100) {
            throw new IllegalArgumentException("fillPercent must be between 0 and 100");
        }
    }
}



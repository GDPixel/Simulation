package action;

import entity.*;
import entity.creature.Herbivore;
import entity.creature.Predator;
import worldmap.Coordinates;
import worldmap.WorldMap;
import worldmap.WorldMapUtil;

import java.util.ArrayList;
import java.util.List;

public class PopulateWorldAction extends Action {
    private final int fillPercentage;

    public PopulateWorldAction(int fillPercentage) {
        this.fillPercentage = fillPercentage;
    }

    @Override
    public void execute(WorldMap worldMap) {
        populate(worldMap);
    }

    private void populate(WorldMap worldMap) {
        if (fillPercentage < 0 || fillPercentage > 100 ) {
            throw new IllegalArgumentException("fillPercent must be between 0 and 100");
        }
        int maxCoordinates = worldMap.getMaxRow() * worldMap.getMaxColumn();
        System.out.println("max free cells: " + maxCoordinates);

        //TODO: refactor
        List<Class<? extends Entity>> entityTypes = new ArrayList<>();
        entityTypes.add(Predator.class);
        entityTypes.add(Herbivore.class);
        entityTypes.add(Grass.class);
        entityTypes.add(Rock.class);
        entityTypes.add(Tree.class);
        int totalEntityTypes = entityTypes.size();
        int eachEntityToAdd = (maxCoordinates * fillPercentage / 100) / totalEntityTypes;
        System.out.println("How many of each entity to add: " + eachEntityToAdd);
        int totalEntitiesToAdd = eachEntityToAdd * totalEntityTypes;
        System.out.println("Total entities to add: " + totalEntitiesToAdd);

        for (var entityType : entityTypes) {
            for (int i = 0; i < eachEntityToAdd; i++) {
                Coordinates freeCell = WorldMapUtil.getRandomFreeCell(worldMap);
                worldMap.addEntity(freeCell, createEntity(entityType));
            }
        }
    }

    private Entity createEntity(Class<? extends Entity> clazz) {
        // TODO: think how to refactor, look like pattern Factory
        if (clazz == Grass.class) {
            return new Grass();
        } else if (clazz == Rock.class) {
            return new Rock();
        } else if (clazz == Tree.class) {
            return new Tree();
        } else if (clazz == Herbivore.class) {
            return new Herbivore();
        } else if (clazz == Predator.class) {
            return new Predator();
        } else {
            throw new IllegalArgumentException("Unknown entity class: " + clazz);
        }
    }
}



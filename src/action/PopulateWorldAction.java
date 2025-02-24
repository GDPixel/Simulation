package action;

import entity.*;
import entity.creature.Herbivore;
import entity.creature.Predator;
import worldmap.Coordinates;
import worldmap.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopulateWorldAction extends Action {
    protected WorldMap worldMap;

    public PopulateWorldAction(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public void execute() {
        populate(25 );
    }

    private void populate(int fillPercent) {
        if (fillPercent < 0 || fillPercent > 100 ) {
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
        int eachEntityToAdd = (maxCoordinates * fillPercent / 100) / totalEntityTypes;
        System.out.println("How many of each entity to add: " + eachEntityToAdd);
        int totalEntitiesToAdd = eachEntityToAdd * totalEntityTypes;
        System.out.println("Total entities to add: " + totalEntitiesToAdd);

        for (var entityType : entityTypes) {
            for (int i = 0; i < eachEntityToAdd; i++) {
                Coordinates freeCell = getRandomFreeCell();
                worldMap.addEntity(freeCell, createEntity(entityType));
            }
        }
    }


    private Entity createEntity(Class<? extends Entity> clazz) {
        // TODO: think how to refactor
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

    private Coordinates getRandomFreeCell() {
        Random rand = new Random();
        while (true) {
            int row = rand.nextInt(worldMap.getMaxRow());
            int col = rand.nextInt(worldMap.getMaxColumn());
            // TODO: don't like it every time create new Coordinate
            // think what to do
            Coordinates coordinates = new Coordinates(row, col);
            if (worldMap.isCellFree(coordinates)) {
                return coordinates;
            }
        }
    }

}

class TestPopulateWorld {
    public static void main(String[] args) {
        PopulateWorldAction world = new PopulateWorldAction(new WorldMap(10, 10));
        world.execute();
    }
}


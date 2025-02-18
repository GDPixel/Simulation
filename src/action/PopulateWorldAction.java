package action;

import entity.*;
import entity.creature.Herbivore;
import entity.creature.Predator;
import worldmap.Coordinates;
import worldmap.WorldMap;

import java.util.Random;

public class PopulateWorldAction extends Action {
    protected WorldMap worldMap;
    private final int totalEntityTypes;

    public PopulateWorldAction(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.totalEntityTypes = EntityType.values().length;
    }

    @Override
    public void execute() {
        populate(20 );
    }

    private void populate(int fillPercent) {
        if (fillPercent < 0 || fillPercent > 100 ) {
            throw new IllegalArgumentException("fillPercent must be between 0 and 100");
        }
        int maxCoordinates = worldMap.getMaxRow() * worldMap.getMaxCol();
        System.out.println("max free cells: " + maxCoordinates);
        int eachEntityToAdd = (maxCoordinates * fillPercent / 100) / totalEntityTypes;
        System.out.println("How many of each entity to add: " + eachEntityToAdd);
        int totalEntitiesToAdd = eachEntityToAdd * totalEntityTypes;
        System.out.println("Total entities to add: " + totalEntitiesToAdd);
        for (EntityType type : EntityType.values()) {
            for (int i = 0; i < eachEntityToAdd; i++) {
                Coordinates freeCell = getRandomFreeCell();
                worldMap.addEntity(freeCell, createEntity(type));
            }
        }
    }

    private Entity createEntity(EntityType type) {
        return switch (type) {
            case GRASS -> new Grass();
            case ROCK -> new Rock();
            case TREE -> new Tree();
            case HERBIVORE -> new Herbivore();
            case PREDATOR -> new Predator();
            default ->
                //TODO: add exception
                    null; // Handle unknown types gracefully
        };
    }

    private Coordinates getRandomFreeCell() {
        Random rand = new Random();
        while (true) {
            int row = rand.nextInt(worldMap.getMaxRow());
            int col = rand.nextInt(worldMap.getMaxCol());
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


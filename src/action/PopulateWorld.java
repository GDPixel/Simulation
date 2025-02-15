package action;

import entity.*;
import entity.creature.Herbivore;
import entity.creature.Predator;
import worldmap.Coordinate;
import worldmap.WorldMap;

import java.util.Random;

public class PopulateWorld extends Action {
    private WorldMap worldMap;
    private int totalEntityTypes;

    public PopulateWorld(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.totalEntityTypes = EntityType.values().length;
    }

    @Override
    public void execute() {
        populate(80);
    }

    private void populate(int fillPercent) {
        int maxCoordinates = worldMap.getMaxRow() * worldMap.getMaxCol();
        System.out.println("max free cells: " + maxCoordinates);
        int eachEntityToAdd = (maxCoordinates * fillPercent / 100) / totalEntityTypes;
        System.out.println("How many of each entity to add: " + eachEntityToAdd);
        int totalEntitiesToAdd = eachEntityToAdd * totalEntityTypes;
        System.out.println("Total entites to add: " + totalEntitiesToAdd);
        for (EntityType type : EntityType.values()) {
            for (int i = 0; i < eachEntityToAdd; i++) {
                Coordinate freeCell = getRandomFreeCell();
                worldMap.addEntity(freeCell, createEntity(type));
            }
        }
    }

    private Entity createEntity(EntityType type) {
        switch (type) {
            case GRASS:
                return new Grass();
            case ROCK:
                return new Rock();
            case TREE:
                return new Tree();
            case HERBIVORE:
                return new Herbivore();
            case PREDATOR:
                return new Predator();
            default:
                return null; // Handle unknown types gracefully
        }
    }

    private Coordinate getRandomFreeCell() {
        Random rand = new Random();
        while (true) {
            int row = rand.nextInt(worldMap.getMaxRow());
            int col = rand.nextInt(worldMap.getMaxCol());
            // TODO: don't like it every time create new Coordinate
            // think what to do
            Coordinate coordinate = new Coordinate(row, col);
            if (worldMap.isCellFree(coordinate)) {
                return coordinate;
            }
        }
    }

}

class TestPopulateWorld {
    public static void main(String[] args) {
        PopulateWorld world = new PopulateWorld(new WorldMap(10, 10));
        world.execute();
    }
}

package main.worldmap;

import main.entity.Entity;
import main.entity.creature.Creature;
import main.exception.WorldMapFullException;

import java.util.List;
import java.util.Random;


public final class WorldMapUtil {

    private static final List<Coordinates> DIRECTIONS = List.of(
            new Coordinates(-1, -1),
            new Coordinates(-1, 0),
            new Coordinates(-1, 1),

            new Coordinates(0, -1),
            new Coordinates(0, 1),

            new Coordinates(1, -1),
            new Coordinates(1, 0),
            new Coordinates(1, 1)
    );

    private WorldMapUtil() {
    }

    public static List<Coordinates> getValidCellsAroundTarget(Coordinates target, WorldMap worldMap) {

        return DIRECTIONS.stream()
                .map(direction -> new Coordinates(
                        target.row() + direction.row(),
                        target.column() + direction.column()))
                .filter(worldMap::isCoordinateValid)
                .toList();

    }

    public static List<Coordinates> getAllCoordinatesWithCreatures(WorldMap worldMap) {
        List<Coordinates> allEntitiesCoordinates = worldMap.getAllCoordinatesWithEntities();
        return allEntitiesCoordinates.stream()
                .filter(c -> worldMap.getEntity(c) instanceof Creature)
                .toList();

    }

    public static Coordinates getRandomFreeCell(WorldMap worldMap) {
        if (worldMap.isFull()) {
            throw new WorldMapFullException("WorldMap is full, can't get free cell");
        }
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

    public static void moveEntity(Coordinates from, Coordinates to, WorldMap worldMap) {
        Entity entity = worldMap.getEntity(from);
        worldMap.addEntity(to, entity);
        worldMap.removeEntity(from);
    }
}

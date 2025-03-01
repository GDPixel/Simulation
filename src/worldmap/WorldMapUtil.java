package worldmap;

import entity.creature.Creature;
import exception.WorldMapFullException;

import java.util.List;
import java.util.Random;

public class WorldMapUtil {

    private WorldMapUtil() {
    }

    public static List<Coordinates> getValidCellsAroundTarget(Coordinates target, WorldMap worldMap) {
        List<Coordinates> surroundingCells = List.of(
                new Coordinates(target.row() - 1, target.column() - 1),
                new Coordinates(target.row() - 1, target.column()),
                new Coordinates(target.row() - 1, target.column() + 1),

                new Coordinates(target.row(), target.column() - 1),
                new Coordinates(target.row(), target.column() + 1),

                new Coordinates(target.row() + 1, target.column() - 1),
                new Coordinates(target.row() + 1, target.column()),
                new Coordinates(target.row() + 1, target.column() + 1)
        );

        return surroundingCells.stream()
                .filter(coordinates -> isCellOnMap(coordinates, worldMap))
                .toList();
    }

    private static boolean isCellOnMap(Coordinates coordinates, WorldMap worldMap) {
        return coordinates.row() >= 0 && coordinates.row() < worldMap.getMaxRow()
                && coordinates.column() >= 0 && coordinates.column() < worldMap.getMaxColumn();
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
}

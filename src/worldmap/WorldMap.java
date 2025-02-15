package worldmap;

import entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private final int maxRow;
    private final int maxCol;
    private final Map<Coordinate, Entity> entities;

    public WorldMap(int maxRow, int maxCol) {
        this.maxRow = maxRow;
        this.maxCol = maxCol;
        entities = new HashMap<Coordinate, Entity>();
    }

    public int getMaxRow() {
        return maxRow;
    }

    public int getMaxCol() {
        return maxCol;
    }

    public Map<Coordinate, Entity> getEntities() {
        return entities;
    }

    public void addEntity(Coordinate coordinate, Entity entity) {
        if (coordinate.getRow() >= maxRow || coordinate.getCol() >= maxCol) {
            throw new IllegalArgumentException("Coordinates (%d, %d) in WorldMap out of bounds"
                    .formatted(coordinate.getRow(), coordinate.getCol()));
        }

        if (coordinate.getRow() < 0 || coordinate.getCol() < 0) {
            throw new IllegalArgumentException("Coordinates (%d, %d) in WorldMap can't be negative"
                    .formatted(coordinate.getRow(), coordinate.getCol()));
        }
        entities.put(coordinate, entity);
    }

    public boolean isCellFree(Coordinate coordinate) {
        return !entities.containsKey(coordinate);
    }
}


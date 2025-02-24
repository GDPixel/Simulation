package worldmap;

import entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private final int maxRow;
    private final int maxColumn;
    private final Map<Coordinates, Entity> entities;

    public WorldMap(int maxRow, int maxColumn) {
        this.maxRow = maxRow;
        this.maxColumn = maxColumn;
        entities = new HashMap<>();
    }

    public int getMaxRow() {
        return maxRow;
    }

    public int getMaxColumn() {
        return maxColumn;
    }

    public Entity getEntity(Coordinates coordinates) {
        // TODO: DRY
        if (coordinates.getRow() >= maxRow || coordinates.getCol() >= maxColumn) {
            throw new IllegalArgumentException("Coordinates (%d, %d) in WorldMap out of bounds"
                    .formatted(coordinates.getRow(), coordinates.getCol()));
        }

        if (coordinates.getRow() < 0 || coordinates.getCol() < 0) {
            throw new IllegalArgumentException("Coordinates (%d, %d) in WorldMap can't be negative"
                    .formatted(coordinates.getRow(), coordinates.getCol()));
        }

        return entities.get(coordinates);
    }

    public void addEntity(Coordinates coordinates, Entity entity) {
        // TODO: DRY
        if (coordinates.getRow() >= maxRow || coordinates.getCol() >= maxColumn) {
            throw new IllegalArgumentException("Coordinates (%d, %d) in WorldMap out of bounds"
                    .formatted(coordinates.getRow(), coordinates.getCol()));
        }

        if (coordinates.getRow() < 0 || coordinates.getCol() < 0) {
            throw new IllegalArgumentException("Coordinates (%d, %d) in WorldMap can't be negative"
                    .formatted(coordinates.getRow(), coordinates.getCol()));
        }
        entities.put(coordinates, entity);
    }

    public boolean isCellFree(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
    }

    public void removeEntity(Coordinates coordinates) {
        // TODO: DRY
        if (coordinates.getRow() >= maxRow || coordinates.getCol() >= maxColumn) {
            throw new IllegalArgumentException("Coordinates (%d, %d) in WorldMap out of bounds"
                    .formatted(coordinates.getRow(), coordinates.getCol()));
        }

        if (coordinates.getRow() < 0 || coordinates.getCol() < 0) {
            throw new IllegalArgumentException("Coordinates (%d, %d) in WorldMap can't be negative"
                    .formatted(coordinates.getRow(), coordinates.getCol()));
        }

        entities.remove(coordinates);
    }
}


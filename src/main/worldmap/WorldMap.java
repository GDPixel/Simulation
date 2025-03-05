package main.worldmap;

import main.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldMap {
    private final int maxRow;
    private final int maxColumn;
    private final Map<Coordinates, Entity> entities;

    public WorldMap(int maxRow, int maxColumn) {
        if (maxRow < 1 || maxColumn < 1) {
            throw new IllegalArgumentException("maxRow and maxColumn must be greater than 0");
        }
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
        validateCoordinates(coordinates);
        return entities.get(coordinates);
    }

    public void addEntity(Coordinates coordinates, Entity entity) {
        validateCoordinates(coordinates);
        entities.put(coordinates, entity);
    }

    public List<Coordinates> getAllCoordinatesWithEntities() {
        return new ArrayList<>(entities.keySet());
    }

    public boolean isCellFree(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
    }

    public void removeEntity(Coordinates coordinates) {
        validateCoordinates(coordinates);
        entities.remove(coordinates);
    }

    private void validateCoordinates(Coordinates coordinates) {
        if (coordinates.row() >= maxRow || coordinates.column() >= maxColumn) {
            throw new IllegalArgumentException("Coordinates (%d, %d) in WorldMap out of bounds"
                    .formatted(coordinates.row(), coordinates.column()));
        }

        if (coordinates.row() < 0 || coordinates.column() < 0) {
            throw new IllegalArgumentException("Coordinates (%d, %d) in WorldMap can't be negative"
                    .formatted(coordinates.row(), coordinates.column()));
        }
    }

    public boolean isFull() {
        return maxRow * maxColumn <= entities.size();
    }
}


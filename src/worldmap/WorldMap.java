package worldmap;

import entity.Entity;

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

    public List<Coordinates> getAllCoordinatesWithEntities() {
        List<Coordinates> coordinateWithEntities = new ArrayList<>();
        for (Coordinates coordinates : entities.keySet()) {
            coordinateWithEntities.add(coordinates);
        }

        return coordinateWithEntities;
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


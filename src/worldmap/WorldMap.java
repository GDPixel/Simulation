package worldmap;

import entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private final int maxRow;
    private final int maxCol;
    private final Map<Coordinates, Entity> entities;

    public WorldMap(int maxRow, int maxCol) {
        this.maxRow = maxRow;
        this.maxCol = maxCol;
        entities = new HashMap<Coordinates, Entity>();
    }

    public int getMaxRow() {
        return maxRow;
    }

    public int getMaxCol() {
        return maxCol;
    }

    // TODO: dont forget u violate encapsulation
    public Map<Coordinates, Entity> getEntities() {
        return entities;
    }

    public void addEntity(Coordinates coordinates, Entity entity) {
        if (coordinates.getRow() >= maxRow || coordinates.getCol() >= maxCol) {
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
}


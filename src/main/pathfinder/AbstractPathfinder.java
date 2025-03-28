package main.pathfinder;

import main.entity.Eatable;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;

import java.util.Set;

public abstract class AbstractPathfinder implements Pathfinder {

    protected long countTargets(Class<? extends Eatable> target, WorldMap worldMap) {
        return worldMap.getAllCellsWithEntities().stream()
                .filter(coordinates -> worldMap.getEntity(coordinates).getClass() == target)
                .count();
    }

    protected boolean isTargetFound(Coordinates coordinates, Class<? extends Eatable> target, WorldMap worldMap) {
        return !worldMap.isCellFree(coordinates) && (worldMap.getEntity(coordinates).getClass() == target);
    }

    protected boolean isValidMove(Set<Coordinates> visited, Coordinates cell, Class<? extends Eatable> target, WorldMap worldMap) {
        return !visited.contains(cell) &&
                (worldMap.isCellFree(cell) || isTargetFound(cell, target, worldMap));
    }
}

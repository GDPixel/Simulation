package main.pathfinder;

import main.entity.Eatable;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapUtil;

import java.util.*;

public class BFSPathfinder extends AbstractPathfinder {
    public List<Coordinates> find(Coordinates start, Class<? extends Eatable> target, WorldMap worldMap) {

        if (countTargets(target, worldMap) == 0) {
            return Collections.emptyList();
        }

        Set<Coordinates> visited = new HashSet<>();
        Queue<Coordinates> cellsToExplore = new LinkedList<>();
        Map<Coordinates, Coordinates> backTrace = new HashMap<>();

        cellsToExplore.add(start);

        while (!cellsToExplore.isEmpty()) {
            Coordinates current = cellsToExplore.remove();
            visited.add(current);

            if (isTargetFound(current, target, worldMap)) {
                return reconstructPath(backTrace, current, start);
            }

            List<Coordinates> neighborCells = WorldMapUtil.getValidCellsAroundTarget(current, worldMap);
            for (Coordinates neighborCell : neighborCells) {
                if (isValidMove(visited, neighborCell, target, worldMap)) {
                    cellsToExplore.add(neighborCell);
                    backTrace.put(neighborCell, current);
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Coordinates> reconstructPath(Map<Coordinates, Coordinates> backTrace, Coordinates start, Coordinates end) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates current = start;
        while (current != end) {
            path.add(current);
            current = backTrace.get(current);
        }

        path.add(end);

        Collections.reverse(path);
        return path;
    }
}

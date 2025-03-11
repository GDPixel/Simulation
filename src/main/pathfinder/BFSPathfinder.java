package main.pathfinder;

import main.entity.Eatable;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapUtil;

import java.util.*;

public class BFSPathfinder extends AbstractPathfinder {

    public List<Coordinates> find(Coordinates start, Class<? extends Eatable> target, WorldMap worldMap) {

        if (targetCount(worldMap, target) == 0) {
            return Collections.emptyList();
        }

        Set<Coordinates> visited = new HashSet<>();
        Queue<Coordinates> queue = new LinkedList<>();
        Map<Coordinates, Coordinates> backTrace = new HashMap<>();

        queue.add(start);

        while (!queue.isEmpty()) {
            Coordinates current = queue.remove();
            visited.add(current);

            if (isTargetEntity(current, target, worldMap)) {
                return reconstructPath(backTrace, current, start);
            }

            List<Coordinates> adjacentCells = WorldMapUtil.getValidCellsAroundTarget(current, worldMap);
            for (Coordinates adjacentCell : adjacentCells) {
                if (isValidMove(visited, adjacentCell, target, worldMap)) {
                    queue.add(adjacentCell);
                    backTrace.put(adjacentCell, current);
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

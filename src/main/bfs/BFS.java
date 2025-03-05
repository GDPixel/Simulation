package main.bfs;

import main.entity.Eatable;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapUtil;

import java.util.*;

public class BFS implements Search {

    public List<Coordinates> findPath(WorldMap worldMap, Coordinates start, Class<? extends Eatable> target) {
        Set<Coordinates> visited = new HashSet<>();
        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(start);
        Map<Coordinates, Coordinates> backTrace = new HashMap<>();
        while (!queue.isEmpty()) {
            Coordinates currentCoordinates = queue.remove();
            visited.add(currentCoordinates);
            if (!worldMap.isCellFree(currentCoordinates) && worldMap.getEntity(currentCoordinates).getClass() == target) {
                return reconstructPath(backTrace, currentCoordinates, start);
            }

            List<Coordinates> adjacentCells = WorldMapUtil.getValidCellsAroundTarget(currentCoordinates, worldMap);
            for (Coordinates adjacentCell : adjacentCells) {
                if ((!visited.contains(adjacentCell))
                        && (worldMap.isCellFree(adjacentCell)
                        || (worldMap.getEntity(adjacentCell).getClass() == target))) {
                    queue.add(adjacentCell);
                    backTrace.put(adjacentCell, currentCoordinates);
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

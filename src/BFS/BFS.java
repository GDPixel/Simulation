package BFS;

import entity.Entity;
import worldmap.Coordinates;
import worldmap.WorldMap;

import java.util.*;

public class BFS {
    private final WorldMap worldMap;
    private final Coordinates start;
    private final Entity target;


    public BFS(WorldMap worldMap, Coordinates start, Entity target) {
        this.worldMap = worldMap;
        this.start = start;
        this.target = target;
    }

    public List<Coordinates> findPath() {
        Set<Coordinates> visited = new HashSet<>();
        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(start);
        Map<Coordinates, Coordinates> backTrace = new HashMap<>();
        while (!queue.isEmpty()) {
            Coordinates currentCoordinates = queue.remove();
            visited.add(currentCoordinates);
            if (!worldMap.isCellFree(currentCoordinates) && worldMap.getEntity(currentCoordinates).getClass() == target.getClass()) {
                return reconstructPath(backTrace, currentCoordinates, start);
            }

            List<Coordinates> adjacentCells = getAdjacentCells(currentCoordinates);
            for (Coordinates adjacentCell : adjacentCells) {
                if ((!visited.contains(adjacentCell))
                        && (worldMap.isCellFree(adjacentCell)
                        || (worldMap.getEntity(adjacentCell).getClass() == target.getClass()))) {
                    queue.add(adjacentCell);
                    backTrace.put(adjacentCell, currentCoordinates);
                }
            }
        }

        return Collections.emptyList();
    }

    private List<Coordinates> getAdjacentCells(Coordinates currentCell) {
        Coordinates leftUp = new Coordinates(currentCell.getRow() - 1, currentCell.getCol() - 1);
        Coordinates up = new Coordinates(currentCell.getRow() - 1, currentCell.getCol());
        Coordinates rightUp = new Coordinates(currentCell.getRow() - 1, currentCell.getCol() + 1);

        Coordinates left = new Coordinates(currentCell.getRow(), currentCell.getCol() - 1);
        Coordinates right = new Coordinates(currentCell.getRow(), currentCell.getCol() + 1);

        Coordinates leftDown = new Coordinates(currentCell.getRow() + 1, currentCell.getCol() - 1);
        Coordinates down = new Coordinates(currentCell.getRow() + 1, currentCell.getCol());
        Coordinates rightDown = new Coordinates(currentCell.getRow() + 1, currentCell.getCol() + 1);


        List<Coordinates> result = new ArrayList<>();
        for (var coordinates : List.of(leftUp, up, rightUp, left, right, rightDown, leftDown, down, rightDown)) {
            if (isCellOnMap(coordinates)) {
                result.add(coordinates);
            }
        }

        return result;
    }

    private boolean isCellOnMap(Coordinates coordinates) {
        return coordinates.getRow() >= 0 && coordinates.getRow() < worldMap.getMaxRow()
                && coordinates.getCol() >= 0 && coordinates.getCol() < worldMap.getMaxCol();
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

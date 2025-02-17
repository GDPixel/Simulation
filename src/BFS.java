import entity.Entity;
import entity.Grass;
import worldmap.Coordinates;
import worldmap.WorldMap;

import java.util.*;

public class BFS {
    private final WorldMap worldMap;
    private final Coordinates start;
    private final Entity target;


    public BFS(WorldMap worldMap, Coordinates start, Grass target) {
        this.worldMap = worldMap;
        this.start = start;
        this.target = target;
    }

    public List<Coordinates> findPath() {
        // TODO: bug move through occupied cell
        Set<Coordinates> visited = new HashSet<Coordinates>();
        Queue<Coordinates> queue = new LinkedList<Coordinates>();
        queue.add(start);
        //TODO: violate encapsulation
        Map<Coordinates, Entity> entities = worldMap.getEntities();
        Map<Coordinates, Coordinates> backTrace = new HashMap<>();
        while (!queue.isEmpty()) {
            Coordinates currentCoordinates = queue.remove();
            visited.add(currentCoordinates);
            if (entities.containsKey(currentCoordinates) && entities.get(currentCoordinates).getClass() == target.getClass()) {
                System.out.println("Found it");
                System.out.println(currentCoordinates.getRow() + " " + currentCoordinates.getCol());
                return reconstructPath(backTrace, currentCoordinates, start);
            }

            List<Coordinates> adjacentCells = getAdjacentCells(currentCoordinates);
            for (Coordinates adjacentCell : adjacentCells) {
                if ((!visited.contains(adjacentCell))
                        && (worldMap.isCellFree(adjacentCell)
                        || (entities.get(adjacentCell).getClass() == target.getClass()))) {
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
        for (var coordinates : List.of(leftUp, up, rightUp, left,right, rightDown, down, rightDown)) {
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

        Collections.reverse(path);
        return path;
    }
}

package main.bfs;

import main.entity.Eatable;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapUtil;

import java.util.*;

public class AStarPathfinder implements Pathfinder {

    @Override
    public List<Coordinates> find(WorldMap worldMap, Coordinates start, Class<? extends Eatable> target) {

        long totalTargets = worldMap.getAllCoordinatesWithEntities().stream()
                .filter(coordinates -> worldMap.getEntity(coordinates).getClass() == target)
                .count();

        if (totalTargets == 0) {
            return Collections.emptyList();
        }

        Set<Coordinates> visited = new HashSet<>();
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        Map<Coordinates, Coordinates> backTrace = new HashMap<>();
        Map<Coordinates, Integer> gScore = new HashMap<>();
        Map<Coordinates, Integer> fScore = new HashMap<>();

        gScore.put(start, 0);
        fScore.put(start, heuristic(start, target, worldMap));

        openSet.add(new Node(start, fScore.get(start)));

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            Coordinates currentCoordinates = currentNode.coordinates;

            // Check if we've reached the target
            if (!worldMap.isCellFree(currentCoordinates) && worldMap.getEntity(currentCoordinates).getClass() == target) {
                return reconstructPath(backTrace, currentCoordinates, start);
            }

            visited.add(currentCoordinates);
            List<Coordinates> adjacentCells = WorldMapUtil.getValidCellsAroundTarget(currentCoordinates, worldMap);

            for (Coordinates adjacentCell : adjacentCells) {
                if ((!visited.contains(adjacentCell))
                        && (worldMap.isCellFree(adjacentCell)
                        || (worldMap.getEntity(adjacentCell).getClass() == target))) {

                    int tentativeGScore = gScore.getOrDefault(currentCoordinates, Integer.MAX_VALUE) + 1; // Assuming cost between adjacent cells is 1

                    if (!gScore.containsKey(adjacentCell) || tentativeGScore < gScore.get(adjacentCell)) {
                        backTrace.put(adjacentCell, currentCoordinates);
                        gScore.put(adjacentCell, tentativeGScore);
                        fScore.put(adjacentCell, tentativeGScore + heuristic(adjacentCell, target, worldMap));

                        if (!openSet.contains(new Node(adjacentCell, fScore.get(adjacentCell)))) {
                            openSet.add(new Node(adjacentCell, fScore.get(adjacentCell)));
                        }
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private int heuristic(Coordinates a, Class<? extends Eatable> target, WorldMap worldMap) {
        //TODO: what if we have no target on the map
        Coordinates targetCoordinates = findTargetCoordinates(a, target, worldMap);

        return chebyshevDistance(a, targetCoordinates);
    }

    // Chebyshev distance is more appropriate, because diagonal moves are allowed
    private int chebyshevDistance(Coordinates a, Coordinates b) {
        return Math.max(Math.abs(a.row() - b.row()), Math.abs(a.column() - b.column()));
    }


    private Coordinates findTargetCoordinates(Coordinates start, Class<? extends Eatable> target, WorldMap
            worldMap) {
        Coordinates closestTarget = null;
        int minDistance = Integer.MAX_VALUE;
        // Iterate through the world map to find all target main.entity's coordinates

        // my try to use stream
        //worldMap.getAllCoordinatesWithEntities().stream()
        //       .filter(coordinates -> (worldMap.getEntity(coordinates).getClass()) == target)

        for (Coordinates coordinates : worldMap.getAllCoordinatesWithEntities()) {
            if (worldMap.getEntity(coordinates).getClass() == target) {
                int distance = chebyshevDistance(start, coordinates);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestTarget = coordinates;
                }
            }
        }

        return closestTarget; // Return the closest target coordinates or null if none found
    }


    private List<Coordinates> reconstructPath(Map<Coordinates, Coordinates> backTrace, Coordinates
            end, Coordinates start) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates current = end;

        while (current != null && !current.equals(start)) {
            path.add(current);
            current = backTrace.get(current);
        }

        if (current != null) {
            path.add(start); // Add the start only if we reached it
        }

        Collections.reverse(path);
        return path;
    }


    private static class Node {
        Coordinates coordinates;
        int f; // Total cost (g + h)

        Node(Coordinates coordinates, int f) {
            this.coordinates = coordinates;
            this.f = f;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Node)) return false;
            Node other = (Node) obj;
            return this.coordinates.equals(other.coordinates);
        }

        @Override
        public int hashCode() {
            return coordinates.hashCode();
        }
    }
}
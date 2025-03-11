package main.pathfinder;

import main.entity.Eatable;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapUtil;

import java.util.*;

public class AStarPathfinder extends AbstractPathfinder {


    @Override
    public List<Coordinates> find(Coordinates start, Class<? extends Eatable> target, WorldMap worldMap) {

        if (targetCount(worldMap, target) == 0) {
            return Collections.emptyList();
        }


        // Priority queue for the open set
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        Set<Coordinates> closedSet = new HashSet<>();

        // Initialize the start node
        Node startNode = new Node(start, 0, heuristic(start, target, worldMap));
        openSet.add(startNode);

        Map<Coordinates, Node> allNodes = new HashMap<>();
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();

            if (isTargetEntity(currentNode.coordinates, target, worldMap)) {
                return reconstructPath(currentNode);
            }

            closedSet.add(currentNode.coordinates);


            List<Coordinates> neighbors = WorldMapUtil.getValidCellsAroundTarget(currentNode.coordinates, worldMap);
            for (Coordinates neighbor : neighbors) {
                if (!isValidMove(closedSet, neighbor, target, worldMap)) {
                    continue;
                }

                int tentativeGScore = currentNode.g + 1; // Assuming uniform cost for simplicity

                Node neighborNode = allNodes.getOrDefault(neighbor, new Node(neighbor, Integer.MAX_VALUE, Integer.MAX_VALUE));

                if (tentativeGScore < neighborNode.g) {
                    // This path to neighbor is better than any previous one
                    neighborNode.g = tentativeGScore;
                    neighborNode.f = tentativeGScore + heuristic(neighbor, target, worldMap);
                    neighborNode.parent = currentNode;

                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                        allNodes.put(neighbor, neighborNode);
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

        for (Coordinates coordinates : worldMap.getAllCellsWithEntities()) {
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

    private List<Coordinates> reconstructPath(Node currentNode) {
        List<Coordinates> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(currentNode.coordinates);
            currentNode = currentNode.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private static class Node {
        Coordinates coordinates;
        int g; // Cost from start to this node
        int f; // Total cost (gScore + heuristic)
        Node parent;

        Node(Coordinates coordinates, int g, int f) {
            this.coordinates = coordinates;
            this.g = g;
            this.f = f;
            this.parent = null;
        }
    }
}
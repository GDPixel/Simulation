package main.pathfinder;

import main.entity.Eatable;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapUtil;

import java.util.*;

public class AStarPathfinder extends AbstractPathfinder {

    private static final int START_G_COST = 0;
    private static final int NEIGHBOR_G_COST = 1;

    @Override
    public List<Coordinates> find(Coordinates start, Class<? extends Eatable> target, WorldMap worldMap) {

        if (countTargets(target, worldMap) == 0) {
            return Collections.emptyList();
        }

        PriorityQueue<Node> openNodes = new PriorityQueue<>(Comparator.comparingInt(node -> node.fScore));
        Set<Coordinates> visited = new HashSet<>();

        Node startNode = new Node(start, START_G_COST, heuristic(start, target, worldMap));
        openNodes.add(startNode);

        while (!openNodes.isEmpty()) {
            Node currentNode = openNodes.poll();

            if (isTargetFound(currentNode.coordinates, target, worldMap)) {
                return reconstructPath(currentNode);
            }

            visited.add(currentNode.coordinates);
            processNeighborNodes(target, currentNode, visited, openNodes, worldMap);
        }

        return Collections.emptyList();
    }

    private void processNeighborNodes(Class<? extends Eatable> target,
                                      Node currentNode,
                                      Set<Coordinates> visited,
                                      PriorityQueue<Node> openNodes,
                                      WorldMap worldMap) {

        List<Coordinates> neighborCells = WorldMapUtil.getValidCellsAroundTarget(currentNode.coordinates, worldMap);
        for (Coordinates neighborCell : neighborCells) {
            if (!isValidMove(visited, neighborCell, target, worldMap)) {
                continue;
            }

            int tentativeGScore = currentNode.gScore + NEIGHBOR_G_COST;

            Node neighborNode = new Node(neighborCell, Integer.MAX_VALUE, Integer.MAX_VALUE);

            if (tentativeGScore < neighborNode.gScore) {
                neighborNode.gScore = tentativeGScore;
                neighborNode.fScore = tentativeGScore + heuristic(neighborCell, target, worldMap);
                neighborNode.parent = currentNode;
                openNodes.add(neighborNode);
            }
        }
    }

    private int heuristic(Coordinates a, Class<? extends Eatable> target, WorldMap worldMap) {
        Coordinates targetCoordinates = findTargetCoordinates(a, target, worldMap);

        return chebyshevDistance(a, targetCoordinates);
    }

    // Chebyshev distance is more appropriate, because diagonal moves are allowed
    private int chebyshevDistance(Coordinates a, Coordinates b) {
        return Math.max(Math.abs(a.row() - b.row()), Math.abs(a.column() - b.column()));
    }


    private Coordinates findTargetCoordinates(Coordinates start, Class<? extends Eatable> target, WorldMap worldMap) {

        Coordinates closestTarget = null;
        int minDistance = Integer.MAX_VALUE;

        for (Coordinates coordinates : worldMap.getAllCellsWithEntities()) {
            if (worldMap.getEntity(coordinates).getClass() == target) {
                int distance = chebyshevDistance(start, coordinates);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestTarget = coordinates;
                }
            }
        }

        return closestTarget;
    }


    private List<Coordinates> reconstructPath(Node currentNode) {

        List<Coordinates> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(currentNode.coordinates);
            currentNode = currentNode.parent;
        }
        Collections.reverse(path);
        System.out.println(path);
        return path;
    }

    private static class Node {
        Coordinates coordinates;
        int gScore; // Cost from start to this node
        int fScore; // Total cost (gScore + heuristic)
        Node parent;

        Node(Coordinates coordinates, int gScore, int fScore) {
            this.coordinates = coordinates;
            this.gScore = gScore;
            this.fScore = fScore;
            this.parent = null;
        }
    }
}
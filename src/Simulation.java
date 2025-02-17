import action.Action;
import action.CustomPopulateWorld;
import action.PopulateWorldAction;
import entity.Grass;

import worldmap.Coordinates;
import worldmap.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    // TODO: add action.Action
    //  add MapRenderer
    //  add action.Action spawn Entities
    //
    private static final int MAX_TURN = 2;
    private final WorldMap worldMap;
    private final Renderer worldMapRenderer;
    private final List<action.Action> initActions;
    private final List<action.Action> turnActions;

    public Simulation() {
        worldMap = new WorldMap(10, 15);
        worldMapRenderer = new Renderer(worldMap);
        initActions = new ArrayList<>();
        initActions.add(new PopulateWorldAction(worldMap));
        //initActions.add(new CustomPopulateWorld(worldMap));
        turnActions = new ArrayList<>();
    }

    public void nextTurn() {
        for (Action action : turnActions) {
            action.execute();
        }
    }

    public void startSimulation() {
        for (action.Action action : initActions) {
            action.execute();
        }

        for (int turn = 0; turn < MAX_TURN; turn++) {
            System.out.println("Turn: " + turn);
            worldMapRenderer.render();
            BFS bfs = new BFS(worldMap, new Coordinates(0,0), new Grass());
            List<Coordinates> path = bfs.findPath();
            System.out.println("Grass is found: " + path);
            for (Coordinates coords : path) {
                System.out.printf("(%d %d) is free:%b%n",coords.getRow(),coords.getCol(), worldMap.isCellFree(coords));
            }
            nextTurn();
        }

    }

    public void stopSimulation() {

    }
}


import action.*;
import worldmap.WorldMap;

import java.util.List;

public class Simulation {
    private static final int MAX_TURN = 100;
    private final WorldMap worldMap;
    private final Renderer worldMapRenderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    public Simulation(WorldMap worldMap, List<Action> initActions, List<Action> turnActions) {
        this.worldMap = worldMap;
        this.initActions = initActions;
        this.turnActions = turnActions;
        worldMapRenderer = new Renderer(worldMap);
    }

    public void nextTurn() {
        for (Action action : turnActions) {
            action.execute();
        }
    }

    public void startSimulation() {
        for (Action action : initActions) {
            action.execute();
        }

        for (int turn = 0; turn < MAX_TURN; turn++) {
            System.out.println("Turn: " + turn);
            worldMapRenderer.render();
            nextTurn();
        }

    }

    public void stopSimulation() {

    }
}


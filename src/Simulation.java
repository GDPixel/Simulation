import action.Action;
import action.CustomPopulateWorldAction;
import action.MoveAllCreaturesAction;
import worldmap.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private static final int MAX_TURN = 25;
    private final WorldMap worldMap;
    private final Renderer worldMapRenderer;
    private final List<action.Action> initActions;
    private final List<action.Action> turnActions;

    public Simulation() {
        worldMap = new WorldMap(10, 15);
        worldMapRenderer = new Renderer(worldMap);
        initActions = new ArrayList<>();
        //initActions.add(new PopulateWorldAction(worldMap));
        initActions.add(new CustomPopulateWorldAction(worldMap));
        turnActions = new ArrayList<>();
        turnActions.add(new MoveAllCreaturesAction(worldMap));
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
            nextTurn();
        }

    }

    public void stopSimulation() {

    }
}


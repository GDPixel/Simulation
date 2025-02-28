import action.*;
import worldmap.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private static final int MAX_TURN = 100;
    private static final int APPLY_HUNGER_EVERY_TURNS = 3;
    private final WorldMap worldMap;
    private final Renderer worldMapRenderer;
    private final List<action.Action> initActions;
    private final List<action.Action> turnActions;

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
        worldMapRenderer = new Renderer(worldMap);
        initActions = new ArrayList<>();
        initActions.add(new PopulateWorldAction(worldMap, 20));
        //initActions.add(new CustomPopulateWorldAction(worldMap));
        turnActions = new ArrayList<>();
        turnActions.add(new HungerAction(worldMap, APPLY_HUNGER_EVERY_TURNS));
        turnActions.add(new GrowGrassAction(worldMap, 1, 1));
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


import action.Action;
import action.PopulateWorld;
import entity.Grass;
import entity.Rock;
import entity.Tree;
import entity.creature.Herbivore;
import entity.creature.Predator;
import worldmap.Coordinate;
import worldmap.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    // TODO: add action.Action
    //  add MapRenderer
    //  add action.Action spawn Entities
    //
    private static final int MAX_TURN = 3;
    private final WorldMap worldMap;
    private final Renderer worldMapRenderer;
    private final List<action.Action> initActions;
    private final List<action.Action> turnActions;

    public Simulation() {
        worldMap = new WorldMap(30, 30);
        worldMapRenderer = new Renderer(worldMap);
        initActions = new ArrayList<>();
        initActions.add(new PopulateWorld(worldMap));
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

//        //fill world for test render
//        worldMap.addEntity(new Coordinate(0,0), new Grass());
//        worldMap.addEntity(new Coordinate(2,1), new Rock());
//        worldMap.addEntity(new Coordinate(6,6), new Predator());
//        worldMap.addEntity(new Coordinate(3,4), new Predator());
//        worldMap.addEntity(new Coordinate(2,7), new Herbivore());
//        worldMap.addEntity(new Coordinate(3,7), new Herbivore());
//        worldMap.addEntity(new Coordinate(7,7), new Tree());
//        worldMap.addEntity(new Coordinate(8,1), new Tree());
//        worldMap.addEntity(new Coordinate(9,9), new Tree());

        for (int turn = 0; turn < MAX_TURN; turn++) {
            System.out.println("Turn: " + turn);
            worldMapRenderer.render();
            nextTurn();
        }

    }

    public void stopSimulation() {

    }
}



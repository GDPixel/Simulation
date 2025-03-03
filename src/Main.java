import action.*;
import worldmap.WorldMap;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(12, 20);

        List<Action> initActions = List.of(
                new PopulateWorldAction(30));

        List<action.Action> turnActions = List.of(
                new HungerAction(1, 3),
                new GrowGrassAction(4, 1),
                new MoveAllCreaturesAction());

        Simulation simulation = new Simulation(worldMap, initActions, turnActions);
        simulation.startSimulation();
    }
}
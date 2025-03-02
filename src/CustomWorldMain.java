import action.*;
import worldmap.WorldMap;

import java.util.List;

public class CustomWorldMain {
    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(10, 15);
        List<Action> initActions = List.of(
                new CustomPopulateWorldAction(worldMap));

        List<action.Action> turnActions = List.of(
                new HungerAction(worldMap, 1, 3),
                new GrowGrassAction(worldMap, 1, 3),
                new MoveAllCreaturesAction(worldMap));

        Simulation simulation = new Simulation(worldMap, initActions, turnActions);
        simulation.startSimulation();
    }
}

import action.*;
import worldmap.WorldMap;

import java.util.List;

public class CustomWorldMain {
    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(10, 15);

        List<Action> initActions = List.of(
                new CustomPopulateWorldAction());

        List<action.Action> turnActions = List.of(
                new HungerAction(1, 3),
                new GrowGrassAction(1, 1),
                new MoveAllCreaturesAction());

        Simulation simulation = new Simulation(worldMap, initActions, turnActions);
        simulation.startSimulation();
    }
}

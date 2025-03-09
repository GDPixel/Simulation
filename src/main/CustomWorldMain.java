package main;

import main.action.*;
import main.entity.Grass;
import main.worldmap.WorldMap;

import java.util.List;

public class CustomWorldMain {
    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(10, 15);

        List<Action> initActions = List.of(
                new EqualPopulateWorldAction(100)
        );

        List<main.action.Action> turnActions = List.of(
                new HungerAction(1, 3),
                new SpawnAction(Grass::new, 1, 7),
                new MoveAllCreaturesAction());

        Simulation simulation = new Simulation(worldMap, initActions, turnActions);
        simulation.startSimulation();
    }
}

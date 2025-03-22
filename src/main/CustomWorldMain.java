package main;

import main.action.*;
import main.entity.Grass;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapFactory;

import java.util.Collections;
import java.util.List;

public class CustomWorldMain {
    public static void main(String[] args) {
        WorldMapFactory worldMapFactory = new WorldMapFactory();
        WorldMap worldMap = worldMapFactory.createCustom10x15();

        List<Action> initActions = Collections.emptyList();

        List<main.action.Action> turnActions = List.of(
                new HungerDamageAction(1, 3),
                new SpawnAction(Grass::new, 1, 7),
                new MoveAllCreaturesAction());

        Simulation simulation = new Simulation(worldMap, initActions, turnActions);
        simulation.startSimulation();
    }
}

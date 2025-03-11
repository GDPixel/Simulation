package main;

import main.action.*;
import main.entity.Grass;
import main.entity.Rock;
import main.entity.Tree;
import main.entity.creature.Herbivore;
import main.entity.creature.Predator;
import main.worldmap.WorldMap;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(12, 20);

        List<Action> initActions = List.of(
                new SpawnAction(Rock::new, 10),
                new SpawnAction(Tree::new, 10),
                new SpawnAction(Grass::new, 10),
                new SpawnAction(Herbivore::new, 5),
                new SpawnAction(Predator::new, 4)
        );

        List<Action> turnActions = List.of(
                new HungerAction(1, 4),
                new SpawnAction(Predator::new, 1,10),
                new SpawnAction(Herbivore::new, 1,5),
                new SpawnAction(Grass::new, 5,2),
                new MoveAllCreaturesAction());

        Simulation simulation = new Simulation(worldMap, initActions, turnActions);
        simulation.startSimulation();
    }
}
package main;

import main.action.*;
import main.entity.Entity;
import main.entity.Grass;
import main.entity.Rock;
import main.entity.Tree;
import main.entity.creature.Herbivore;
import main.entity.creature.Predator;
import main.worldmap.WorldMap;

import java.util.List;
import java.util.function.Supplier;

public class CustomWorldMain {
    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(10, 15);

        List<Supplier<Entity>> entitySuppliers = List.of(
                Rock::new,
                Grass::new,
                Tree::new,
                Herbivore::new,
                Predator::new
        );

        List<Action> initActions = List.of(
                new EqualPopulateWorldAction(100, entitySuppliers)
        );

        List<main.action.Action> turnActions = List.of(
                new HungerAction(1, 3),
                new SpawnAction(Grass::new, 1, 7),
                new MoveAllCreaturesAction());

        Simulation simulation = new Simulation(worldMap, initActions, turnActions);
        simulation.startSimulation();
    }
}

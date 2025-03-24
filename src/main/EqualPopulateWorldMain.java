package main;

import main.action.*;
import main.entity.Entity;
import main.entity.Grass;
import main.entity.Rock;
import main.entity.Tree;
import main.entity.creature.Herbivore;
import main.entity.creature.Predator;
import main.renderer.ConsoleRenderer;
import main.renderer.Renderer;
import main.simulation.Simulation;
import main.worldmap.WorldMap;

import java.util.List;
import java.util.function.Supplier;

public class EqualPopulateWorldMain {
    public static final boolean DONT_SHOW_HP = false;

    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(12,20);
        Renderer consoleRenderer = new ConsoleRenderer(DONT_SHOW_HP);

        List<Supplier<Entity>> entitySuppliers = List.of(
                Rock::new,
                Grass::new,
                Tree::new,
                Herbivore::new,
                Predator::new
        );

        List<Action> initActions = List.of(
                new EqualPopulateWorldAction(40, entitySuppliers)
        );

        List<main.action.Action> turnActions = List.of(
                new HungerDamageAction(1, 2),
                new SpawnAction(Grass::new, 3, 2),
                new SpawnAction(Herbivore::new, 1, 5),
                new MoveAllCreaturesAction());

        Simulation simulation = new Simulation(worldMap, consoleRenderer, initActions, turnActions);
        simulation.startSimulation();
    }
}

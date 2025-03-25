package main;

import main.action.*;
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

public class Main {
    public static final int WORLD_MAP_ROWS = 12;
    public static final int WORLD_MAP_COLUMNS = 20;

    public static final int INITIAL_ROCK_AMOUNT = 16;
    public static final int INITIAL_TREE_AMOUNT = 15;
    public static final int INITIAL_GRASS_AMOUNT = 10;
    public static final int INITIAL_HERBIVORE_AMOUNT = 8;
    public static final int INITIAL_PREDATOR_AMOUNT = 6;

    public static final int HUNGER_DAMAGE = 1;
    public static final int HUNGER_DAMAGE_FREQUENCY = 4;

    public static final int PREDATOR_RESPAWN_AMOUNT = 1;
    public static final int PREDATOR_RESPAWN_FREQUENCY = 10;
    public static final int HERBIVORE_RESPAWN_AMOUNT = 1;
    public static final int HERBIVORE_RESPAWN_FREQUENCY = 5;
    public static final int GRASS_RESPAWN_AMOUNT = 5;
    public static final int GRASS_RESPAWN_FREQUENCY = 2;


    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(WORLD_MAP_ROWS, WORLD_MAP_COLUMNS);
        Renderer consoleRenderer = new ConsoleRenderer();

        List<Action> initActions = List.of(
                new SpawnAction(Rock::new, INITIAL_ROCK_AMOUNT),
                new SpawnAction(Tree::new, INITIAL_TREE_AMOUNT),
                new SpawnAction(Grass::new, INITIAL_GRASS_AMOUNT),
                new SpawnAction(Herbivore::new, INITIAL_HERBIVORE_AMOUNT),
                new SpawnAction(Predator::new, INITIAL_PREDATOR_AMOUNT)
        );

        List<Action> turnActions = List.of(
                new HungerDamageAction(HUNGER_DAMAGE, HUNGER_DAMAGE_FREQUENCY),
                new SpawnAction(Predator::new, PREDATOR_RESPAWN_AMOUNT, PREDATOR_RESPAWN_FREQUENCY),
                new SpawnAction(Herbivore::new, HERBIVORE_RESPAWN_AMOUNT, HERBIVORE_RESPAWN_FREQUENCY),
                new SpawnAction(Grass::new, GRASS_RESPAWN_AMOUNT, GRASS_RESPAWN_FREQUENCY),
                new MoveAllCreaturesAction());

        Simulation simulation = new Simulation(worldMap, consoleRenderer, initActions, turnActions);
        simulation.startSimulation();
    }
}
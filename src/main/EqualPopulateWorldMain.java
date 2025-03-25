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
    public static final int WORLD_MAP_ROWS = 15;
    public static final int WORLD_MAP_COLUMNS = 20;

    public static final int EQUAL_POPULATE_WORLD_PERCENT = 40;
    public static final List<Supplier<Entity>> ENTITIES_TO_SPAWN = List.of(
            Rock::new,
            Grass::new,
            Tree::new,
            Herbivore::new,
            Predator::new
    );

    public static final int HUNGER_DAMAGE = 1;
    public static final int HUNGER_DAMAGE_FREQUENCY = 2;

    public static final int HERBIVORE_RESPAWN_AMOUNT = 2;
    public static final int HERBIVORE_RESPAWN_FREQUENCY = 5;
    public static final int GRASS_RESPAWN_AMOUNT = 4;
    public static final int GRASS_RESPAWN_FREQUENCY = 1;

    public static final boolean DONT_SHOW_HP = false;

    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(WORLD_MAP_ROWS, WORLD_MAP_COLUMNS);
        Renderer consoleRenderer = new ConsoleRenderer(DONT_SHOW_HP);


        List<Action> initActions = List.of(
                new EqualPopulateWorldAction(EQUAL_POPULATE_WORLD_PERCENT, ENTITIES_TO_SPAWN)
        );

        List<main.action.Action> turnActions = List.of(
                new HungerDamageAction(HUNGER_DAMAGE, HUNGER_DAMAGE_FREQUENCY),
                new SpawnAction(Grass::new, GRASS_RESPAWN_AMOUNT, GRASS_RESPAWN_FREQUENCY),
                new SpawnAction(Herbivore::new, HERBIVORE_RESPAWN_AMOUNT, HERBIVORE_RESPAWN_FREQUENCY),
                new MoveAllCreaturesAction());

        Simulation simulation = new Simulation(worldMap, consoleRenderer, initActions, turnActions);
        simulation.startSimulation();
    }
}

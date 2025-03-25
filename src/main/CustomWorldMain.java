package main;

import main.action.*;
import main.entity.Grass;
import main.entity.creature.Herbivore;
import main.renderer.AnsiColors;
import main.renderer.ConsoleRenderer;
import main.renderer.Renderer;
import main.simulation.Simulation;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapFactory;

import java.util.Collections;
import java.util.List;

public class CustomWorldMain {
    public static final int HUNGER_DAMAGE = 1;
    public static final int HUNGER_DAMAGE_FREQUENCY = 3;

    public static final int HERBIVORE_RESPAWN_AMOUNT = 1;
    public static final int HERBIVORE_RESPAWN_FREQUENCY = 7;
    public static final int GRASS_RESPAWN_AMOUNT = 1;
    public static final int GRASS_RESPAWN_FREQUENCY = 5;

    public static void main(String[] args) {
        WorldMapFactory worldMapFactory = new WorldMapFactory();
        WorldMap worldMap = worldMapFactory.createCustom7x7();
        Renderer consoleRenderer = new ConsoleRenderer(AnsiColors.BACKGROUND_BLUE);

        List<Action> initActions = Collections.emptyList();

        List<main.action.Action> turnActions = List.of(
                new HungerDamageAction(HUNGER_DAMAGE, HUNGER_DAMAGE_FREQUENCY),
                new SpawnAction(Grass::new, GRASS_RESPAWN_AMOUNT, GRASS_RESPAWN_FREQUENCY),
                new SpawnAction(Herbivore::new, HERBIVORE_RESPAWN_AMOUNT, HERBIVORE_RESPAWN_FREQUENCY),
                new MoveAllCreaturesAction());

        Simulation simulation = new Simulation(worldMap, consoleRenderer, initActions, turnActions);
        simulation.startSimulation();
    }
}

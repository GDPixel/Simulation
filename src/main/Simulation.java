package main;

import main.action.*;
import main.menu.IntegerSelectDialog;
import main.menu.MenuFactory;
import main.renderer.ConsoleRenderer;
import main.renderer.Renderer;
import main.worldmap.WorldMap;
import main.menu.Menu;

import java.util.List;

public class Simulation {
    private static final int INITIAL_TURN = 1;
    private static final int DELAY = 1500;
    private static final int STOP_INFINITE_SIMULATION = 1;

    private final WorldMap worldMap;
    private final Renderer renderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private int currentTurn = INITIAL_TURN;
    private boolean isPaused;
    private final Menu simulationMenu;
    private final Object lock = new Object();
    private boolean isRunning;

    public Simulation(WorldMap worldMap, List<Action> initActions, List<Action> turnActions) {
        this.worldMap = worldMap;
        this.initActions = initActions;
        this.turnActions = turnActions;
        renderer = new ConsoleRenderer(worldMap);
        simulationMenu = MenuFactory.createSimulationMenu(this);
        isRunning = true;
        isPaused = true;
        initSimulation();
    }

    public void nextTurn() {
        for (Action action : turnActions) {
            action.execute(worldMap);
        }

        System.out.println("Turn: " + currentTurn);
        renderer.render();
        currentTurn++;
    }

    public void startSimulation() {
        System.out.println("WorldMap initial position");
        renderer.render();
        Thread thread = createSimulationThread();
        thread.start();
        while (isRunning) {
            displayAndProcessMenu();
        }
        thread.interrupt();
    }

    private Thread createSimulationThread() {
        Thread thread = new Thread(() -> {
            while (isRunning) {
                synchronized (lock) {
                    while (isPaused) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            if (!isRunning) {
                                return; // Exit the thread
                            }
                        }
                    }
                }
                nextTurn();
                System.out.printf("Infinite simulation, enter %d to stop%n", STOP_INFINITE_SIMULATION);
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return thread;
    }

    public void pauseSimulation() {
        isPaused = true;
    }

    public void resumeSimulation() {
        isPaused = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    public void exitSimulation() {
        isRunning = false;
    }

    private void initSimulation() {
        for (Action action : initActions) {
            action.execute(worldMap);
        }
    }

    private void displayAndProcessMenu() {
        if (isPaused) {
            simulationMenu.show();
            simulationMenu.select();
        } else {
            IntegerSelectDialog dialog = new IntegerSelectDialog("",
                    "Wrong input, enter %d to stop simulation".formatted(STOP_INFINITE_SIMULATION),
                    List.of(STOP_INFINITE_SIMULATION));
            if (dialog.input() == STOP_INFINITE_SIMULATION) {
                pauseSimulation();
            }
        }
    }
}
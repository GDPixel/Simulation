package main;

import main.action.*;
import main.menu.IntegerSelectDialog;
import main.menu.SimulationMenu;
import main.worldmap.WorldMap;

import java.util.List;

public class Simulation {
    private static final int MAX_TURN = 100;
    private static final int INITIAL_TURN = 1;
    private static final int DELAY = 1500;
    private static final int STOP_INFINITE_SIMULATION = 1;

    private final WorldMap worldMap;
    private final Renderer worldMapRenderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private int currentTurn = INITIAL_TURN;
    private boolean isPaused;
    private final SimulationMenu simulationMenu;
    private final Object lock = new Object();
    private boolean isRunning;

    public Simulation(WorldMap worldMap, List<Action> initActions, List<Action> turnActions) {
        this.worldMap = worldMap;
        this.initActions = initActions;
        this.turnActions = turnActions;
        worldMapRenderer = new Renderer(worldMap);
        simulationMenu = new SimulationMenu("Simulation menu", "Choose command:", "wrong input", this);
        isRunning = true;
        isPaused = false;
        initSimulation();
    }

    public void nextTurn() {
        for (Action action : turnActions) {
            action.execute(worldMap);
        }

        System.out.println("Turn: " + currentTurn);
        worldMapRenderer.render();
        currentTurn++;
    }

    public void startSimulation() {
        System.out.println("WorldMap initial position");
        worldMapRenderer.render();
        Thread thread = createSimulationThread();
        thread.start();
        while (isRunning) {
            displayAndProcessMenu();
        }
    }

    private Thread createSimulationThread() {
        Thread thread = new Thread(() -> {
            while (isRunning) {
                synchronized (lock) {
                    while (!isPaused) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
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
        isPaused = false;
    }

    public void resumeSimulation() {
        isPaused = true;
        synchronized (lock) {
            lock.notify();
        }
    }

    public void exitSimulation() {
        // TODO: fix exiting
        isRunning = false;
    }

    private void initSimulation() {
        for (Action action : initActions) {
            action.execute(worldMap);
        }
    }

    private void displayAndProcessMenu() {
        if (!isPaused) {
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
package main.menu;

import main.Simulation;

public class SimulationMenu extends Menu {
    private final Simulation simulation;

    public SimulationMenu(String title, String description, String error, Simulation simulation) {
        super(title, description, error);
        this.simulation = simulation;
        initialize();
    }

    public void initialize() {
        addItem("Run One Turn", simulation::nextTurn);
        addItem("Start Simulation", simulation::resumeSimulation);
        addItem("Exit", simulation::exitSimulation);
    }
}
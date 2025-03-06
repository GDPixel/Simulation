package main.menu;

import main.Simulation;

public class TitleMenu extends Menu {
    private final Simulation simulation;

    public TitleMenu(String title, String description, String error, Simulation simulation) {
        super(title, description, error);
        this.simulation = simulation;
        initialize();
    }

    public void initialize() {
        addItem("Start", simulation::startSimulation);
        addItem("Exit", () -> {
        });
    }
}

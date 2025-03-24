package main.menu;

import main.simulation.Simulation;

public class MenuFactory {
    public static Menu createSimulationMenu(Simulation simulation) {
        Menu menu = new Menu("Simulation menu", "Choose command:", "wrong input");

        menu.addItem("Run One Turn", simulation::nextTurn);
        menu.addItem("Start Infinite Simulation", simulation::resumeSimulation);
        menu.addItem("Exit", simulation::exitSimulation);
        return menu;
    }
}

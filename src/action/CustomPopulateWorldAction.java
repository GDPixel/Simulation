package action;

import entity.Grass;
import entity.Rock;
import entity.Tree;
import entity.creature.Herbivore;
import entity.creature.Predator;
import worldmap.Coordinates;
import worldmap.WorldMap;

public class CustomPopulateWorldAction extends PopulateWorldAction {
    public CustomPopulateWorldAction(WorldMap worldMap) {
        super(worldMap);
    }

    @Override
    public void execute() {
        custom10x15();
    }

    private void custom10x15() {
        worldMap.addEntity(new Coordinates(1, 4), new Tree());
        worldMap.addEntity(new Coordinates(1, 6), new Grass());
        worldMap.addEntity(new Coordinates(2, 1), new Rock());
        worldMap.addEntity(new Coordinates(2, 5), new Herbivore());
        worldMap.addEntity(new Coordinates(3, 7), new Grass());
        worldMap.addEntity(new Coordinates(3, 6), new Tree());
        worldMap.addEntity(new Coordinates(5, 8), new Predator());
        worldMap.addEntity(new Coordinates(6, 2), new Grass());
        worldMap.addEntity(new Coordinates(7, 5), new Herbivore());
        worldMap.addEntity(new Coordinates(7, 10), new Tree());
        worldMap.addEntity(new Coordinates(9, 14), new Predator());
    }

    private void custom7x7() {
        worldMap.addEntity(new Coordinates(3, 0), new Grass());
        worldMap.addEntity(new Coordinates(1, 6), new Grass());
        worldMap.addEntity(new Coordinates(6, 2), new Grass());
        worldMap.addEntity(new Coordinates(2, 1), new Rock());
        worldMap.addEntity(new Coordinates(6, 6), new Predator());
        worldMap.addEntity(new Coordinates(3, 4), new Predator());
        worldMap.addEntity(new Coordinates(2, 4), new Herbivore());
        worldMap.addEntity(new Coordinates(3, 5), new Herbivore());
        worldMap.addEntity(new Coordinates(1, 4), new Tree());
        worldMap.addEntity(new Coordinates(6, 1), new Tree());
        worldMap.addEntity(new Coordinates(3, 6), new Tree());
    }

    private void custom5x5() {
        worldMap.addEntity(new Coordinates(0, 1), new Predator());
        worldMap.addEntity(new Coordinates(1, 1), new Tree());
        //worldMap.addEntity(new Coordinates(1,3), new Grass());
        worldMap.addEntity(new Coordinates(2, 1), new Rock());
        worldMap.addEntity(new Coordinates(2, 2), new Herbivore());
        worldMap.addEntity(new Coordinates(2, 3), new Herbivore());
        worldMap.addEntity(new Coordinates(2, 4), new Tree());
        worldMap.addEntity(new Coordinates(3, 1), new Tree());
        worldMap.addEntity(new Coordinates(3, 2), new Herbivore());
        //worldMap.addEntity(new Coordinates(4,0), new Grass());
        worldMap.addEntity(new Coordinates(4, 4), new Grass());
    }
}

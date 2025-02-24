package action;

import entity.Entity;
import entity.Grass;
import entity.creature.Creature;
import entity.creature.Herbivore;
import entity.creature.Predator;
import worldmap.Coordinates;
import worldmap.WorldMap;
import BFS.BFS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveAllCreaturesAction extends Action {
    private final WorldMap worldMap;

    // TODO: may inject moving algorithm BFS or A*
    public MoveAllCreaturesAction(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public void execute() {
        Set<Coordinates> allCreatures = getAllCoordinatesWithCreatures(worldMap);
        for (Coordinates creaturePosition : allCreatures) {
            BFS bfs = null;
            Creature creature = (Creature) worldMap.getEntity(creaturePosition);
            if (creature instanceof Predator) {
                bfs = new BFS(worldMap, creaturePosition, new Herbivore());
            }

            if (creature instanceof Herbivore) {
                bfs = new BFS(worldMap, creaturePosition, new Grass());
            }
            // TODO: check NULLpointer
            List<Coordinates> path = bfs.findPath();
            creature.makeMove(path, worldMap);
        }
    }

    private Set<Coordinates> getAllCoordinatesWithCreatures(WorldMap worldMap) {
        Set<Coordinates> allCoordinates = new HashSet<>();
        for (int row = 0; row < worldMap.getMaxRow(); row++) {
            for (int col = 0; col < worldMap.getMaxColumn(); col++) {
                Coordinates coordinates = new Coordinates(row, col);
                if (!worldMap.isCellFree(coordinates)) {
                    Entity entity = worldMap.getEntity(coordinates);
                    if (entity instanceof Creature) {
                        allCoordinates.add(coordinates);
                    }
                }
            }
        }

        return allCoordinates;
    }
}

package action;

import entity.Grass;
import entity.creature.Creature;
import entity.creature.Herbivore;
import entity.creature.Predator;
import worldmap.Coordinates;
import worldmap.WorldMap;
import bfs.BFS;
import worldmap.WorldMapUtil;

import java.util.List;

public class MoveAllCreaturesAction extends Action {

    // TODO: may inject moving algorithm BFS or A*
    public MoveAllCreaturesAction() {
    }

    @Override
    public void execute(WorldMap worldMap) {
        List<Coordinates> allCreatures = WorldMapUtil.getAllCoordinatesWithCreatures(worldMap);
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
            // TODO: Null
            if (path == null) {
                System.out.println("Path: " + path);
                throw new RuntimeException("BFS path is NULL");
            }
            creature.makeMove(path, worldMap);
        }
    }
}

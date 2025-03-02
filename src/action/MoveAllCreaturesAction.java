package action;

import entity.creature.Creature;
import worldmap.Coordinates;
import worldmap.WorldMap;
import worldmap.WorldMapUtil;

import java.util.List;

public class MoveAllCreaturesAction extends Action {

    // TODO: may inject moving algorithm BFS or A*
    public MoveAllCreaturesAction() {
    }

    @Override
    public void execute(WorldMap worldMap) {
        //TODO: possible bug if remove creature before it moved
        List<Coordinates> allCreatures = WorldMapUtil.getAllCoordinatesWithCreatures(worldMap);
        for (Coordinates creaturePosition : allCreatures) {
            Creature creature = (Creature) worldMap.getEntity(creaturePosition);
            creature.makeMove(creaturePosition, worldMap);
        }
    }
}

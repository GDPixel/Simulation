package main.action;

import main.entity.creature.Creature;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapUtil;

import java.util.List;

public class MoveAllCreaturesAction extends Action {

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

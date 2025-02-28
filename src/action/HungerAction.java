package action;

import entity.creature.Creature;
import worldmap.Coordinates;
import worldmap.WorldMap;
import worldmap.WorldMapUtil;

import java.util.List;

public class HungerAction extends Action {
    private static final int HUNGER_DAMAGE = 1;
    private final WorldMap worldMap;
    private final int frequency;
    private int currentTurn = 1;

    public HungerAction(WorldMap worldMap, int frequency) {
        if (frequency <= 0) {
            throw new IllegalArgumentException("frequency must be greater than 0");
        }
        this.worldMap = worldMap;
        this.frequency = frequency;
    }


    public void execute() {
        if (currentTurn % frequency == 0) {
            List<Coordinates> allCreaturesCoordinates = WorldMapUtil.getAllCoordinatesWithCreatures(worldMap);
            for (Coordinates creatureCoordinates : allCreaturesCoordinates) {
                Creature creature = (Creature) worldMap.getEntity(creatureCoordinates);
                creature.setHp(creature.getHp() - HUNGER_DAMAGE);
                if (!creature.isAlive()) {
                    worldMap.removeEntity(creatureCoordinates);
                }
            }
        }
        currentTurn++;
    }
}

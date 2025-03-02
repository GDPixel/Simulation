package action;

import entity.creature.Creature;
import worldmap.Coordinates;
import worldmap.WorldMap;
import worldmap.WorldMapUtil;

import java.util.List;

public class HungerAction extends Action {
    private final WorldMap worldMap;
    private final int frequency;
    private final int hungerDamage;
    private int currentTurn = 1;

    public HungerAction(WorldMap worldMap, int hungerDamage, int frequency) {
        if (frequency <= 0) {
            throw new IllegalArgumentException("frequency must be greater than 0");
        }

        if (hungerDamage <= 0) {
            throw new IllegalArgumentException("hungerDamage must be greater than 0");
        }

        this.worldMap = worldMap;
        this.frequency = frequency;
        this.hungerDamage = hungerDamage;
    }


    public void execute() {
        if (currentTurn % frequency == 0) {
            List<Coordinates> allCreaturesCoordinates = WorldMapUtil.getAllCoordinatesWithCreatures(worldMap);
            for (Coordinates creatureCoordinates : allCreaturesCoordinates) {
                Creature creature = (Creature) worldMap.getEntity(creatureCoordinates);
                creature.setHp(creature.getHp() - hungerDamage);
                if (!creature.isAlive()) {
                    worldMap.removeEntity(creatureCoordinates);
                }
            }
        }
        currentTurn++;
    }
}

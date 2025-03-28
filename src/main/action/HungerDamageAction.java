package main.action;

import main.entity.creature.Creature;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapUtil;

import java.util.List;

public class HungerDamageAction extends Action {
    private final int frequency;
    private final int hungerDamage;
    private int currentTurn = 1;

    public HungerDamageAction(int hungerDamage, int frequency) {
        if (frequency <= 0) {
            throw new IllegalArgumentException("frequency must be greater than 0");
        }

        if (hungerDamage <= 0) {
            throw new IllegalArgumentException("hungerDamage must be greater than 0");
        }

        this.frequency = frequency;
        this.hungerDamage = hungerDamage;
    }

    public void execute(WorldMap worldMap) {
        if (currentTurn % frequency == 0) {
            List<Coordinates> allCreaturesCoordinates = WorldMapUtil.getAllCoordinatesWithCreatures(worldMap);
            for (Coordinates creatureCoordinates : allCreaturesCoordinates) {
                Creature creature = (Creature) worldMap.getEntity(creatureCoordinates);
                creature.setHp(creature.getHp() - hungerDamage);
                if (creature.isDead()) {
                    worldMap.removeEntity(creatureCoordinates);
                }
            }
        }
        currentTurn++;
    }
}

package action;

import entity.Grass;
import worldmap.Coordinates;
import worldmap.WorldMap;
import worldmap.WorldMapUtil;


public class GrowGrassAction extends Action {

    private final int growRate;
    private final int frequency;
    private int currentTurn = 1;

    public GrowGrassAction(int growRate, int frequency) {
        if (growRate <= 0) {
            throw new IllegalArgumentException("growRate must be greater than 0");
        }

        if (frequency <= 0) {
            throw new IllegalArgumentException("frequency must be greater than 0");
        }

        this.growRate = growRate;
        this.frequency = frequency;
    }

    @Override
    public void execute(WorldMap worldMap) {
        if (currentTurn % frequency == 0) {
            for (int i = 0; i < growRate; i++) {
                Coordinates freeCell = WorldMapUtil.getRandomFreeCell(worldMap);
                worldMap.addEntity(freeCell, new Grass());
            }
        }
        currentTurn++;
    }
}

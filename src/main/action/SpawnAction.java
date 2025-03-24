package main.action;

import main.entity.Entity;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapUtil;

import java.util.function.Supplier;

public class SpawnAction extends Action {
    private static final int INITIAL_TURN = 1;
    private static final int DEFAULT_FREQUENCY = 0;

    private final Supplier<Entity> supplier;
    private final int amount;
    private final int frequency;
    private int turn = INITIAL_TURN;

    public SpawnAction(Supplier<Entity> supplier, int amount) {
        this(supplier, amount, DEFAULT_FREQUENCY);
    }

    public SpawnAction(Supplier<Entity> supplier, int amount, int frequency) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (frequency < 0) {
            throw new IllegalArgumentException("Frequency cannot be negative.");
        }

        this.supplier = supplier;
        this.amount = amount;
        this.frequency = frequency;
    }

    @Override
    public void execute(WorldMap worldMap) {
        if (shouldExecute()) {
            create(worldMap);
        }
        turn++;
    }

    private boolean shouldExecute() {
        return frequency == DEFAULT_FREQUENCY || turn % frequency == 0;
    }

    private void create(WorldMap worldMap) {
        for (int i = 0; i < amount; i++) {
            Entity entity = supplier.get();
            Coordinates coordinates = WorldMapUtil.getRandomFreeCell(worldMap);
            worldMap.addEntity(coordinates, entity);
        }
    }
}

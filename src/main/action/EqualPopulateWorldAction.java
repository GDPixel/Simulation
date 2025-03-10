package main.action;

import main.entity.*;
import main.worldmap.WorldMap;

import java.util.List;
import java.util.function.Supplier;

public class EqualPopulateWorldAction extends Action {
    private final List<Supplier<Entity>> entitySuppliers;
    private final int fillPercentage;

    public EqualPopulateWorldAction(int fillPercentage, List<Supplier<Entity>> entitySuppliers) {
        validateFillPercentage(fillPercentage);
        this.fillPercentage = fillPercentage;
        this.entitySuppliers = entitySuppliers;
    }

    @Override
    public void execute(WorldMap worldMap) {
        int totalCoordinates = worldMap.getMaxRow() * worldMap.getMaxColumn();
        int totalEntityTypes = entitySuppliers.size();
        int eachEntityToAdd = (totalCoordinates * fillPercentage / 100) / totalEntityTypes;

        for (Supplier<Entity> supplier : entitySuppliers) {
            SpawnAction spawnAction = new SpawnAction(supplier, eachEntityToAdd);
            spawnAction.execute(worldMap);
        }
    }

    private void validateFillPercentage(int fillPercentage) {
        if (fillPercentage < 0 || fillPercentage > 100) {
            throw new IllegalArgumentException("fillPercent must be between 0 and 100");
        }
    }
}



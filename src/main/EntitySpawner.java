package main;

import main.entity.Entity;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;
import main.worldmap.WorldMapUtil;

import java.util.function.Supplier;

public class EntitySpawner {
    WorldMap worldMap;
    Supplier<Entity> supplier;
    int amount;

    public EntitySpawner(Supplier<Entity> supplier, int amount, WorldMap worldMap) {
        this.supplier = supplier;
        this.amount = amount;
        this.worldMap = worldMap;
    }

    public void create() {
        for (int i = 0; i < amount; i++) {
            Entity entity = supplier.get();
            Coordinates coordinates = WorldMapUtil.getRandomFreeCell(worldMap);
            worldMap.addEntity(coordinates, entity);
        }
    }
}

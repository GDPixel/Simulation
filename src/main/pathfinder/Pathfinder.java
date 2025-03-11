package main.pathfinder;

import main.entity.Eatable;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;

import java.util.List;

public interface Pathfinder {
    List<Coordinates> find(Coordinates start, Class<? extends Eatable> target, WorldMap worldMap);
}


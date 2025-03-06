package main.bfs;

import main.entity.Eatable;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;

import java.util.List;

public interface Pathfinder {
    List<Coordinates> find(WorldMap worldMap, Coordinates start, Class<? extends Eatable> target);
}
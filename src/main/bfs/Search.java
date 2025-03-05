package main.bfs;

import main.entity.Eatable;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;

import java.util.List;

public interface Search {
    List<Coordinates> findPath(WorldMap worldMap, Coordinates start, Class<? extends Eatable> target);
}

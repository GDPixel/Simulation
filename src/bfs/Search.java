package bfs;

import entity.Eatable;
import worldmap.Coordinates;
import worldmap.WorldMap;

import java.util.List;

public interface Search {
    List<Coordinates> findPath(WorldMap worldMap, Coordinates start, Class<? extends Eatable> target);
}

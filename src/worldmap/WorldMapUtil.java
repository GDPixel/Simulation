package worldmap;

import java.util.ArrayList;
import java.util.List;

public class WorldMapUtil {
    private WorldMapUtil() {
    }

    public static List<Coordinates> getCellsAroundTarget(Coordinates target, WorldMap worldMap) {
        ArrayList<Coordinates> cells = new ArrayList<>();
        Coordinates leftUp = new Coordinates(target.getRow() - 1, target.getCol() - 1);
        Coordinates up = new Coordinates(target.getRow() - 1, target.getCol());
        Coordinates rightUp = new Coordinates(target.getRow() - 1, target.getCol() + 1);

        Coordinates left = new Coordinates(target.getRow(), target.getCol() - 1);
        Coordinates right = new Coordinates(target.getRow(), target.getCol() + 1);

        Coordinates leftDown = new Coordinates(target.getRow() + 1, target.getCol() - 1);
        Coordinates down = new Coordinates(target.getRow() + 1, target.getCol());
        Coordinates rightDown = new Coordinates(target.getRow() + 1, target.getCol() + 1);

        for (var coordinates : List.of(leftUp, up, rightUp, left, right, leftDown, down, rightDown)) {
            // TODO: I got only valid cells, rename method or use two methods
            if (isCellOnMap(coordinates, worldMap)) {
                cells.add(coordinates);
            }
        }

        return cells;
    }

    private static boolean isCellOnMap(Coordinates coordinates, WorldMap worldMap) {
        return coordinates.getRow() >= 0 && coordinates.getRow() < worldMap.getMaxRow()
                && coordinates.getCol() >= 0 && coordinates.getCol() < worldMap.getMaxCol();
    }
}

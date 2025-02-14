import entity.Entity;
import entity.Grass;
import entity.Rock;
import entity.Tree;
import entity.creature.Herbivore;
import entity.creature.Predator;
import worldmap.Coordinate;
import worldmap.WorldMap;

import java.util.Map;

public class Renderer {
    private static final String FREE_CELL = "⬛";
    private static final String ROCK = "\uD83D\uDDFB" ;
    private static final String TREE = "\uD83C\uDF33";
    private static final String GRASS = "\uD83C\uDF40";
    private static final String HERBIVORE = "\uD83D\uDC04";
    private static final String PREDATOR = "\uD83D\uDC05";
    private final WorldMap worldMap;
    private int maxCol;
    private int maxRow;

    Renderer(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.maxRow = worldMap.getMaxRow();
        this.maxCol = worldMap.getMaxCol();
    }

    public void render() {
        Map<Coordinate, Entity> entities = worldMap.getEntities();
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                // TODO: add equal hashcode to Coordinate
                Coordinate coordinate = new Coordinate(row, col);
                if (entities.containsKey(coordinate)) {
                    String sprite = toSprite(entities.get(coordinate));
                    System.out.printf("%s", sprite);
                } else {
                    System.out.printf("%s", FREE_CELL);
                }
            }
            System.out.println();
        }
    }

    private String toSprite(Entity entity) {
        String result = "";
        if (entity instanceof Rock) {
            result = ROCK;
        } else if (entity instanceof Grass) {
            result = GRASS;
        } else if (entity instanceof Tree) {
            result = TREE;
        } else if (entity instanceof Herbivore) {
            result = HERBIVORE;
        } else if (entity instanceof Predator) {
            result = PREDATOR;
        }

        return result;
    }

}

import entity.Entity;
import entity.Grass;
import entity.Rock;
import entity.Tree;
import entity.creature.Creature;
import entity.creature.Herbivore;
import entity.creature.Predator;
import worldmap.Coordinates;
import worldmap.WorldMap;

public class Renderer {
    public static final String ANSI_GREEN_SQUARE_BACKGROUND = "\u001B[0;42m";
    public static final String ANSI_RED_SQUARE_BACKGROUND = "\u001B[0;101m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[0;103m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static final String FREE_CELL = "⬛";
    private static final String ROCK = "\uD83D\uDDFB";
    private static final String TREE = "\uD83C\uDF33";
    private static final String GRASS = "\uD83C\uDF40";
    private static final String HERBIVORE = "\uD83D\uDC04";
    private static final String PREDATOR = "\uD83D\uDC05";

    private static final double LOW_HP_RATE = 0.3;
    private static final double MIDDLE_HP_RATE = 0.6;
    private final WorldMap worldMap;
    private final int maxCol;
    private final int maxRow;

    Renderer(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.maxRow = worldMap.getMaxRow();
        this.maxCol = worldMap.getMaxColumn();
    }

    public void render() {
        //Map<Coordinates, Entity> entities = worldMap.getEntities();
        // TODO: stringbuilder
        for (int row = 0; row < maxRow; row++) {
            System.out.print(ANSI_GREEN_SQUARE_BACKGROUND);
            for (int col = 0; col < maxCol; col++) {
                Coordinates coordinates = new Coordinates(row, col);
                if (worldMap.isCellFree(coordinates)) {
                    System.out.print(FREE_CELL);
                } else {
                    Entity entity = worldMap.getEntity(coordinates);
                    String sprite = toSprite(entity);
                    System.out.print(sprite);
                }
            }
            System.out.println(ANSI_RESET);
        }
    }


    private String toSprite(Entity entity) {
        return switch (entity) {
            case Rock rock -> ROCK;
            case Grass grass -> GRASS;
            case Tree tree -> TREE;
            case Herbivore herbivore -> colorCreatureHP(herbivore);
            case Predator predator -> colorCreatureHP(predator);
            default -> throw new IllegalArgumentException("Unknown entity type " + entity);
        };
    }

    // TODO: refactor
    private String colorCreatureHP(Creature creature) {
        return switch (creature) {
            case Herbivore herbivore -> colorHP(herbivore.getHp(), Herbivore.MAX_HEALTH, HERBIVORE);
            case Predator predator -> colorHP(predator.getHp(), Predator.MAX_HEALTH, PREDATOR);
            default -> throw new IllegalArgumentException("Unknown entity type " + creature);
        };
    }


    private String colorHP(int currentHp, int maxHp, String creatureSprite) {
        double hpLeftRate = (double) currentHp / maxHp;

        if (hpLeftRate < LOW_HP_RATE) {
            return ANSI_RED_SQUARE_BACKGROUND + creatureSprite + ANSI_GREEN_SQUARE_BACKGROUND;
        } else if (hpLeftRate < MIDDLE_HP_RATE) {
            return ANSI_YELLOW_BACKGROUND + creatureSprite + ANSI_GREEN_SQUARE_BACKGROUND;
        } else {
            return creatureSprite;
        }
    }
}

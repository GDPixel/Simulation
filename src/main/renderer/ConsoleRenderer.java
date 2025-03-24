package main.renderer;

import main.entity.Entity;
import main.entity.Grass;
import main.entity.Rock;
import main.entity.Tree;
import main.entity.creature.Creature;
import main.entity.creature.Herbivore;
import main.entity.creature.Predator;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;

public class ConsoleRenderer implements Renderer {
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[0;42m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[0;101m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[0;103m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String DEFAULT_BACKGROUND_COLOR = ANSI_GREEN_BACKGROUND;

    private static final String FREE_CELL_SPRITE = "⬛";
    private static final String ROCK_SPRITE = "\uD83D\uDDFB";
    private static final String TREE_SPRITE = "\uD83C\uDF33";
    private static final String GRASS_SPRITE = "\uD83C\uDF40";
    private static final String HERBIVORE_SPRITE = "\uD83D\uDC04";
    private static final String PREDATOR_SPRITE = "\uD83D\uDC05";

    private static final boolean DEFAULT_SHOW_HEALTH_INDICATOR = true;

    private final boolean showHealthIndicator;

    private enum HealthIndicator {
        LOW_HP(0.3, ANSI_RED_BACKGROUND),
        MIDDLE_HP(0.6, ANSI_YELLOW_BACKGROUND),
        FULL_HP(1.0, ANSI_GREEN_BACKGROUND);

        private final double rate;
        private final String color;

        HealthIndicator(double rate, String color) {
            this.rate = rate;
            this.color = color;
        }

        public double getRate() {
            return rate;
        }

        public String getColor() {
            return color;
        }
    }

    public ConsoleRenderer() {
        this(DEFAULT_SHOW_HEALTH_INDICATOR);
    }

    public ConsoleRenderer(boolean showHealthIndicator) {
        this.showHealthIndicator = showHealthIndicator;
    }



    @Override
    public void render(WorldMap worldMap) {
        for (int row = 0; row < worldMap.getMaxRow(); row++) {
            StringBuilder line = new StringBuilder();
            line.append(DEFAULT_BACKGROUND_COLOR);
            for (int column = 0; column < worldMap.getMaxColumn(); column++) {
                Coordinates coordinates = new Coordinates(row, column);
                if (worldMap.isCellFree(coordinates)) {
                    line.append(FREE_CELL_SPRITE);
                } else {
                    Entity entity = worldMap.getEntity(coordinates);
                    String sprite = toSprite(entity);
                    line.append(sprite);
                }
            }
            line.append(ANSI_RESET);
            System.out.println(line);
        }
    }

    private String toSprite(Entity entity) {
        return switch (entity) {
            case Rock rock -> ROCK_SPRITE;
            case Grass grass -> GRASS_SPRITE;
            case Tree tree -> TREE_SPRITE;
            case Creature creature -> showHealthIndicator ? colorCreatureHP(creature) : getSpriteForCreature(creature);
            default -> throw new IllegalArgumentException("Unknown Entity type " + entity);
        };
    }

    private String colorCreatureHP(Creature creature) {
        double hpLeftRate = getHpLeftRate(creature);

        String creatureSprite = getSpriteForCreature(creature);
        for (HealthIndicator indicator : HealthIndicator.values()) {
            if (hpLeftRate < indicator.getRate()) {
                return indicator.getColor() + creatureSprite + DEFAULT_BACKGROUND_COLOR;
            }
        }
        return creatureSprite;
    }

    private double getHpLeftRate(Creature creature) {
        int currentHp = creature.getHp();
        int maxHp = creature.getMaxHp();
        return (double) currentHp / maxHp;
    }

    private String getSpriteForCreature(Creature creature) {
        return switch (creature) {
            case Herbivore herbivore -> HERBIVORE_SPRITE;
            case Predator predator -> PREDATOR_SPRITE;
            default -> throw new IllegalArgumentException("Unknown Creature type " + creature);
        };
    }
}

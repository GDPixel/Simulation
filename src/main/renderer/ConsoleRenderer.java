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
    private static final String DEFAULT_BACKGROUND_COLOR = AnsiColors.BACKGROUND_GREEN;

    private static final String FREE_CELL_SPRITE = "⬛";
    private static final String ROCK_SPRITE = "\uD83D\uDDFB";
    private static final String TREE_SPRITE = "\uD83C\uDF33";
    private static final String GRASS_SPRITE = "\uD83C\uDF40";
    private static final String HERBIVORE_SPRITE = "\uD83D\uDC04";
    private static final String PREDATOR_SPRITE = "\uD83D\uDC05";

    private static final double DOUBLE_PRECISION_EPSILON = 0.00001;
    private static final boolean DEFAULT_SHOW_HEALTH_INDICATOR = true;

    private final boolean showHealthIndicator;
    private final String backgroundColor;

    private enum HealthIndicator {
        LOW_HP(0.3, AnsiColors.BACKGROUND_RED),
        MIDDLE_HP(0.6, AnsiColors.BACKGROUND_YELLOW),
        FULL_HP(1.0, AnsiColors.BACKGROUND_GREEN);

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
        this(DEFAULT_BACKGROUND_COLOR, DEFAULT_SHOW_HEALTH_INDICATOR);
    }

    public ConsoleRenderer(String backgroundColor) {
        this(backgroundColor, DEFAULT_SHOW_HEALTH_INDICATOR);
    }

    public ConsoleRenderer(boolean showHealthIndicator) {
        this(DEFAULT_BACKGROUND_COLOR, showHealthIndicator);
    }

    public ConsoleRenderer(String backgroundColor, boolean showHealthIndicator) {
        this.backgroundColor = backgroundColor;
        this.showHealthIndicator = showHealthIndicator;
    }

    @Override
    public void render(WorldMap worldMap) {
        for (int row = 0; row < worldMap.getMaxRow(); row++) {
            StringBuilder line = new StringBuilder();
            line.append(backgroundColor);
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
            line.append(AnsiColors.RESET);
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
            if (hpLeftRate < (indicator.getRate() + DOUBLE_PRECISION_EPSILON)) {
                return indicator.getColor() + creatureSprite + backgroundColor;
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

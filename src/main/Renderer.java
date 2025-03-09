package main;

import main.entity.Entity;
import main.entity.Grass;
import main.entity.Rock;
import main.entity.Tree;
import main.entity.creature.Creature;
import main.entity.creature.Herbivore;
import main.entity.creature.Predator;
import main.worldmap.Coordinates;
import main.worldmap.WorldMap;

public class Renderer {
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

    private final WorldMap worldMap;

    Renderer(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void render() {
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
            case Herbivore herbivore -> colorCreatureHP(herbivore);
            case Predator predator -> colorCreatureHP(predator);
            default -> throw new IllegalArgumentException("Unknown main.entity type " + entity);
        };
    }

    // TODO: refactor
    private String colorCreatureHP(Creature creature) {
        return switch (creature) {
            case Herbivore herbivore -> colorHealthIndicator(herbivore.getHp(), herbivore.getMaxHp(), HERBIVORE_SPRITE);
            case Predator predator -> colorHealthIndicator(predator.getHp(), predator.getMaxHp(), PREDATOR_SPRITE);
            default -> throw new IllegalArgumentException("Unknown main.entity type " + creature);
        };
    }


    private String colorHealthIndicator(int currentHp, int maxHp, String creatureSprite) {
        double hpLeftRate = (double) currentHp / maxHp;

        if (hpLeftRate < HealthIndicator.LOW_HP.getRate()) {
            return HealthIndicator.LOW_HP.getColor() + creatureSprite + DEFAULT_BACKGROUND_COLOR;
        } else if (hpLeftRate < HealthIndicator.MIDDLE_HP.getRate()) {
            return HealthIndicator.MIDDLE_HP.getColor() + creatureSprite + DEFAULT_BACKGROUND_COLOR;
        } else if (hpLeftRate <= HealthIndicator.FULL_HP.getRate()) {
            return HealthIndicator.FULL_HP.getColor() + creatureSprite + DEFAULT_BACKGROUND_COLOR;
        }
        return creatureSprite;
    }
}

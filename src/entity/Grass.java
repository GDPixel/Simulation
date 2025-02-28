package entity;

public class Grass extends Entity implements Eatable {
    private static final int HEALTH_RESTORATION_VALUE = 5;
    @Override
    public int getHealthRestorationValue() {
        return HEALTH_RESTORATION_VALUE;
    }
}


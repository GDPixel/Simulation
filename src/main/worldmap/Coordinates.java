package main.worldmap;

public record Coordinates(int row, int column) {

    @Override
    public String toString() {
        return "(" + row + ", " + column + ')';
    }
}


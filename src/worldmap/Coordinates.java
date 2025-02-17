package worldmap;

import java.util.Objects;

public class Coordinates {
    private final int row;
    private final int col;

    //TODO: delete later?
    @Override
    public String toString() {
        return "(" + row + ", " + col + ')';
    }

    public Coordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}


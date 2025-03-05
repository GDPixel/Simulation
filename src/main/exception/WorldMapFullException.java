package main.exception;

public class WorldMapFullException extends RuntimeException {
    public WorldMapFullException(String message) {
        super(message);
    }
}

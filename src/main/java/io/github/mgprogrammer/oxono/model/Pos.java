package io.github.mgprogrammer.oxono.model;

/**
 * The Pos class represents a 2D position with x and y coordinates.
 * This is an immutable data structure, implemented as a record.
 * It provides a custom string representation of the position.
 */
public record Pos(int x, int y) {

    @Override
    public String toString() {
        return "(" + x + " ; " + y + ")";
    }
}

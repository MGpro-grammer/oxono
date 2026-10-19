package io.github.mgprogrammer.oxono.model;

/**
 * The Totem class extends the Piece class and represents a specific type of
 * game piece characterized by a blue color and a symbolic representation.
 * It applies a unique behavior when the piece is rendered as a string.
 *
 * Responsibilities:
 * - Inherits the properties and behaviors of the Piece class.
 * - Overrides the toString method to apply a specific string format for the Totem.
 */
public class Totem extends Piece{

    /**
     * Constructs a Totem game piece with a specific symbol and a predefined blue color.
     *
     * @param symbol the symbolic representation of the totem's role or type in the game.
     */
    public Totem(Symbol symbol){
        super(symbol, Color.BLUE);
    }

    /**
     * Returns the string representation of the Totem object.
     * The representation includes the symbol of the Totem formatted
     * with blue color ANSI escape codes.
     *
     * @return the string representation of the Totem object with blue color formatting.
     */
    @Override
    public String toString() {
        return "\u001B[34m" + getSymbol() + "\u001B[0m";
    }
}

package io.github.mgprogrammer.oxono.model;

/**
 * The Token class represents a specific type of game piece in the system.
 * It extends the abstract base class Piece and provides a concrete implementation
 * for rendering the token's symbolic representation with a color.
 *
 * Responsibilities:
 * - Encapsulates a symbol and a color associated with this token, inherited from the Piece class.
 * - Overrides the toString method to provide a colored string representation
 *   of the token based on its color attribute.
 *
 * The Token class assumes that:
 * - A token's color can be BLACK or PINK, where BLACK is rendered in black text color,
 *   and PINK is rendered in purple/magenta text color.
 * - The token's symbolic representation is obtained from the Symbol enum (e.g., X or O).
 */
public class Token extends Piece{

    /**
     * Constructs a Token with the specified symbol and color.
     *
     * @param symbol the symbolic representation of the token, representing its type or role in the game.
     * @param color the color associated with the token, indicating its team or affiliation.
     */
    public Token(Symbol symbol, Color color) {
        super(symbol, color);
    }

    /**
     * Returns a string representation of the token with its color applied.
     * If the token's color is BLACK, it is rendered in black text color.
     * For any other color (e.g., PINK), it is rendered in purple/magenta text color.
     *
     * @return the colored string representation of the token's symbol.
     */
    @Override
    public String toString() {
        if (getColor() == Color.BLACK) {
            return "\u001B[30m" + getSymbol() + "\u001B[0m";
        }
        return "\u001B[35m" + getSymbol() + "\u001B[0m";
    }
}

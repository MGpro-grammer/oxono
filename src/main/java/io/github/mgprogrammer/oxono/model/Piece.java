package io.github.mgprogrammer.oxono.model;

/**
 * Represents an abstract base class for game pieces that can be extended to
 * define specific types of pieces with unique behaviors and characteristics.
 * A Piece is characterized by its symbolic representation and its assigned color.
 *
 * Responsibilities:
 * - Encapsulates a symbol used to visually represent the piece.
 * - Encapsulates the color associated with the piece.
 * - Serves as a base class for concrete implementations of game pieces.
 *
 * The class is designed to be extended and does not implement its own
 * specific behavior for rendering or other operations.
 */
public abstract class Piece {

    private final Symbol symbol;
    private final Color color;

    /**
     * Constructs a Piece with the specified symbol and color.
     *
     * @param symbol the symbolic representation of the piece, representing its type or role in the game.
     * @param color the color associated with the piece, indicating its team or affiliation.
     */
    public Piece(Symbol symbol, Color color) {
        this.symbol = symbol;
        this.color = color;
    }

    /**
     * Retrieves the symbolic representation of this piece.
     *
     * @return the symbol associated with this piece.
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Returns the color associated with this game piece.
     *
     * @return the color of the game piece, which can be PINK, BLACK, or BLUE.
     */
    public Color getColor() {
        return color;
    }
}

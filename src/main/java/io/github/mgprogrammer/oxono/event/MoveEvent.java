package io.github.mgprogrammer.oxono.event;

import io.github.mgprogrammer.oxono.model.Pos;
import io.github.mgprogrammer.oxono.model.Symbol;

/**
 * Represents an event that occurs when a move is made in the game.
 * This event encapsulates the symbol and the position involved in the move,
 * providing information necessary to notify observers about the action performed.
 */
public class MoveEvent extends EventType {

    private final Symbol symbol;
    private final Pos position;

    /**
     * Creates a new instance of MoveEvent with the specified symbol and position.
     * This event represents the action of a player moving a token to a specific position on the board.
     *
     * @param symbol   the symbol representing the player making the move (e.g., X or O)
     * @param position the position on the board where the token is moved, represented as a Pos object
     */
    public MoveEvent(Symbol symbol, Pos position) {
        this.symbol = symbol;
        this.position = position;
    }

    /**
     * Retrieves the symbol associated with this event.
     *
     * @return the symbol represented as an instance of the Symbol enum.
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Retrieves the position associated with the MoveEvent.
     *
     * @return the position represented as a Pos object.
     */
    public Pos getPosition() {
        return position;
    }
}

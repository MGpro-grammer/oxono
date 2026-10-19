package io.github.mgprogrammer.oxono.event;

import io.github.mgprogrammer.oxono.model.Color;
import io.github.mgprogrammer.oxono.model.Player;

/**
 * Represents an event for determining and notifying the next player's turn
 * in the game. This event encapsulates details of the current player, allowing
 * observers to handle the transition to the next player's turn.
 *
 * This class extends the EventType class, which provides a generic representation
 * for all events occurring in the application.
 */
public class NextEvent extends EventType{
    private final Player currentPlayer;

    /**
     * Constructs a NextEvent with the specified current player.
     * This event is used to notify observers about transitioning to the next player's turn.
     *
     * @param currentPlayer the player whose turn it currently is
     */
    public NextEvent(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Retrieves the current player associated with the event.
     *
     * @return the current player represented as a Player object.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Determines and returns the color of the next player based on the current player's color.
     * If the current player's color is BLACK, the next player's color is PINK.
     * Otherwise, the next player's color is BLACK.
     *
     * @return the color of the next player as an instance of the Color enum.
     */
    public Color getNextPlayer() {
        if (currentPlayer.getColor() == Color.BLACK) {
            return Color.PINK;
        } else {
            return Color.BLACK;
        }
    }
}

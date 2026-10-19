package io.github.mgprogrammer.oxono.strategy;

import io.github.mgprogrammer.oxono.model.Game;
import io.github.mgprogrammer.oxono.model.Pos;

/**
 * The InsertStrategy interface defines a strategy for selecting a position
 * to insert a token in a game. Implementations of this interface provide
 * their specific logic for determining a valid insertion position, adhering
 * to the rules and state of the game.
 */
public interface InsertStrategy {
    /**
     * Determines a position to insert a token in the game according to the
     * implemented strategy. The selected position adheres to the game's rules
     * and the current state of the board.
     *
     * @param game the current state of the game, including the board,
     *             rules, and any relevant game-specific configurations
     * @return the selected position for the token insertion
     */
    Pos chooseInsert(Game game);
}

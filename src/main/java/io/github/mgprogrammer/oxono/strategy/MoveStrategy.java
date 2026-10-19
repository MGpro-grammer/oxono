package io.github.mgprogrammer.oxono.strategy;

import io.github.mgprogrammer.oxono.model.Game;
import io.github.mgprogrammer.oxono.model.Pos;
import io.github.mgprogrammer.oxono.model.Symbol;

/**
 * The MoveStrategy interface provides a mechanism for defining
 * different strategies to choose a move in a game.
 * Implementations of this interface should define the logic
 * for determining the next move based on the current state of the game
 * and the player's symbol.
 */
public interface MoveStrategy {
    /**
     * Determines the next move for a player in the game based on
     * the given game state and the player's symbol.
     *
     * @param game The current state of the game. Provides information
     *             such as the game board, rules, and status.
     * @param symbol The symbol representing the player making the move.
     *               Used to identify which player's move is being determined.
     * @return A Pos object representing the coordinates of the chosen move
     *         on the game board.
     */
    Pos chooseMove(Game game, Symbol symbol);
}

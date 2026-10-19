package io.github.mgprogrammer.oxono.strategy;

import io.github.mgprogrammer.oxono.model.Game;
import io.github.mgprogrammer.oxono.model.Pos;
import io.github.mgprogrammer.oxono.model.Symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * The RandomMoveStrategy class implements the MoveStrategy interface.
 * This strategy chooses the next move for a player randomly from a list of
 * all valid positions on the game board. The randomness ensures no predictable
 * pattern in the player's move selection.
 */
public class RandomMoveStrategy implements MoveStrategy {

    /**
     * Chooses the next move for a player by selecting a random valid position
     * from the list of all possible valid moves for the specified game state.
     *
     * @param game   The current game state from which to determine valid positions.
     * @param symbol The symbol representing the player making the move.
     * @return A randomly selected valid position for the player's next move.
     */
    @Override
    public Pos chooseMove(Game game, Symbol symbol) {
        List<Pos> validPos = getAllValidPosTotem(symbol, game);
        return validPos.get((int) (Math.random() * validPos.size()));
    }

    /**
     * Retrieves a list of all valid positions on the game board where a move
     * can be made by the provided symbol. A position is considered valid
     * if it satisfies the game's rules for a legal move.
     *
     * @param symbol the symbol representing the current player, used to check
     *               valid moves for this player.
     * @param game   the game instance which provides the board state and validates
     *               moves based on its rules.
     * @return a list of positions (Pos objects) that are valid moves for the
     *         specified symbol in the current game state.
     */
    public List<Pos> getAllValidPosTotem(Symbol symbol, Game game) {
        List<Pos> validPos = new ArrayList<>();
        for (int x = 0; x < game.getSize(); x++) {
            for (int y = 0; y < game.getSize(); y++) {
                if (game.getIsValidMove(symbol, new Pos(x, y))) {
                    validPos.add(new Pos(x, y));
                }
            }
        }
        return validPos;
    }
}

package io.github.mgprogrammer.oxono.strategy;

import io.github.mgprogrammer.oxono.model.Game;
import io.github.mgprogrammer.oxono.model.Pos;

import java.util.ArrayList;
import java.util.List;

/**
 * RandomInsertStrategy is an implementation of the InsertStrategy interface
 * that selects a random valid position for token insertion. This strategy
 * evaluates all valid positions on the game board and chooses one randomly,
 * adhering to the game's rules and current state.
 */
public class RandomInsertStrategy implements InsertStrategy {

    /**
     * Selects a random valid position for inserting a token based on the current state of the game.
     *
     * @param game the current game instance, containing the board state and rules for valid token insertion
     * @return a randomly selected valid position for token insertion
     */
    @Override
    public Pos chooseInsert(Game game) {
        List<Pos> validPos = getAllValidPosToken(game);
        int random = (int) (Math.random() * validPos.size());
        return validPos.get(random);
    }

    /**
     * Retrieves all valid positions on the game board where a token can be inserted.
     * This method iterates through the entire game board, checking each position
     * to determine if it is a valid insertion point according to the game's rules.
     *
     * @param game the game instance containing the board and its current state
     * @return a list of all valid positions on the game board for token insertion
     */
    public List<Pos> getAllValidPosToken(Game game) {
        List<Pos> validPos = new ArrayList<>();
        for (int x = 0; x < game.getSize(); x++) {
            for (int y = 0; y < game.getSize(); y++) {
                if (game.getIsValidInsert(new Pos(x, y))) {
                    validPos.add(new Pos(x, y));
                }
            }
        }
        return validPos;
    }
}

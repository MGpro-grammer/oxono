package io.github.mgprogrammer.oxono.model;

import io.github.mgprogrammer.oxono.util.Command;

/**
 * Represents a command to handle the transition to the next turn in the game.
 * This command updates the symbol to be inserted for the next turn while
 * maintaining the ability to revert to the previous state.
 *
 * This class follows the Command design pattern, providing the means to execute
 * and unexecute (undo) its associated actions.
 */
public class NextTurnCommand implements Command {
    private Game game;
    private Symbol previousToInsert;
    private Symbol actualInsert;

    /**
     * Constructs a command that manages the transition to the next turn in a game.
     * This command keeps track of the previous symbol to be inserted, allowing
     * it to revert to the prior state if necessary.
     *
     * @param game the game instance where the turn transition takes place
     * @param previousToInsert the symbol that was set to be inserted before the transition
     */
    public NextTurnCommand(Game game, Symbol previousToInsert) {
        this.game = game;
        this.previousToInsert = previousToInsert;
    }


    /**
     * Executes the command, updating the state of the symbol to be inserted for the next turn.
     * This implementation resets the {@code actualInsert} field to {@code null},
     * preparing the state for future modifications.
     */
    @Override
    public void execute() {
        actualInsert = null;
    }

    /**
     * Reverts the game state to the previous symbol to be inserted.
     * This is part of the undo functionality in the Command design pattern,
     * allowing the game to revert to the state before the last executed command.
     */
    @Override
    public void unexecute() {
        game.revertToInsert(previousToInsert);
    }
}

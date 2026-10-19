package io.github.mgprogrammer.oxono.model;

import io.github.mgprogrammer.oxono.util.Command;

/**
 * Represents a command to insert a token onto the board at a specific position.
 * This command is designed to be executed and unexecuted, enabling reversible game actions.
 * It implements the Command interface to provide standard behavior for command-based operations.
 *
 * The insertion of the token involves interacting with the game board and the current player,
 * ensuring consistency with the game's state and rules.
 */
public class InsertTokenCommand implements Command {

    private final Board board;
    private final Pos insertPos;
    private final Player currentPlayer;
    private final Symbol symbol;

    /**
     * Constructs an InsertTokenCommand that represents an action to insert a token
     * onto the board at a specified position. This command is associated with a
     * specific player and their chosen symbol.
     *
     * @param board the game board where the token will be inserted
     * @param insertPos the position on the board where the token will be placed
     * @param currentPlayer the player performing the insertion
     * @param symbol the symbol representing the token being inserted
     */
    public InsertTokenCommand(Board board, Pos insertPos, Player currentPlayer, Symbol symbol) {
        this.board = board;
        this.insertPos = insertPos;
        this.currentPlayer = currentPlayer;
        this.symbol = symbol;
    }


    /**
     * Executes the command to insert a token on the board at a specified position.
     *
     * The method performs the following operations:
     * 1. Instantiates a new {@code Token} using the specified symbol and the color of the current player.
     * 2. Inserts the created token into the board at the given position using the {@code doInsert} method.
     * 3. Decreases the count of available tokens for the current player, corresponding to the symbol being used.
     *
     * This method is part of a command-based implementation allowing actions in the game to be executed and unexecuted.
     */
    @Override
    public void execute() {
        board.doInsert(new Token(symbol, currentPlayer.getColor()), insertPos);
        currentPlayer.decrease(symbol);
    }

    /**
     * Reverses the previously executed command to restore the game state to its prior state.
     *
     * This method performs the necessary actions to undo a token insertion operation. It removes
     * the token from the specified position on the board by inserting a null value at that position
     * and restores the player's symbol bag by increasing the count associated with the undone token.
     *
     * In essence, this method ensures that the game state is reverted correctly, maintaining
     * consistency with the command pattern's requirements for reversibility.
     */
    @Override
    public void unexecute() {
        board.doInsert(null, insertPos);
        currentPlayer.increase(symbol);
    }
}

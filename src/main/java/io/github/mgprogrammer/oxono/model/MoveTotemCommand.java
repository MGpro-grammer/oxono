package io.github.mgprogrammer.oxono.model;

import io.github.mgprogrammer.oxono.util.Command;

/**
 * Encapsulates a command that moves a totem on the board from one position to another.
 * This command is responsible for executing and unexecuting the move action
 * while preserving the state of the board before and after the move.
 */
public class MoveTotemCommand implements Command {

    private final Board board;
    private final Symbol symbol;
    private Pos nextPos;
    private Pos previousPos;

    /**
     * Constructs a MoveTotemCommand object used to move a totem from one position to another on the board.
     *
     * @param board The game board where the move operation will be performed.
     * @param symbol The symbol representing the totem to be moved.
     * @param nextPos The target position to move the totem to.
     * @param previousPos The initial position of the totem before the move.
     */
    public MoveTotemCommand(Board board, Symbol symbol, Pos nextPos, Pos previousPos) {
        this.board = board;
        this.symbol = symbol;
        this.nextPos = nextPos;
        this.previousPos = previousPos;
    }

    /**
     * Executes the command to move a totem on the board. The method updates
     * the previous position of the totem based on the symbol and performs
     * the move operation to transition the totem from its current position
     * to a new position.
     *
     * This method leverages the board's methods to retrieve the current
     * position of the totem and update the board's state by moving the totem
     * to the specified new position.
     */
    @Override
    public void execute() {
        previousPos = board.getPosTotem(symbol);
        board.doMove(symbol, nextPos, previousPos);
    }

    /**
     * Reverses the effect of the most recent move executed by this command,
     * restoring the totem associated with the given symbol to its previous position
     * on the board. Updates the state of the board accordingly.
     *
     * The method retrieves the current position of the totem from the board,
     * then moves the totem back to its previous position while updating the internal
     * state of the command object to reflect this change.
     */
    @Override
    public void unexecute() {
        nextPos = board.getPosTotem(symbol);
        board.doMove(symbol, previousPos, nextPos);
    }
}

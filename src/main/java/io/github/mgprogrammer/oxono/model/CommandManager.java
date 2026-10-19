package io.github.mgprogrammer.oxono.model;

import io.github.mgprogrammer.oxono.util.Command;
import io.github.mgprogrammer.oxono.util.OxonoException;

import java.util.Stack;

/**
 * The CommandManager class is responsible for managing the undo and redo functionality
 * by maintaining two stacks: one for undoable commands and another for redoable commands.
 * It provides methods to record command history, execute undo operations,
 * and execute redo operations.
 */
public class CommandManager {

    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;

    /**
     * Initializes a new instance of the CommandManager class.
     * This constructor creates two internal stacks: one for storing undoable commands
     * and another for storing redoable commands, enabling the undo/redo functionality.
     */
    public CommandManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * Records a command in the undo stack and clears the redo stack.
     *
     * @param command the command to be added to the undo stack
     */
    public void history(Command command) {
        undoStack.add(command);
        redoStack.clear();
    }

    /**
     * Reverses the last executed command by invoking its unexecute method.
     * The command is removed from the undo stack and added to the redo stack,
     * allowing it to be redone later if needed.
     *
     * @throws OxonoException if the undo stack is empty, indicating there are no commands to undo.
     */
    public void undo() {
        if (undoStack.isEmpty()) {
            throw new OxonoException("Impossible to do undo, it's empty");
        } else {
            Command command = undoStack.pop();
            command.unexecute();
            redoStack.add(command);
        }
    }

    /**
     * Re-executes the last undone command by invoking its execute method.
     * The command is removed from the redo stack and added to the undo stack,
     * ensuring the command history remains consistent.
     *
     * @throws OxonoException if the redo stack is empty, indicating there are no commands to redo.
     */
    public void redo() {
        if (redoStack.isEmpty()) {
            throw new OxonoException("Impossible to do redo, it's empty");
        } else {
            Command command = redoStack.pop();
            command.execute();
            undoStack.add(command);
        }
    }
}

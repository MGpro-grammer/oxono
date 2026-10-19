package io.github.mgprogrammer.oxono.util;

/**
 * Represents a contract for command-based actions in the system.
 * Classes that implement the Command interface encapsulate an action
 * that can be executed and potentially undone, following the command design pattern.
 *
 * This interface ensures that implementing classes provide the necessary
 * methods for executing the command and reversing its effects.
 *
 * Commands are designed to encapsulate specific behavior, making them a suitable
 * choice for systems where reversible or transactional actions are required.
 */
public interface Command {
    /**
     * Executes the encapsulated action represented by the command.
     * This method is intended to be implemented by concrete command classes,
     * providing the specific implementation for the action that the command performs.
     *
     * The execute method is a core part of the command pattern, enabling
     * consistent handling of actions through a unified interface. It can be
     * called directly to perform the action or indirectly by a CommandManager
     * for purposes such as redo operations.
     *
     * Implementations of this method should ensure that the action is performed
     * as intended and may also interact with other system components as needed.
     */
    void execute();
    /**
     * Reverses the effects of a previously executed command.
     *
     * This method is typically used in conjunction with the undo functionality
     * to allow commands to revert the changes they have made. Implementations should
     * ensure that the unexecution process is consistent and effectively reverses the
     * action performed by the corresponding to execute method.
     *
     * It is assumed that this method is invoked in contexts where an executed
     * command is being undone, often managed by a controlling component such
     * as a command manager.
     */
    void unexecute();
}

package io.github.mgprogrammer.oxono.model;

/**
 * Represents the various statuses of a game lifecycle.
 * Each status indicates a different phase or action in the game.
 *
 * - MOVE: Indicates a state where a player is making a move.
 * - INSERT: Represents the phase where elements or items are being added to the game.
 * - NEXT: Denotes the transition to the next player's turn.
 * - END: Signifies the conclusion of the game.
 */
public enum GameStatus {
    MOVE, INSERT, NEXT, END
}

package io.github.mgprogrammer.oxono.model;

import io.github.mgprogrammer.oxono.strategy.InsertStrategy;
import io.github.mgprogrammer.oxono.strategy.MoveStrategy;
import io.github.mgprogrammer.oxono.strategy.RandomInsertStrategy;
import io.github.mgprogrammer.oxono.strategy.RandomMoveStrategy;
import io.github.mgprogrammer.oxono.util.OxonoException;

/**
 * The Robot class is a specialized type of player that operates autonomously in the game.
 * It inherits from the Player class and leverages strategies for making moves and inserting tokens
 * based on its predefined difficulty level.
 */
public class Robot extends Player {

    private final int level;
    private MoveStrategy moveStrategy;
    private InsertStrategy insertStrategy;

    /**
     * Constructs a Robot object with a specified color and difficulty level.
     * The Robot inherits the general player properties and behaviors, while also
     * implementing specific strategies based on its predefined level.
     *
     * @param color the color assigned to this robot
     * @param level the difficulty level of the robot (e.g., 0 for random strategy)
     */
    public Robot(Color color, int level) {
        super(color);
        this.level = level;
        setMindOfRobot();
    }

    /**
     * Constructs a Robot with the specified color, board size, and difficulty level.
     * A Robot is an autonomous player that makes decisions based on strategies defined
     * by its level. The number of tokens available to the Robot is calculated based
     * on the board size, and the Robot's behavior is initialized according to its level.
     *
     * @param color the color assigned to the robot
     * @param size  the size of the game board, used to calculate the initial number of tokens
     * @param level the difficulty level of the robot, which determines its strategies for
     *              making moves and inserting tokens
     */
    public Robot(Color color, int size, int level) {
        super(color, size);
        this.level = level;
        setMindOfRobot();
    }

    /**
     * Configures the AI strategies for the robot player based on its defined difficulty level.
     * The method sets specific move and insert strategies according to the robot's level parameter.
     * If the level is not recognized, an exception is thrown to indicate invalid configuration.
     *
     * At level 0, the robot is assigned the following strategies:
     * - {@link RandomMoveStrategy} for deciding moves randomly.
     * - {@link RandomInsertStrategy} for randomly selecting positions for token insertion.
     *
     * If a level other than 0 is encountered, an {@link OxonoException} is thrown with a message
     * indicating the unrecognized level.
     *
     * This method should be invoked during the robot's initialization to ensure proper behavior
     * in subsequent gameplay interactions.
     *
     * @throws OxonoException if the robot's level does not correspond to a valid predefined difficulty.
     */
    private void setMindOfRobot() {
        if (level == 0) {
            moveStrategy = new RandomMoveStrategy();
            insertStrategy = new RandomInsertStrategy();
        } else {
            throw new OxonoException("Level not found");
        }
    }

    /**
     * Determines the next move for the current player based on the specified game state
     * and the symbol to be placed. The move is selected using the robot's move strategy.
     *
     * @param game the current game instance which provides the state of the game board
     * @param symbol the symbol (e.g., X or O) that the robot intends to place on the board
     * @return the position (Pos) on the game board for the robot's next move
     */
    public Pos chooseMove(Game game, Symbol symbol) {
        return moveStrategy.chooseMove(game, symbol);
    }

    /**
     * Determines a position to insert a token in the game based on the robot's strategy.
     *
     * @param game the current state of the game; provides necessary context for determining the insertion position
     * @return the selected position for insertion, represented as a Pos object
     */
    public Pos chooseInsert(Game game) {
        return insertStrategy.chooseInsert(game);
    }

    /**
     * Generates and returns a random symbol, either Symbol.X or Symbol.O, based on a random number logic.
     * If the player's token bag for X or O is empty, the method recursively calls itself until a valid
     * symbol is selected.
     *
     * @return a randomly selected Symbol (Symbol.X or Symbol.O) while ensuring that the player's token
     *         bag for the selected symbol is not empty.
     */
    public Symbol getRandomTotem() {
        int random = (int) (Math.random() * 101);

        if (random < 50 && !isEmptyTokenX()) {
            return Symbol.X;
        } else if (random >= 50 && !isEmptyTokenO()){
            return Symbol.O;
        } else {
            return getRandomTotem();
        }
    }
}

package io.github.mgprogrammer.oxono.model;

/**
 * The Player class represents a participant in the game, with attributes related to their
 * game piece color and the tokens they control. A player is responsible for managing their
 * tokens and interacting with the board through defined operations.
 *
 * It includes logic for managing tokens of two types (X and O) and provides methods to
 * query and modify the state of these tokens.
 */
public class Player {

    private final Color color;
    private int bagTokenX;
    private int bagTokenO;

    /**
     * Constructs a Player object with a specified color. The player starts with
     * a default number of tokens (8 for each type: X and O).
     *
     * @param color the color assigned to this player
     */
    public Player(Color color) {
        this.color = color;
        this.bagTokenX = 8;
        this.bagTokenO = 8;
    }

    /**
     * Constructs a Player with the specified color and initializes the number
     * of tokens based on the specified board size.
     *
     * @param color the color assigned to the player
     * @param size  the size of the game board, used to calculate the initial
     *              number of tokens for the player
     */
    public Player(Color color, int size) {
        this.color = color;

        int nbrToken = ((size * size) - 4) / 4 ;
        this.bagTokenX = nbrToken;
        this.bagTokenO = nbrToken;
    }

    /**
     * Returns the current number of tokens of type 'X' in the player's bag.
     *
     * @return the number of tokens of type 'X' the player currently holds.
     */
    public int getBagTokenX() {
        return bagTokenX;
    }

    /**
     * Retrieves the number of tokens of type 'O' currently in the player's bag.
     *
     * @return the number of 'O' tokens the player has remaining.
     */
    public int getBagTokenO() {
        return bagTokenO;
    }

    /**
     * Retrieves the color associated with this player.
     *
     * @return the color of the player, which indicates their assigned color piece in the game.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Checks if both types of tokens (X and O) held by the player are empty.
     *
     * @return true if the player has no tokens of type X and type O remaining, false otherwise.
     */
    public boolean isEmptyToken() {
        return isEmptyTokenX() && isEmptyTokenO();
    }

    /**
     * Checks if the player's bag of X tokens is empty.
     *
     * @return true if the bag of X tokens contains no tokens, false otherwise.
     */
    public boolean isEmptyTokenX() {
        return bagTokenX == 0;
    }

    /**
     * Checks if the player's bag of "O" tokens is empty.
     *
     * @return true if the number of "O" tokens in the bag is zero, false otherwise.
     */
    public boolean isEmptyTokenO() {
        return bagTokenO == 0;
    }

    /**
     * Decreases the count of tokens for the specified symbol.
     *
     * @param symbol The symbol whose token count should be decreased.
     *               Expected values are Symbol.X or Symbol.O.
     */
    protected void decrease(Symbol symbol) {
        if (Symbol.X == symbol) {
            bagTokenX -= 1;
        } else {
            bagTokenO -= 1;
        }
    }

    /**
     * Increments the number of tokens in the player's bag for the specified symbol.
     * If the symbol is X, the X token count is increased.
     * Otherwise, the O token count is increased.
     *
     * @param symbol the type of token to increment, either {@code Symbol.X} or {@code Symbol.O}
     */
    protected void increase(Symbol symbol) {
        if (Symbol.X == symbol) {
            bagTokenX += 1;
        } else {
            bagTokenO += 1;
        }
    }
}

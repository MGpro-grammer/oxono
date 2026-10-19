package io.github.mgprogrammer.oxono.controller;

import io.github.mgprogrammer.oxono.console.Console;
import io.github.mgprogrammer.oxono.model.*;
import io.github.mgprogrammer.oxono.view.ViewBoard;
import io.github.mgprogrammer.oxono.view.ViewData;

/**
 * The Controller class is responsible for managing the interaction between
 * the game logic and the console interface. It handles user inputs, game status
 * updates, and player turns. This class acts as a mediator to control the flow
 * of the game and interact with both players and robots.
 */
public class Controller {

    private final Game game;
    private Console console;

    /**
     * Constructs a new Controller object with the specified Game and Console instances.
     *
     * @param game the Game instance this controller will manage
     * @param console the Console instance used for input/output interactions
     */
    public Controller(Game game, Console console) {
        this.game = game;
        this.console = console;
    }

    /**
     * Constructs a new Controller instance and initializes the game with the specified board size and AI difficulty level.
     *
     * @param size the size of the game board
     * @param level the difficulty level of the AI
     */
    public Controller(int size, int level) {
        this.game = new Game(size, level);
    }

    /**
     * Initiates the game loop, managing user input, game status, and player actions.
     *
     * This method begins by displaying the current game state and continues running
     * until the game status indicates the end of the game. The primary responsibilities
     * of this method include:
     * 1. Handling user input for various game actions like moving tokens, inserting tokens,
     *    undoing moves, redoing moves, surrendering, and progressing to the next player's turn.
     * 2. Validating inputs against the current game status to execute appropriate actions.
     * 3. Checking for win or draw conditions after each action and updating the game state accordingly.
     * 4. Managing robot players by calling their respective decision-making methods
     *    until a non-robot player becomes active.
     *
     * The method leverages other class methods to perform specific operations
     * such as querying the console for user input, interacting with game logic,
     * and updating the console display to reflect the latest state.
     */
    public void start() {
        console.display();
        while (game.getGameStatus() != GameStatus.END) {
            String data = null;
            if (!(game.getCurrentPlayer() instanceof Robot)) {
                data = console.ask();
            }

            if (data.equals("move") && game.getGameStatus() == GameStatus.MOVE) {
                console.moveTotem();
            } else if (data.equals("insert") && game.getGameStatus() == GameStatus.INSERT) {
                console.insertToken();
            } else if (data.equals("undo")) {
                game.undo();
            } else if (data.equals("redo")) {
                game.redo();
            } else if (data.equals("next") && game.getGameStatus() == GameStatus.NEXT) {
                game.nextTurnPlayer();
            } else if (game.won()) {
                console.displayWin(game.getCurrentPlayer().getColor());
            } else if (game.draw()) {
                console.displayDraw();
            } else if (data.equals("surrender")) {
                game.surrender();
                console.displaySurrender(game.getCurrentPlayer().getColor());
            } else {
                console.displayGameStatus(game.getGameStatus());
            }

            while (game.getCurrentPlayer() instanceof Robot robot) {
                askRobotToPlay(robot);
            }
        }
    }

    /**
     * Executes the turn of the current player if the player is a Robot.
     * This method retrieves the current player from the game and casts it
     * to a Robot. It then delegates the Robot's gameplay actions by invoking
     * the private method askRobotToPlay, which handles the Robot's moves or inserts
     * based on the game's current status.
     *
     * The game status is checked to determine whether the Robot should make a move
     * or insert a piece, and the corresponding actions are handled accordingly.
     */
    public void robotTurn() {
        Robot robot = (Robot) game.getCurrentPlayer();
        askRobotToPlay(robot);
    }

    /**
     * Executes the robot's turn to play based on the current game status.
     * If the game status is MOVE, the robot selects a random symbol, chooses a move position,
     * and performs the move. If the game status is INSERT, the robot selects an insertion
     * position and performs the insertion.
     *
     * @param robot The robot player that will perform the actions during its turn.
     */
    private void askRobotToPlay(Robot robot) {
        if (game.getGameStatus() == GameStatus.MOVE) {
            Symbol symbol = robot.getRandomTotem();
            Pos movePos = robot.chooseMove(game, symbol);
            game.move(symbol, movePos);
        } else if (game.getGameStatus() == GameStatus.INSERT) {
            Pos insertPos = robot.chooseInsert(game);
            game.insert(insertPos);
        }
    }

    /**
     * Checks if a totem represented by the given symbol can be moved to the specified position.
     *
     * This method interacts with the game logic to verify whether the move is valid and
     * permissible according to the game's current state and rules.
     *
     * @param symbol the symbol of the totem (e.g., X or O) to be moved
     * @param newPos the target position to which the totem is intended to move
     * @return {@code true} if the move is valid and successfully executed; {@code false} otherwise
     */
    public boolean ableToMove(Symbol symbol, Pos newPos) {
        return game.move(symbol, newPos);
    }

    /**
     * Determines whether it is valid to insert a token at the specified position.
     *
     * @param insertPos the position where a token is intended to be inserted
     * @return true if the insertion at the specified position is valid, false otherwise
     */
    public boolean ableToInsert(Pos insertPos) {
        return game.insert(insertPos);
    }

    /**
     * Checks whether a move is valid for a given symbol at a specific position.
     *
     * @param symbol the symbol that attempts the move, typically representing a player or a totem.
     * @param movePos the position on the board where the move is being attempted.
     * @return true if the move is valid according to game rules; false otherwise.
     */
    public boolean askIsValidMove(Symbol symbol, Pos movePos) {
        return game.getIsValidMove(symbol, movePos);
    }

    /**
     * Checks if a token can be validly inserted at a specified position on the board.
     *
     * @param insertPos the position where the token is intended to be inserted
     * @return true if the insertion is valid, otherwise false
     */
    public boolean askIsValidInsert(Pos insertPos) {
        return game.getIsValidInsert(insertPos);
    }

    /**
     * Retrieves a {@link Player} based on the given string identifier.
     * Accepts specific keywords to determine which player to return.
     *
     * @param ask a string identifier for the player to retrieve.
     *            Accepted values are:
     *            - "black": retrieves the black player.
     *            - "pink": retrieves the pink player.
     *            - "current": retrieves the current active player.
     *            If any other value is provided, the method returns null.
     * @return the {@link Player} object corresponding to the specified
     *         identifier, or null if the identifier is invalid.
     */
    public Player askPlayer(String ask) {
        switch (ask) {
            case "black" -> {
                return game.getPlayerBlack();
            }
            case "pink" -> {
                return game.getPlayerPink();
            }
            case "current" -> {
                return game.getCurrentPlayer();
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Retrieves the total number of empty spaces available in the game.
     *
     * @return the number of empty spaces available in the game.
     */
    public int askEmptySpaces() {
        return game.emptySpaces();
    }

    /**
     * Retrieves the size of the game grid or board.
     *
     * @return the size of the grid or board as an integer.
     */
    public int askSize() {
        return game.getSize();
    }

    /**
     * Retrieves the piece located at the specified position on the board.
     *
     * @param pos the position on the board for which to retrieve the piece
     * @return the piece at the specified position, or null if no piece is present
     */
    public Piece askPiece(Pos pos) {
        return game.getPieceOfBoard(pos);
    }

    /**
     * Retrieves the current status of the game.
     *
     * @return the current {@code GameStatus} which can be MOVE, INSERT, NEXT, or END.
     */
    public GameStatus askGameStatus() {
        return game.getGameStatus();
    }

    /**
     * Executes an undo or redo operation on the game based on the provided input.
     *
     * @param ask a String representing the requested action, "undo" to perform an undo
     *            operation or "redo" to perform a redo operation.
     */
    public void askUndoRedo(String ask) {
        if (ask.equals("undo")) {
            game.undo();
        } else if (ask.equals("redo")) {
            game.redo();
        }
    }

    /**
     * Checks if the game has been won.
     *
     * @return true if the game has been won, otherwise false.
     */
    public boolean askWin() {
        return game.won();
    }

    /**
     * Checks whether the game has resulted in a draw.
     *
     * @return true if both players have no remaining tokens and the game
     *         status is set to END; false otherwise.
     */
    public boolean askDraw() {
        return game.draw();
    }

    /**
     * Triggers the surrender action in the current game session.
     * This method invokes the `surrender` operation on the associated `Game` object,
     * setting the game status to end and indicating that the current player has surrendered.
     */
    public void askSurrender() {
        game.surrender();
    }

    /**
     * Updates the game state to transition to the next player's turn.
     * Invokes the method that determines the next player in the game
     * and updates the game status. It also triggers any appropriate
     * notifications for observers.
     */
    public void askNextPlayer() {
        game.nextTurnPlayer();
    }

    /**
     * Attaches the given observers to the game instance, allowing these observers
     * to be notified of changes or events occurring in the game.
     *
     * @param viewBoard the ViewBoard observer that will observe and respond to game events
     *                  for visual updates on the game board.
     * @param viewData  the ViewData observer that gathers and manages game data updates
     *                  for additional visual or logical processing.
     */
    public void attachObservers(ViewBoard viewBoard, ViewData viewData) {
        game.registerObserver(viewBoard);
        game.registerObserver(viewData);
    }
}

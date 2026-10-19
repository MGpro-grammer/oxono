package io.github.mgprogrammer.oxono.model;

import io.github.mgprogrammer.oxono.event.*;
import io.github.mgprogrammer.oxono.event.*;
import io.github.mgprogrammer.oxono.util.Observable;
import io.github.mgprogrammer.oxono.util.Observer;

import java.util.HashSet;
import java.util.Set;

/**
 * The Game class represents the core functionality of the game. It implements the Observable interface
 * to allow observers to be notified of events occurring in the game.
 * The game involves two players, each of whom takes turns to either move or insert pieces on the board.
 * It handles the game state, player interactions, and command execution for move and insert operations.
 */
public class Game implements Observable {

    private final Board board;
    private final Player playerBlack;
    private final Player playerPink;
    private Player currentPlayer;
    private Symbol toInsert;
    private GameStatus gameStatus;
    private final CommandManager commandManager = new CommandManager();

    private final Set<Observer> observers = new HashSet<>();

    /**
     * Default constructor for the Game class.
     * Initializes a new game with default settings:
     * - Creates a new game board.
     * - Sets up two players with predefined colors (BLACK and PINK).
     * - Assigns the first turn to the PINK player.
     * - Sets the game's status to "MOVE".
     * - Initializes the piece to be inserted as null.
     */
    public Game() {
        this.board = new Board();
        this.playerBlack = new Player(Color.BLACK);
        this.playerPink = new Player(Color.PINK);
        this.currentPlayer = playerPink;
        this.gameStatus = GameStatus.MOVE;
        this.toInsert = null;
    }

    /**
     * Constructs a new instance of the Game class with the specified board size and robot difficulty level.
     *
     * @param size the size of the board to be created.
     * @param level the difficulty level of the Robot player (Black).
     */
    public Game(int size, int level) {
        this.board = new Board(size);
        this.playerBlack = new Robot(Color.BLACK, size, level);
        this.playerPink = new Player(Color.PINK, size);
        this.currentPlayer = playerPink;
        this.gameStatus = GameStatus.MOVE;
        this.toInsert = null;
    }

    /**
     * Retrieves the size of the game board.
     *
     * @return the size of the game board as an integer.
     */
    public int getSize() {
        return board.getSize();
    }

    /**
     * Reverts the current operation to an insert operation by setting the provided symbol.
     *
     * @param toInsert The symbol to be inserted during the revert operation.
     */
    protected void revertToInsert(Symbol toInsert) {
        this.toInsert = toInsert;
    }

    /**
     * Retrieves the symbol to be inserted.
     *
     * @return the Symbol object that is to be inserted.
     */
    private Symbol getToInsert() {
        return toInsert;
    }

    /**
     * Evaluates whether a move is valid in the current game state for the given symbol and position.
     *
     * A move is considered invalid if:
     * - The game is not in the MOVE status.
     * - The specified position is outside the bounds of the board.
     * - The specified position is not empty.
     * - The current player does not have the required token (handled via {@code isValidMoveWithBag}).
     *
     * If the move passes the above conditions, it is considered valid under the following rules:
     * - If the totem associated with the symbol is blocked both horizontally and vertically, the move is valid.
     * - If the move aligns with the rules for valid movements for the symbol, it is valid.
     * - If the symbol's position is enclosed and the move is valid under enclosed movement constraints, it is valid.
     *
     * @param symbol the symbol (e.g., X or O) representing the player making the move
     * @param movePos the position on the board to move to, represented as a {@code Pos} object
     * @return {@code true} if the move is valid; {@code false} otherwise
     */
    public boolean getIsValidMove(Symbol symbol, Pos movePos) {
        boolean isValid;

        if (GameStatus.MOVE != gameStatus || !board.isInside(movePos) || !board.isEmpty(movePos) ||
                isValidMoveWithBag(symbol, currentPlayer)) {
            isValid = false;
        } else if (board.isTotemBlocked(symbol)) {
            isValid = true;
        } else if (board.isValidMove(symbol, movePos)) {
            isValid = true;
        } else isValid = board.isEnclosed(symbol) && board.isValidMoveEnclosed(symbol, movePos);

        return isValid;
    }

    /**
     * Determines whether the insertion at the specified position is valid.
     * The method evaluates the game state and checks the board conditions for a valid insertion.
     *
     * @param insertPos the {@code Pos} object representing the coordinates of the position to be validated for insertion.
     * @return {@code true} if the insertion at the given position is valid based on the current game state,
     *         board bounds, position emptiness, and specific symbol validation rules; {@code false} otherwise.
     */
    public boolean getIsValidInsert(Pos insertPos) {
        boolean isValid;

        if (GameStatus.INSERT != gameStatus || !board.isInside(insertPos) || !board.isEmpty(insertPos)) {
            isValid = false;
        } else if (board.isEnclosed(toInsert)) {
            isValid = true;
        } else isValid = board.isValidInsert(toInsert, insertPos);

        return isValid;
    }

    /**
     * Retrieves the current active player in the game.
     *
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Retrieves the current status of the game.
     *
     * @return the current {@code GameStatus}, which represents the state of the game
     *         (e.g., MOVE, INSERT, NEXT, or END).
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * Retrieves the black player participating in the game.
     *
     * @return the player assigned to the black pieces in the game.
     */
    public Player getPlayerBlack() {
        return playerBlack;
    }

    /**
     * Retrieves the player with the pink color.
     *
     * @return the Player object representing the pink-colored player
     */
    public Player getPlayerPink() {
        return playerPink;
    }

    /**
     * Retrieves the piece located at the specified position on the board.
     *
     * @param pos the position on the board from which to retrieve the piece
     * @return the piece located at the specified position, or null if the position is empty
     */
    public Piece getPieceOfBoard(Pos pos) {
        return board.getPiece(pos);
    }

    /**
     * Determines whether a specific position on the game board is empty.
     *
     * @param pos the position to check, represented as a {@code Pos} object
     * @return {@code true} if the specified position on the board is empty, {@code false} otherwise
     */
    public boolean isEmptyOfBoard(Pos pos) {
        return board.isEmpty(pos);
    }

    /**
     * Checks whether there is a winning condition in the game.
     * If a win is detected, the game status is updated to END.
     *
     * @return true if a winning condition is met on the board, false otherwise.
     */
    public boolean won() {
        if (board.checkWin()) {
            gameStatus = GameStatus.END;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the game is in a draw state. A draw state occurs when both
     * playerBlack and playerPink have no available tokens to play.
     * If both players are out of tokens, the game status is updated to END.
     *
     * @return true if the game is in a draw state and the status is updated to END,
     *         false otherwise.
     */
    public boolean draw() {
        if (playerBlack.isEmptyToken() && playerPink.isEmptyToken()) {
            gameStatus = GameStatus.END;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Ends the current game by setting the game status to END.
     *
     * @return true after the game status is successfully changed to END.
     */
    public boolean surrender() {
        gameStatus = GameStatus.END;
        return true;
    }

    /**
     * Determines whether the move is valid for the player based on the tokens available in their bag.
     *
     * @param symbol the symbol (X or O) being used for the move
     * @param currentPlayer the player who is attempting the move
     * @return true if the player has at least one token of the given symbol in their bag, false otherwise
     */
    public boolean isValidMoveWithBag(Symbol symbol, Player currentPlayer) {
        if (Symbol.X == symbol) {
            return currentPlayer.isEmptyTokenX();
        } else {
            return currentPlayer.isEmptyTokenO();
        }
    }

    /**
     * Attempts to move the totem represented by the specified symbol to the given position
     * on the board. The move is performed if it adheres to the game's rules and conditions.
     * This method also updates the game status and notifies observers in case of a valid move.
     *
     * @param symbol the {@link Symbol} representing the totem (X or O) to move
     * @param movePos the target position on the board represented as a {@link Pos} object
     * @return {@code true} if the move is successfully performed, {@code false} otherwise
     */
    public boolean move(Symbol symbol, Pos movePos) {
        boolean isValid;
        MoveTotemCommand moveTotemCommand = new MoveTotemCommand(board, symbol, movePos, board.getPosTotem(symbol));

        if (GameStatus.MOVE != gameStatus || !board.isInside(movePos) || !board.isEmpty(movePos) ||
                isValidMoveWithBag(symbol, currentPlayer)) {
            isValid = false;
        } else if (board.isTotemBlocked(symbol)) {
            board.doMove(symbol, movePos, board.getPosTotem(symbol));
            commandManager.history(moveTotemCommand);
            isValid = true;
        } else if (board.isValidMove(symbol, movePos)) {
            board.doMove(symbol, movePos, board.getPosTotem(symbol));
            commandManager.history(moveTotemCommand);
            isValid = true;
        } else if (board.isEnclosed(symbol) && board.isValidMoveEnclosed(symbol, movePos)) {
            board.doMove(symbol, movePos, board.getPosTotem(symbol));
            commandManager.history(moveTotemCommand);
            isValid = true;
        } else {
            isValid = false;
        }
        if (isValid) {
            gameStatus = GameStatus.INSERT;
            toInsert = symbol;
            EventType eventType = new MoveEvent(symbol, movePos);
            notifyObservers(eventType);
        }

        return isValid;
    }

    /**
     * Attempts to insert a token into the game at the specified position.
     * The method ensures that the insertion respects the game rules, updates
     * the player's token count, modifies the game board accordingly, and triggers appropriate events.
     *
     * @param insertPos the position where the token is to be inserted, represented as a {@code Pos} object
     * @return {@code true} if the insertion was successful; {@code false} otherwise
     */
    public boolean insert(Pos insertPos) {
        boolean isValid;
        InsertTokenCommand insertTokenCommand = new InsertTokenCommand(board, insertPos, currentPlayer, toInsert);

        if (GameStatus.INSERT != gameStatus || !board.isInside(insertPos) || !board.isEmpty(insertPos)) {
            isValid = false;
        } else if (board.isEnclosed(toInsert)) {
            board.doInsert(new Token(toInsert, currentPlayer.getColor()), insertPos);
            commandManager.history(insertTokenCommand);
            isValid = true;
        } else if (board.isValidInsert(toInsert, insertPos)) {
            board.doInsert(new Token(toInsert, currentPlayer.getColor()), insertPos);
            commandManager.history(insertTokenCommand);
            isValid = true;
        } else {
            isValid = false;
        }
        if (isValid) {
            gameStatus = GameStatus.NEXT;
            currentPlayer.decrease(toInsert);
            EventType eventType = new InsertEvent(insertPos, currentPlayer);
            notifyObservers(eventType);
        }

        return isValid;
    }

    /**
     * Switches the current turn to the other player.
     * If the current player is playerBlack, it switches to playerPink.
     * If the current player is playerPink, it switches to playerBlack.
     */
    public void backNextTurnPlayer() {
        if (currentPlayer == playerBlack) {
            currentPlayer = playerPink;
        } else if (currentPlayer == playerPink) {
            currentPlayer = playerBlack;
        }
    }

    /**
     * Advances the game to the next player's turn, updates the game status,
     * and notifies all observers about the turn change through an event.
     *
     * This method performs the following actions:
     * 1. Calls the `backNextTurnPlayer` method to switch the `currentPlayer`
     *    to the next player (alternates between playerBlack and playerPink).
     * 2. Updates the `gameStatus` to `GameStatus.MOVE` to reflect that the new
     *    player's move phase is ready to begin.
     * 3. Creates a `NextEvent` instance encapsulating the new current player
     *    and passes it to the `notifyObservers` method to inform all registered
     *    observers about the change in turn.
     */
    public void nextTurnPlayer() {
        NextTurnCommand nextTurnCommand = new NextTurnCommand(this, this.getToInsert());
        commandManager.history(nextTurnCommand);
        backNextTurnPlayer();
        gameStatus = GameStatus.MOVE;
        EventType eventType = new NextEvent(currentPlayer);
        notifyObservers(eventType);
    }

    /**
     * Calculates the total number of empty spaces available based on the tokens
     * remaining in the bags of the black and pink players, and includes an offset of 2.
     *
     * @return the total number of available empty spaces including the remaining tokens
     *         in the bags of both players and an additional constant value of 2.
     */
    public int emptySpaces() {
        return playerBlack.getBagTokenX() + playerBlack.getBagTokenO() +
                playerPink.getBagTokenX() + playerPink.getBagTokenO() + 2;
    }

    /**
     * Reverts the state of the game by undoing the last executed command and
     * updating the game state accordingly. This method interacts with the
     * command manager to reverse the most recent action and adjust the gameStatus.
     */
    public void undo() {
        commandManager.undo();

        if (gameStatus == GameStatus.NEXT) {
            gameStatus = GameStatus.INSERT;
        } else if (gameStatus == GameStatus.INSERT) {
            gameStatus = GameStatus.MOVE;
        } else if (gameStatus == GameStatus.MOVE) {
            backNextTurnPlayer();
            gameStatus = GameStatus.NEXT;
        }

        if (currentPlayer instanceof Robot) {
            undo();
        }

        EventType eventType = new UndoEvent();
        notifyObservers(eventType);
    }

    /**
     * Re-applies the last undone command using the command manager's redo stack and
     * appropriately adjusts the state of the game. Transition rules for gameStatus are applied.
     */
    public void redo() {
        commandManager.redo();

        if (gameStatus == GameStatus.INSERT) {
            gameStatus = GameStatus.NEXT;
        } else if (gameStatus == GameStatus.MOVE) {
            gameStatus = GameStatus.INSERT;
        } else if (gameStatus == GameStatus.NEXT) {
            backNextTurnPlayer();
            gameStatus = GameStatus.MOVE;
        }

        if (currentPlayer instanceof Robot) {
            redo();
        }

        EventType eventType = new RedoEvent();
        notifyObservers(eventType);
    }

    /**
     * Registers a new observer to the list of observers.
     *
     * @param o the observer to be registered; it will be notified of the
     *          events associated with this observable object.
     */
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    /**
     * Removes an observer from the list of observers.
     *
     * @param o the observer to be removed
     */
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Notifies all registered observers about a specific event.
     *
     * @param eventType the event that occurred, which is passed to the observers
     *                  so they can react to the notification appropriately.
     */
    @Override
    public void notifyObservers(EventType eventType) {
        for (Observer observer : observers) {
            observer.update(eventType);
        }
    }
}

package io.github.mgprogrammer.oxono.console;

import io.github.mgprogrammer.oxono.controller.Controller;
import io.github.mgprogrammer.oxono.event.*;
import io.github.mgprogrammer.oxono.model.*;
import io.github.mgprogrammer.oxono.util.Observer;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Console class provides a text-based interface for the player
 * to interact with the game. It implements the Observer interface
 * to receive updates about game events and displays the state of
 * the game to the user.
 */
public class Console implements Observer {

    private Controller controller;
    private final Scanner scanner = new Scanner(System.in);


    /**
     * Prompts the user for input and returns the entered line of text.
     *
     * @return a string representing the line of text entered by the user
     */
    public String ask() {
        return scanner.nextLine();
    }

    /**
     * Assigns a {@code Controller} instance to the current Console.
     *
     * @param controller the {@code Controller} to be associated with this Console
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Prompts the user to input the coordinates for moving their totem
     * and validates the input. If the command is valid, parses the
     * input and returns the resulting position as a Pos object.
     * If the input is invalid, the method recursively re-prompts
     * the user until valid input is provided.
     *
     * @param scanner the Scanner object used for reading user input
     * @return a Pos object representing the coordinates entered by the user
     */
    private Pos choosePosTotem(Scanner scanner) {

        System.out.println("Where do you want to move your totem ?");
        String command = scanner.nextLine();

        if (isValidCommand(command)) {
            String[] infos = command.split(" ");
            return new Pos(Integer.parseInt(infos[0]), Integer.parseInt(infos[1]));
        } else {
            System.out.println("The command entered is incorrect, try again.");
            return choosePosTotem(scanner);
        }
    }

    /**
     * Prompts the user to choose a totem symbol (X or O) and returns the selected symbol.
     * If the user inputs an invalid choice, they are prompted again until a valid choice is made.
     *
     * @param scanner the scanner object used to read user input
     * @return the chosen totem symbol (Symbol.X or Symbol.O)
     */
    private Symbol chooseWhichTotem(Scanner scanner) {

        System.out.println("Which totem do you choose? [X/O]?");
        String command = scanner.nextLine();

        if (command.equals("X")) {
            return Symbol.X;
        } else if (command.equals("O")) {
            return Symbol.O;
        } else {
            System.out.println("The command entered is incorrect, try again.");
            return chooseWhichTotem(scanner);
        }
    }

    /**
     * Facilitates the movement of a totem on the board by interacting with the user.
     *
     * This method prompts the player to choose a totem they wish to move and a new
     * position to move the totem to. It ensures that the move is valid by querying
     * the controller. If the move is invalid, an appropriate message is displayed
     * and the player is asked to try again by recursively calling the method.
     *
     * The movement logic involves:
     * - Selecting a totem (Symbol) using user input.
     * - Specifying the intended target position (Pos) for the totem.
     * - Checking with the controller if the move is valid.
     * - Notifying the player in case the move is invalid and retrying.
     */
    public void moveTotem() {
        Symbol symbol = chooseWhichTotem(scanner);
        Pos newPos = choosePosTotem(scanner);

        if (!controller.ableToMove(symbol, newPos)) {
            System.out.println("Impossible to move in this direction !");
            moveTotem();
        }
    }

    /**
     * Prompts the user to input a position to insert a token, validates the input,
     * and returns the referenced position as a Pos object. If the input is invalid,
     * it requests the input again recursively until a valid input is provided.
     *
     * @param scanner the Scanner object used to read user input.
     * @return a Pos object representing the chosen position (x, y) for the token.
     */
    private Pos choosePosToken(Scanner scanner) {

        System.out.println("Where do you want to insert your token ?");
        String command = scanner.nextLine();

        if (isValidCommand(command)) {
            String[] infos = command.split(" ");
            return new Pos(Integer.parseInt(infos[0]), Integer.parseInt(infos[1]));
        } else {
            System.out.println("The command entered is incorrect, try again.");
            return choosePosToken(scanner);
        }
    }

    /**
     * Inserts a token into the game at a position chosen by the user.
     * The method uses the `choosePosToken` helper method to prompt the user
     * for input, collect the desired position, and validate the input.
     * If the chosen position is invalid or if a token cannot be placed in the specified location,
     * the method prompts the user to try again recursively.
     *
     * The insertion process depends on the ability of the underlying controller
     * to validate whether the chosen position is valid for token placement.
     * If the position is unavailable for insertion, an error message is displayed,
     * and the user is prompted to provide a new input.
     */
    public void insertToken() {
        Pos insertPos = choosePosToken(scanner);

        if (!controller.ableToInsert(insertPos)) {
            System.out.println("Impossible to place a token here !");
            insertToken();
        }
    }

    /**
     * Validates whether a given command conforms to the expected format.
     * The command is expected to be a string containing two integers separated by a space.
     *
     * @param command the input command string to be validated
     * @return true if the input command matches the expected format, false otherwise
     */
    private boolean isValidCommand(String command) {
        String regex = "^\\d+ \\d+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        return matcher.matches();
    }

    /**
     * Displays the current state of the game board in the console.
     * The board is represented as a grid with rows and columns labeled numerically.
     * Each cell in the grid will display either:
     * - A piece's symbol if a piece is present at the corresponding position.
     * - A dot (.) if the cell is empty.
     * Additionally, labels and a dividing line are used for visual clarity.
     * After rendering the board, displays the current player's turn and game status by invoking
     * an auxiliary method.
     */
    public void display() {

        System.out.print("  ");
        for (int j = 0; j < 6; j++) {
            System.out.print(" " + j + " ");
        }
        System.out.println();
        System.out.print("  " + "___".repeat(6) + "\n");
        for (int i = 0; i < 6; i++) {
            System.out.print(i + "|");
            for (int j = 0; j < 6; j++) {
                Piece piece = controller.askPiece(new Pos(i, j));
                System.out.print(piece == null ? " . " : " " + piece + " ");
            }
            System.out.println();
        }
        displayPiecePlayer();
    }

    /**
     * Displays information about the current player, including their color,
     * the number of X and O tokens remaining, and the remaining number of empty spaces on the board.
     * <ul>
     * - Retrieves the current player using the controller.
     * - Prints the player's color and the remaining tokens for both X and O.
     * - Retrieves and prints the number of empty spaces on the board.
     * </ul>
     * This method is intended to provide a concise overview of the player's status during their turn.
     */
    private void displayPiecePlayer() {
        Player currentPlayer = controller.askPlayer("current");
        System.out.println("Player " + currentPlayer.getColor() + " is your turn.");
        System.out.println("Token X remaining : " + currentPlayer.getBagTokenX());
        System.out.println("Token O remaining : " + currentPlayer.getBagTokenO());
        int emptySpaces = controller.askEmptySpaces();
        System.out.println("Empty cell remaining : " + emptySpaces);
    }

    /**
     * Displays a message indicating that the player with the specified color has won the game.
     *
     * @param color the color of the player who won the game
     */
    public void displayWin(Color color) {
        System.out.println("Player " + color + ", you won the game !");
    }

    /**
     * Displays a message to indicate that the game has ended in a draw.
     * The method prints a predefined message to the console, notifying
     * the players of the draw outcome.
     */
    public void displayDraw() {
        System.out.println("Sorry it's a draw !");
    }

    /**
     * Displays a message indicating that a player has surrendered.
     *
     * @param color the color associated with the surrendering player
     */
    public void displaySurrender(Color color) {
        System.out.println("Oh no ! You have surrender " + color + " !");
    }

    /**
     * Displays the current game status with a corresponding message.
     *
     * @param gameStatus the current status of the game, represented as a value
     *                   from the {@code GameStatus} enum (e.g., MOVE, INSERT, NEXT, END).
     */
    public void displayGameStatus(GameStatus gameStatus) {
        System.out.println("Sorry you have to " + gameStatus + ", thank you !");
    }

    /**
     * Updates the state based on the provided event type, handling specific logic
     * for different types of events. After processing the event, the display is
     * updated to reflect the current state.
     *
     * @param eventType the type of event to process. It can be an instance of
     *                  MoveEvent, InsertEvent, NextEvent, UndoEvent, or RedoEvent.
     *                  The method performs specific actions depending on the
     *                  concrete type of the event.
     */
    @Override
    public void update(EventType eventType) {
        if (eventType instanceof MoveEvent) {
            System.out.println("The Totem (" + ((MoveEvent) eventType).getSymbol() +
                    ") moved to position " + ((MoveEvent) eventType).getPosition() + ".");
        } else if (eventType instanceof InsertEvent) {
            System.out.println("The Token was inserted at position " + ((InsertEvent) eventType).getPosition()
                    + " by the " + ((InsertEvent) eventType).getPlayer().getColor() + " player.");
        } else if (eventType instanceof NextEvent) {
            System.out.println("Change of player from " + ((NextEvent) eventType).getNextPlayer()
                    + " to " + ((NextEvent) eventType).getCurrentPlayer().getColor() + ".");
        } else if (eventType instanceof UndoEvent) {
            System.out.println("An undo has been performed.");
        } else if (eventType instanceof RedoEvent) {
            System.out.println("A redo has been performed.");
        }
        display();
    }

    public static void main(String[] args) {
        Game game = new Game();
        Console console = new Console();
        Controller controller = new Controller(game, console);

        console.setController(controller);

        game.registerObserver(console);

        controller.start();
    }
}

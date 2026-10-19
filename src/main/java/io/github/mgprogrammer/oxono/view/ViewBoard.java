package io.github.mgprogrammer.oxono.view;

import io.github.mgprogrammer.oxono.controller.Controller;
import io.github.mgprogrammer.oxono.event.*;
import io.github.mgprogrammer.oxono.model.*;
import io.github.mgprogrammer.oxono.event.*;
import io.github.mgprogrammer.oxono.model.*;
import io.github.mgprogrammer.oxono.util.Observer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.Objects;

/**
 * The ViewBoard class is a graphical representation of a game board, responsible for rendering
 * the game layout and handling user interactions. It extends {@code GridPane} from JavaFX
 * and implements the {@code Observer} interface to react to game updates.
 *
 * Responsibilities:
 * - Displays the game board grid and its contents, including pieces such as Tokens and Totems.
 * - Handles user interactions, such as clicking on cells, selecting pieces, and performing moves or inserts.
 * - Dynamically updates the board display in response to game events.
 * - Communicates directly with a {@code Controller} to query game state and perform actions.
 *
 * The board visually represents various game pieces using images, and it adjusts styles based
 * on their state, such as validating potential moves or indicating invalid placements.
 *
 * This class relies on a {@code Controller} for accessing and manipulating the game's state
 * and logic. It assumes the presence of a reactive event-driven architecture where it listens
 * for updates and visually refreshes to reflect changes in the game's state.
 */
public class ViewBoard extends GridPane implements Observer {

    private Piece selectedPiece;
    private final Image totemXImage;
    private final Image totemOImage;
    private final Image tokenPinkXImage;
    private final Image tokenPinkOImage;
    private final Image tokenBlackXImage;
    private final Image tokenBlackOImage;
    private Controller controller;

    /**
     * The ViewBoard class represents the graphical board for the game.
     * It extends the GridPane class from JavaFX and is an observer of game events.
     *
     * The class initializes all the required images for pieces and tokens in the game.
     * These images are loaded from resources and are used to display the game state.
     * Additionally, a CSS stylesheet is applied to stylize the view.
     *
     * This constructor also ensures that any required resources are correctly
     * initialized before the board is displayed.
     */
    public ViewBoard() {
        super();
        this.selectedPiece = null;
        this.totemXImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/totem-X.png")));
        this.totemOImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/totem-O.png")));
        this.tokenPinkXImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/token-pink-X.png")));
        this.tokenPinkOImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/token-pink-O.png")));
        this.tokenBlackXImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/token-black-X.png")));
        this.tokenBlackOImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/token-black-O.png")));
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        this.getStyleClass().add("view-board");
    }

    /**
     * Assigns a controller to the ViewBoard.
     * The controller is responsible for managing interactions between the view and the underlying data or logic.
     *
     * @param controller the Controller instance to be associated with the ViewBoard
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Initializes and sets up the game grid. This method creates a two-dimensional
     * grid structure representing the game board, where each cell is visualized
     * using a {@link StackPane}. Each cell is populated with a {@link Region} for
     * styling purposes and, if applicable, a {@link Piece} that represents the
     * game piece present at that position. The method assigns mouse event handlers
     * to cells for user interactions and manages the game flow when a {@link Robot}
     * is the current player.
     */
    public void setGrid() {
        for (int row = 0; row < controller.askSize(); row++) {
            for (int col = 0; col < controller.askSize(); col++) {
                StackPane stack = new StackPane();
                Region square = new Region();

                stack.getStyleClass().add("cell-selected");
                square.getStyleClass().add("cell");

                Piece piece = controller.askPiece(new Pos(row, col));
                addPieceToStack(stack, square, piece);

                if (controller.askPlayer("current") instanceof Robot) {
                    controller.robotTurn();
                }

                if (controller.askGameStatus() != GameStatus.END) {
                    mainMouseEvents(stack, square, row, col);
                }

                add(stack, row, col);
            }
        }
    }

    /**
     * Adds a game piece and its associated graphical elements to a stack.
     * If the piece is null, only the base square is added to the stack.
     * Otherwise, both the square and the piece's visual representation
     * are added.
     *
     * @param stack the StackPane to which the elements are added, representing the visual container.
     * @param square the Region representing the base square of the board cell.
     * @param piece the Piece to be added. If null, only the square is added to the stack.
     */
    private void addPieceToStack(StackPane stack, Region square, Piece piece) {
        if (piece == null) {
            stack.getChildren().add(square);
            return;
        }

        ImageView imageView = getImageViewForPiece(piece);
        stack.getChildren().addAll(square, imageView);
    }

    /**
     * Creates and returns an ImageView object representing the graphical representation
     * of the given game piece. The ImageView dimensions are set to match the specified
     * cell size. The method determines the specific image based on whether the piece
     * is a Totem or a Token and their respective symbols and colors.
     *
     * @param piece the game piece for which the corresponding ImageView is to be generated.
     *              It can be an instance of Totem or Token, characterized by a symbol
     *              (X or O) and a color (PINK, BLACK, or BLUE).
     * @return an ImageView object configured with the corresponding image for the
     *         given piece, matching its symbol and color.
     */
    private ImageView getImageViewForPiece(Piece piece) {
        ImageView imageView = new ImageView();
        imageView.getStyleClass().add("image-piece");

        if (piece instanceof Totem totem) {
            if (totem.getSymbol() == Symbol.X) {
                imageView.setImage(totemXImage);
            } else {
                imageView.setImage(totemOImage);
            }
        } else if (piece instanceof Token token) {
            if (token.getSymbol() == Symbol.X && token.getColor() == Color.PINK) {
                imageView.setImage(tokenPinkXImage);
            } else if (token.getSymbol() == Symbol.X && token.getColor() == Color.BLACK) {
                imageView.setImage(tokenBlackXImage);
            } else if (token.getSymbol() == Symbol.O && token.getColor() == Color.PINK) {
                imageView.setImage(tokenPinkOImage);
            } else if (token.getSymbol() == Symbol.O && token.getColor() == Color.BLACK) {
                imageView.setImage(tokenBlackOImage);
            }
        }

        return imageView;
    }

    /**
     * Handles the main mouse events for a specific cell in the grid.
     * This method sets up mouse click and hover interactions for a given stack pane
     * and its associated square region, identified by its row and column indices.
     *
     * @param stack The {@code StackPane} representing the cell where events are to be applied.
     * @param square The {@code Region} associated with the cell, representing its visual appearance.
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     */
    private void mainMouseEvents(StackPane stack, Region square, int row, int col) {
        stack.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                primaryClick(row, col);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                secondaryClick(row, col);
            }
        });

        stack.setOnMouseEntered(event -> placementCheck(square, new Pos(row, col)));

        stack.setOnMouseExited(event -> bringBackCell(square));
    }

    /**
     * Handles the primary click events on the game board at a specific cell.
     * If no piece is currently selected and the clicked cell contains a valid piece
     * (not a Token), the piece will be selected. Otherwise, it determines whether a
     * token can be inserted at the clicked position.
     *
     * @param row the row index of the cell that was clicked
     * @param col the column index of the cell that was clicked
     */
    private void primaryClick(int row, int col) {
        Piece piece = controller.askPiece(new Pos(row, col));
        if (selectedPiece == null && piece != null && !(piece instanceof Token)) {
            selectedPiece = piece;
        } else {
            Pos pos = new Pos(row, col);
            controller.ableToInsert(pos);
        }
    }

    /**
     * Handles the secondary click action within the game interface. This method is invoked
     * when a user performs a secondary click on a specific position in the grid.
     * It determines if a selected game piece can be moved to the specified position
     * and updates the game state accordingly.
     *
     * @param row the row index of the grid where the secondary click occurred
     * @param col the column index of the grid where the secondary click occurred
     */
    private void secondaryClick(int row, int col) {
        if (selectedPiece != null) {
            Symbol symbol = selectedPiece.getSymbol();
            Pos pos = new Pos(row, col);
            controller.ableToMove(symbol, pos);
            selectedPiece = null;
        }
    }

    /**
     * Determines the visual representation of a game board cell during a placement operation.
     * Updates the style of the given Region (game board cell) based on the validity of the
     * move or insertion at the specified position.
     *
     * @param square the Region representing the game board cell whose visual style will be updated
     * @param pos the position on the game board being checked for a valid move or insertion
     */
    private void placementCheck(Region square, Pos pos) {
        if (selectedPiece != null) {
            if (selectedPiece instanceof Totem) {
                if (controller.askIsValidMove(selectedPiece.getSymbol(), pos)) {
                    square.getStyleClass().clear();
                    square.getStyleClass().add("cell-green");
                } else {
                    square.getStyleClass().clear();
                    square.getStyleClass().add("cell-red");
                }
            }
        } else {
            if (controller.askIsValidInsert(pos)) {
                square.getStyleClass().clear();
                square.getStyleClass().add("cell-green");
            } else {
                square.getStyleClass().clear();
                square.getStyleClass().add("cell-red");
            }
        }
    }

    /**
     * Resets the visual appearance of a given cell (Region) by clearing its
     * style classes and applying the default "cell" style.
     *
     * @param square The Region object representing the cell to reset.
     */
    private void bringBackCell(Region square) {
        square.getStyleClass().clear();
        square.getStyleClass().add("cell");
    }

    /**
     * Updates the state of the view based on the type of event provided.
     * Observes various game events and triggers the appropriate action to reflect changes
     * in the user interface, such as moves, insertions, turn changes, undo or redo actions.
     *
     * @param eventType the event that occurred, represented by an instance of the EventType class or its subclasses.
     *                  This parameter determines the type of action to perform:
     *                  - For MoveEvent: Logs the symbol and position of the move.
     *                  - For InsertEvent: Logs the position and the player's color who performed the insertion.
     *                  - For NextEvent: Logs the change of turn from one player to the next.
     *                  - For UndoEvent: Logs an undo action.
     *                  - For RedoEvent: Logs a redo action.
     */
    @Override
    public void update(EventType eventType) {
        Platform.runLater(() -> {
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
            setGrid();
        });
    }
}

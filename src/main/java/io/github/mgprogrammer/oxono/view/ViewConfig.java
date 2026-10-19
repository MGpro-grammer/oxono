package io.github.mgprogrammer.oxono.view;

import io.github.mgprogrammer.oxono.controller.Controller;
import io.github.mgprogrammer.oxono.model.GameStatus;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Objects;

/**
 * ViewConfig is a graphical user interface class extending VBox that provides the layout and
 * functionality for a simple interactive view. It contains buttons and labels to manage user
 * actions like undo, redo, next turn, and surrender, which interact with a controller class to
 * perform game-related operations.
 *
 * This class utilizes JavaFX components, such as Buttons, Labels, HBox, and PauseTransition,
 * along with CSS for styling the UI.
 */
public class ViewConfig extends VBox {

    private Button left;
    private Button right;
    private Button hold;
    private Button next;
    private PauseTransition holdTimer;
    private Controller controller;
    private final Label playerSurrender;

    /**
     * Constructs a new instance of the ViewConfig class, which provides various configurations for
     * undo and redo functionality, next turn button, surrender button, and UI layout.
     * This class extends VBox and focuses on rendering and interaction for a specific
     * part of the user interface.
     *
     * The constructor initializes default properties, such as stylesheets and preferred size,
     * and calls private methods to configure buttons and their respective actions.
     * Key elements are added to the VBox instance with specific alignments and spacing.
     */
    public ViewConfig() {
        super(10);
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        this.getStyleClass().add("config-size");
        configUndoAndRedo();
        configNextButton();
        HBox undoAndRedo = new HBox(10);

        undoAndRedo.getChildren().addAll(left, right);
        undoAndRedo.getStyleClass().add("view-config");

        playerSurrender = new Label();

        this.getChildren().addAll(undoAndRedo, hold, playerSurrender, next);
        this.getStyleClass().add("view-config");
    }

    /**
     * Sets the controller for this view configuration.
     *
     * @param controller the controller to be associated with this view.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Configures the undo and redo functionality as well as the surrender button for the view.
     *
     * The method initializes buttons for undo and redo actions with corresponding icons
     * and sets their behavior to invoke undo or redo operations when clicked, provided
     * the game is not in the END state.
     *
     * Additionally, it sets up a "Surrender" button and configures its behavior
     * using the {@code holdButton} method. The surrender button triggers a surrender
     * action when pressed for a specified duration.
     */
    private void configUndoAndRedo() {
        left = initButton("/images/fleche-gauche.png");
        left.setOnAction(event -> {
            if (controller.askGameStatus() != GameStatus.END) {
                controller.askUndoRedo("undo");
            }
        });

        right = initButton("/images/fleche-droite.png");
        right.setOnAction(event -> {
            if (controller.askGameStatus() != GameStatus.END) {
                controller.askUndoRedo("redo");
            }
        });

        hold = new Button("Surrender");
        holdButton();
    }

    /**
     * Configures the "Next Turn" button used to transition to the next player's turn
     * in the game. Initializes the button with appropriate text and sets its action event.
     * Upon clicking the button:
     * - Checks if the current game status is `NEXT` (indicating readiness to move to the next turn).
     * - If so, triggers the transition to the next player by calling the appropriate method in the controller.
     *
     * This method directly interacts with the game's controller to manage the flow between players.
     */
    private void configNextButton() {
        next = new Button("Next Turn");
        next.setOnAction(event -> {
            if (controller.askGameStatus() == GameStatus.NEXT) {
                controller.askNextPlayer();
            }
        });
    }

    /**
     * Initializes a button with an image as its graphic and applies a specific style class.
     *
     * @param path the path to the image file to be used as the button's graphic
     * @return the initialized Button instance
     */
    private Button initButton(String path) {
        Button button = new Button();
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        button.getStyleClass().add("image-button");
        return button;
    }

    /**
     * Configures the behavior of the hold button, enabling a surrender action
     * when the button is held down for a specific duration. The button's visual
     * style is updated to reflect the hold action, and a timer is initiated to
     * track the hold duration.
     *
     * A `PauseTransition` is used to implement a 5-second hold timer. If the
     * button is held for the entire duration, a surrender action is triggered,
     * which notifies the controller to process a surrender request for the
     * current player. Additionally, a label is updated to indicate the surrender
     * status and the color associated with the surrendering player.
     *
     * If the hold is released before the timer completes, the surrender action
     * is not executed and the timer is stopped.
     */
    private void holdButton() {
        hold.getStyleClass().add("button-hold");

        holdTimer = new PauseTransition(Duration.seconds(5));
        holdTimer.setOnFinished(event -> {
            controller.askSurrender();
            playerSurrender.setText("You have surrender " + controller.askPlayer("current").getColor());
        });

        hold.setOnMousePressed(event -> holdTimer.play());
        hold.setOnMouseReleased(event -> holdTimer.stop());
    }
}

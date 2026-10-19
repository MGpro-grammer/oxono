package io.github.mgprogrammer.oxono.view;

import io.github.mgprogrammer.oxono.controller.Controller;
import io.github.mgprogrammer.oxono.event.EventType;
import io.github.mgprogrammer.oxono.model.Player;
import io.github.mgprogrammer.oxono.util.Observer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The ViewData class extends VBox and implements the Observer interface.
 * This class is responsible for displaying game-related data and updating this information
 * in response to changes notified by the controller. The displayed data includes the remaining tokens
 * for players, the current player, the number of remaining empty spaces, and the game result.
 */
public class ViewData extends VBox implements Observer {

    private Label tokenXBlackLabel;
    private Label tokenOBlackLabel;
    private Label tokenXPinkLabel;
    private Label tokenOPinkLabel;
    private Label infoCurrentPlayer;
    private Label infoEmptySpacesRemaining;
    private Label winOrDraw;
    private Controller controller;

    /**
     * Constructs a new ViewData instance with a default spacing of 10.
     * This class extends VBox and is used in the user interface to display
     * game-related information such as player statuses, remaining spaces,
     * and the state of the game (win, lose, or draw). It also functions
     * as an observer to update data based on game events.
     */
    public ViewData() {
        super(10);
        this.getStyleClass().add("view-data");
    }

    /**
     * Sets the controller for the view.
     *
     * @param controller the Controller instance to be associated with this view
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Configures and populates the display with data regarding player tokens, the current player,
     * empty spaces, and game status. This information is dynamically fetched through interaction
     * with the controller and updated on the user interface.
     *
     * The method performs the following:
     * - Retrieves the two players (black and pink) and their respective data, such as token counts.
     * - Fetches the current active player.
     * - Retrieves the number of remaining empty spaces in the game.
     * - Constructs labels for each piece of information, dynamically populated with current data.
     * - Updates the view with the created labels to display real-time game details.
     */
    public void data() {
        Player playerBlack = controller.askPlayer("black");
        Player playerPink = controller.askPlayer("pink");
        Label infoTokenXBlacklabel = new Label("Nombre de token X restant du joueur " + playerBlack.getColor() + ": ");
        tokenXBlackLabel = new Label(String.valueOf(playerBlack.getBagTokenX()));
        Label infoTokenOBlacklabel = new Label("Nombre de token O restant du joueur " + playerBlack.getColor() + ": ");
        tokenOBlackLabel = new Label(String.valueOf(playerBlack.getBagTokenO()));
        Label infoTokenXPinklabel = new Label("Nombre de token X restant du joueur " + playerPink.getColor() + ": ");
        tokenXPinkLabel = new Label(String.valueOf(playerPink.getBagTokenX()));
        Label infoTokenOPinklabel = new Label("Nombre de token X restant du joueur " + playerPink.getColor() + ": ");
        tokenOPinkLabel = new Label(String.valueOf(playerPink.getBagTokenO()));
        Label infoCurrentPlayerlabel = new Label("Joueur actuel : ");
        infoCurrentPlayer = new Label(String.valueOf(controller.askPlayer("current").getColor()));
        Label infoEmptySpaceslabel = new Label("Nombre de cases vides restantes :");
        infoEmptySpacesRemaining = new Label(String.valueOf(controller.askEmptySpaces()));
        winOrDraw = new Label();


        getChildren().addAll(infoTokenXBlacklabel, tokenXBlackLabel,
                infoTokenOBlacklabel, tokenOBlackLabel,
                infoTokenXPinklabel, tokenXPinkLabel,
                infoTokenOPinklabel, tokenOPinkLabel,
                infoCurrentPlayerlabel, infoCurrentPlayer,
                infoEmptySpaceslabel, infoEmptySpacesRemaining,
                winOrDraw);
    }

    /**
     * Updates the graphical user interface with the latest game state information.
     *
     * This method synchronizes UI components with the current game data retrieved
     * from the controller. It updates the labels associated with the token counts
     * of both players, the current player's color, the remaining empty spaces on
     * the board, and the game result if applicable (win or draw).
     *
     * Updates:
     * - Token counts (X and O) for the black and pink players.
     * - Current player's color.
     * - The total number of empty spaces available.
     * - Displays a win message if the game is won.
     * - Displays a draw message if the game is drawn.
     */
    public void setData() {
        tokenXBlackLabel.setText(String.valueOf(controller.askPlayer("black").getBagTokenX()));
        tokenOBlackLabel.setText(String.valueOf(controller.askPlayer("black").getBagTokenO()));
        tokenXPinkLabel.setText(String.valueOf(controller.askPlayer("pink").getBagTokenX()));
        tokenOPinkLabel.setText(String.valueOf(controller.askPlayer("pink").getBagTokenO()));
        infoCurrentPlayer.setText(String.valueOf(controller.askPlayer("current").getColor()));
        infoEmptySpacesRemaining.setText(String.valueOf(controller.askEmptySpaces()));
        if (controller.askWin()) {
            winOrDraw.setText("You won " + controller.askPlayer("current").getColor() + " !");
        } else if (controller.askDraw()) {
            winOrDraw.setText("Oh no ! It's a draw...");
        }
    }

    /**
     * Updates the state of the view based on the given event type.
     * This method is called on the JavaFX application thread to ensure
     * thread safety when updating the graphical user interface.
     *
     * @param eventType the event type that triggered the update. This parameter
     *                  provides context about the event and is used to notify the
     *                  observer about changes in the application's state.
     */
    @Override
    public void update(EventType eventType) {
        Platform.runLater(this::setData);
    }
}

package io.github.mgprogrammer.oxono.view;

import io.github.mgprogrammer.oxono.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The ViewMenu class extends VBox and provides the main user interface for
 * configuring game settings, including player level and board size. It
 * validates user input and initializes game components upon submission.
 */
public class ViewMenu extends VBox {

    private TextField levelField;
    private TextField sizeField;

    /**
     * Constructs an instance of ViewMenu with a default spacing of 10.
     *
     * The constructor initializes the layout with a predefined spacing value,
     * which organizes child elements within the VBox container. This spacing
     * value ensures consistent layout and visual alignment for the user interface
     * components added to the ViewMenu instance.
     */
    public ViewMenu() {
        super(10);
    }

    /**
     * Initializes the input fields for level and board size settings.
     * This method creates and configures two text fields with placeholder text to
     * guide the user in entering their desired game configuration values:
     *
     * 1. The levelField prompts the user to enter a player level.
     *    Currently, only the value "0" is supported.
     *
     * 2. The sizeField prompts the user to enter a board size, which must be an
     *    even number greater than or equal to 6.
     *
     * These fields are used later to capture and validate user input in the menu setup.
     */
    public void initLabel(){
        levelField = new TextField();
        levelField.setPromptText("Entrez un nombre (malheureusement il y a que 0 pour le moment)");

        sizeField = new TextField();
        sizeField.setPromptText("Entrez un nombre pair supérieur ou égal à 6");
    }

    /**
     * Constructs and displays the menu interface for configuring game settings,
     * including player level and board size. Validates user input and triggers the
     * game initialization on successful submission.
     *
     * @param primaryStage the primary stage of the JavaFX application to which the
     *                     user interface elements are added
     */
    public void menu(Stage primaryStage) {

        Label levelLabel = new Label("Niveau du joueur automatique :");
        Label sizeLabel = new Label("Taille du tableau :");
        initLabel();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            try {
                int level = Integer.parseInt(levelField.getText());
                int size = Integer.parseInt(sizeField.getText());

                if (level != 0) {
                    showError("Le niveau du joueur automatique doit être 0");
                } else if (size < 6 || size % 2 != 0) {
                    showError("La taille du tableau doit être un nombre pair supérieur ou égal à 6.");
                } else {
                    config(size, level, primaryStage);
                }
            } catch (NumberFormatException e) {
                showError("Ce que vous entrez n'est pas des nombres !");
            }
        });

        getChildren().addAll(levelLabel, levelField, sizeLabel, sizeField, submitButton);
    }

    /**
     * Configures the main game setup by initializing UI components, attaching them to
     * a controller, and setting the primary stage scene.
     *
     * @param size the size of the game board, must be an even number greater than or equal to 6
     * @param level the level of the automatic player, currently restricted to 0
     * @param primaryStage the primary stage for displaying the application window
     */
    private void config(int size, int level, Stage primaryStage) {
        BorderPane root = new BorderPane();
        ViewBoard grid = new ViewBoard();
        ViewData viewData = new ViewData();
        ViewConfig viewConfig = new ViewConfig();

        Controller controller = new Controller(size, level);
        controller.attachObservers(grid, viewData);

        grid.setController(controller);
        viewData.setController(controller);
        viewConfig.setController(controller);

        root.setCenter(grid);
        root.setLeft(viewConfig);
        root.setRight(viewData);

        viewData.data();
        grid.setGrid();

        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
    }

    /**
     * Displays an error message in a modal dialog and clears the level and size input fields.
     *
     * This method is invoked to notify the user of input validation errors or other issues
     * by showing an error message in a dialog box. After the alert is acknowledged, the
     * input fields for "level" and "size" are reset to their default empty states.
     *
     * @param message the error message to be displayed in the alert dialog
     */
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        levelField.clear();
        sizeField.clear();
    }
}

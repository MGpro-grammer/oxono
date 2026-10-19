package io.github.mgprogrammer.oxono;

import io.github.mgprogrammer.oxono.view.ViewMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point for the JavaFX application.
 * It sets up the primary stage and initializes the main user interface via the ViewMenu.
 * The application allows users to configure and launch a game with custom settings.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application by setting up the primary stage and its initial display.
     * This method configures the position, size, and title of the primary stage, initializes
     * the main menu interface using the ViewMenu class, and prepares the scene for display.
     *
     * @param primaryStage the primary stage for this application, provided by the JavaFX runtime
     */
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setX(300);
        primaryStage.setY(50);

        ViewMenu viewMenu = new ViewMenu();
        viewMenu.menu(primaryStage);

        Scene scene = new Scene(viewMenu, 300, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Oxono");
        primaryStage.show();
    }

    /**
     * The entry point of the JavaFX application. This method launches the JavaFX
     * application by calling the `launch` method, which initializes the application
     * lifecycle and sets up the primary stage.
     *
     * @param args the command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}

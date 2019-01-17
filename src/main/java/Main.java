import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class
 */
public class Main extends Application {

    /**
     * Main. Create a controller and view then launch the application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // launch the view
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Unicode Map");
        // we need a controller
        Controller controller = new Controller();

        // we need a view and need to tell the controller about the new view
        View view = new View(controller);
        controller.setView(view);

        // get the scene and display
        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }
}

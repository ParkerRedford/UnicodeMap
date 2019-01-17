import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.NoSuchElementException;

/**
 * Controller class. The controller class creates the required models and
 * connects the view with any postalCode that needs a listener. The controller class
 * receives action events from the view and processes the events.
 */
public class Controller {
    private View view;

    /**
     * Set the view
     *
     * @param view our gui view
     */
    public void setView(View view) {
        this.view = view;
    }
}

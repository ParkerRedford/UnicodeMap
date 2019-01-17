import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;

/**
 * View class. This class creates and manages the view.
 */

public class View {
    private final Scene scene;
    private List<UnicodeSet> sets = new ArrayList<>();
    private ComboBox<String> comboFonts = new ComboBox<>();
    private ObservableList<String> list = FXCollections.observableArrayList();
    private ComboBox<String> comboPresets = new ComboBox<>();

    /**
     * Constructor
     */
    public View(Controller controller) {
        /**
         * Get all fonts from system and add to combo box
         */
        Font.getFamilies().forEach(i -> comboFonts.getItems().add(i));
        StringBuilder fontString = new StringBuilder();
        /**
         * Slider for font size
         */
        Slider size = new Slider();
        size.setMin(8);
        size.setMax(64);
        size.setValue(32);
        size.setShowTickLabels(true);
        size.setShowTickMarks(true);
        size.setMajorTickUnit(8);
        size.setMinorTickCount(8);
        size.setBlockIncrement(16);

        /**
         * Unicode Presets
         */
        addSet("Miscellaneous Technical", 8960, 9210);
        addSet("Block Elements", 9600, 9631);
        addSet("Box Drawing", 9472, 9599);
        addSet("NKo", 1984, 2047);
        addSet("Alchemical Symbols", 128768, 128883);
        addSet("Greek Extended", 7936, 8190);
        addSet("General Punctuation", 8192, 8303);
        addSet("Superscripts and Subscripts", 8304, 8348);
        addSet("Currency Symbols", 8352, 8383);
        addSet("Spacing Modifier Letters", 688, 767);
        addSet("Combining Diacritical Marks", 768, 879);
        addSet("Playing Cards", 127136, 127221);
        addSet("Geometric Shapes", 9632, 9727);
        addSet("Geometric Shapes Extended", 128896, 128984);
        addSet("Domino Tiles", 127024, 127123);
        addSet("Ornamental Dingbats", 128592, 128639);
        addSet("Number Forms", 8528, 8587);
        addSet("Letterlike Symbols", 8448, 8527);
        addSet("Mathematical Operators", 8704, 8954);
        addSet("Mathematical Alphanumeric Symbols", 119808, 120071);
        addSet("Supplemental Mathematical Operators", 10752, 11002);
        addSet("Miscellaneous Mathematical Symbols-A", 10176, 10223);
        addSet("Miscellaneous Mathematical Symbols-B", 10624, 10751);
        addSet("Arabic Mathematical Alphabetic Symbols", 126464, 126705);
        addSet("Supplemental Arrows-A", 10224, 10239);
        addSet("Armenian", 1329, 1423);
        addSet("Basic Latin", 0, 159);
        addSet("Latin-1 Supplement", 128, 255);
        addSet("Latin Extended-A", 256, 383);
        addSet("Latin Extended-B", 384, 591);
        addSet("Cyrillic", 1024, 1274);
        addSet("Cyrillic Supplement", 1280, 1327);
        addSet("Tags", 917505, 917631);
        addSet("Emoticons", 128512, 128591);
        addSet("Osage", 66736, 66811);
        addSet("Osmanya", 66688, 66729);
        addSet("Lydian", 67872, 67903);
        addSet("IPA Extensions", 592, 687);
        addSet("Linear B Syllabary", 65536, 65629);
        addSet("Cypriot Syllabary", 67584, 67647);


        /**
         * Base
         */
        VBox base = new VBox();


        /**
         * Predefined unicode list for selection
         */
        // Sort list
        java.util.Collections.sort(list, Collator.getInstance());
        // Use Observable List for the combo box
        comboPresets.setItems(list);

        /**
         * Unicode Flow Pane
         */
        HBox unicodePane = new HBox();

        FlowPane flow = new FlowPane();
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(flow);

        HBox.setHgrow(scroll, Priority.ALWAYS);

        unicodePane.getChildren().addAll(scroll);

        /**
         * Grid - input and output
         */
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 0, 10, 10));

        //grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);
        /**
         * As integer
         */
        Label firstText = new Label("First unicode number:" + '\t' + '\t');
        Label lastText = new Label("Last unicode number:" + '\t' + '\t');
        TextField first = new TextField();
        TextField last = new TextField();

        grid.add(firstText, 0, 0);
        grid.add(lastText, 0, 1);
        grid.add(first, 1, 0);
        grid.add(last, 1, 1);
        grid.add(size, 3, 0);
        grid.add(comboFonts, 2, 1);
        grid.add(comboPresets, 2, 0);

        comboFonts.setPrefWidth(250);
        comboPresets.setPrefWidth(250);

        Button submit = new Button("Submit");
        /**
         * Events
         */

        /**
         * Submit button
         */
        submit.setOnAction(sub -> {
            flow.getChildren().clear();

            int index = comboFonts.getSelectionModel().getSelectedIndex();
            fontString.setLength(0);
            fontString.append(comboFonts.getItems().get(index));

            for (int i = Integer.valueOf(first.getText()); i <= Integer.valueOf(last.getText()); i++) {
                Button button = new Button(new String(new int[]{i}, 0, 1));
                button.setFont(Font.font(fontString.toString(), size.getValue()));
                button.setBackground(null);
                button.setStyle("-fx-border-color: black");
                button.setOnAction(e -> {
                    final Clipboard board = Clipboard.getSystemClipboard();
                    final ClipboardContent content = new ClipboardContent();
                    content.putString(button.getText());
                    board.setContent(content);
                });
                flow.getChildren().add(button);
            }
        });
        /**
         * Set text fields from unicode combo box
         */
        comboPresets.setOnAction(e -> {
            int index = comboPresets.getSelectionModel().getSelectedIndex();

            first.setText(Integer.toString(sets.get(index).getFirstNumber()));
            last.setText(Integer.toString(sets.get(index).getLastNumber()));
        });

        grid.add(submit, 1, 3);

        base.getChildren().setAll(grid, scroll);

        scene = new Scene(base, 1080, 720);

        flow.prefWidthProperty().bind(getScene().widthProperty());
    }

    private void addSet(String setName, int setFirst, int setLast) {
        UnicodeSet set = new UnicodeSet(setName, setFirst, setLast);
        sets.add(set);
        list.add(set.getName());
    }

    /**
     * Get the scene for this view
     *
     * @return scene for this view
     */
    public Scene getScene() {
        return scene;
    }

}

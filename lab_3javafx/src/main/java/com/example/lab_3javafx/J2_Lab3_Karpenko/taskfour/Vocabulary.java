package com.example.lab_3javafx.J2_Lab3_Karpenko.taskfour;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

/**
 * This class represents a simple vocabulary app, where users can add words and search for translations.
 */
public class Vocabulary extends Application {

    // Define constant strings
    private static final String SEARCH_ENG_LABEL = "Enter searched word in English:";
    private static final String SEARCH_UKR_LABEL = "Enter searched word in Ukrainian:";
    private static final String SEARCH_ENG_BUTTON_LABEL = "Search Ukrainian word";
    private static final String SEARCH_UKR_BUTTON_LABEL = "Search English word";
    private static final String EMPTY_FIELD_ERROR = "Please enter a word";
    private static final String NO_TRANSLATION_ERROR = "No translation found for: ";
    private static final String ALREADY_EXISTS = " already exists in the dictionary";
    private static final String ALPHABETICAL_CHARACTERS_ERROR = "The word can only contain alphabetical characters.";
    private static final VBox vboxForAdd = new VBox();

    private static final TextField engField = new TextField();
    private static final TextField ukrField = new TextField();
    // Cache frequently used value
    ObservableMap<String, String> attribMap = FXCollections.observableHashMap();

    // Use descriptive variable names
    private final TextField resultOfSearchTextField = new TextField();
    private final Label engSearchLabel = new Label(SEARCH_ENG_LABEL);
    private final TextField engSearchField = new TextField();
    private final Button engSearchButton = new Button(SEARCH_ENG_BUTTON_LABEL);
    private final VBox vboxForEngSearch = new VBox();
    private final Label ukrSearchLabel = new Label(SEARCH_UKR_LABEL);
    private final TextField ukrSearchField = new TextField();
    private final Button ukrSearchButton = new Button(SEARCH_UKR_BUTTON_LABEL);
    private final VBox vboxForUkrSearch = new VBox();

    private final HBox hBox = new HBox();

    /**
     * The main method, which launches the JavaFX application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Show the error message as an alert box.
     *
     * @param message the error message to show
     */
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Initializes the search box, including the search labels, search fields, and search buttons.
     */
    public void initSearchBox() {
        // Set the action for the search button using a lambda expression
        engSearchButton.setOnAction(e -> searchWord(engSearchField.getText(), true));
        ukrSearchButton.setOnAction(e -> searchWord(ukrSearchField.getText(), false));

        resultOfSearchTextField.setEditable(false);

        // Add the search components to the search VBox
        vboxForEngSearch.getChildren().addAll(engSearchLabel, engSearchField, engSearchButton);
        vboxForUkrSearch.getChildren().addAll(ukrSearchLabel, ukrSearchField, ukrSearchButton);

        // Add the search boxes to the HBox
        hBox.setSpacing(10);
        hBox.getChildren().addAll(vboxForEngSearch, vboxForUkrSearch);
    }

    /**
     * Searches for a word in the attribMap and displays the result in resultOfSearchTextField.
     *
     * @param searchedValue The word to be searched.
     * @param isEngSearch   A boolean value indicating if the search is in English or Ukrainian.
     */
    private void searchWord(String searchedValue, boolean isEngSearch) {
        // Check if the searchedValue is empty
        if (searchedValue.isEmpty()) {
            showError(EMPTY_FIELD_ERROR);
            return;
        }

        String result = null;
        if (isEngSearch) {
            // Search for the word in the attribMap using the English word as the key
            result = attribMap.get(searchedValue);
        } else {
            // Search for the word in the attribMap using the Ukrainian word as the value
            for (Map.Entry<String, String> entry : attribMap.entrySet()) {
                if (searchedValue.equals(entry.getValue())) {
                    result = entry.getKey();
                    break;
                }
            }
        }

        if (result == null) {
            showError(NO_TRANSLATION_ERROR + searchedValue);// Show an error message
        } else {
            System.out.println("Searched word: " + result);
            resultOfSearchTextField.setText(result);// Set the result in the resultOfSearchTextField
        }
    }

    /**
     * Initializes the Add Box by creating and adding UI elements to the vboxForAdd.
     */
    public void initAddBox() {
        Label engLabel = new Label("English:");
        Label ukrLabel = new Label("Ukrainian:");
        Button addButton = new Button("Add word");
        addButton.setOnAction(e -> {
            String engValue = engField.getText().trim();
            String ukrValue = ukrField.getText().trim();
            if (!engValue.equals("") && !ukrValue.equals("")) { // Check if fields are not empty
                if (attribMap.containsKey(engValue)) {
                    showError(ALREADY_EXISTS + engValue);
                } else if (attribMap.containsValue(ukrValue)) {
                    showError(ALREADY_EXISTS + ukrValue);
                }else if (!engValue.matches("^[a-zA-Z]+$")) { // Check that word contains only alphabetical characters
                    showError(ALPHABETICAL_CHARACTERS_ERROR + engValue);
                } else {
                    attribMap.put(engValue, ukrValue); // Add the word to the attribMap
                    engField.clear();
                    ukrField.clear();
                }
            } else {
                showError(EMPTY_FIELD_ERROR);
            }
        });
        // Add the UI elements to the vboxForAdd
        vboxForAdd.getChildren().addAll(engLabel, engField, ukrLabel, ukrField, addButton);
    }

    /**
     * The start method, which sets up the JavaFX UI and functionality.
     *
     * @param stage The JavaFX stage.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Vocabulary");

        FlowPane rootNode = new FlowPane(5, 5);
        rootNode.setAlignment(Pos.CENTER);

        Scene scene = new Scene(rootNode, 400, 400); // розміри вікна
        stage.setScene(scene);

        attribMap.addListener((MapChangeListener<String, String>) change -> System.out.println("Item was added\n" + attribMap));
        attribMap.put("First", "Перший");
        attribMap.put("Second", "Другий");
        attribMap.put("Third", "Третій");

        initAddBox();
        initSearchBox();

        rootNode.getChildren().addAll(vboxForAdd, hBox, resultOfSearchTextField);
        stage.setScene(scene);
        stage.show();
    }
}
package com.example.lab_3javafx.J2_Lab3_Karpenko.taskone;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * The MetroController class is a JavaFX controller for the MetroFX application.
 * It handles user input and events, updates the UI, and manages the MetroStream data model.
 */
public class MetroController implements Initializable {
    public MetroStream metroStream = new MetroStream();
    public ObservableList<Hour> observableList;

    /**
     * Displays an information message dialog with the given message.
     *
     * @param message the message to display
     */
    public static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Діалогове вікно повідомлення про помилку
     *
     * @param message - текст повідомлення
     */
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Creates a file chooser dialog with filters for XML files and all files.
     *
     * @param title the title to display for the file chooser dialog
     * @return a FileChooser object with filters for XML files and all files
     */
    public static FileChooser getFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        // Починаємо шукати з поточної теки:
        fileChooser.setInitialDirectory(new File("."));
        // Встановлюємо фільтри для пошуку файлів:
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML-файли (*.xml)", "*.xml"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Усі файли (*.*)", "*.*"));
        // Вказуємо заголовок вікна:
        fileChooser.setTitle(title);
        return fileChooser;
    }

    @FXML
    public TextField textFieldMetroStation;
    @FXML
    public TextField textFieldOpeningYear;
    @FXML
    public TextField textFieldHourByComment;
    @FXML
    public TextArea textAreaResults;
    @FXML
    public TextArea textAreaAvrg;
    @FXML
    public TableView<Hour> tableViewHours;
    @FXML
    public TableColumn<Hour, Integer> tableColumnPassengerCount;
    @FXML
    public TableColumn<Hour, String> tableColumnComment;

    /**
     * Initializes the table view with an empty placeholder.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewHours.setPlaceholder(new Label(""));
    }

    /**
     * Clears all fields and table view when a "New" button is pressed.
     *
     * @param actionEvent the event that triggered this method
     */
    public void doNew(ActionEvent actionEvent) {
        metroStream = new MetroStream();
        observableList = null;
        textFieldMetroStation.setText("");
        textFieldOpeningYear.setText("");
        textFieldHourByComment.setText("");
        textAreaResults.setText("");
        textAreaAvrg.setText("");
        tableViewHours.setItems(null);
        tableViewHours.setPlaceholder(new Label(""));
    }

    /**
     * Opens an XML file and displays its contents in the appropriate fields and table view.
     *
     * @param event the event that triggered this method
     */
    @FXML
    public void doOpen(ActionEvent event) {
        FileChooser fileChooser = getFileChooser("Відкрити XML-файл");
        File file;
        if ((file = fileChooser.showOpenDialog(null)) != null) {
            try {
                metroStream = FileUtils.deserializeFromXML(file.getCanonicalPath());
                // Заповнюємо текстові поля прочитаними даними:
                assert metroStream != null;
                textFieldMetroStation.setText(metroStream.getName());
                textFieldOpeningYear.setText(metroStream.getOpeningYear() + "");
                textAreaResults.setText("");
                textAreaAvrg.setText("");
                // Очищаємо та оновлюємо таблицю:
                tableViewHours.setItems(null);
                updateTable();
            } catch (IOException e) {
                showError("Файл не знайдено");
            } catch (Exception e) {
                showError("Неправильний формат файлу");
            }
        }
    }

    /**
     * Handles the "Save" button action. Shows a dialog to choose a file to save the metro stream data to,
     * updates the source data in the model, and serializes it to XML.
     *
     * @param actionEvent the event that triggered the action.
     */
    public void doSave(ActionEvent actionEvent) {
        FileChooser fileChooser = getFileChooser("Зберегти XML-файл");
        File file;
        System.out.println(metroStream);
        System.out.println("!!!!!!!!!!!!!!!!!!!!1" + metroStream.getName());
        if ((file = fileChooser.showSaveDialog(null)) != null) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!2" + metroStream.getName());
            try {
                updateSourceData(); // оновлюємо дані в моделі
                System.out.println("!!!!!!!!!!!!!!!!!!!!" + metroStream.getName());
                FileUtils.serializeToXML(metroStream, file.getCanonicalPath());
                showMessage("Результати успішно збережені");
            } catch (Exception e) {
                showError("Помилка запису в файл");
            }
        }
    }

    /**
     * Updates the source data in the model with the values entered by the user in the UI.
     */
    private void updateSourceData() {
        metroStream = new MetroStream();

        metroStream.setOpeningYear(Integer.parseInt(textFieldOpeningYear.getText()));
        metroStream.setName(textFieldMetroStation.getText());
        for (Hour h : observableList) {
            metroStream.addHour(h);
        }
    }

    /**
     * Handles the "Exit" button action. Exits the JavaFX application.
     *
     * @param actionEvent the event that triggered the action.
     */
    public void doExit(ActionEvent actionEvent) {
        Platform.exit(); // коректне завершення застосунку JavaFX
    }

    /**
     * Handles the "Add" button action. Shows a dialog to add a new hour to the metro stream data, and updates
     * the table with the new data.
     *
     * @param actionEvent the event that triggered the action.
     */
    public void doAdd(ActionEvent actionEvent) {
        Dialog<Hour> dialog = new Dialog<>();
        dialog.setTitle("Add Hour");
        dialog.setContentText("Add Hour to Metro Stream");

        Label label1 = new Label("Comment: ");
        Label label2 = new Label("Passenger count: ");
        TextField commentTextField = new TextField();
        TextField passengerCountTextField = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(commentTextField, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(passengerCountTextField, 2, 2);
        dialog.getDialogPane().setContent(grid);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(saveButtonType);
//        dialog.showAndWait();

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String comment = commentTextField.getText();
                String passengerCountText = passengerCountTextField.getText();

                // Check if fields are empty
                if (comment.trim().isEmpty() || passengerCountText.trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Fields cannot be empty!");
                    alert.showAndWait();
                    return null;
                }
                // Check if passenger count is a number
                try {
                    int passengerCount = Integer.parseInt(passengerCountText);
                    return new Hour(passengerCount, comment);
                } catch (NumberFormatException e) {
                    showError("Passenger Count must be a number!");
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Passenger Count must be a number!");
//                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });
        Optional<Hour> result = dialog.showAndWait();

        if (result.isPresent()) {
            metroStream.addHour(result.get().getComment(), result.get().getPassengersNumber());
            updateTable(); // створюємо нові дані
        }
    }

    /**
     * Removes the last row from the observable list.
     * If there are no rows, does nothing.
     * If all rows have been removed, sets the observable list to null.
     *
     * @param actionEvent the event that triggered the action
     */
    public void doRemove(ActionEvent actionEvent) {
        // Не можемо видалити рядок, якщо немає даних:
        if (observableList == null) {
            return;
        }
        // Якщо є рядки, видаляємо останній:
        if (observableList.size() > 0) {
            observableList.remove(observableList.size() - 1);
        }
        // Якщо немає рядків, вказуємо, що дані відсутні:
        if (observableList.size() == 0) {
            observableList = null;
        }
//        tableViewHours.setItems(observableList);
        updateSourceData();
    }

    /**
     * Sorts the observable list by passenger number in descending order.
     *
     * @param actionEvent the event that triggered the action
     */
    @FXML
    public void sortByPassengerNumberDesc(ActionEvent actionEvent) {
        updateSourceData();
        metroStream.sortByPassengerNumberDesc();
        updateTable();
    }

    /**
     * Sorts the observable list by comment length in descending order.
     *
     * @param actionEvent the event that triggered the action
     */
    @FXML
    public void sortByCommentLengthDesc(ActionEvent actionEvent) {
        updateSourceData();
        metroStream.sortByCommentLengthDesc();
        updateTable();
    }

    /**
     * Sorts the observable list by comment alphabetically.
     *
     * @param actionEvent the event that triggered the action
     */
    @FXML
    public void sortByCommentAlphabetically(ActionEvent actionEvent) {
        updateSourceData();
        metroStream.sortByCommentAlphabetically();
        updateTable();
    }

    /**
     * Displays information about the program in an alert.
     *
     * @param actionEvent the event that triggered the action
     */
    public void doAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Про програму...");
        alert.setHeaderText("Data about metro station");
        alert.setContentText("Version 1.0");
        alert.showAndWait();
    }

    /**
     * Updates the name of the MetroStream object when the user changes the text in textFieldMetroStation.
     *
     * @param actionEvent the event that triggered the action
     */
    public void nameChanged(KeyEvent actionEvent) {
        // Коли користувач змінив дані в textFieldMetroStation,
        // автоматично оновлюємо назву:
        metroStream.setName(textFieldMetroStation.getText());
        System.out.println(metroStream.getName());
    }

    /**
     * Updates the opening year of the MetroStream object when the user changes the text in textFieldOpeningYear.
     *
     * @param actionEvent the event that triggered the action
     */
    public void openingYearChanged(KeyEvent actionEvent) {
        metroStream.setOpeningYear(Integer.parseInt(textFieldOpeningYear.getText()));
        System.out.println(metroStream.getOpeningYear());
    }

    /**
     * Finds hours in the MetroStream object that contain the substring entered in textFieldHourByComment.
     * Displays the results in the textAreaResults.
     *
     * @param actionEvent the event that triggered the action
     */
    public void doFindHoursByComment(ActionEvent actionEvent) {
        updateSourceData();
        textAreaResults.setText("");

        for (int i = 0; i < metroStream.hoursCount(); i++) {
            Hour c = (Hour) metroStream.getHour(i);
            if (c.containsSubstring(textFieldHourByComment.getText())) {
                showResults(c);
            }
        }
    }
    public void updatetextAreaAvrg() {
        textAreaAvrg.setText("");

//        for (int i = 0; i < metroStream.hoursCount(); i++) {
//            Hour c = (Hour) metroStream.getHour(i);
            textAreaAvrg.setText(String.valueOf(Arrays.stream(metroStream.getHours()).mapToInt(Hour::getPassengersNumber).average().orElse(0)));
//        }
    }

    /**
     * Displays the results of the search in the text area by appending the string representation
     * of the hour object and a new line character to the text area.
     *
     * @param h the hour object to be displayed
     */
    private void showResults(Hour h) {
        textAreaResults.appendText(h.toString());
        textAreaResults.appendText("\n");
    }

    /**
     * Updates the comment of the hour object in the table view by getting the new value from
     * the cell edit event and setting it to the hour object. Then, it updates the observable list
     * with the hours data from the metro stream and sets the new observable list to the table view.
     *
     * @param t the cell edit event that triggered the update
     */
    public void updateComment(TableColumn.CellEditEvent<Hour, String> t) {
        Hour h = t.getTableView().getItems().get(t.getTablePosition().getRow());
        h.setComment(t.getNewValue());

        System.out.println("Comment updated");

        // Обновляем список элементов observableList:
        observableList.setAll(metroStream.getHours());

        System.out.println("observableList size: " + observableList.size());
        System.out.println("h.getComment(): " + h.getComment());
        updatetextAreaAvrg();
    }

    /**
     * Updates the passenger count of the hour object in the table view by getting the new value from
     * the cell edit event and setting it to the hour object.
     *
     * @param t the cell edit event that triggered the update
     */
    public void updatePassenger(TableColumn.CellEditEvent<Hour, Integer> t) {
        Hour h = t.getTableView().getItems().get(t.getTablePosition().getRow());
        h.setPassengersNumber(t.getNewValue());
    }

    /**
     * Updates the table view by populating the observable list with the hours data
     * from the metro stream, and setting the table columns' properties and editing
     * mechanisms based on the type of cells.
     */
    public void updateTable() {
        // Заповнюємо observableList:
        List<Hour> list = new ArrayList<>();
//        observableList.clear();
        observableList = FXCollections.observableList(list);
        for (int i = 0; i < metroStream.hoursCount(); i++) {
            list.add(metroStream.getHour(i));
        }
        tableViewHours.setItems(observableList);

        // Вказуємо для колонок зв'язану з ними властивість і механізм редагування
        // залежно від типу комірок:
        tableColumnPassengerCount.setCellValueFactory(new PropertyValueFactory<>("passengersNumber"));
        tableColumnPassengerCount.setCellFactory(
                TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColumnPassengerCount.setOnEditCommit(this::updatePassenger);

        tableColumnComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        tableColumnComment.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnComment.setOnEditCommit(this::updateComment);
    }
}
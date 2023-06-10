package shchepinda.labfourth.task3;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Клас, що відповідає DaysController
 *
 * Дисципліна «Поглиблений курс програмування Java
 * Лабораторна робота №4, Варіант №23, завдання 3
 *
 * @author Щепін Данило, KH-221a
 */
public class DaysController implements Initializable {
    private final List<ExhibitionWithStreams> exhibitionList = new ArrayList<ExhibitionWithStreams>();
    private ObservableList<DayForDB> observableListDays;

    /**
     * An arbitrary message dialog box
     *
     * @param message - message text
     */
    public static void showMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * The error message dialog box
     *
     * @param message - message text
     */
    public static void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Create a file selection dialog box
     *
     * @param title - the text of the window title
     */
    public static FileChooser getFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON-файли (*.json)", "*.json"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Усі файли (*.*)", "*.*"));
        fileChooser.setTitle(title);
        return fileChooser;
    }

    // Fields associated with visual elements:
    @FXML
    private TextField artistField;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> exhibitionChoiceBox;
    @FXML
    private TextField textFieldText;
    @FXML
    private TextArea textAreaResults;
    @FXML
    private TableView<DayForDB> tableViewDays;
    @FXML
    private TableColumn<DayForDB, Integer> tableColumnVisitors;
    @FXML
    private TableColumn<DayForDB, String> tableColumnComments;
    /**
     * Method for initializing visual components described in an FXML document
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DbUtils.createConnection();
        tableViewDays.setPlaceholder(new Label(""));
    }

    //Methods are event handlers:
    @FXML
    private void doNew(ActionEvent event) {
        DbUtils.createDatabase();
        observableListDays = null;
        artistField.setText("");
        nameField.setText("");
        textFieldText.setText("");
        textAreaResults.setText("");
        tableViewDays.setItems(null);
        tableViewDays.setPlaceholder(new Label(""));
    }

    @FXML
    private void doOpen(ActionEvent event) {
        FileChooser fileChooser = getFileChooser("Відкрити JSON-файл");
        File file;
        if ((file = fileChooser.showOpenDialog(null)) != null) {
            try {
                DbUtils.importFromJSON(file.getCanonicalPath());
                textAreaResults.setText("");
                tableViewDays.setItems(null);
                updateChoiceBox();
                exhibitionChoiceBox.setValue(exhibitionChoiceBox.getItems().get(0));
                updateTable(DbUtils.getExhibitionByName(exhibitionChoiceBox.getSelectionModel().getSelectedItem()));
            }
            catch (IOException e) {
                showError("Файл не знайдено");
            }
            catch (Exception e) {
                showError("Неправильний формат файлу");
            }
        }
    }

    @FXML
    private void doSave(ActionEvent event) {
        FileChooser fileChooser = getFileChooser("Зберегти JSON-файл");
        File file;
        if ((file = fileChooser.showSaveDialog(null)) != null) {
            try {
                DbUtils.exportToJSON(file.getCanonicalPath());
                showMessage("Результати успішно збережені");
            }
            catch (Exception e) {
                showError("Помилка запису в файл");
            }
        }
    }

    @FXML
    private void doExit(ActionEvent event) {
        DbUtils.closeConnection();
        Platform.exit();
    }

    @FXML
    private void doAddExhibition(ActionEvent event) {
        Stage window = new Stage();
        window.setTitle("Додати виставку");
        window.setMinWidth(400);
        window.setMinHeight(300);
        window.setResizable(false);

        Label nameLabel = new Label("Назва виставки");
        TextField nameField = new TextField();
        Label artistLabel = new Label("Прізвище художника");
        TextField artistField = new TextField();

        Button btnAdd = new Button("Додати");
        Button btnClear = new Button("Очистити");

        System.out.println(nameField.getText() + artistField.getText());

        btnAdd.setOnAction(e -> {
            if (!nameField.getText().isEmpty() || !artistField.getText().isEmpty()) {
                DbUtils.addExhibition(new ExhibitionForDB(nameField.getText(), artistField.getText()));
                updateChoiceBox();
                exhibitionChoiceBox.getSelectionModel().select(nameField.getText());
            }
            else {
                showError("Заповніть \"Назву\" та \"Прізвище художника\"");
            }
        });
        btnClear.setOnAction(e -> {
            nameField.setText("");
            artistField.setText("");
        });

        HBox visitors = new HBox(10);
        visitors.getChildren().addAll(nameLabel, nameField);
        HBox comment = new HBox(10);
        comment.getChildren().addAll(artistLabel, artistField);
        HBox btns = new HBox(10);
        btns.getChildren().addAll(btnAdd, btnClear);

        VBox root = new VBox(10);
        root.getChildren().addAll(visitors, comment, btns);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(0, 0, 0, 50));

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
    }

    private void updateChoiceBox() {
        List<String> exhibitionListName = new ArrayList<String>();
        ObservableList<String> observableListExhibitionName = FXCollections.observableList(exhibitionListName);

        Exhibitions exhibitions = DbUtils.getExhibitionsFromDB();
        List<ExhibitionForDB> dbList = exhibitions.getList();
        for (ExhibitionForDB e : dbList) {
            exhibitionListName.add(e.getName());
        }
        observableListExhibitionName = FXCollections.observableList(exhibitionListName);
        exhibitionChoiceBox.setItems(observableListExhibitionName);
    }

    @FXML
    private void selectExhibition(ActionEvent event) {
        String exhibitionName = exhibitionChoiceBox.getSelectionModel().getSelectedItem();
        ExhibitionForDB exhibition = DbUtils.getExhibitionByName(exhibitionName);
        textAreaResults.setText("");
        updateAll(exhibition);
    }

    @FXML
    private void doAddDay(ActionEvent event) {
        if (exhibitionChoiceBox.getValue() == null) {
            showError("Виберіть виставку!");
            return;
        }

        String exhibitionName = exhibitionChoiceBox.getSelectionModel().getSelectedItem();
        ExhibitionForDB exhibition = DbUtils.getExhibitionByName(exhibitionName);

        Stage window = new Stage();
        window.setTitle("Додати новий день");
        window.setMinWidth(400);
        window.setMinHeight(300);
        window.setResizable(false);

        Label visitorsLabel = new Label("Кількість відвідувачів");
        TextField visitorsField = new TextField();
        Label commentLabel = new Label("Коментар");
        TextField commentField = new TextField();

        Button btnAdd = new Button("Додати");
        Button btnClear = new Button("Очистити");

        btnAdd.setOnAction(e -> {
            if (!visitorsField.getText().isEmpty()) {
                if (Integer.parseInt(visitorsField.getText()) <= 0) {
                    visitorsField.setText("");
                    showError("Неправильна кількість відвідувачів");
                }
                else {
                    String comment;
                    if (commentField.getText() == "") comment = "Без коментаря.";
                    else comment = commentField.getText();
                    DbUtils.addDay(exhibition.getName(), new DayForDB(Integer.parseInt(visitorsField.getText()), comment));

                    updateTable(Objects.requireNonNull(DbUtils.getExhibitionByName(exhibitionName)));
                }
            }
            else {
                showError("Заповніть \"Кількість відвідувачів\" та \"Коментар\"");
            }
        });
        btnClear.setOnAction(e -> {
            visitorsField.setText("");

            commentField.setText("");
        });

        HBox visitors = new HBox(10);
        visitors.getChildren().addAll(visitorsLabel, visitorsField);
        HBox comment = new HBox(10);
        comment.getChildren().addAll(commentLabel, commentField);
        HBox btns = new HBox(10);
        btns.getChildren().addAll(btnAdd, btnClear);

        VBox root = new VBox(10);
        root.getChildren().addAll(visitors, comment, btns);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(0, 0, 0, 50));

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
    }

    private boolean confirmDialog(String message) {
        Stage window = new Stage();
        window.setTitle("Підтвердження");
        window.setMinWidth(400);
        window.setMinHeight(300);
        window.setResizable(false);

        Label label = new Label(message);
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: red");
        Button btnYes = new Button("ТАК");
        Button btnNo = new Button("НІ");

        AtomicBoolean confirm = new AtomicBoolean(false);
        btnYes.setOnAction(e -> {
            confirm.set(true);
            window.close();
        });
        btnNo.setOnAction(e -> {
            confirm.set(false);
            window.close();
        });

        HBox btns = new HBox(10);
        btns.getChildren().addAll(btnYes, btnNo);
        btns.setAlignment(Pos.CENTER);

        VBox root = new VBox(10);
        root.getChildren().addAll(label, btns);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
        return confirm.get();
    }

    @FXML
    private void doRemoveExhibition(ActionEvent event) {
        ExhibitionForDB selected = DbUtils.getExhibitionByName(exhibitionChoiceBox.getSelectionModel().getSelectedItem());
        System.out.println(selected);

        if (selected != null) {
            if (confirmDialog("Ви дійсно хочете видалити виставку " + selected.getName() + "?")) {
                DbUtils.removeExhibition(selected.getId());

                updateChoiceBox();
                exhibitionChoiceBox.setValue(exhibitionChoiceBox.getItems().get(0));
                updateAll(DbUtils.getExhibitionByName(exhibitionChoiceBox.getSelectionModel().getSelectedItem()));
            }
        }
        else {
            showError("Виберіть виставку!");
        }
    }

    /**
     * Method for removing the last row from a table
     * @param event - object that represents the event
     */
    @FXML
    private void doRemoveDay(ActionEvent event) {
        DayForDB selected = tableViewDays.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (confirmDialog("Ви дійсно хочете видалити день " + selected.getVisitorsNum())) {
                DbUtils.removeDay(exhibitionChoiceBox.getSelectionModel().getSelectedItem(),selected.getVisitorsNum());
                ExhibitionWithStreams exhibition = new ExhibitionWithStreams();
                updateTable(DbUtils.getExhibitionByName(exhibitionChoiceBox.getSelectionModel().getSelectedItem()));
            }
        } else {
            showError("Виберіть день з таблиці!");
        }
    }

    @FXML
    private void doSortByVisitors(ActionEvent event) {
        if (exhibitionChoiceBox.getValue() == null) {
            showError("Виберіть виставку зі списка");
            return;
        }
        ExhibitionForDB exhibition = DbUtils.getExhibitionByName(exhibitionChoiceBox.getSelectionModel().getSelectedItem());
        exhibition = DbUtils.sortByVisitors(exhibitionChoiceBox.getSelectionModel().getSelectedItem());
        updateTable(exhibition);
    }

    @FXML
    private void doSortByComments(ActionEvent event) {
        if (exhibitionChoiceBox.getValue() == null) {
            showError("Виберіть виставку зі списка");
            return;
        }
        ExhibitionForDB exhibition = DbUtils.getExhibitionByName(exhibitionChoiceBox.getSelectionModel().getSelectedItem());
        long id = exhibition.getId();
        exhibition = DbUtils.sortByComments(exhibitionChoiceBox.getSelectionModel().getSelectedItem());
        updateTable(exhibition);
    }

    @FXML
    private void doAbout(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Про програму...");
        alert.setHeaderText("Дані про художні виставки.");
        alert.setContentText("Версія 1.0\nАвтор: Щепін Данило");
        alert.showAndWait();
    }

    @FXML
    private void artistChanged(ActionEvent event) {
        try {
            ExhibitionForDB exhibition = DbUtils.getExhibitionByName(exhibitionChoiceBox.getSelectionModel().getSelectedItem());
            System.out.println(exhibition);
            if (exhibitionChoiceBox.getValue() == null) {
                artistField.setText("");
                showError("Виставка не вибрана!");
                return;
            }
            String newName = artistField.getText();
            DbUtils.updateArtist(exhibition, newName);
            updateChoiceBox();
            exhibitionChoiceBox.getSelectionModel().select(exhibition.getName());
        }
        catch (java.sql.SQLException e) {
            showError("Такої виставки не існує!");
        }
    }

    @FXML
    private void nameChanged(ActionEvent event) {
        try {
            ExhibitionForDB exhibition = DbUtils.getExhibitionByName(exhibitionChoiceBox.getSelectionModel().getSelectedItem());
            if (exhibitionChoiceBox.getValue() == null) {
                nameField.setText("");
                showError("Виставка не вибрана!");
                return;
            }
            String newName = nameField.getText();
            DbUtils.updateExhibitionName(exhibition, newName);
            updateChoiceBox();
            exhibitionChoiceBox.getSelectionModel().select(newName);
        }
        catch (java.sql.SQLException e) {
            showError("Такої виставки не існує!");
        }
    }

    @FXML
    private void doSearchByWord(ActionEvent event) {
        textAreaResults.setText("");

        String word = textFieldText.getText();
        if (word.trim().equals("")) {
            showError("Введіть слово, щоб почати шукати");
            return;
        }

        if (exhibitionChoiceBox.getValue() == null) {
            showError("Виберіть виставку");
            return;
        }
        ExhibitionForDB exhibition = DbUtils.getExhibitionByName(exhibitionChoiceBox.getSelectionModel().getSelectedItem());
        List<DayForDB> days = DbUtils.searchByWord(word);
        long id = exhibition.getId();

        for (DayForDB d : days) {
            if (DbUtils.getExhibitionIdInDay(d.getId()) == id) {
                showResults(d);
            }
        }
    }

    private void showResults(DayForDB day) {
        textAreaResults.appendText("Кількість відвідувачів: " + day.getVisitorsNum() + "\n");
        textAreaResults.appendText("Коментар: " + day.getComment() + "\n");
        textAreaResults.appendText("\n");
    }

    private void updateAll(ExhibitionForDB exhibition) {
        if (exhibition == null) {
            nameField.setText("");
            artistField.setText("");
            if (tableViewDays.getItems() != null) tableViewDays.getItems().clear();
            return;
        }
        nameField.setText(exhibition.getName());
        artistField.setText(exhibition.getArtSurname());

        updateTable(exhibition);
    }

    private void updateVisitors(CellEditEvent<DayForDB, Integer> t) {
        DayForDB c = t.getTableView().getItems().get(t.getTablePosition().getRow());
        c.setVisitorsNum(t.getNewValue());
    }

    private void updateComments(CellEditEvent<DayForDB, String> t) {
        DayForDB c = t.getTableView().getItems().get(t.getTablePosition().getRow());
        c.setComment(t.getNewValue());
    }

    private void updateTable(ExhibitionForDB ex) {
        List<DayForDB> list = new ArrayList<DayForDB>();
        observableListDays = FXCollections.observableList(list);

        for (int i = 0; i < ex.getDaysCount(); i++) {
            list.add((DayForDB) ex.getDay(i));
        }

        tableViewDays.setItems(observableListDays);

        tableColumnVisitors.setCellValueFactory(new PropertyValueFactory<>("visitorsNum"));
        tableColumnVisitors.setCellFactory(
                TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColumnVisitors.setOnEditCommit(this::updateVisitors);

        tableColumnComments.setCellValueFactory(new PropertyValueFactory<>("comment"));
        tableColumnComments.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnComments.setOnEditCommit(this::updateComments);
    }

    @FXML
    private void doFindAllVisitors(ActionEvent event) {
        try {
            if (exhibitionChoiceBox.getValue() == null) {
                nameField.setText("");
                showError("Виставка не вибрана!");
                return;
            }
            textAreaResults.setText("");
            textAreaResults.appendText("Загальна кількість відвідувачів виставки: " +
                    DbUtils.getAllVisitors(exhibitionChoiceBox.getSelectionModel().getSelectedItem()) + "\n");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void doFindLeastVisitedDay(ActionEvent event) {
        try {
            if (exhibitionChoiceBox.getValue() == null) {
                nameField.setText("");
                showError("Виставка не вибрана!");
                return;
            }
            textAreaResults.setText("");
            textAreaResults.appendText("День з найменшою кількістю відвідувачів: " +
                    DbUtils.getLeastVisitedDay(exhibitionChoiceBox.getSelectionModel().getSelectedItem()) + "\n");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

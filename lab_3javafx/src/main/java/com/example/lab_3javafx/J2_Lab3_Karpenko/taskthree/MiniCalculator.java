package com.example.lab_3javafx.J2_Lab3_Karpenko.taskthree;


import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The MiniCalculator program implements a simple calculator that allows
 * <p>
 * the user to perform basic arithmetic operations on two input values.
 * <p>
 * The program includes checks to ensure that the input data is valid.
 */
public class MiniCalculator extends Application {
    private Button button;
    private TextField field1, field2, field3;
    private ToggleGroup radioGroup;

    /**
     * The start method sets up the user interface for the MiniCalculator application.
     * <p>
     * It creates a window with a set of input fields and buttons for the user to interact with.
     *
     * @param stage the primary stage for this application, onto which the application scene can be set.
     * @throws Exception if an error occurs while setting up the scene.
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Mini Calculator");

        FlowPane rootNode = new FlowPane(5, 5);
        rootNode.setAlignment(Pos.CENTER);

        Scene scene = new Scene(rootNode, 200, 200); // розміри вікна
        stage.setScene(scene);

        RadioButton radioButtonPlus = new RadioButton("+");
        RadioButton radioButtonMinus = new RadioButton("-");
        RadioButton radioButtonDivide = new RadioButton("/");
        RadioButton radioButtonMultiply = new RadioButton("*");

        radioGroup = new ToggleGroup();

        radioGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            button.setDisable(false);
        });

        radioButtonPlus.setToggleGroup(radioGroup);
        radioButtonMinus.setToggleGroup(radioGroup);
        radioButtonDivide.setToggleGroup(radioGroup);
        radioButtonMultiply.setToggleGroup(radioGroup);

        field1 = new TextField();
        field2 = new TextField();
        field3 = new TextField();
        field3.setEditable(false);

        button = new Button("Calculate");   // визначаємо напис на кнопці
        button.setDisable(true);
        button.setOnAction(this::buttonClick);// визначаємо функцію, яка обробляє подію

        HBox hbox = new HBox(radioButtonPlus, radioButtonMinus, radioButtonDivide, radioButtonMultiply);
        hbox.setSpacing(10);
        VBox vbox = new VBox(field1, hbox, field2, button, field3);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);
        rootNode.getChildren().addAll(vbox);
        stage.show();
    }

    /**
     * The buttonClick method performs the requested arithmetic operation on the input values.
     * It first checks that the input values are valid and throws an exception if they are not.
     *
     * @param event the event that triggered this method.
     */
    private void buttonClick(Event event) {
        try {
            int num1 = Integer.parseInt(field1.getText());
            int num2 = Integer.parseInt(field2.getText());
            RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
            String operation = selectedRadioButton.getText();

            double result = switch (operation) {
                case "+" -> num1 + num2;
                case "-" -> num1 - num2;
                case "*" -> num1 * num2;
                case "/" -> {
                    if (num2 == 0) { // проверка на деление на ноль
                        throw new IllegalArgumentException("Нельзя делить на ноль.");
                    }
                    yield (double) num1 / num2;
                }
                default -> throw new IllegalStateException("Unexpected value: " + operation);
            };
            field3.setText(String.valueOf(result));
        } catch (NumberFormatException e1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input! Please enter two numbers!");
            alert.showAndWait();
        } catch (IllegalArgumentException e2) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot divide by zero!");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
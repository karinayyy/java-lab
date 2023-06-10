package shchepinda.labfourth.task3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * Клас, що відповідає DaysFX
 *
 * Дисципліна «Поглиблений курс програмування Java
 * Лабораторна робота №3, Варіант №23, завдання 1
 *
 * @author Щепін Данило, KH-221a
 */
public class DaysFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("DaysForm.fxml"));
            Scene scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            stage.setTitle("Виставка");
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

module com.karinayyy.lab4 {
    exports com.karinayyy.lab4.taskone;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires xstream;
    requires junit;
    requires mysql.connector.j;
    requires org.json;
    opens com.karinayyy.lab4.taskone to xstream, jettison;
}
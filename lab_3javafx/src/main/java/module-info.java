module com.example.lab_3javafx {
    exports com.example.lab_3javafx.J2_Lab3_Karpenko.taskthree;
    exports com.example.lab_3javafx.J2_Lab3_Karpenko.taskfour;
    exports com.example.lab_3javafx.J2_Lab3_Karpenko.taskone;
    requires javafx.controls;
    requires javafx.fxml;
    requires xstream;
    requires org.apache.logging.log4j;
    requires junit;
    opens com.example.lab_3javafx.J2_Lab3_Karpenko.taskone to xstream;
}
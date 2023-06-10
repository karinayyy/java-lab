package J2_Lab2_Karpenko.example.taskfifthandsixth;

import J2_Lab2_Karpenko.example.taskfifthandsixth.taskfifth.StudentStreamApi;
import J2_Lab2_Karpenko.example.taskfifthandsixth.taskfifth.StudentXml;
import J2_Lab2_Karpenko.example.taskfifthandsixth.taskfifth.StudentJson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static J2_Lab2_Karpenko.example.taskfifthandsixth.tasksixth.StudentOrgJson.studentOrgJsonDeserialization;
import static J2_Lab2_Karpenko.example.taskfifthandsixth.tasksixth.StudentOrgJson.studentOrgJsonSerialization;

public class StudentDemo {
    public static void main(String[] args) throws IOException {
        List<Student> students = Arrays.asList(
                new Student("Azian Mafia", "KN-221A"),
                new Student("Izabella Gaborets", "KN-221A"),
                new Student("Dukarschka Dunaj", "KN-221A")
        );
        AcademicGroup academicGroup = new AcademicGroup();
        for (Student student : students) {
            academicGroup.addStudent(student);
        }

        //streamApi serialization and deserialization to file AcademicGroup.txt
        System.out.println("StreamAPI Serialization and Deserialization");
        StudentStreamApi.streamApiWrite(academicGroup);
        StudentStreamApi.streamApiRead();

        System.out.println();

        //xml serialization and deserialization to file AcademicGroup.xml
        System.out.println("XML Serialization and Deserialization");
        StudentXml.studentXmlSerialization(academicGroup);
        StudentXml.studentXmlDeserialization();

        System.out.println();

        //json serialization and deserialization to file AcademicGroup.json
        System.out.println("JSON Serialization and Deserialization");
        StudentJson.studentJsonSerialization(academicGroup);
        StudentJson.studentJsonDeserialization();

        System.out.println();

        //json.org serialization and deserialization to file AcademicGroupOrg.json
        System.out.println("json.org Serialization and Deserialization");
        studentOrgJsonSerialization(academicGroup);
        studentOrgJsonDeserialization();
    }
}

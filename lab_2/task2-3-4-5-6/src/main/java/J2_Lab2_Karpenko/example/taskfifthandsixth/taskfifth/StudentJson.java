package J2_Lab2_Karpenko.example.taskfifthandsixth.taskfifth;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import J2_Lab2_Karpenko.example.taskfifthandsixth.Student;
import J2_Lab2_Karpenko.example.taskfifthandsixth.AcademicGroup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The StudentJson class provides methods for serializing and deserializing
 * objects of the AcademicGroup class to/from JSON format using XStream library.
 */
public class StudentJson {
    /**
     * Serializes an AcademicGroup object to JSON format and writes it to a file.
     *
     * @param academicGroup the AcademicGroup object to be serialized
     */
    public static void studentJsonSerialization(AcademicGroup academicGroup) {
        XStream xStream = new XStream(new JsonHierarchicalStreamDriver());
        xStream.alias("academicGroup", AcademicGroup.class);
        xStream.alias("student", Student.class);

        String xml = xStream.toXML(academicGroup);
        try (FileWriter fw = new FileWriter("AcademicGroup.json");
             PrintWriter out = new PrintWriter(fw)) {
            out.println(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes an AcademicGroup object from a JSON file and prints its contents.
     */
    public static void studentJsonDeserialization() {
        XStream xStream = new XStream(new JettisonMappedXmlDriver());
        xStream.addPermission(AnyTypePermission.ANY);

        xStream.alias("academicGroup", AcademicGroup.class);
        xStream.addImplicitCollection(AcademicGroup.class, "students");
        xStream.alias("students", Student.class);

        AcademicGroup newAcademicGroup = (AcademicGroup) xStream.fromXML(new File("AcademicGroup.json"));

        for (Student student : newAcademicGroup.getStudents()) {
            System.out.println(student.toString());
        }
    }
}

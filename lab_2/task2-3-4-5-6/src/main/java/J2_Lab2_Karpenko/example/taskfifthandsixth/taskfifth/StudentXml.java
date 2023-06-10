package J2_Lab2_Karpenko.example.taskfifthandsixth.taskfifth;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import J2_Lab2_Karpenko.example.taskfifthandsixth.AcademicGroup;
import J2_Lab2_Karpenko.example.taskfifthandsixth.Student;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * The StudentXml class provides methods for serialization and deserialization of AcademicGroup to/from XML format.
 */
public class StudentXml {
    /**
     * Serializes an AcademicGroup object to XML format and saves it to a file.
     *
     * @param academicGroup the AcademicGroup object to be serialized
     */
    public static void studentXmlSerialization(AcademicGroup academicGroup) {
        XStream xStream = new XStream();

        xStream.alias("academicGroup", AcademicGroup.class);
        xStream.alias("student", Student.class);

        String xml = xStream.toXML(academicGroup);
        try (FileWriter fw = new FileWriter("AcademicGroup.xml");
             PrintWriter out = new PrintWriter(fw)) {
            out.println(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes an AcademicGroup object from an XML file and prints the student information to the console.
     */
    public static void studentXmlDeserialization() {
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);

        xStream.alias("academicGroup", AcademicGroup.class);
        xStream.alias("student", Student.class);

        AcademicGroup newAcademicGroup = (AcademicGroup) xStream.fromXML(new File("AcademicGroup.xml"));
        for (Student student : newAcademicGroup.getStudents()) {
            System.out.println(student.toString());
        }
    }
}

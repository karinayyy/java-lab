package J2_Lab2_Karpenko.example.taskfifthandsixth.taskfifth;

import J2_Lab2_Karpenko.example.taskfifthandsixth.AcademicGroup;
import J2_Lab2_Karpenko.example.taskfifthandsixth.Student;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


/**
 * This class provides static methods for writing and reading student information using Stream API.
 */
public class StudentStreamApi {
    /**
     * Writes information about students from an AcademicGroup object to a text file using Stream API.
     *
     * @param academicGroup the AcademicGroup object containing the list of students to be written to the file
     * @throws FileNotFoundException if the file cannot be created or opened
     */
    public static void streamApiWrite(AcademicGroup academicGroup) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter("AcademicGroup.txt")) {
            academicGroup.getStudents().stream()
                    .map(Student::toString)
                    .forEach(writer::println);
        }
    }

    /**
     * Reads information about students from a text file and prints it to the console using Stream API.
     * The file must contain one student record per line, with the student's name and group separated by a space.
     */
    public static void streamApiRead() {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("AcademicGroup.txt"))) {
            Stream<String> strings = bufferedReader.lines();
            System.out.println("///StreamAPI");
            strings.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package J2_Lab2_Karpenko.example.taskfifthandsixth;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an academic group that consists of students.
 */
public class AcademicGroup {
    /**
     * List of students in the academic group.
     */
    private List<Student> students;

    /**
     * Constructs an academic group with an empty list of students.
     */

    public AcademicGroup() {
        this.students = new ArrayList<>();
    }

    /**
     * Adds a student to the academic group.
     *
     * @param student the student to be added to the academic group.
     */
    public void addStudent(Student student) {
        this.students.add(student);
    }

    /**
     * Returns the list of students in the academic group.
     *
     * @return the list of students in the academic group.
     */
    public List<Student> getStudents() {
        return students;
    }
}

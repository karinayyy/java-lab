package J2_Lab2_Karpenko.example.taskfifthandsixth;

/**
 * Represents a student in the academic group.
 */
public class Student {
    /**
     * The name of the student.
     */
    private String name;

    /**
     * The name of the group the student belongs to.
     */
    private String group;

    /**
     * Constructs a student with a given name and group.
     *
     * @param name  the name of the student.
     * @param group the name of the group the student belongs to.
     */
    public Student(String name, String group) {
        this.name = name;
        this.group = group;
    }

    /**
     * Returns the name of the student.
     *
     * @return the name of the student.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the name of the group the student belongs to.
     *
     * @return the name of the group the student belongs to.
     */
    public String getGroup() {
        return group;
    }

    /**
     * Returns a string representation of the student.
     *
     * @return a string representation of the student.
     */
    @Override
    public String toString() {
        return name + " " + group;
    }
}

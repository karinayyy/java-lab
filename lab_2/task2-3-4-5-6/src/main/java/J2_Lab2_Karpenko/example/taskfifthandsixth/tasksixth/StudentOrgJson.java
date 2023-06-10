package J2_Lab2_Karpenko.example.taskfifthandsixth.tasksixth;

import J2_Lab2_Karpenko.example.taskfifthandsixth.AcademicGroup;
import J2_Lab2_Karpenko.example.taskfifthandsixth.Student;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A utility class that provides methods for serializing and deserializing an {@link AcademicGroup} object
 * to and from Org.JSON format.
 */
public class StudentOrgJson {
    /**
     * Serializes an {@link AcademicGroup} object to Org.JSON format and writes the output to a file named "AcademicGroupOrg.json".
     *
     * @param academicGroup the academic group to serialize
     * @throws IOException if an I/O error occurs while writing the file
     */
    public static void studentOrgJsonSerialization(AcademicGroup academicGroup) throws IOException {
        JSONObject academicGroupJson = new JSONObject();
        JSONArray studentsJson = new JSONArray();
        for (Student student : academicGroup.getStudents()) {
            JSONObject studentJson = new JSONObject();
            studentJson.put("name", student.getName());
            studentJson.put("group", student.getGroup());
            studentsJson.put(studentJson);
        }
        academicGroupJson.put("students", studentsJson);

        try (FileWriter file = new FileWriter("AcademicGroupOrg.json")) {
            JSONObject academicGroupWrapper = new JSONObject();
            academicGroupWrapper.put("academicGroup", academicGroupJson);
            file.write(academicGroupWrapper.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Deserializes an {@link AcademicGroup} object from a file named "AcademicGroupOrg.json" in Org.JSON format and returns it.
     * prints the deserialized academic group
     */
    public static void studentOrgJsonDeserialization() {

        try {
            String json = new String(Files.readAllBytes(Paths.get("AcademicGroupOrg.json")));
            JSONObject jsonObject = new JSONObject(json);
            AcademicGroup academicGroup = new AcademicGroup();

            JSONArray jsonArray = jsonObject.getJSONObject("academicGroup").getJSONArray("students");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject studentJson = jsonArray.getJSONObject(i);
                Student student = new Student(studentJson.getString("name"), studentJson.getString("group"));
                academicGroup.addStudent(student);
            }

            for (Student student : academicGroup.getStudents()) {
                System.out.println(student.getName() + " " + student.getGroup());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

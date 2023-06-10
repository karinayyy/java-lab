package J2_Lab2_Karpenko.example.tasksecond;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static J2_Lab2_Karpenko.example.tasksecond.FileNio.fileNio;

/**
 * A demo class for the FileNio utility class.
 */
public class FileNioDemo {

    /**
     * Main method for testing the FileNio utility class.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // определяем объект для каталога
        Path dir = Paths.get("testdir");
        // если объект представляет каталог и существует
        if (Files.exists(dir) || Files.isDirectory(dir)) {
            fileNio(dir);
        } else {
            System.out.println("Хибне ім\'я теки або вона не існуе!");
        }
    }
}

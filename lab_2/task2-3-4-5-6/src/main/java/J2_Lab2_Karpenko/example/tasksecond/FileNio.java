package J2_Lab2_Karpenko.example.tasksecond;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * A utility class for working with files using the NIO library.
 */
public class FileNio {

    /**
     * Recursively prints the names of all regular files within a given directory and its subdirectories.
     *
     * @param dir the directory to search
     * @throws RuntimeException if an I/O error occurs
     */
    public static void fileNio(Path dir) {
        try (Stream<Path> s = Files.walk(dir)) {
            s.filter(Files::isRegularFile)
                    .forEach(k -> System.out.println(k.getFileName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

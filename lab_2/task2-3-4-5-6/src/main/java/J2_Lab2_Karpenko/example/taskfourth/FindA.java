package J2_Lab2_Karpenko.example.taskfourth;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * The FindA class provides functionality to read from a file and write to a file.
 * It reads from a file named "FindAin.txt" and writes to a file named "FindAout.txt".
 * It filters the lines that contain the character 'a', and writes them sorted by length
 * in ascending order.
 */
public class FindA {
    /**
     * Reads from a file named "FindAin.txt", filters the lines that contain the character 'a',
     * sorts them by length in ascending order, and writes them to a file named "FindAout.txt".
     *
     * @throws IOException if there is an error reading from or writing to the file.
     */
    public static void findA() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("FindAin.txt"))) {
            Stream<String> stream = reader.lines().sorted(Comparator.comparing(String::length)).
                    filter(s -> s.contains("a"));
            Files.write(Paths.get("FindAout.txt"), stream.toList());
        }
    }
}

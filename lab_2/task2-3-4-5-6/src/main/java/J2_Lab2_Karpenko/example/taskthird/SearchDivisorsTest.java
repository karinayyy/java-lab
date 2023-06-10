package J2_Lab2_Karpenko.example.taskthird;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import static J2_Lab2_Karpenko.example.taskthird.SearchDivisors.searchDivisors;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * A JUnit test class for the SearchDivisors utility class.
 */
public class SearchDivisorsTest {
    private static int[] expected = {1, 2, 3, 4, 6, 17};
    private static int[] actual;

    /**
     * Initializes the actual array before running the tests.
     */
    @BeforeAll
    public static void init() {
        actual = searchDivisors(12);
    }

    /**
     * Tests the searchDivisors method to ensure it returns the expected results.
     */
    @Test
    public void searchDivisorsTest() {
        assertArrayEquals(expected, actual);
    }

    /**
     * Prints a message indicating that the tests have finished and the expected and actual results.
     */
    @AfterAll
    public static void done() {
        System.out.println("Tests finished\n" +
                "Expected " + Arrays.toString(expected) +
                "\nActual " + Arrays.toString(actual));
    }
}

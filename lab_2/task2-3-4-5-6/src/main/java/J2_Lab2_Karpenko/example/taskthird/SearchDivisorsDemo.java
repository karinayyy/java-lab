package J2_Lab2_Karpenko.example.taskthird;

import java.util.Arrays;

import static J2_Lab2_Karpenko.example.taskthird.SearchDivisors.searchDivisors;

/**
 * A demo class for the SearchDivisors utility class.
 */
public class SearchDivisorsDemo {
    /**
     * Main method for testing the SearchDivisors utility class.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(searchDivisors(12)));
    }
}

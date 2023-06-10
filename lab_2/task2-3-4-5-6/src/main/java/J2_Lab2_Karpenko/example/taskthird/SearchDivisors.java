package J2_Lab2_Karpenko.example.taskthird;

import java.util.stream.IntStream;

/**
 * The SearchDivisors class provides a utility method to find all the divisors of a given number.
 */
public class SearchDivisors {
    /**
     * Returns an array of all the divisors of the given number.
     *
     * @param num the number to find divisors for
     * @return an array of integers representing the divisors of the given number
     */
    public static int[] searchDivisors(int num) {
        return IntStream.rangeClosed(1, num)
                .filter(i -> num % i == 0)
                .toArray();
    }
}

package shchepinda.labfourth.task3;

import java.util.Arrays;
/**
 * Class that represents the second of the entities of an individual task using Streams <br>
 * @author Shchepin Daniel
 */
public class DayWithStreams extends Day {
    /**
     * The constructor initializes the object with set values
     */
    public DayWithStreams() {}

    /**
     * The constructor initializes the object with the specified values
     * @param visitorsNum number of visitors
     * @param comment comment
     */
    public DayWithStreams(int visitorsNum, String comment) {
        setVisitorsNum(visitorsNum);
        setComment(comment);
    }

    /**
     * Checks if the word is contained in the comment text
     * @param word specific word
     * @return {@code true} if the word is contained in the comment text
     *      ,{@code false} otherwise
     */
    @Override
    public boolean containsWord(String word) {
        return Arrays.stream(getComment().split("\s")).anyMatch(s -> s.equalsIgnoreCase(word));
    }

    public boolean containsSubstring(String word) {
        return getComment().toLowerCase().contains(word.toLowerCase());
    }
}

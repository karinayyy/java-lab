package shchepinda.labfourth.task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
/**
 * A class to represent the exhibition with Streams.
 * To process a sequence of words, the Stream API tools are used
 */
public class ExhibitionWithStreams extends ExhibitionWithList {
    /**
     * Set array of Days
     * @param days - array which you want to set
     */
    @Override
    public void setAllDays(Day[] days) {
        if (Arrays.stream(days).anyMatch(c -> c instanceof DayWithStreams)) {
            super.setAllDays(days);
        }
        else {
            new RuntimeException();
        }
    }

    /**
     * Overrode function toString()
     * @return Converts objects parameters into string format sentences and returns it.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getName() + " - " + getArtSurname());
        for (int i = 0; i < getDaysCount(); i++) {
            result.append("\n\t").append(getDay(i));
        }
        return result + "";
    }

    /**
     * Adds a link to the new Day to the end of the sequence
     * @param day object of the Day class that you want to add to the list
     * @return {@code true}, if the link was added successfully
     * {@code false} otherwise
     */
    @Override
    public boolean addDay(Day day) {
        if (day instanceof DayWithStreams) {
            return super.addDay(day);
        }
        return false;
    }

    /**
     * Creates a new Day and adds a link to it at the end of the sequence.
     * @param visitorsNum number of visitors
     * @param comment comment to exhibition
     * @return {@code true}, if the link was added successfully
     * {@code false} otherwise
     */
    @Override
    public boolean addDay(int visitorsNum, String comment) {
        return super.addDay(new DayWithStreams(visitorsNum, comment));
    }

    /**
     * Sorts the rewrite sequence by comment alphabet
     */
    @Override
    public void sortByComments() {
        setList(getList().stream().sorted(Comparator.comparing(Day::getComment)).toList());
    }

    /**
     * Find the lowest number of visitors using stream
     * @return lowest number of visitors
     */
    @Override
    public int findLowestVisitors() {
        return getList().stream().min(Comparator.comparing(Day::getVisitorsNum)).get().getVisitorsNum();
    }

    public Day findLowestVisitorsDay() {
        return getList().stream().min(Comparator.comparing(Day::getVisitorsNum)).get().getDay();
    }
    /**
     * Creates and returns an array of Days with the specified word in the comments
     * @param word word to be searched
     * @return an array of Days with the specified word in the comments
     */
    public Day[] findWords(String word) {
        return getList().stream().filter(c -> c.containsWord(word)).toArray(Day[]::new);
    }
    /**
     * Creates and returns a list of strings that contains the following data
     * about the Exhibition as a whole and about all Days
     *
     * @return a list of strings with data about the exhibition
     */
    public List<String> toListOfStrings() {
        ArrayList<String> list = new ArrayList<>();
        list.add(getName() + " " + getArtSurname());
        Arrays.stream(getDays()).forEach(d -> list.add(d.getVisitorsNum() + " " + d.getComment()));
        return list;
    }
    /**
     * Reads exhibition data from a list of strings and enters it into the appropriate fields
     *
     * @param list list of strings with exhibition data
     */
    public void fromListOfStrings(List<String> list) {
        String[] words = list.get(0).split("\s");
        setName(words[0]);
        setArtSurname(words[1]);
        list.remove(0);
        list.stream().forEach(s -> {    String[] line = s.split("\s");
                        addDay(Integer.parseInt(line[0]), s.substring(s.indexOf(line[1])));
        }   );
    }
    /**
     * Creates and returns an object of type ExhibitionWithStreams for testing
     * @return an object of type ExhibitionWithStreams
     */
    public static ExhibitionWithStreams createExhibitionWithStreams() {
        ExhibitionWithStreams exhibition = new ExhibitionWithStreams();

        exhibition.setName("Metamorphosis");
        exhibition.setArtSurname("Alice Johnson");
        exhibition.addDay(320, "People liked most of the pictures");
        exhibition.addDay(221, "Some visitors were outraged by several paintings");
        exhibition.addDay(122, "The least crowded day");
        exhibition.addDay(415, "This thought-provoking painting depicts the transformative process of a caterpillar into a butterfly");
        exhibition.addDay(258, "Typical day");

        return exhibition;
    }
}

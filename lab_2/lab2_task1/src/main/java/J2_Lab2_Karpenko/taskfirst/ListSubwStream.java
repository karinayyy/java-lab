package J2_Lab2_Karpenko.taskfirst;

import java.text.Collator;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class extends the ListSubwStation class to represent a subway station with streaming data.
 */
public class ListSubwStream extends ListSubwStation {
    /**
     * Constructs a new ListSubwStream object with the given name and opening year.
     *
     * @param name        the name of the subway station.
     * @param openingYear the year the subway station was opened.
     */
    public ListSubwStream(String name, int openingYear) {
        super(name, openingYear);
    }

    /**
     * Returns a string representation of this subway station, including its name, opening year,
     * and operating hours.
     *
     * @return a string representation of this subway station.
     */
    @Override
    public String toString() {
        return String.format("%s='%s', %s=%d, %s:%s",
                "Subway_station_name", getName(),
                "year_of_open", getOpeningYear(),
                "Hours", toStringHours(getHours()));
    }

    /**
     * Returns an array of hours that contain the given text in their comment.
     *
     * @param text the text to search for in the comments.
     * @return an array of hours that contain the given text in their comment.
     */
    public Hour[] findHoursByComment(String text) {  //da
        return Arrays.stream(getHours()).filter((hour -> {
            for (String word : hour.getComment().split(WORDS)) {
                if (word.startsWith(text) || word.endsWith(text)) {
                    return true;
                }
            }
            return false;
        })).toArray(Hour[]::new);
    }

    /**
     * Returns the average number of passengers per hour for this subway station.
     *
     * @return the average number of passengers per hour for this subway station.
     */
    public double getAvgPassengers() { //da
        return Arrays.stream(getHours()).mapToInt(Hour::getPassengersNumber).average().orElse(0);
    }

    /**
     * Returns the shortest period between two consecutive hours for this subway station.
     *
     * @return the shortest period between two consecutive hours for this subway station.
     */
    public Period getShortestPeriod() { //da
        List<HourWithDates> hours = Arrays.stream(getHours())
                .map((hour -> (HourWithDates) hour))
                .collect(Collectors.toList());
        if (hours.size() < 2) {
            return Period.ZERO;
        }
        hours.sort((Comparator.comparing(HourWithDates::getTimeOfEditing).reversed()));
        return Period.between(LocalDate.from(hours.get(1).getTimeOfEditing()),
                LocalDate.from(hours.get(0).getTimeOfEditing()));
    }

    /**
     * Sorts the operating hours of this subway station by comment in alphabetical order.
     */
    @Override
    public void sortByCommentAlphabetically() { //da
        Hour[] hours = getHours();
        Collator collator = Collator.getInstance(Locale.US);

        hours = Arrays.stream(hours)
                .sorted((o1, o2) -> collator.compare(o1.getComment(), o2.getComment()))
                .toArray(Hour[]::new);

        setHours(hours);
    }

    /**
     * Creates and returns a new ListSubwStream object with test data.
     *
     * @return a new ListSubwStream object with test data.
     */
    static ListSubwStream createSubwayStation() {
        ListSubwStream station = new ListSubwStream("Kholodnogirska", 1975);

        HourWithDates[] hours = new HourWithDates[]{
                new HourWithDates(1267, "bcoment i am very big", Add.getCurrentDateTimeMinusDays(30)),
                new HourWithDates(213, "acomment i am", Add.getCurrentDateTimeMinusDays(51)),
                new HourWithDates(800, "wcomment i am very", Add.getCurrentDateTimeMinusDays(0)),
                new HourWithDates(1500, "zcomment i", Add.getCurrentDateTimeMinusDays(69)),
                new HourWithDates(1000, "comment", Add.getCurrentDateTimeMinusDays(4))
        };
        station.setHours(hours);
        return station;
    }

    public void test() {
        System.out.println(this);
        System.out.println(this.getTotalPassengersNumber());
        System.out.println(this.getHourWithMaxWordNumberInComment());
        System.out.println(this.getHourWithLeastPassengersNumber());
        System.out.println(this.getAvgPassengers());
        System.out.printf("The shortest period ==> %d %s", this.getShortestPeriod().getDays(), "days");
        String comment = "very";
        System.out.printf("\nFind hours by comment heisst -> %s\n%s ", comment, this.toStringHours(this.findHoursByComment(comment)));

        this.sortByCommentLengthDesc();
        System.out.printf("SortedByCommentLengthDesc:\n%s", this);

        this.sortByPassengerNumberDesc();
        System.out.printf("sortedByPassengerNumberDesc:\n%s", this);

        this.sortByCommentAlphabetically();
        System.out.printf("sortedByCommentAlphabetically:\n%s", this);

    }

    public static void main(String[] args) {
        ListSubwStream stat =
                ListSubwStream.createSubwayStation();
        stat.test();
    }
}
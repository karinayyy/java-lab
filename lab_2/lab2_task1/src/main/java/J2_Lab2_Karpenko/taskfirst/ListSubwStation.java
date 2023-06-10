package J2_Lab2_Karpenko.taskfirst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The ListSubwStation class extends the SubwayStation class and adds a List of Hour objects. It also overrides the
 * <p>
 * sortByPassengerNumberDesc() and sortByCommentLengthDesc() methods from the superclass to sort the hours List by
 * <p>
 * passenger number and comment length, respectively. Additionally, it overrides the getHours() and setHours() methods
 * <p>
 * to access and modify the hours List.
 */
public class ListSubwStation extends SubwayStation {
    private List<Hour> hours = new ArrayList<>();

    public ListSubwStation(String name, int openingYear) {
        setName(name);
        setOpeningYear(openingYear);
    }

    /**
     * Sorts the hours List by passenger number in descending order.
     */
    @Override
    public void sortByPassengerNumberDesc() { //da
        hours = hours.stream()
                .sorted(Comparator.comparingInt(hour -> -hour.getPassengersNumber()))
                .collect(Collectors.toList());
    }

    /**
     * Sorts the hours List by comment length in descending order.
     */
    @Override
    public void sortByCommentLengthDesc() { //da
        hours = hours.stream()
                .sorted(Comparator.comparingInt(hour -> -hour.getComment().length()))
                .collect(Collectors.toList());
    }

    /**
     * Returns an array of Hour objects representing the hours of operation of this subway station.
     *
     * @return an array of Hour objects representing the hours of operation of this subway station.
     */
    @Override
    public Hour[] getHours() {
        return hours.toArray(new Hour[]{});
    }

    /**
     * Sets the hours of operation of this subway station to the specified array of Hour objects.
     *
     * @param hours the new hours of operation of this subway station.
     */
    @Override
    public void setHours(Hour[] hours) {
        this.hours = Arrays.asList(hours);
    }
}

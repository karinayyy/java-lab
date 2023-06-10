package com.example.lab_3javafx.J2_Lab3_Karpenko.taskone;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * The MetroStationList class extends the SubwayStation class and adds a List of Hour objects. It also overrides the
 * <p>
 * sortByPassengerNumberDesc() and sortByCommentLengthDesc() methods from the superclass to sort the hours List by
 * <p>
 * passenger number and comment length, respectively. Additionally, it overrides the getHours() and setHours() methods
 * <p>
 * to access and modify the hours List.
 */
@XStreamAlias("MetroStationList")
public class MetroStationList extends MetroStation {
    /**
     * A list of Hour objects representing the hours of the metro station.
     */
    @XStreamImplicit(itemFieldName = "hours")
    private List<Hour> hours = new ArrayList<>();

    /**
     * Constructs a MetroStationList object with the specified name and opening year.
     *
     * @param name        The name of the metro station.
     * @param openingYear The opening year of the metro station.
     */
    public MetroStationList(String name, int openingYear) {
        setName(name);
        setOpeningYear(openingYear);
    }

    /**
     * Returns the number of hours in the metro station.
     *
     * @return The number of hours in the metro station.
     */
    public int hoursCount() {
        return hours.size();
    }

    /**
     * Sorts the hours in the list by passenger number in descending order.
     */
    @Override
    public void sortByPassengerNumberDesc() {
        hours.sort(((o1, o2) -> Integer.compare(o2.getPassengersNumber(), o1.getPassengersNumber())));
    }

    /**
     * Sorts the hours in the list by comment length in descending order.
     */
    @Override
    public void sortByCommentLengthDesc() {
        hours.sort((o1, o2) -> Integer.compare(o2.getComment().length(), o1.getComment().length()));
    }

    /**
     * Returns an array of Hour objects representing the hours.
     *
     * @return an array of Hour objects representing the hours.
     */
    @Override
    public Hour[] getHours() {
        return hours.toArray(new Hour[]{});
    }

    /**
     * Returns the Hour object at the specified index.
     *
     * @param i the index of the Hour object to return.
     * @return the Hour object at the specified index.
     */
    @Override
    public Hour getHour(int i) {
        return hours.get(i);
    }

    /**
     * Sets the collection of hours  to the specified array of Hour objects.
     *
     * @param hours the array of Hour objects to set as the collection of hours .
     */
    @Override
    public void setHours(Hour[] hours) {
        this.hours = Arrays.asList(hours);
    }

    /**
     * Adds the specified Hour object to the collection of hours  if it is not already present.
     *
     * @param hour the Hour object to add to the collection of hours.
     * @return true if the Hour object was added to the collection of hours, false otherwise.
     */
    public boolean addHour(Hour hour) {
        if (hours.contains(hour)) {
            return false;
        }
        return hours.add(hour);
    }

    /**
     * Adds a new Hour object to the collection of hours with the specified comment and pass number.
     *
     * @param comment the comment to set on the new Hour object.
     * @param passNum the pass number to set on the new Hour object.
     */
    public void addHour(String comment, int passNum) {
        addHour(new Hour(passNum, comment));
    }
}

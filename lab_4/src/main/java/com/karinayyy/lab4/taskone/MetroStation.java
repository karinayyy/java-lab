package com.karinayyy.lab4.taskone;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * The abstract MetroStation class represents a metro station with a name, opening year, and hours of operation.
 * It provides methods to retrieve and modify the hours of operation, as well as several utility methods to calculate
 * statistics on the station's passenger traffic.
 */
@XStreamAlias("MetroStation")
public abstract class MetroStation {
    protected static final String WORDS = "(?U)([^\\w]+)";

    @XStreamAlias("name")
    private String name;

    @XStreamAlias("openingYear")
    private int openingYear;

    protected MetroStation() {
    }


    /**
     * Returns the name of the metro station.
     *
     * @return the name of the metro station
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the metro station.
     *
     * @param name the new name for the metro station
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the opening year of the metro station.
     *
     * @return the opening year of the metro station
     */
    public int getOpeningYear() {
        return openingYear;
    }

    /**
     * Sets the opening year of the metro station.
     *
     * @param openingYear the opening year of the metro station
     */
    public void setOpeningYear(int openingYear) {
        this.openingYear = openingYear;
    }

    /**
     * Returns an array of Hour objects representing the hours of operation for this metro station.
     *
     * @return an array of Hour objects representing the hours of operation for this metro station
     */
    public abstract Hour[] getHours();

    /**
     * Returns the Hour object at the specified index in the hours of operation array.
     *
     * @param i the index of the Hour object to retrieve
     * @return the Hour object at the specified index in the hours of operation array
     */
    public abstract Hour getHour(int i);

    /**
     * Sets the hours of operation for this metro station.
     *
     * @param hours an array of Hour objects representing the new hours of operation for this metro station
     */
    public abstract void setHours(Hour[] hours);

    /**
     * Returns the total number of passengers that passed through this metro station during all hours of operation.
     *
     * @return the total number of passengers that passed through this metro station during all hours of operation
     */
    public int getTotalPassengersNumber() {
        int total = 0;
        for (Hour hour : getHours()) {
            total += hour.getPassengersNumber();
        }
        return total;
    }

    /**
     * Returns the Hour object with the fewest number of passengers during all hours of operation.
     *
     * @return the Hour object with the fewest number of passengers during all hours of operation, or null if no hours are available
     */
    public Hour getHourWithLeastPassengersNumber() {
        Hour[] hours = getHours();
        if (hours.length < 1) return null;
        Hour min = hours[0];

        for (int i = 1; i < hours.length; i++) {
            if (min.getPassengersNumber() > hours[i].getPassengersNumber()) {
                min = hours[i];
            }
        }
        return min;
    }

    /**
     * Returns the Hour object with the longest comment (in terms of number of words) during all hours of operation.
     *
     * @return the Hour object with the longest comment during all hours of operation, or null if no hours are available
     */
    public Hour getHourWithMaxWordNumberInComment() {
        Hour[] hours = getHours();
        if (hours.length < 1) return null;
        Hour max = hours[0];

        for (int i = 1; i < hours.length; i++) {
            if (wordCount(max.getComment()) < wordCount(hours[i].getComment())) {
                max = hours[i];
            }
        }
        return max;
    }

    /**
     * Returns the word count of the given text.
     *
     * @param text the text to count the words of
     * @return the word count of the given text
     */

    private static int wordCount(String text) {
        return text.split(WORDS).length;
    }

    /**
     * Sorts the hours of operation for this metro station alphabetically by comment.
     */
    public void sortByCommentAlphabetically() {
        Arrays.sort(getHours(), Comparator.comparing(Hour::getComment));
    }

    /**
     * Sorts the hours of operation for this metro station in descending order by number of passengers.
     */
    public void sortByPassengerNumberDesc() {
        Hour[] hours = getHours();
        for (int i = 0; i < hours.length - 1; i++) {
            for (int j = 0; j < hours.length - i - 1; j++) {
                if (hours[j].getPassengersNumber() < hours[j + 1].getPassengersNumber()) {
                    Hour temp = hours[j];
                    hours[j] = hours[j + 1];
                    hours[j + 1] = temp;
                }
            }
        }
        setHours(hours);
    }

    /**
     * Sorts the hours of operation for this metro station in descending order by length of comment (in terms of number of characters).
     */
    public void sortByCommentLengthDesc() {
        Hour[] hours = getHours();

        for (int i = 1; i < hours.length; i++) {
            Hour key = hours[i];
            int j = i - 1;

            while (j >= 0 && key.getComment().length() > hours[j].getComment().length()) {
                hours[j + 1] = hours[j];
                --j;
            }

            hours[j + 1] = key;
        }
        setHours(hours);
    }

    /**
     * Returns a string representation of this MetroStation object, including its name, opening year, and hours of operation.
     *
     * @return a string representation of this MetroStation object
     */
    @Override
    public String toString() {
        return "Metro station name='" + name + '\'' +
                "| Metro station opening year=" + openingYear + "| hours: \n" + toStringHours(getHours());
    }

    /**
     * Checks if the given object is equal to this metro station.
     *
     * @param o the object to compare with
     * @return true if the given object is equal to this metro station, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetroStation that = (MetroStation) o;

        if (openingYear != that.openingYear) return false;
        return Objects.equals(name, that.name);
    }

    /**
     * Returns the hash code of this metro station.
     *
     * @return the hash code of this metro station
     */
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + openingYear;
        return result;
    }

    /**
     * Returns a string representation of the hours of operation for the metro station.
     *
     * @param hours an array of hours of operation for the metro station
     * @return a string representation of the hours of operation for the metro station
     */
    public String toStringHours(Hour[] hours) {
        StringBuilder string = new StringBuilder();

        Arrays.stream(hours).forEach((h) -> string.append(h.toString()).append("\n"));

        return string.toString();
    }

    /**
     * Adds an hour of operation to the metro station.
     *
     * @param hour the hour of operation to add
     * @return true if the hour was added successfully, false otherwise
     */
    public abstract boolean addHour(Hour hour);

    /**
     * Adds an hour of operation to the metro station with a comment and number of passengers.
     *
     * @param comment the comment for the hour of operation
     * @param passNum the number of passengers for the hour of operation
     */
    public abstract void addHour(String comment, int passNum);
}

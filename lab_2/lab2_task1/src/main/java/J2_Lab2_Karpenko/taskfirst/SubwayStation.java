package J2_Lab2_Karpenko.taskfirst;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
/**

 Represents a subway station with a name, opening year, and hours of operation.
 */
public abstract class SubwayStation {
    /**

     Regular expression pattern used to split text into words for counting.
     */
    protected static final String WORDS = "(?U)([^\\w]+)";
    /**

     The name of the subway station.
     */
    private String name;
    /**

     The year in which the subway station opened.
     */
    private int openingYear;
    /**

     Returns the name of the subway station.
     @return the name of the subway station.
     */
    public String getName() {
        return name;
    }
    /**

     Sets the name of the subway station.
     @param name the new name of the subway station.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**

     Returns the year in which the subway station opened.
     @return the year in which the subway station opened.
     */
    public int getOpeningYear() {
        return openingYear;
    }
    /**

     Sets the year in which the subway station opened.
     @param openingYear the new opening year of the subway station.
     */
    public void setOpeningYear(int openingYear) {
        this.openingYear = openingYear;
    }
    /**

     Returns an array of Hour objects representing the hours of operation of the subway station.
     @return an array of Hour objects representing the hours of operation of the subway station.
     */
    public abstract Hour[] getHours();
    /**

     Sets the hours of operation of the subway station.
     @param hours an array of Hour objects representing the new hours of operation of the subway station.
     */
    public abstract void setHours(Hour[] hours);
    /**

     Returns the total number of passengers who used the subway station during its hours of operation.
     @return the total number of passengers who used the subway station during its hours of operation.
     */
    public int getTotalPassengersNumber() {
        int total = 0;
        for (Hour hour : getHours()) {
            total += hour.getPassengersNumber();
        }
        return total;
    }
    /**

     Returns the Hour object representing the hour of operation with the least number of passengers.

     @return the Hour object representing the hour of operation with the least number of passengers, or null if there are no hours of operation.
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

     Returns the Hour object representing the hour of operation with the most words in its comment.

     @return the Hour object representing the hour of operation with the most words in its comment, or null if there are no hours of operation.
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

    private static int wordCount(String text) {
        return text.split(WORDS).length;
    }

    public void sortByCommentAlphabetically() {
        Arrays.sort(getHours(), Comparator.comparing(Hour::getComment));
    }

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

    @Override
    public String toString() {
        return "Subway station name='" + name + '\'' +
                ", Subway station opening year=" + openingYear + ", hours: \n" + toStringHours(getHours());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubwayStation that = (SubwayStation) o;

        if (openingYear != that.openingYear) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + openingYear;
        return result;
    }

    public String toStringHours(Hour[] hours) {
        StringBuilder string = new StringBuilder();

        Arrays.stream(hours).forEach((h) -> string.append(h.toString()).append("\n"));

        return string.toString();
    }
}


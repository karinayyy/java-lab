package com.example.lab_3javafx.J2_Lab3_Karpenko.taskone;

import com.thoughtworks.xstream.XStream;

import java.text.Collator;
import java.util.*;

/**
 * This class extends the MetroStationList class to represent a subway station with streaming data.
 */
public class MetroStream extends MetroStationList {
    /**
     * Constructs a new MetroStationList object with the given name and opening year.
     *
     * @param name        the name of the subway station.
     * @param openingYear the year the subway station was opened.
     */
    public MetroStream(String name, int openingYear) {
        super(name, openingYear);
    }

    /**
     * Constructs a new empty MetroStationList object.
     */
    public MetroStream() {
        super("", 0);
    }

    /**
     * Sets the name of this subway station.
     *
     * @param name the name of this subway station.
     */
    @Override
    public void setName(String name) {
        super.setName(name);
    }

    /**
     * Sets the operating hours of this subway station.
     *
     * @param hours an array of Hour objects that represent the operating hours.
     */
    @Override
    public void setHours(Hour[] hours) {
        super.setHours(hours);
    }

    /**
     * Sets the opening year of this subway station.
     *
     * @param openingYear the opening year of this subway station.
     */
    @Override
    public void setOpeningYear(int openingYear) {
        super.setOpeningYear(openingYear);
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
     * Adds an Hour object to this subway station's operating hours.
     *
     * @param hour the Hour object to add.
     * @return true if the Hour object was added successfully, false otherwise.
     */
    @Override
    public boolean addHour(Hour hour) {
        if (hour != null) {
            return super.addHour(hour);
        }
        return false;
    }

    /**
     * Returns the average number of passengers per hour for this subway station.
     *
     * @return the average number of passengers per hour for this subway station.
     */
    public double getAvgPassengers() { //da
        return Arrays.stream(getHours()).mapToInt(Hour::getPassengersNumber).average().orElse(0);
    }

//    /**
//     * Returns the shortest period between two consecutive hours for this subway station.
//     *
//     * @return the shortest period between two consecutive hours for this subway station.
//     */
//    public Period getShortestPeriod() { //da
//        List<HourWithDates> hours = Arrays.stream(getHours())
//                .map((hour -> (HourWithDates) hour))
//                .collect(Collectors.toList());
//        if (hours.size() < 2) {
//            return Period.ZERO;
//        }
//        hours.sort((Comparator.comparing(HourWithDates::getTimeOfEditing).reversed()));
//        return Period.between(LocalDate.from(hours.get(1).getTimeOfEditing()),
//                LocalDate.from(hours.get(0).getTimeOfEditing()));
//    }

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

    public void setHour(Hour h) {
        Hour[] hours = getHours();
        for (int i = 0; i < hours.length; i++) {
            if (hours[i].equals(h)) {
                hours[i] = h;
                setHours(hours);
                return;
            }
        }
        Hour[] newHours = Arrays.copyOf(hours, hours.length + 1);
        newHours[newHours.length - 1] = h;
        setHours(newHours);
    }

    /**
     * Creates and returns a new MetroStream object with test data.
     *
     * @return a new MetroStream object with test data.
     */
    static MetroStream createSubwayStation() {
        MetroStream station = new MetroStream("Kholodnogirska", 1975);

        Hour[] hours = new Hour[]{
                new Hour(1267, "bcoment i am very big"),
                new Hour(213, "acomment i am"),
                new Hour(800, "wcomment i am very"),
                new Hour(1500, "zcomment i"),
                new Hour(1000, "comment")
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
//        System.out.printf("The shortest period ==> %d %s", this.getShortestPeriod().getDays(), "days");
        String comment = "very";
        System.out.printf("\nFind hours by comment heisst -> %s\n%s ", comment, this.toStringHours(this.findHoursByComment(comment)));

        this.sortByCommentLengthDesc();
        System.out.printf("SortedByCommentLengthDesc:\n%s", this);

        this.sortByPassengerNumberDesc();
        System.out.printf("sortedByPassengerNumberDesc:\n%s", this);

        this.sortByCommentAlphabetically();
        System.out.printf("sortedByCommentAlphabetically:\n%s", this);

    }

    // Register the custom converter with XStream

    public static void main(String[] args) {
        MetroStream stat =
                MetroStream.createSubwayStation();
        stat.test();
        MetroStream metroStream = new MetroStream("Station 1", 2022);
        Hour[] hours = new Hour[]{
                new Hour(1267, "bcoment i am very big"),
                new Hour(213, "acomment i am"),
                new Hour(800, "wcomment i am very"),
                new Hour(1500, "zcomment i"),
                new Hour(1000, "comment")
        };
        metroStream.setHours(hours);
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        String xml = xStream.toXML(metroStream);
        System.out.println(xml);
    }
}
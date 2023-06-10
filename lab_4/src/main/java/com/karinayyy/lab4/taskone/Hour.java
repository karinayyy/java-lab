package com.karinayyy.lab4.taskone;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Objects;
/**
 * The Hour class represents an hour with a number of passengers and a comment.
 * It implements the Comparable interface to allow for sorting by the number of passengers.
 */
@XStreamAlias("Hour")
public class Hour implements Comparable<Hour> {

    @XStreamAlias("passengersNumber")
    private int passengersNumber;
    @XStreamAlias("comment")
    private String comment;
    public boolean containsSubstring(String substring) {
        return getComment().toUpperCase().contains(substring.toUpperCase());
    }
    public Hour() {
    }
    /**
     * Constructs a new Hour object with a number of passengers and a comment.
     *
     * @param passengersNumber the number of passengers.
     * @param comment          the comment.
     * @throws IllegalArgumentException if the number of passengers is negative.
     */
    public Hour(int passengersNumber, String comment) {
        if (passengersNumber < 0)
            throw new IllegalArgumentException("keine negativ");
        this.passengersNumber = passengersNumber;
        this.comment = comment;
    }
    /**
     * Returns the number of passengers.
     *
     * @return the number of passengers.
     */
    public int getPassengersNumber() {
        return passengersNumber;
    }
    /**
     * Sets the number of passengers.
     *
     * @param passengersNumber the number of passengers.
     */
    public void setPassengersNumber(int passengersNumber) {
        this.passengersNumber = passengersNumber;
    }
    /**
     * Returns the comment.
     *
     * @return the comment.
     */
    public String getComment() {
        return comment;
    }
    /**
     * Sets the comment.
     *
     * @param comment the comment.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * Returns a string representation of this hour object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {

        return "passengersNumber=" + passengersNumber +
                ", comment='" + comment + "'";
    }
    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the object to compare to
     * @return true if this object is the same as the o argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hour hour = (Hour) o;

        if (passengersNumber != hour.passengersNumber) return false;
        return Objects.equals(comment, hour.comment);
    }
    /**
     * Returns the hash code value for the object. This method is used in conjunction with the equals() method
     * to implement the hashCode() contract. It generates a hash code value based on the values of the object's
     * passengersNumber and comment fields.
     *
     * @return the hash code value for the object
     */
    @Override
    public int hashCode() {
        int result = passengersNumber;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
    /**
     * Compares this Hour object with the specified Hour object for order.
     * Returns a negative integer, zero, or a positive integer
     * as this Hour object is less than, equal to, or greater than the specified object.
     *
     * @param o the Hour object to be compared
     * @return a negative integer, zero, or a positive integer
     * as this Hour object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Hour o) {
        return Integer.compare(passengersNumber, o.passengersNumber);
    }
    public static void main(String[] args) {
        Hour hour1 = new Hour(2000, "Lorem ipsum dolor");
        Hour hour2 = new Hour(1000, "Quisque rutrum. Aenean imperdiet.");

        System.out.println("hour1.toString() ==> " + hour1);
        System.out.println("hour2.toString() ==> " + hour2);

        System.out.println("\nhour1.equals(hour2) ==> " + hour1.equals(hour2));

        System.out.println("invoke hour2.setPassengersNumber(hour1.getPassengersNumber())");
        System.out.println("invoke hour2.setComment(hour1.getComment())");
        hour2.setPassengersNumber(hour1.getPassengersNumber());
        hour2.setComment(hour1.getComment());

        System.out.println("\nhour1.equals(hour2) ==> " + hour1.equals(hour2));
    }

}

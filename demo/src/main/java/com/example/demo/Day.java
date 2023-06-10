package shchepinda.labfourth.task3;

import java.util.Objects;
import java.util.StringTokenizer;

/**
 * Class that represents the second of the entities of an individual task <br>
 * Contains String comment and int visitorsNum <br>
 * Implements Comparable Day
 * @author Shchepin Daniel
 */
public class Day implements Comparable<Day> {
    protected String comment;
    protected int visitorsNum;

    public Day() {}

    /**
     * Constructor of class Day
     * @param visitorsNum number of visitors
     * @param comment comment about today exhibition
     */
    public Day(int visitorsNum, String comment) {
        this.visitorsNum = visitorsNum;
        this.comment = comment;
    }

    /**
     * Check if comment contains specific word
     * @param word specific word
     * @return true - if the word is in the comment<br>
     * false - if not
     */
    public boolean containsWord(String word) {
        StringTokenizer st = new StringTokenizer(comment);
        String s;
        while (st.hasMoreTokens()) {
            s = st.nextToken();
            if (s.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Print specific day
     * @param days - day which you want to print
     */
    public void printDay(Day[] days) {
        for (Day day : days)
            System.out.println(day);
    }

    /**
     * Overrode function of Comparable interface<br>
     * Compares links and then parameters of objects.
     * @param o object to compare
     * @return true - equal<br>
     * false - not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return visitorsNum == day.visitorsNum && Objects.equals(comment, day.comment);
    }

    /**
     * Overrode function hashCode()
     * @return converts objects parameters into string format sentences and returns it
     */
    @Override
    public int hashCode() {
        return Objects.hash(visitorsNum, comment);
    }

    /**
     * Overrode function compareTo()
     * @param o object to compare
     * @return Returns some int < 0 if first string is less than second string, <br>0 if strings are equal, > 1 if first string is bigger than second.
     */
    @Override
    public int compareTo(Day o) {
        return comment.compareToIgnoreCase(o.getComment());
    }

    /**
     * Overrode function toString()
     * @return Converts objects parameters into string format sentences and returns it.
     */
    @Override
    public String toString() {
        return "\nКількість відвідувачів: " + visitorsNum + "\t" + '\"' + comment + '\"';
    }

    /**
     * Function to access the visitors number
     * @return visitors number
     */
    public int getVisitorsNum() {
        return visitorsNum;
    }

    /**
     * Function to access comment
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter for comment
     * @param comment - comment for exhibition
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Setter for visitorsNum
     * @param visitorsNum - number of visitors
     */
    public void setVisitorsNum(int visitorsNum) {
        this.visitorsNum = visitorsNum;
    }

    /**
     * Function to test this class
     */
    protected void testDay() {
        setVisitorsNum(100);
        setComment("Test exhibition");
        System.out.println(this);
    }

    public Day getDay() {
        return this;
    }
}

package J2_Lab2_Karpenko.taskfirst;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * The HourWithDates class extends the Hour class and adds a timeOfEditing field, which represents the date and time
 * <p>
 * when the hour object was edited. It also overrides the toString() method to include the timeOfEditing field in the
 * <p>
 * output.
 */
public class HourWithDates extends Hour {
    private ZonedDateTime timeOfEditing;

    public HourWithDates(int passengersNumber, String comment, ZonedDateTime timeOfEditing) {
        super(passengersNumber, comment);
        this.timeOfEditing = timeOfEditing;
    }

    public ZonedDateTime getTimeOfEditing() {
        return timeOfEditing;
    }

    public void setTimeOfEditing(ZonedDateTime timeOfEditing) {
        this.timeOfEditing = timeOfEditing;
    }

    /**
     * Overrides the getComment() method from the superclass.
     *
     * @return the comment of this hour object.
     */
    @Override
    public String getComment() {
        return super.getComment();
    }

    /**
     * Overrides the toString() method to include the timeOfEditing field in the output.
     *
     * @return a string representation of this hour object, including the number of passengers, the comment, and the
     * time of editing.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.FULL);
        return String.format(
                "{%s: %d, %s: '%s', %s: %s}",
                "Pass_num", getPassengersNumber(),
                "Comment", getComment(),
                "Time_of_editing", getTimeOfEditing().format(formatter));
    }
}

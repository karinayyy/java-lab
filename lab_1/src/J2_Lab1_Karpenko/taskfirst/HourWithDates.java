package J2_Lab1_Karpenko.taskfirst;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class HourWithDates extends Hour {
    private ZonedDateTime timeOfEditing;
    private Locale locale;

    public HourWithDates(int passengersNumber, String comment, ZonedDateTime timeOfEditing, Locale locale) {
        super(passengersNumber, comment);
        this.timeOfEditing = timeOfEditing;
        this.locale = locale;
    }

    public ZonedDateTime getTimeOfEditing() {
        return timeOfEditing;
    }

    public void setTimeOfEditing(ZonedDateTime timeOfEditing) {
        this.timeOfEditing = timeOfEditing;
    }

    @Override
    public String getComment() {
        ResourceBundle bundle = Add.getLocalizationBundle(locale);
        return bundle.getString(super.getComment());
    }

    @Override
    public String toString() {
        ResourceBundle bundle = Add.getLocalizationBundle(locale);
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.FULL)
                .withLocale(locale);
        return String.format(
                locale,
                "{%s: %d, %s: '%s', %s: %s}",
                bundle.getString("hour.passengersNumber"), getPassengersNumber(),
                bundle.getString("hour.comment"), getComment(),
                bundle.getString("hour.timeOfEditing"), getTimeOfEditing().format(formatter));
    }
}

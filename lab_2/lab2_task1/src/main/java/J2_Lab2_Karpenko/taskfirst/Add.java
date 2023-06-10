package J2_Lab2_Karpenko.taskfirst;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The Add class provides a method to get the current date and time minus a specified number of days.
 */
public final class Add {

    /**
     * Returns the current date and time minus a specified number of days, in the "Europe/Kiev" time zone.
     *
     * @param minusDays the number of days to subtract from the current date and time.
     * @return a ZonedDateTime object representing the current date and time minus the specified number of days.
     */
    public static ZonedDateTime getCurrentDateTimeMinusDays(long minusDays) {
        return ZonedDateTime.now(ZoneId.of("Europe/Kiev")).minusDays(minusDays);
    }

}
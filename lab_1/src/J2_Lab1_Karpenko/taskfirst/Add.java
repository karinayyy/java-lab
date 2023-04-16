package J2_Lab1_Karpenko.taskfirst;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public final class Add {

    public static ZonedDateTime getCurrentDateTimeMinusDays(long minusDays) {
        return ZonedDateTime.now(ZoneId.of("Europe/Kiev")).minusDays(minusDays);
    }

    public static ResourceBundle getLocalizationBundle(Locale locale) {//подбирает нужный текст.файл для наш локали
        return ResourceBundle.getBundle("localization", locale);
    }
}

package J2_Lab1_Karpenko.taskfirst;
import java.text.Collator;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MetroStationListWithLocalization extends MetroStationList {
    private Locale locale;
    public MetroStationListWithLocalization(String name, int openingYear, Locale locale) {
        super(name, openingYear);
        this.locale = locale;
    }
    @Override
    public String toString() {
        ResourceBundle bundle = Add.getLocalizationBundle(locale);
        return String.format("%s='%s', %s=%d, %s:%n%s",
                bundle.getString("label.subwayStation.name"), getName(),
                bundle.getString("label.subwayStation.opYear"), getOpeningYear(),
                bundle.getString("hours"), toStringHours(getHours()));
    }
    public Hour[] findHoursByComment(String text) {
        return Arrays.stream(getHours()).filter((hour -> {
            for (String word : hour.getComment().split(WORDS)) {
                if (word.startsWith(text) || word.endsWith(text)) {
                    return true;
                }
            }
            return false;
        })).toArray(Hour[]::new);
    }

    public double getAvgPassengers() {
        return Arrays.stream(getHours()).mapToInt(Hour::getPassengersNumber).average().orElse(0);
    }

    public Period getShortestPeriod() {
        List<HourWithDates> hours = Arrays.stream(getHours())
                .map((hour -> (HourWithDates) hour))
                .collect(Collectors.toList());
        if (hours.size() < 2) {
            return Period.ZERO;
        }
        hours.sort((Comparator.comparing(HourWithDates::getTimeOfEditing).reversed()));
        return Period.between(LocalDate.from(hours.get(1).getTimeOfEditing()),
                LocalDate.from(hours.get(0).getTimeOfEditing()));
    }

    @Override
    public void sortByCommentAlphabetically() {
        Hour[] hours = getHours();
        Arrays.sort(hours, new Comparator<>() {
            final Collator collator = Collator.getInstance(locale);

            @Override
            public int compare(Hour o1, Hour o2) {
                return collator.compare(o1.getComment(), o2.getComment());
            }
        });
        setHours(hours);
    }

    private static MetroStationListWithLocalization createSubwayStation(Locale locale) {
        ResourceBundle bundle = Add.getLocalizationBundle(locale);
        MetroStationListWithLocalization station =
                new MetroStationListWithLocalization(
                        bundle.getString("subwayStation.name"), 1975, locale);

        HourWithDates[] hours = new HourWithDates[]{
                new HourWithDates(1234, "comment1",
                        Add.getCurrentDateTimeMinusDays(30),
                        locale),
                new HourWithDates(213, "comment2",
                        Add.getCurrentDateTimeMinusDays(51),
                        locale),
                new HourWithDates(800, "comment3",
                        Add.getCurrentDateTimeMinusDays(0),
                        locale),
                new HourWithDates(1500, "comment4",
                        Add.getCurrentDateTimeMinusDays(69),
                        locale),
                new HourWithDates(1000, "comment5",
                        Add.getCurrentDateTimeMinusDays(4),
                        locale)
        };
        station.setHours(hours);
        return station;
    }

    public void test() {
        ResourceBundle bundle = Add.getLocalizationBundle(locale);
        System.out.println(this);
        System.out.printf("%n%s -> %d",
                bundle.getString("label.totalPassengers"),
                getTotalPassengersNumber());
        System.out.printf("%n%s -> %s",
                bundle.getString("label.hourWithMaxWordNumberInComment"),
                getHourWithMaxWordNumberInComment());
        System.out.printf("%n%s -> %s",
                bundle.getString("label.hourWithLeastPassengersNumber"),
                getHourWithLeastPassengersNumber());
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        System.out.printf("%n%s -> %s",
                bundle.getString("label.avgPassengers"),
                numberFormat.format(getAvgPassengers()));
        System.out.printf("%n%s -> %d %s",
                bundle.getString("label.shortestPeriod"),
                getShortestPeriod().getDays(),
                bundle.getString("label.days"));
        String comment = "com";
        System.out.printf("%n%s '%s': %n%s",
                bundle.getString("label.findHoursByComment"),
                comment,
                toStringHours(findHoursByComment(comment)));

        System.out.printf("///////%s%n", bundle.getString("label.sortByCommentLengthDesc"));
        sortByCommentLengthDesc();
        Arrays.stream(getHours()).forEach((hour -> System.out.println(hour.getComment())));
        System.out.printf("///////%s%n", bundle.getString("label.sortByPassengerNumberDesc"));
        sortByPassengerNumberDesc();
        Arrays.stream(getHours()).forEach((hour -> System.out.println(hour.getPassengersNumber())));
        System.out.printf("///////%s%n", bundle.getString("label.sortByCommentAlphabetically"));
        sortByCommentAlphabetically();
        Arrays.stream(getHours()).forEach((hour -> System.out.println(hour.getComment())));
    }

    public static void main(String[] args) {
        Locale loc = Locale.US;
        MetroStationListWithLocalization station =
                MetroStationListWithLocalization.createSubwayStation(loc);
        System.out.println("\nLocale: " + loc + "\n");
        station.test();
        loc = new Locale("uk");
        System.out.println("\nlocale: " + loc + "\n");
        station = MetroStationListWithLocalization.createSubwayStation(loc);
        station.test();
    }
}

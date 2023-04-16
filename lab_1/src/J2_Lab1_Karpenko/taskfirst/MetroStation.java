package J2_Lab1_Karpenko.taskfirst;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public abstract class MetroStation {
    protected static final String WORDS = "(?U)([^\\w]+)";
    private String name;
    private int openingYear;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getOpeningYear() {
        return openingYear;
    }
    public void setOpeningYear(int openingYear) {
        this.openingYear = openingYear;
    }
    public abstract Hour[] getHours();

    public abstract void setHours(Hour[] hours);

    public int getTotalPassengersNumber() {
        int total = 0;
        for (Hour hour : getHours()) {
            total += hour.getPassengersNumber();
        }
        return total;
    }

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
        return "Metro station name='" + name + '\'' +
                "| Metro station opening year=" + openingYear + "| hours: \n" + toStringHours(getHours());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetroStation that = (MetroStation) o;

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

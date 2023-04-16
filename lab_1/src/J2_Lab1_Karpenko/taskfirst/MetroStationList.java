package J2_Lab1_Karpenko.taskfirst;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetroStationList extends MetroStation {
    private List<Hour> hours = new ArrayList<>();
    public MetroStationList(String name, int openingYear) {
        setName(name);
        setOpeningYear(openingYear);
    }
    @Override
    public void sortByPassengerNumberDesc() {
        hours.sort(((o1, o2) -> Integer.compare(o2.getPassengersNumber(), o1.getPassengersNumber())));
    }
    @Override
    public void sortByCommentLengthDesc() {
        hours.sort((o1, o2) -> Integer.compare(o2.getComment().length(), o1.getComment().length()));
    }
    @Override
    public Hour[] getHours() {
        return hours.toArray(new Hour[]{});
    }
    @Override
    public void setHours(Hour[] hours) {
        this.hours = Arrays.asList(hours);
    }
}

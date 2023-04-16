package J2_Lab1_Karpenko.tasksecond;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.*;

public class DemoDate {

    public static void main(String[] args) {
        String datein = "22.03.2023";

        Pattern p = Pattern.compile("^(\\d{2})\\.(\\d{2})\\.(\\d{4})$");
        Matcher m = p.matcher(datein);
        if (m.matches()) {
            int day = Integer.parseInt(m.group(1));
            int month = Integer.parseInt(m.group(2));
            int year = Integer.parseInt(m.group(3));

            Calendar calendar = new GregorianCalendar(year, month-1, day);
            Date date = new Date(year - 1900, month - 1, day);
            LocalDate localDate = LocalDate.of(year, month, day);
            System.out.println("Об'єкт Date: " + date);
            System.out.println("Об'єкт GregorianCalendar: " + calendar.getTime());
            System.out.println("Об'єкт LocalDate: " + localDate);
        } else {
            System.out.println("Wrong date format");
        }
    }
}


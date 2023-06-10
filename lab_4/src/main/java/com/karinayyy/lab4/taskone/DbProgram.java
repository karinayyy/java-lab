package com.karinayyy.lab4.taskone;

import static com.karinayyy.lab4.taskone.DbUtils.*;

public class DbProgram {
    public static void main(String[] args) {
        Metros metros = createMetros();
        exportToJSON(metros, "Metros.json");
        metros = importFromJSON("Metros.json");
        createConnection();
        if (createDatabase()) {
            addAll(metros);
            showAll();
            System.out.println("\nSorted:");
            displayMetroInfo("Derzprom");
            System.out.println("\nЗа зменшенням кількості пасажирів:");
            showSortedByPassengerDec("Derzprom");
            System.out.println("\nЗа зменшенням довжини коментаря:");
            showSortedByCommentLenDec("Derzprom");
            System.out.println("\nЗа алфавітом коментаря:");
            showSortedByCommentAlp("Derzprom");
            System.out.println("\nДодаємо годину:");
            addHour("Derzprom", new HourForDB(12323, "Many Passengers"));
            showAll();
            System.out.println("\nВидаляємо годину:");
            removeHour("Derzprom", 1);
            showAll();
            System.out.println("\nДодаємо метро:");
            addMetro(new MetroForDB("Kholodnogirska", 1932));
            addHour("Kholodnogirska", new HourForDB(1234, "New Hour for Kholodnogirska"));
            showAll();
//            System.out.println("\nПошук слова \"перепис\":");
//            findWord("перепис");
            exportToJSON("MetrosFromDB.json");
            closeConnection();
        }
    }

    /**
     * Створення об'єкта Countries і заповнення даними для демонстрації програми
     *
     * @return об'єкт, який містить необхідні дані для демонстрації програми
     */
    static Metros createMetros() {
        MetroForDB metro = new MetroForDB();
        metro.setName("Derzprom");
        metro.setOpeningYear(1934);
        metro.addHour("bcoment i am very big", 1267);
        metro.addHour("acomment i am", 1234);
        metro.addHour("wcomment i am very", 3456);
        metro.addHour("comment", 41321);
        Metros metros = new Metros();
        metros.getList().add(metro);
        return metros;
    }
}

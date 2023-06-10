package shchepinda.labfourth.task3;

import shchepinda.labfourth.task1.DayWithStreams;
/**
 * Клас, що відповідає DayForDB
 *
 * Дисципліна «Поглиблений курс програмування Java
 * Лабораторна робота №4, Варіант №23, завдання 3
 *
 * @author Щепін Данило, KH-221a
 */
public class DayForDB extends DayWithStreams {
    private long id = -1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DayForDB() {}

    public DayForDB(int visitorsNum, String comment, long id) {
        this.visitorsNum = visitorsNum;
        this.comment = comment;
        this.id = id;
    }

    public DayForDB(int visitorsNum, String comment) {
        this.visitorsNum = visitorsNum;
        this.comment = comment;
    }
}

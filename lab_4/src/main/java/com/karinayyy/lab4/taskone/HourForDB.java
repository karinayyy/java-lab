package com.karinayyy.lab4.taskone;

public class HourForDB extends HourWithStreams{
    private long id = -1;
    /**
     * Повертає ID
     *
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * Встановлює ID
     *
     * @param id ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Конструктор ініціалізує об'єкт усталеними значеннями
     */
    public HourForDB() {
    }

    /**
     * Конструктор ініціалізує об'єкт вказаними значеннями
     *
     * @param passgCount
     * @param comments   текст коментаря
     */
    public HourForDB(int passgCount, String comments) {
        super(passgCount,  comments);
    }
}

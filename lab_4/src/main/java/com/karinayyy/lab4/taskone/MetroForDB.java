package com.karinayyy.lab4.taskone;

public class MetroForDB extends MetroStream{

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

    public MetroForDB() {

    }

    public MetroForDB(String name, int year) {
        setName(name);
        setOpeningYear(year);
    }

}

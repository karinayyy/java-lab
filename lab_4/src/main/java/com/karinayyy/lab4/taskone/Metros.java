package com.karinayyy.lab4.taskone;

import java.util.ArrayList;
import java.util.List;

public class Metros {
    private List<MetroForDB> list = new ArrayList<>();
    public List<MetroForDB> getList () {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}

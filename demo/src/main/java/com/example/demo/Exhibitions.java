package shchepinda.labfourth.task3;

import java.util.ArrayList;
import java.util.List;
/**
 * Клас, що відповідає Exhibitions
 *
 * Дисципліна «Поглиблений курс програмування Java
 * Лабораторна робота №4, Варіант №23, завдання 3
 *
 * @author Щепін Данило, KH-221a
 */
public class Exhibitions {
    private List<ExhibitionForDB> list = new ArrayList<>();

    public List<ExhibitionForDB> getList() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}

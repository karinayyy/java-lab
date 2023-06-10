package shchepinda.labfourth.task3;

import shchepinda.labfourth.task1.ExhibitionWithStreams;
/**
 * Клас, що відповідає ExhibitionForDB
 *
 * Дисципліна «Поглиблений курс програмування Java
 * Лабораторна робота №4, Варіант №23, завдання 3
 *
 * @author Щепін Данило, KH-221a
 */
public class ExhibitionForDB extends ExhibitionWithStreams {
    private long id = -1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ExhibitionForDB() {
    }

    public ExhibitionForDB(String name, String artSurname) {
        setName(name);
        setArtSurname(artSurname);
    }
}

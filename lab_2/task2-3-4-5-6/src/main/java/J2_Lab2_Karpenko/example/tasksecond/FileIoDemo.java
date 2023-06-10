package J2_Lab2_Karpenko.example.tasksecond;

import java.io.File;

import static J2_Lab2_Karpenko.example.tasksecond.FileIo.fileIo;

/**
 * Клас FileIoDemo містить метод main, який демонструє використання методу fileIo класу FileIo для виведення інформації про файли та підкаталоги у заданому каталозі та його підкаталогах.
 */
public class FileIoDemo {

    /**
     * Точка входу в програму. Створює об'єкт класу File для каталогу "testdir" та викликає метод fileIo з цим об'єктом в якості аргументу, щоб вивести на екран інформацію про файли та підкаталоги у цьому каталозі.
     *
     * @param args масив аргументів командного рядка. Цей параметр не використовується в даному методі.
     */
    public static void main(String[] args) {
        // определяем объект для каталога
        File dir = new File("testdir");
        // если объект представляет каталог и существует
        if (dir.exists() || dir.isDirectory()) {
            fileIo(dir);
        } else {
            System.out.println("Хибне ім\'я теки або вона не існуе!");
        }
    }
}

package J2_Lab2_Karpenko.example.tasksecond;

import java.io.File;

/**
 * Клас FileIo містить статичний метод fileIo, який виводить на екран інформацію про всі файли та підкаталоги у заданому каталозі та його підкаталогах.
 */
public class FileIo {
    /**
     * Виводить на екран інформацію про всі файли та підкаталоги у заданому каталозі та його підкаталогах.
     * Якщо каталог пустий, виводиться повідомлення про це.
     *
     * @param dir об'єкт класу File, що представляє каталог, для якого необхідно вивести інформацію про файли та підкаталоги
     */
    public static void fileIo(File dir) {
        File[] list = dir.listFiles();
        if (list == null || list.length == 0) {
            System.out.println("Папка пуста!");
            return;
        }
        // Виводяться дані про файли, каталоги, підкаталоги
        for (File file : list) {
            if (file.isDirectory()) {
                fileIo(file);
            } else {
                System.out.println(file.getAbsolutePath());
            }
        }
    }
}

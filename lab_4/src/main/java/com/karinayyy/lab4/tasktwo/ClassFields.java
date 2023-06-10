package com.karinayyy.lab4.tasktwo;

import java.lang.reflect.Field;
import java.util.Scanner;

public class ClassFields {

    public static void main(String[] args) {
        System.out.print("Уведіть повне ім\'я класу: ");
        String className = new Scanner(System.in).next();
        try {
            Class<?> c = Class.forName(className);
            System.out.println("Поля класу " + className + ":");
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true); // дозволити отримувати доступ до закритих і захищених полів
                System.out.printf("  %s %s%n", f.getType().getCanonicalName(), f.getName());
            }
        }
        catch (ClassNotFoundException e) {
            System.err.println("Помилка введення імені класу!");
        }
    }

}
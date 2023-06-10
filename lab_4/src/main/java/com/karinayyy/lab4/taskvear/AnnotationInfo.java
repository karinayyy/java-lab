package com.karinayyy.lab4.taskvear;

import javafx.fxml.FXML;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.Scanner;

public class AnnotationInfo {
    static class YourClass {
        @Retention(RetentionPolicy.RUNTIME)
        @interface MyAnnotation {
            String value();
        }

        @MyAnnotation("Аннотация для метода 1")
        public void method1() {
            // Код
        }

        @MyAnnotation("Аннотация для метода 2")
        public void method2() {
            // Код
        }

        public void method3() {
            // Код
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя метода класса: ");
        String methodName = scanner.nextLine();

        try {
            // Получаем класс, в котором находится метод
            Class<?> clazz = YourClass.class;

            // Получаем метод по имени
            Method method = clazz.getDeclaredMethod(methodName);

            // Получаем все аннотации метода
            Annotation[] annotations = method.getDeclaredAnnotations();

            // Выводим информацию об аннотациях
            if (annotations.length > 0) {
                System.out.println("Аннотации, отмечающие метод " + methodName + ":");
                for (Annotation annotation : annotations) {
                    if (annotation instanceof YourClass.MyAnnotation) {
                        YourClass.MyAnnotation myAnnotation = (YourClass.MyAnnotation) annotation;
                        System.out.println(myAnnotation.value());
                    }
                }
            } else {
                System.out.println("Метод " + methodName + " не содержит аннотаций.");
            }
        } catch (NoSuchMethodException e) {
            System.out.println("Метод " + methodName + " не найден.");
        }
    }
}

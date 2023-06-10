package com.example.lab_3javafx.J2_Lab3_Karpenko.tasktwo;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * A demonstration of the use of ObservableList and its listeners.
 */
public class UseOfObservableList {
    /**
     * The main method creates an ObservableList of Double objects and adds a listener to it. It then creates two new
     * ObservableLists to hold the positive and negative numbers from the first list. It clears the original list, adds
     * the positive numbers back in, and then appends the negative numbers in reverse order. The final state of the list is printed to the console.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create an observable list of Double objects and initialize it with some values
        final ObservableList<Double> list = FXCollections.observableArrayList(-5.5, 3.2, -20.5, 4.0, -2.1, 5.6, -3.7);

        // Add a listener to the list to print out any changes
        list.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        System.out.println("Numbers were added " + list);
                        return;
                    }
                    if (change.wasRemoved()) {
                        System.out.println("List was cleared " + list);
                        return;
                    }
                    System.out.println("Detected changes " + list);
                }
            }
        });

        System.out.println("Start list " + list);

        // Create two new lists to hold the positive and negative numbers from the first list
        final ObservableList<Double> listWithPositive = FXCollections.observableArrayList();
        final ObservableList<Double> listWithNegative = FXCollections.observableArrayList();

        //Iterate through the original list and add positive numbers to listWithPositive and negative numbers to listWithNegative
        for (Double i : list) {
            if (i >= 0) {
                listWithPositive.add(i);
            } else {
                listWithNegative.add(i);
            }
        }

        list.clear();
        list.addAll(listWithPositive);
        for (int i = listWithNegative.size() - 1; i >= 0; i--) {
            list.add(listWithNegative.get(i));
        }
        System.out.println("Final list " + list);
    }
}

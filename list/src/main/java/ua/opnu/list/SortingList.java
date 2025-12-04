package ua.opnu.list;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;

/**
 * Додаток, який показує список студентів
 * і дозволяє сортувати його за ім'ям, прізвищем та середнім балом.
 */
public class SortingList extends Application {

    private ObservableList<Student> students;
    private ListView<Student> listView;

    @Override
    public void start(Stage primaryStage) {
        // Створюємо дані
        students = FXCollections.observableArrayList(
                new Student("Іван", "Петренко", 85.5),
                new Student("Марія", "Іваненко", 91.0),
                new Student("Олег", "Сидоренко", 78.0),
                new Student("Анна", "Коваль", 95.3),
                new Student("Богдан", "Шевченко", 82.7)
        );

        // Список, який показує студентів
        listView = new ListView<>(students);
        listView.setPrefHeight(250);

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        // Верх – список, низ – панель з кнопками
        root.getChildren().addAll(listView, createButtonsPanel());
        VBox.setVgrow(listView, Priority.ALWAYS);

        Scene scene = new Scene(root, 500, 350);
        primaryStage.setTitle("Сортування списку студентів");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Створює панель з трьома кнопками сортування.
     */
    private HBox createButtonsPanel() {
        HBox hb = new HBox();
        hb.setPadding(new Insets(10));

        Button sortByNameButton = new Button("Сортувати за ім'ям");
        Button sortByLastNameButton = new Button("Сортувати за прізвищем");
        Button sortByMarkButton = new Button("Сортувати за балом");

        // Сортування за ім'ям (використовуємо готовий NameSorter)
        sortByNameButton.setOnAction(e ->
                FXCollections.sort(students, new NameSorter(true))
        );

        // Сортування за прізвищем (Comparator всередині)
        sortByLastNameButton.setOnAction(e ->
                FXCollections.sort(students, new Comparator<Student>() {
                    @Override
                    public int compare(Student s1, Student s2) {
                        return s1.getLastName().compareTo(s2.getLastName());
                    }
                })
        );

        // Сортування за середнім балом (від більшого до меншого)
        sortByMarkButton.setOnAction(e ->
                FXCollections.sort(students,
                        Comparator.comparingDouble(Student::getAvgMark).reversed())
        );

        // Відстань між елементами ряду
        hb.setSpacing(5);
        // Додаємо до ряду елементи. У нашому випадку – кнопки
        hb.getChildren().addAll(sortByNameButton, sortByLastNameButton, sortByMarkButton);
        // Говоримо, що елементи в ряді мають бути вирівняні по центру
        hb.setAlignment(Pos.CENTER);

        return hb;
    }

    public static void main(String[] args) {
        // Метод запускає додаток
        launch(args);
    }
}

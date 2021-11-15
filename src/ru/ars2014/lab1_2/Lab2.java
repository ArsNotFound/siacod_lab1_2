package ru.ars2014.lab1_2;

import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab2 {
    public static final Scanner in = new Scanner(System.in);
    public static HashTable<Integer> hashTable;

    public static void main(String[] args) {
        int choice;

        do {
            System.out.print("""
                    Выберите нужный вариант:
                    1 - Сгенерировать новую таблицу
                    2 - Использовать существующую таблицу
                    0 - Выход из программы
                    ?:\040""");
            choice = in.nextInt();

        } while (choice < 0 || choice > 2);

        if (choice == 1) {
            hashTable = Lab1.generateIntHashTable(Lab1.N, false);
        } else {
            hashTable = new HashTable<>(Lab1::hash);
            StringBuilder tabStrBuilder = new StringBuilder();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.trim().toLowerCase(Locale.ROOT).equals("end")) {
                    break;
                }
                tabStrBuilder.append(line);
            }

            String tabStr = tabStrBuilder.toString();

            Pattern pat = Pattern.compile("\\d+: \\d+");
            Matcher matcher = pat.matcher(tabStr);

            while (matcher.find()) {
                String el = tabStr.substring(matcher.start(), matcher.end()).split(":")[1].trim();
                int elem = Integer.parseInt(el);
                hashTable.add(elem);
            }
        }

        System.out.println();
        System.out.println("Начальная таблица:");
        Lab1.printHashTable(hashTable);


        do {
            System.out.print("""
                                        
                    Выберите нужный вариант:
                    1 - Найти элемент
                    2 - Удалить элемент
                    3 - Добавить новый элемент
                    4 - Добавить сгенерированые элементы
                    5 - Заменить элемент
                    6 - Вывести таблицу с коэффициентами
                    0 - Выход из программы
                    ?:\040""");
            choice = in.nextInt();

            switch (choice) {
                case 1 -> findElement();
                case 2 -> deleteElement();
                case 3 -> addElement();
                case 4 -> addRandomElements();
                case 5 -> replaceElement();
                case 6 -> {
                    System.out.println();
                    Lab1.printHashTable(hashTable);
                }
            }
        } while (choice != 0);
    }

    public static void findElement() {
        System.out.print("\nВведите элемент: ");
        int elem = in.nextInt();

        int index = hashTable.find(elem);
        System.out.println(index < 0 ? "Элемента в таблице нет" : "Элемент " + elem + " имеет индекс " + index);
    }

    public static void deleteElement() {
        System.out.print("\nВведите элемент: ");
        int elem = in.nextInt();

        int index = hashTable.remove(elem);
        System.out.println(index >= 0 ? "Элемент был успешно удалён из индекса " + index : "Элемента в таблице нет");
    }

    public static void addElement() {
        System.out.print("\nВведите элемент: ");
        int elem = in.nextInt();

        boolean ok = hashTable.add(elem);
        if (ok) {
            int index = hashTable.find(elem);
            System.out.println("Элемент был успешно добавлен с индексом " + index);
        } else {
            System.out.println("Элемент уже есть в таблице");
        }
    }

    public static void addRandomElements() {
        System.out.print("\nВведите количество элементов: ");
        int count = in.nextInt();

        while (count >= 0) {
            int elem = Lab1.rand.nextInt(Lab1.MAX - Lab1.MIN + 1) + Lab1.MIN;
            if (hashTable.add(elem)) {
                System.out.println("Добавлен элемент " + elem + " с индексом " + hashTable.find(elem));
                count--;
            } else {
                System.out.println("Элемент " + elem + " уже есть в таблице. Пропуск...");
            }
        }
    }

    public static void replaceElement() {
        System.out.print("\nВведите элемент для удаления: ");
        int elem = in.nextInt();

        int index = hashTable.remove(elem);
        System.out.println(index >= 0 ? "Элемент " + elem + " удалён из индекса " + index : "Элемента в таблице нет");
        if (index < 0) {
            return;
        }

        System.out.print("Введите элемент для добавления: ");
        elem = in.nextInt();
        boolean ok = hashTable.add(elem);
        if (ok) {
            index = hashTable.find(elem);
            System.out.println("Элемент был успешно добавлен с индексом " + index);
        } else {
            System.out.println("Элемент уже есть в таблице");
        }
    }
}

package ru.ars2014.lab1_2;

import java.util.Scanner;

public class Lab2 {
    public static final int N = 47;
    public static final Scanner in = new Scanner(System.in);
    public static HashTable<Integer> hashTable;

    public static void main(String[] args) {
        hashTable = Lab1.generateIntHashTable(N, false);
        System.out.println();

        System.out.println("Начальная таблица:");
        Lab1.printHashTable(hashTable);

        int choice;
        do {
            System.out.print("""
                                        
                    Выберите нужный вариант:
                    1 - Найти элемент
                    2 - Удалить элемент
                    3 - Добавить новый элемент
                    4 - Заменить элемент
                    5 - Вывести таблицу с коэффициентами
                    0 - Выход из программы
                    ?:\040""");
            choice = in.nextInt();

            switch (choice) {
                case 1 -> findElement();
                case 2 -> deleteElement();
                case 3 -> addElement();
                case 4 -> replaceElement();
                case 5 -> {
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
        System.out.println(index == -1 ? "Элемента в таблице нет" : "Элемент " + elem + " имеет индекс " + index);
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
        System.out.println(ok ? "Элемент был успешно добавлен" : "Элемент уже есть в таблице");
    }

    public static void replaceElement() {
        System.out.print("\nВведите элемент для удаления: ");
        int elem = in.nextInt();

        int index = hashTable.remove(elem);
        System.out.println(index >= 0 ? "Элемент " + elem + " удалён из индекса " + index : "Элемента в таблице нет");

        System.out.print("Введите элемент для добавления: ");
        elem = in.nextInt();
        boolean ok = hashTable.add(elem);
        System.out.println(ok ? "Элемент был успешно добавлен" : "Элемент уже есть в таблице");
    }
}

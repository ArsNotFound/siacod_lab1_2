package ru.ars2014.lab1_2;

import java.util.*;

public class Lab1 {
    public static final int N = 47;
    public static final int L = 6;
    public static final int TERM_WIDTH = 120;
    public static final int ELEMS_PER_LINE = TERM_WIDTH / (L + 3);
    public static final int ELEMS_PER_LINE_HASH = TERM_WIDTH / (L + 12);

    public static final int MIN = (int) Math.pow(10, L - 1);
    public static final int MAX = (int) Math.pow(10, L) - 1;

    public static final Random rand = new Random();

    public static void main(String[] args) {
        HashTable<Integer> hashTable = generateIntHashTable(N, true);
        System.out.println();

        System.out.println("Результирующая таблица:");
        printHashTable(hashTable);
    }

    public static int hash(Integer i) {
        String s = i.toString();
        int low = Integer.parseInt(s.substring(0, 3));
        int high = Integer.parseInt(s.substring(s.length() - 3));
        return low + high;
    }

    public static HashTable<Integer> generateIntHashTable(int size, boolean print) {
        Set<Integer> set = new HashSet<>();
        while (set.size() != size) {
            set.add(rand.nextInt(MAX - MIN + 1) + MIN);
        }

        HashTable<Integer> hashTable = new HashTable<>(size, Lab1::hash);

        if (print) System.out.println("Входная последовательность:");
        Iterator<Integer> iter = set.iterator();
        while (iter.hasNext()) {
            for (int i = 0; i < ELEMS_PER_LINE && iter.hasNext(); i++) {
                Integer elem = iter.next();
                hashTable.add(elem);
                if (print) System.out.print(elem + " ");
            }
            if (print) System.out.println();
        }

        return hashTable;
    }

    public static void printHashTable(HashTable<Integer> hashTable) {
        Iterator<HashTable.Node<Integer>> data = Arrays.stream(hashTable.getData()).iterator();

        int i = 0;
        while (data.hasNext()) {
            for (int j = 0; j < ELEMS_PER_LINE_HASH && data.hasNext(); j++) {
                HashTable.Node<Integer> d = data.next();
                System.out.printf("%1$2d: %2$" + -(L + 1) + "s ", i++, (d == null ? "null" : d.valid ? d.value : "deleted"));
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Среднее количество шагов: " + HashTable.STEPS.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0));
        System.out.println("Коэффициент заполнения таблицы: " + (double) hashTable.getLength() / hashTable.getCapacity());
    }
}

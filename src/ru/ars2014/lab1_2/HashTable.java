package ru.ars2014.lab1_2;

import java.util.Arrays;
import java.util.function.Function;

public class HashTable<T> {
    private static final double REHASH_SIZE = 0.75;
    private static final int DEFAULT_SIZE = 8;
    private final Function<T, Integer> hashFunc;
    public int STEPS = 0;
    private Node<T>[] data;

    private int capacity;
    private int deleted = 0;
    private int length = 0;

    public HashTable(int capacity, Function<T, Integer> hashFunc) {
        this.capacity = (int) (capacity * REHASH_SIZE * 2);
        this.hashFunc = hashFunc;
        this.data = new Node[this.capacity];
        Arrays.fill(data, null);
    }

    public HashTable(Function<T, Integer> hashFunc) {
        this(DEFAULT_SIZE, hashFunc);
    }

    private void resize() {
        capacity *= 2;
        rehash();
    }

    private void rehash() {
        length = 0;
        deleted = 0;

        Node<T>[] newData = new Node[capacity];
        Arrays.fill(newData, null);

        Node<T>[] oldData = data;
        data = newData;

        for (Node<T> node : oldData)
            if (node != null && node.valid)
                add(node.value);
    }

    public boolean add(T value) {
        if (length + 1 > (int) (REHASH_SIZE * capacity))
            resize();

        if (deleted > length)
            rehash();

        int h = hashFunc.apply(value) % capacity;
        int i = 0;
        int firstDeleted = -1;
        int _h = (h + i) % capacity;
        while (data[_h] != null && i < capacity) {
            if (data[_h].valid && data[_h].value.equals(value))
                return false;
            if (!data[_h].valid && firstDeleted == -1)
                firstDeleted = _h;
            i++;
            _h = (h + i) % capacity;
        }

        STEPS += i + 1;

        if (firstDeleted == -1) {
            data[_h] = new Node<>(value);
        } else {
            data[firstDeleted].valid = true;
            data[firstDeleted].value = value;
            deleted--;
        }

        length++;
        return true;
    }

    public int find(T value) {
        int h = hashFunc.apply(value) % capacity;
        int i = 0;
        int h_ = (h + i) % capacity;
        while (data[h_] != null && i < capacity) {
            if (data[h_].valid && data[h_].value.equals(value))
                return h;
            i++;
            h_ = (h + i) % capacity;
        }
        return -1;
    }

    public int remove(T value) {
        int index = find(value);
        if (index >= 0) {
            data[index].valid = false;
            length--;
            deleted++;
            return index;
        }
        return -1;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getDeleted() {
        return deleted;
    }

    public int getLength() {
        return length;
    }

    public Node<T>[] getData() {
        return data;
    }

    public static class Node<R> {
        public R value;
        public boolean valid;

        public Node(R value) {
            this.value = value;
            this.valid = true;
        }
    }
}

package com.data_structure_java;

public interface List<E> {
    boolean add(E e, int index);

    void addFirst(E e);

    void addLast(E e);

    E get(int index);

    E getFirst();

    E remove(int index);

    E removeLast();

    int size();
}

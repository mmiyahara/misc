package com.data_structure_java;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IntListTest {
    @Test
    public void shouldAddSuccess() {
        IntList i = new IntList(1);

        i.add(2, 0);
        assertTrue(i.size() == 2);
        assertTrue(i.get(0) == 2);
        assertTrue(i.get(1) == 1);

        i.add(3, 2);
        assertTrue(i.size() == 3);
        assertTrue(i.get(0) == 2);
        assertTrue(i.get(1) == 1);
        assertTrue(i.get(2) == 3);
    }

    @Test
    public void shouldAddFirstSuccess() {
        IntList i = new IntList(1);
        i.addFirst(2);
        assertTrue(i.size() == 2);
        assertTrue(i.get(0) == 2);
        assertTrue(i.get(1) == 1);
    }

    @Test
    public void shouldAddLastSuccess() {
        IntList i = new IntList(1);
        i.addLast(2);
        assertTrue(i.size() == 2);
        assertTrue(i.get(0) == 1);
        assertTrue(i.get(1) == 2);
    }

    @Test
    public void shouldGetSuccess() {
        IntList i = new IntList(1);
        assertTrue(i.get(0) == 1);
    }

    @Test
    public void shouldGetFirstSuccess() {
        IntList i = new IntList(1);
        assertTrue(i.getFirst() == 1);
    }

    @Test
    public void shouldRemoveSuccess1() {
        IntList i = new IntList(1);
        i.remove(0);
        assertTrue(i.size() == 0);
    }

    @Test
    public void shouldRemoveSuccess2() {
        IntList i = new IntList(1);
        i.addLast(2);
        i.remove(0);
        assertTrue(i.size() == 1);
        assertTrue(i.get(0) == 2);
    }

    @Test
    public void shouldRemoveLastSuccess1() {
        IntList i = new IntList(1);
        i.removeLast();
        assertTrue(i.size() == 0);
    }

    @Test
    public void shouldRemoveLastSuccess2() {
        IntList i = new IntList(1);
        i.addLast(2);
        i.removeLast();
        assertTrue(i.size() == 1);
        assertTrue(i.get(0) == 1);
    }
}

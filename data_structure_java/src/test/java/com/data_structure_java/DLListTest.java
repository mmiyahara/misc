package com.data_structure_java;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DLListTest {
    @Test
    public void shouldAddSuccess() {
        DLList<Integer> d = new DLList<Integer>();
        d.add(1, 0);
        d.add(2, 1);
        assertTrue(d.size() == 2);
        assertTrue(d.get(0) == 1);
        assertTrue(d.get(1) == 2);
    }

    @Test
    public void shouldAddFirstSuccess() {
        DLList<Integer> d = new DLList<Integer>();
        d.addFirst(1);
        d.addFirst(2);
        assertTrue(d.size() == 2);
        assertTrue(d.get(0) == 2);
        assertTrue(d.get(1) == 1);
    }

    @Test
    public void shouldAddLastSuccess() {
        DLList<Integer> d = new DLList<Integer>();
        d.addLast(1);
        d.addLast(2);
        assertTrue(d.size() == 2);
        assertTrue(d.get(0) == 1);
        assertTrue(d.get(1) == 2);
    }

    @Test
    public void shouldGetSuccess() {
        DLList<Integer> d = new DLList<Integer>(1);
        assertTrue(d.size() == 1);
        assertTrue(d.get(0) == 1);
        d.addLast(2);
        assertTrue(d.size() == 2);
        assertTrue(d.get(0) == 1);
        assertTrue(d.get(1) == 2);
    }

    @Test
    public void shouldGetFirstSuccess() {
        DLList<Integer> d = new DLList<Integer>(1);
        assertTrue(d.size() == 1);
        assertTrue(d.getFirst() == 1);
    }

    @Test
    public void shouldRemoveSuccess() {
        DLList<Integer> d = new DLList<Integer>(1);
        d.addLast(2);
        d.addLast(3);
        d.remove(0);
        assertTrue(d.size() == 2);
        assertTrue(d.get(0) == 2);
        d.remove(0);
        assertTrue(d.size() == 1);
        assertTrue(d.get(0) == 3);
    }

    @Test
    public void shouldRemoveLastSuccess() {
        DLList<Integer> d = new DLList<Integer>(1);
        d.addLast(2);
        d.addLast(3);
        d.removeLast();
        assertTrue(d.size() == 2);
        assertTrue(d.get(1) == 2);
        d.removeLast();
        assertTrue(d.size() == 1);
        assertTrue(d.get(0) == 1);
    }
}

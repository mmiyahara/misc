package com.data_structure_java;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SLListTest {
    @Test
    public void shouldAddSuccess() {
        SLList<Integer> s = new SLList<Integer>();
        s.add(1, 0);
        s.add(2, 1);
        assertTrue(s.size() == 2);
        assertTrue(s.get(0) == 1);
        assertTrue(s.get(1) == 2);
    }

    @Test
    public void shouldAddFirstSuccess() {
        SLList<Integer> s = new SLList<Integer>();
        s.addFirst(1);
        s.addFirst(2);
        assertTrue(s.size() == 2);
        assertTrue(s.get(0) == 2);
        assertTrue(s.get(1) == 1);
    }

    @Test
    public void shouldAddLastSuccess() {
        SLList<Integer> s = new SLList<Integer>();
        s.addLast(1);
        s.addLast(2);
        assertTrue(s.size() == 2);
        assertTrue(s.get(0) == 1);
        assertTrue(s.get(1) == 2);
    }

    @Test
    public void shouldGetSuccess() {
        SLList<Integer> s = new SLList<Integer>(1);
        assertTrue(s.size() == 1);
        assertTrue(s.get(0) == 1);
        s.addLast(2);
        assertTrue(s.size() == 2);
        assertTrue(s.get(0) == 1);
        assertTrue(s.get(1) == 2);
    }

    @Test
    public void shouldGetFirstSuccess() {
        SLList<Integer> s = new SLList<Integer>(1);
        assertTrue(s.size() == 1);
        assertTrue(s.getFirst() == 1);
    }

    @Test
    public void shouldRemoveSuccess() {
        SLList<Integer> s = new SLList<Integer>(1);
        s.addLast(2);
        s.addLast(3);
        s.remove(0);
        assertTrue(s.size() == 2);
        assertTrue(s.get(0) == 2);
        s.remove(0);
        assertTrue(s.size() == 1);
        assertTrue(s.get(0) == 3);
    }

    @Test
    public void shouldRemoveLastSuccess() {
        SLList<Integer> s = new SLList<Integer>(1);
        s.addLast(2);
        s.addLast(3);
        s.removeLast();
        assertTrue(s.size() == 2);
        assertTrue(s.get(1) == 2);
        s.removeLast();
        assertTrue(s.size() == 1);
        assertTrue(s.get(0) == 1);
    }
}

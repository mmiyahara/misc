package com.data_structure_java;

public class SLList<T> implements List<T> {
    private class Node {
        private T value;
        private Node next;

        public Node() {
            value = null;
            next = null;
        }

        public Node(T v) {
            value = v;
            next = null;
        }
    }

    private Node sentinel = new Node();
    private int size = 0;

    public SLList() {
    }

    public SLList(T v) {
        Node node = new Node(v);
        sentinel.next = node;
        size += 1;
    }
    private boolean addHelper(int targetIndex, int currentIndex, Node addNode, Node prevNode) {
        if (targetIndex < 0 || targetIndex > size) {
            System.err.println("targetIndex(" + targetIndex + ") is out of range.");
            return false;
        }
        if (targetIndex == currentIndex) {
            Node tmp = prevNode.next;
            prevNode.next = addNode;
            prevNode.next.next = tmp;
            size += 1;
            return true;
        }
        currentIndex += 1;
        prevNode = prevNode.next;
        return addHelper(targetIndex, currentIndex, addNode, prevNode);
    }

    @Override
    public boolean add(T v, int index) {
        Node node = new Node(v);
        return addHelper(index, 0, node, sentinel);
    }

    @Override
    public void addFirst(T v) {
        add(v, 0);
    }

    @Override
    public void addLast(T v) {
        add(v, size);
    }

    private T getHelper(int targetIndex, int currentIndex, Node currentNode) {
        if (targetIndex < 0 || targetIndex >= size) {
            System.err.println("targetIndex(" + targetIndex + ") is out of range.");
            return null;
        }
        if (size == 0) {
            return null;
        }
        if (targetIndex == currentIndex) {
            return currentNode.next.value;
        }
        currentIndex += 1;
        currentNode = currentNode.next;
        return getHelper(targetIndex, currentIndex, currentNode);
    }

    @Override
    public T get(int index) {
        return getHelper(index, 0, sentinel);
    }

    @Override
    public T getFirst() {
        return getHelper(0, 0, sentinel);
    }

    private T removeHelper(int targetIndex, int currentIndex, Node currentNode) {
        if (size == 0 || targetIndex < 0 || targetIndex >= size) {
            System.err.println("targetIndex(" + targetIndex + ") is out of range.");
            return null;
        }
        if (targetIndex == currentIndex) {
            Node removedNode = currentNode.next;
            currentNode.next = currentNode.next.next;
            size -= 1;
            return removedNode.value;
        }
        currentIndex += 1;
        currentNode = currentNode.next;
        return removeHelper(targetIndex, currentIndex, currentNode);
    }

    @Override
    public T remove(int index) {
        return removeHelper(index, 0, sentinel);
    }

    @Override
    public T removeLast() {
        return removeHelper(size - 1, 0, sentinel);
    }

    @Override
    public int size() {
        return size;
    }
}

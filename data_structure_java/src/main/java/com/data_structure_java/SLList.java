package com.data_structure_java;

public class SLList<T> implements List<T> {
    private class Node {
        private T value;
        private Node nextNode;

        public Node() {
            value = null;
            nextNode = null;
        }

        public Node(T v) {
            value = v;
            nextNode = null;
        }
    }

    private Node sentinel = new Node();
    private int size = 0;

    public SLList() {
    }

    public SLList(T v) {
        sentinel.nextNode = new Node(v);
        size += 1;
    }

    private boolean addHelper(int targetIndex, int currentIndex, Node addNode, Node prevNode) {
        if (targetIndex < 0 || targetIndex > size) {
            System.err.println("targetIndex(" + targetIndex + ") is out of range.");
            return false;
        }
        if (targetIndex == currentIndex) {
            Node tmp = prevNode.nextNode;
            prevNode.nextNode = addNode;
            prevNode.nextNode.nextNode = tmp;
            size += 1;
            return true;
        }
        currentIndex += 1;
        prevNode = prevNode.nextNode;
        return addHelper(targetIndex, currentIndex, addNode, prevNode);
    }

    @Override
    public boolean add(T v, int index) {
        Node node = new Node(v);
        return addHelper(index, 0, node, sentinel);
    }

    @Override
    public void addFirst(T v) {
        Node node = new Node(v);
        addHelper(0, 0, node, sentinel);
    }

    @Override
    public void addLast(T v) {
        Node node = new Node(v);
        addHelper(size, 0, node, sentinel);
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
            return currentNode.nextNode.value;
        }
        currentIndex += 1;
        currentNode = currentNode.nextNode;
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
            Node removedNode = currentNode.nextNode;
            currentNode.nextNode = currentNode.nextNode.nextNode;
            size -= 1;
            return removedNode.value;
        }
        currentIndex += 1;
        currentNode = currentNode.nextNode;
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

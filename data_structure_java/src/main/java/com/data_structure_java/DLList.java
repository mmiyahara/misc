package com.data_structure_java;

public class DLList<T> implements List<T> {
    private class Node {
        private T value;
        private Node prev;
        private Node next;

        public Node() {
            value = null;
            prev = null;
            next = null;
        }

        public Node(T v) {
            value = v;
            prev = null;
            next = null;
        }
    }

    private Node sentinel = new Node();
    private int size = 0;

    public DLList() {
    }

    public DLList(T v) {
        Node node = new Node(v);
        sentinel.prev = node;
        sentinel.next = node;
        node.prev = sentinel;
        node.next = sentinel;
        size += 1;
    }

    /*
     * NOTE:
     * The time complexity of `addFirst` and `addLast` operations is O(1), which
     * means that these operations take constant time to execute, regardless of the
     * size of the data structure they are applied to. This makes them highly
     * efficient and desirable for use im many applications.
     */
    private boolean addHelper(int targetIndex, int currentIndex, Node addNode, Node prevNode) {
        if (targetIndex < 0 || targetIndex > size) {
            System.err.println("targetIndex(" + targetIndex + ") is out of range.");
            return false;
        }
        if (size == 0) {
            sentinel.next = addNode;
            sentinel.prev = addNode;
            addNode.prev = sentinel;
            addNode.next = sentinel;
            size += 1;
            return true;
        }
        if (targetIndex == size) {
            Node tmp = sentinel.prev;
            sentinel.prev = addNode;
            addNode.next = sentinel;
            addNode.prev = tmp;
            tmp.next = addNode;
            size += 1;
            return true;
        }
        if (targetIndex == currentIndex) {
            Node tmp = prevNode.next;
            prevNode.next = addNode;
            addNode.prev = prevNode;
            addNode.next = tmp;
            tmp.prev = addNode;
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
        Node node = new Node(v);
        addHelper(0, 0, node, sentinel);
    }

    @Override
    public void addLast(T v) {
        Node node = new Node(v);
        addHelper(size, 0, node, sentinel);
    }

    private T getHelper(int targetIndex, int currentIndex, Node prevNode) {
        if (targetIndex < 0 || targetIndex >= size) {
            System.err.println("targetIndex(" + targetIndex + ") is out of range.");
            return null;
        }
        if (size == 0) {
            return null;
        }
        if (targetIndex == currentIndex) {
            return prevNode.next.value;
        }
        currentIndex += 1;
        prevNode = prevNode.next;
        return getHelper(targetIndex, currentIndex, prevNode);
    }

    @Override
    public T get(int index) {
        return getHelper(index, 0, sentinel);
    }

    @Override
    public T getFirst() {
        return getHelper(0, 0, sentinel);
    }

    private T removeHelper(int targetIndex, int currentIndex, Node prevNode) {
        if (size == 0 || targetIndex < 0 || targetIndex >= size) {
            System.err.println("targetIndex(" + targetIndex + ") is out of range.");
            return null;
        }
        if (targetIndex == size - 1) {
            Node removedNode = sentinel.prev;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return removedNode.value;
        }
        if (targetIndex == currentIndex) {
            Node removedNode = prevNode.next;
            prevNode.next = prevNode.next.next;
            prevNode.next.prev = prevNode;
            size -= 1;
            return removedNode.value;
        }
        currentIndex += 1;
        prevNode = prevNode.next;
        return removeHelper(targetIndex, currentIndex, prevNode);
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

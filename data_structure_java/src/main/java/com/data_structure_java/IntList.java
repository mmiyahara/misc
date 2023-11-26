package com.data_structure_java;

public class IntList implements List<Integer> {
    private IntNode firstNode;
    private int size;

    private class IntNode {
        private Integer value;
        private IntNode nextNode;

        public IntNode() {
            value = null;
            nextNode = null;
        }

        public IntNode(int i) {
            value = i;
            nextNode = null;
        }
    }

    public IntList() {
        firstNode = new IntNode();
        size = 0;
    }

    public IntList(int i) {
        firstNode = new IntNode(i);
        size = 1;
    }

    /*
     * NOTE:
     * The `add` operation should insert a value prior to the value at
     * `targetIndex`.
     * This is likely one of the reasons why SLList and DLList with `sentinel` are
     * easier to implement than those without `sentinel`.
     */
    private boolean addHelper(int targetIndex, int currentIndex, IntNode addNode, IntNode currentNode,
            IntNode prevNode) {
        if (targetIndex < 0 || targetIndex > size) {
            System.err.println("Index " + targetIndex + " is out of range.");
            return false;
        }
        if (currentIndex == targetIndex) {
            if (targetIndex == 0) {
                IntNode tmp = firstNode;
                addNode.nextNode = tmp;
                firstNode = addNode;
            } else {
                IntNode tmp = prevNode.nextNode;
                prevNode.nextNode = addNode;
                addNode.nextNode = tmp;
            }
            size += 1;
            return true;
        }
        currentIndex += 1;
        prevNode = currentNode;
        currentNode = currentNode.nextNode;
        return addHelper(targetIndex, currentIndex, addNode, currentNode, prevNode);
    }

    @Override
    public boolean add(Integer e, int index) {
        IntNode node = new IntNode(e);
        return addHelper(index, 0, node, firstNode, null);
    }

    @Override
    public void addFirst(Integer e) {
        IntNode node = new IntNode(e);
        addHelper(0, 0, node, firstNode, null);
    }

    @Override
    public void addLast(Integer e) {
        IntNode node = new IntNode(e);
        addHelper(size, 0, node, firstNode, null);
    }

    private Integer getHelper(int targetIndex, int currentIndex, IntNode currentNode) {
        if (targetIndex < 0 || targetIndex >= size) {
            System.err.println("targetIndex(" + targetIndex + ") is out of range.");
            return null;
        }
        if (size == 0) {
            return null;
        }
        if (targetIndex == currentIndex) {
            return currentNode.value;
        }
        int nextIndex = currentIndex + 1;
        currentNode = currentNode.nextNode;
        return getHelper(targetIndex, nextIndex, currentNode);
    }

    @Override
    public Integer get(int index) {
        return getHelper(index, 0, firstNode);
    }

    @Override
    public Integer getFirst() {
        return getHelper(0, 0, firstNode);
    }

    private Integer removeHelper(int targetIndex, int currentIndex, IntNode currentNode, IntNode prevNode) {
        if (size == 0 || targetIndex < 0 || targetIndex > size) {
            System.err.println("Index " + targetIndex + " is out of range.");
            return null;
        }
        if (currentIndex == targetIndex) {
            IntNode removedNode = null;
            if (targetIndex == 0) {
                firstNode = firstNode.nextNode;
            } else {
                removedNode = currentNode;
                prevNode.nextNode = currentNode.nextNode;
            }
            size -= 1;
            return removedNode == null ? null : removedNode.value;
        }
        currentIndex += 1;
        prevNode = currentNode;
        currentNode = currentNode.nextNode;
        return removeHelper(targetIndex, currentIndex, currentNode, prevNode);
    }

    @Override
    public Integer remove(int index) {
        return removeHelper(index, 0, firstNode, null);
    }

    @Override
    public Integer removeLast() {
        return removeHelper(size - 1, 0, firstNode, null);
    }

    @Override
    public int size() {
        return size;
    }
}

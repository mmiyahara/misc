package com.data_structure_java;

public class IntList implements List<Integer> {
    private IntNode firstNode;
    private int size;

    private class IntNode {
        private int value;
        private IntNode nextNode;

        public IntNode(int i) {
            value = i;
            nextNode = null;
        }
    }

    public IntList() {
        firstNode = null;
        size = 0;
    }

    public IntList(int i) {
        firstNode = new IntNode(i);
        size = 1;
    }

    /*
     * NOTE:
     * It has to include `prevNode` as an argument because
     *
     * - `IntNode` only has `nextNode` prop. It does not have `prevNode` prop.
     * - `add` operation should insert a value previous to the value at
     * `targetIndex`.
     *
     * I guess this is one of the reasons why SLList and DLList are easier to
     * implement List.
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
        add(e, 0);
    }

    @Override
    public void addLast(Integer e) {
        IntNode lastNode = getHelper(size - 1, 0, firstNode);
        IntNode node = new IntNode(e);
        lastNode.nextNode = node;
        size += 1;
    }

    private IntNode getHelper(int targetIndex, int currentIndex, IntNode n) {
        if (targetIndex == currentIndex) {
            return n;
        }
        int nextIndex = currentIndex + 1;
        return getHelper(targetIndex, nextIndex, n.nextNode);
    }

    @Override
    public Integer get(int index) {
        IntNode node = getHelper(index, 0, firstNode);
        return node.value;
    }

    @Override
    public Integer getFirst() {
        return get(0);
    }

    private Integer removeHelper(int targetIndex, int currentIndex, IntNode currentNode, IntNode prevNode) {
        if (targetIndex < 0 || targetIndex > size) {
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
        return remove(size - 1);
    }

    @Override
    public int size() {
        return size;
    }
}

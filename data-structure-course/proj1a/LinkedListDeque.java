public class LinkedListDeque<T> {
  private class Node {
    public T item;
    public Node prev;
    public Node next;

    public Node(T i, Node p, Node n) {
      item = i;
      prev = p;
      next = n;
    }
  }

  private Node sentinel;
  private int size;

  /* Create an empty linked list deque. */
  public LinkedListDeque() {
    sentinel = new Node((T)null, null, null);
    sentinel.prev = sentinel;
    sentinel.next = sentinel;
    size = 0;
  }

  /* Create a deep copy of other */
  public LinkedListDeque(LinkedListDeque<T> other) {
    sentinel = new Node(null, null, null);
    sentinel.prev = sentinel;
    sentinel.next = sentinel;
    size = other.size;
    for (int i = 0; i < size; i++) {
      T item = other.get(i);
      sentinel.prev = new Node(item, sentinel.prev, sentinel);
      sentinel.prev.prev.next = sentinel.prev;
    }
  }

  /* Adds an item of type T to the front of the deque. */
  public void addFirst(T item) {
    sentinel.next = new Node(item, sentinel, sentinel.next);
    sentinel.next.next.prev = sentinel.next;
    size += 1;
  }

  /* Adds an item of type T to the back of the deque. */
  public void addLast(T item) {
    sentinel.prev = new Node(item, sentinel.prev, sentinel);
    sentinel.prev.prev.next = sentinel.prev;
    size += 1;
  }

  /*
   * Removes and returns the item at the front of the deque.
   * If no such item exists, returns null.
   */
  public T removeFirst() {
    if (isEmpty()) {
      return null;
    }
    Node first = sentinel.next;
    sentinel.next = sentinel.next.next;
    sentinel.next.prev = sentinel;
    first.prev = null;
    first.next = null;
    size -= 1;
    return first.item;
  }

  /*
   * Removes and returns the item at the back of the deque.
   * If no such item exists, returns null.
   */
  public T removeLast() {
    if (isEmpty()) {
      return null;
    }
    Node last = sentinel.prev;
    sentinel.prev = sentinel.prev.prev;
    sentinel.prev.next = sentinel;
    last.prev = null;
    last.next = null;
    size -= 1;
    return last.item;
  }

  // Returns true if deque is empty, false otherwise.
  public boolean isEmpty() {
    return sentinel.next == sentinel;
  }

  /*
   * Gets the item at the given index, where 0 is the front, 1 is the next item,
   * and so forth. If no such item exists, returns null. Must not alter the deque!
   */
  public T get(int index) {
    Node p = sentinel;
    for (int i = 0; i <= index; i++) {
      p = p.next;
      if (p == sentinel) {
        return null;
      }
    }
    return p.item;
  }

  private T getRecursive(Node n, int i) {
    if (i == 0) {
      return n.item;
    }
    if (n == sentinel) {
      return null;
    }
    return getRecursive(n.next, i - 1);
  }

  // Same as get, but uses recursion.
  public T getRecursive(int index) {
    if (index >= size || index < 0) {
      return null;
    }
    return getRecursive(sentinel.next, index);
  }

  // Returns the number of items in the deque.
  public int size() {
    return size;
  }

  private void printDeque(Node n) {
    if (n.next == sentinel) {
      System.out.println();
      return;
    }
    System.out.print(n.next.item + " ");
    printDeque(n.next);
  }

  /*
   * Prints the items in the deque from first to last, separated by a space. Once
   * all the items have been printed, print out a new line.
   */
  public void printDeque() {
    printDeque(sentinel);
  }

  public static void main(String[] args) {
    LinkedListDeque<Integer> l = new LinkedListDeque<>();
    l.addFirst(2);
    l.addFirst(1);
    l.printDeque();
    System.out.println(l.get(1));
    LinkedListDeque<Integer> m = new LinkedListDeque<>(l);
    m.printDeque();
  }
}

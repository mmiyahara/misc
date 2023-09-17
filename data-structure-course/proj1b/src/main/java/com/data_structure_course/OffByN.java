package com.data_structure_course;

public class OffByN implements CharacterComparator {
  private int offset;

  public OffByN(int n) {
    offset = n;
  }

  /*
   * Return true for characters that are off by n.
   * 
   * For example, the following calls should return true.
   *
   * OffByN offBy5 = new OffByN(5);
   * offBy5.equalChars('a', 'f'); // true
   * offBy5.equalChars('f', 'a'); // true
   *
   * The following calls should return false.
   *
   * offBy5.equalChars('f', 'h'); // false
   *
   */
  @Override
  public boolean equalChars(char x, char y) {
    return x - y == offset || y - x == offset;
  }

}

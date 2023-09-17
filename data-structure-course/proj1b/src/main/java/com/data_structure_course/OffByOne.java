package com.data_structure_course;

public class OffByOne implements CharacterComparator {

  /*
   * Return true if the characters are different by exactly one.
   * 
   * For example, the following calls should return true.
   *
   * OffByOne obo = new OffByOne();
   * obo.equalChars('a', 'b'); // true
   * obo.equalChars('b', 'a'); // true
   * obo.equalChars('r', 'q'); // true
   *
   * However, the three calls below should return false:
   * 
   * obo.equalChars('a', 'e'); // false
   * obo.equalChars('z', 'a'); // false
   * obo.equalChars('a', 'a'); // false
   * 
   */
  @Override
  public boolean equalChars(char x, char y) {
    return x - y == 1 || y - x == 1;
  }
}

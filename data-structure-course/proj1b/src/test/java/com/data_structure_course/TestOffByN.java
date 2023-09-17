package com.data_structure_course;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
  @Test
  public void TestEqualCharsZero() {
    CharacterComparator offByZero = new OffByN(0);
    assertTrue(offByZero.equalChars('a', 'a'));
    assertFalse(offByZero.equalChars('a', 'e'));
  }

  @Test
  public void TestEqualCharsOne() {
    CharacterComparator offByOne = new OffByN(1);
    assertTrue(offByOne.equalChars('a', 'b'));
    assertTrue(offByOne.equalChars('b', 'a'));
    assertFalse(offByOne.equalChars('a', 'a'));
    assertFalse(offByOne.equalChars('a', 'e'));
  }

  @Test
  public void TestEqualCharsTwo() {
    CharacterComparator offByTwo = new OffByN(2);
    assertTrue(offByTwo.equalChars('a', 'c'));
    assertTrue(offByTwo.equalChars('c', 'a'));
    assertFalse(offByTwo.equalChars('a', 'e'));
  }
}

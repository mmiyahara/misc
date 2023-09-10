package com.data_structure_course;

public class Palindrome {
  /*
   * Given a string, return a Deque where the characters appear in the same order
   * as in the String. For example, if the word is "persiflage", then the returned
   * Deque should have 'p' at the front, followed by 'e', and so forth.
   */
  public Deque<Character> wordToDeque(String word) {
    Deque<Character> deque = new LinkedListDeque<>();
    return wordToDeque(word, deque);
  }

  /* Helper function which adds each character of the word to a deque and return it. */
  private Deque<Character> wordToDeque(String word, Deque<Character> d) {
    int nextIndex = d.size();
    if (nextIndex == word.length()) {
      return d;
    }
    d.addLast(word.charAt(nextIndex));
    return wordToDeque(word, d);
  }
}

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

  /*
   * Helper function to return a deque where the characters appear in the same
   * order as in the `word`.
   */
  private Deque<Character> wordToDeque(String word, Deque<Character> d) {
    int nextIndex = d.size();
    if (nextIndex == word.length()) {
      return d;
    }
    d.addLast(word.charAt(nextIndex));
    return wordToDeque(word, d);
  }

  /*
   * Given a string, return true if it is a palindrome. Otherwise, return false.
   *
   * A palindrome is defined as a word that is the same whether it is read
   * forwards or backwards.
   *
   * For example, "a", "racecar", and "noon" are all palindromes.
   * "horse", "rancor", and "aaaaab" are not palindromes.
   * Any word of length 1 or 0 is a palindrome.
   * 
   * 'A' and 'a' should not be considered equal.
   */
  public boolean isPalindrome(String word) {
    Deque<Character> d = this.wordToDeque(word);
    return isPalindrome(d);
  }

  /*
   * Helper function to see if a word is palindrome.
   *
   * Take the first character and the last character from a deque.
   * If the characters are not the same, the word is not a palindrome.
   *
   * If the above can be repeated until the length of the deque is 0 or 1,
   * then the word is a palindrome.
   */
  private boolean isPalindrome(Deque<Character> d) {
    if (d.size() == 0 || d.size() == 1) {
      return true;
    }

    Character first = d.removeFirst();
    Character last = d.removeLast();
    if (first != last) {
      return false;
    }

    return isPalindrome(d);
  }

  /*
   * Given a string and the CharacterComparator passed in as argument `cc`,
   * return true if the word is a palindrome according to the character comparison
   * test provided by `cc`.
   */
  public boolean isPalindrome(String word, CharacterComparator cc) {
    Deque<Character> d = this.wordToDeque(word);
    return isPalindrome(d, cc);
  }

  private boolean isPalindrome(Deque<Character> d, CharacterComparator cc) {
    if (d.size() == 0 || d.size() == 1) {
      return true;
    }

    Character first = d.removeFirst();
    Character last = d.removeLast();
    if (!cc.equalChars(last, first)) {
      return false;
    }

    return isPalindrome(d, cc);
  }

}

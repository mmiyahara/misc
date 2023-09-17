package com.data_structure_course;

/**
 * This class outputs all palindromes in the words file in the current
 * directory.
 */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("words.txt");
        CharacterComparator offByN = new OffByN(3);
        Palindrome palindrome = new Palindrome();

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word, offByN)) {
                System.out.println(word);
            }
        }
    }
}

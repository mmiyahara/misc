package com.data_structure_course;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindromeLengthZero() {
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void testIsPalindromeLengthOne() {
        assertTrue(palindrome.isPalindrome("a"));
    }

    @Test
    public void testIsPalindrome() {
        String[] palindromes = {
                "anna", "civic", "deed", "deified", "kayak", "level", "madam", "minim", "noon", "peep", "poop", "radar",
                "redder", "refer", "repaper", "reviver", "rotator", "rotor", "sagas", "sees", "sexes", "shahs", "tenet",
                "toot"
        };
        for (int i = 0; i < palindromes.length; i++) {
            assertTrue(palindrome.isPalindrome(palindromes[i]));
        }
        assertFalse(palindrome.isPalindrome("horse"));
    }

    @Test
    public void testIsPalindromeOffByOneLengthZero() {
        CharacterComparator obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("", obo));
    }

    @Test
    public void testIsPalindromeOffByOneLengthOne() {
        CharacterComparator obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("a", obo));
    }

    @Test
    public void testIsPalindromeOffByOne() {

        CharacterComparator obo = new OffByOne();

        String[] palindromes = {
                "climb", "done", "dope", "edged", "fiche", "flake", "flame", "fluke", "irish", "often", "onion",
                "porno", "reads", "reds", "reeds", "reefs", "refs", "remands", "reminds", "rends", "romps", "runts",
                "rusts", "ruts", "sight", "smelt", "spoor", "spot", "stout", "strut", "tabs", "teds", "tends", "this",
                "thongs", "thugs", "tons", "tops", "towns", "tress", "truss", "tufts", "tumults", "tutu" };
        for (int i = 0; i < palindromes.length; i++) {
            assertTrue(palindrome.isPalindrome(palindromes[i], obo));
        }

        assertFalse(palindrome.isPalindrome("noon", obo));
    }

    @Test
    public void testIsPalindromeOffByNLengthZero() {
        CharacterComparator obn = new OffByN(3);
        assertTrue(palindrome.isPalindrome("", obn));
    }

    @Test
    public void testIsPalindromeOffByNLengthOne() {
        CharacterComparator obn = new OffByN(3);
        assertTrue(palindrome.isPalindrome("a", obn));
    }

    @Test
    public void testIsPalindromeOffByN() {

        CharacterComparator obn = new OffByN(3);

        String[] palindromes = {
                "absolved", "bade", "barcode", "bible", "bile", "bole", "bore", "borehole", "chef", "chief", "drug",
                "gold", "gourd", "guard", "hole", "ideal", "kiln", "pads", "pills", "polls", "pools", "pours", "pros",
                "purrs", "purveyors", "quart", "quern", "sheep", "slip", "sloop", "slop", "threw", "verbs", "vials",
                "vilify", "whet", "wilt"
        };
        for (int i = 0; i < palindromes.length; i++) {
            assertTrue(palindrome.isPalindrome(palindromes[i], obn));
        }

        assertFalse(palindrome.isPalindrome("noon", obn));
    }

}

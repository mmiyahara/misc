package com.data_structure_java;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void shouldIsLargerTrue() {
        App app = new App();
        assertTrue(app.isLarger(1, 0));
    }
}

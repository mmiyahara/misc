package com.data_structure_java;

/**
 * Hello world!
 *
 */
public class App {

    public App() {
    }

    public boolean isLarger(int x, int y) {
        return x > y;
    }

    public static void main(String[] args) {
        App app = new App();
        int x = 1;
        int y = 0;
        if (app.isLarger(x, y)) {
            System.out.println(x + " is larger than " + y);
        }

    }
}

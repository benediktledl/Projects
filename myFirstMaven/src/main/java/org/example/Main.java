package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("Starting program...");
        greetUser();
        int result = multiplyNumbers(2, 3);
        System.out.println("The result of multiplying 2 and 3 is " + result);
        String reversed = reverseString("hello world");
        System.out.println("The reversed string is " + reversed);
        System.out.println("Program complete.");
    }

    public static void greetUser() {
        System.out.println("Hello, user!");
        logger.info("Greeted user");
    }

    public static int multiplyNumbers(int x, int y) {
        logger.info("Multiplying " + x + " and " + y + "...");
        int result = x * y;
        return result;
    }

    public static String reverseString(String str) {
        logger.info("Reversing string...");
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }
}

package main.java.core;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GameConstants {
    public static final Scanner SCANNER = new Scanner(System.in, StandardCharsets.UTF_8);

    public static final int MAX_ERROR = 7;
    public static final String MASK_SYMBOL = "_";
    public static final char START = 'Н';
    public static final char EXIT = 'В';
}

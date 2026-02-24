package main.java.core;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GameConstants {
    public static final Scanner SCANNER = new Scanner(System.in, StandardCharsets.UTF_8);

    public static final int MAX_ERROR = 7;
    public static final String MASK_SYMBOL = "_";
    public static final String ONE_RUS_LETTER_REGEX = "[а-яёА-ЯЁ]";
    public static final Pattern ONE_RUS_LETTER_PATTERN = Pattern.compile(ONE_RUS_LETTER_REGEX);
    public static final String START = "Н";
    public static final String EXIT = "В";
}

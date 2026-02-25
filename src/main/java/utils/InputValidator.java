package main.java.utils;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InputValidator {
    public static final Scanner SCANNER = new Scanner(System.in, StandardCharsets.UTF_8);
    private static final String ONE_RUS_LETTER_REGEX = "[а-яёА-ЯЁ]";
    private static final Pattern ONE_RUS_LETTER_PATTERN = Pattern.compile(ONE_RUS_LETTER_REGEX);

    private InputValidator() {}

    public static char inputRussianLetter() {
        System.out.println("Попробуй ввести букву:");
        while (true) {
            String inputValue = SCANNER.nextLine();
            if (isOneRussianLetter(inputValue)) {
                return inputValue.charAt(0);
            }
            System.out.println("Некорректный ввод! Введи одну букву кириллицы.");
        }
    }

    public static boolean isOneRussianLetter(String inputValue) {
        Matcher matcher = ONE_RUS_LETTER_PATTERN.matcher(inputValue);
        return matcher.matches();
    }
}
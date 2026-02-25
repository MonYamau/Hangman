package main.java.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.java.core.GameConstants.SCANNER;
import static main.java.core.GameMessages.printIncorrectInput;

public class InputValidator {
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
            printIncorrectInput();
        }
    }

    public static boolean isOneRussianLetter(String inputValue) {
        Matcher matcher = ONE_RUS_LETTER_PATTERN.matcher(inputValue);
        return matcher.matches();
    }
}
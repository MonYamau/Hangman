package main.java;

import main.java.util.HangmanRenderer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    private static final Scanner SCANNER = new Scanner(System.in, StandardCharsets.UTF_8);
    private static final Path PATH = Path.of("/resources/dictionary.txt").toAbsolutePath();

    private static final int MAX_ERROR = 7;
    private static final String MASK_SYMBOL = "_";
    private static final String ONE_RUS_LETTER_REGEX = "[а-яёА-ЯЁ]";
    private static final Pattern ONE_RUS_LETTER_PATTERN = Pattern.compile(ONE_RUS_LETTER_REGEX);
    private static final String START = "Н";
    private static final String EXIT = "В";
    private static final String INSTRUCTIONS_SCRIPT = """
            
            Желаешь начать новую игру?
            [Введите '%s', чтобы начать новую игру]
            [Введите '%s', чтобы выйти]
            """.formatted(START, EXIT);
    private static final String INCORRECT_INPUT_SCRIPT = "Некорректный ввод! Введи одну букву кириллицы.";

    private static String secretWord;
    private static int mistakeCount;
    private static StringBuilder displayField;
    private static String usedLetters;


    public static void main(String[] args) {
        System.out.printf("Привет! " + INSTRUCTIONS_SCRIPT);
        processStartChoice();
    }

    private static void processStartChoice() {
        while (true) {
            switch (SCANNER.nextLine().toUpperCase()) {
                case START:
                    startGameRound();
                    break;
                case EXIT:
                    return;
                default:
                    System.out.println(INCORRECT_INPUT_SCRIPT);
            }
        }
    }

    private static void startGameRound() {
        secretWord = generateWord();
        mistakeCount = 0;
        displayField = new StringBuilder(makeMask());
        usedLetters = "";
        System.out.printf("Начинаем! Загадано слово из %d букв.\n", secretWord.length());
        System.out.printf("[%s]\n", displayField);
        gameLoop();
        printResultGameRound();
    }

    private static String generateWord() {
        Random random = new Random();
        List<String> dictionary;
        try {
            dictionary = Arrays.asList((Files.readString(PATH)).split(" "));
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't read the file: " + PATH.toAbsolutePath());
        }
        int randomIndex = random.nextInt(dictionary.size());
        return dictionary.get(randomIndex);
    }

    private static String makeMask() {
        return MASK_SYMBOL.repeat(secretWord.length()).trim();
    }

    private static void gameLoop() {
        while (!isGameOver()) {
            char letter = inputRussianLetter();
            letter = Character.toUpperCase(letter);
            if (!isUsedLetter(letter)) {
                addUsedLetters(letter);
                if (isSecretWordLetter(letter)) {
                    openLetter(letter);
                    showGameRoundStatus();
                } else {
                    mistakeCount++;
                    printMistakeLetterMessage(letter);
                    showGameRoundStatus();
                }
            } else {
                printUsedLetterMessage();
            }
        }
    }

    private static char inputRussianLetter() {
        System.out.println("Попробуй ввести букву:");
        while (true) {
            String inputValue = SCANNER.nextLine();
            if (isOneRussianLetter(inputValue)) {
                return inputValue.charAt(0);
            }
            System.out.println(INCORRECT_INPUT_SCRIPT);
        }
    }

    private static boolean isOneRussianLetter(String inputValue) {
        Matcher matcher = ONE_RUS_LETTER_PATTERN.matcher(inputValue);
        return matcher.matches();
    }

    private static boolean isUsedLetter(char letter) {
        return usedLetters.contains(String.valueOf(letter));
    }

    private static void addUsedLetters(char letter) {
        usedLetters = (usedLetters + " " + letter).trim();
    }

    private static void printUsedLetterMessage() {
        System.out.println("Буква уже была использована!");
    }

    private static void printMistakeLetterMessage(char letter) {
        System.out.printf("Буквы '%s' нет в данном слове.\n", letter);
    }

    private static boolean isSecretWordLetter(char letter) {
        String s = String.valueOf(letter);
        return secretWord.contains(s);
    }

    private static void openLetter(char letter) {
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letter) {
                displayField.setCharAt(i, letter);
            }
        }
    }

    private static void showGameRoundStatus() {
        System.out.printf("Использованные буквы: [%s]\n", usedLetters);
        System.out.println("Количество ошибок: " + mistakeCount);
        System.out.printf("[%s]\n", displayField);
        HangmanRenderer.printHangman(mistakeCount);
    }

    private static boolean isGameOver() {
        return isLose() || isWin();
    }

    private static boolean isWin() {
        return !displayField.toString().contains(MASK_SYMBOL);
    }

    private static boolean isLose() {
        return mistakeCount == MAX_ERROR;
    }

    private static void printResultGameRound() {
        if (isWin()) {
            printWinMessage();
        } else {
            printLoseMessage();
        }
        System.out.println("Загаданное слово: " + secretWord);
        System.out.println(INSTRUCTIONS_SCRIPT);
    }

    private static void printWinMessage() {
        System.out.println("ТЫ ВЫИГРАЛ! ^О^");
    }

    private static void printLoseMessage() {
        System.out.println("Ты проиграл Х_Х");
    }
}
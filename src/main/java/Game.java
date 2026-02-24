package main.java;

import main.java.utils.HangmanRenderer;
import main.java.utils.Dictionary;

import static main.java.core.GameConstants.*;
import static main.java.core.GameMessages.*;

import java.io.UncheckedIOException;
import java.util.*;
import java.util.regex.Matcher;

public class Game {
    private static String secretWord;
    private static int mistakeCount;
    private static StringBuilder displayField;
    private static String usedLetters;

    public static void main(String[] args) {
        System.out.printf("Привет! " + INSTRUCTIONS_SCRIPT);
        try {
            processStartChoice();
        } catch (UncheckedIOException e) {
            System.err.println("Ошибка чтения файла. Работа программы прекращена.");
            System.err.println(e.getMessage());
        }
    }

    private static void processStartChoice() {
        while (true) {
            String choice = SCANNER.nextLine().toUpperCase();
            if (choice.equals(START)) {
                startGameRound();
                continue;
            }
            if (choice.equals(EXIT)) {
                return;
            }
            System.out.println(INCORRECT_INPUT_SCRIPT);
        }
    }

    private static void startGameRound() {
        secretWord = Dictionary.generateWord();
        mistakeCount = 0;
        displayField = new StringBuilder(makeMask());
        usedLetters = "";
        System.out.printf("Начинаем! Загадано слово из %d букв.\n", secretWord.length());
        System.out.printf("[%s]\n", displayField);
        gameLoop();
        printResultGameRound();
    }

    private static String makeMask() {
        return MASK_SYMBOL.repeat(secretWord.length());
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
}
package main.java;

import main.java.utils.HangmanRenderer;
import main.java.utils.Dictionary;

import static main.java.core.GameConstants.*;
import static main.java.core.GameMessages.*;
import static main.java.core.InputValidator.*;

import java.io.UncheckedIOException;
import java.util.*;

public class Game {
    private static String secretWord;
    private static int mistakeCount;
    private static StringBuilder displayField;
    private static String usedLetters;

    public static void main(String[] args) {
        System.out.println("Привет!");
        printInstructions();
        try {
            processStartChoice();
        } catch (UncheckedIOException e) {
            System.err.println("Ошибка чтения файла. Работа программы прекращена.");
            System.err.println(e.getMessage());
        }
    }

    private static void processStartChoice() {
        while (true) {
            char choice = inputRussianLetter();
            choice = Character.toUpperCase(choice);
            if (choice == START) {
                startGameRound();
                continue;
            }
            if (choice == EXIT) {
                return;
            }
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
        printInstructions();
    }
}
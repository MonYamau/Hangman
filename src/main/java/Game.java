package main.java;

import main.java.util.HangmanRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.System.exit;

public class Game {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final Path PATH = Paths.get("src/main/resources/dictionary.txt");

    private static final int MAX_ERROR = 7;
    private static final String ACCEPTABLE_LETTERS = "[а-яёА-ЯЁ]+";
    private static final String START = "Н";
    private static final String EXIT = "В";
    private static final String INSTRUCTIONS_SCRIPT = """
            
            Желаешь начать новую игру?
            [Введите "Н", чтобы начать новую игру]
            [Введите "В", чтобы выйти]
            """;
    private static final String INCORRECT_INPUT_SCRIPT = "Некорректный ввод! Введи одну букву кириллицы.";

    private static String secretWord;
    private static int mistakeCount;
    private static String displayField;
    private static String usedLetters;


    public static void main(String[] args) {
        System.out.printf("Привет! %s", INSTRUCTIONS_SCRIPT);
        processStartChoice();
    }

    private static void processStartChoice() {
        while (true) {
            switch (INPUT.nextLine().toUpperCase()) {
                case START:
                    startGameRound();
                case EXIT:
                    exit(0);
                default:
                    System.out.println(INCORRECT_INPUT_SCRIPT);
            }
        }
    }

    private static void startGameRound() {
        secretWord = generateWord();
        mistakeCount = 0;
        displayField = "_ ".repeat(secretWord.length()).trim();
        usedLetters = "";
        System.out.printf("Начинаем! Загадано слово из %d букв.\n", secretWord.length());
        System.out.printf("[%s]\n", displayField);

        while (!isGameOver()) {
            System.out.println("Попробуй ввести букву:");
            char letter = validateInputLetter();
            if (!isUsedLetter(letter)) {
                usedLetters = (usedLetters + " " + letter).trim();
                incrementErrorCount(letter);
                openLetter(letter);
                System.out.printf("Использованные буквы: [%s]\n", usedLetters);
                System.out.printf("Количество ошибок: %d\n", mistakeCount);
                System.out.printf("[%s]\n", displayField);
                HangmanRenderer.printHangman(mistakeCount);
            } else {
                System.out.println("Буква уже была использована!");
            }
        }
    }

    private static String generateWord() {
        Random random = new Random();
        List<String> dictionaryWords;
        try {
            dictionaryWords = Arrays.asList((Files.readString(PATH)).split(" "));
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't read the file: " + PATH.toAbsolutePath(), e);
        }
        int randomIndex = random.nextInt(dictionaryWords.size());
        return dictionaryWords.get(randomIndex);
    }

    private static char validateInputLetter() {
        String inputValue = null;
        boolean validate = false;
        while (!validate) {
            validate = true;
            inputValue = INPUT.nextLine();
            boolean isRus = Pattern.matches(ACCEPTABLE_LETTERS, inputValue);
            if (inputValue.length() != 1 || !isRus) {
                validate = false;
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println(INCORRECT_INPUT_SCRIPT);
                }
            }
            inputValue = inputValue.toUpperCase();
        }
        char letter = inputValue.charAt(0);
        return letter;
    }

    private static boolean isUsedLetter(char letter) {
        return usedLetters.contains(String.valueOf(letter));
    }

    private static void incrementErrorCount(char letter) {
        if (!secretWord.contains(String.valueOf(letter))) {
            System.out.printf("Буквы \"%s\" нет в данном слове.\n", letter);
            mistakeCount++;
        }
    }

    private static void openLetter(char letter) {
        List<String> iterateWord = new ArrayList<>(Arrays.asList(secretWord.split("")));
        List<String> iterateField = new ArrayList<>(Arrays.asList(displayField.split(" ")));
        for (int i = 0; i < secretWord.length(); i++) {
            if (iterateWord.get(i).equals(String.valueOf(letter))) {
                iterateField.set(i, String.valueOf(letter));
            }
        }
        displayField = String.join(" ", iterateField);
    }

    private static boolean isGameOver() {
        if (mistakeCount == MAX_ERROR) {
            System.out.printf("Ты проиграл Х_Х\nЗагаданное слово: %s\n" + INSTRUCTIONS_SCRIPT, secretWord);
            processStartChoice();
        }
        if (!displayField.contains("_")) {
            System.out.printf("ТЫ ВЫИГРАЛ! ^О^\nЗагаданное слово: %s\n" + INSTRUCTIONS_SCRIPT, secretWord);
            processStartChoice();
        }
        return false;
    }
}
package main.java;

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
    private static final String INSTRUCTIONS_SCRIPT = """

            Желаешь начать новую игру?
            [Введите "Н", чтобы приступить к игре]
            [Введите "В", чтобы отменить]
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

    private static void processStartChoice(){
        while (true) {
            switch (INPUT.nextLine().toUpperCase()) {
                case "Н":
                    startGameRound();
                case "В":
                    exit(0);
                default:
                    System.out.println(INCORRECT_INPUT_SCRIPT);
            }
        }
    }

    private static void startGameRound(){
        secretWord = generateWord();
        mistakeCount = 0;
        displayField = "_ ".repeat(secretWord.length()).trim();
        usedLetters = "";
        System.out.printf("Начинаем! Загадано слово из %d букв.\n", secretWord.length());
        System.out.printf("[%s]\n", displayField);

        while (!isGameOver()) {
            System.out.println("Попробуй ввести букву:");
            String letter = validateInputLetter();
            processGuessedLetter(letter);
            checkVictory();
        }
    }

    private static String generateWord(){
        Random random = new Random();
        List<String> dictionaryWords;
        try {
            dictionaryWords = Arrays.asList((Files.readString(PATH)).split(" "));
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't read the file: " + PATH, e);
        }
        int randomIndex = random.nextInt(dictionaryWords.size());
        return dictionaryWords.get(randomIndex);
    }

    private static String validateInputLetter(){
        String letter = null;
        boolean validate = false;
        while (!validate) {
            validate = true;
            letter = INPUT.nextLine();
            boolean isRus = Pattern.matches(ACCEPTABLE_LETTERS, letter);
            if (letter.length() != 1 || !isRus) {
                validate = false;
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println(INCORRECT_INPUT_SCRIPT);
                }
            }
            letter = letter.toUpperCase();
        }
        return letter;
    }

    private static void processGuessedLetter(String letter){
        if (!usedLetters.contains(letter)) {
            usedLetters = (usedLetters + " " + letter).trim();
            incrementErrorCount(letter);
            openLetter(letter);
            System.out.printf("Использованные буквы: [%s]\n", usedLetters);
            System.out.printf("Количество ошибок: %d\n", mistakeCount);
            System.out.printf("[%s]\n", displayField);
            new HangmanRenderer().printHangman(mistakeCount);
        } else {
            System.out.println("Буква уже была использована!");
        }
    }

    private static void incrementErrorCount(String letter){
        if (!secretWord.contains(letter)) {
            System.out.printf("Буквы \"%s\" нет в данном слове.\n", letter);
            mistakeCount++;
        }
    }

    private static void openLetter(String letter){
        List<String> iterateWord = new ArrayList<>(Arrays.asList(secretWord.split("")));
        List<String> iterateField = new ArrayList<>(Arrays.asList(displayField.split(" ")));
        for (int i = 0; i < secretWord.length(); i++) {
            if (iterateWord.get(i).equals(letter)) {
                iterateField.set(i, letter);
            }
        }
        displayField = String.join(" ", iterateField);
    }

    private static void checkVictory(){
        if (!displayField.contains("_")) {
            System.out.printf("ТЫ ВЫИГРАЛ! ^О^\nЗагаданное слово: %s\n" + INSTRUCTIONS_SCRIPT, secretWord);
            processStartChoice();
        }
    }

    private static boolean isGameOver(){
        if (mistakeCount == MAX_ERROR) {
            System.out.printf("Ты проиграл Х_Х\nЗагаданное слово: %s\n" + INSTRUCTIONS_SCRIPT, secretWord);
            processStartChoice();
        }
        return false;
    }
}
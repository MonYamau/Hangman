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

    private static String word;
    private static int errorCounter;
    private static String field;
    private static String attemptList;



    public static void main(String[] args) {
        System.out.printf("Привет! %s", INSTRUCTIONS_SCRIPT);
        callInputScript();
    }

    private static void callInputScript(){
        while (true) {
            switch (INPUT.nextLine().toUpperCase()) {
                case "Н":
                    gameLoop();
                case "В":
                    exit(0);
                default:
                    System.out.println(INCORRECT_INPUT_SCRIPT);
            }
        }
    }

    private static void gameLoop(){
        word = generateWord();
        errorCounter = 0;
        field = "_ ".repeat(word.length()).trim();
        attemptList = "";
        System.out.printf("Начинаем! Загадано слово из %d букв.\n", word.length());
        System.out.printf("[%s]\n", field);

        while (!isGameOver()) {
            System.out.println("Попробуй ввести букву:");
            String letter = validateInput();
            checkLetter(letter);
            achieveVictory();
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

    private static String validateInput(){
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

    private static void checkLetter(String letter){
        if (!attemptList.contains(letter)) {
            attemptList = (attemptList + " " + letter).trim();
            errorTrack(letter);
            openLetter(letter);
            System.out.printf("Использованные буквы: [%s]\n", attemptList);
            System.out.printf("Количество ошибок: %d\n", errorCounter);
            System.out.printf("[%s]\n", field);
            new HangmanRenderer().printHangman(errorCounter);
        } else {
            System.out.println("Буква уже была использована!");
        }
    }

    private static void errorTrack(String letter){
        if (!word.contains(letter)) {
            System.out.printf("Буквы \"%s\" нет в данном слове.\n", letter);
            errorCounter++;
        }
    }

    private static void openLetter(String letter){
        List<String> iterateWord = new ArrayList<>(Arrays.asList(word.split("")));
        List<String> iterateField = new ArrayList<>(Arrays.asList(field.split(" ")));
        for (int i = 0; i < word.length(); i++) {
            if (iterateWord.get(i).equals(letter)) {
                iterateField.set(i, letter);
            }
        }
        field = String.join(" ", iterateField);
    }

    private static void achieveVictory(){
        if (!field.contains("_")) {
            System.out.printf("ТЫ ВЫИГРАЛ! ^О^\nЗагаданное слово: %s\n" + INSTRUCTIONS_SCRIPT, word);
            callInputScript();
        }
    }

    private static boolean isGameOver(){
        if (errorCounter == MAX_ERROR) {
            System.out.printf("Ты проиграл Х_Х\nЗагаданное слово: %s\n" + INSTRUCTIONS_SCRIPT, word);
            callInputScript();
        }
        return false;
    }
}
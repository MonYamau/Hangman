package main.java.core;

public class GameMessages {
    public static void printUsedLetterMessage() {
        System.out.println("Буква уже была использована!");
    }

    public static void printMistakeLetterMessage(char letter) {
        System.out.printf("Буквы '%s' нет в данном слове.\n", letter);
    }

    public static void printWinMessage() {
        System.out.println("ТЫ ВЫИГРАЛ! ^О^");
    }

    public static void printLoseMessage() {
        System.out.println("Ты проиграл Х_Х");
    }
}

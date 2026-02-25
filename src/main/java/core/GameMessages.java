package main.java.core;

import static main.java.core.GameConstants.EXIT;
import static main.java.core.GameConstants.START;

public final class GameMessages {
    private GameMessages() {}

    public static void printInstructions(){
        System.out.printf("""
                
                Желаешь начать новую игру?
                [Введите '%s', чтобы начать новую игру]
                [Введите '%s', чтобы выйти]
                %n""", START, EXIT);
    }

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

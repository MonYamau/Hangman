package main.java;
import static main.java.core.GameMessages.*;
import static main.java.core.GameRound.processStartChoice;

import java.io.UncheckedIOException;

public class Main {

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
}
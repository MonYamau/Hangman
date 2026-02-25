package main.java.utils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class Dictionary {
    private static final Path PATH = Path.of("resources/dictionary.txt").toAbsolutePath();

    private Dictionary() {}

    public static String generateWord() {
        Random random = new Random();
        List<String> dictionary;
        try {
            dictionary = Arrays.asList((Files.readString(PATH)).split(" "));
        } catch (IOException e) {
            throw new UncheckedIOException("Couldn't read the file: " + PATH.toAbsolutePath(), e);
        }
        int randomIndex = random.nextInt(dictionary.size());
        return dictionary.get(randomIndex);
    }
}

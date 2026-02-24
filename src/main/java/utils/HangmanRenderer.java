package main.java.utils;

public final class HangmanRenderer {
    private HangmanRenderer() {}

    private static final String[] HANGMAN_STATUS = {
            """
                        _________
                        |
                        |
                        |
                        |
                        |___________""",

            """
                        _________
                        |     |
                        |
                        |
                        |
                        |___________""",
            """
                        _________
                        |     |
                        |     O
                        |
                        |
                        |___________""",
            """
                        _________
                        |     |
                        |     O
                        |     |
                        |
                        |___________""",
            """
                        _________
                        |     |
                        |     O
                        |    /|
                        |
                        |___________""",
            """
                        _________
                        |     |
                        |     O
                        |    /|\\
                        |
                        |___________""",
            """
                        _________
                        |     |
                        |     O
                        |    /|\\
                        |    /
                        |___________""",
            """
                        _________
                        |     |
                        |     O
                        |    /|\\
                        |    / \\
                        |___________"""
    };

    public static void printHangman(int numPicture) {
        System.out.println(HANGMAN_STATUS[numPicture]);
    }
}

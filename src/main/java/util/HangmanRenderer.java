package main.java.util;

public final class HangmanRenderer {
    private HangmanRenderer() {}

    private static final String[] hangmanStatus = {
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
                        |     /
                        |___________""",
            """
                        _________
                        |     |
                        |     O
                        |    /|\\
                        |     /\\
                        |___________"""
    };

    public static void printHangman(int numPicture) {
        System.out.println(hangmanStatus[numPicture]);
    }
}

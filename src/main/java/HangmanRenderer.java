package main.java;

public class HangmanRenderer {
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

    public void printHangman(int numPicture) {
        System.out.println(hangmanStatus[numPicture]);
    }
}

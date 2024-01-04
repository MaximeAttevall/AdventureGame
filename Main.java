import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // This line will create an instance of AdventureGame and display the GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AdventureGame();
            }
        });
    }
}

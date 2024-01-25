import javax.swing.*;
import java.io.IOException;

public class Path4 {
    private static final int NUM_ROUNDS = 3;
    private static final ImageIcon DOOR_IMAGE = new ImageIcon("Images/3door.jpg");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Path4::playGame);
    }

    private static void playGame() {
        while (true) {
            for (int round = 1; round <= NUM_ROUNDS; round++) {
                showRoundMessage(round);

                int userChoice = getUserChoice();

                if (userChoice == round - 1) {
                    showCongratulationsMessage();
                } else {
                    showGameOverMessage();
                    break; // Exit the inner loop if the user loses a round
                }
            }

            int playAgain = JOptionPane.showConfirmDialog(null, "Do you want to play again?");
            if (playAgain != JOptionPane.YES_OPTION) {
                showGoodbyeMessage();
                break; // Exit the outer loop if the user chooses not to play again
            }
        }
    }

    private static void showRoundMessage(int round) {
        JOptionPane.showMessageDialog(null, "Round " + round + ": Choose a door.", "Door Game",
                JOptionPane.PLAIN_MESSAGE, DOOR_IMAGE);
    }

    private static int getUserChoice() {
        Object[] options = {"Door 1", "Door 2", "Door 3"};
        int userChoice = JOptionPane.showOptionDialog(
                null,
                "Which door do you want to open?",
                "Door Game",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                DOOR_IMAGE,
                options,
                options[0]
        );

        return userChoice;
    }

    private static void showCongratulationsMessage() {
        JOptionPane.showMessageDialog(null, "Congratulations! You chose the right door. Proceed to the next round.");
    }

    private static void showGameOverMessage() {
        JOptionPane.showMessageDialog(null, "Sorry, you chose the wrong door. Game over!");

        try {
            func.Die();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void showGoodbyeMessage() {
        JOptionPane.showMessageDialog(null, "Thanks for playing! Goodbye!");
    }
}

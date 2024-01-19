import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Path1 {

    private static final String WINNING_BACKGROUND_PATH = "images/win.png";
    private static final String END_BACKGROUND_PATH = "images/youdied.jpg";
    private static final String OPTION2_BACKGROUND_PATH = "images/TwoPaths.png";
    private static final String OPTION1A_BACKGROUND_PATH = "images/Option1A.jpg";
    private static final String OPTION1B_BACKGROUND_PATH = "images/Option1B.png";
    private static final String OPTION1A1_BACKGROUND_PATH = "images/Deer.jpg";
    private static final String EXIT_BACKGROUND_PATH = "images/exit.png";

    private static String currentBackgroundPath = WINNING_BACKGROUND_PATH;
    private static JFrame frame;


    public static void main(String[] args) {
        currentBackgroundPath = WINNING_BACKGROUND_PATH;

        SwingUtilities.invokeLater(() -> {
            frame = createFrame();
            showWelcomeDialog();
        });
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame("AdventureGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        return frame;
    }

    private static void showWelcomeDialog() {
        JOptionPane.showMessageDialog(frame, "Welcome Adventurer!");

        String name = JOptionPane.showInputDialog(frame, "What's your name?");

        if (name != null) {
            showWelcomeMessage(name);
        } else {
            showGameOverMessage();
        }
    }

    private static void showWelcomeMessage(String name) {
        String welcomeMessage = "Welcome adventurer " + name;
        JOptionPane.showMessageDialog(frame, welcomeMessage);

        // Now set the background to OPTION2_BACKGROUND_PATH
        currentBackgroundPath = OPTION2_BACKGROUND_PATH;
        updateBackground();

        showOptions("You see two paths in front of you, choose your path!",
                "Option 1", "Option 2",
                Path1::ToScene1, Path1::ToScene2);
    }

    private static void showOptions(String message, String option1Text, String option2Text,
                                    ActionListener option1Listener, ActionListener option2Listener) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JLabel messageLabel = new JLabel(message);
        JButton option1Button = new JButton(option1Text);
        JButton option2Button = new JButton(option2Text);

        option1Button.addActionListener(option1Listener);
        option2Button.addActionListener(option2Listener);

        buttonPanel.add(messageLabel);
        buttonPanel.add(option1Button);
        buttonPanel.add(option2Button);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.revalidate();
    }

    private static void ToScene1(ActionEvent e) {
        currentBackgroundPath = OPTION1A_BACKGROUND_PATH;
        updateBackground();

        showOptions("You see a lost person in the woods, would you like to help them?",
                "Yes", "No",
                Path1::ToScene3, Path1::ToScene4);
        
    }

    private static void ToScene2(ActionEvent e) {
        currentBackgroundPath = END_BACKGROUND_PATH;
        updateBackground();

    }
    private static void ToScene3(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "You decided to help the person, the person will now follow along with you through the woods");

        currentBackgroundPath = OPTION1A1_BACKGROUND_PATH;
        updateBackground();

        showOptions("You see a strange Deer infront of you, do you approach the deer?",
                "Yes", "No",
                Path1::ToScene6, Path1::ToScene4);

    }

    private static void ToScene4(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "You ignore the person and keep going through the woods");

        currentBackgroundPath = OPTION1A1_BACKGROUND_PATH;
        updateBackground();

        showOptions("You see a strange Deer infront of you, do you approach the deer?",
                "Yes", "No",
                Path1::ToScene2, Path1::ToScene5);
    }

    private static void ToScene5(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "You ignore the deer and keep on going");

        currentBackgroundPath = EXIT_BACKGROUND_PATH;
        updateBackground();

        showOptions("You see a exit from the woods, do you take it?",
                "Yes", "No",
                Path1::ToScene7, Path1::ToScene2);
    }

    private static void ToScene6(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "The deer attacks the boy and you manage to escape on your own");
        JOptionPane.showMessageDialog(frame, "You keep going through the woods");

        currentBackgroundPath = EXIT_BACKGROUND_PATH;
        updateBackground();

        showOptions("You see a exit from the woods, do you take it?",
                "Yes", "No",
                Path1::ToScene7, Path1::ToScene2);
    }
    private static void ToScene7(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "You exit the woods!");

        currentBackgroundPath = WINNING_BACKGROUND_PATH;
        updateBackground();

        JOptionPane.showMessageDialog(frame, "Congratulations you see the light and have exited the woods!");
    }

    private static void updateBackground() {
        try {
            BufferedImage backgroundImage = ImageIO.read(new File(currentBackgroundPath));
            BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
            frame.setContentPane(backgroundPanel);
            frame.validate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class BackgroundPanel extends JPanel {
        private final BufferedImage backgroundImage;

        public BackgroundPanel(BufferedImage backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private static void showGameOverMessage() {
        JOptionPane.showMessageDialog(frame, "No choice was made. The adventure is over.");
        frame.dispose();
    }
}
/*

public class Encounter {
 public Encounter(
    String backgroundImagePath,
    String option1Text,
    String option2Text
    ){
    this.backgroundImagePath = backgroundImagePath;
    this.option1Text = option1Text;
    this.option2Text = option2Text;
    }
}
 */

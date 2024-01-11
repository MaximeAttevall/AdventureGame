import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Path1 {
    private static final String DEFAULT_BACKGROUND_PATH = "images/win.png";
    private static final String OPTION1_BACKGROUND_PATH = "images/youdied.jpg";
    private static final String OPTION2_BACKGROUND_PATH = "images/TwoPaths.png";
    private static String currentBackgroundPath = DEFAULT_BACKGROUND_PATH;
    private static JFrame frame;

    public static void main(String[] args) {
        // Set the initial background to DEFAULT_BACKGROUND_PATH
        currentBackgroundPath = DEFAULT_BACKGROUND_PATH;

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

        // Now show the game options
        showGameOptionsWithButtons();
    }

    private static void showGameOptionsWithButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton option1Button = new JButton("Option 1");
        JButton option2Button = new JButton("Option 2");

        option1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You Live!");
                currentBackgroundPath = DEFAULT_BACKGROUND_PATH;
                updateBackground();
            }
        });

        option2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentBackgroundPath = OPTION1_BACKGROUND_PATH;
                updateBackground();
            }
        });

        buttonPanel.add(option1Button); //Lägger till knapparna till button panelen
        buttonPanel.add(option2Button);

        frame.add(buttonPanel, BorderLayout.SOUTH); //Lägger till button panelen till vår JFrame
        frame.revalidate(); //Uppdaterar våran JFrame med alla ny komponenter
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
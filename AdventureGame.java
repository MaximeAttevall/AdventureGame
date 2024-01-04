import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;




public class AdventureGame {
    private JFrame frame;
    private JButton cave1Button;
    private JButton cave2Button;
    private GamePanel gamePanel;
    private BufferedImage dragonImage;
    private BufferedImage background;
    private BufferedImage darkCaveImage;

    public AdventureGame() {
        loadImages();
        initComponents();
    }

    private void loadImages() {
        try {
            BufferedImage originalDragonImage = ImageIO.read(new File("images/testdrka.png"));
            Image scaledImage = originalDragonImage.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
            dragonImage = new BufferedImage(500, 300, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = dragonImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            background = ImageIO.read(new File("images/darkcaveny.png")); // Original background
            darkCaveImage = ImageIO.read(new File("images/darkcave.png")); // Dark cave image for choices
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load images.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void initComponents() {
        frame = new JFrame("Äventyrsspelet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        gamePanel = new GamePanel();
        gamePanel.setLayout(new BorderLayout());

        cave1Button = createButton("Grotta 1");
        cave2Button = createButton("Grotta 2");
        cave3Button = createButton("Grotta 3");

        cave1Button.addActionListener(e -> processChoice(1));
        cave2Button.addActionListener(e -> processChoice(2));
        cave3Button.addActionListener(e -> processChoice(3));



        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.add(cave1Button);
        buttonPanel.add(cave2Button);
        buttonPanel.add(cave3Button);

        gamePanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(gamePanel);
        frame.setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Pixel Emulator", Font.BOLD, 18)); // Custom retro font
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setForeground(Color.BLACK);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.green);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.BLACK);
            }
        });
        return button;
    }

    private void processChoice(int choice) {
        cave1Button.setVisible(false); // Hide buttons after a choice
        cave2Button.setVisible(false);

        if (choice == 1) {
            gamePanel.setBackgroundImage(darkCaveImage); // Set dark cave background
            JOptionPane.showMessageDialog(frame, "Du är närmare utgången grattis");
        } else if (choice == 2) {
            gamePanel.setBackgroundImage(darkCaveImage); // Set dark cave background with dragon
            gamePanel.setDragonVisible(true); // Make dragon visible
            JOptionPane.showMessageDialog(frame, "Du förlorade, monstret kommer äta dig!");
        }
        else if  (choice == 3) {
            gamePanel.setBackgroundImage(darkCaveImage);
            gamePanel.setDragonVisible(false);
            JOptionPane.showMessageDialog(frame, "Du är påväg till grotta 3! ");

        }

        else if  (choice == 4) {
            gamePanel.setBackgroundImage(darkCaveImage);
            gamePanel.setDragonVisible(false);
            JOptionPane.showMessageDialog(frame, "Du är påväg till grotta 3! ");

        }


        else if {
            gamePanel.setBackgroundImage(darkCaveImage);

        }
        gamePanel.repaint(); // Repaint to update the visuals
    }

    private class GamePanel extends JPanel {
        private boolean dragonVisible = false; // Flag to control dragon visibility

        public void setBackgroundImage(BufferedImage image) {
            background = image; // Change the background image
        }

        public void setDragonVisible(boolean visible) {
            dragonVisible = visible;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
            if (dragonVisible) {
                int x = (this.getWidth() - dragonImage.getWidth(null)) / 2;
                int y = (this.getHeight() - dragonImage.getHeight(null)) / 2;
                g.drawImage(dragonImage, x, y, this); // Draw dragon only if visible
            }
            g.setFont(new Font("Pixel Emulator", Font.BOLD, 36));
            drawCenteredString(g, "Välkommen to the Adventure!", getWidth(), 100);
        }

        private void drawCenteredString(Graphics g, String text, int width, int yPos) {
            FontMetrics metrics = g.getFontMetrics();
            int x = (width - metrics.stringWidth(text)) / 2;
            g.setColor(Color.RED);
            g.drawString(text, x, yPos);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdventureGame());
    }
}

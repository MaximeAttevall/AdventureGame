import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class AdventureGame {
    private JFrame frame;
    private JButton cave1Button;
    private JButton cave2Button;
    private GamePanel gamePanel;
    private BufferedImage dragonImage;
    private BufferedImage background;

    public AdventureGame() {
        loadImages();
        initComponents();
    }
    // Draw the image on to the buffered image
    Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();



    private void drawCenteredString(Graphics g, String text, int width, int yPos) {
        FontMetrics metrics = g.getFontMetrics();
        int x = (width - metrics.stringWidth(text)) / 2;
        g.setColor(Color.RED);
        g.drawString(text, x, yPos);
    }
    private void loadImages() {
        try {
            BufferedImage originalDragonImage = ImageIO.read(new File("images/testdrka.png"));
            dragonImage = toBufferedImage(originalDragonImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH));
            background = ImageIO.read(new File("images/darkcaveny.png"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load images.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }


    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
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
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.add(cave1Button);
        buttonPanel.add(cave2Button);


        cave1Button.addActionListener(e -> processChoice(1));
        cave2Button.addActionListener(e -> processChoice(2));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.add(cave1Button);
        buttonPanel.add(cave2Button);

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
                button.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.BLACK);
            }
        });
        return button;
    }

    private void processChoice(int choice) {
    }


    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the background image, scaled to fill the panel
            g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
            // Draw the dragon image
            int x = (this.getWidth() - dragonImage.getWidth(null)) / 2;
            int y = (this.getHeight() - dragonImage.getHeight(null)) / 2;
            g.drawImage(dragonImage, x, y, this);

            // Draw the welcome text over the dragon image
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
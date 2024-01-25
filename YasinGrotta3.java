import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class YasinGrotta3 {
    private BufferedImage background;
    private JFrame frame;
    private JPanel buttonPanel;

    // Constructor
    public YasinGrotta3() {
        loadImages();
        initComponents();

        System.out.println("Kod från YasinGrotta3 körs nu...");
    }

    // Method to start Path3
    public void launchPath3() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Path3(); // Use the constructor to initialize and show the UI
            }
        });
    }

    private void loadImages() {
        try {
            background = ImageIO.read(new File("Images/grottayas.png")); // Change background to "val2.png"
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load images.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void initComponents() {
        frame = new JFrame("YasinGrotta3");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null) {
                    g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
                }
            }
        };

        gamePanel.setLayout(new BorderLayout());

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        // Add two buttons for user choice
        addButton("Option 1");
        addButton("Option 2");

        gamePanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(gamePanel);
        frame.setVisible(true);
    }

    private void addButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Pixel Emulator", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setForeground(new Color(255, 0, 0));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the button click event here
                JOptionPane.showMessageDialog(null, "You clicked: " + text, "Button Clicked", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonPanel.add(button);
        frame.pack(); // Adjust the frame size to accommodate the new button
    }
}

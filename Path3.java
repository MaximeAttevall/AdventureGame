import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Path3 {
    private BufferedImage background;
    private JFrame frame;
    private JPanel buttonPanel;
    private JPanel gamePanel; // Make gamePanel a class member so it can be accessed in methods


    public Path3() {
        loadImages();
        initComponents();
        System.out.println("Code from Path3 is now running...");
    }

    private void loadImages() {
        try {
            background = ImageIO.read(new File("images/grottayas.png")); // Ensure this path is correct
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load images.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void initComponents() {
        frame = new JFrame("Path3");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        frame.setLayout(new BorderLayout());

        gamePanel = new JPanel() {
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


        addButton("Option 1");
        addButton("Option 2");

        gamePanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(gamePanel);


        frame.setSize(800, 600);
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


        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(139, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(255, 0, 0));
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Option 1".equals(text)) {
                    changeBackgroundImage("images/vag2.png");
                    updateButtonPanel(new String[]{"Keep going", "Look around"});
                } else if ("Option 2".equals(text)) {
                    changeBackgroundImage("images/mosntervag.png");

                } else {

                    JOptionPane.showMessageDialog(null, "You clicked: " + text, "Button Clicked", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        buttonPanel.add(button);

    }

    private void changeBackgroundImage(String imagePath) {
        try {
            background = ImageIO.read(new File(imagePath));
            gamePanel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load image: " + imagePath, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateButtonPanel(String[] buttonLabels) {
        buttonPanel.removeAll();

        for (String label : buttonLabels) {
            addButton(label);
        }

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Path3()); // Initialize and show the UI
    }
}

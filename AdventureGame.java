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
    private GamePanel gamePanel;
    private BufferedImage background;

    public AdventureGame() {
        loadImages();
        initComponents();
    }

    private void loadImages() {
        try {
            background = ImageIO.read(new File("images/darkcaveny.png")); // Original background
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);



        for (int i = 1; i <= 5; i++) {
            String filePath = "Path" + i + ".java";
            JButton button = createButton("Path " + i, filePath);
            buttonPanel.add(button);
        }
        

        gamePanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(gamePanel);
        frame.setVisible(true);
    }

    private JButton createButton(String text, String filePath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Pixel Emulator", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setForeground(new Color(255, 0, 0));
    
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    executeJavaFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
    
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(139, 0, 0));
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(255, 0, 0));
            }
        });
        return button;
    }
    
    private void executeJavaFile(String filePath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("javac", "-encoding", "UTF-8", filePath);
        Process compileProcess = processBuilder.start();
        
        try {
            int exitCode = compileProcess.waitFor();
            if (exitCode == 0) {
                ProcessBuilder runProcessBuilder = new ProcessBuilder("java", filePath.replace(".java", ""));
                runProcessBuilder.start();
            } else {
                System.out.println("Compilation failed.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
            }
            g.setFont(new Font("Pixel Emulator", Font.BOLD, 36));
            drawCenteredString(g, "Welcome to the Adventure!", getWidth(), 100);
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

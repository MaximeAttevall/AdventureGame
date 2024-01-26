import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;



public class Path3 {
    private BufferedImage background;

    private JLayeredPane layeredPane;

    public JFrame frame;

    private JPanel buttonPanel;
    private JPanel gamePanel;

    private Timer monsterSpawnTimer;
    private int monstersKilled = 0;
    private final int totalMonsters = 10;
    private List<Timer> monsterAnimationTimers = new ArrayList<>();

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
/*
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


        gamePanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                gamePanel.removeComponentListener(this); // Remove the listener after the panel is shown
            }

            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                gamePanel.removeComponentListener(this); // Remove the listener after the panel is resized
            }
        });

    }
*/
private void initComponents() {
    frame = new JFrame("Path3");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLayout(new BorderLayout());



    JLayeredPane layeredPane = new JLayeredPane();
    layeredPane.setPreferredSize(new Dimension(800, 600));


    gamePanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    };
    gamePanel.setBounds(0, 0, 800, 600);
    gamePanel.setOpaque(false); // Ensure gamePanel is transparent
    gamePanel.setLayout(new BorderLayout()); // Set layout

    buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    buttonPanel.setBounds(0, 500, 800, 100); // Adjust the bounds as per your need
    buttonPanel.setOpaque(false); // Ensure buttonPanel is transparent

    addButton("Go deeper in cave");
    addButton("Have a look around");

    // Add the panels to the layered pane
    layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER); // Add gamePanel to default layer
    layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER); // Add buttonPanel to a higher layer


    frame.add(layeredPane); // Add layeredPane to the frame

    frame.setSize(800, 600);
    frame.setVisible(true);
}


    private void addButton(String text) {
        JButton button = new JButton(text);
        styleButton(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Go deeper in cave".equals(text)) {
                    changeBackgroundImage("images/vag2.png");
                    updateButtonPanel(new String[]{"Keep going", "Look around"});
                } else if ("Have a look around".equals(text)) {
                    changeBackgroundImage("images/mosntervag.png");
                    buttonPanel.removeAll();
                    spawnMonsters();
                } else if ("Look around".equals(text)) {

                    changeBackgroundImage("images/mosntervag.png");
                    buttonPanel.removeAll();
                    spawnMonsters();
                } else if ("Keep going".equals(text)) {
                    changeBackgroundImage("images/option2-win.png");
                    updateButtonPanel(new String[]{"Use the latter to climb", "Keep going!"});
                } else if ("Keep going!".equals(text)) {
                    changeBackgroundImage("images/mosntervag.png");
                    buttonPanel.removeAll();
                    spawnMonsters();
                } else if ("Use the latter to climb".equals(text)) {
                    changeBackgroundImage("images/win.png");
                    buttonPanel.removeAll();
                    JOptionPane.showMessageDialog(frame, "Congratulations you made it out of the cave!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "You clicked: " + text, "Button Clicked", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        buttonPanel.add(button);
    }

    private void styleButton(JButton button) {
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
    }

    private void spawnMonsters() {
        System.out.println("spawnMonsters is called");

        monsterSpawnTimer = new Timer(1000, new ActionListener() {
            private int spawnedMonsters = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (spawnedMonsters < totalMonsters && monstersKilled < totalMonsters) {
                    addMonster("images/monsterclick1.png", "images/monsterclick2.png");
                    spawnedMonsters++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        monsterSpawnTimer.start();
    }

    private void addMonster(String imagePath1, String imagePath2) {
        try {
            gamePanel.setLayout(null);

            BufferedImage monsterImage = ImageIO.read(new File(Math.random() < 0.5 ? imagePath1 : imagePath2));
            JLabel monsterLabel = new JLabel(new ImageIcon(monsterImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));

            gamePanel.validate();
            int maxX = gamePanel.getWidth() - 100;
            int maxY = gamePanel.getHeight() - 100;
            Random rand = new Random();
            int initialX = rand.nextInt(maxX + 1);
            int initialY = rand.nextInt(maxY + 1);

            monsterLabel.setBounds(initialX, initialY, 100, 100);
            gamePanel.add(monsterLabel);
            gamePanel.revalidate();
            gamePanel.repaint();
            System.out.println("Monster spawning at x: " + initialX + ", y: " + initialY);

            Timer monsterTimer = animateMonster(monsterLabel, monsterImage);
            monsterLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    killMonster(monsterLabel, monsterTimer);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load monster image.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private Timer animateMonster(JLabel monsterLabel, BufferedImage monsterImage) {
        Timer animationTimer = new Timer(100, null);
        Point originalCenter = new Point(monsterLabel.getX() + monsterLabel.getWidth() / 2,
                monsterLabel.getY() + monsterLabel.getHeight() / 2);

        animationTimer.addActionListener(new ActionListener() {
            private double scale = 1.0;

            @Override
            public void actionPerformed(ActionEvent e) {
                scale += 0.05;
                int newWidth = (int) (100 * scale);
                int newHeight = (int) (100 * scale);


                int newX = originalCenter.x - newWidth / 2;
                int newY = originalCenter.y - newHeight / 2;

                // Adjust position if out of bounds
                if (newX < 0) newX = 0;
                if (newY < 0) newY = 0;
                if (newX + newWidth > gamePanel.getWidth()) newX = gamePanel.getWidth() - newWidth;
                if (newY + newHeight > gamePanel.getHeight()) newY = gamePanel.getHeight() - newHeight;

                monsterLabel.setBounds(newX, newY, newWidth, newHeight);


                if (newWidth >= gamePanel.getWidth() || newHeight >= gamePanel.getHeight()) {
                    animationTimer.stop();
                    triggerDeathScene();
                } else {

                    monsterLabel.setIcon(new ImageIcon(monsterImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH)));
                }
            }
        });

        animationTimer.start();
        monsterAnimationTimers.add(animationTimer);
        return animationTimer;
    }

/*
    private void killMonster(JLabel monsterLabel, Timer monsterTimer) {
        try {
            BufferedImage deathSceneImage = ImageIO.read(new File("images/monsterdeathscene.png"));
            monsterLabel.setIcon(new ImageIcon(deathSceneImage.getScaledInstance(monsterLabel.getWidth(), monsterLabel.getHeight(), Image.SCALE_SMOOTH)));

            Timer fadeTimer = new Timer(100, null);
            fadeTimer.addActionListener(new ActionListener() {
                private float opacity = 1.0f;

                @Override
                public void actionPerformed(ActionEvent e) {
                    opacity -= 0.1f;
                    if (opacity <= 0.0f) {
                        fadeTimer.stop();
                        gamePanel.remove(monsterLabel);
                        gamePanel.revalidate();
                        gamePanel.repaint();
                    } else {
                        Image translucentImage = makeImageTranslucent(deathSceneImage, opacity);
                        monsterLabel.setIcon(new ImageIcon(translucentImage));
                    }
                }
            });
            fadeTimer.start();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load death scene image.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        monstersKilled++;
        monsterTimer.stop(); // Stop the animation for this monster
        if (monstersKilled == totalMonsters) {
            if (monsterSpawnTimer != null) {
                monsterSpawnTimer.stop(); // Ensure no more monsters are spawned
            }
            showClearStagePopup();
        }
    }
*/

    /*SKIT KOD*/
    private void killMonster(JLabel monsterLabel, Timer monsterTimer) {
        final Timer[] fadeTimer = {null};

        try {

            BufferedImage hurtImage = ImageIO.read(new File("images/hurtmonster.png"));


            int centerX = (monsterLabel.getWidth() - hurtImage.getWidth()) / 2;
            int centerY = (monsterLabel.getHeight() - hurtImage.getHeight()) / 2;


            BufferedImage combinedImage = new BufferedImage(monsterLabel.getWidth(), monsterLabel.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = combinedImage.createGraphics();


            BufferedImage deathSceneImage = ImageIO.read(new File("images/monsterdeathscene.png"));
            g2d.drawImage(deathSceneImage, 0, 0, null);


            g2d.drawImage(hurtImage, centerX, centerY, null);

            g2d.dispose();


            monsterLabel.setIcon(new ImageIcon(combinedImage));


            fadeTimer[0] = new Timer(100, new ActionListener() {
                private float opacity = 1.0f;

                @Override
                public void actionPerformed(ActionEvent e) {
                    opacity -= 0.1f;
                    if (opacity <= 0.0f) {
                        fadeTimer[0].stop();
                        // Remove the 'monsterdeathscene.png' image
                        gamePanel.remove(monsterLabel);
                        gamePanel.revalidate();
                        gamePanel.repaint();
                    } else {
                        // Apply fade effect to 'monsterdeathscene.png' image
                        Image translucentImage = makeImageTranslucent(deathSceneImage, opacity);
                        monsterLabel.setIcon(new ImageIcon(translucentImage));
                    }
                }
            });
            fadeTimer[0].setRepeats(true);
            fadeTimer[0].setCoalesce(true);
            fadeTimer[0].start();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load images.", "Error", JOptionPane.ERROR_MESSAGE);
        }


        monstersKilled++;
        monsterTimer.stop();
        if (monstersKilled == totalMonsters) {
            if (monsterSpawnTimer != null) {
                monsterSpawnTimer.stop();
            }
            showClearStagePopup();
        }
    }


    private void showClearStagePopup() {
        if (monsterSpawnTimer != null) {
            monsterSpawnTimer.stop();
        }


        for (Timer timer : monsterAnimationTimers) {
            timer.stop();
        }
        monsterAnimationTimers.clear();


        gamePanel.removeAll();
        gamePanel.revalidate();
        gamePanel.repaint();


        JOptionPane.showMessageDialog(frame, "You cleared the stage! Press 'OK' to proceed.", "Stage Cleared", JOptionPane.INFORMATION_MESSAGE);


        proceedToNextStage();
    }
    private Image makeImageTranslucent(BufferedImage image, float alpha) {
        BufferedImage aimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics2D g = aimg.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return aimg;
    }

    private void proceedToNextStage() {
        changeBackgroundImage("images/win.png");
        gamePanel.removeAll();
        buttonPanel.removeAll();
        JOptionPane.showMessageDialog(frame, "Congratulations, you made it out of the cave!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);


    }
/*
private void proceedToNextStage() {
    SwingUtilities.invokeLater(() -> {
        changeBackgroundImage("images/option2-win.png");
        gamePanel.removeAll();
        buttonPanel.removeAll();

        // Reset the size or layout of gamePanel or layeredPane if needed
        gamePanel.setPreferredSize(new Dimension(800, 600)); // Example, set to your needed dimensions

        // Add "Keep going!" button
        JButton keepGoingButton = new JButton("Keep going!");
        keepGoingButton.addActionListener(e -> {
            try {
                changeBackgroundImage("images/mosntervag.png");
                gamePanel.removeAll();
                buttonPanel.removeAll();
                spawnMonsters();
                buttonPanel.revalidate();
                buttonPanel.repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.add(keepGoingButton);
        styleButton(keepGoingButton);

        // Add "Use the ladder to climb" button
        JButton useLadderButton = new JButton("Use the ladder to climb");
        useLadderButton.addActionListener(e -> {
            changeBackgroundImage("images/win.png");
            showClearStagePopup();
            buttonPanel.removeAll();
            JOptionPane.showMessageDialog(frame, "Congratulations, you made it out of the cave!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            buttonPanel.revalidate();
            buttonPanel.repaint();

        });
        buttonPanel.add(useLadderButton);
        styleButton(useLadderButton);

        spawnMonsters();

        gamePanel.revalidate();
        gamePanel.repaint();
    });
}
*/


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

    private void triggerDeathScene() {

        if (monsterSpawnTimer != null) {
            monsterSpawnTimer.stop();
        }


        stopAllMonsterAnimations();


        gamePanel.removeAll();
        gamePanel.revalidate();
        gamePanel.repaint();


        monstersKilled = 0;
        monsterAnimationTimers.clear();


        changeBackgroundImage("images/deathyas.png");


        buttonPanel.removeAll();
        buttonPanel.revalidate();
        buttonPanel.repaint();


        showDeathPopup();
    }


    private void showDeathPopup() {
        int choice = JOptionPane.showConfirmDialog(
                frame,
                "You died! Press 'Restart' to try again.",
                "Game Over",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

        if (choice == JOptionPane.OK_OPTION) {
            restartGame();
        } else {
            frame.dispose();
        }
    }

    private void restartGame() {

        if (monsterSpawnTimer != null) {
            monsterSpawnTimer.stop();
        }
        stopAllMonsterAnimations();

        gamePanel.removeAll();
        monstersKilled = 0;

        loadImages();
        initComponents();
        frame.revalidate();
        frame.repaint();
    }

    private void stopAllMonsterAnimations() {
        for (Timer timer : monsterAnimationTimers) {
            if (timer != null) {
                timer.stop();
            }
        }
        monsterAnimationTimers.clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Path3());
    }
}

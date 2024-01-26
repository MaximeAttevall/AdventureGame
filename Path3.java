import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
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
        loadImages(); // Laddar in bilder
        initComponents(); // Initialiserar komponenter
        System.out.println("Koden från Path3 körs nu...");
    }

    // Laddar bakgrundsbilden
    private void loadImages() {
        try {
            background = ImageIO.read(new File("images/grottayas.png")); // Kontrollera att denna sökväg är korrekt
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kunde inte ladda bilderna.", "Fel", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    // Initiera gränssnittskomponenter
    private void initComponents() {
        frame = new JFrame("Path3");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
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
        gamePanel.setOpaque(false);
        gamePanel.setLayout(new BorderLayout());

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBounds(0, 500, 800, 100);
        buttonPanel.setOpaque(false);

        addButton("Utforska djupare i grottan");
        addButton("Titta runt");

        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

        frame.add(layeredPane);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // Lägg till knappar och definiera deras stil och funktion

    private void addButton(String text) {
        JButton button = new JButton(text);
        styleButton(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Utforska djupare i grottan".equals(text)) {
                    changeBackgroundImage("images/vag2.png");
                    updateButtonPanel(new String[]{"Fortsätt", "Titta runt"});
                } else if ("Titta runt".equals(text)) {
                    changeBackgroundImage("images/mosntervag.png");
                    buttonPanel.removeAll();
                    spawnMonsters();
                } else if ("Fortsätt".equals(text)) {
                    changeBackgroundImage("images/option2-win.png");
                    updateButtonPanel(new String[]{"Använd stegen för att klättra", "Fortsätt!"});
                } else if ("Fortsätt!".equals(text)) {
                    // Ändring här: "Fortsätt!" knappen kommer nu att trigga monster scenen
                    changeBackgroundImage("images/mosntervag.png");
                    buttonPanel.removeAll();
                    spawnMonsters();
                } else if ("Använd stegen för att klättra".equals(text)) {
                    changeBackgroundImage("images/win.png");
                    buttonPanel.removeAll();
                    JOptionPane.showMessageDialog(frame, "Grattis, du tog dig ut ur grottan!", "Grattis", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Du klickade: " + text, "Knapp klickad", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        buttonPanel.add(button);
    }


    // Styla knapparna
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

    // Skapa monster
    private void spawnMonsters() {
        System.out.println("Monsters skapas...");

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

    // Lägg till monster i spelet
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
            System.out.println("Monster skapas på x: " + initialX + ", y: " + initialY);

            Timer monsterTimer = animateMonster(monsterLabel, monsterImage);
            monsterLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    killMonster(monsterLabel, monsterTimer);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kunde inte ladda monsterbild.", "Fel", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Animera monster på spelet
    private Timer animateMonster(JLabel monsterLabel, BufferedImage monsterImage) {
        Timer animationTimer = new Timer(100, null);
        Point originalCenter = new Point(monsterLabel.getX() + monsterLabel.getWidth() / 2,
                monsterLabel.getY() + monsterLabel.getHeight() / 2);

        animationTimer.addActionListener(new ActionListener() {
            private double scale = 1.0;

            @Override
            public void actionPerformed(ActionEvent e) {
                scale += 0.05; // Öka skalan för att få monstret att växa
                int newWidth = (int) (100 * scale); // Beräkna ny bredd
                int newHeight = (int) (100 * scale); // Beräkna ny höjd

                // Beräkna ny position för att hålla monstret centrerat
                int newX = originalCenter.x - newWidth / 2;
                int newY = originalCenter.y - newHeight / 2;

                // Justera positionen om den är utanför gränserna
                if (newX < 0) newX = 0;
                if (newY < 0) newY = 0;
                if (newX + newWidth > gamePanel.getWidth()) newX = gamePanel.getWidth() - newWidth;
                if (newY + newHeight > gamePanel.getHeight()) newY = gamePanel.getHeight() - newHeight;

                monsterLabel.setBounds(newX, newY, newWidth, newHeight); // Uppdatera monstrets position och storlek

                // Om monstret når gränserna för spelpanelen, stoppa animationen och trigga dödsscenen
                if (newWidth >= gamePanel.getWidth() || newHeight >= gamePanel.getHeight()) {
                    animationTimer.stop();
                    triggerDeathScene();
                } else {
                    // Uppdatera bilden av monstret för att matcha den nya skalan
                    monsterLabel.setIcon(new ImageIcon(monsterImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH)));
                }
            }
        });

        animationTimer.start(); // Starta timer som hanterar animationen
        monsterAnimationTimers.add(animationTimer); // Lägg till timern i listan för att kunna hantera den senare
        return animationTimer;
    }

    /* Döda monster */
    private void killMonster(JLabel monsterLabel, Timer monsterTimer) {
        final Timer[] fadeTimer = {null};

        try {
            // Läs in bild för skadat monster
            BufferedImage hurtImage = ImageIO.read(new File("images/hurtmonster.png"));

            // Centrera den skadade bilden på monstret
            int centerX = (monsterLabel.getWidth() - hurtImage.getWidth()) / 2;
            int centerY = (monsterLabel.getHeight() - hurtImage.getHeight()) / 2;

            // Skapa en ny bild som kombinerar monsterbilden och bilden för skadat monster
            BufferedImage combinedImage = new BufferedImage(monsterLabel.getWidth(), monsterLabel.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = combinedImage.createGraphics();

            // Läs in bilden för dödsscenen
            BufferedImage deathSceneImage = ImageIO.read(new File("images/monsterdeathscene.png"));
            g2d.drawImage(deathSceneImage, 0, 0, null); // Rita dödsscenen
            g2d.drawImage(hurtImage, centerX, centerY, null); // Rita den skadade bilden över dödsscenen
            g2d.dispose(); // Rensa upp grafikkontexten

            monsterLabel.setIcon(new ImageIcon(combinedImage)); // Uppdatera monstrets bild till den kombinerade bilden

            // Skapa en timer som långsamt gör bilden genomskinlig för att visa en bleknande effekt
            fadeTimer[0] = new Timer(100, new ActionListener() {
                private float opacity = 1.0f;

                @Override
                public void actionPerformed(ActionEvent e) {
                    opacity -= 0.1f; // Minska opaciteten
                    if (opacity <= 0.0f) {
                        fadeTimer[0].stop(); // Stoppa timern när bilden är helt genomskinlig
                        gamePanel.remove(monsterLabel); // Ta bort monstret från spelpanelen
                        gamePanel.revalidate();
                        gamePanel.repaint();
                    } else {
                        // Skapa en genomskinlig version av bilden och uppdatera monstrets bild
                        Image translucentImage = makeImageTranslucent(deathSceneImage, opacity);
                        monsterLabel.setIcon(new ImageIcon(translucentImage));
                    }
                }
            });
            fadeTimer[0].setRepeats(true);
            fadeTimer[0].setCoalesce(true);
            fadeTimer[0].start(); // Starta timern för blekningseffekten

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kunde inte ladda bilder.", "Fel", JOptionPane.ERROR_MESSAGE);
        }

        monstersKilled++;
        monsterTimer.stop(); // Stoppa monstrets animations-timer
        if (monstersKilled == totalMonsters) {
            if (monsterSpawnTimer != null) {
                monsterSpawnTimer.stop(); // Stoppa spawn-timern om alla monster är döda
            }
            showClearStagePopup(); // Visa meddelande om att scenen är avklarad
        }
    }

    // Visar popup när en scen är klar
    private void showClearStagePopup() {
        // Stoppa alla timers relaterade till monster spawn
        if (monsterSpawnTimer != null) {
            monsterSpawnTimer.stop();
        }

        // Stoppa alla animations-timers för monster
        for (Timer timer : monsterAnimationTimers) {
            timer.stop();
        }
        monsterAnimationTimers.clear(); // Rensa listan av monster animations-timers

        // Rensa spelpanelen och uppdatera den
        gamePanel.removeAll();
        gamePanel.revalidate();
        gamePanel.repaint();

        // Visa popup meddelande om att spelaren har klarat scenen
        JOptionPane.showMessageDialog(frame, "Du klarade scenen! Tryck på 'OK' för att fortsätta.", "Scen Avklarad", JOptionPane.INFORMATION_MESSAGE);

        // Gå vidare till nästa scen
        proceedToNextStage();
    }

    // Gör en bild genomskinlig baserat på angiven alpha-värde
    private Image makeImageTranslucent(BufferedImage image, float alpha) {
        BufferedImage aimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics2D g = aimg.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // Ställ in alpha komposit
        g.drawImage(image, 0, 0, null); // Rita bilden med den nya genomskinligheten
        g.dispose(); // Rensa upp grafikkontexten
        return aimg;
    }

    // Gå vidare till nästa scen
    private void proceedToNextStage() {
        // Ändra bakgrundsbilden för att representera nästa scen
        changeBackgroundImage("images/win.png");
        gamePanel.removeAll(); // Rensa spelpanelen
        buttonPanel.removeAll(); // Rensa knappanelen
        // Visa popup meddelande om att spelaren har klarat spelet
        JOptionPane.showMessageDialog(frame, "Grattis, du tog dig ut ur grottan!", "Grattis", JOptionPane.INFORMATION_MESSAGE);
    }

    // Ändra bakgrundsbilden
    private void changeBackgroundImage(String imagePath) {
        try {
            background = ImageIO.read(new File(imagePath)); // Läs in den nya bakgrundsbilden
            gamePanel.repaint(); // Uppdatera spelpanelen för att visa den nya bilden
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kunde inte ladda bilden: " + imagePath, "Fel", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Uppdatera knappanelen med nya knappar
    private void updateButtonPanel(String[] buttonLabels) {
        buttonPanel.removeAll(); // Rensa knappanelen

        // Lägg till nya knappar baserade på angivna etiketter
        for (String label : buttonLabels) {
            addButton(label);
        }

        buttonPanel.revalidate(); // Validera knappanelen för att inkludera de nya knapparna
        buttonPanel.repaint(); // Uppdatera visningen av knappanelen
    }

    // Trigger för dödsscenen
    private void triggerDeathScene() {
        if (monsterSpawnTimer != null) {
            monsterSpawnTimer.stop(); // Stoppa timer för monster spawn
        }

        stopAllMonsterAnimations(); // Stoppa alla monster animationer

        // Rensa och uppdatera spelpanelen
        gamePanel.removeAll();
        gamePanel.revalidate();
        gamePanel.repaint();

        monstersKilled = 0; // Återställ antal dödade monster
        monsterAnimationTimers.clear(); // Rensa listan av monster animations-timers

        changeBackgroundImage("images/deathyas.png"); // Ändra bakgrundsbilden till dödsscenen

        // Rensa och uppdatera knappanelen
        buttonPanel.removeAll();
        buttonPanel.revalidate();
        buttonPanel.repaint();

        showDeathPopup(); // Visa popup för dödsscenen
    }

    // Visa popup för dödsscenen
    private void showDeathPopup() {
        int choice = JOptionPane.showConfirmDialog(
                frame,
                "Du dog! Tryck på 'Starta om' för att försöka igen.",
                "Spelet är över",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

        if (choice == JOptionPane.OK_OPTION) {
            restartGame(); // Starta om spelet om spelaren väljer att göra det
        } else {
            frame.dispose(); // Stäng av spelet om spelaren väljer att inte starta om
        }
    }

    // Starta om spelet
    private void restartGame() {
        if (monsterSpawnTimer != null) {
            monsterSpawnTimer.stop(); // Stoppa timer för monster spawn
        }
        stopAllMonsterAnimations(); // Stoppa alla monster animationer

        gamePanel.removeAll(); // Rensa spelpanelen
        monstersKilled = 0; // Återställ antal dödade monster

        loadImages(); // Ladda om bilderna
        initComponents(); // Initiera komponenterna på nytt
        frame.revalidate(); // Validera ramen på nytt
        frame.repaint(); // Uppdatera visningen av ramen
    }

    // Stoppa alla monster animationer

    private void stopAllMonsterAnimations() {
        for (Timer timer : monsterAnimationTimers) {
            if (timer != null) {
                timer.stop(); // Stoppar timer i listan
            }
        }
        monsterAnimationTimers.clear(); // Rensar listan av animations monster
    }

    // Startar spelet
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Path3()); // startar spelet event
    }
}


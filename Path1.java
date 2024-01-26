import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Path1 {
    // Filvägar för olika bakgrundsbilder
    private static final String WINNING_BACKGROUND_PATH = "images/mahmud.jpg";
    private static final String OPTION2_BACKGROUND_PATH = "images/TwoPaths.png";
    private static final String OPTION1A_BACKGROUND_PATH = "images/Option1A.jpg";
    private static final String OPTION1B_BACKGROUND_PATH = "images/castle.jpg";
    private static final String OPTION1B1_BACKGROUND_PATH = "images/butler.jpg";
    private static final String OPTION1B1A_BACKGROUND_PATH = "images/hall.jpg";
    private static final String OPTION1B1B_BACKGROUND_PATH = "images/Dining.jpg";
    private static final String OPTION1A1_BACKGROUND_PATH = "images/Deer.jpg";
    private static final String EXIT_BACKGROUND_PATH = "images/exit.png";

    // Aktuell filväg för bakgrundsbilden
    private static String currentBackgroundPath = WINNING_BACKGROUND_PATH;
    private static JFrame frame;
    private static String playerName; // Lagrar namnet som en instansvariabel, nu kan vi återanvända det


    public static void main(String[] args) {
        currentBackgroundPath = WINNING_BACKGROUND_PATH;

        // Starta Swing-applikationen
        SwingUtilities.invokeLater(() -> {
            frame = createFrame();
            showWelcomeDialog();
        });
    }

    // Skapar min JFrame
    static JFrame createFrame() {
        JFrame frame = new JFrame("AdventureGame"); // Skapa ny Jframe med en titel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // När vår jframe stängs ner avslutas koden också
        frame.setSize(800, 600); // Storlek
        frame.setLayout(new BorderLayout()); // Layout för fönster (Borderlayout e ofta defualt)
        frame.setLocationRelativeTo(null); // Centrera jframe
        frame.setVisible(true); // Gör framen synlig
        return frame; // returnera framen som skapats för användning
    }

    // Visa välkomstdialogruta och begär spelarens namn, lagrar namn. Som i powerpoint 2 (ifall jag minns rätt)
    private static void showWelcomeDialog() {
        JOptionPane.showMessageDialog(frame, "Welcome Adventurer!");

        String name = JOptionPane.showInputDialog(frame, "What's your name?");

        if (name != null) {
            showWelcomeMessage(name);
        } else {
            showGameOverMessage();
        }
    }

    // Välkomstmeddelande med spelarens namn och presentera två alternativ som tar oss till olika scener.
    public static void showWelcomeMessage(String name) {
        playerName = name; // Spara spelarens namn som en instansvariabel

        String welcomeMessage = "Welcome adventurer " + name;
        JOptionPane.showMessageDialog(frame, welcomeMessage);

        currentBackgroundPath = OPTION2_BACKGROUND_PATH;
        updateBackground();

        showOptions("You see two paths in front of you, choose your path!",
                "Left", "Right",
                Path1::ToScene1, Path1::ToScene8);
    }

    // Skapar en metod för att ha två val och sedan bestämma vad vår "actionlistener" gör.
    private static void showOptions(String message, String option1Text, String option2Text,
                                    ActionListener option1Listener, ActionListener option2Listener) {
        // Skapa en panel för knapparna
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Skapa meddelandet
        JLabel messageLabel = new JLabel(message);

        // Skapa två knappar med (ja/nej,höger/vänster etc)
        JButton option1Button = new JButton(option1Text);
        JButton option2Button = new JButton(option2Text);

        // Associera actionlistener med våra knapar, så något händer ifall vi clickar på dom.
        option1Button.addActionListener(option1Listener);
        option2Button.addActionListener(option2Listener);

        // Lägg till etiketten/meddelande och knapparna till vår panel
        buttonPanel.add(messageLabel);
        buttonPanel.add(option1Button);
        buttonPanel.add(option2Button);

        // Gör så att knapparna hamnar i mitten (dock inget ändras med söder,norr).
        frame.add(buttonPanel, BorderLayout.CENTER);
        // Uppdaterar vår frame så allting som vi gjort med våra actionlisteners går först -> ändras framen.
        frame.revalidate();
    }

    // En av mina Actionlisteners (vad som händer när man klickar på en knapp)
    private static void ToScene1(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "You continue into the woods");
        currentBackgroundPath = OPTION1A_BACKGROUND_PATH; // Ändrar backgrunds bild till våran frame
        updateBackground(); // Uppdaterar

        // Kallar på vår showOptions metod och tar oss vidare till våra nästa scener med två olika val.
        showOptions("You see a lost person in the woods, would you like to help them?",
                "Help", "Leave the boy alone!",
                Path1::ToScene3, Path1::ToScene4);

    }

    private static void ToScene2(ActionEvent e) {
        frame.dispose(); // Tar bort vår frame helt och hållet då vi ska kalla på en ny frame via, func.Die() (annars blir det två Jframes)
        // Kallar på våran func.Die som lagrar och visar dödsmeddelande ifall man gör fel val i en Json
        try {
            func.Die();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void ToScene3(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "You decided to help the person, the person will now follow along with you through the woods");

        currentBackgroundPath = OPTION1A1_BACKGROUND_PATH;
        updateBackground();

        showOptions("You see a strange Deer infront of you, do you approach the deer?",
                "Yes", "No",
                Path1::ToScene6, Path1::ToScene5);

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

        showOptions("You see a what seems to be an exit from the woods, do you take it?",
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

        JOptionPane.showMessageDialog(frame, "Congratulations you can see the light and have exited the woods!");
    }

    private static void ToScene8(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "You see what seems to be a haunted castle close by");

        currentBackgroundPath = OPTION1B_BACKGROUND_PATH;
        updateBackground();

        showOptions("Do you walk into the castle or continue through the woods?",
                "Castle", "Woods",
                Path1::ToScene9, Path1::ToScene1);
    }

    public static void ToScene9(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "You enter the castle");

        currentBackgroundPath = OPTION1B1_BACKGROUND_PATH;
        updateBackground();

        String welcomeMessage = "Welcome " + playerName + " I've been expecting you!";
        JOptionPane.showMessageDialog(frame, welcomeMessage);

        showOptions("The butler invites you to the dinning area?",
                "Follow along", "Explore on your own",
                Path1::ToScene11, Path1::ToScene10);
    }

    private static void ToScene10(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "You continue on your own, ignoring the butler");

        currentBackgroundPath = OPTION1B1A_BACKGROUND_PATH;
        updateBackground();

        showOptions("You are not sure where you are headed, do you walk forward or turn around?",
                "Walk forward", "Turn around",
                Path1::ToScene2, Path1::ToScene2);
    }

    private static void ToScene11(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "You enter the dining area, with prepared meals in front of you");

        currentBackgroundPath = OPTION1B1B_BACKGROUND_PATH;
        updateBackground();

        showOptions("The butler presents two options: 1. You eat his home cooked meal or 2. You eat the hamburger he recently bought",
                "1", "2",
                Path1::ToScene12, Path1::ToScene2);
    }

    private static void ToScene12(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "You suddendly appear in front of a strange deer in the woods");

        currentBackgroundPath = OPTION1A1_BACKGROUND_PATH;
        updateBackground();

        showOptions("Do you approach the deer?",
                "Approach", "No thank you",
                Path1::ToScene2, Path1::ToScene5);
    }

    private static void updateBackground() {
        try {
            // Läser en image file från våran currentbackgroundpath
            BufferedImage backgroundImage = ImageIO.read(new File(currentBackgroundPath));
            // Skapar en instans med backgroundimage som parameter
            BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
            // Sätter så backgroundpanel blir det som visas i vår Jframe
            frame.setContentPane(backgroundPanel);
            // Uppdaterar/validerar så ändringar går igenom ordentlig
            frame.validate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Stänger ner vår frame.
    private static void showGameOverMessage() {
        JOptionPane.showMessageDialog(frame, "No choice was made. The adventure is over.");
        frame.dispose();
    }

    private static class BackgroundPanel extends JPanel {
        private final BufferedImage backgroundImage;

        // Sätter igång med backgroundimage med den bakgrundsbild som vi använder för olika scener
        public BackgroundPanel(BufferedImage backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Super.paintcomponent metod anrop inom Swing sin helt ocyh håller färgar om bakgrunds färgen helt och hållet.
            // I vårt fall med bilder.
            //https://quarkphysics.ca/ICS4U1/unit4-graphics/paintComponent.html
            super.paintComponent(g);
            // Om bakgrundsImage inte är null så "ritar" vi på vår Jframe genom g-drawImage och bilden är skalad för att anpassa sig till framen.
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
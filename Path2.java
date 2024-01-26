import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Path2 {

    // Huvudmetod där programmet startar
    public static void main(String[] args) {
        forstaVagskalet();
    }

    // Metod för det första vägskälet
    private static void forstaVagskalet() {
        // Skapar en array med knappar och visar en dialog med en bild
        String[] buttons = {"integer x;", "variable x;", "int x;"};
        int result = showOptionDialog("Första vägskälet", null, "Images/Grotta2_1.jpg", buttons);

        // Startar om ("Dör") eller går vidare till nästa quiz på det valda alternativet. Frågan i sig är på bilden
        try {
            switch (result) {
                case 0:
                    func.Die();
                    forstaVagskalet();
                    break;
                case 1:
                    func.Die();
                    forstaVagskalet();
                    break;
                case 2:
                    andraVagskalet();
                    break;
                default:
                    break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Metod för det andra vägskälet
    private static void andraVagskalet() {
        // Skapar en array med knappar och visar en dialog med en bild
        String[] buttons = {"int numbers[] = new int[5];", "int numbers[5] = {1, 2, 3, 4, 5};", "int[] numbers = new int[];"};
        int result = showOptionDialog("Andra vägskälet", null, "Images/Grotta2_2.jpg", buttons);

        // Börjar om från början eller går vidare till quiz 3 beroende på svar
        try {
            switch (result) {
                case 0:
                    tredjeVagskalet();
                    break;
                case 1:
                    func.Die();
                    forstaVagskalet();
                    break;
                case 2:
                    func.Die();
                    forstaVagskalet();
                    break;
                default:
                    break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Metod för det tredje vägskälet
    private static void tredjeVagskalet() {
        // Skapar en array med knappar och visar en dialog med en bild
        String[] buttons = {"loop (int i = 0; i < 5; i++) { }", "for (int i = 0; i < 5; i++) { }", "for (int i = 0; i < 5) { }"};
        int result = showOptionDialog("Tredje vägskälet", null, "Images/Grotta2_3.jpg", buttons);

        // Startar om spelet eller kör Win funktionen och stänger sedan programmet.
        try {
            switch (result) {
                case 0:
                    func.Die();
                    forstaVagskalet();
                    break;
                case 1:
                    func.Win();
                    break;
                case 2:
                    func.Die();
                    forstaVagskalet();
                    break;
                default:
                    break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Metod för att visa en dialog med anpassad bakgrundsbild och knappar
    private static int showOptionDialog(String title, String message, String imagePath, String[] buttons) {
        // Skapar en anpassad JOptionPane med en bakgrundsbild och knappar
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(new ImageIcon(imagePath)), BorderLayout.CENTER);

        int result = JOptionPane.showOptionDialog(
                null,       // Förälderkomponent (null för standard)
                panel,      // Panel med anpassad bakgrundsbild
                title,      // Titel
                JOptionPane.DEFAULT_OPTION,  // Typ av alternativ
                JOptionPane.PLAIN_MESSAGE, // Typ av meddelande
                null,                        // Ikon (null för standard)
                buttons,                     // Alternativ
                buttons[0]);                 // Standardalternativ

        return result;
    }
}

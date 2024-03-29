import java.io.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class func {

    // Sökvägen till filen "statistics.json" lagras
    private static final String JSON_FILE_PATH = "statistics.json";
    // Strängen som ska stå i den filen lagras
    private static final String COUNTER_KEY = "Deathcounter";

    /**
     * Visar dialogruta med dödsbilden samt hur många gånger man har dött
     */
    public static void Die() throws IOException {
        // Hämta det aktuella räknarvärdet från filen
        int currentCount = readJsonFromFile();

        // Öka räknaren med ett
        int newCount = currentCount + 1;

        // Spara det nya räknarvärdet till filen
        writeJsonToFile(newCount);

        // Sparar filvägen till bilden när man dör som en variabel
        String imagePath = "Images/youdied.jpg";

        // Skapar en imageIcon med övre strängens filväg
        ImageIcon imageIcon = new ImageIcon(imagePath);

        // Visar bilden i dialogruta med info om hur många gånger man dött som titel
        JOptionPane.showMessageDialog(null, imageIcon, "You've died " + newCount + " times", JOptionPane.PLAIN_MESSAGE);
    }


    /**
     * Visar dialogruta med vinstbilden
     */
    public static void Win() throws IOException{
        // Sparar filvägen till bilden när man dör som en variabel
        String imagePath = "Images/win.png";

        // Skapar en imageIcon med övre strängens filväg
        ImageIcon imageIcon = new ImageIcon(imagePath);

        // Visar bilden i dialogruta med info om hur många gånger man dött som titel
        JOptionPane.showMessageDialog(null, imageIcon, "You found the exit!", JOptionPane.PLAIN_MESSAGE);
    }

    //Här kontrolleras om filen som anges på rad 5 existerar
    protected static int readJsonFromFile() throws IOException {
        File file = new File(JSON_FILE_PATH);

        //Finns inte filen så anropas metoden writeJsonToFile som skapar filen
        if (!file.exists()) {
            writeJsonToFile(0);
        }

        // Skapar Bufferedreader och öppnar filen
        try (BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE_PATH))) {

            // Läser den första raden från filen och sparar den i variabeln line
            String line = reader.readLine();

            //kontrollerar om line då är null
            if (line != null) {
                // splittar strängen till innan kolontecknet = String och efter kolontecknet = Int
                String[] parts = line.split(":");
                return Integer.parseInt(parts[1].trim());
            }
        }

        return 0;
    }
    // skapar en BufferedWriter som öppnar filen för att skriva
    protected static void writeJsonToFile(int value) throws IOException {
        // Anger den lagrade filvägen till statistics.json
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE_PATH))) {
            // Skriver den lagrade stränger + : + (Nya dödsantalet)
            writer.write(COUNTER_KEY + ": " + value);
        }
    }
}

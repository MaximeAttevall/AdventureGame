import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Ta bort statistics.json innan du kör testet
 */
public class funcTest {

    @Test
    public void testReadJsonFromFile() {
        // Anta att JSON-filen finns och innehåller en giltig sträng
        String fakeJsonData = "Deathcounter: 0";
        try {
            // Skapa en temporär fil med testdata
            Path tempFilePath = Files.createTempFile("tempTestFile", ".json");
            Files.write(tempFilePath, fakeJsonData.getBytes());

            // Testa att läsa från filen
            int result = func.readJsonFromFile();

            // Kontrollerar att resultatet är 0 baserat på det falska JSON-data som skapades
            assertEquals(0, result);

            // Radera den temporära filen efter testet
            Files.delete(tempFilePath);
        } catch (IOException e) {
            // Fångar IOException om det uppstår
            e.printStackTrace();
        }
    }

    @Test
    public void testWriteJsonToFile() {
        try {
            // Testa att skriva till filen och försök sedan läsa från filen för att verifiera
            func.writeJsonToFile(0);

            // Läs från filen
            int result = func.readJsonFromFile();

            // kontrollerar att resultatet är 0
            assertEquals(0, result);
        } catch (IOException e) {
            // Fångar IOException om det uppstår
            e.printStackTrace();
        }
    }
}

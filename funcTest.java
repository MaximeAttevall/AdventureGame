import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class funcTest {

    // Ta bort statistics.json innan testet körs

    private func func;

    @BeforeEach
    public void setUp() throws IOException {
        func = new func();
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Återställer värdet i filen efter varje test
        func.writeJsonToFile(0);
    }

    @Test
    public void testDie() throws IOException {
        // Anropa Die-metoden och lagra det nya dödsantalet
        int deathCount = func.Die();

        // Kontrollera att det nya dödsantalet är 1
        assertEquals(1, deathCount);
    }

    @Test
    public void testWin() throws IOException {
        // Anropa Win-metoden, bör inte kasta några undantag
        assertDoesNotThrow(() -> func.Win());
    }

    @Test
    public void testReadJsonFromFile() throws IOException {
        // Läs det aktuella dödsantalet från filen
        int currentCount = func.readJsonFromFile();

        // Kontrollera att det aktuella dödsantalet är 0 (eftersom ingen död har registrerats ännu)
        assertEquals(0, currentCount);
    }

    @Test
    public void testWriteJsonToFile() throws IOException {
        // Skriv ett nytt värde till filen och läs det direkt efteråt
        int newValue = 42;
        func.writeJsonToFile(newValue);
        int readValue = func.readJsonFromFile();

        // Kontrollera att det värde som skrevs till filen är det samma som det som lästes
        assertEquals(newValue, readValue);
    }
}

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * välj de rätta alternativen för att testen ska gå igenom
 */
public class Path2Test {

    @Test
    public void testForstaVagskalet() {
        // Skapa ett objekt av klassen Path2
        Path2 path2 = new Path2();

        // Anropa metoden och kontrollera att den inte kastar något exception
        path2.forstaVagskalet();

    }

    @Test
    public void testAndraVagskalet() {
        // Skapa ett objekt av klassen Path2
        Path2 path2 = new Path2();

        // Anropa metoden och kontrollera att den inte kastar något exception
        path2.andraVagskalet();

        // Om du vill kontrollera att inget exception kastas, använd bara följande rad:
        // assertDoesNotThrow(() -> path2.andraVagskalet());
    }


    @Test
    public void testTredjeVagskalet() {
        // Skapa ett objekt av klassen Path2
        Path2 path2 = new Path2();

        // Anropa metoden och kontrollera att den inte kastar något exception
        path2.tredjeVagskalet();

        // Om du vill kontrollera att inget exception kastas, använd bara följande rad:
        // assertDoesNotThrow(() -> path2.tredjeVagskalet());
    }

}

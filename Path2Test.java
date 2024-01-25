import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Path2Test {

    @Test
    public void testForstaVagskalet() {
        // Skapa ett objekt av klassen Path2
        Path2 path2 = new Path2();

        // Ge in det korrekta alternativet
        int result = path2.forstaVagskalet();

        // Kontrollera att det korrekta alternativet valdes
        assertEquals(2, result);
    }
    @Test
    public void testAndraVagskalet() {
        Path2 path2 = new Path2();
        int result = path2.andraVagskalet();
        // Kontrollera att det korrekta alternativet valdes
        assertEquals(0, result);
    }

    @Test
    public void testTredjeVagskalet() {
        Path2 path2 = new Path2();
        int result = path2.tredjeVagskalet();
        // Kontrollera att det korrekta alternativet valdes
        assertEquals(1, result);
    }
}

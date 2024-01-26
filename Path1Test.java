import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;
import org.junit.jupiter.api.Test;

class YourTestClass {
    @Test // Bestämmer att det är ett test case som vi hanterar med
    void testCreateFrame() {
        JFrame frame = Path1.createFrame(); // Kallar på våran createFrame metod och sätter den som variabel frame

        assertNotNull(frame); // Kollar så vår frame inte är null = existerar.
        assertEquals("AdventureGame", frame.getTitle()); //Kollar om titeln är AdventureGame
        assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation()); // Kollar om våran exit_on_close fungerar som den ska
    }
}

// Kan göra mer test cases, ifall mer behövs säg gärna till så jobbar jag mer på det!

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MedlemstypTest {

    @Test
    public void fromString_fungerar_och_fallbacks() {
        assertEquals(Medlemstyp.GULD, Medlemstyp.fromString("GULD"));
        assertEquals(Medlemstyp.PLATINA, Medlemstyp.fromString("PLATINA"));
        assertEquals(Medlemstyp.STANDARD, Medlemstyp.fromString("STANDARD"));
        assertEquals(Medlemstyp.STANDARD, Medlemstyp.fromString("VIP")); //Fallback
    }

    @Test
    public void tillSvenskText_fungerar() {
        assertEquals("Standard kund", Medlemstyp.STANDARD.tillSvensktext());
        assertEquals("Guld kund", Medlemstyp.GULD.tillSvensktext());
        assertEquals("Platina kund", Medlemstyp.PLATINA.tillSvensktext());
    }
}
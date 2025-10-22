import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MedlemTest {

    @Test

    public void getters_fungerar() {
        Medlem m = new Medlem("Anna", "AVägen", "a@a", "980529-1111",
                LocalDate.of(2020,1,1), LocalDate.of(2024,5,1), Medlemstyp.GULD);

        assertEquals("Anna", m.namn());
        assertEquals("AVägen", m.adress());
        assertEquals(Medlemstyp.GULD, m.medlemstyp());
    }
}
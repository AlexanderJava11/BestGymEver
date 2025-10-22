import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PtLoggTest {

    @Test
    public void loggar_besok_skriver_rad() throws IOException {
        Medlem medlem = new Medlem("Anna Andersson", "Adress", "A@A", "950529-1111",
                LocalDate.of(2020,1,1), LocalDate.of(2024,5,1), Medlemstyp.GULD);

        PtLogg.loggaBesök(medlem, LocalDate.of(2024,10,22));

        Path loggfil = Path.of("pt-logg.txt");
        assertTrue(Files.exists(loggfil));
        List<String> rader = Files.readAllLines(loggfil);
        assertTrue(rader.get(rader.size()-1).contains("Anna Andersson"));
    }
}
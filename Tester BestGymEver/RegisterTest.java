import org.junit.Test;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegisterTest {

    @Test
    public void laser_in_fil() throws IOException {
        Path fil = Path.of("members.txt");
        Register register = new Register(fil);
        assertTrue("Minst en medlem ska läsas in", register.getAllaMedlemmar().size() > 0);
    }

    @Test
    public void hittar_medlem_pa_personnummer() throws IOException {
        Register register = new Register(Path.of("members.txt"));
        assertTrue(register.hittaMedlem("970629-1111").isPresent());
    }

    @Test
    public void hittar_inte_ogiltig_medlem() throws IOException {
        Register register = new Register(Path.of("members.txt"));
        assertTrue(register.hittaMedlem("000000-0000").isEmpty());
    }

    @Test
    public void beraknar_kategori_nuvarande_tidigare() {
        LocalDate idag = LocalDate.of(2024, 10, 22);
        Medlem aktuell = new Medlem("Marija","M","M@A","010629-1111",
                LocalDate.of(2020,1,1), LocalDate.of(2024,5,1),Medlemstyp.PLATINA);
        Medlem gammal = new Medlem("Maja","M", "M@A", "970529.1111",
                LocalDate.of(2019,1,1), LocalDate.of(2022,1,1), Medlemstyp.GULD);

        assertEquals(MedlemsKategori.NUVARANDE, Register.beräknaKategori(aktuell, idag));
        assertEquals(MedlemsKategori.TIDIGARE, Register.beräknaKategori(gammal, idag));
    }
}
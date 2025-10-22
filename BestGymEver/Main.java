import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

// Huvudklass som  kör programmet BestGymEver
// Hanterar inläsning av medlemmar, användarinmatning och loggning av PT-besök
public class Main {
    public static void main(String[] args) {

        // Förbereder filen som innehåller alla medlemmar i gymmet
        Path fil = Paths.get("members.txt");

        // Försöker skapa en Register objekt som läser in min fil
        // Vid fel visas ett meddelande och programmet avslutas.
        Register register;
        try {
            register = new Register(fil);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Kunde inte läsa filen: " + fil.toAbsolutePath() + "\nFel: " + e.getMessage(),
                    "Fel vid inläsning", JOptionPane.ERROR_MESSAGE);
            return;
            // Avsluta programmet om registret inte kunde laddas
        }

        // Ber användaren skriva in namn eller sin personnummer
        String inmatning = JOptionPane.showInputDialog(null,
                "Skriv kundens namn eller personnummer:",
                "Kundinmatning", JOptionPane.QUESTION_MESSAGE);

        // Avbryter om användaren klickar "Avbryt" eller lämnar tomt
        if (inmatning == null || inmatning.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Ingen inmatning. Avslutar.", "Avslutat", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Söker efter medlem i registret (returnerar Optional)
        Optional<Medlem> kanskeMedlem = register.hittaMedlem(inmatning.trim());

        // Om medlemmen finns inte finns - visa varning och sluta
        if (kanskeMedlem.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Personen finns inte i filen och är därför obehörig i detta gym.",
                    "Ej behörig", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Hämtar medlem och beräknar vilken kategori den tillhör (nuvarande/tidigare)
        Medlem medlem = kanskeMedlem.get();
        MedlemsKategori kategori = register.beräknaKategori(medlem, LocalDate.now());

        // Bygger upp texten med medlemsinformation
        StringBuilder info = new StringBuilder();
        info.append("Namn: ").append(medlem.namn()).append("\n");
        info.append("Personnummer: ").append(medlem.personnummer()).append("\n");
        info.append("Medlemstyp: ").append(medlem.medlemstyp()).append("\n");
        info.append("Senaste betalt: ").append(medlem.senasteBetalt()).append("\n");
        info.append("Kategori: ").append(kategori.tillSvensktext()).append("\n");

        // Medlemstyp kan vara null - visa "okänd" i så fall
        if (medlem.medlemstyp() != null) {
            info.append("Medlemstyp: ").append(medlem.medlemstyp().tillSvensktext()).append("\n");
        } else {
            info.append("Medlemstyp: (okänd)").append("\n");
        }

        // Visar alla medlemsinformation i en dialogruta
        JOptionPane.showMessageDialog(
                null,
                info.toString(),
                "Medlemsinformation",
                JOptionPane.INFORMATION_MESSAGE);

        // Logga PT-besök om medlemmen är nuvarande (aktiv kund)
        if (kategori == MedlemsKategori.NUVARANDE) {
            try {
                PtLogg.loggaBesök(medlem, LocalDate.now());
                JOptionPane.showMessageDialog(null,
                        "PT-loggen är uppdaterad (pt-logg.txt).",
                        "Logg sparad", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Kunde inte skriva till PT-loggen: " + e.getMessage(),
                        "Fel vid inloggning", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Om medlemmen inte är nuvarande visas info att logg inte sparas
            JOptionPane.showMessageDialog(null,
                    "Ingen PT-Logg skrivs eftersom personen inte är betalande kund.",
                    "Ingen loggning", JOptionPane.INFORMATION_MESSAGE);
        }

        JOptionPane.showMessageDialog(null,
                MotivationsTips.slumpaTips(),
                "Dagens motivation",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
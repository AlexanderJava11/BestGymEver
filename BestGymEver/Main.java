import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        // Förbered indatafilen som innehåller alla medlemmar på BestGymEver
        // Paths.get ("members.txt") letar efter filen
        Path fil = Paths.get("members.txt");

        // Försöker skapa en Register objekt som läser in min fil
        // Samt fångar in alla fel såsom "fil saknas" och visar dialog till användaren
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

        // Hanterar avbruten eller tom inmatning
        if (inmatning == null || inmatning.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Ingen inmatning. Avslutar.", "Avslutat", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Slår ihop medlemmar i registret (retunerar optional)
        Optional<Medlem> kanskeMedlem = register.hittaMedlem(inmatning.trim());

        // Saknas en medlem så visa varning och avsluta programmet
        if (kanskeMedlem.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Personen finns inte i filen och är därför obehörig i detta gym.",
                    "Ej behörig", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Hämtar medlem och beräkknar kategori (nuvarande/föredetta/obetalande) för dagens datum
        Medlem medlem = kanskeMedlem.get();
        MedlemsKategori kategori = register.beräknaKategori(medlem, LocalDate.now());

        // Bygger upp en enkel text med info att visa
        StringBuilder info = new StringBuilder();
        info.append("Namn: ").append(medlem.namn()).append("\n");
        info.append("Personnummer: ").append(medlem.personnummer()).append("\n");
        info.append("Medlemstyp: ").append(medlem.medlemstyp()).append("\n");
        info.append("Senaste betalt: ").append(medlem.senasteBetalt()).append("\n");
        info.append("Kategori: ").append(kategori.tillSvensktext()).append("\n");

        // Medlemstyp kan vara null, visa "okänd" om den saknas
        if (medlem.medlemstyp() != null) {
            info.append("Medlemstyp: ").append(medlem.medlemstyp().tillSvensktext()).append("\n");
        } else {
            info.append("Medlemstyp: (okänd)").append("\n");
        }

        // Visa sammanställd medlemsinformation
        JOptionPane.showMessageDialog(
                null,
                info.toString(),
                "Medlemsinformation",
                JOptionPane.INFORMATION_MESSAGE);

        // Logga PT-besök endast för nuvarande (betalande kunder)
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
            JOptionPane.showMessageDialog(null,
                    "Ingen PT-Logg skrivs eftersom personen inte är betalande kund.",
                    "Ingen loggning", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
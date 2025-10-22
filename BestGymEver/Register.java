import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Register {

    // Gemensamt datumformat som används när vi tolkar datum från filen
    private static final DateTimeFormatter DATUMFORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Håller alla inlästa medlemmar i minnet
    private final List<Medlem> allaMedlemmar = new ArrayList<>();

    // Skapar ett register och läser direkt in filen.
    public Register(Path fil) throws IOException {
        läsInFil(fil);
    }

    /**
     * Läser in medlemsfilen rad för rad och bygger upp listan 'allaMedlemmar'.
     * Hoppar över tomma rader, kommentarer (#) och rubriksrader.
     * Validerar att varje data rad har exakt 7 fält.
     * Parsar datum med format YYYY-MM-DD och ger tydligt felmeddelnade vid fel.
     */

    private void läsInFil(Path fil) throws IOException {
        int radnummer = 0;

        try (BufferedReader reader = Files.newBufferedReader(fil, StandardCharsets.UTF_8)) {
            String rad;
            while ((rad = reader.readLine()) != null) {
                radnummer++;

                String tommaRader = rad.trim();

                // Hoppa över tomma rader, kommentarsrader och rubriker
                if (tommaRader.isEmpty() || tommaRader.startsWith("#") || ärRubrikrad(tommaRader)) {
                    continue;
                }

                /**
                 * Dela upp rader i fält med ett eller flera whitespace som separator.
                 * "\\s*;\\s*" = dela på semikolan (;) med mellanslag runt
                 * -1 = behåller tomma fält i resultatet.
                 */
                String[] fält = tommaRader.split("\\s*;\\s*", -1 );

                // Säkerställ att vi har rätt antal fält
                if (fält.length != 7) {
                    throw new IOException("Rad " + radnummer + " har " + fält.length +
                            " fält (förväntade 7). Raden var : \"" + rad + "\"");
                }

                // Trimmar alla fält
                for (int i = 0; i < fält.length; i++) {
                    fält[i] = fält[i].trim();
                }

                // Mappar fält = variabler
                String namn = fält[0];
                String adress = fält[1];
                String epost = fält[2];
                String personnummer = fält[3];
                LocalDate blevMedlem = tolkaDatum(fält[4], radnummer);
                LocalDate senasteBetalt = tolkaDatum(fält[5], radnummer);
                String medlemsTyp = fält[6];

                // Konvertera medlemstyp från text -> enum
                Medlemstyp medlemstyp = Medlemstyp.fromString(fält[6]);

                // Skapa och spara medlemmaen
                allaMedlemmar.add(new Medlem(namn,adress,epost,personnummer,blevMedlem,senasteBetalt,medlemstyp));
            }
        } catch (IOException e) {
            // Paketerar om med filens absoluta sökväg för extra tydlighet
            throw new IOException("Kunde inte läsa filen: " + fil.toAbsolutePath() +
                    "\nFel: " + e.getMessage(), e);
        }
    }

    // Heuristik för att känna igen en rubrikrad såsom "namn; personnummer; datum; betalt..."
    private boolean ärRubrikrad(String rad) {
        String lower = rad.toLowerCase(Locale.ROOT);
        return lower.contains("namn") && lower.contains("person") &&
                (lower.contains("datum") || lower.contains("betalt"));
    }
    /**
     * Sök efter en medlem via personnummer eller namn
     * Retunerar Optional.empty() om ingen träff hittas.
     */
    public Optional<Medlem> hittaMedlem(String sökterm) {
        if (sökterm == null) return Optional.empty();
        String mål = sökterm.trim();

        for (Medlem m : allaMedlemmar) {
            if (m.personnummer().equalsIgnoreCase(mål) || m.namn().equalsIgnoreCase(mål)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }
    /**
     * Avgör medlemskategori utifrån senaste betalningsdatum:
     * - NUVARANDE: har betalat efter (idag minus 1år)
     * - TIDIGARE: annars
     */
    public static MedlemsKategori beräknaKategori(Medlem medlem, LocalDate idag) {
        LocalDate gräns = idag.minusYears(1);
        if (medlem.senasteBetalt().isAfter(gräns)) {
            return MedlemsKategori.NUVARANDE;
        } else {
            return MedlemsKategori.TIDIGARE;
        }
    }

    // Ger en oföränderlig vy av alla medlemmar
    public List<Medlem> getAllaMedlemmar() {
        return Collections.unmodifiableList(allaMedlemmar);
    }

    // Tolkar ett datumfält och ger ett tydligt felmeddelande om formatet är fel.
    private LocalDate tolkaDatum(String text, int radnummer) throws IOException {
        try {
            return LocalDate.parse(text.trim(), DATUMFORMAT);
        } catch (DateTimeParseException e) {
            throw new IOException("Fel datumformat på rad " + radnummer +
                    "- använd format YYYY-MM-DD. Felaktigt värde: \"" + text + "\"", e);
        }
    }
}
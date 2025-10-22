import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

// Klass som hanterar loggning av PT-besök
public class PtLogg {

    // Filen där alla PT-besök sparas
    private static final Path LOGGFIL = Paths.get("pt-logg.txt");

    // Skriver ett PT-besök till loggfilen
    public static void loggaBesök(Medlem medlem, LocalDate datum) throws IOException {
        String rad = datum + " " + medlem.namn() + " " + medlem.personnummer();

        // Öppnar filen för att skriva(skapar om den inte finns, lägger till annars)
        try (BufferedWriter writer = Files.newBufferedWriter(
                LOGGFIL,
                StandardCharsets.UTF_8,         // Texten skrivs i UTF_8 format som är stöd för å, ä, ö.
                StandardOpenOption.CREATE,      // Skapar filen om den inte redan finns.
                StandardOpenOption.APPEND)) {   // Lägger till ny text längst ner i filen istället för att skriva över den.
            writer.write(rad);  // Skriver raden till filen
            writer.newLine();   // Ny rad efter varje inlägg
        }
    }
}
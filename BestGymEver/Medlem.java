import java.time.LocalDate;

/**
 * En record som representerar en medlem i gymmet.
 * Records används för att lagra data på ett enkelt och oföränderligt sätt.
 * Varje fält blir automatiskt en final variabel med getter-metoder.

 */

public record Medlem(
        String namn,                // Medlemmens fullständiga namn
        String adress,              // Address
        String epost,               // E-postaddress
        String personnummer,        // Personnummer
        LocalDate blevMedlem,       // Datum då personen blev medlem
        LocalDate senasteBetalt,    // Datum för senaste betalning
        Medlemstyp medlemstyp) {    // Typ av medlemskap (SÅKLART ÄR VI PLATINUM)
}
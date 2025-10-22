// Enum som visar vilken typ av medlemskap en kund har
public enum Medlemstyp {
    STANDARD, GULD, PLATINA;

    // Gör om en text till rätt Medlemstyp
    public static Medlemstyp fromString(String text) {
        if (text == null) return null;
        return switch (text.trim().toUpperCase()) {
            case "STANDARD" -> STANDARD;
            case "GULD" -> GULD;
            case "PLATINA" -> PLATINA;
            default -> throw new IllegalArgumentException("Okänt medlemstyp " + text);
        };
    }

    // Returnerar svensk text för varje medlemstyp
    public String tillSvensktext() {
        return switch (this) {
            case STANDARD -> "Standard kund";
            case GULD -> "Guld kund";
            case PLATINA -> "Platina kund";
        };
    }
}

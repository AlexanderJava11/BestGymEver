/**
Enum som visar vilken typ av kund en person är
En enum innehåller fasta värden som inte kan ändras
 */
public enum MedlemsKategori {
    NUVARANDE, TIDIGARE, OBEHÖRIG;

    // Retunerar svensk text för varje kategori
    public String tillSvensktext() {
        switch (this) {
            case NUVARANDE:
                return "Nuvarande kund";
            case TIDIGARE:
                return "Tidigare kund";
            default:
                return "Obehörig kund";
        }
    }
}

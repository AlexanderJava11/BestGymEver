import java.util.List;
import java.util.Random;

public class MotivationsTips {
    private static final List<String> tips = List.of(
            "Kom ihåg: Du ångrar aldrig ett träningspass!",
            "Träna för att må bra, inte bara för att se bra ut \uD83D\uDCAA",
            "Lite träning är bättre än ingen träning alls!",
            "Svetten är bara fett som gråter \uD83D\uDE05",
            "Ge inte upp, varje steg räknas");

    public static String slumpaTips() {
        Random rand = new Random();
        return tips.get(rand.nextInt(tips.size()));
    }
}

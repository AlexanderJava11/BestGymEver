import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Betalning {

    private static final double KLARNA_MÅNANDSAVGIFT = 59.0;    // kr per månad
    private static final double KLARNA_ÅRSRÄNTA = 0.0499;       // 4.99% årlig ränta

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Välj gymkort/plan
        System.out.println("Välj ditt gymkort:");
        System.out.println("1. Standard     - 299kr/mån");
        System.out.println("2. Guld         - 399kr/mån");
        System.out.println("3. Platina      - 499kr/mån");
        System.out.print("Ange ditt val (1-3)");

        int planVal = safeNextInt(scanner);
        double price;   // Grundpris (delar upp detta vid delbetalning)
        String plan;

        switch (planVal) {
            case 1 -> {
                price = 299;
                plan = "Standard";
            }
            case 2 -> {
                price = 399;
                plan = "Guld";
            }
            case 3 -> {
                price = 499;
                plan = "Platina";
            }
            default -> {
                System.out.println("Ogiltigt val för gymkort.");
                scanner.close();
                return;
            }
        }

        // Välj betalningsmetod
        System.out.println("\nVälj betalningsmetod:");
        System.out.println("1. Bankbetalning");
        System.out.println("2. PayPal");
        System.out.println("3. Swish");
        System.out.println("4. Klarna (delbetalning");
        System.out.println("Ange ditt val (1-4)");

        int metodVal = safeNextInt(scanner);
        String metod;
        int månader = 1;    // Standard - ingen delbetalning
        double månadspris = price;  // Default

        switch (metodVal) {
            case 1:
                metod = "Bankbetalning";
                break;
            case 2:
                metod = "PayPal";
                break;
            case 3:
                metod = "Swish";
                break;
            case 4:
                metod = "Klarna (delbetalning)";
                månader = frågaAntalMånader(scanner);   // 3 / 6 / 12 månader
                månadspris = beräknaKlarnaMånadsbelopp(price, månader, KLARNA_ÅRSRÄNTA, KLARNA_MÅNANDSAVGIFT);
                break;
            default:
                System.out.println("Ogiltigt val för betalning.");
                scanner.close();
                return;
        }

        // Sammanfattning och eventuellt delbetalning
        System.out.println("\nDu har valt: " + plan + " - " + price + " kr/mån");
        System.out.println("Betalningsmetod: " + metod);
        if (metod.startsWith("Klarna")) {
            double total = avrunda2(månadspris * månader);
            System.out.println("Delbetalning: " + månader + " månader x " + månadspris + " kr/mån (inklusive avgifter/ränta)");
            System.out.println("Total att betala: " + total + " kr");
        }

        // Bekräftelse
        System.out.println("Vill du gå vidare till betalning? (JA/NEJ): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("JA")) {
            System.out.println("\nInitierar " + metod + " ...");
            simuleringsProccess(7);

            boolean success = random.nextDouble() < 0.5;    // 50/50 simulering
            String orderNummer = generateOrderNummer();

            if (success) {
                System.out.println("\n✅ Betalning genomförd via " + metod + "!");
                System.out.println("Tack för ditt köp av " + plan + "-medlemskap.");
               if (metod.startsWith("Klarna")) {
                   System.out.println("Din delbetalningsplan: " + månader + " månader x " + månadspris + " kr/mån.");
               }
                System.out.println("Ordernummer: " + orderNummer);
            } else {
                System.out.println("\n❌ Betalning misslyckades via " + metod + ".");
                System.out.println("Vänligen försök igen senare");
                System.out.println("Ordernummer: " + orderNummer);
            }
        } else {
            System.out.println("Betalning avbruten.");
        }
        scanner.close();
    }

    /* Beräkning för Klarna Delbetalning
    Ränta per månad: (årsränta / 12) * antal månader
    Månadsavgift adderas varje månad
     */
    private static double beräknaKlarnaMånadsbelopp(double grundPris, int månader, double årsRänta, double månadsAvgift) {
        double månadsRänta = årsRänta / 12.0;
        double räntafaktor = 1 + (månader * månadsRänta);
        double kapitalPerMånad = (grundPris * räntafaktor) / månader;
        double belopp = kapitalPerMånad + månadsAvgift;
        return avrunda2(belopp);
    }

    private static int safeNextInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            }catch (NumberFormatException e) {
                System.out.println("Ogiltigt inmatning. Ange ett tal: ");
            }
        }
    }

    private static int frågaAntalMånader(Scanner scanner) {
        System.out.println("Välj antal månader (3, 6 eller 12): ");
        int m = safeNextInt(scanner);
        if (m != 3 && m != 6 && m != 12) {
            System.out.println("Ogiltig val - sätter 6 månader.");
            m = 6;
        }
        return m;
    }

    private static double avrunda2(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    private static void simuleringsProccess(int sekunder) {
        for (int i = 0; i < sekunder; i++) {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {}
            System.out.println(".");
        }
    }

    private static String generateOrderNummer() {
        String datum = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomPart = 1000 + new Random().nextInt(9000);
        return datum + String.valueOf(randomPart);
    }
}
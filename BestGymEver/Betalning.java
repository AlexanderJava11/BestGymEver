import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Betalning {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Välj ditt gymkort:");
        System.out.println("1. Standard - 299kr/mån");
        System.out.println("2. Guld - 399kr/mån");
        System.out.println("3. Platina - 499kr/mån");
        System.out.print("Ange ditt val (1-3)");

        int planVal = safeNextInt(scanner);
        double price;
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

        System.out.println("\nVälj betalningsmetod:");
        System.out.println("1. Bankbetalning");
        System.out.println("2. PayPal");
        System.out.println("3. Swish");
        System.out.println("Ange ditt val (1-3)");

        int betalningsmetod = safeNextInt(scanner);
        String metod = "";
        switch (betalningsmetod) {
            case 1:
                metod = "Bankbetalning";
                break;
            case 2:
                metod = "PayPal";
                break;
            case 3:
                metod = "Swish";
                break;
            default:
                System.out.println("Ogiltigt val för gymkort.");
                scanner.close();
                return;
        }

        System.out.println("\nDu har valt: " + plan + " - " + price + " kr/mån");
        System.out.println("Betalningsmetod: " + metod);
        System.out.println("Vill du gå vidare till betalning? (JA/NEJ): ");
        String confirm = scanner.next();

        if (confirm.equalsIgnoreCase("JA")) {
            System.out.println("\nInitierar " + metod + " ...");
            simuleringsProccess(5);

            boolean success = random.nextDouble() < 0.7;
            String orderNummer = generateOrderNummer();

            if (success) {
                System.out.println("\n✅ Betalning genomförd via " + metod + "!");
                System.out.println("Tack för ditt köp av " + plan + "-medlemskap.");
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

    private static int safeNextInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Ogiltig inmatning. Ange ett tal: ");
            scanner.next();
        }
        return scanner.nextInt();
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
import java.util.Scanner;

public class Betalnig {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Välj ditt gymkort:");
        System.out.println("1. Standard - 299kr/mån");
        System.out.println("2. Guld - 399kr/mån");
        System.out.println("3. Platina - 499kr/mån");
        System.out.print("Ange ditt val (1-3)");

        int choice = scanner.nextInt();
        double price = 0;
        String plan = "";

        switch (choice) {
            case 1:
                price = 299;
                plan = "Standard";
                break;
            case 2:
                price = 399;
                plan = "Guld";
                break;
            case 3:
                price = 499;
                plan = "Platina";
                break;
            default:
                System.out.println("Ogiltigt val.");
                System.exit(0);
        }

        System.out.println("\nDu har valt: " + plan + " - " + price + " kr/mån");
        System.out.println("Vill du gå vidare till betalning? (JA/NEJ): ");
        String confirm = scanner.next();

        if (confirm.equalsIgnoreCase("JA")) {
            System.out.println("\nBearbetar betalning...");
            System.out.println("Betalning genomförd! Tack för ditt köp av " + plan + "-medlemskap.");
        } else {
            System.out.println("Betalning avbruten.");
        }
        scanner.close();
    }
}

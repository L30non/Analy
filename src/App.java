import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        boolean isRunning = true;

        while (isRunning) {
            if (User.isLoggedIn) {
                TransactionTracker.showMainMenu();
                continue;
            }

            System.out.println("================================");
            System.out.println("        Welcome to Analy        ");
            System.out.println("================================");

            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            System.out.println("================================");

            System.out.print("Enter your choice (1 - 3): ");
            String mode = input.next();

            switch (mode) {
                case "1":
                    User.login();
                    break;
                case "2":
                    User.register();
                    break;
                case "3":
                    System.out.println("Thank you for using Analy!");
                    input.close();
                    isRunning = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}

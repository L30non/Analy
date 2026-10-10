import java.util.Scanner;

public class App {
    // ============== Authentication System ==============
    static boolean isLoggedIn = false;

    static boolean isPasswordStrong(String password) {
        /* Should have at least one special character
         * Should have at least five characters long
         * Should have at least one number
         * Should have at least one Capital letter */

        boolean isContainSpecialChar = password.matches(".*[^a-zA-Z0-9\\s].*");
        boolean isContainOneNum = password.matches(".*[0-9].*");
        boolean isContainCapitalChar = password.matches(".*[A-Z].*");
        boolean isFiveCharLong = password.length() >= 5;

        if (isContainSpecialChar && isContainOneNum && isContainCapitalChar && isFiveCharLong) {
            return true;
        } else {
            return false;
        }
    }

    static void register()  {
        Scanner input = new Scanner(System.in);

        String username = "";
        while (username.isEmpty()) {
            System.out.print("Enter your username: ");
            username = input.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("Error: Username cannot be empty. Please try again.\n");
            }
        }

        String password = "";
        boolean isStrong = false;
        do {
            System.out.print("Enter your password: ");
            password = input.next().trim();;

            if (password.isEmpty()) {
                System.out.println("Please enter your username and password");
            } else if (!isPasswordStrong(password)) {
                System.out.println("Please enter a strong password");
            } else {
                isStrong = true;
            }
        } while (!isStrong);

        System.out.println("Successfully create an account");
    }

    static void login() {
        Scanner input = new Scanner(System.in);

        String username = "";
        while (username.isEmpty()) {
            System.out.print("Enter your username: ");
            username = input.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("Error: Username cannot be empty. Please try again.\n");
            }
        }

        String password = "";
        while (password.isEmpty()) {
            System.out.print("Enter your password: ");
            password = input.next().trim();

            if (password.isEmpty()) {
                System.out.println("Error: Password cannot be empty. Please try again.\n");
            }
        }

        /* For simplicity, any non-empty credentials = successful login */
        isLoggedIn = true;
        System.out.println("Successfully logged in!\n");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Analy!");

        System.out.println("Enter 1 (login), 2 (register): "); // Add more option or custom more
        String mode = input.next();

        switch (mode) {
            case "1":
                login();
                if (isLoggedIn) {
                    TransactionTracker.showMenu();
                }
                break;
            case "2":
                register();
                break;
            default:
                System.out.println("Invalid Option");
        }
    }
}

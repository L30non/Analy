import java.util.Scanner;

public class TransactionTracker {
    // ============== Storage Arrays ==============
    static String[] expenseNames = new String[100];
    static double[] expenseAmounts = new double[100];
    static String[] expenseCategories = new String[100];
    static int expenseCount = 0;

    // ============== Main Menu ==============
    public static void showMenu(boolean isRunning) {
        Scanner input = new Scanner(System.in);

        while (isRunning) {
            System.out.println("\n========================================");
            System.out.println("      Transaction Tracking System      ");
            System.out.println("========================================");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Update Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice (1-5): ");

            String choice = input.next();

            switch (choice) {
                case "1":
                    addExpense();
                    break;
                case "2":
                    viewExpenses();
                    break;
                case "3":
                    updateExpense();
                    break;
                case "4":
                    deleteExpense();
                    break;
                case "5":
                    User.isLoggedIn = false;
                    isRunning = false;
                    System.exit(0);
                    System.out.println("Thank you for using Analy!");
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1-5.");
            }
        }
    }

    // ============== Create Expense ==============
    static void addExpense() {
        System.out.println("\n--- Add New Expense ---");
        System.out.println("Coming soon: Add a new expense");
        // Scanner input = new Scanner(System.in);

        // /* Check if storage is full */
        // if (expenseCount >= expenseNames.length) {
        //     System.out.println("Error: Cannot add more expenses. Storage is full.");
        //     return;
        // }

        // System.out.println("\n--- Add New Expense ---");

        // // STEP 1: Get expense name/description
        // input.nextLine(); // Clear buffer
        // String name = "";
        // while (name.isEmpty()) {
        //     System.out.print("Enter expense name/description: ");
        //     name = input.nextLine().trim();

        //     if (name.isEmpty()) {
        //         System.out.println("Error: Expense name cannot be empty. Please try again.");
        //     }
        // }

        // // STEP 2: Get and validate amount
        // String amountStr = "";
        // double amount = 0.0;
        // boolean validAmount = false;

        // while (!validAmount) {
        //     System.out.print("Enter amount: ");
        //     amountStr = input.next().trim();

        //     if (isValidAmount(amountStr)) {
        //         amount = Double.parseDouble(amountStr);
        //         if (amount > 0) {
        //             validAmount = true;
        //         } else {
        //             System.out.println("Error: Amount must be positive. Please try again.");
        //         }
        //     } else {
        //         System.out.println("Error: Invalid amount format. Please enter a valid number.");
        //     }
        // }

        // // STEP 3: Get category
        // input.nextLine(); // Clear buffer again
        // System.out.println("Available categories:");
        // System.out.println("  - Food");
        // System.out.println("  - Transport");
        // System.out.println("  - Entertainment");
        // System.out.println("  - Others");

        // String category = "";
        // while (category.isEmpty()) {
        //     System.out.print("Enter category: ");
        //     category = input.nextLine().trim();

        //     if (category.isEmpty()) {
        //         System.out.println("Error: Category cannot be empty. Please try again.");
        //     }
        // }

        // // STEP 4: Confirmation
        // System.out.println("\n--- Expense Details ---");
        // System.out.println("Name: " + name);
        // System.out.println("Amount: $" + String.format("%.2f", amount));
        // System.out.println("Category: " + category);
        // System.out.print("\nAre you sure you want to add this expense? (yes/no): ");

        // String confirmation = input.next().trim().toLowerCase();

        // if (confirmation.equals("yes")) {
        //     // STEP 5: Save the expense
        //     expenseNames[expenseCount] = name;
        //     expenseAmounts[expenseCount] = amount;
        //     expenseCategories[expenseCount] = category;
        //     expenseCount++;

        //     System.out.println("Success: Expense added successfully!");
        // } else {
        //     System.out.println("Expense not added. Returning to menu.");
        // }
    }

    // ============== Validation Helper ==============
    static boolean isValidAmount(String amountStr) {
        try {
            Double.parseDouble(amountStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // ============== Read Expenses ==============
    static void viewExpenses() {
        System.out.println("\n--- View Expenses ---");

        if (expenseCount == 0) {
            System.out.println("No expenses recorded yet.");
        } else {
            System.out.println("Coming soon: View all expenses");
        }
    }

    // ============== Update Expense ==============
    static void updateExpense() {
        System.out.println("\n--- Update Expense ---");
        System.out.println("Coming soon: Update an existing expense");
    }

    // ============== Delete Expense ==============
    static void deleteExpense() {
        System.out.println("\n--- Delete Expense ---");
        System.out.println("Coming soon: Delete an existing expense");
    }
}

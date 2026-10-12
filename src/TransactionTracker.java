import java.util.Scanner;

public class TransactionTracker {
    
    // ============== Storage Arrays ==============
    static String[] categoryNames = new String[100];
    static int categoryCount = 0;

    static String[] expenseNames = new String[100];
    static String[] expenseDescriptions = new String[100];
    static double[] expenseAmounts = new double[100];
    static int[] expenseCategoryIds = new int[100];
    static int expenseCount = 0;

    public static void main(String[] args) {
        showMainMenu();
    }

    // ============== Main Menu ==============
    public static void showMainMenu() {
        Scanner input = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n========================================");
            System.out.println("        Transaction Tracking System       ");
            System.out.println("========================================");
            System.out.println("1. View All Categories & Expenses");
            System.out.println("2. Select Category to Manage");
            System.out.println("3. Add Category");
            System.out.println("4. Delete Category");
            System.out.println("5. Update Category Name");
            System.out.println("0. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");

            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    viewAll();
                    break;
                case "2":
                    selectCategoryWorkspace(input);
                    break;
                case "3":
                    addCategory(input);
                    viewAll();
                    break;
                case "4":
                    deleteCategory(input);
                    viewAll();
                    break;
                case "5":
                    updateCategory(input);
                    viewAll();
                    break;
                case "0":
                    running = false;
                    System.out.println("Thank you for using Transaction Tracking System!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please enter a valid choice.");
            }
        }
        input.close();
    }

    // ============== Category Workspace (Sub-Menu) ==============
    static void selectCategoryWorkspace(Scanner input) {
        viewAll();
        if (categoryCount == 0) return;

        System.out.print("Enter Category ID you want to work with: ");
        int catId;
        try {
            catId = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format.");
            return;
        }

        int index = catId - 1;

        if (index < 0 || index >= categoryCount) {
            System.out.println("Error: Category not found.");
            return;
        }

        boolean inCategory = true;
        while (inCategory) {
            System.out.println("\n----------------------------------------");
            System.out.println(" Active Category: " + categoryNames[index] + " [ ID = " + catId + " ]");
            System.out.println("----------------------------------------");
            System.out.println("1. View Expenses in this Category");
            System.out.println("2. Add Expense to this Category");
            System.out.println("3. Delete Expense from this Category");
            System.out.println("4. Update Expense in this Category");
            System.out.println("0. Back to Main Menu");
            System.out.println("----------------------------------------");
            System.out.print("Choose an action: ");

            String subChoice = input.nextLine().trim();

            switch (subChoice) {
                case "1":
                    displayCategoryDetails(catId);
                    break;
                case "2":
                    addExpenseToCategory(input, catId);
                    displayCategoryDetails(catId);
                    break;
                case "3":
                    deleteExpenseFromCategory(input, catId);
                    displayCategoryDetails(catId);
                    break;
                case "4":
                    updateExpenseInCategory(input, catId);
                    break;
                case "0":
                    inCategory = false;
                    System.out.println("Exiting category workspace...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // ============== Display Single Category Details ==============
    static void displayCategoryDetails(int catId) {
        int catIndex = catId - 1;
        System.out.println("\nCategory: " + categoryNames[catIndex] + " [ ID = " + catId + " ]");
        
        boolean hasExpenses = false;
        for (int j = 0; j < expenseCount; j++) {
            if (expenseCategoryIds[j] == catId) {
                hasExpenses = true;
                break;
            }
        }

        if (!hasExpenses) {
            System.out.println("   -> No expenses in this category.");
        } else {
            System.out.println(String.format("    %-6s | %-12s | %-18s | %s", "ID", "Name", "Description", "Amount"));
            System.out.println("    --------------------------------------------------------");
            
            int displayId = 1;
            for (int j = 0; j < expenseCount; j++) {
                if (expenseCategoryIds[j] == catId) {
                    System.out.println(String.format("    %-6d | %-12s | %-18s | $%.2f", 
                        displayId, 
                        expenseNames[j], 
                        expenseDescriptions[j], 
                        expenseAmounts[j]
                    ));
                    displayId++;
                }
            }
        }
        System.out.println();
    }

    // ============== View All ==============
    static void viewAll() {
        System.out.println("\n--- View All Categories & Expenses ---");
        if (categoryCount == 0) {
            System.out.println("No categories available.");
            return;
        }

        for (int i = 0; i < categoryCount; i++) {
            displayCategoryDetails(i + 1);
        }
    }

    // ============== Add Category (With Duplicate Check) ==============
    static void addCategory(Scanner input) {
        System.out.println("\n--- Add Category ---");
        if (categoryCount >= categoryNames.length) {
            System.out.println("Error: Category storage is full.");
            return;
        }

        String name = "";
        while (true) {
            System.out.print("Enter category name: ");
            name = input.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Error: Category name cannot be empty.");
                continue;
            }

            boolean exists = false;
            for (int i = 0; i < categoryCount; i++) {
                if (categoryNames[i].equalsIgnoreCase(name)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                System.out.println("Error: A category with this name already exists. Please enter a different name.");
            } else {
                break;
            }
        }

        categoryNames[categoryCount] = name;
        categoryCount++;
        System.out.println("Success: Category added successfully!");
    }

    // ============== Delete Category (With Blocking Rule) ==============
    static void deleteCategory(Scanner input) {
        System.out.println("\n--- Delete Category ---");
        System.out.print("Enter Category ID to delete: ");
        int catId;
        try {
            catId = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format.");
            return;
        }

        int index = catId - 1;

        if (index < 0 || index >= categoryCount) {
            System.out.println("Error: Category not found.");
            return;
        }

        for (int j = 0; j < expenseCount; j++) {
            if (expenseCategoryIds[j] == catId) {
                System.out.println("Error: Cannot delete category because it still contains expenses!");
                System.out.println("Please delete those expenses first.");
                return;
            }
        }

        for (int i = index; i < categoryCount - 1; i++) {
            categoryNames[i] = categoryNames[i + 1];
        }
        categoryNames[categoryCount - 1] = null;
        categoryCount--;

        for (int j = 0; j < expenseCount; j++) {
            if (expenseCategoryIds[j] > catId) {
                expenseCategoryIds[j]--;
            }
        }

        System.out.println("Success: Category deleted successfully!");
    }

    // ============== Update Category Name ==============
    static void updateCategory(Scanner input) {
        System.out.println("\n--- Update Category Name ---");
        System.out.print("Enter Category ID to update: ");
        int catId;
        try {
            catId = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format.");
            return;
        }

        int index = catId - 1;

        if (index < 0 || index >= categoryCount) {
            System.out.println("Error: Category not found.");
            return;
        }

        System.out.print("Enter new category name: ");
        String newName = input.nextLine().trim();
        if (newName.isEmpty()) {
            System.out.println("Error: Category name cannot be empty.");
            return;
        }

        categoryNames[index] = newName;
        System.out.println("Success: Category name updated successfully!");
    }

    // ============== Add Expense to Specific Category ==============
    static void addExpenseToCategory(Scanner input, int catId) {
        System.out.println("\n--- Add Expense ---");
        if (expenseCount >= expenseNames.length) {
            System.out.println("Error: Expense storage is full.");
            return;
        }

        System.out.print("Enter expense name: ");
        String name = input.nextLine().trim();
        while (name.isEmpty()) {
            System.out.println("Error: Expense name cannot be empty.");
            System.out.print("Enter expense name: ");
            name = input.nextLine().trim();
        }

        System.out.print("Enter description (optional): ");
        String desc = input.nextLine().trim();

        double amount = 0.0;
        while (true) {
            System.out.print("Enter amount: ");
            String amtStr = input.nextLine().trim();
            try {
                amount = Double.parseDouble(amtStr);
                if (amount > 0) {
                    break;
                } else {
                    System.out.println("Error: Amount must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter valid numbers only.");
            }
        }

        expenseNames[expenseCount] = name;
        expenseDescriptions[expenseCount] = desc;
        expenseAmounts[expenseCount] = amount;
        expenseCategoryIds[expenseCount] = catId;
        expenseCount++;

        System.out.println("Success: Expense added successfully!");
    }

    // ============== Update Existing Expense in Category ==============
    static void updateExpenseInCategory(Scanner input, int catId) {
        displayCategoryDetails(catId);

        System.out.println("\n--- Update Expense ---");
        System.out.print("Enter Expense ID to update: ");
        int targetLocalId;
        try {
            targetLocalId = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format.");
            return;
        }

        int currentLocalId = 1;
        int targetGlobalIndex = -1;

        for (int j = 0; j < expenseCount; j++) {
            if (expenseCategoryIds[j] == catId) {
                if (currentLocalId == targetLocalId) {
                    targetGlobalIndex = j;
                    break;
                }
                currentLocalId++;
            }
        }

        if (targetGlobalIndex == -1) {
            System.out.println("Error: Expense ID not found in this category.");
            return;
        }

        System.out.print("Enter new expense name (" + expenseNames[targetGlobalIndex] + "): ");
        String newName = input.nextLine().trim();
        if (!newName.isEmpty()) {
            expenseNames[targetGlobalIndex] = newName;
        }

        System.out.print("Enter new description (optional) (" + expenseDescriptions[targetGlobalIndex] + "): ");
        String newDesc = input.nextLine().trim();
        if (!newDesc.isEmpty()) {
            expenseDescriptions[targetGlobalIndex] = newDesc;
        }

        double newAmount = 0.0;
        while (true) {
            System.out.print("Enter new amount ($" + expenseAmounts[targetGlobalIndex] + "): ");
            String amtStr = input.nextLine().trim();
            if (amtStr.isEmpty()) {
                // Keep previous amount if user just presses enter
                newAmount = expenseAmounts[targetGlobalIndex];
                break;
            }
            try {
                newAmount = Double.parseDouble(amtStr);
                if (newAmount > 0) {
                    break;
                } else {
                    System.out.println("Error: Amount must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter valid numbers only.");
            }
        }
        expenseAmounts[targetGlobalIndex] = newAmount;

        System.out.println("Success: Expense updated successfully!");
        displayCategoryDetails(catId);
    }

    // ============== Delete Expense from Specific Category ==============
    static void deleteExpenseFromCategory(Scanner input, int catId) {
        System.out.println("\n--- Delete Expense ---");
        System.out.print("Enter Expense ID to delete: ");
        int targetLocalId;
        try {
            targetLocalId = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format.");
            return;
        }

        int currentLocalId = 1;
        int targetGlobalIndex = -1;

        for (int j = 0; j < expenseCount; j++) {
            if (expenseCategoryIds[j] == catId) {
                if (currentLocalId == targetLocalId) {
                    targetGlobalIndex = j;
                    break;
                }
                currentLocalId++;
            }
        }

        if (targetGlobalIndex == -1) {
            System.out.println("Error: Expense ID not found in this category.");
            return;
        }

        for (int i = targetGlobalIndex; i < expenseCount - 1; i++) {
            expenseNames[i] = expenseNames[i + 1];
            expenseDescriptions[i] = expenseDescriptions[i + 1];
            expenseAmounts[i] = expenseAmounts[i + 1];
            expenseCategoryIds[i] = expenseCategoryIds[i + 1];
        }

        expenseNames[expenseCount - 1] = null;
        expenseDescriptions[expenseCount - 1] = null;
        expenseAmounts[expenseCount - 1] = 0.0;
        expenseCategoryIds[expenseCount - 1] = 0;
        expenseCount--;

        System.out.println("Success: Expense deleted successfully!");
    }
}
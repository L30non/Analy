import java.util.ArrayList;
import java.util.Scanner;

// --- 1. CATEGORY CLASS ---
class Category {
    private int id;
    private String name;
    private ArrayList<Expense> expenses;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        this.expenses = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void removeExpense(Expense expense) {
        expenses.remove(expense);
    }

    @Override
    public String toString() {
        return "Category [ID=" + id + ", Name=" + name + ", Total Expenses=" + expenses.size() + "]";
    }
}

// --- 2. EXPENSE CLASS ---
class Expense {
    private int id;
    private double amount;
    private String description;
    private Category category;

    public Expense(int id, double amount, String description, Category category) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    @Override
    public String toString() {
        String catName = (category != null) ? category.getName() : "None";
        return "Expense [ID=" + id + ", Amount=$" + amount + ", Desc=" + description + ", Category=" + catName + "]";
    }
}

// --- 3. MANAGER CLASS (Logic) ---
class ExpenseManager {
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Expense> allExpenses = new ArrayList<>();

    private int nextCategoryId = 1;
    private int nextExpenseId = 1;

    // Helper method to re-index all IDs sequentially so there are no gaps
    private void refreshIds() {
        // Re-index categories
        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setId(i + 1);
        }
        nextCategoryId = categories.size() + 1;

        // Re-index all expenses across categories
        int expCounter = 1;
        for (Category c : categories) {
            for (Expense e : c.getExpenses()) {
                e.setId(expCounter++);
            }
        }
        nextExpenseId = expCounter;
    }

    public Category findCategoryById(int id) {
        for (Category c : categories) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    // Add Category
    public void addCategory(String name) {
        categories.add(new Category(nextCategoryId++, name));
        System.out.println("Category added successfully!");
    }

    // Update Category Name
    public void updateCategory(int categoryId, String newName) {
        Category category = findCategoryById(categoryId);
        if (category == null) {
            System.out.println("Error: Category not found.");
            return;
        }

        category.setName(newName);
        System.out.println("Category name updated successfully!");
    }

    // Delete Category (With Blocking Rule & ID Refresh)
    public void deleteCategory(int categoryId) {
        Category category = findCategoryById(categoryId);
        if (category == null) {
            System.out.println("Error: Category not found.");
            return;
        }

        if (!category.getExpenses().isEmpty()) {
            System.out.println("Error: Cannot delete category because it still contains expenses!");
            System.out.println("Please delete or move those expenses first.");
            return;
        }

        categories.remove(category);
        refreshIds(); // Re-index IDs after deletion
        System.out.println("Category deleted successfully!");
    }

    // Add Expense
    public void addExpense(int categoryId, double amount, String description) {
        Category cat = findCategoryById(categoryId);
        if (cat == null) {
            System.out.println("Error: Category ID not found!");
            return;
        }

        Expense expense = new Expense(nextExpenseId++, amount, description, cat);
        allExpenses.add(expense);
        cat.addExpense(expense);
        System.out.println("Expense added successfully!");
    }

    // Delete Expense (Requires Category ID first, then Expense ID)
    public void deleteExpense(int categoryId, int expenseId) {
        Category cat = findCategoryById(categoryId);
        if (cat == null) {
            System.out.println("Error: Category not found.");
            return;
        }

        Expense target = null;
        for (Expense e : cat.getExpenses()) {
            if (e.getId() == expenseId) {
                target = e;
                break;
            }
        }

        if (target == null) {
            System.out.println("Error: Expense with ID " + expenseId + " not found in this category.");
            return;
        }

        cat.removeExpense(target);
        allExpenses.remove(target);
        refreshIds(); // Re-index IDs after deletion
        System.out.println("Expense deleted successfully!");
    }

    // View All
    public void displayAll() {
        System.out.println("\n=== CATEGORIES & THEIR EXPENSES ===");
        if (categories.isEmpty()) {
            System.out.println("No categories available.");
            return;
        }

        for (Category c : categories) {
            System.out.println("\n[Category ID: " + c.getId() + "] " + c.getName());
            if (c.getExpenses().isEmpty()) {
                System.out.println("   -> No expenses in this category.");
            } else {
                for (Expense e : c.getExpenses()) {
                    System.out.println("   - Expense ID " + e.getId() + ": $" + e.getAmount() + " (" + e.getDescription() + ")");
                }
            }
        }
        System.out.println();
    }
}

// --- 4. TERMINAL USER INTERFACE (Main App) ---
public class DataUpdate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        // Starter data
        manager.addCategory("Food");
        manager.addCategory("Transport");
        manager.addExpense(1, 12.50, "Lunch");

        boolean running = true;
        while (running) {
            System.out.println("=== MENU ===");
            System.out.println("1. View Categories & Expenses");
            System.out.println("2. Add Category");
            System.out.println("3. Delete Category");
            System.out.println("4. Update Category Name");
            System.out.println("5. Add Expense");
            System.out.println("6. Delete Expense");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    manager.displayAll();
                    break;
                case "2":
                    System.out.print("Enter category name: ");
                    manager.addCategory(scanner.nextLine());
                    manager.displayAll();
                    break;
                case "3":
                    System.out.print("Enter Category ID to delete: ");
                    int delCatId = Integer.parseInt(scanner.nextLine());
                    manager.deleteCategory(delCatId);
                    manager.displayAll();
                    break;
                case "4":
                    System.out.print("Enter Category ID to update: ");
                    int upCatId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter new category name: ");
                    String newName = scanner.nextLine();
                    manager.updateCategory(upCatId, newName);
                    manager.displayAll();
                    break;
                case "5":
                    System.out.print("Enter Category ID to add expense into: ");
                    int cid = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter amount: ");
                    double amt = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter description: ");
                    String desc = scanner.nextLine();
                    manager.addExpense(cid, amt, desc);
                    manager.displayAll();
                    break;
                case "6":
                    System.out.print("Enter Category ID first: ");
                    int delCat = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Expense ID to delete: ");
                    int delExp = Integer.parseInt(scanner.nextLine());
                    manager.deleteExpense(delCat, delExp);
                    manager.displayAll();
                    break;
                case "0":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}
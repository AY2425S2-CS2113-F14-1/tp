package ui;

import static ui.ConsoleFormatter.*;

import enumStructure.Category;
import enumStructure.Currency;
import seedu.duke.FinancialGoal;
import seedu.duke.Transaction;
import seedu.duke.TransactionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        System.out.println("Enter your command:");
        return scanner.nextLine();
    }

    public void printWelcomeMessage() {
        printLine();
        printCenteredLine(".  .    ,    .  .    __.                 ");
        printCenteredLine("|\\ | _ -+- _ |  |._.(__  _..  ,*._  _  __");
        printCenteredLine("| \\|(_) | (/,|__|[  .__)(_] \\/ |[ )(_]_)");
        printCenteredLine("                                  ._|");
        printCenteredLine("");
        printCenteredLine("Hello! This is NoteUrSavings here!");
        printCenteredLine("What can I do for you?");
        printLine();
    }

    public void help() {
        printCenteredTitle("Help");
        printLeftAlignedLine("add: Adds a new transaction");
        printLeftAlignedLine("  Usage: add d/<description> a/<amount> c/<category>");
        printLeftAlignedLine("  Example: add 'Grocery Shopping' 50.0 SGD Groceries");
        printLeftAlignedLine("           2025-04-01 Pending");
        printLeftAlignedLine("");
        printLeftAlignedLine("delete: Deletes an existing transaction by ID");
        printLeftAlignedLine("  Usage: delete <transaction_id>");
        printLeftAlignedLine("  Example: delete 1");
        printLeftAlignedLine("");
        printLeftAlignedLine("list: Lists all transactions");
        printLeftAlignedLine("  Usage: list");
        printLeftAlignedLine("  Example: list");
        printLeftAlignedLine("");
        printLeftAlignedLine("search: Searches transactions based on a query (description)");
        printLeftAlignedLine("  Usage: search <query>");
        printLeftAlignedLine("  Example: search 'Groceries'");
        printLeftAlignedLine("");
        printLeftAlignedLine("update: edit an existing transaction's details");
        printLeftAlignedLine("  Usage: edit <transaction_id>");
        printLeftAlignedLine("         <description|amount|currency|category|status>");
        printLeftAlignedLine("         <new_value>");
        printLeftAlignedLine("  Example: edit 1 description 'Monthly Groceries'");
        printLeftAlignedLine("");
        printLeftAlignedLine("remind: Sets up reminders for recurring transactions");
        printLeftAlignedLine("  Usage: remind");
        printLeftAlignedLine("  Example: remind");
        printLeftAlignedLine("");
        printLeftAlignedLine("exit: Exits the application");
        printLeftAlignedLine("  Usage: exit");
        printLine();
    }

    public void printExit() {
        printLine();
        printCenteredLine("Goodbye! Hope to see you again!");
        printLine();
    }

    public void showError(String message) {
        printLine();
        printLeftAlignedLine("Failed: " + message);
        printLine();
    }

    public static void printDeleteTask(Transaction transaction, int count) {
        printLine();
        System.out.println("Noted. I've removed this transaction:");
        System.out.println(transaction);
        System.out.printf("Now you have %d transactions in the list.%n", count);
        printLine();
    }

    public void PrintBudgetLimit(TransactionManager transaction) {
        printLine();
        if (transaction.getTransactions().isEmpty()) {
            System.out.println("Please add a transaction first before you set the budget!");
        } else {
            double currentBudgetLimit = transaction.getBudgetLimit();
            transaction.checkBudgetLimit(currentBudgetLimit);
        }
        printLine();
    }

    public void PrintClear() {
        printLine();
        System.out.println("All transactions have been cleared!");
        printLine();
    }

    public void listNotification(ArrayList<Transaction> upcomingTransactions, String description) {
        printLine();
        if (upcomingTransactions.isEmpty()) {
            System.out.println("No upcoming expenses.");
        } else {
            System.out.println("Upcoming Expenses:");
            for (Transaction transaction : upcomingTransactions) {
                if (transaction.getDescription().equals(description) && transaction.getDate() != null) {
                    System.out.println("- " + transaction.getDescription() + " of " + transaction.getAmount() + " "
                            + transaction.getCurrency() + " in category " + transaction.getCategory() + " is due on "
                            + transaction.getDate());
                }
            }
        }
        printLine();
    }

    public void listNotifications(ArrayList<Transaction> upcomingTransactions) {
        if (upcomingTransactions.isEmpty()) {
            System.out.println("There are no upcoming transactions for now.");
            return;
        }
        boolean hasUpcoming = false;
        for (Transaction transaction : upcomingTransactions) {
            if (transaction.getDate() != null) {
                if (!hasUpcoming) {
                    System.out.println("Upcoming Expenses:");
                    hasUpcoming = true;
                }
                System.out.println("- " + transaction.getDescription() + " of " + transaction.getAmount() + " "
                        + transaction.getCurrency() + " in category " + transaction.getCategory() + " is due on "
                        + transaction.getDate());
            }
        }
        if (!hasUpcoming) {
            System.out.println("No upcoming expenses for now.");
        }
    }

    public void PrintPriority(ArrayList<Transaction> transactions, int index) {
        printLine();
        if (transactions.isEmpty()) {
            System.out.println("Please add a transaction first before you set the priority!");
        } else {
            System.out.println("Priority is set to " + transactions.get(index).getPriority() + " for current transaction.");
        }
        printLine();
    }

    public void listPriorities(ArrayList<Transaction> upcomingTransactions) {
        String defaultPriority = "HIGH";
        boolean hasHighPriority = false;
        for (Transaction transaction : upcomingTransactions) {
            if (transaction.getPriority() != null && transaction.getPriority().toString().equalsIgnoreCase(defaultPriority)) {
                if (!hasHighPriority) {
                    System.out.println("Following transactions have the high priority:");
                    hasHighPriority = true;
                }
                System.out.println("- " + transaction.getDescription() + " " + transaction.getAmount() + " "
                        + transaction.getCurrency() + " in category " + transaction.getCategory());
            }
        }
        if (!hasHighPriority) {
            System.out.println("No high priority transactions found.");
        }
    }

    public void printSummary(List<Transaction> transactions, double total, LocalDate start, LocalDate end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Expense Summary from " + start.format(formatter) + " to " + end.format(formatter));
        System.out.println("--------------------------------------------------");
        for (Transaction t : transactions) {
            System.out.printf("%s | %s | %.2f\n", t.getDate(), t.getDescription(), t.getAmount());
        }
        System.out.println("--------------------------------------------------");
        System.out.printf("Total Expenses: %.2f\n", total);
    }

    public void printTransactions(ArrayList<Transaction> transactions) {
        printLine();
        if (transactions.isEmpty()) {
            System.out.println("No transaction found.");
            printLine();
            return;
        }
        System.out.println("Here is the list of transactions:");
        printTransactionsTable(transactions);
        printLine();
    }

    public void printTransaction(Transaction transaction) {
        System.out.println(transaction);
    }

    public void printTransactionsTable(List<Transaction> transactions) {
        final int TOTAL_WIDTH = 121;
        final String INNER_HEADER_FORMAT = "| %-2s | %-15s | %-9s | %-19s | %-9s | %-10s | %-9s | %-8s |";
        final String INNER_ROW_FORMAT    = "| %2d | %-15s | %-9.2f | %-19s | %-9s | %-10s | %-9s | %-8s |";

        String sampleHeader = String.format(INNER_HEADER_FORMAT,
                "ID", "Description", "Amount", "Currency", "Category", "Date", "Completed", "Priority");

        int tableWidth = sampleHeader.length(); // ~64
        int spaceInsideBox = TOTAL_WIDTH - 4;   // 外框两侧 || 各占2
        int sidePadding = (spaceInsideBox - tableWidth) / 2;

        // 打印顶边框
        printLine();

        if (transactions.isEmpty()) {
            printLeftAlignedLine("No transaction found.");
            printLine();
            return;
        }

        // 打印表头
        printTableLine(sampleHeader, sidePadding);

        // 表头下横线
        printTableLine("-".repeat(tableWidth), sidePadding);

        // 每一行打印
        for (Transaction t : transactions) {
            String completedMark = t.isCompleted() ? "    ✔" : "    ✖";
            String row = String.format(INNER_ROW_FORMAT,
                    t.getId(),
                    limitWithEllipsis(t.getDescription(),15),
                    t.getAmount(),
                    t.getCurrency().toString(),
                    t.getCategory().toString(),
                    t.getDate() == null ? "N/A" : t.getDate().toString(),
                    completedMark,
                    t.getPriority().toString());

            printTableLine(row, sidePadding);

        }
        // 打印底边框
        printLine();
    }

    private static String limitWithEllipsis(String input, int maxLength) {
        if (input == null) return "";
        if (input.length() <= maxLength) return input;
        return input.substring(0, maxLength - 3) + "...";
    }


    /**
     * 打印表格行，包裹 || 并居中填充空格
     */
    public void printTableLine(String content, int sidePadding) {
        final int TOTAL_WIDTH = 121;
        int contentWidth = TOTAL_WIDTH - 4;
        int rightPadding = contentWidth - sidePadding - content.length();
        String line = "| " + " ".repeat(sidePadding) + content + " ".repeat(Math.max(0, rightPadding)) + " |";
        System.out.println(line);
    }

    public void tickTransaction(Transaction transaction) {
        printLine();
        System.out.println("I have ticked the following transaction:");
        printTransaction(transaction);
        printLine();
    }

    public void unTickTransaction(Transaction transaction) {
        printLine();
        System.out.println("I un tick the following transaction:");
        printTransaction(transaction);
        printLine();
    }

    public void add(Transaction transaction) {
        printLine();
        System.out.println("I have added the following transaction to the list:");
        printTransaction(transaction);
        printLine();
    }

    public void search(boolean isIndex) {
        if (isIndex) {
            System.out.println("I have searched the transaction with the given index.");
        } else {
            System.out.println("I have searched the transactions containing the keywords.");
        }
    }

    public void setPeriod(Transaction transaction, int recurringPeriod) {
        printLine();
        if (recurringPeriod > 0) {
            System.out.println("I have set the given transaction to recur every"
                    + (recurringPeriod == 1 ? " day." : " " + recurringPeriod + " days."));
        } else {
            System.out.println("I have disabled this transaction from recurring.");
        }
        printTransaction(transaction);
        printLine();
    }

    public void printGoal(FinancialGoal goal) {
        printLine();
        System.out.println(goal);
        printLine();
    }

    public void setGoalTarget(FinancialGoal goal) {
        printLine();
        System.out.println("I have updated your target to: " + goal.getTargetAmount());
        printLine();
    }

    public void setGoalDescription(FinancialGoal goal) {
        printLine();
        System.out.println("I have updated your description to:\n" + goal.getDescription());
        printLine();
    }

    public void setGoalTitle(FinancialGoal goal) {
        printLine();
        System.out.println("I have updated your goal to:\n" + goal.getGoal());
        printLine();
    }

    public static void createGoalConfirm() {
        printLine();
        System.out.println("Want to set a new goal (Y/N)? ");
        printLine();
    }

    public static void createGoalName() {
        System.out.println("Name of new goal:");
        printLine();
    }

    public static void createGoalTarget() {
        System.out.println("Target amount of new goal:");
        printLine();
    }

    public static void createGoalDescription() {
        printLine();
        System.out.println("Description of new goal:");
        printLine();
    }

    public static void createGoalSuccess() {
        printLine();
        System.out.println("Goal successfully created\nRun 'goal' to see it!");
        printLine();
    }

    public static void createGoalAborted() {
        printLine();
        System.out.println("Goal creation cancelled by user.");
        printLine();
    }

    public static void subFromSavings(double amount, double currentAmount) {
        printLine();
        System.out.println("Subtracted " + amount + " from your savings.");
        if (currentAmount < 0) {
            System.out.println("Warning. You currently have a negative balance.");
        }
        printLine();
    }

    public static boolean printGoalStatus(double currentAmount, double targetAmount) {
        printLine();
        if (currentAmount >= targetAmount) {
            System.out.println("You have achieved the goal! Congratulations!");
            return true;
        }
        System.out.println("You're " + currentAmount + " out of " + targetAmount + ". Good luck!");
        printLine();
        return false;
    }

    public void printEdited(String value, int typeId) {
        String type = switch (typeId) {
            case 0 -> "description";
            case 1 -> "category";
            case 2 -> "amount";
            case 3 -> "currency";
            default -> "";
        };

        printLine();
        System.out.println("Done! The " + type
                + " of the target transaction has been updated to:\n" + value);
        printLine();
    }

    public static void printRecurringTransactions(ArrayList<Transaction> transactions) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        printLine();
        System.out.println("Here is a list of your upcoming recurring payments:");
        int count = 1;
        for (Transaction transaction : transactions) {
            System.out.println(count + ". " + transaction.getDescription()
                    + " - " + transaction.getDate().format(df));
            count++;
        }
        printLine();
    }

    public void printSavingOverview(FinancialGoal goal) {
        printCenteredTitle("Saving Overview");

        if (goal.isBlank()) {
            printLeftAlignedLine("💰 You haven't set a saving goal yet.");
            printLeftAlignedLine("💡 Tip: Use 'saving > set' to create one and start tracking!");
            printLine();
            return;
        }

        double current = goal.getBalance();
        double target = goal.getTargetAmount();
        double percent = current / target;
        percent = Math.max(0.0, Math.min(percent, 1.0));

        int barLength = 20;
        int filled = (int) (percent * barLength);
        int empty = barLength - filled;

        String bar = "[" + "█".repeat(filled) +
                " ".repeat(empty) +
                "]";

        printLeftAlignedLine("Goal:         \"" + goal.getGoal() + "\"");
        printLeftAlignedLine("Description:  " + goal.getDescription());
        printLeftAlignedLine("");

        printLeftAlignedLine("Status:       You're currently at:"+String.format("  %s  %.1f%% complete",
                bar, percent * 100, current, target));
        if (percent >= 1.0) {
            printLeftAlignedLine("Analysis:     Amazing! You've achieved your savings goal. Time to celebrate!");
        } else if (percent >= 0.75) {
            printLeftAlignedLine("Analysis:     You're almost there! Keep going, you're doing great!");
        } else if (percent >= 0.5) {
            printLeftAlignedLine("Analysis:     You're halfway through. Keep up the good work!");
        } else if (percent > 0.0) {
            printLeftAlignedLine("Analysis:     Good start! Keep saving and you'll get there in no time.");
        } else {
            printLeftAlignedLine("Analysis:     You haven't started saving yet. Let's begin today!");
        }
        printLine();
    }

    public void printCurrencyChoice() {
        ConsoleFormatter.printLine();
        System.out.println("You can enter exit to quit choose progress");
        System.out.println("Please choose a valid currency from the list below:");

        int index = 1;
        for (Currency currency : Currency.values()) {
            System.out.println(index + ". " + currency.name());
            index++;
        }
        ConsoleFormatter.printLine();
    }

    public void printCurrencyHint() {
        System.out.print("Enter currency number (1-" + Currency.values().length + "): ");
    }

    public void printCurrencySetting() {
        System.out.println("Set your default currency successfully!");
        ConsoleFormatter.printLine();
    }

    public void printCategoryChoice() {
        ConsoleFormatter.printLine();
        System.out.println("You can enter exit to quit choose progress");
        System.out.println("Please choose a valid category from the list below:");
        int index = 1;
        for (Category category : Category.values()) {
            System.out.println(index + ". " + category.name());
            index++;
        }
        ConsoleFormatter.printLine();
    }

    public void printCategoryHint() {
        System.out.print("Enter category number (1-" + Category.values().length + "): ");
    }

    public void printCategoryChoose() {
        System.out.println("Choose category successfully!");
        ConsoleFormatter.printLine();
    }
}

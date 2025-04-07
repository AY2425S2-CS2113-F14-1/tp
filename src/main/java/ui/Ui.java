package ui;

import static ui.ConsoleFormatter.printLine;
import static ui.ConsoleFormatter.printCenteredLine;
import static ui.ConsoleFormatter.printCenteredTitle;
import static ui.ConsoleFormatter.printLeftAlignedLine;

import enums.Category;
import enums.Currency;

import seedu.duke.FinancialGoal;
import seedu.duke.Transaction;
import seedu.duke.TransactionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Map;

import java.time.format.DateTimeFormatter;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        System.out.print(">");
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
        printCenteredTitle("NoteUrSavings - Help Menu");

        printLeftAlignedLine("General Commands:");
        printLeftAlignedLine("  help                - Show this help menu");
        printLeftAlignedLine("  exit                - Exit the application");

        printLine();
        printLeftAlignedLine("Transaction Commands:");
        printLeftAlignedLine("  add                 - Start guided wizard to add a transaction");
        printLeftAlignedLine("  edit                - Edit a transaction step by step (wizard)");
        printLeftAlignedLine("  status              - Mark/unmark transaction complete/incomplete");
        printLeftAlignedLine("  list                - Show all transactions");
        printLeftAlignedLine("  view <id>           - View full details of a specific transaction");
        printLeftAlignedLine("  search <text>       - Search transactions by keyword");
        printLeftAlignedLine("  delete <id>         - Delete a transaction by ID");

        printLine();
        printLeftAlignedLine("Statistics & Balance:");
        printLeftAlignedLine("  stats               - Show overview of your finances by category/status");
        printLeftAlignedLine("  balance             - Show total balance (based on completed transactions)");
        printLeftAlignedLine("  currency            - View all exchange rates to SGD");
        printLeftAlignedLine("  currency XXX RATE   - Update exchange rate for currency XXX (e.g. USD 0.75)");

        printLine();
        printLeftAlignedLine("Saving Mode:");
        printLeftAlignedLine("  saving              - Enter interactive Saving Mode");
        printLeftAlignedLine("    - set              - Create or update saving goal");
        printLeftAlignedLine("    - list             - Show saving goal details");
        printLeftAlignedLine("    - contribute a/X   - Add funds from balance (simulate expense)");
        printLeftAlignedLine("    - deduct a/X       - Withdraw from savings (simulate income)");
        printLeftAlignedLine("    - exit             - Return to main menu");

        printLine();
        printLeftAlignedLine("Budget Mode:");
        printLeftAlignedLine("  budget              - Enter interactive Budget Mode (not detailed here)");

        printLine();
        printCenteredLine("Type commands directly. Fields will be prompted in wizard mode.");
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

    // @@author Lukapeng77

    /**
     * Prints a message indicating a transaction has been deleted, along with the
     * updated transaction count.
     *
     * @param transaction The transaction that was deleted.
     * @param count       The number of transactions remaining after deletion.
     */
    public static void printDeleteTask(Transaction transaction, int count) {
        printLine();
        System.out.println("Noted. I've removed this transaction:");
        System.out.println(transaction);
        System.out.printf("Now you have %d transactions in the list.%n", count);
        printLine();
    }

    /**
     * Prints the result of a currency conversion from one currency to another.
     *
     * @param originalAmount  The original amount before conversion.
     * @param from            The original currency.
     * @param convertedAmount The amount after conversion.
     * @param to              The target currency.
     */
    public void printConversion(double originalAmount, Currency from, double convertedAmount, Currency to) {
        printLine();
        System.out.printf("Converted %.2f %s to %.2f %s%n",
                originalAmount, from.name(), convertedAmount, to.name());
        printLine();
    }

    // @@author

    /**
     * Prints a confirmation message indicating that all transactions have been
     * cleared.
     */
    public void printClear() {
        printLine();
        System.out.println("All transactions have been cleared!");
        printLine();
    }

    // @@author Lukapeng77

    /**
     * Prints a list of upcoming transactions that match the given description and
     * have a due date.
     *
     * @param upcomingTransactions A list of upcoming transactions.
     * @param description          The description to filter transactions by.
     */
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

    /**
     * Prints a list of all upcoming transactions with due dates.
     *
     * @param upcomingTransactions A list of upcoming transactions.
     */
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

    /**
     * Prints the priority setting for a specified transaction.
     *
     * @param transactions The list of transactions.
     * @param index        The index of the transaction whose priority is being set.
     */
    public void printPriority(ArrayList<Transaction> transactions, int index) {
        printLine();
        if (transactions.isEmpty()) {
            System.out.println("Please add a transaction first before you set the priority!");
        } else {
            System.out.println("Priority is set to " +
                    transactions.get(index).getPriority() + " for current transaction.");
        }
        printLine();
    }

    /**
     * Lists all transactions that have a high priority setting.
     *
     * @param upcomingTransactions A list of upcoming transactions to check for high
     *                             priority.
     */
    public void listPriorities(ArrayList<Transaction> upcomingTransactions) {
        String defaultPriority = "HIGH";
        boolean hasHighPriority = false;
        for (Transaction transaction : upcomingTransactions) {
            if (transaction.getPriority() != null
                    && transaction.getPriority().toString().equalsIgnoreCase(defaultPriority)) {
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

    /**
     * Prints a summary of transactions between a given date range, including total
     * expenses.
     *
     * @param transactions A list of transactions to summarize.
     * @param total        The total sum of all transaction amounts.
     * @param start        The start date of the summary period.
     * @param end          The end date of the summary period.
     */
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
    // @@author

    public void printTransactions(ArrayList<Transaction> transactions) {
        if (transactions.isEmpty()) {
            printCenteredTitle("View Transaction");
            printCenteredLine("No transaction found.");
            printLine();
            return;
        }
        printTransactionsTable(transactions);
    }

    public void printTransaction(Transaction transaction) {
        viewTransactionDetail(transaction);
    }

    public void printTransactionsTable(List<Transaction> transactions) {
        final int TOTAL_WIDTH = 121;
        final String INNER_HEADER_FORMAT = "| %-2s | %-12s | %9s | %-8s | %-9s | %-10s | %-11s | %-8s |";
        final String INNER_ROW_FORMAT = "| %2d | %-12s | %9s | %-8s | %-9s | %-10s | %-11s | %-8s |";

        String sampleHeader = String.format(INNER_HEADER_FORMAT,
                "ID", "Description", "Amount", "Currency", "Category", "Date", "Completed", "Priority");

        int tableWidth = sampleHeader.length();
        int spaceInsideBox = TOTAL_WIDTH - 4;
        int sidePadding = (spaceInsideBox - tableWidth) / 2;

        printCenteredTitle("View Transaction");

        if (transactions.isEmpty()) {
            printCenteredLine("No transaction found.");
            printLine();
            return;
        }

        printTableLine(sampleHeader, sidePadding);
        printTableLine("-".repeat(tableWidth), sidePadding);

        for (Transaction t : transactions) {
            String completedMark = t.isCompleted() ? " [ ● ] " : " [ ○ ] ";

            // 内容字段超长截断
            String desc = trimToFit(t.getDescription(), 12);
            String curr = trimToFit(t.getCurrency().toString(), 8);
            String cat = trimToFit(t.getCategory().toString(), 9);
            String date = trimToFit(t.getDate() == null ? "N/A" : t.getDate().toString(), 10);
            String prio = trimToFit(t.getPriority().toString(), 8);

            // 数值字段过大转科学计数法
            String amountFormatted;
            if (Math.abs(t.getAmount()) >= 1e7 || Math.abs(t.getAmount()) < 0.01 && t.getAmount() != 0) {
                amountFormatted = String.format("%9.2E", t.getAmount());
            } else {
                amountFormatted = String.format("%9.2f", t.getAmount());
            }

            String row = String.format(INNER_ROW_FORMAT,
                    t.getId(),
                    desc,
                    amountFormatted,
                    curr,
                    cat,
                    date,
                    completedMark,
                    prio);

            printTableLine(row, sidePadding);
        }
        // 打印底边框
        printLine();
    }

    private static String limitWithEllipsis(String input) {
        if (input == null) {
            return "";
        }
        if (input.length() <= 15) {
            return input;
        }
        return input.substring(0, 15 - 3) + "...";
    }

    /**
     * 打印表格行，包裹 || 并居中填充空格 !!! 把这个翻译成英语 !!!
     */
    public void printTableLine(String content, int sidePadding) {
        final int totalWidth = 121;
        int contentWidth = totalWidth - 4;
        int rightPadding = contentWidth - sidePadding - content.length();
        String line = "| " + " ".repeat(sidePadding) + content + " ".repeat(Math.max(0, rightPadding)) + " |";
        System.out.println(line);
    }

    private String trimToFit(String content, int maxLength) {
        if (content.length() <= maxLength) {
            return content;
        } else if (maxLength >= 3) {
            return content.substring(0, maxLength - 3) + "...";
        } else {
            return content.substring(0, maxLength); // fallback
        }
    }

    public void tickTransaction(Transaction transaction) {
        printLine();
        System.out.println("I have ticked the following transaction:");
        printTransaction(transaction);
        printLine();
    }

    public void unTickTransaction(Transaction transaction) {
        printLine();
        System.out.println("I have un-ticked the following transaction:");
        printTransaction(transaction);
        printLine();
    }

    public void add(Transaction transaction) {
        printLine();
        printCenteredTitle("Added the following transaction Successfully:");
        printLine();
        printTransaction(transaction);
        printLine();
    }

    // @@author yangyi-zhu
    /**
     * Prints a message based on whether the search was by index or by keyword.
     *
     * @param isIndex True if the search was by transaction index; false if by
     *                keyword.
     */
    public void search(boolean isIndex) {
        if (isIndex) {
            System.out.println("I have searched the transaction with the given index.");
        } else {
            System.out.println("I have searched the transactions containing the keywords.");
        }
    }

    /**
     * Sets the recurring period for a given transaction and prints confirmation.
     *
     * @param transaction     The transaction to modify.
     * @param recurringPeriod The number of days for recurrence; set to 0 or less to
     *                        disable.
     */
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

    /**
     * Prints the details of a financial goal.
     *
     * @param goal The goal to be printed.
     */
    public void printGoal(FinancialGoal goal) {
        printLine();
        System.out.println(goal);
        printLine();
    }

    /**
     * Prints a confirmation message for updating a goal's target amount.
     *
     * @param goal The goal to update.
     */
    public void setGoalTarget(FinancialGoal goal) {
        printLine();
        System.out.println("I have updated your target to: " + goal.getTargetAmount());
        printLine();
    }

    /**
     * Prints a confirmation message for updating a goal's description.
     *
     * @param goal The goal to update.
     */
    public void setGoalDescription(FinancialGoal goal) {
        printLine();
        System.out.println("I have updated your description to:\n" + goal.getDescription());
        printLine();
    }

    /**
     * Prints a confirmation message for updating a goal's title/name.
     *
     * @param goal The goal to update.
     */
    public void setGoalTitle(FinancialGoal goal) {
        printLine();
        System.out.println("I have updated your goal to:\n" + goal.getGoal());
        printLine();
    }

    /**
     * Prompts the user to confirm whether they want to create a new goal.
     */
    public static void createGoalConfirm() {
        printLine();
        ConsoleFormatter.printLeftAlignedLine("Want to set a new goal (Y/N)? ");
        printLine();
    }

    /**
     * Prompts the user to input the name of a new goal.
     */
    public static void createGoalName() {
        printLine();
        ConsoleFormatter.printLeftAlignedLine("Name of new goal:");
        printLine();
    }

    /**
     * Prompts the user to input the target amount of a new goal.
     */
    public static void createGoalTarget() {
        printLine();
        ConsoleFormatter.printLeftAlignedLine("Target amount of new goal:");
        printLine();
    }

    /**
     * Prompts the user to input the description of a new goal.
     */
    public static void createGoalDescription() {
        printLine();
        ConsoleFormatter.printLeftAlignedLine("Description of new goal:");
        printLine();
    }

    /**
     * Displays a message indicating the goal has been successfully created.
     */
    public static void createGoalSuccess() {
        printLine();
        ConsoleFormatter.printLeftAlignedLine("Goal successfully created");
        ConsoleFormatter.printLeftAlignedLine("Run 'goal' in the main menu to see it!");
        printLine();
    }

    /**
     * Displays a message indicating the goal creation was aborted.
     */
    public static void createGoalAborted() {
        printLine();
        ConsoleFormatter.printLeftAlignedLine("Goal creation cancelled by user.");
        printLine();
    }

    /**
     * Subtracts a specified amount from savings and displays a warning if the
     * balance is negative.
     *
     * @param amount        The amount to subtract.
     * @param currentAmount The resulting balance after subtraction.
     */
    public static void subFromSavings(double amount, double currentAmount) {
        printLine();
        ConsoleFormatter.printLeftAlignedLine("Subtracted " + amount + " from your savings.");
        if (currentAmount < 0) {
            ConsoleFormatter.printLeftAlignedLine("Warning. You currently have a negative balance.");
        }
        printLine();
    }

    /**
     * Checks and prints the goal status based on current savings and target.
     *
     * @param currentAmount The current balance saved.
     * @param targetAmount  The savings target to reach.
     * @return True if the goal is achieved; false otherwise.
     */
    public static boolean printGoalStatus(double currentAmount, double targetAmount) {
        printLine();
        if (currentAmount >= targetAmount) {
            ConsoleFormatter.printLeftAlignedLine("You have achieved the goal! Congratulations!");
            return true;
        }
        ConsoleFormatter.printLeftAlignedLine("You're " + currentAmount +
                " out of " + targetAmount + ". Good luck!");
        printLine();
        return false;
    }

    /**
     * Prints a confirmation message after editing a transaction.
     *
     * @param value  The new value of the attribute.
     * @param typeId The type of attribute edited: 0=desc, 1=category, 2=amount,
     *               3=currency.
     */
    public void printEdited(String value, int typeId) {
        String type;
        switch (typeId) {
            case 0:
                type = "description";
                break;
            case 1:
                type = "category";
                break;
            case 2:
                type = "amount";
                break;
            case 3:
                type = "currency";
                break;
            default:
                type = "";
        }

        printLine();
        ConsoleFormatter.printLeftAlignedLine("Done! The " + type
                + " of the target transaction has been updated to:");
        ConsoleFormatter.printLeftAlignedLine((typeId == 3 ? Currency.valueOf(value).toString()
                        : (typeId == 2) ? Double.parseDouble(value) : value).toString());
        printLine();
    }

    /**
     * Displays a list of upcoming recurring transactions, if any.
     *
     * @param transactions A list of recurring transactions.
     */
    public static void printRecurringTransactions(ArrayList<Transaction> transactions) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        printLine();
        if (transactions.isEmpty()) {
            ConsoleFormatter.printLeftAlignedLine("You have no recurring payments ahead.");
            printLine();
            return;
        }
        System.out.println("Here is a list of your upcoming recurring payments:");
        int count = 1;
        for (Transaction transaction : transactions) {
            System.out.println(count + ". " + transaction.getDescription()
                    + " - " + transaction.getDate().format(df));
            count++;
        }
        printLine();
    }

    // @@author
    public void printSavingOverview(FinancialGoal goal) {
        printCenteredTitle("Saving Overview");

        if (goal.isBlank()) {
            printLeftAlignedLine("You haven't set a saving goal yet.");
            printLeftAlignedLine("Tip: Use 'saving > set' to create one and start tracking!");
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

        printLeftAlignedLine("Status:       You're currently at:" + String.format("  %s  %.1f%% complete",
                bar, percent * 100));
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
        System.out.println("You can enter exit to quit choosing progress");
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
        System.out.println("Chosen category successfully!");
        ConsoleFormatter.printLine();
    }

    public void viewTransactionDetail(Transaction t) {
        printCenteredTitle("Transaction Details");

        printLeftAlignedLine("ID:            " + t.getId());
        printLeftAlignedLine("Description:   " + t.getDescription());

        String typeLabel = t.getAmount() < 0 ? "Expense" : "Income";
        String amountStr = String.format("%.2f %s (%s)", t.getAmount(), t.getCurrency(), typeLabel);
        printLeftAlignedLine("Amount:        " + amountStr);

        printLeftAlignedLine("Category:      " + t.getCategory());
        printLeftAlignedLine("Date:          " + (t.getDate() == null ? "N/A" : t.getDate().toString()));
        printLeftAlignedLine("Priority:      " + t.getPriority());

        printLeftAlignedLine("Completed:     " + (t.isCompleted() ? "[ YES ]" : "[ NO ]"));

        // String recurringStr = (t.getRecurringPeriod() > 0)
        // ? "Every " + t.getRecurringPeriod() + " days"
        // : "No";
        // printLeftAlignedLine("Recurring: " + recurringStr);
        //
        // String tags = t.getTags().isEmpty()
        // ? "(none)"
        // : String.join(", ", t.getTags());
        // printLeftAlignedLine("Tags: " + tags);

        printLine(); // 底部边框
    }

    public void printCurrencyRates() {
        printCenteredTitle("Currency Rates (Base: SGD)");

        for (Currency currency : Currency.values()) {
            if (!currency.equals(Currency.SGD)) {
                printLeftAlignedLine("1 SGD = " + currency.getRate() + " " + currency);
            }
        }

        printLine();
    }

    public void showMessage(String message) {
        printLine();
        printLeftAlignedLine(message);
        printLine();
    }

    public void printBalanceOverview(double balance) {
        printCenteredTitle("Account Balance Overview");

        printLeftAlignedLine("Net Completed Balance:    " + String.format("%.2f SGD", balance));
        printLeftAlignedLine("");

        if (balance > 0) {
            printLeftAlignedLine("Analysis: Positive net savings. Keep up the good work!");
        } else if (balance == 0) {
            printLeftAlignedLine("Analysis: Net balance is zero. Consider reviewing your expenses.");
        } else {
            printLeftAlignedLine("Analysis: You’ve spent more than your earnings. Be cautious!");
        }

        printLine();
    }

    public void printStatisticsOverview(TransactionManager tm) {
        printCenteredTitle("Transaction Statistics");

        // Completion Stats
        int[] stats = tm.getCompletionStats();
        printLeftAlignedLine("Completed:     " + stats[0]);
        printLeftAlignedLine("Incomplete:    " + stats[1]);

        // Per-category
        printLeftAlignedLine("Completed Amount per Category (in SGD):");
        Map<Category, Double> categoryMap = tm.getCompletedAmountPerCategory();

        double total = 0;
        if (categoryMap.isEmpty()) {
            printLeftAlignedLine("  (empty)");
        } else {
            for (Map.Entry<Category, Double> entry : categoryMap.entrySet()) {
                printLeftAlignedLine("  - " + entry.getKey() + ": " + String.format("%.2f", entry.getValue()));
                total += entry.getValue();
            }
        }

        printLeftAlignedLine("");
        printLeftAlignedLine("Total Completed Amount (in SGD): " + String.format("%.2f", total));
        printLine();
    }
}

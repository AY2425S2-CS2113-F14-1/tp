package seedu.duke.budget;

import java.util.ArrayList;
import java.util.List;

import seedu.duke.Transaction;
import ui.Ui;
import ui.ConsoleFormatter;

public class BudgetList {
    private ArrayList<Budget> budgets;

    public BudgetList() {
        budgets = new ArrayList<>();
    }

    public void add(Budget budget) {
        budgets.removeIf(b -> b.getCategory() == budget.getCategory());
        budgets.add(budget);
    }

    public Budget get(int index) {
        return budgets.get(index);
    }

    public void remove(int index) {
        budgets.remove(index);
    }

    public int size() {
        return budgets.size();
    }

    public ArrayList<Budget> getAll() {
        return budgets;
    }

    public boolean isEmpty() {
        return budgets.isEmpty();
    }

    public void clear() {
        budgets.clear();
    }

    public void printBudgetDetail(int index, Ui ui, List<Transaction> transactions) {
        if (index < 0 || index >= budgets.size()) {
            ui.showError("Invalid budget index.");
            return;
        }

        Budget b = budgets.get(index);
        double remaining = b.calculateRemaining(transactions);

        ConsoleFormatter.printLine();
        System.out.println("🔍 Budget Details:");
        System.out.println("Name: " + b.getName());
        System.out.println("Target Amount: $" + b.getTotalAmount());
        System.out.println("Remaining: $" + String.format("%.2f", remaining));
        System.out.println("End Date: " + b.getEndDate());
        System.out.println("Category: " + b.getCategory());
        ConsoleFormatter.printLine();
    }

    public void printAllBudgets(Ui ui, List<Transaction> transactions) {
        final int totalWidth = 121;
        final String headerFormat = "| %-2s | %-14s | %9s | %-10s | %-10s |";
        final String rowFormat = "| %2d | %-14s | %9.2f | %-10s | %-10s |";

        if (budgets.isEmpty()) {
            ConsoleFormatter.printLine();
            ConsoleFormatter.printLeftAlignedLine("No budgets found.");
            ConsoleFormatter.printLine();
            return;
        }

        String headerLine = String.format(headerFormat, "#", "Name", "Amount", "End Date", "Category");
        int tableWidth = headerLine.length();
        int spaceInsideBox = totalWidth - 4;
        int sidePadding = (spaceInsideBox - tableWidth) / 2;

        ConsoleFormatter.printLine();

        int headerLeftPad = (totalWidth - headerLine.length()) / 2;
        System.out.println(" ".repeat(headerLeftPad) + headerLine);

        ui.printTableLine("-".repeat(tableWidth), sidePadding);

        for (int i = 0; i < budgets.size(); i++) {
            Budget b = budgets.get(i);
            String row = String.format(rowFormat,
                    i + 1,
                    b.getName(),
                    b.getTotalAmount(),
                    b.getEndDate().toString(),
                    b.getCategory());
            ui.printTableLine(row, sidePadding);
        }

        ConsoleFormatter.printLine();
    }

}

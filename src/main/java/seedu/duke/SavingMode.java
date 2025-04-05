package seedu.duke;

import ui.Ui;
import java.util.Scanner;

public class SavingMode {

    public static void enter(Ui ui, FinancialGoal goal, Storage storage) {
        Scanner scanner = new Scanner(System.in);
        ui.showLine();
        System.out.println("You're now in Saving Mode!");
        System.out.println("Type 'help' to see saving commands. Type 'exit' to return.");
        ui.showLine();

        while (true) {
            System.out.print("saving> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                ui.showLine();
                System.out.println("Exiting Saving Mode...");
                ui.showLine();
                break;
            }

            try {
                parseSavingCommand(input, ui, goal, storage);
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
    }

    private static void parseSavingCommand(String input, Ui ui, FinancialGoal goal, Storage storage) throws Exception {
        if (input.isBlank()) {
            return;
        }

        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();

        switch (command) {
            case "help":
                printHelp(ui);
                break;

            case "set":
                goal.createNewGoal(ui);
                storage.saveGoal(goal);  // 保存新设置的目标
                break;

            case "list":
                ui.printGoal(goal);
                break;

            case "contribute":
            case "save":  // alias
                handleAddToSavings(parts, goal, storage);
                break;

            case "deduct":
                handleSubtractFromSavings(parts, goal, storage);
                break;

            default:
                throw new Exception("Unknown command in Saving Mode. Type 'help' for available commands.");
        }
    }

    private static void handleAddToSavings(String[] parts, FinancialGoal goal, Storage storage) throws Exception {
        if (parts.length < 2 || !parts[1].startsWith("a/")) {
            throw new Exception("Usage: contribute a/AMOUNT");
        }
        double addAmount = Double.parseDouble(parts[1].substring(2).trim());
        if (addAmount <= 0) {
            throw new Exception("Amount must be a positive number.");
        }
        goal.addToSavings(addAmount);
        System.out.printf("✅ You have added $%.2f to your savings goal.%n", addAmount);
        storage.saveGoal(goal);
    }

    private static void handleSubtractFromSavings(String[] parts, FinancialGoal goal, Storage storage) throws Exception {
        if (parts.length < 2 || !parts[1].startsWith("a/")) {
            throw new Exception("Usage: deduct a/AMOUNT");
        }
        double subAmount = Double.parseDouble(parts[1].substring(2).trim());
        if (subAmount <= 0) {
            throw new Exception("Amount must be a positive number.");
        }
        goal.subFromSavings(subAmount);
        System.out.printf("✅ You have deducted $%.2f from your savings.%n", subAmount);
        storage.saveGoal(goal);
    }


    private static void printHelp(Ui ui) {
        ui.showLine();
        System.out.println("Saving Mode Commands:");
        System.out.println("- set: Create a new saving goal interactively");
        System.out.println("- list: Show current saving goal details");
        System.out.println("- contribute a/AMOUNT: Add funds to your goal");
        System.out.println("- save a/AMOUNT: (alias) Add funds to your goal");
        System.out.println("- deduct a/AMOUNT: Subtract funds from your goal");
        System.out.println("- exit: Return to main menu");
        ui.showLine();
    }
}

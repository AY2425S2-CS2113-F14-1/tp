package seedu.duke;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;

import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Priority;
import enumStructure.Status;

public class Storage {
    private static final String FOLDER_PATH = "data";
    private static final String FILE_PATH = FOLDER_PATH + "/transactions.csv";
    private static final String GOAL_FILE_PATH = FOLDER_PATH + "/goal.csv";

    private void createDataFolderIfNeeded() {
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void saveTransactions(ArrayList<Transaction> transactions) {
        assert transactions != null;

        createDataFolderIfNeeded();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Transaction t : transactions) {
                assert t != null;
                writer.write(formatTransaction(t));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    public ArrayList<Transaction> loadTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return transactions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Transaction t = parseTransaction(line);
                    if (t != null) {
                        transactions.add(t);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }

        return transactions;
    }

    private String formatTransaction(Transaction t) {
        return String.format("%d,%s,%f,%s,%s,%s,%s,%d,%b,%b,%s",
                t.getId(), t.getDescription(), t.getAmount(), t.getCurrency(),
                t.getCategory(), t.getDate(), t.getStatus(),
                t.getRecurringPeriod(), t.isDeleted(), t.isCompleted(), t.getPriority());
    }

    private Transaction parseTransaction(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length != 11) return null;

            int id = Integer.parseInt(parts[0]);
            String description = parts[1];
            double amount = Double.parseDouble(parts[2]);
            Currency currency = Currency.valueOf(parts[3]);
            Category category = Category.valueOf(parts[4]);
            LocalDate date = LocalDate.parse(parts[5]);
            Status status = Status.valueOf(parts[6].toUpperCase());
            int recurringPeriod = Integer.parseInt(parts[7]);
            boolean isDeleted = Boolean.parseBoolean(parts[8]);
            boolean isCompleted = Boolean.parseBoolean(parts[9]);
            Priority priority = Priority.valueOf(parts[10].toUpperCase());

            Transaction transaction = new Transaction(id, description, amount, currency, category, date, status);
            transaction.setRecurringPeriod(recurringPeriod);
            transaction.setPriority(priority);
            if (isDeleted) transaction.delete();
            if (isCompleted) transaction.complete();
            return transaction;
        } catch (Exception e) {
            System.out.println("Error parsing transaction: " + line + " - " + e.getMessage());
            return null;
        }
    }

    // ✅ Saving FinancialGoal to goal.csv
    public void saveGoal(FinancialGoal goal) {
        createDataFolderIfNeeded();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GOAL_FILE_PATH))) {
            writer.write(formatGoal(goal));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving financial goal: " + e.getMessage());
        }
    }

    private String formatGoal(FinancialGoal goal) {
        return String.format("%s,%f,%s,%f,%b",
                goal.getGoal(),
                goal.getTargetAmount(),
                goal.getDescription().replace(",", " "),
                goal.getBalance(),
                goal.isAchieved());
    }

    public FinancialGoal loadGoal() {
        File file = new File(GOAL_FILE_PATH);
        if (!file.exists()) {
            return new FinancialGoal();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",", 5);
                String name = parts[0];
                double target = Double.parseDouble(parts[1]);
                String description = parts[2];
                double deposits = Double.parseDouble(parts[3]);
                boolean isAchieved = Boolean.parseBoolean(parts[4]);

                FinancialGoal goal = new FinancialGoal(name, target, description);
                goal.forceSetDeposits(deposits);
                goal.forceSetAchieved(isAchieved);
                return goal;
            }
        } catch (Exception e) {
            System.out.println("Error loading financial goal: " + e.getMessage());
        }

        return new FinancialGoal();
    }
}

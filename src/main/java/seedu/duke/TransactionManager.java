package seedu.duke;

import java.util.ArrayList;
import java.util.Comparator;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import constant.Constant;
import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Status;
import exceptions.InvalidCommand;
import exceptions.NullException;
import ui.Ui;

public class TransactionManager {
    private ArrayList<Transaction> transactions;
    private Currency defaultCurrency = Currency.SGD;
    private double budgetLimit = -1; // -1 means budget is not set yet
    private boolean isBudgetSet = false;

    public TransactionManager() {
        transactions = new ArrayList<>();
    }

    public int getNum() {
        return transactions.size();
    }

    public int getSize() {
        int count = 0;
        for (Transaction transaction : transactions) {
            if (!transaction.isDeleted()) {
                count++;
            }
        }
        return count;
    }

    public double getTotalTransactionAmount() {
        double totalAmount = 0;
        for (Transaction transaction : transactions) {
            if (!transaction.isDeleted()) {
                totalAmount += transaction.getAmount();
            }
        }
        return totalAmount;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public boolean isBudgetSet() {
        return isBudgetSet;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public boolean addTransaction(int id, String description, double amount, Category category) {
        LocalDate date = LocalDate.now();
        Transaction transaction = new Transaction(id, description, amount, defaultCurrency, category, date, Status.PENDING);

        if (isBudgetSet) {
            double projectedTotal = getTotalTransactionAmount() + transaction.getAmount();
            if (projectedTotal > budgetLimit) {
                System.out.println("Cannot add new transaction! Budget limit exceeded!\n");
                return false;
            }
        }
        transactions.add(transaction);
        return true;
    }

    public ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> printTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (!transaction.isDeleted()) {
                printTransactions.add(transaction);
                sortTransactions(printTransactions);
            }
        }
        return printTransactions;
    }

    /**
     * Deletes a transaction from the transaction list.
     *
     * @param id the index of the transaction to be removed.
     */
    public void deleteExpense(int id) {
        if (checkIdEmpty(id)) {
            return;
        }
        searchTransaction(id + 1).delete();
    }

    /*
    function to record and trace the total budget limit
     */
    public void checkBudgetLimit(double budgetLimit) {
        double totalAmount = getTotalTransactionAmount();

        if (totalAmount > budgetLimit) {
            double exceedingAmount = totalAmount - budgetLimit;
            System.out.println("Warning: You have exceeded your budget limit!\n");
            System.out.println("Current amount that exceed the budget are: " + exceedingAmount);
        } else {
            this.budgetLimit = budgetLimit;
            this.isBudgetSet = true;
            System.out.println("Budget limit set to " + budgetLimit + " " + defaultCurrency);
        }
    }

    public void clear() {
        transactions.clear();
    }

    public Transaction searchTransaction(int id) {
        try {
            for (Transaction transaction : transactions) {
                if (transaction.getId() == id && !transaction.isDeleted()) {
                    return transaction;
                }
            }
            throw new NullException("Transaction is invalid");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Transaction> searchTransactionList(boolean isIndex, String searchTerm) throws Exception {
        try {
            ArrayList<Transaction> printTransactions = new ArrayList<>();
            if (isIndex) {
                int id = Integer.parseInt(searchTerm);
                for (Transaction transaction : transactions) {
                    if (transaction.getId() == id && !transaction.isDeleted()) {
                        printTransactions.add(transaction);
                        break;
                    }
                }
            } else {
                for (Transaction transaction : transactions) {
                    if (transaction.getDescription().contains(searchTerm) && !transaction.isDeleted()) {
                        printTransactions.add(transaction);
                    }
                }
            }
            return printTransactions;
        } catch (NumberFormatException ne) {
            throw new InvalidCommand("That wasn't an id... Try again!");
        } catch (Exception e) {
            throw new InvalidCommand("An unexpected error occurred, try again!");
        }
    }

    public void remindRecurringTransactions() {
        ArrayList<Transaction> nextRecurring = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (!transaction.isDeleted() && transaction.getRecurringPeriod() > 0) {
                nextRecurring.add(transaction);
            }
        }
        if (nextRecurring.isEmpty()) {
            return;
        }

        nextRecurring = sortRecurringTransactions(nextRecurring);
        Ui.printRecurringTransactions(nextRecurring);
    }

    public ArrayList<Transaction> sortRecurringTransactions(ArrayList<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            int period = transaction.getRecurringPeriod();
            while (transaction.getDate().isBefore(LocalDate.now())) {
                transaction.setDate(transaction.getDate().plusDays(period));
            }
        }
        transactions.sort(Comparator.comparing(Transaction::getDate));
        return transactions;
    }

    // Sets a notification for an upcoming transaction
    public void notify(String description, double amount, String categoryString, String date) {
        LocalDate dueDate = LocalDate.parse(date);
        Category category = Category.valueOf(categoryString);
        for (Transaction transaction : transactions) {
            if (transaction.getDescription().equals(description) && transaction.getCategory().equals(category)) {
                transaction.setDate(dueDate);
            }
        }
    }

    public void tickTransaction(int id) {
        Transaction transaction = searchTransaction(id);
        if (transaction == null) {
            return;
        }
        transaction.complete();
    }

    public void unTickTransaction(int id) {
        Transaction transaction = searchTransaction(id);
        if (transaction == null) {
            return;
        }
        transaction.notComplete();
    }

    public void setRecur(int id, int recurringPeriod) {
        Transaction transaction = searchTransaction(id);
        if (transaction == null) {
            return;
        }
        transaction.setRecurringPeriod(recurringPeriod);
    }

    public ArrayList<Transaction> sortTransactions(ArrayList<Transaction> transactions) {

        transactions.sort((t1, t2) -> {
            if (t1.getDate() == null && t2.getDate() == null) {
                return 0;
            }
            if (t1.getDate() == null) {
                return -1;
            }
            if (t2.getDate() == null) {
                return 1;
            }
            return t1.getDate().compareTo(t2.getDate());
        });
        return transactions;
    }

    public ArrayList<Transaction> getTransactionsOnDate(LocalDate targetDate) {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction t : getTransactions()) {
            if (t.getDate() != null && t.getDate().equals(targetDate)) {
                filteredTransactions.add(t);
            }
        }
        return filteredTransactions;
    }

    public ArrayList<Transaction> getTransactionsThisMonth() {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction t : getTransactions()) {
            if (t.getDate() != null &&
                    t.getDate().getMonthValue() == month &&
                    t.getDate().getYear() == year) {
                filteredTransactions.add(t);
            }
        }
        return filteredTransactions;
    }

    public ArrayList<Transaction> getTransactionsThisWeek() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction t : getTransactions()) {
            if (t.getDate() != null &&
                    !t.getDate().isBefore(today) &&
                    !t.getDate().isAfter(nextWeek)) {
                filteredTransactions.add(t);
            }
        }
        return filteredTransactions;
    }

    public void getUpcomingTransactions(String period) {

        period = period.toLowerCase();
        switch (period) {
        case "today":
            System.out.println(getTransactionsOnDate(LocalDate.now()));
            break;
        case "week":
            System.out.println(getTransactionsThisWeek());
            break;
        case "month":
            System.out.println(getTransactionsThisMonth());
            break;
        default:
            try {
                LocalDate date = LocalDate.parse(period);
                System.out.println(getTransactionsOnDate(date));
            } catch (Exception e) {
                System.out.println("Invalid period. Use 'today', 'week', 'month', or a date (yyyy-mm-dd)");
            }
            break;
        }
    }

    public void editInfo(int id, String info, int type) throws Exception {
        if (checkIdEmpty(id)) {
            return;
        }
        switch (type) {
        case 0:
            transactions.get(id).setDescription(info);
            break;
        case 1:
            transactions.get(id).setCategory(Category.valueOf(info));
            break;
        case 2:
            int value;
            try {
                value = Integer.parseInt(info);
            } catch (Exception e) {
                throw new InvalidCommand("Invalid amount, try again!");
            }
            if (value < 0) {
                throw new InvalidCommand("Expense cannot be negative!");
            }
            transactions.get(id).setAmount(value);
            break;
        case 3:
            transactions.get(id).setCurrency(Currency.valueOf(info));
            break;
        default:
            break;
        }
    }

    public boolean checkIdEmpty(int id) {
        if (searchTransaction(id) == null) {
            System.out.println(Constant.INVALID_TRANSACTION_ID);
            return true;
        }
        return false;
    }

    public double getTotalAmount() {
        return getRecurringAmount() + getNormalAmount();
    }

    public double getRecurringAmount() {
        double sum = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getRecurringPeriod() > 0 && !transaction.isDeleted()) {
                long daysBetween = ChronoUnit.DAYS.between(transaction.getDate(), LocalDate.now());
                sum += transaction.getAmount() * (int) ((double) daysBetween / transaction.getRecurringPeriod() + 1);
            }
        }
        return sum;
    }

    public double getNormalAmount() {
        double sum = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getRecurringPeriod() <= 0 && !transaction.isDeleted()) { //&& transaction.isCompleted()) {
                sum += transaction.getAmount();
            }
        }
        return sum;
    }
}



package seedu.duke;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import constant.Constant;
import enums.Category;
import enums.Currency;
import enums.Status;
import exceptions.InvalidCommand;
import ui.Ui;
import seedu.duke.budget.BudgetList;

public class TransactionManager {
    private ArrayList<Transaction> transactions;
    private Currency defaultCurrency = Currency.SGD;
    private double budgetLimit = 0;
    private boolean isBudgetSet = false;

    private BudgetList budgetList = new BudgetList();

    private Storage storage;
    private int currentMaxId = 0;

    public TransactionManager() {
        transactions = new ArrayList<>();
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
        this.currentMaxId = storage.loadMaxTransactionId();
    }

    //@@author Lukapeng77
    public void loadBudgetFromStorage() {
        if (storage != null) {
            double savedBudget = storage.loadBudgetLimit();
            if (savedBudget > 0) {
                this.budgetLimit = savedBudget;
                this.isBudgetSet = true;
            }
        }
    }
    //@@author

    private int getNextAvailableId() {
        currentMaxId += 1;
        if (storage != null) {
            storage.saveMaxTransactionId(currentMaxId);
        }
        return currentMaxId;
    }

    public void setBudgetList(BudgetList budgetList) {
        this.budgetList = budgetList;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public int getNum() {
        return currentMaxId;
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

    //@@author Lukapeng77
    public double getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(double budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public void setBudgetSet(boolean budgetSet) {
        isBudgetSet = budgetSet;
    }

    public double getTotalTransactionAmount() {
        double totalAmount = 0;
        for (Transaction transaction : transactions) {
            if (!transaction.isDeleted()) {
                totalAmount += transaction.getAmount() * 1 / transaction.getCurrency().getRate();
            }
        }
        return totalAmount;
    }
    //@@author

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        int id = transaction.getId();
        if (id > currentMaxId) {
            currentMaxId = id;
            if (storage != null) {
                storage.saveMaxTransactionId(currentMaxId);
            }
        }
    }

    public boolean addTransaction(String description, double amount, Category category, LocalDate date) {
        int id = getNextAvailableId();
        LocalDate now = LocalDate.now();
        Transaction transaction;
        if (date == null) {
            transaction = new Transaction(id, description, amount, defaultCurrency, category, now, Status.PENDING);
        } else {
            transaction = new Transaction(id, description, amount, defaultCurrency, category, date, Status.PENDING);
        }

        if (isBudgetSet && (getTotalTransactionAmount() + amount > budgetLimit)) {
            return false;
        }

        transactions.add(transaction);
        if (id > currentMaxId) {
            currentMaxId = id;
            if (storage != null) {
                storage.saveMaxTransactionId(currentMaxId);
            }
        }
        return true;
    }

    public ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> existTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (!transaction.isDeleted()) {
                existTransactions.add(transaction);
            }
        }
        sortTransactions(existTransactions);
        return existTransactions;
    }

    //@@author Lukapeng77
    public ArrayList<Transaction> getTransactionsBetween(LocalDate start, LocalDate end) {
        return (ArrayList<Transaction>) transactions.stream()
                .filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
                .collect(Collectors.toList());
    }

    public void deleteExpense(int id) {
        Transaction transaction = searchTransaction(id);
        if (transaction != null) {
            transaction.delete();
        }
    }

    public void checkBudgetLimit(double budgetLimit) {
        double totalTransactionAmount = getTotalTransactionAmount();
        setBudgetLimit(budgetLimit);
        setBudgetSet(true);

        if (storage != null) {
            storage.saveBudgetLimit(budgetLimit);
        }

        System.out.println("Budget limit set to " + budgetLimit + " " + defaultCurrency);
        System.out.println("The remaining Budget amount is " +
                (budgetLimit - totalTransactionAmount) + " " + defaultCurrency + "\n");
    }

    //@@author
    public void clear() {
        transactions.clear();
        currentMaxId = 0;
        storage.saveMaxTransactionId(currentMaxId);
        budgetList.clear();
    }

    public Transaction searchTransaction(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId() == id && !transaction.isDeleted()) {
                return transaction;
            }
        }
        return null;
    }

    public ArrayList<Transaction> searchTransactionList(boolean isIndex, String searchTerm) throws Exception {
        try {
            ArrayList<Transaction> result = new ArrayList<>();
            if (isIndex) {
                int id = Integer.parseInt(searchTerm);
                for (Transaction t : transactions) {
                    if (t.getId() == id && !t.isDeleted()) {
                        result.add(t);
                        break;
                    }
                }
            } else {
                for (Transaction t : transactions) {
                    if (!t.isDeleted() && t.getDescription().contains(searchTerm)) {
                        result.add(t);
                    }
                }
            }
            return result;
        } catch (NumberFormatException ne) {
            throw new InvalidCommand("That wasn't an id... Try again!");
        } catch (Exception e) {
            throw new InvalidCommand("An unexpected error occurred, try again!");
        }
    }

    public ArrayList<Transaction> getRecurringTransactions() {
        ArrayList<Transaction> upcoming = new ArrayList<>();
        for (Transaction t : transactions) {
            if (!t.isDeleted() && t.getRecurringPeriod() > 0) {
                upcoming.add(t);
            }
        }
        return sortRecurringTransactions(upcoming);
    }

    public void remindRecurringTransactions() {
        ArrayList<Transaction> upcoming = getRecurringTransactions();
        if (!upcoming.isEmpty()) {
            Ui.printRecurringTransactions(upcoming);
        }
    }

    public ArrayList<Transaction> sortRecurringTransactions(ArrayList<Transaction> list) {
        for (Transaction t : list) {
            int period = t.getRecurringPeriod();
            while (t.getDate().isBefore(LocalDate.now())) {
                t.setDate(t.getDate().plusDays(period));
            }
        }
        list.sort(Comparator.comparing(Transaction::getDate));
        return list;
    }

    //@@author Lukapeng77
    public void notify(String description, String category, LocalDate date) {
        try {

            LocalDate minDate = LocalDate.of(2020, 1, 1);
            if (date.isBefore(minDate)) {
                throw new IllegalArgumentException("Date cannot be before January 1, 2020");
            }

            Category cat = Category.valueOf(category);
            for (Transaction t : transactions) {
                if (t.getDescription().equals(description) && t.getCategory() == cat) {
                    t.setDate(date);
                }
            }
        } catch (DateTimeParseException e) {
            // Handle invalid date format
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD format", e);
        }
    }
    //@@author

    public void tickTransaction(int id) throws Exception {
        Transaction transaction = searchTransaction(id);
        if (transaction != null) {
            transaction.complete();
        } else {
            throw new InvalidCommand("Transaction not found! Please type in a valid id");
        }
    }

    public void unTickTransaction(int id) throws Exception {
        Transaction transaction = searchTransaction(id);
        if (transaction != null) {
            transaction.notComplete();
        } else {
            throw new InvalidCommand("Transaction not found! Please type in a valid id");
        }
    }

    public void setRecur(int id, int period) throws Exception {
        Transaction t = searchTransaction(id);
        if (t != null) {
            t.setRecurringPeriod(period);
        } else {
            throw new Exception();
        }
    }

    public void sortTransactions(ArrayList<Transaction> list) {
        list.sort(Comparator.comparing(Transaction::getDate, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    public ArrayList<Transaction> getTransactionsOnDate(LocalDate date) {
        ArrayList<Transaction> result = new ArrayList<>();
        for (Transaction t : getTransactions()) {
            if (t.getDate() != null && t.getDate().equals(date)) {
                result.add(t);
            }
        }
        return result;
    }

    public ArrayList<Transaction> getTransactionsThisMonth() {
        ArrayList<Transaction> result = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (Transaction t : getTransactions()) {
            if (t.getDate() != null &&
                    t.getDate().getMonthValue() == now.getMonthValue() &&
                    t.getDate().getYear() == now.getYear()) {
                result.add(t);
            }
        }
        return result;
    }

    public ArrayList<Transaction> getTransactionsThisWeek() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        ArrayList<Transaction> result = new ArrayList<>();
        for (Transaction t : getTransactions()) {
            if (t.getDate() != null &&
                    !t.getDate().isBefore(today) &&
                    !t.getDate().isAfter(nextWeek)) {
                result.add(t);
            }
        }
        return result;
    }

    public void getUpcomingTransactions(String period) {
        switch (period.toLowerCase()) {
        case "today" -> System.out.println(getTransactionsOnDate(LocalDate.now()));
        case "week" -> System.out.println(getTransactionsThisWeek());
        case "month" -> System.out.println(getTransactionsThisMonth());
        default -> {
            try {
                LocalDate date = LocalDate.parse(period);
                System.out.println(getTransactionsOnDate(date));
            } catch (Exception e) {
                System.out.println("Invalid period. Use 'today', 'week', 'month', or a date (yyyy-mm-dd)");
            }
        }
        }
    }

    public void editInfo(int id, String value, int type) throws Exception {
        if (checkIdEmpty(id)) {
            return;
        }
        Transaction t = searchTransaction(id);
        if (t == null) {
            return;
        }

        switch (type) {
        case 0:
            t.setDescription(value);
            break;
        case 1:
            t.setCategory(Category.valueOf(value));
            break;
        case 2:
            double val = Double.parseDouble(value);
            if (val < 0) {
                throw new InvalidCommand("Expense cannot be negative!");
            }
            t.setAmount(val);
            break;
        case 3:
            t.setCurrency(Currency.valueOf(value.toUpperCase()));
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
        for (Transaction t : transactions) {
            if (!t.isDeleted() && t.getRecurringPeriod() > 0) {
                long days = ChronoUnit.DAYS.between(t.getDate(), LocalDate.now());
                sum += t.getAmount() * ((double) days / t.getRecurringPeriod() + 1);
            }
        }
        return sum;
    }

    public double getNormalAmount() {
        double sum = 0;
        for (Transaction t : transactions) {
            if (!t.isDeleted() && t.getRecurringPeriod() <= 0) {
                sum += t.getAmount();
            }
        }
        return sum;
    }

    public BudgetList getBudgetList() {
        return budgetList;
    }
}

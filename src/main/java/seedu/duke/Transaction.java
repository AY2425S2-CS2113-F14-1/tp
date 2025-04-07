package seedu.duke;

import enums.Category;
import enums.Currency;
import enums.Priority;
import enums.Status;

import java.time.LocalDate;
import java.util.ArrayList;

public class Transaction {
    private final int id;
    private double amount;
    private final Status status;
    private Currency currency;
    private LocalDate date;

    // changeable fields
    private String description;
    private Category category;
    private Priority priority;
    private ArrayList<String> tags;
    private boolean isDeleted = false;
    private int recurringPeriod; // Repeated every recurringPeriod days, one-time if 0
    private boolean isCompleted = false;

    //Constructor
    public Transaction(int id, String description, double amount, Currency currency,
                       Category category, LocalDate date, Status status) {
        assert description != null : "Description cannot be null";
        assert currency != null : "Currency cannot be null";
        assert category != null : "Category cannot be null";
        assert date != null : "Date cannot be null";
        assert status != null : "Status cannot be null";
        assert amount >= 0 : "Amount should be non-negative";

        this.id = id;
        this.amount = amount;
        this.description = description;
        this.currency = currency;
        this.category = category;
        this.date = date;
        this.status = status;
        this.priority = Priority.LOW;
        tags = new ArrayList<>();
        recurringPeriod = 0;
    }


    public Transaction(int id, String description, double amount, Currency currency, LocalDate date, Status status) {
        assert description != null : "Description cannot be null";
        assert currency != null : "Currency cannot be null";
        assert date != null : "Date cannot be null";
        assert status != null : "Status cannot be null";
        assert amount >= 0 : "Amount should be non-negative";

        this.id = id;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.status = status;
        this.priority = Priority.LOW;
        this.tags = new ArrayList<>();
        recurringPeriod = 0;
    }


    @Override
    public String toString() {
        return id + "," +
                description + "," +
                amount + "," +
                currency + "," +
                category + "," +
                priority + "," +     // 第6列
                status + "," +       // 第7列
                (date == null ? "N/A" : date.toString());  // 第8列
    }


    //get method
    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Category getCategory() {
        return category;
    }

    //@@author Lukapeng77
    public LocalDate getDate() {
        return date;
    }

    public Priority getPriority() {
        return priority;
    }
    //@@author

    public ArrayList<String> getTags() {
        return new ArrayList<>(tags);
    }

    public Status getStatus() {
        return status;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public int getRecurringPeriod() {
        return recurringPeriod;
    }

    public boolean isCompleted() {
        return isCompleted;
    }


    //set method
    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setRecurringPeriod(int recurringPeriod) {
        this.recurringPeriod = recurringPeriod;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    //@@author Lukapeng77
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    //@@author

    public void complete() {
        isCompleted = true;
    }

    public void notComplete() {
        isCompleted = false;
    }


    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public boolean containsTag(String tag) {
        return tags.contains(tag);
    }

    public boolean isSameTransaction(Transaction otherTransaction) {
        return this.id == otherTransaction.id;
    }

    public void delete() {
        isDeleted = true;
    }

    public void recover() {
        isDeleted = false;
    }

    public void convertTo(Currency currency) {
        if (this.currency == currency) {
            return; // No conversion needed
        }

        // Step 1: Convert from original currency to SGD
        double amountInSGD = this.amount / this.currency.getRate();

        // Step 2: Convert from SGD to target currency
        this.amount = amountInSGD * currency.getRate();
        this.currency = currency;
    }
}

package seedu.duke;

import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Priority;
import enumStructure.Status;

import java.time.LocalDate;
import java.util.ArrayList;

public class Transaction {
    private final int id;
    private double amount;
    private final Status status;


    // changeable fields
    private String description;
    private Category category;
    private Priority priority;
    private ArrayList<String> tags;
    private boolean isDeleted = false;
    private int recurringPeriod; // Repeated every recurringPeriod days, one-time if 0
    private boolean isCompleted = false;

    //Constructor
    Transaction(int id, String description, double amount, Currency currency,
                Category category, LocalDate date, Status status) {
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

    Transaction(int id, String description, double amount, Currency currency, LocalDate date, Status status) {
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


    public String toString() {
        String checkBox = (recurringPeriod > 0) ? "[R]" : (isCompleted ? "[\u2713]" : "[ ]");
        return "Transaction id: " + id + "   " + checkBox + "\namount: "
                + amount + "\ndescription: " + description + "\ncategory: " + category
                + (recurringPeriod > 0 ? "\nperiod: " + recurringPeriod : "");
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

    public LocalDate getDate() {
        return date;
    }

    public Priority getPriority() {
        return priority;
    }

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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

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
        if (this.id == otherTransaction.id) {
            return true;
        } else {
            return false;
        }
    }

    public void delete() {
        isDeleted = true;
    }

    public void recover() {
        isDeleted = false;
    }

    public void convertTo(Currency currency) {
        double toSGD = 1/this.currency.getRate();
        this.amount = currency.getRate() * toSGD;
        this.currency = currency;
    }
}

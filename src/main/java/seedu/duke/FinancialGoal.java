package seedu.duke;

import enumStructure.Currency;
import ui.Ui;

import java.util.Scanner;

public class FinancialGoal {

    private double deposits;
    private String currentGoal;
    private double targetAmount;
    private String description;
    private Currency currency;

    // Changeable fields

    private boolean isAchieved;
    private double currentAmount;
    private boolean isBlank;
    private double expenses;

    // Constructors

    public FinancialGoal(String name, double targetAmount, String description) {
        this.currentGoal = name;
        this.targetAmount = targetAmount;
        this.description = description;
        this.isAchieved = false;
        isBlank = false;
        this.currency = Currency.SGD;
    }

    public FinancialGoal(String name, double targetAmount, String description, Currency currency) {
        this.currentGoal = name;
        this.targetAmount = targetAmount;
        this.description = description;
        this.isAchieved = false;
        isBlank = false;
        this.currency = currency;
    }

    public FinancialGoal() {
        this.currentGoal = "--Loose Savings--";
        this.targetAmount = Integer.MAX_VALUE;
        this.description = "General Savings - Use command to add goal";
        this.isAchieved = false;
        this.deposits = 0;
        isBlank = true;
    }

    // Get methods

    public boolean isAchieved() {
        return this.isAchieved;
    }

    public String getDescription() {
        return this.description;
    }

    public double getTargetAmount() {
        return this.targetAmount;
    }

    public double getCurrentAmount() {
        return this.currentAmount;
    }

    public String getGoal() {
        return this.currentGoal;
    }

    public boolean isBlank() {
        return this.isBlank;
    }

    public double getExpenses() {
        return this.expenses;
    }

    public double getBalance() {
        return this.deposits;
    }

    // Set methods

    public void setDescription(String description) {
        isBlank = false;
        this.description = description;
    }

    public void setTargetAmount(double targetAmount) {
        isBlank = false;
        this.targetAmount = targetAmount;
    }

    public void setGoal(String goal) {
        isBlank = false;
        this.currentGoal = goal;
    }

    // Saving methods

    public void addToSavings(double amount) {
        isBlank = false;
        deposits += amount;
        checkGoalStatus();
    }

    public void subFromSavings(double amount) {
        isBlank = false;
        deposits -= amount;
        Ui.subFromSavings(amount, getBalance());
    }

    public void checkGoalStatus() {
        isAchieved = Ui.printGoalStatus(getBalance(), targetAmount);
    }

    public void updateExpenses(TransactionManager transactions) {
        expenses = transactions.getTotalAmount();
    }

    // Goal setting (uses ui parameter)

    public FinancialGoal createNewGoal(Ui ui) {
        Scanner sc = new Scanner(System.in);
        double amount;
        Ui.createGoalConfirm();

        if (!sc.nextLine().equalsIgnoreCase("Y")) {
            Ui.createGoalAborted();
            return this;
        }

        Ui.createGoalName();
        setGoal(sc.nextLine());

        Ui.createGoalTarget();
        try {
            amount = Double.parseDouble(sc.nextLine());
            if (amount <= 0) {
                ui.showError("Target amount must be a positive number.");
                return this;
            }
            setTargetAmount(amount);
        } catch (NumberFormatException e) {
            ui.showError("Invalid target amount. Please enter a valid number.");
            return this;
        }

        Ui.createGoalDescription();
        setDescription(sc.nextLine());

        Ui.createGoalSuccess();
        return this;
    }

    // To String method

    @Override
    public String toString() {
        if (targetAmount == Integer.MAX_VALUE) {
            return currentGoal + "\n"
                    + description + "\n$"
                    + "balance: " + getBalance() + "\n";
        }
        return currentGoal + "\n"
                + description + "\n$"
                + getBalance() + " / $" + targetAmount + " saved \n"
                + (isAchieved ? "Goal Reached!" : "Keep saving!");
    }
    public void forceSetDeposits(double deposits) {
        this.deposits = deposits;
    }

    public void forceSetAchieved(boolean isAchieved) {
        this.isAchieved = isAchieved;
    }

}

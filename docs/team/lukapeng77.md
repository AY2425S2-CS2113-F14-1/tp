# Project Portfolio Page

## Overview
This project is a text application that interact using text line. The Main Purpose of this product is for expense management.
It covers data saving, data parsing and data analysis.

## Summary of Contributions

### Code Contributions:
Can view my code contribution from [here](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=Luka&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

I implemented five main functional features of this product, such as deletion, set budget and priorities, notification, alert and summary.

---
**Feature Description:**
added three main functionalities to delete transaction, set budget limit and notifications for transactions:
* Delete Transaction (delete): Lets users remove unwanted or erroneous transaction entries using their index in the displayed list.
* Set Budget Limit (setBudget): Enables users to define a spending cap to avoid overspending. Once the total recorded expenses exceed this limit, a warning is displayed to alert the user.
* Set Notifications for Upcoming Payments (notify): Allows users to schedule reminders for future expenses based on the transaction's description, amount, category, and due date.
* Set Priority (priority): Allows users to assign a priority level (low, medium, or high) to a transaction. By default, all expenses are low priority. This helps users focus on the most urgent or important expenses.
* View Spending Alerts (alert): Displays an overview of upcoming expenses, recurring payments, current remaining budget, and whether the spending has exceeded the set budget limit. This command combines notification, priority, and budget insights in a single view.
* Summary of Expenses (summary): Provides an overview of expenses for a specified time frame, enabling quick and effective financial review.
* Convert Currency (convert): Allows users to convert the amount of a transaction from its original currency to a specified target currency. This is particularly useful for users tracking multi-currency expenses or international purchases.
```java
/**
     * Deletes a transaction from the transaction list.
     *
     * @param id the index of the transaction to be removed.
     */
    public void deleteExpense(int id) {
        if (checkIdEmpty(id)) {
            return;
        }
        transactions.remove(id);
    }

/** 
    *function to record and trace the total budget limit
    */
    public void checkBudgetLimit(int budgetLimit) {
        int totalAmount = 0;
        for (Transaction transaction : transactions) {
            if (!transaction.isDeleted()) {
                totalAmount += transaction.getAmount();
            }
        }
        if (totalAmount > budgetLimit) {
            System.out.println("Warning: You have exceeded your budget limit!");
        }
    }
    
/** 
    *Sets a notification for an upcoming transaction
    */
    public void notify(String description, int amount, String categoryString, String date) {
        LocalDate dueDate = LocalDate.parse(date);

        Category category = Category.valueOf(categoryString);

        for (Transaction transaction : transactions) {
            if (transaction.getDescription().equals(description) && transaction.getCategory().equals(category)) {
                transaction.setDate(dueDate);
            }
        }
    }

/**
 * @throws NullException If the date format is invalid.
 */
public AlertCommand(TransactionManager transcations, Ui ui) throws NullException {

    try {
        ui.listNotifications(transcations.getTransactions());
        ConsoleFormatter.printLine();
        ui.listPriorities(transcations.getTransactions());
        Ui.printRecurringTransactions(transcations.getTransactions());
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}

/**
 * @param index       The index for the corresponding transaction.
 * @param priorityStr The string representing the priority level want to set.
 * @throws NullException If the date format is invalid.
 */
public SetPriorityCommand(int index, String priorityStr, TransactionManager transcations, Ui ui) throws NullException {
    Priority priority = Priority.valueOf(priorityStr.toUpperCase());

    try {
        transcations.getTransactions().get(index).setPriority(priority);
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    ui.PrintPriority(transcations.getTransactions(), index);
}

/**
 * Constructs a SummaryCommand and immediately prints a summary of transactions
 * between the specified start and end dates.
 *
 * @param start        The start date (inclusive) of the transaction range.
 * @param end          The end date (inclusive) of the transaction range.
 * @param transactions The TransactionManager object that holds all transactions.
 * @param ui           The Ui object used for displaying output.
 */
public SummaryCommand(LocalDate start, LocalDate end, TransactionManager transactions, Ui ui) {
    List<Transaction> filteredTransactions = transactions.getTransactionsBetween(start, end);
    double total = filteredTransactions.stream()
            .mapToDouble(Transaction::getAmount)
            .sum();
    ui.printSummary(filteredTransactions, total, start, end);
}

/**
 * Constructs a ConvertCommand and immediately converts the currency
 * of the specified transaction to the given target currency.
 *
 * @param transactionId  The index of the transaction to convert.
 * @param targetCurrency The currency to which the transaction should be converted.
 * @param transactions   The TransactionManager object containing all transactions.
 * @param ui             The Ui object used for displaying the conversion result.
 * @throws IndexOutOfBoundsException if the transactionId is invalid (e.g., out of bounds).
 */
public ConvertCommand(int transactionId, Currency targetCurrency, TransactionManager transactions, Ui ui) {
    Transaction transaction = transactions.getTransactions().get(transactionId - 1);

    Currency originalCurrency = transaction.getCurrency();
    double originalAmount = transaction.getAmount();

    transaction.convertTo(targetCurrency);

    ui.printConversion(originalAmount, originalCurrency, transaction.getAmount(), targetCurrency);
}
```
    
**Design Consideration:**

The `deletion`, `budget limit` and `notification` features are designed to improve financial awareness and discipline.
Users can monitor their spending relative to a predefined threshold and receive timely reminders for future payments.

Priority levels help users organize and focus on transactions based on importance or urgency.  
Spending alerts consolidate relevant financial insights—notifications, recurring transactions, and budget status—into
one unified interface for quick decision-making.

Expense Summaries: The summary feature provides users with quick, insightful overviews of their spending patterns 
within a selected time frame, enhancing their ability to review and adjust financial behavior promptly.

The convert feature adds a layer of flexibility for users dealing with multiple currencies. It simplifies financial tracking by converting values into a uniform currency for easier comparison and understanding. It is designed with immediate feedback through the UI to help users understand how currency changes affect their recorded transactions. 
The conversion is performed using the transaction's stored data and is integrated directly into the TransactionManager, ensuring that currency changes do not interfere with other transaction attributes or analytical features.

These capabilities integrate seamlessly with the transaction management system, enhancing the user experience through automation
and clear visual cues for overspending or pending transactions.

---

### Enhancements implemented:
For the budget feature, logic enhancement ensures users verify current budget conditions before adding transactions. 
Users receive immediate feedback if a transaction exceeds the budget limit, encouraging proactive adjustments.

### Contributions to the UG:
I contribute the user command part of UG. I listed the features implemented by myself and gave a simple description and the input command format and examples.

### Contributions to the DG:
I contribute to the V2.0 User stories, Non-functional requirement and my own part sequence diagrams of the features implemented in Developer Guide.
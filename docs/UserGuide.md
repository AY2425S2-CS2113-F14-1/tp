# User Guide

## Introduction

NoteUrSavings is a command-line application that helps students document and plan their expenses. It helps to visualize spendings, record savings, and track progress towards savings goals.

This application is designed to be optimised for a Command Line Interface (CLI), making it more efficient than conventional GUI-based applications for fast typers.


## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Down the latest version of `NoteUrSavings.jar` from [here](http://link.to/duke).
> (Optional) Ideally, relocate the `.jar` file to somewhere easy to find first.
3. Click on the `.jar` file to run.
> If the above does not work, right-click the file and select `Copy as path`. <br>
> Open CMD and run `java -jar [path you just copied]`.

## Features 

### Adding an expense: `add`
Adds an expense entry to the budget tracker.

Format: `add d/DESCRIPTION a/AMOUNT c/CATEGORY [t/TAG]`

* `DESCRIPTION` refers to the name of the expense.
* `AMOUNT` is the cost of the expense.
* `CATEGORY` is the category of spending (e.g., food, transport, entertainment).
* `TAG` (optional) can be used for additional labeling.

Example of usage: 

`add d/Dinner a/15.50 c/Food`  
`add d/Netflix Subscription a/12.99 c/Entertainment t/Monthly`

---

### Listing all expenses: `list`
Displays all recorded expenses.

Format: `list`

---

### Editing an expense: `edit`
Edits an existing expense entry.

Format: `edit [tag] [id] [value]` <br>
Tags: `desc`, `cat`, `am`, `curr`

* `desc`: Task description
* `cat`: Expense category
* `am`: Amount to pay
* `curr`: Currency

Examples:

* `edit desc 1 50mm flathead screws` (Sets description of expense `1` to `50mm flathead screws`)
* `edit cat 2 OTHER` (Sets category of expense `2` to `OTHER`)
* `edit am 3 25` (Sets amount to pay of expense `3` to `25`)
* `edit curr 4 JPY` (Sets currency of expense `4` to Japanese Yen)

---

### Deleting an expense: `delete`
Deletes the specified expense entry.

Format: `delete INDEX`
* `INDEX` refers to the position in the displayed expense list.
Examples:
* `delete 2` (Deletes the 2nd expense in the list)

---

### Clearing all expenses: `clear`
Removes all recorded expenses.

Format: `clear`

---

### Setting a budget limit: `setbudget`
Sets a spending limit to prevent overspending.

Format: `setbudget a/AMOUNT`
* `AMOUNT` is the maximum spending limit.
Example:
* `setBudget a/500` (Sets a spending cap of 500)

---

### Searching for expenses: `search`
Finds expenses based on description or category.

Format: `search [keyword]`, `search id-[id]`

Examples:
* `search Netflix` (Finds expenses with `Netflix` in the description)
* `search id-5` (Finds the expense with id `5`)

---

### Tracking recurring transactions: `recurring`
Creates a recurring expense entry.

Format: `recur [id]/[period]`
* `[period]` refers to how often this payment is made in days. <br>
Example:
* `recur 1/7` (Sets expense with id `1` to recur weekly)

---

### Setting financial goals: `goal`
Set a savings goal.

Format: `goal [tag] [value]` <br>
Tags: `target`, `desc`, `title`, `status`, `new`

* `target`: Update target balance to save up for.
* `desc`: Update goal description.
* `title`: Update goal title.
* `status`: Check progress towards goal.
* `new`: Create new goal.
> By default, goal commands without a recognized tag print the current goal. <br>
> If there is not a goal yet, `goal new` is run instead. <br>

Example:
* `goal target 1000` (Updates target to 1000)
* `goal desc New phone and earpieces` (Updates description to `New phone and earpieces`)
* `goal title Gadgets` (Updates title to `Gadgets`)
* `goal status` (Prints progress towards goal)
* `goal new` (Creates new goal)
* `goal` (Creates new goal if no goal exists, prints goal information otherwise)

---

### Ticking off expenditures: `tick`
Marks an expense as paid or completed.

Format: `tick INDEX`
Example:
* `tick 3 (Marks the 3rd expense as completed)`

---

### Setting notifications for upcoming payments: `notify`
Sets reminders for upcoming expenses.

Format: `notify d/DESCRIPTION a/AMOUNT c/CATEGORY t/DATE`
Example:
* `notify d/Rent a/1000 c/Housing t/2025-03-01 (Sets a reminder for rent payment on March 1st, 2025)`

---

### Prioritizing specific expenses: `priority`
Marks an expense based on different priorities, default set to be low.

Format: `priority INDEX priority_level`
Example:
* `priority 1 high` (Marks the 1st expense as high priority)

---

### Setting alerts for higher spending: `alert`
Marks an expense based on different priorities, default set to be low.

Format: `alert`
Example:
![My Diagram](Screenshoots/Alert_demo.png)


---

### Exiting the program: `exit`
Closes the application.

Format: `exit`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`

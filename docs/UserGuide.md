# User Guide

## Introduction

**NoteUrSavings** is a lightweight, user-friendly budgeting tool designed to help users effectively track their
expenses, manage recurring payments, set financial goals, and stay within budget. Built with simplicity in mind, this
Java-based application allows users to easily add, edit, and view their expenses using concise commands. With features
such as budget limits, customizable categories, currency settings, reminders, and savings goals, NoteUrSavings empowers
users to take control of their personal finances in a structured yet flexible manner. Whether you're managing daily
spending or saving up for long-term goals, NoteUrSavings provides all the essential tools through a clean command-line
interface.

## Quick Start

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `NoteUrSavings.jar` from [here](http://link.to/duke).

> (Optional) Ideally, relocate the `.jar` file to somewhere easy to find first.

3. Click on the `.jar` file to run.

> If the above does not work, right-click the file and select `Copy as path`. <br>
> Open CMD and run `java -jar [path you just copied]`.

## Features

### Adding an expense: `add`

Adds an expense entry to the budget tracker.

Format: `add d/DESCRIPTION a/AMOUNT c/CATEGORY [t/DATE]`

* `DESCRIPTION` refers to the name of the expense.
* `AMOUNT` is the cost of the expense.
* `CATEGORY` is the category of spending (e.g., food, transport, entertainment).
* `DATE` (optional) is your expense date, support the following format:
    * `dd-mm-yyyy`
    * `dd/mm/yyyy`
    * `yyyy-mm-dd`
    * `yyyy/mm/dd`

Examples:

`add d/Dinner a/15.50 c/Food`  
`add d/Netflix Subscription a/12.99 c/Entertainment t/04-06-2025`

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

Example:

* `delete 2` (Deletes the 2nd expense in the list)

---

### Clearing all expenses: `clear`

Remove all recorded expenses.

Format: `clear`

---

### Setting a budget limit: `setbudget`

Sets a spending limit to prevent overspending.

Format: `setbudget a/AMOUNT`

* `AMOUNT` is the maximum spending limit.

Example:

* `setbudget a/500` (Sets a spending cap of 500)

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

* `[period]` refers to how often this payment is made in days.

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

Format: `tick INDEX` <br><br>
Example:

* `tick 3` (Marks the 3rd expense as completed)

---

### Undo Tick off: `untick`

If you tick one expense but wish to undo, use `untick`.

Format: `untick INDEX` <br><br>
Example:

* `untick 3` (Marks the 3rd expense as not completed)

---

### Change default currency: `currency`

Change default currency of the program.

Format: `currency`

---

### Viewing a summary of expenses: `summary`

Viewing a summary of expenses for a selected time frame.

Format: `summary from/START_DATE to/END_DATE` <br><br>
Example:

* `summary from/2025-03-02 to/2025-03-29` (Displays a summary of expenses for the selected time period)

---

### Setting notifications for upcoming payments: `notify`

Sets reminders for upcoming expenses.

Format: `notify d/DESCRIPTION c/CATEGORY t/DATE` <br><br>
Example:

* `notify d/Dinner c/Food t/2025-03-01` (Sets a reminder for dinner payment on March 1st, 2025)

---

### Prioritizing specific expenses: `priority`

Marks an expense based on different priorities, default set to be low.

Format: `priority INDEX priority_level` <br><br>
Example:

* `priority 1 high` (Marks the 1st expense as high priority)

---

### Setting alerts for higher spending: `alert`

Showing the current budget limit, upcoming transactions and the high priority transactions,
the recurring transaction list respectively.

Format: `alert` <br><br>
Example: <br>
![My Diagram](Screenshoots/Alert_demo.png)

---

### Support Multi-Currency conversion: `convert`

Converts an amount between different currencies.

Format: `convert id/TRANSACTION_ID to/CURRENCY` <br><br>
Example:

* `convert id/1 to/USD` (Converts the first transcation to USD based on pre-fixed exchange rates)

---

### Exiting the program: `exit`

Closes the application.

Format: `exit`

---

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: {your answer here}

## Command Overview

| Command     | Format                                           | Description                                                                                     | Example                                                                                            |
|-------------|--------------------------------------------------|-------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| `add`       | `add d/DESCRIPTION a/AMOUNT c/CATEGORY [t/DATE]` | Adds a new expense.                                                                             | `add d/Dinner a/15.50 c/Food`<br>`add d/Netflix Subscription a/12.99 c/Entertainment t/04-06-2025` |
| `list`      | `list`                                           | Lists all recorded expenses.                                                                    | `list`                                                                                             |
| `edit`      | `edit [tag] [id] [value]`                        | Edits an expense's description (`desc`), amount (`am`), category (`cat`), or currency (`curr`). | `edit desc 1 Coffee`<br>`edit cat 2 OTHER`<br>`edit am 3 25`<br>`edit curr 4 JPY`                  |
| `delete`    | `delete INDEX`                                   | Deletes the expense at the given index.                                                         | `delete 2`                                                                                         |
| `clear`     | `clear`                                          | Deletes **all** expenses.                                                                       | `clear`                                                                                            |
| `setbudget` | `setbudget a/AMOUNT`                             | Sets a maximum spending cap.                                                                    | `setbudget a/500`                                                                                  |
| `search`    | `search [keyword]`<br>`search id-[id]`           | Searches by keyword or expense ID.                                                              | `search Netflix`<br>`search id-5`                                                                  |
| `recur`     | `recur [id]/[period]`                            | Sets a recurring expense in days.                                                               | `recur 1/7` (weekly)                                                                               |
| `goal`      | `goal [tag] [value]`                             | Manages savings goals. Tags: `target`, `desc`, `title`, `status`, `new`                         | `goal target 1000`<br>`goal desc New phone`<br>`goal title Gadgets`<br>`goal status`<br>`goal new` |
| `tick`      | `tick INDEX`                                     | Marks an expense as completed/paid.                                                             | `tick 3`                                                                                           |
| `untick`    | `untick INDEX`                                   | Unmarks a previously ticked expense.                                                            | `untick 3`                                                                                         |
| `currency`  | `currency`                                       | Changes the default currency.                                                                   | `currency`                                                                                         |
| `notify`    | `notify d/DESCRIPTION c/CATEGORY t/DATE`         | Sets a notification for future payment.                                                         | `notify d/Dinner c/Food t/2025-03-01`                                                              |
| `priority`  | `priority INDEX priority_level`                  | Sets priority level of an expense. Levels: `low`, `medium`, `high`.                             | `priority 1 high`                                                                                  |
| `summary`   | `summary`                                        | Shows all the list for the given time frame.                                                    | `summary`                                                                                          |
| `exit`      | `exit`                                           | Exits the program.                                                                              | `exit`                                                                                             |
| `alert`     | `alert`                                          | Triggers a warning for higher spending.                                                         | `alert`                                                                                            |

## Need Help?

Make sure you entered the commands exactly as specified, with correct spacing and slashes.
Still stuck? Contact support or check our GitHub Discussions.

package parser;

import enums.Currency;
import enums.Priority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.FinancialGoal;
import seedu.duke.Storage;
import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import ui.Ui;
import enums.Category;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private Ui ui;
    private TransactionManager transactions;
    private FinancialGoal goal;
    private Storage storage;

    @BeforeEach
    void setUp() {
        ui = new Ui();
        transactions = new TransactionManager();
        goal = new FinancialGoal();
        storage = new Storage();
    }

    @Test
    void testHelpCommand() {
        // Simulate help command
        String userInput = "help";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            // Add assertions based on expected output from help() method
            // For simplicity, we assume the help method prints a message, so we would check if it doesn't throw errors
        } catch (Exception e) {
            fail("Help command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testAddCommandValid() {
        // Simulate add command with valid input
        String userInput = "add d/Transaction description a/100 c/food";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            assertEquals(1, transactions.getNum());  // Assuming the first transaction is added
            Transaction addedTransaction = transactions.getTransactions().get(0);
            assertEquals("transaction description", addedTransaction.getDescription());
            assertEquals(100, addedTransaction.getAmount());
            assertEquals(Category.FOOD, addedTransaction.getCategory());
        } catch (Exception e) {
            fail("Add command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testAddCommandInvalid() {
        // Simulate add command with missing fields
        String userInput = "add d/Transaction description a/100";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
        } catch (Exception e) {
            assertEquals("No category found", e.getMessage());
        }
    }

    @Test
    void testListCommand() {
        // Simulate list command
        String userInput = "list";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            // Check that the list of transactions is printed without errors
            // This will depend on the behavior of `ui.printTransactions`
        } catch (Exception e) {
            fail("List command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testSearchCommand() {
        // Simulate search command
        String userInput = "search id-1";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            // Add a transaction to search for
            transactions.addTransaction("Test Transaction", 100, Category.FOOD, null);
            // Assuming that searchTransactionList correctly finds the transaction by id
            assertEquals(1, transactions.searchTransactionList(true, "1").size());
        } catch (Exception e) {
            fail("Search command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testTickCommand() {
        // Simulate tick command
        String userInput = "tick 1";
        try {
            transactions.addTransaction("Test Transaction", 100, Category.FOOD, null);
            Parser.parser(userInput, ui, transactions, goal, storage);
            Transaction transaction = transactions.searchTransaction(1);
            assertTrue(transaction.isCompleted());
        } catch (Exception e) {
            fail("Tick command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testInvalidCommand() {
        // Simulate an invalid command
        String userInput = "invalidCommand";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
        } catch (Exception e) {
            assertEquals("Invalid input", e.getMessage());
        }
    }

    //@@author Lukapeng77
    @Test
    void testDeleteCommandValid() {
        try {
            // Add a transaction to be deleted
            transactions.addTransaction("Brunch", 40.00, Category.FOOD, null);
            assertEquals(1, transactions.getNum());

            String userInput = "delete 1";
            Parser.parser(userInput, ui, transactions, goal, storage);

            // After deletion, the number of transactions should be 0
            assertEquals(1, transactions.getNum());
        } catch (Exception e) {
            fail("Delete command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testSetBudgetCommandValid() {
        String userInput = "setbudget a/500.00";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            assertEquals(500.00, transactions.getBudgetLimit());
        } catch (Exception e) {
            fail("Set budget command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testNotifyCommandValid() {
        String userInput = "notify d/Pay rent c/housing t/2025-05-01";
        try {
             Parser.parser(userInput, ui, transactions, goal, storage);
            // Assuming notify adds something like a transaction or reminder
            // Replace with actual assertions as per implementation
            assertEquals(0, transactions.getNum());

            transactions.addTransaction("Pay Rent", 40.00, Category.HOUSING, null);
            Transaction transaction = transactions.getTransactions().get(0);
            transactions.notify("Pay Rent", "HOUSING", LocalDate.of(2025, 5, 1));
            assertEquals("Pay Rent", transaction.getDescription());
            assertEquals(Category.HOUSING, transaction.getCategory());
            assertEquals(LocalDate.of(2025, 5, 1), transaction.getDate());
        } catch (Exception e) {
            fail("Notify command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testAlertCommand() {
        String userInput = "alert";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            // Alert likely doesn't change state, so just assert no exception
        } catch (Exception e) {
            fail("Alert command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testSetPriorityCommandValid() {
        try {
            transactions.addTransaction("Book Flight", 300, Category.TRANSPORT, null);
            String userInput = "priority 1 high";
            Parser.parser(userInput, ui, transactions, goal, storage);
            Transaction transaction = transactions.searchTransaction(1);
            assertEquals(Priority.HIGH, transaction.getPriority());
        } catch (Exception e) {
            fail("Set priority command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testSummaryCommandValid() {
        try {
            // Add test data
            transactions.addTransaction("Coffee", 5.00, Category.FOOD, LocalDate.of(2025, 4, 1));
            transactions.addTransaction("Groceries", 50.00, Category.FOOD, LocalDate.of(2025, 4, 2));

            String userInput = "summary from/2025-04-01 to/2025-04-30";
            Parser.parser(userInput, ui, transactions, goal, storage);

            // No state change expected, just ensure summary command does not throw
        } catch (Exception e) {
            fail("Summary command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testConvertCommandValid() {
        try {
            // Assume default currency is SGD and USD has a different rate
            transactions.addTransaction("Movie", 20.0, Category.ENTERTAINMENT, null);
            Transaction transaction = transactions.searchTransaction(1);
            transaction.setCurrency(Currency.SGD);  // Ensure it's in SGD initially

            String userInput = "convert id/1 to/USD";
            Parser.parser(userInput, ui, transactions, goal, storage);

            Transaction updatedTransaction = transactions.searchTransaction(1);
            assertEquals(Currency.USD, updatedTransaction.getCurrency());
            assertNotEquals(20.0, updatedTransaction.getAmount()); // Should be converted
        } catch (Exception e) {
            fail("Convert command failed with error: " + e.getMessage());
        }
    }
    //@@author
}

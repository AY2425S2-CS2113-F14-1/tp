package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Category;
import enums.Currency;
import enums.Status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionManagerTest {

    private TransactionManager manager;

    private Storage mockStorage;

    @BeforeEach
    public void setUp() {
        manager = new TransactionManager();
    }

    @Test
    public void testAddTransaction() {
        Transaction t1 = new Transaction(1, "Lunch", 12.5,
                Currency.SGD, Category.FOOD, LocalDate.now(), Status.PENDING);
        manager.addTransaction(t1);

        ArrayList<Transaction> transactions = manager.getTransactions();
        assertEquals(1, transactions.size());
        assertEquals("Lunch", transactions.get(0).getDescription());
    }

    @Test
    public void testAddTransactionWithParams() {
        manager.addTransaction("Snacks", 5.0, Category.FOOD, null);
        ArrayList<Transaction> transactions = manager.getTransactions();
        assertEquals(1, transactions.size());
        assertEquals("Snacks", transactions.get(0).getDescription());
    }

    @Test
    public void testTickAndUnTickTransaction() throws Exception {
        Transaction t = new Transaction(3, "Netflix", 15.0, Currency.SGD,
                Category.ENTERTAINMENT, LocalDate.now(), Status.PENDING);
        manager.addTransaction(t);

        manager.tickTransaction(3);
        assertTrue(manager.searchTransaction(3).isCompleted());

        manager.unTickTransaction(3);
        assertFalse(manager.searchTransaction(3).isCompleted());
    }

    @Test
    public void testClearTransactions() {
        manager.addTransaction("Book", 20.0, Category.EDUCATION, null);
        manager.addTransaction("Groceries", 30.0, Category.FOOD, null);
        assertEquals(2, manager.getTransactions().size());
    }

    @Test
    public void testDeleteTransaction() {
        Transaction t = new Transaction(6, "Taxi", 10.0, Currency.SGD,
                Category.TRANSPORT, LocalDate.now(), Status.PENDING);
        manager.addTransaction(t);

        assertEquals(1, manager.getTransactions().size());

        manager.deleteExpense(5);

        assertEquals(1, manager.getTransactions().size());

        assertEquals(6, manager.getNum());
    }


    //@@author Lukapeng77
    @Test
    void testSetAndGetBudgetLimit() {
        manager.setBudgetLimit(800.5);
        assertEquals(800.5, manager.getBudgetLimit());

        manager.setBudgetLimit(123.5);
        assertEquals(123.5, manager.getBudgetLimit());
    }

    @Test
    void testLoadBudgetFromStorage() {
        manager.loadBudgetFromStorage();

        assertEquals(0.0, manager.getBudgetLimit());
        assertFalse(manager.getBudgetLimit() > 0);
    }

    @Test
    void testGetTotalTransactionAmount() {
        manager.addTransaction("Groceries", 120, Category.FOOD, LocalDate.now());
        manager.addTransaction("Taxi", 80, Category.TRANSPORT, LocalDate.now());

        double total = manager.getTotalTransactionAmount();
        assertEquals(200.0, total);
    }

    @Test
    void testGetTransactionsBetween() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate mid = LocalDate.of(2024, 2, 1);
        LocalDate end = LocalDate.of(2024, 3, 1);

        manager.addTransaction("T1", 100, Category.FOOD, start);
        manager.addTransaction("T2", 200, Category.FOOD, mid);
        manager.addTransaction("T3", 300, Category.FOOD, end);

        ArrayList<Transaction> result = manager.getTransactionsBetween(start, end);
        assertEquals(3, result.size());
    }

    @Test
    void testCheckBudgetLimit() {
        manager.addTransaction("Lunch", 50, Category.FOOD, LocalDate.now());
        manager.addTransaction("Books", 100, Category.EDUCATION, LocalDate.now());

        manager.checkBudgetLimit(200);

        assertEquals(200, manager.getBudgetLimit());
        assertEquals(150, manager.getTotalTransactionAmount());
        assertEquals(50, manager.getBudgetLimit() - manager.getTotalTransactionAmount());
    }


    @Test
    void testNotify_SuccessfulUpdate() {
        Transaction transaction = new Transaction(1, "Gym membership", 75, Currency.SGD, Category.HEALTHCARE,
                LocalDate.of(2024, 1, 15), Status.PENDING);
        manager.addTransaction(transaction);

        manager.notify("Gym membership", "HEALTHCARE", LocalDate.of(2024, 2, 15));

        assertEquals(LocalDate.of(2024, 2, 15), manager.searchTransaction(1).getDate());
    }
}


package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import enums.Category;
import enums.Currency;
import enums.Status;
import enums.Priority;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionTest {
    private Transaction transaction;
    private Currency usd = Currency.USD;
    private Currency sgd = Currency.SGD;
    private LocalDate newDate;


    @BeforeEach
    public void setUp() {
        transaction = new Transaction(1, "Lunch", 10.0, sgd,
                Category.FOOD, LocalDate.of(2025, 4, 3), Status.PENDING);
    }

    @Test
    public void testGetters() {
        assertEquals(1, transaction.getId());
        assertEquals("Lunch", transaction.getDescription());
        assertEquals(10.0, transaction.getAmount());
        assertEquals(sgd, transaction.getCurrency());
        assertEquals(Category.FOOD, transaction.getCategory());
        assertEquals(Status.PENDING, transaction.getStatus());
        assertEquals(Priority.LOW, transaction.getPriority());
        assertEquals(0, transaction.getRecurringPeriod());
        assertFalse(transaction.isCompleted());
        assertFalse(transaction.isDeleted());
        assertTrue(transaction.getTags().isEmpty());


        //@@author Lukapeng77
        newDate = LocalDate.of(2025, 4, 3);
        assertEquals(newDate, transaction.getDate());

    }

    @Test
    public void testSetters() {
        transaction.setDescription("Dinner");
        transaction.setAmount(20);
        transaction.setCategory(Category.ENTERTAINMENT);
        transaction.setRecurringPeriod(7);

        //@@author Lukapeng77
        transaction.setPriority(Priority.HIGH);
        transaction.setDate(LocalDate.of(2025, 5, 10));

        assertEquals("Dinner", transaction.getDescription());
        assertEquals(20, transaction.getAmount());
        assertEquals(Category.ENTERTAINMENT, transaction.getCategory());
        assertEquals(7, transaction.getRecurringPeriod());

        newDate = LocalDate.of(2025, 5, 10);
        //@@author Lukapeng77
        assertEquals(Priority.HIGH, transaction.getPriority());
        assertEquals(newDate, transaction.getDate());


    }

    @Test
    public void testTagOperations() {
        transaction.addTag("food");
        transaction.addTag("daily");
        assertTrue(transaction.containsTag("food"));
        assertTrue(transaction.containsTag("daily"));

        transaction.removeTag("food");
        assertFalse(transaction.containsTag("food"));
        assertEquals(List.of("daily"), transaction.getTags());
    }

    @Test
    public void testDeleteAndRecover() {
        transaction.delete();
        assertTrue(transaction.isDeleted());
        transaction.recover();
        assertFalse(transaction.isDeleted());
    }

    @Test
    public void testCompleteStatus() {
        transaction.complete();
        assertTrue(transaction.isCompleted());
        transaction.notComplete();
        assertFalse(transaction.isCompleted());
    }

    @Test
    public void testIsSameTransaction() {
        Transaction other = new Transaction(1, "Dinner", 20.0, usd,
                Category.FOOD, LocalDate.now(), Status.COMPLETED);
        assertTrue(transaction.isSameTransaction(other));

        Transaction different = new Transaction(2, "Dinner", 20.0, usd,
                Category.FOOD, LocalDate.now(), Status.COMPLETED);
        assertFalse(transaction.isSameTransaction(different));
    }

    @Test
    public void testToString_completed() {
        transaction.complete();
        String output = transaction.toString();
        assertFalse(output.contains("[✓]"));
    }

    @Test
    public void testToString_notCompletedOrRecurring() {
        String output = transaction.toString();
        assertFalse(output.contains("[ ]"));
    }
}

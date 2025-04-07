package command;

import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import ui.Ui;

import java.time.LocalDate;
import java.util.List;

//@@author Lukapeng77

/**
 * Represents a command that summarizes all transactions
 * between a specified start and end date, displaying the total amount
 * and individual transaction details.
 */
public class SummaryCommand extends Command {

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
     * Executes the command.
     * This method is intentionally left empty as the summary is printed in the constructor.
     *
     * @param transactions The TransactionManager object (not used in this implementation).
     * @param ui           The Ui object (not used in this implementation).
     */
    @Override
    public void execute(TransactionManager transactions, Ui ui) {
    }
}

package command;

import enums.Currency;
import exceptions.NullException;
import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import ui.Ui;

//@@author Lukapeng77

/**
 * Represents a command that converts the currency of a specified transaction
 * to a target currency and prints the conversion result.
 */
public class ConvertCommand extends Command {
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

    @Override
    public void execute(TransactionManager transactions, Ui ui) throws NullException {
    }
}

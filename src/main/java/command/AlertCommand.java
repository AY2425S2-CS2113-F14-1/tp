package command;

import exceptions.NullException;
import seedu.duke.TransactionManager;
import ui.Ui;
import static ui.ConsoleFormatter.printLine;

//@@author Lukapeng77
public class AlertCommand extends Command{
    /**
     * @throws NullException If the date format is invalid.
     */
    public AlertCommand(TransactionManager transactions, Ui ui) throws NullException {

        try {
            transactions.checkBudgetLimit();
            ui.listNotifications(transactions.getTransactions());
            printLine();
            ui.listPriorities(transactions.getTransactions());
            ui.printRecurringTransactions(transactions.getRecurringTransactions());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Executes the command by searching for tasks scheduled on the specified date
     * and displaying the results to the user.
     *
     * @param transactions The task list containing all tasks.
     * @param ui           The user interface for displaying results.
     * @throws NullException If an error occurs while processing the command.
     */
    @Override
    public void execute(TransactionManager transactions, Ui ui) throws NullException {
    }
}

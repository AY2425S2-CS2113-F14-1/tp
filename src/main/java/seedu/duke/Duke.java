package seedu.duke;

import ui.Ui;
import parser.Parser;
import java.util.ArrayList;

public class Duke {
    private TransactionManager transactions;
    private Ui ui;
    private FinancialGoal goal;
    private Storage storage;

    public Duke() {
        ui = new Ui();
        storage = new Storage();
        transactions = new TransactionManager();
        goal = storage.loadGoal();

        assert ui != null : "UI should be initialized";
        assert goal != null : "FinancialGoal should be initialized";
        assert storage != null : "Storage should be initialized";
        assert transactions != null : "TransactionManager should be initialized";
    }


    public void run() {
        ui.printWelcomeMessage();

        ArrayList<Transaction> savedTransactions = storage.loadTransactions();
        assert savedTransactions != null : "Loaded transactions should not be null";

        for (Transaction t : savedTransactions) {
            assert t != null : "Loaded transaction should not be null";
            transactions.addTransaction(t);
        }

        transactions.remindRecurringTransactions();

        while (true) {
            String command = ui.readCommand();
            assert command != null && !command.trim().isEmpty() : "User input should not be null or empty";

            Parser.parser(command, ui, transactions, goal, storage);
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}



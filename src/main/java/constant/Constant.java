package constant;

public class Constant {
    // Command keywords
    /**
     * Command keyword for adding a new expense
     */
    public static final String COMMAND_HELP = "help";

    /**
     * Command keyword for listing tasks
     */
    public static final String COMMAND_LIST = "list";

    /**
     * Command keyword for adding a new expense
     */
    public static final String COMMAND_ADD = "add";

    /**
     * Command keyword for searching transactions
     */
    public static final String COMMAND_SEARCH = "search";

    /**
     * Command keyword for setting a spending budget limit
     */
    public static final String COMMAND_SET_BUDGET = "setbudget";

    /**
     * Command keyword for setting reminders for upcoming expenses
     */
    public static final String COMMAND_NOTIFY = "notify";

    /**
     * Command keyword for seeing the coming Alert for expenses
     */
    public static final String COMMAND_ALERT = "alert";

    /**
     * Command keyword for setting priority for expenses
     */
    public static final String COMMAND_SET_PRIORITY = "priority";

    /**
     * Command keyword for modifying savings
     */
    public static final String COMMAND_SUMMARY = "summary";

    public static final String COMMAND_CONVERT = "convert";

    /**
     * Command keyword for unmarking a task (marking it as incomplete)
     */
    public static final String COMMAND_UNTICK = "untick";

    /**
     * Command keyword for unmarking a task (marking it as complete)
     */
    public static final String COMMAND_TICK = "tick";

    /**
     * Command keyword for deleting a task
     */
    public static final String COMMAND_DELETE = "delete";

    /**
     * Command prefix for editing a task
     */
    public static final String COMMAND_EDIT = "edit";

    /**
     * Command keyword for exiting the program
     */
    public static final String COMMAND_EXIT = "exit";

    /**
     * Command keyword for setting a period for a transaction to be recurring
     */
    public static final String COMMAND_RECUR = "recur";

    /**
     * Command prefix for goal-related actions
     */
    public static final String COMMAND_GOAL = "goal";

    /**
     * Command keyword for clear all the transactions
     */
    public static final String COMMAND_CLEAR = "clear";

    /**
     * Command tag for modifying the target amount
     */
    public static final String GOAL_TARGET = "target";

    /**
     * Command tag for modifying the goal description
     */
    public static final String GOAL_DESC = "desc";

    /**
     * Command tag for modifying the goal title
     */
    public static final String GOAL_TITLE = "title";

    /**
     * Command tag for checking whether the goal is met
     */
    public static final String GOAL_STATUS = "status";

    /**
     * Command tag for creating a new goal
     */
    public static final String GOAL_NEW = "new";

    /**
     * Command tag for looking up tasks in a given time period.
     */
    public static final String FIND_DATE = "find";

    /**
     * Represents an invalid input exception.
     * Command tag for editing the description of an expense
     */
    public static final String EDIT_DESC = "desc";

    /**
     * Command tag for editing the category of an expense
     */
    public static final String EDIT_CAT = "cat";

    /**
     * Command tag for editing the value of an expense
     */
    public static final String EDIT_AM = "am";

    /**
     * Command tag for editing the currency of an expense
     */
    public static final String EDIT_CURR = "curr";

    /**
     * Command keyword for changing default currency
     */
    public static final String COMMAND_CURRENCY = "currency";


    /**
     * Represents an invalid input exception
     */
    public static final String INVALID_INPUT = "OOPS!!! I'm sorry, but I don't know what that means :-(";

    /**
     *
     */
    public static final String INVALID_TRANSACTION_ID = "No transaction found at given id! Try again?";

    /**
     * Identifier for setting a budget amount in user input
     */
    public static final String IDENTIFIER_AMOUNT = "a/";
}

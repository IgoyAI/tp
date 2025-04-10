package budgetflow.command;

import budgetflow.exception.FinanceException;
import budgetflow.expense.ExpenseList;
import budgetflow.income.Income;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for LogExpenseCommand.
 * This class tests various scenarios to ensure the correct functionality
 * and error handling of the LogExpenseCommand class.
 */
class LogExpenseCommandTest {
    /**
     * Tests if a valid expense input is logged correctly.
     *
     * @throws FinanceException if an error occurs while executing the command
     */
    //@@author dariusyawningwhiz
    @Test
    void logExpense_validInput_logsExpense() throws FinanceException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command command = new LogExpenseCommand(
                "log-expense category/Dining desc/DinnerWithFriends amt/45.75 d/15-03-2025");
        command.execute(incomes, expenseList);
        String expectedOutput = "Expense logged: Dining | DinnerWithFriends | $45.75 | 15-03-2025";
        assertEquals(expectedOutput, command.getOutputMessage());
    }

    /**
     * Tests handling of an empty input command.
     * Ensures that an appropriate exception is thrown.
     */
    //@@author dariusyawningwhiz
    @Test
    void logExpense_emptyInputTest() {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command command = new LogExpenseCommand("log-expense ");
        try {
            command.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "Expense should not be empty";
            assertEquals(expectedError, e.getMessage());
        }
    }

    /**
     * Tests if the absence of a category results in an error message.
     */
    //@@author dariusyawningwhiz
    @Test
    void logExpense_missingCategory_showsError() {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command command = new LogExpenseCommand(
                "log-expense desc/DinnerWithFriends amt/45.75 d/15-03-2025");
        try {
            command.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "Error: Expense category is required.";
            assertEquals(expectedError, e.getMessage());
        }
    }

    /**
     * Tests if the absence of a description results in an error message.
     */
    //@@author dariusyawningwhiz
    @Test
    void logExpense_missingDescription_showsError() {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command command = new LogExpenseCommand(
                "log-expense category/Dining amt/45.75 d/15-03-2025");
        try {
            command.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "Error: Expense description is required.";
            assertEquals(expectedError, e.getMessage());
        }
    }

    /**
     * Tests if the absence of an amount results in an error message.
     */
    //@@author dariusyawningwhiz
    @Test
    void logExpense_missingAmount_showsError() {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command command = new LogExpenseCommand(
                "log-expense category/Dining desc/DinnerWithFriends d/15-03-2025");
        try {
            command.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "Error: Expense amount is required.";
            assertEquals(expectedError, e.getMessage());
        }
    }

    /**
     * Tests if the absence of a date results in an error message.
     */
    //@@author dariusyawningwhiz
    @Test
    void logExpense_missingDate_showsError() {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command command = new LogExpenseCommand(
                "log-expense category/Dining desc/DinnerWithFriends amt/45.75");
        try {
            command.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "Error: Expense date is required.";
            assertEquals(expectedError, e.getMessage());
        }
    }

    /**
     * Tests if an invalid amount format results in an error message.
     */
    //@@author dariusyawningwhiz
    @Test
    void logExpense_invalidAmountFormat_showsError() {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command command = new LogExpenseCommand(
                "log-expense category/Dining desc/DinnerWithFriends amt/invalid d/15-03-2025");
        try {
            command.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "Error: Expense amount is required.";
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void logExpense_invalidDateFormat_showsError() {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command command = new LogExpenseCommand(
                "log-expense category/Dining desc/DinnerWithFriends amt/45.75 d/2025-03-15");
        try {
            command.execute(incomes, expenseList);
        } catch (FinanceException e) {
            String expectedError = "Error: Income date is in wrong format." +
                    "please use DD-MM-YYYY format.";
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void logExpense_invalidDate_showsError() {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command command = new LogExpenseCommand(
                "log-expense category/Dining desc/DinnerWithFriends amt/45.75 d/99-99-1234");
        try {
            command.execute(incomes, expenseList);
        } catch (FinanceException e) {
            String expectedError = "Error: Date is not a valid date";
            assertEquals(expectedError, e.getMessage());
        }
    }

    //@@author thienkimtranhoang
    @Test
    void addIncome_extraParameters_ignoresExtraParams() throws FinanceException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command command = new AddIncomeCommand("add category/Salary amt/2500.00 d/15-03-2025 extra/parameter");
        command.execute(incomes, expenseList);
        String expectedOutput = "Income added: Salary, Amount: $2500.00, Date: 15-03-2025";
        assertEquals(expectedOutput, command.getOutputMessage());
    }



}

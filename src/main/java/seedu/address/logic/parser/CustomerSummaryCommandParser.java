package seedu.address.logic.parser;

import seedu.address.logic.commands.CustomerSummaryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input for the customerSummary command.
 * Currently this command does not take arguments.
 */
public class CustomerSummaryCommandParser implements Parser<CustomerSummaryCommand> {

    @Override
    public CustomerSummaryCommand parse(String args) throws ParseException {
        return new CustomerSummaryCommand();
    }
}

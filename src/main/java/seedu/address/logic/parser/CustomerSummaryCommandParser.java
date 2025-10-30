package seedu.address.logic.parser;

import seedu.address.logic.commands.CustomerSummaryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input for the customerSummary command.
 * This command does not take arguments.
 */
public class CustomerSummaryCommandParser implements Parser<CustomerSummaryCommand> {

    @Override
    public CustomerSummaryCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        if (!trimmed.isEmpty()) {
            throw new ParseException(
                    String.format("This command does not take arguments. Usage: %s",
                            CustomerSummaryCommand.MESSAGE_USAGE));
        }
        return new CustomerSummaryCommand();
    }
}

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BILL_AMOUNT;

import seedu.address.logic.commands.UpdatePointsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new {@code UpdatePointsCommand} object.
 * Command format: updatepoints p/{@code <phone>} b/{@code <bill amount>}
 */
public class UpdatePointsCommandParser implements Parser<UpdatePointsCommand> {

    private static final String DECIMAL_REGEX = "^\\d+(\\.\\d{1,2})?$"; // up to 2 decimal places

    /**
     * Parses the given {@code String} of arguments in the context of the {@code UpdatePointsCommand}
     * and returns an {@code UpdatePointsCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public UpdatePointsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_BILL_AMOUNT);

        boolean hasPhone = argMultimap.getValue(PREFIX_PHONE).isPresent();
        boolean hasAmount = argMultimap.getValue(PREFIX_BILL_AMOUNT).isPresent();

        if (!hasPhone || !hasAmount || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, UpdatePointsCommand.MESSAGE_USAGE));
        }

        // Prevent duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE, PREFIX_BILL_AMOUNT);

        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        String amountStr = argMultimap.getValue(PREFIX_BILL_AMOUNT).get().trim();

        // Validate format: numeric, up to 2 decimal places
        if (!amountStr.matches(DECIMAL_REGEX)) {
            throw new ParseException("Amount must be a positive number with at most 2 decimal places.");
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid number format for amount.");
        }

        if (amount <= 0) {
            throw new ParseException("Amount must be greater than zero.");
        }

        return new UpdatePointsCommand(phone, amount);
    }
}

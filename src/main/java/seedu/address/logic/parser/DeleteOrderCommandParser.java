package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.DeleteOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteOrderCommand object
 */
public class DeleteOrderCommandParser implements Parser<DeleteOrderCommand> {
    @Override
    public DeleteOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_INDEX);
        if (!arePrefixesPresent(argMultimap, PREFIX_PHONE, PREFIX_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE, PREFIX_INDEX);
        int supplierPhone;
        int orderIndex;
        try {
            supplierPhone = Integer.parseInt(argMultimap.getValue(PREFIX_PHONE).get());
        } catch (NumberFormatException e) {
            throw new ParseException("Phone number must be numeric.");
        }
        try {
            orderIndex = Integer.parseInt(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (NumberFormatException e) {
            throw new ParseException("Order index must be a valid integer.");
        }
        if (orderIndex < 1) {
            throw new ParseException("Order index must be a positive integer.");
        }
        return new DeleteOrderCommand(supplierPhone, orderIndex);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        for (Prefix prefix : prefixes) {
            if (!argumentMultimap.getValue(prefix).isPresent()) {
                return false;
            }
        }
        return true;
    }
}

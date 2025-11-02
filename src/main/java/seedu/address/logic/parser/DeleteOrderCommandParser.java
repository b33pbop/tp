package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDERNUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.DeleteOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.OrderIndex;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new DeleteOrderCommand object
 */
public class DeleteOrderCommandParser implements Parser<DeleteOrderCommand> {
    @Override
    public DeleteOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_ORDERNUM);
        if (!arePrefixesPresent(argMultimap, PREFIX_PHONE, PREFIX_ORDERNUM) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE, PREFIX_ORDERNUM);
        Phone supplierPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        OrderIndex orderIndexParsed = ParserUtil.parseOrderIndex(argMultimap.getValue(PREFIX_ORDERNUM).get());
        int orderIndex = Integer.parseInt(orderIndexParsed.toString());
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

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERYDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITPRICE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the given {@code String} of arguments in the context of the AddOrderCommand
 * and returns an AddOrderCommand object for execution.
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {

    @Override
    public AddOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_PHONE,
                        PREFIX_ITEM,
                        PREFIX_QUANTITY,
                        PREFIX_UNITPRICE,
                        PREFIX_DELIVERYDAY);

        // all parameters need to be present (check for it)
        if (!arePrefixesPresent(argMultimap, PREFIX_PHONE, PREFIX_ITEM, PREFIX_QUANTITY, PREFIX_UNITPRICE,
                PREFIX_DELIVERYDAY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }
        // check got no dupes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE, PREFIX_ITEM, PREFIX_QUANTITY, PREFIX_UNITPRICE,
                PREFIX_DELIVERYDAY);

        // then process from their formats into strings
        int supplierPhone = Integer.parseInt(argMultimap.getValue(PREFIX_PHONE).get());
        String orderItem = argMultimap.getValue(PREFIX_ITEM).get();
        int orderQuantity = Integer.parseInt(argMultimap.getValue(PREFIX_QUANTITY).get());
        double orderUnitPrice = Double.parseDouble(argMultimap.getValue(PREFIX_UNITPRICE).get());
        String orderDeliveryDay = argMultimap.getValue(PREFIX_DELIVERYDAY).get();

        return new AddOrderCommand(supplierPhone, orderItem, orderQuantity, orderUnitPrice, orderDeliveryDay);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

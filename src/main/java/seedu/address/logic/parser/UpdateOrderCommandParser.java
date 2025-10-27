package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERYDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDERNUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITPRICE;

import seedu.address.logic.commands.UpdateOrderCommand;
import seedu.address.logic.commands.UpdateOrderCommand.UpdateOrderDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateOrderCommand object
 */
public class UpdateOrderCommandParser implements Parser<UpdateOrderCommand> {

    @Override
    public UpdateOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_PHONE,
                PREFIX_ORDERNUM,
                PREFIX_ITEM,
                PREFIX_QUANTITY,
                PREFIX_UNITPRICE,
                PREFIX_DELIVERYDAY);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE,
                PREFIX_ORDERNUM,
                PREFIX_ITEM,
                PREFIX_QUANTITY,
                PREFIX_UNITPRICE,
                PREFIX_DELIVERYDAY);

        UpdateOrderDescriptor updateOrderDescriptor = new UpdateOrderDescriptor();

        if (argMultimap.getValue(PREFIX_PHONE).isEmpty() || argMultimap.getValue(PREFIX_ORDERNUM).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateOrderCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_ITEM).isPresent()) {
            updateOrderDescriptor.updateItem(argMultimap.getValue(PREFIX_ITEM).get());
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            updateOrderDescriptor.updateQuantity(Integer.parseInt(argMultimap.getValue(PREFIX_QUANTITY).get()));
        }
        if (argMultimap.getValue(PREFIX_UNITPRICE).isPresent()) {
            String unitPriceRaw = argMultimap.getValue(PREFIX_UNITPRICE).get().trim();
            if (unitPriceRaw.startsWith("$")) {
                unitPriceRaw = unitPriceRaw.substring(1);
            }
            updateOrderDescriptor.updateUnitPrice(Double.parseDouble(unitPriceRaw));
        }
        if (argMultimap.getValue(PREFIX_DELIVERYDAY).isPresent()) {
            updateOrderDescriptor.updateDeliveryDay(argMultimap.getValue(PREFIX_DELIVERYDAY).get());
        }

        if (!updateOrderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateOrderCommand.MESSAGE_NO_CHANGE);
        }

        int supplierPhone = Integer.parseInt(argMultimap.getValue(PREFIX_PHONE).get());
        int orderNumber = Integer.parseInt(argMultimap.getValue(PREFIX_ORDERNUM).get());

        return new UpdateOrderCommand(supplierPhone, orderNumber, updateOrderDescriptor);
    }
}

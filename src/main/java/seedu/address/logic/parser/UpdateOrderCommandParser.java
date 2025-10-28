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
import seedu.address.model.person.ItemDeliveryDay;
import seedu.address.model.person.ItemQuantity;
import seedu.address.model.person.ItemUnitPrice;
import seedu.address.model.person.Phone;

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
            updateOrderDescriptor.updateItem(ParserUtil.parseItemName(argMultimap.getValue(PREFIX_ITEM).get()));
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            ItemQuantity quantityRaw = ParserUtil.parseItemQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
            updateOrderDescriptor.updateQuantity(quantityRaw);
        }
        if (argMultimap.getValue(PREFIX_UNITPRICE).isPresent()) {
            String unitPriceRaw = argMultimap.getValue(PREFIX_UNITPRICE).get().trim();
            if (unitPriceRaw.startsWith("$")) {
                unitPriceRaw = unitPriceRaw.substring(1);
            }
            updateOrderDescriptor.updateUnitPrice(new ItemUnitPrice(unitPriceRaw));
        }
        if (argMultimap.getValue(PREFIX_DELIVERYDAY).isPresent()) {
            String deliveryDayRaw = argMultimap.getValue(PREFIX_DELIVERYDAY).get();
            updateOrderDescriptor.updateDeliveryDay(new ItemDeliveryDay(deliveryDayRaw));
        }

        if (!updateOrderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateOrderCommand.MESSAGE_NO_CHANGE);
        }

        Phone supplierPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        int orderNumber = Integer.parseInt(argMultimap.getValue(PREFIX_ORDERNUM).get());

        return new UpdateOrderCommand(supplierPhone, orderNumber, updateOrderDescriptor);
    }
}

package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERYDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITPRICE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ItemUnitPrice;
import seedu.address.model.person.Order;
import seedu.address.model.person.Supplier;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.SupplierBuilder;

public class AddOrderCommandParserTest {
    private AddOrderCommandParser parser = new AddOrderCommandParser();
    private Supplier supplier = new SupplierBuilder().build();
    private String expectedInput = " p/ 85355255 i/ Chicken q/ 99 u/ 0.99 d/ every Thursday";


    @Test
    public void allFieldsPresent_success() {
        Order expectedOrder = new OrderBuilder().build();
        AddOrderCommand expectedCommand = new AddOrderCommand(supplier.getPhone(),
                expectedOrder.getItem(),
                expectedOrder.getQuantity(),
                expectedOrder.getUnitPrice(),
                expectedOrder.getDeliveryDay());

        assertParseSuccess(parser, AddOrderCommand.COMMAND_WORD + expectedInput, expectedCommand);
    }

    @Test
    public void repeatedFieldsPresent_failure() {

        // multiple phone numbers
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD + expectedInput + " p/ 91111111",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple items
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD + expectedInput + " i/ Another item",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM));

        // multiple quantity
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD + expectedInput + " q/ 10000",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));

        // multiple unit price
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD + expectedInput + " u/ 1.17",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_UNITPRICE));

        // multiple delivery day
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD + expectedInput + " d/ every Saturday",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DELIVERYDAY));
    }

    @Test
    public void missingCompulsoryFields_failure() {
        String expectedErrorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE);

        // missing phone number
        String missingPhone = " i/ Chicken q/ 99 u/ 0.99 d/ every Thursday";
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD + missingPhone, expectedErrorMessage);

        // missing item
        String missingItem = " p/ 85355255 q/ 99 u/ 0.99 d/ every Thursday";
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD + missingItem, expectedErrorMessage);

        // missing quantity
        String missingQuantity = " p/ 85355255 i/ Chicken u/ 0.99 d/ every Thursday";
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD + missingQuantity, expectedErrorMessage);

        // missing unit price
        String missingUnitPrice = " p/ 85355255 i/ Chicken q/ 99 d/ every Thursday";
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD + missingUnitPrice, expectedErrorMessage);

        // missing delivery day
        String missingDeliveryDay = " p/ 85355255 i/ Chicken q/ 99 u/ 0.99";
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD + missingDeliveryDay, expectedErrorMessage);

        // all prefixes missing
        String missingAll = " 85355255 Chicken 99 0.99 every Thursday";
        assertParseFailure(parser, AddOrderCommand.COMMAND_WORD + missingAll, expectedErrorMessage);
    }

    @Test
    public void parse_unitPriceWithDollar_success() throws ParseException {
        String args = "p/85355255 i/Chicken q/99 u/$0.99 d/every Thursday";
        AddOrderCommand cmd = parser.parse(args);
        assertEquals(new ItemUnitPrice("0.99"), cmd.getOrderUnitPrice());
    }

    @Test
    public void parse_unitPriceWithoutDollar_success() throws ParseException {
        String args = "p/85355255 i/Chicken q/99 u/0.99 d/every Thursday";
        AddOrderCommand cmd = parser.parse(args);
        assertEquals(new ItemUnitPrice("0.99"), cmd.getOrderUnitPrice());
    }
}

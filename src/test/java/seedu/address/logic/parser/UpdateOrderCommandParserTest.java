package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERYDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDERNUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITPRICE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateOrderCommand;
import seedu.address.logic.commands.UpdateOrderCommand.UpdateOrderDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Order;
import seedu.address.model.person.Supplier;
import seedu.address.testutil.SupplierBuilder;

public class UpdateOrderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                                        UpdateOrderCommand.MESSAGE_USAGE);

    private UpdateOrderCommandParser parser = new UpdateOrderCommandParser();

    @Test
    public void missingParameters_failure() {
        // missing phone number
        String missingPhone = " o/ 1 i/ Testing";
        assertParseFailure(parser, UpdateOrderCommand.COMMAND_WORD + missingPhone, MESSAGE_INVALID_FORMAT);

        // missing order number
        String missingOrderNum = " p/ 91111111 i/ Testing";
        assertParseFailure(parser, UpdateOrderCommand.COMMAND_WORD + missingOrderNum, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void duplicateFields_failure() {
        String validInput = " p/ 91111111 o/ 1 i/ Testing q/ 99 u/ 0.99 d/ every Monday";

        // duplicate phone
        assertParseFailure(parser, UpdateOrderCommand.COMMAND_WORD + " p/ 99999999" + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // duplicate order number
        assertParseFailure(parser, UpdateOrderCommand.COMMAND_WORD + " o/ 9" + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ORDERNUM));

        // duplicate item
        assertParseFailure(parser, UpdateOrderCommand.COMMAND_WORD + " i/ Another Item" + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM));

        // duplicate quantity
        assertParseFailure(parser, UpdateOrderCommand.COMMAND_WORD + " q/ 999" + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));

        // duplicate unit price
        assertParseFailure(parser, UpdateOrderCommand.COMMAND_WORD + " u/ 1.79" + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_UNITPRICE));

        // duplicate delivery day
        assertParseFailure(parser, UpdateOrderCommand.COMMAND_WORD + " d/ every Sunday" + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DELIVERYDAY));
    }

    @Test
    public void allFieldsIncluded() {
        String userInput = "update p/ 85355255 o/ 1 i/ Updated q/ 1000 u/ 111.11 d/ Today";

        Supplier supplier = new SupplierBuilder().withCategory("Supplier").build();
        Order baseOrder = new Order("Pencils",
                40,
                0.5,
                "every Friday");
        supplier.addOrder(baseOrder);
        int supplierPhone = Integer.parseInt(supplier.getPhone().value);

        UpdateOrderDescriptor updateOrderDescriptor = new UpdateOrderDescriptor();
        updateOrderDescriptor.updateItem("Updated");
        updateOrderDescriptor.updateQuantity(1000);
        updateOrderDescriptor.updateUnitPrice(111.11);
        updateOrderDescriptor.updateDeliveryDay("Today");

        UpdateOrderCommand expectedCommand = new UpdateOrderCommand(supplierPhone, 1, updateOrderDescriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void someFieldsIncluded() {
        String userInput = "update p/ 85355255 o/ 1 q/ 1000 d/ Today";

        Supplier supplier = new SupplierBuilder().withCategory("Supplier").build();
        Order baseOrder = new Order("Pencils",
                40,
                0.5,
                "every Friday");
        supplier.addOrder(baseOrder);
        int supplierPhone = Integer.parseInt(supplier.getPhone().value);

        UpdateOrderDescriptor updateOrderDescriptor = new UpdateOrderDescriptor();
        updateOrderDescriptor.updateQuantity(1000);
        updateOrderDescriptor.updateDeliveryDay("Today");

        UpdateOrderCommand expectedCommand = new UpdateOrderCommand(supplierPhone, 1, updateOrderDescriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void oneFieldIncluded() {
        String userInput = "update p/ 85355255 o/ 1 ";
        Supplier supplier = new SupplierBuilder().withCategory("Supplier").build();
        Order baseOrder = new Order("Pencils",
                40,
                0.5,
                "every Friday");
        supplier.addOrder(baseOrder);
        int supplierPhone = Integer.parseInt(supplier.getPhone().value);

        // only item
        UpdateOrderDescriptor onlyItem = new UpdateOrderDescriptor();
        onlyItem.updateItem("Updated");
        UpdateOrderCommand updateItemOnly = new UpdateOrderCommand(supplierPhone, 1, onlyItem);
        assertParseSuccess(parser, userInput + "i/ Updated", updateItemOnly);

        // only quantity
        UpdateOrderDescriptor onlyQuantity = new UpdateOrderDescriptor();
        onlyQuantity.updateQuantity(20);
        UpdateOrderCommand updateQuantityOnly = new UpdateOrderCommand(supplierPhone, 1, onlyQuantity);
        assertParseSuccess(parser, userInput + "q/ 20", updateQuantityOnly);

        // only unit price
        UpdateOrderDescriptor onlyUnitPrice = new UpdateOrderDescriptor();
        onlyUnitPrice.updateUnitPrice(2.22);
        UpdateOrderCommand updateUnitPriceOnly = new UpdateOrderCommand(supplierPhone, 1, onlyUnitPrice);
        assertParseSuccess(parser, userInput + "u/ 2.22", updateUnitPriceOnly);

        // only delivery day
        UpdateOrderDescriptor onlyDeliveryDay = new UpdateOrderDescriptor();
        onlyDeliveryDay.updateDeliveryDay("Updated");
        UpdateOrderCommand updateDeliveryDayOnly = new UpdateOrderCommand(supplierPhone, 1, onlyDeliveryDay);
        assertParseSuccess(parser, userInput + "d/ Updated", updateDeliveryDayOnly);


    }

    @Test
    public void parse_unitPriceWithDollar_success() throws ParseException {
        String args = "p/91234567 o/1 i/Pens q/100 u/$0.90 d/Tuesday";
        UpdateOrderCommand cmd = parser.parse(args);
        assertEquals(0.90, cmd.descriptor.getUnitPrice().get());
    }

    @Test
    public void parse_unitPriceWithoutDollar_success() throws ParseException {
        String args = "p/91234567 o/1 i/Pens q/100 u/0.90 d/Tuesday";
        UpdateOrderCommand cmd = parser.parse(args);
        assertEquals(0.90, cmd.descriptor.getUnitPrice().get());
    }

    @Test
    public void parse_withDeliveryDay_success() throws ParseException {
        String args = "p/91234567 o/1 i/Pens q/100 u/0.90 d/Tuesday";
        UpdateOrderCommand cmd = parser.parse(args);
        assertEquals("Tuesday", cmd.descriptor.getDeliveryDay().get());
    }

    @Test
    public void parse_withoutDeliveryDay_success() throws ParseException {
        String args = "p/91234567 o/1 i/Pens q/100 u/0.90";
        UpdateOrderCommand cmd = parser.parse(args);
        assertFalse(cmd.descriptor.getDeliveryDay().isPresent());
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ItemDeliveryDay;
import seedu.address.model.person.ItemName;
import seedu.address.model.person.ItemQuantity;
import seedu.address.model.person.ItemUnitPrice;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Supplier;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.SupplierBuilder;

public class AddOrderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void addOrderToSupplier_success() throws CommandException {
        Supplier supplier = new SupplierBuilder().withCategory("Supplier").build();
        Order newOrder = new OrderBuilder().build();

        Phone supplierPhone = supplier.getPhone();
        ItemName orderItem = newOrder.getItem();
        ItemQuantity orderQuantity = newOrder.getQuantity();
        ItemUnitPrice orderUnitPrice = newOrder.getUnitPrice();
        ItemDeliveryDay orderDeliveryDay = newOrder.getDeliveryDay();
        AddOrderCommand addOrderCommand = new AddOrderCommand(supplierPhone, orderItem,
                                                                orderQuantity, orderUnitPrice,
                                                                orderDeliveryDay);
        AddCommand addCommand = new AddCommand(supplier);
        addCommand.execute(model);
        CommandResult result = addOrderCommand.execute(model);
        assertEquals(AddOrderCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void supplierIsNull_failure() throws CommandException, ParseException {
        Order newOrder = new OrderBuilder().build();
        Supplier supplier = new SupplierBuilder().withPhone("91111111").build();

        Phone supplierPhone = ParserUtil.parsePhone("91111111");
        ItemName orderItem = newOrder.getItem();
        ItemQuantity orderQuantity = newOrder.getQuantity();
        ItemUnitPrice orderUnitPrice = newOrder.getUnitPrice();
        ItemDeliveryDay orderDeliveryDay = newOrder.getDeliveryDay();
        AddOrderCommand addOrderCommand = new AddOrderCommand(supplierPhone, orderItem,
                orderQuantity, orderUnitPrice,
                orderDeliveryDay);

        AddCommand addCommand = new AddCommand(supplier);
        addCommand.execute(model);
        assertCommandFailure(addOrderCommand, model, AddOrderCommand.MESSAGE_NOT_SUPPLIER);
    }

    @Test
    public void personFoundNotSupplier() throws ParseException, CommandException {
        Order newOrder = new OrderBuilder().build();
        Person notSupplier = new PersonBuilder().withPhone("91111111").build();

        Phone supplierPhone = ParserUtil.parsePhone("91111111");
        ItemName orderItem = newOrder.getItem();
        ItemQuantity orderQuantity = newOrder.getQuantity();
        ItemUnitPrice orderUnitPrice = newOrder.getUnitPrice();
        ItemDeliveryDay orderDeliveryDay = newOrder.getDeliveryDay();

        AddOrderCommand addOrderCommand = new AddOrderCommand(supplierPhone, orderItem,
                orderQuantity, orderUnitPrice,
                orderDeliveryDay);

        AddCommand addCommand = new AddCommand(notSupplier);
        addCommand.execute(model);
        assertCommandFailure(addOrderCommand, model, AddOrderCommand.MESSAGE_NOT_SUPPLIER);
    }

    @Test
    public void equals() {
        Supplier supplier = new SupplierBuilder().build();
        Order firstOrder = new OrderBuilder().withItem(new ItemName("Pencils")).build();
        Order secondOrder = new OrderBuilder().withItem(new ItemName("Pens")).build();

        Phone supplierPhone = supplier.getPhone();
        ItemName firstItem = firstOrder.getItem();
        ItemQuantity firstQuantity = firstOrder.getQuantity();
        ItemUnitPrice firstUnitPrice = firstOrder.getUnitPrice();
        ItemDeliveryDay firstDeliveryDay = firstOrder.getDeliveryDay();

        ItemName secondItem = secondOrder.getItem();
        ItemQuantity secondQuantity = secondOrder.getQuantity();
        ItemUnitPrice secondUnitPrice = secondOrder.getUnitPrice();
        ItemDeliveryDay secondDeliveryDay = secondOrder.getDeliveryDay();


        AddOrderCommand addPencilsCommand = new AddOrderCommand(supplierPhone, firstItem,
                firstQuantity, firstUnitPrice,
                firstDeliveryDay);
        AddOrderCommand addPensCommand = new AddOrderCommand(supplierPhone, secondItem,
                secondQuantity, secondUnitPrice,
                secondDeliveryDay);

        // same object -> return true
        assertEquals(addPencilsCommand, addPencilsCommand);

        // same values -> return true
        AddOrderCommand addPencilsCommandCopy = new AddOrderCommand(supplierPhone, firstItem,
                firstQuantity, firstUnitPrice,
                firstDeliveryDay);
        assertEquals(addPencilsCommand, addPencilsCommandCopy);

        //different types -> return false
        assertNotEquals(2, addPencilsCommand);

        // null -> return false
        assertNotEquals(null, addPencilsCommand);

        // different values -> return false
        assertNotEquals(addPensCommand, addPencilsCommand);

    }
}

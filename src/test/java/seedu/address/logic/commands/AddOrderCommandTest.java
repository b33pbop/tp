package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Order;
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

        int supplierPhone = Integer.parseInt(supplier.getPhone().value);
        String orderItem = newOrder.getItem();
        int orderQuantity = newOrder.getQuantity();
        double orderUnitPrice = newOrder.getUnitPrice();
        String orderDeliveryDay = newOrder.getDeliveryDay();
        AddOrderCommand addOrderCommand = new AddOrderCommand(supplierPhone, orderItem,
                                                                orderQuantity, orderUnitPrice,
                                                                orderDeliveryDay);
        AddCommand addCommand = new AddCommand(supplier);
        addCommand.execute(model);
        CommandResult result = addOrderCommand.execute(model);
        assertEquals(result.getFeedbackToUser(), AddOrderCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void equals() {
        Supplier supplier = new SupplierBuilder().build();
        Order firstOrder = new OrderBuilder().withItem("Pencils").build();
        Order secondOrder = new OrderBuilder().withItem("Pens").build();

        int supplierPhone = Integer.parseInt(supplier.getPhone().value);
        String firstItem = firstOrder.getItem();
        int firstQuantity = firstOrder.getQuantity();
        double firstUnitPrice = firstOrder.getUnitPrice();
        String firstDeliveryDay = firstOrder.getDeliveryDay();

        String secondItem = secondOrder.getItem();
        int secondQuantity = secondOrder.getQuantity();
        double secondUnitPrice = secondOrder.getUnitPrice();
        String secondDeliveryDay = secondOrder.getDeliveryDay();


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
        assertFalse(addPensCommand.equals(addPencilsCommand));

    }
}

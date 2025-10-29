package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.UpdateOrderCommand.createEditedOrder;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateOrderCommand.UpdateOrderDescriptor;
import seedu.address.model.person.ItemDeliveryDay;
import seedu.address.model.person.ItemName;
import seedu.address.model.person.ItemQuantity;
import seedu.address.model.person.ItemUnitPrice;
import seedu.address.model.person.Order;

public class UpdateOrderDescriptorTest {

    @Test
    public void fieldsNullUponCreation() {
        UpdateOrderDescriptor emptyDescriptor = new UpdateOrderDescriptor();
        assertFalse(emptyDescriptor.isAnyFieldEdited());
    }
    @Test
    public void successfulUpdateOfParameters() {
        Order baseOrder = new Order(new ItemName("Pencils"),
                                    new ItemQuantity("10"),
                                    new ItemUnitPrice("0.1"),
                                    new ItemDeliveryDay("every Monday"));

        // if name different -> not equal
        UpdateOrderDescriptor nameTest = new UpdateOrderDescriptor();
        nameTest.updateItem(new ItemName("Changed item"));
        Order orderWithEditedName = createEditedOrder(baseOrder, nameTest);
        assertNotEquals(orderWithEditedName, baseOrder);

        // if quantity different -> no equal
        UpdateOrderDescriptor quantityTest = new UpdateOrderDescriptor();
        quantityTest.updateQuantity(new ItemQuantity("100"));
        Order orderWithEditedQuantity = createEditedOrder(baseOrder, quantityTest);
        assertNotEquals(orderWithEditedQuantity, baseOrder);

        // if unit price different -> no equal
        UpdateOrderDescriptor unitPriceTest = new UpdateOrderDescriptor();
        unitPriceTest.updateUnitPrice(new ItemUnitPrice("0.5"));
        Order orderWithEditedUnitPrice = createEditedOrder(baseOrder, unitPriceTest);
        assertNotEquals(orderWithEditedUnitPrice, baseOrder);

        // if delivery date different -> no equal
        UpdateOrderDescriptor deliveryDateTest = new UpdateOrderDescriptor();
        deliveryDateTest.updateDeliveryDay(new ItemDeliveryDay("every Saturday"));
        Order orderWithEditedDeliveryDate = createEditedOrder(baseOrder, deliveryDateTest);
        assertNotEquals(orderWithEditedDeliveryDate, baseOrder);
    }

    @Test
    public void equals() {
        UpdateOrderDescriptor baseDescriptor = new UpdateOrderDescriptor();

        assertEquals(baseDescriptor, baseDescriptor);

        // another data type
        assertNotEquals(baseDescriptor, 10);

        // different item name
        UpdateOrderDescriptor differentName = new UpdateOrderDescriptor();
        differentName.updateItem(new ItemName("Testing"));
        assertNotEquals(differentName, baseDescriptor);

        // different item quantity
        UpdateOrderDescriptor differentQuantity = new UpdateOrderDescriptor();
        differentQuantity.updateQuantity(new ItemQuantity("20"));
        assertNotEquals(differentQuantity, baseDescriptor);

        // different item unit price
        UpdateOrderDescriptor differentUnitPrice = new UpdateOrderDescriptor();
        differentUnitPrice.updateUnitPrice(new ItemUnitPrice("20.23"));
        assertNotEquals(differentUnitPrice, baseDescriptor);

        // different item delivery day
        UpdateOrderDescriptor differentDeliveryDay = new UpdateOrderDescriptor();
        differentDeliveryDay.updateDeliveryDay(new ItemDeliveryDay("Tuesday"));
        assertNotEquals(differentDeliveryDay, baseDescriptor);
    }
}

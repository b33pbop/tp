package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.UpdateOrderCommand.createEditedOrder;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateOrderCommand.UpdateOrderDescriptor;
import seedu.address.model.person.ItemName;
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
                                    10,
                                    0.1,
                                    "every Monday");

        // if name different -> not equal
        UpdateOrderDescriptor nameTest = new UpdateOrderDescriptor();
        nameTest.updateItem(new ItemName("Changed item"));
        Order orderWithEditedName = createEditedOrder(baseOrder, nameTest);
        assertNotEquals(orderWithEditedName, baseOrder);

        // if quantity different -> no equal
        UpdateOrderDescriptor quantityTest = new UpdateOrderDescriptor();
        quantityTest.updateQuantity(100);
        Order orderWithEditedQuantity = createEditedOrder(baseOrder, quantityTest);
        assertNotEquals(orderWithEditedQuantity, baseOrder);

        // if unit price different -> no equal
        UpdateOrderDescriptor unitPriceTest = new UpdateOrderDescriptor();
        unitPriceTest.updateUnitPrice(0.5);
        Order orderWithEditedUnitPrice = createEditedOrder(baseOrder, unitPriceTest);
        assertNotEquals(orderWithEditedUnitPrice, baseOrder);

        // if delivery date different -> no equal
        UpdateOrderDescriptor deliveryDateTest = new UpdateOrderDescriptor();
        deliveryDateTest.updateDeliveryDay("every Saturday");
        Order orderWithEditedDeliveryDate = createEditedOrder(baseOrder, deliveryDateTest);
        assertNotEquals(orderWithEditedDeliveryDate, baseOrder);
    }
}

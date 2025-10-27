package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.OrderBuilder;

public class OrderTest {

    @Test
    public void equals() {
        Order baseCopy = new OrderBuilder().build();

        // same object -> return true
        assertEquals(baseCopy, baseCopy);

        // same values -> return true
        Order secondBaseCopy = new OrderBuilder().build();
        assertEquals(baseCopy, secondBaseCopy);

        // null -> return false
        assertNotEquals(null, baseCopy);

        // different type -> return false
        assertNotEquals(baseCopy, 17);

        // different order -> return false
        Order editedCopy = new OrderBuilder().withItem(new ItemName("Not an item")).build();
        assertNotEquals(baseCopy, editedCopy);

        // different item -> return false
        Order differentName = new OrderBuilder().withItem(new ItemName("Changed item")).build();
        assertNotEquals(baseCopy, differentName);

        // different quantity
        Order differentQuantity = new OrderBuilder().withQuantity(1717).build();
        assertNotEquals(baseCopy, differentQuantity);

        // different unit price -> return false
        Order differentUnitPrice = new OrderBuilder().withUnitPrice(new ItemUnitPrice("0.17")).build();
        assertNotEquals(baseCopy, differentUnitPrice);

        // different delivery day -> return false
        Order differentDeliveryDay = new OrderBuilder().withDeliveryDay("Changed Day of Delivery").build();
        assertNotEquals(baseCopy, differentDeliveryDay);
    }
}

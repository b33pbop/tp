package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.ItemDeliveryDay;
import seedu.address.model.person.ItemName;
import seedu.address.model.person.ItemQuantity;
import seedu.address.model.person.ItemUnitPrice;
import seedu.address.model.person.Order;

public class JsonAdaptedOrderTest {

    private static final ItemName VALID_ITEM = new ItemName("Cabbage");
    private static final ItemQuantity VALID_QUANTITY = new ItemQuantity("10");
    private static final ItemUnitPrice VALID_UNIT_PRICE = new ItemUnitPrice("3.25");
    private static final ItemDeliveryDay VALID_DELIVERY_DAY = new ItemDeliveryDay("Every Tuesday");

    private static final Order VALID_ORDER = new Order(VALID_ITEM, VALID_QUANTITY,
            VALID_UNIT_PRICE, VALID_DELIVERY_DAY);

    @Test
    public void toModelType_validOrderDetails_returnsOrder() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ORDER);
        assertTrue(VALID_ORDER.equals(order.toModelType()));
    }

    @Test
    public void toModelType_validFields_returnsOrder() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, VALID_QUANTITY,
                VALID_UNIT_PRICE, VALID_DELIVERY_DAY);
        Order modelOrder = order.toModelType();

        assertEquals(VALID_ITEM, modelOrder.getItem());
        assertEquals(VALID_QUANTITY, modelOrder.getQuantity());
        assertEquals(VALID_UNIT_PRICE, modelOrder.getUnitPrice());
        assertEquals(VALID_DELIVERY_DAY, modelOrder.getDeliveryDay());
    }

    @Test
    public void toModelType_negativeQuantity_failure() {
        assertThrows(IllegalArgumentException.class, () -> new ItemQuantity(("-5")));
    }

    @Test
    public void toModelType_zeroQuantity_failure() {
        assertThrows(IllegalArgumentException.class, () -> new ItemQuantity(("0")));
    }

    @Test
    public void toModelType_negativeUnitPrice_returnsOrder() {
        assertThrows(IllegalArgumentException.class, () -> new ItemUnitPrice(("-5.9")));
    }

    @Test
    public void toModelType_zeroUnitPrice_returnsOrder() {
        ItemUnitPrice zeroPrice = new ItemUnitPrice("0.0");
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, VALID_QUANTITY,
                zeroPrice, VALID_DELIVERY_DAY);
        Order modelOrder = order.toModelType();

        assertEquals(zeroPrice, modelOrder.getUnitPrice());
    }

    @Test
    public void constructor_fromOrderSource_preservesAllFields() {
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(VALID_ORDER);

        assertEquals(VALID_ITEM, adaptedOrder.getItem());
        assertEquals(VALID_QUANTITY, adaptedOrder.getQuantity());
        assertEquals(VALID_UNIT_PRICE, adaptedOrder.getUnitPrice());
        assertEquals(VALID_DELIVERY_DAY, adaptedOrder.getDeliveryDay());
    }

}

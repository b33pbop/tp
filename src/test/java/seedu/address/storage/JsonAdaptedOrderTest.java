package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Order;

public class JsonAdaptedOrderTest {

    private static final String VALID_ITEM = "Cabbage";
    private static final int VALID_QUANTITY = 10;
    private static final double VALID_UNIT_PRICE = 3.25;
    private static final String VALID_DELIVERY_DATE = "2024-12-31";

    private static final String INVALID_DELIVERY_DATE = "invalid-date";
    private static final String INVALID_DELIVERY_DATE_FORMAT = "31/12/2024"; // Wrong format

    private static final Order VALID_ORDER = new Order(VALID_ITEM, VALID_QUANTITY,
            VALID_UNIT_PRICE, LocalDate.parse(VALID_DELIVERY_DATE));

    @Test
    public void toModelType_validOrderDetails_returnsOrder() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ORDER);
        assertTrue(VALID_ORDER.equals(order.toModelType()));
    }

    @Test
    public void toModelType_validFields_returnsOrder() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, VALID_QUANTITY,
                VALID_UNIT_PRICE, VALID_DELIVERY_DATE);
        Order modelOrder = order.toModelType();

        assertEquals(VALID_ITEM, modelOrder.getItem());
        assertEquals(VALID_QUANTITY, modelOrder.getQuantity());
        assertEquals(VALID_UNIT_PRICE, modelOrder.getUnitPrice());
        assertEquals(LocalDate.parse(VALID_DELIVERY_DATE), modelOrder.getDeliveryDate());
    }

    @Test
    public void toModelType_nullDeliveryDate_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, VALID_QUANTITY,
                VALID_UNIT_PRICE, null);
        assertThrows(NullPointerException.class, order::toModelType);
    }

    @Test
    public void toModelType_negativeQuantity_returnsOrder() {
        // Assuming negative quantities are allowed (returns, etc.)
        int negativeQuantity = -5;
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, negativeQuantity,
                VALID_UNIT_PRICE, VALID_DELIVERY_DATE);
        Order modelOrder = order.toModelType();

        assertEquals(negativeQuantity, modelOrder.getQuantity());
    }

    @Test
    public void toModelType_zeroQuantity_returnsOrder() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, 0,
                VALID_UNIT_PRICE, VALID_DELIVERY_DATE);
        Order modelOrder = order.toModelType();

        assertEquals(0, modelOrder.getQuantity());
    }

    @Test
    public void toModelType_negativeUnitPrice_returnsOrder() {
        // Assuming negative prices are allowed (discounts, refunds, etc.)
        double negativePrice = -50.0;
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, VALID_QUANTITY,
                negativePrice, VALID_DELIVERY_DATE);
        Order modelOrder = order.toModelType();

        assertEquals(negativePrice, modelOrder.getUnitPrice());
    }

    @Test
    public void toModelType_zeroUnitPrice_returnsOrder() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, VALID_QUANTITY,
                0.0, VALID_DELIVERY_DATE);
        Order modelOrder = order.toModelType();

        assertEquals(0.0, modelOrder.getUnitPrice());
    }

    @Test
    public void toModelType_emptyItem_returnsOrder() {
        String emptyItem = "";
        JsonAdaptedOrder order = new JsonAdaptedOrder(emptyItem, VALID_QUANTITY,
                VALID_UNIT_PRICE, VALID_DELIVERY_DATE);
        Order modelOrder = order.toModelType();

        assertEquals(emptyItem, modelOrder.getItem());
    }

    @Test
    public void toModelType_specialCharactersInItem_returnsOrder() {
        String specialItem = "Item@With#Special$Chars%";
        JsonAdaptedOrder order = new JsonAdaptedOrder(specialItem, VALID_QUANTITY,
                VALID_UNIT_PRICE, VALID_DELIVERY_DATE);
        Order modelOrder = order.toModelType();

        assertEquals(specialItem, modelOrder.getItem());
    }

    @Test
    public void constructor_fromOrderSource_preservesAllFields() {
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(VALID_ORDER);

        assertEquals(VALID_ITEM, adaptedOrder.getItem());
        assertEquals(VALID_QUANTITY, adaptedOrder.getQuantity());
        assertEquals(VALID_UNIT_PRICE, adaptedOrder.getUnitPrice());
        assertEquals(VALID_DELIVERY_DATE, adaptedOrder.getDeliveryDate());
    }

    @Test
    public void toModelType_leapYearDate_returnsOrder() {
        String leapYearDate = "2024-02-29"; // 2024 is a leap year
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, VALID_QUANTITY,
                VALID_UNIT_PRICE, leapYearDate);
        Order modelOrder = order.toModelType();

        assertEquals(LocalDate.parse(leapYearDate), modelOrder.getDeliveryDate());
    }

    @Test
    public void toModelType_minimumDate_returnsOrder() {
        String minDate = "0001-01-01";
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, VALID_QUANTITY,
                VALID_UNIT_PRICE, minDate);
        Order modelOrder = order.toModelType();

        assertEquals(LocalDate.parse(minDate), modelOrder.getDeliveryDate());
    }
}

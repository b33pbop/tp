package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.person.ItemName;
import seedu.address.model.person.ItemQuantity;
import seedu.address.model.person.ItemUnitPrice;
import seedu.address.model.person.Order;

/**
 * Jackson-friendly version of {@link Order}.
 */
public class JsonAdaptedOrder {

    private final ItemName item;
    private final ItemQuantity quantity;
    private final ItemUnitPrice unitPrice;
    private final String deliveryDay; // store as a string for JSON

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("itemName") ItemName item,
                            @JsonProperty("quantity") ItemQuantity quantity,
                            @JsonProperty("itemUnitPrice") ItemUnitPrice unitPrice,
                            @JsonProperty("deliveryDay") String deliveryDay) {
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.deliveryDay = deliveryDay;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use
     */
    public JsonAdaptedOrder(Order source) {
        this.item = source.getItem();
        this.quantity = source.getQuantity();
        this.unitPrice = source.getUnitPrice();
        this.deliveryDay = source.getDeliveryDay();
    }

    public ItemName getItem() {
        return item;
    }
    public ItemQuantity getQuantity() {
        return quantity;
    }
    public ItemUnitPrice getUnitPrice() {
        return unitPrice;
    }
    public String getDeliveryDay() {
        return deliveryDay;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Order} object.
     */
    public Order toModelType() {
        return new Order(item, quantity, unitPrice, deliveryDay);
    }
}

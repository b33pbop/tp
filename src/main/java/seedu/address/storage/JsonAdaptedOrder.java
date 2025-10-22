package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.person.Order;

/**
 * Jackson-friendly version of {@link Order}.
 */
public class JsonAdaptedOrder {

    private final String item;
    private final int quantity;
    private final double unitPrice;
    private final String deliveryDate; // store as a string for JSON

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("item") String item,
                            @JsonProperty("quantity") int quantity,
                            @JsonProperty("unitPrice") double unitPrice,
                            @JsonProperty("deliveryDate") String deliveryDate) {
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.deliveryDate = deliveryDate;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use
     */
    public JsonAdaptedOrder(Order source) {
        this.item = source.getItem();
        this.quantity = source.getQuantity();
        this.unitPrice = source.getUnitPrice();
        this.deliveryDate = source.getDeliveryDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Order} object.
     */
    public Order toModelType() {
        LocalDate parsedDate = LocalDate.parse(deliveryDate, DateTimeFormatter.ISO_LOCAL_DATE);
        return new Order(item, quantity, unitPrice, parsedDate);
    }
}

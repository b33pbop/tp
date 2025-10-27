package seedu.address.testutil;

import seedu.address.model.person.ItemName;
import seedu.address.model.person.Order;

/**
 * A utility class to help with building Orders
 */
public class OrderBuilder {

    public static final ItemName DEFAULT_ITEM = new ItemName("Chicken");
    public static final int DEFAULT_QUANTITY = 99;
    public static final double DEFAULT_UNITPRICE = 0.99;
    public static final String DEFAULT_DELIVERYDAY = "every Thursday";

    protected ItemName item;
    protected int quantity;
    protected double unitPrice;
    protected String deliveryDay;

    /**
     * Creates an Order with the default details
     */
    public OrderBuilder() {
        this.item = DEFAULT_ITEM;
        this.quantity = DEFAULT_QUANTITY;
        this.unitPrice = DEFAULT_UNITPRICE;
        this.deliveryDay = DEFAULT_DELIVERYDAY;
    }

    /**
     * Creates an Order based on the details of the given order
     * @param toCopy The order to be copied from
     */
    public OrderBuilder(Order toCopy) {
        this.item = toCopy.getItem();
        this.quantity = toCopy.getQuantity();
        this.unitPrice = toCopy.getUnitPrice();
        this.deliveryDay = toCopy.getDeliveryDay();
    }

    /**
     * Sets the name of the item of the order we are building
     * @param item The name of the item in the new order
     * @return An OrderBuilder object with the name of the item specified
     */
    public OrderBuilder withItem(ItemName item) {
        this.item = item;
        return this;
    }

    /**
     * Sets the quantity of the item of the order we are building
     * @param quantity The quantity of the item in the new order
     * @return An OrderBuilder object with the quantity of the item specified
     */
    public OrderBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Sets the unit price of the item of the order we are building
     * @param unitPrice The unit price of the item in the new order
     * @return An OrderBuilder object with the unit price of the item specified
     */
    public OrderBuilder withUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    /**
     * Sets the delivery day of the item of the order we are building
     * @param deliveryDay The day of delivery of the item in the new order
     * @return An OrderBuilder object with the delivery day of the item specified
     */
    public OrderBuilder withDeliveryDay(String deliveryDay) {
        this.deliveryDay = deliveryDay;
        return this;
    }

    public Order build() {
        return new Order(item, quantity, unitPrice, deliveryDay);
    }



}

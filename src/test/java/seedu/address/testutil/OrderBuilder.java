package seedu.address.testutil;

import seedu.address.model.person.Order;

public class OrderBuilder {

    public static final String DEFAULT_ITEM = "Chicken";
    public static final int DEFAULT_QUANTITY = 99;
    public static final double DEFAULT_UNITPRICE = 0.99;
    public static final String DEFAULT_DELIVERYDAY = "every Thursday";

    protected String item;
    protected int quantity;
    protected double unitPrice;
    protected String deliveryDay;

    public OrderBuilder(){
        this.item = DEFAULT_ITEM;
        this.quantity = DEFAULT_QUANTITY;
        this.unitPrice = DEFAULT_UNITPRICE;
        this.deliveryDay = DEFAULT_DELIVERYDAY;
    }

    public OrderBuilder(Order toCopy) {
        this.item = toCopy.getItem();
        this.quantity = toCopy.getQuantity();
        this.unitPrice = toCopy.getUnitPrice();
        this.deliveryDay = toCopy.getDeliveryDay();
    }

    public OrderBuilder withItem(String item) {
        this.item = item;
        return this;
    }

    public OrderBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderBuilder withUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public OrderBuilder withDeliveryDay(String deliveryDay) {
        this.deliveryDay = deliveryDay;
        return this;
    }

    public Order build() {
        return new Order(item, quantity, unitPrice, deliveryDay);
    }



}

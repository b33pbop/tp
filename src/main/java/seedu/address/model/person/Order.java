package seedu.address.model.person;

/**
 * Represents an order made to a supplier
 */
public class Order {

    private final ItemName item;
    private final ItemQuantity quantity;
    private final ItemUnitPrice unitPrice;
    private final String deliveryDay;

    /**
     * Constructor for order
     * @param item name of item to be ordered
     * @param quantity quantity of item
     * @param unitPrice price of each item
     * @param deliveryDay day to be delivered
     */
    public Order(ItemName item, ItemQuantity quantity, ItemUnitPrice unitPrice, String deliveryDay) {
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.deliveryDay = deliveryDay;
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

    @Override
    public String toString() {
        return String.format("%s of %s (at $%s each) to be delivered %s",
                getQuantity(), getItem(), getUnitPrice(), getDeliveryDay());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;

        if (!(otherOrder.getItem().equals(this.item))) {
            return false;
        }

        if (!(otherOrder.getQuantity() == this.quantity)) {
            return false;
        }

        if (!(otherOrder.getUnitPrice().equals(this.unitPrice))) {
            return false;
        }

        if (!(otherOrder.getDeliveryDay().equals(this.deliveryDay))) {
            return false;
        }

        return true;

    }
}

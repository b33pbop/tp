package seedu.address.model.person;

import java.time.LocalDate;

/**
 * Represents an order made to a supplier
 */
public class Order {

    private String item;
    private int quantity;
    private double unitPrice;
    private LocalDate deliveryDate;

    /**
     * Constructor for order
     * @param item name of item to be ordered
     * @param quantity quantity of item
     * @param unitPrice price of each item
     * @param deliveryDate date to be delivered
     */
    public Order(String item, int quantity, double unitPrice, LocalDate deliveryDate) {
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.deliveryDate = deliveryDate;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    @Override
    public String toString() {
        return String.format("%s of %s (at $%s each) to be delivered %s",
                getQuantity(), getItem(), getUnitPrice(), getDeliveryDate());
    }


}

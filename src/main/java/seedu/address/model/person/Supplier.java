package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.category.Category;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Supplier extends Person {

    // Identity fields
    private final ArrayList<Order> orders;

    /**
     * Every field must be present and not null.
     */
    public Supplier(Name name, Phone phone, Email email, Address address, Category category) {
        super(name, phone, email, address, category);
        requireAllNonNull(name, phone, email, address, category);
        this.orders = new ArrayList<>();
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Supplier)) {
            return false;
        }

        Supplier otherPerson = (Supplier) other;
        return super.equals(otherPerson);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", super.getName())
                .add("phone", super.getPhone())
                .add("email", super.getEmail())
                .add("address", super.getAddress())
                .add("category", super.getCategory())
                .toString();
    }

    /**
     * Takes in a new order to be placed and adds it to the list
     * @param newOrder Order to be placed
     */
    public void addOrder(Order newOrder) {
        orders.add(newOrder);
    }

    public ArrayList<Order> getOrders() {
        return new ArrayList<>(this.orders);
    }

    /**
     * Returns a string content the orders placed under supplier in numbered list format
     * @return String of orders in list
     */
    public String listOrders() {
        return IntStream.range(0, orders.size())
                .mapToObj(i -> String.valueOf(i + 1) + ". " + orders.get(i).toString())
                .collect(Collectors.joining("\n"));
    }

    public int getSize() {
        return this.orders.size();
    }

    public Order getOrder(int index) {
        return this.orders.get(index - 1);
    }

    public void updateOrders(int index, Order newOrder) {
        this.orders.set(index - 1, newOrder);
    }

    /**
     * Removes an order from the supplier's order list.
     * @param index The 1-based index of the order to remove
     */
    public void removeOrder(int index) {
        this.orders.remove(index - 1);
    }

    /**
     * Checks whether the specified order exists in the list
     * @param toCheck The order to be checked
     * @return A boolean value indicating whether the order is in the list
     */
    public boolean hasOrder(Order toCheck) {
        for (Order current : this.orders) {
            if (current.equals(toCheck)) {
                return true;
            }
        }
        return false;
    }
}

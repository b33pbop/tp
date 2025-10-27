package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.category.Category;
import seedu.address.model.tier.Tier;

/**
 * Represents a customer in the address book where customer is a customer who has signed up for membership
 * Inherits from person and tracks the customer's points and membership tier.
 */
public class Customer extends Person {
    private Tier tier;
    private int points;

    /**
     * Every field must be present and not null.
     */
    public Customer(Name name, Phone phone, Email email, Address address, Category category) {
        super(name, phone, email, address, category);
        requireAllNonNull(name, phone, email, address, category);
        this.tier = Tier.MEMBER;
        this.points = 0;
    }

    public Tier getTier() {
        return this.tier;
    }

    public int getPoints() {
        return this.points;
    }

    /**
     * Takes the amount a customer spent and converts it into points to add to the customer's points.
     * Updates the customer's membership tier if applicable.
     *
     * @param amountSpent Amount of money a customer spends.
     */
    public void addPointsFromSpending(double amountSpent) {
        int points = calculatePointsFromSpending(amountSpent);
        this.points += points;
        updateTier();
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameCustomer(Customer otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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
        if (!(other instanceof Customer otherPerson)) {
            return false;
        }

        return otherPerson.isSameCustomer(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private int calculatePointsFromSpending(double amount) {
        return (int) amount; // truncates decimal points
    }

    private void updateTier() {
        this.tier = Tier.getTierForPoints(this.points);
    }
}

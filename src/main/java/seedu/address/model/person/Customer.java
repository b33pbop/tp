package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
/*
Possible methods to implement would be spend(double amount)
should create a tier class to take care of tier abstraction and discounts
 */
public class Customer extends Person {
    //private static final double STARTING_AMOUNT = 0;
    // Identity fields
    //private final double totalSpent;
    //create Tier class?
    //private Tier tier

    /**
     * Every field must be present and not null.
     */
    public Customer(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        requireAllNonNull(name, phone, email, address, tags);
        //this.totalSpent = STARTING_AMOUNT;
        //this.tier = new Tier();

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

}

package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.model.category.Category;

/**
 * Represents a staff member in the address book.
 * Inherits from person. Additional staff-specific behavior/fields can be added later.
 */
public class Staff extends Person {

    /**
     * Every field must be present and not null.
     */
    public Staff(Name name, Phone phone, Email email, Address address, Set<Category> categories) {
        super(name, phone, email, address, categories);
        requireAllNonNull(name, phone, email, address, categories);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameStaff(Staff otherPerson) {
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
        if (!(other instanceof Staff otherPerson)) {
            return false;
        }

        return otherPerson.isSameStaff(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

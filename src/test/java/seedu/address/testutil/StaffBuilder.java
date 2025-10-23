package seedu.address.testutil;

import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;

/**
 * A utility class to help with building {@link Staff} objects.
 * Inherits from {@link PersonBuilder}.
 */
public class StaffBuilder extends PersonBuilder {

    public StaffBuilder() {
        super();
    }

    public StaffBuilder(Person personToCopy) {
        super(personToCopy);
    }

    @Override
    public Staff build() {
        return new Staff(name, phone, email, address, category, shift);
    }

    @Override
    public StaffBuilder withName(String name) {
        super.withName(name);
        return this;
    }

    @Override
    public StaffBuilder withPhone(String phone) {
        super.withPhone(phone);
        return this;
    }

    @Override
    public StaffBuilder withEmail(String email) {
        super.withEmail(email);
        return this;
    }

    @Override
    public StaffBuilder withAddress(String address) {
        super.withAddress(address);
        return this;
    }

    @Override
    public StaffBuilder withCategory(String category) {
        super.withCategory(category);
        return this;
    }

    /**
     * Sets the {@code Shift} of the {@code Staff} that we are building.
     */
    public StaffBuilder withShift(String shift) {
        super.withShift(shift);
        return this;
    }
}

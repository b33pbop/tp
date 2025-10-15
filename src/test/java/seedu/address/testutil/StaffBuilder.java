package seedu.address.testutil;

import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;

/**
 * A utility class to help with building Staff objects.
 * It inherits from PersonBuilder.
 */
public class StaffBuilder extends PersonBuilder {

    /**
     * Creates a {@code StaffBuilder} with the default details.
     */
    public StaffBuilder() {
        super();
    }

    /**
     * Initializes the {@code StaffBuilder} with the data of {@code personToCopy}.
     */
    public StaffBuilder(Person personToCopy) {
        super(personToCopy);
    }

    @Override
    public Staff build() {
        // reuse all the fields defined in PersonBuilder
        return new Staff(name, phone, email, address, categories);
    }
}

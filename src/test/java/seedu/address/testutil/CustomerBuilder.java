package seedu.address.testutil;

import seedu.address.model.category.Category;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building {@link Customer} objects.
 * Inherits from {@link PersonBuilder}.
 */
public class CustomerBuilder extends PersonBuilder {

    /**
     * Creates a default {@code SupplierBuilder} with default item Chicken.
     */
    public CustomerBuilder() {
        super();
    }

    /**
     * Creates a {@code CustomerBuilder} using an existing {@code Person} to copy fields.
     *
     * @param personToCopy The person to copy fields from.
     */
    public CustomerBuilder(Person personToCopy) throws IllegalArgumentException {
        super(personToCopy);
        this.category = new Category("Customer");
    }

    @Override
    public Customer build() {
        return new Customer(name, phone, email, address, category);
    }

    @Override
    public CustomerBuilder withName(String name) {
        super.withName(name);
        return this;
    }

    @Override
    public CustomerBuilder withPhone(String phone) {
        super.withPhone(phone);
        return this;
    }

    @Override
    public CustomerBuilder withEmail(String email) {
        super.withEmail(email);
        return this;
    }

    @Override
    public CustomerBuilder withAddress(String address) {
        super.withAddress(address);
        return this;
    }

    @Override
    public CustomerBuilder withCategory(String category) {
        super.withCategory("Customer"); // always Supplier
        return this;
    }
}

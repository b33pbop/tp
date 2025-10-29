package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_CUSTOMER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Customer} objects to be used in tests.
 */
public class TypicalCustomers {
    public static final Customer ALICE = new CustomerBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withCategory("Customer")
            .build();
    public static final Customer BENSON = new CustomerBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com")
            .withPhone("98765432").withCategory("Customer")
            .build();
    public static final Customer CARL = new CustomerBuilder().withName("Carl Kurz")
            .withPhone("95352563").withEmail("heinz@example.com")
            .withAddress("wall street").withCategory("Customer")
            .build();
    public static final Customer DANIEL = new CustomerBuilder().withName("Daniel Meier")
            .withPhone("87652533").withEmail("cornelia@example.com")
            .withAddress("10th street").withCategory("Customer")
            .build();
    public static final Customer ELLE = new CustomerBuilder().withName("Elle Meyer")
            .withPhone("94822241").withEmail("werner@example.com")
            .withAddress("michegan ave").withCategory("Customer")
            .build();
    public static final Customer FIONA = new CustomerBuilder().withName("Fiona Kunz")
            .withPhone("94824271").withEmail("lydia@example.com")
            .withAddress("little tokyo").withCategory("Customer")
            .build();
    public static final Customer GEORGE = new CustomerBuilder().withName("George Best")
            .withPhone("94824421").withEmail("anna@example.com")
            .withAddress("4th street").withCategory("Customer")
            .build();

    // Manually added
    public static final Customer HOON = new CustomerBuilder().withName("Hoon Meier")
            .withPhone("84824241").withEmail("stefan@example.com")
            .withAddress("little india").withCategory("Customer")
            .build();
    public static final Customer IDA = new CustomerBuilder().withName("Ida Mueller")
            .withPhone("84821311").withEmail("hans@example.com")
            .withAddress("chicago ave").withCategory("Customer")
            .build();

    // Manually added - Customer's details found in {@code CommandTestUtil}
    public static final Customer AMY = new CustomerBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withCategory(VALID_CATEGORY_CUSTOMER)
            .build();

    public static final Customer BOB = new CustomerBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withCategory(VALID_CATEGORY_CUSTOMER)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCustomers() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalCustomers()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalCustomers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}

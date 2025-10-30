package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.category.Category;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tier.Tier;

public class CustomerSummaryCommandTest {

    @Test
    public void summary_twoCustomers_correctCounts() {
        Customer alice = new Customer(
                new Name("Alice"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("Some Street"),
                new Category("Customer")
        );
        Customer bob = new Customer(
                new Name("Bob"),
                new Phone("98765432"),
                new Email("bob@example.com"),
                new Address("Another Street"),
                new Category("Customer")
        );
        alice.addPointsFromSpending(100);
        bob.addPointsFromSpending(200);

        int totalPoints = alice.getPoints() + bob.getPoints();
        assertEquals(300, totalPoints);

        assertEquals(Tier.getTierForPoints(100), alice.getTier());
        assertEquals(Tier.getTierForPoints(200), bob.getTier());
    }

    @Test
    public void tierProgression_checkThresholds() {
        Customer c = new Customer(
                new Name("Eve"),
                new Phone("91112222"),
                new Email("eve@example.com"),
                new Address("Skyline"),
                new Category("Customer")
        );

        c.addPointsFromSpending(9999); // hit top
        assertNotNull(c.getTier());
    }

    @Test
    public void execute_noCustomers_showsNoCustomersMessage() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        CustomerSummaryCommand command = new CustomerSummaryCommand();

        CommandResult result = command.execute(model);

        assertEquals("There are currently no customers in the address book.",
                result.getFeedbackToUser());
    }

    @Test
    public void execute_withCustomers_showsSummary() {
        AddressBook ab = new AddressBook();
        Model model = new ModelManager(ab, new UserPrefs());

        Customer alice = new Customer(
                new Name("Alice"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("Some Street"),
                new Category("Customer")
        );
        alice.addPointsFromSpending(150);

        Customer bob = new Customer(
                new Name("Bob"),
                new Phone("98765432"),
                new Email("bob@example.com"),
                new Address("Another Street"),
                new Category("Customer")
        );
        bob.addPointsFromSpending(200);

        model.addPerson(alice);
        model.addPerson(bob);

        CustomerSummaryCommand command = new CustomerSummaryCommand();
        CommandResult result = command.execute(model);

        String output = result.getFeedbackToUser();
        assertTrue(output.contains("Customer summary:"));
        assertTrue(output.contains("Total customers: 2"));
        assertTrue(output.contains("Total points across all customers: 350"));
    }
}

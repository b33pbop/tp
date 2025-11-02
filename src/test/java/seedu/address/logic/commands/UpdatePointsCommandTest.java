package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Phone;
import seedu.address.model.tier.Tier;
import seedu.address.testutil.CustomerBuilder;

public class UpdatePointsCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(); // resets model before each test
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        // Customer not in model
        model.addPerson(ALICE);
        Phone phone = new Phone("89998888");
        int newPoints = 100;

        UpdatePointsCommand command = new UpdatePointsCommand(phone, newPoints);

        Exception exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(UpdatePointsCommand.MESSAGE_PERSON_NOT_FOUND, phone), exception.getMessage());
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        // Model is empty
        UpdatePointsCommand command = new UpdatePointsCommand(new Phone("81234567"), 200);

        Exception exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(UpdatePointsCommand.MESSAGE_EMPTY_LIST, exception.getMessage());
    }

    @Test
    public void execute_validCustomer_success() throws Exception {
        // Arrange
        Customer original = new CustomerBuilder()
                .withName("Alice")
                .withPhone("81234567")
                .withTier(Tier.BRONZE)
                .build();

        model.addPerson(original);

        double amountSpent = 200.0;
        int pointsAdded = (int) amountSpent; // points earned from this spending

        UpdatePointsCommand command = new UpdatePointsCommand(original.getPhone(), amountSpent);
        CommandResult result = command.execute(model);

        // Message only shows points added
        assertEquals(String.format(UpdatePointsCommand.MESSAGE_SUCCESS,
                original.getName(), pointsAdded), result.getFeedbackToUser());

        // Total points = previous + added
        Customer updatedCustomer = (Customer) model.getFilteredPersonList().get(0);
        assertEquals(pointsAdded, updatedCustomer.getPoints());


    }

    @Test
    public void equals_sameAndDifferent() {
        Phone phone1 = new Phone("81234567");
        Phone phone2 = new Phone("89998888");

        UpdatePointsCommand command1 = new UpdatePointsCommand(phone1, 100);
        UpdatePointsCommand command2 = new UpdatePointsCommand(phone2, 200);

        // same object -> true
        assertEquals(command1, command1);

        // same values -> true
        UpdatePointsCommand command1Copy = new UpdatePointsCommand(phone1, 100);
        assertEquals(command1, command1Copy);

        // different -> false
        assert !command1.equals(command2);
    }

    @Test
    public void execute_personNotFoundFiltered_throwsExtendedMessage() {
        // Add one person
        Customer newCustomerA = new CustomerBuilder()
                .withName("Alice")
                .withPhone("81234567")
                .withTier(Tier.BRONZE)
                .build();
        Customer newCustomerB = new CustomerBuilder()
                .withName("Blice")
                .withPhone("91234567")
                .withTier(Tier.BRONZE)
                .build();
        model.addPerson(newCustomerA);
        model.addPerson(newCustomerB);

        model.updateFilteredPersonList(person -> person.isSamePerson(newCustomerB));

        UpdatePointsCommand cmd = new UpdatePointsCommand(new Phone("81234567"), 100);

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));

        assertEquals(
                String.format(UpdatePointsCommand.MESSAGE_PERSON_NOT_FOUND
                        + UpdatePointsCommand.ERROR_EXTENSION, new Phone("81234567")),
                ex.getMessage()
        );
    }

    @Test
    public void execute_customerAtMaxPoints_throwsCommandException() throws Exception {
        // Arrange
        Customer customer = new CustomerBuilder()
                .withName("Bob")
                .withPhone("81234567")
                .build();

        // Fill customer points to MAX_POINTS
        customer.addPointsFromSpending(Customer.MAX_POINTS);

        model.addPerson(customer);

        double extraSpending = 50.0; // Any positive number

        UpdatePointsCommand command = new UpdatePointsCommand(customer.getPhone(), extraSpending);

        // Act + Assert
        CommandException thrown = assertThrows(CommandException.class, () ->
                command.execute(model)
        );

        assertEquals(
                String.format(UpdatePointsCommand.MESSAGE_MAX_POINTS, customer.getName()),
                thrown.getMessage()
        );
    }
}

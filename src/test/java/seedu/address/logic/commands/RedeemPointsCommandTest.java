package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.RedeemPointsCommand.ERROR_EXTENSION;
import static seedu.address.logic.commands.RedeemPointsCommand.MESSAGE_NOT_ENOUGH_POINTS;
import static seedu.address.logic.commands.RedeemPointsCommand.MESSAGE_NOT_FOUND;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Phone;
import seedu.address.model.tier.Tier;
import seedu.address.testutil.CustomerBuilder;

public class RedeemPointsCommandTest {

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

        RedeemPointsCommand command = new RedeemPointsCommand(phone, newPoints);

        assertEquals(new CommandResult(String.format(MESSAGE_NOT_FOUND, phone.value)),
                command.execute(model));
    }

    @Test
    public void notEnoughPoints_failure() {
        Customer validCustomer = new CustomerBuilder().build();
        model.addPerson(validCustomer);
        Phone phone = validCustomer.getPhone();
        int requiredPoints = 1000;

        RedeemPointsCommand command = new RedeemPointsCommand(phone, requiredPoints);

        assertEquals(new CommandResult(String.format(MESSAGE_NOT_ENOUGH_POINTS,
                        validCustomer.getName().fullName, requiredPoints, validCustomer.getPoints())),
                command.execute(model));
    }

    @Test
    public void personNotFoundFiltered_failure() {
        Customer firstCustomer = new CustomerBuilder()
                .withName("Alice")
                .withPhone("81234567")
                .withTier(Tier.BRONZE)
                .build();
        Customer secondCustomer = new CustomerBuilder()
                .withName("Blice")
                .withPhone("91234567")
                .withTier(Tier.BRONZE)
                .build();
        model.addPerson(firstCustomer);
        model.addPerson(secondCustomer);

        model.updateFilteredPersonList(person -> person.isSamePerson(secondCustomer));

        RedeemPointsCommand command = new RedeemPointsCommand(firstCustomer.getPhone(), 100);
        assertEquals(new CommandResult(String.format(MESSAGE_NOT_FOUND + ERROR_EXTENSION, firstCustomer.getPhone())),
                command.execute(model));
    }

}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Shift;
import seedu.address.model.person.Staff;
import seedu.address.testutil.StaffBuilder;

class UpdateShiftCommandTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new ModelManager();
    }

    @Test
    void execute_success() throws Exception {
        // Create staff and add to model
        Staff staff = new StaffBuilder().withName("Alex Tan")
                .withPhone("81234567")
                .withShift("AM")
                .build();
        model.addPerson(staff);

        Phone phone = new Phone("81234567");
        Shift newShift = new Shift("PM");

        UpdateShiftCommand command = new UpdateShiftCommand(phone, newShift);
        CommandResult result = command.execute(model);

        // Check feedback
        assertEquals(String.format(UpdateShiftCommand.MESSAGE_SUCCESS, staff.getName(), newShift.getValue()),
                result.getFeedbackToUser());

        // Check shift updated
        Staff updatedStaff = (Staff) model.getFilteredPersonList().get(0);
        assertEquals("PM", updatedStaff.getShift().getValue());
    }

    @Test
    void execute_personNotStaff_throwsCommandException() {
        // Add a non-staff person
        Person person = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getAddress(), ALICE.getCategory());
        model.addPerson(person);

        UpdateShiftCommand command = new UpdateShiftCommand(person.getPhone(), new Shift("PM"));

        Exception exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(UpdateShiftCommand.MESSAGE_NOT_A_STAFF, person.getPhone()), exception.getMessage());
    }

    @Test
    void execute_personNotFound_throwsCommandException() {
        Phone phone = new Phone("89998888");
        Shift shift = new Shift("PM");

        Person person = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getAddress(), ALICE.getCategory());
        model.addPerson(person);

        UpdateShiftCommand command = new UpdateShiftCommand(phone, shift);

        Exception exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(UpdateShiftCommand.MESSAGE_PERSON_NOT_FOUND, phone), exception.getMessage());
    }

    @Test
    void execute_emptyList_throwsCommandException() {
        // Model is empty
        UpdateShiftCommand command = new UpdateShiftCommand(new Phone("81234567"), new Shift("PM"));

        Exception exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(UpdateShiftCommand.MESSAGE_EMPTY_LIST, exception.getMessage());
    }

    @Test
    void equals_sameAndDifferent() {
        Phone phone1 = new Phone("81234567");
        Shift shift1 = new Shift("AM");
        UpdateShiftCommand command1 = new UpdateShiftCommand(phone1, shift1);

        Phone phone2 = new Phone("89998888");
        Shift shift2 = new Shift("PM");
        UpdateShiftCommand command2 = new UpdateShiftCommand(phone2, shift2);

        // same object -> true
        assertEquals(command1, command1);

        // same values -> true
        UpdateShiftCommand command1Copy = new UpdateShiftCommand(phone1, shift1);
        assertEquals(command1, command1Copy);

        // different -> false
        assert !command1.equals(command2);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Shift;
import seedu.address.model.person.Staff;

/**
 * Updates the shift of a staff member identified by their phone number.
 */
public class UpdateShiftCommand extends Command {

    public static final String COMMAND_WORD = "updateShift";
    public static final String COMMAND_LOWER = "updateshift";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the shift of a staff member identified by their phone number.\n"
            + "Parameters: "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_SHIFT + "SHIFT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_SHIFT + "PM";

    public static final String MESSAGE_SUCCESS = "Updated shift for %1$s to %2$s.";
    public static final String MESSAGE_NOT_A_STAFF = "The person with phone number %1$s is not a staff member.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No person found with phone number %1$s.";
    public static final String ERROR_EXTENSION = " Try running 'list' before using the command again.";
    public static final String MESSAGE_EMPTY_LIST = "Empty contact list: No contacts available to update!";

    private final Phone phone;
    private final Shift newShift;

    /**
     * Creates an UpdateShiftCommand to update the shift of the specified staff.
     *
     * @param phone    Phone number identifying the staff.
     * @param newShift The new shift value.
     */
    public UpdateShiftCommand(Phone phone, Shift newShift) {
        requireAllNonNull(phone, newShift);

        this.phone = phone;
        this.newShift = newShift;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> personList = model.getFilteredPersonList();

        if (personList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        //  Find person by phone number
        Optional<Person> matchedPerson = personList.stream()
                .filter(p -> p.getPhone().equals(phone))
                .findFirst();

        ObservableList<Person> fullList = model.getAddressBook().getPersonList();
        if (matchedPerson.isEmpty() && fullList.size() != personList.size()) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND + ERROR_EXTENSION, phone));
        }
        if (matchedPerson.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, phone));
        }

        Person personToUpdate = matchedPerson.get();

        //  Check if person is a Staff
        if (!(personToUpdate instanceof Staff)) {
            throw new CommandException(String.format(MESSAGE_NOT_A_STAFF, phone));
        }

        Staff staffToUpdate = (Staff) personToUpdate;
        staffToUpdate.setShift(newShift);

        model.setPerson(personToUpdate, staffToUpdate);
        //refresh the ui to show updated change
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                staffToUpdate.getName(), newShift.getValue()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateShiftCommand)) {
            return false;
        }

        UpdateShiftCommand otherCommand = (UpdateShiftCommand) other;
        return phone.equals(otherCommand.phone)
                && newShift.equals(otherCommand.newShift);
    }


    @Override
    public String toString() {
        return String.format("UpdateShiftCommand{phone=%s, newShift=%s}", phone, newShift);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BILL_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Updates the spending amount of a customer identified by their phone number.
 */
public class UpdatePointsCommand extends Command {

    public static final String COMMAND_WORD = "updatePoints";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Award points to the customer based on the specified amount spent.\n"
            + "Parameters: "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_BILL_AMOUNT + "BILL AMOUNT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_BILL_AMOUNT + "50.0";

    public static final String MESSAGE_SUCCESS = "Added %2$d points for %1$s.";
    public static final String MESSAGE_NOT_A_CUSTOMER = "The person with phone number %1$s is not a customer.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No person found with phone number %1$s.";
    public static final String ERROR_EXTENSION = " Try running 'list' before using the command again.";
    public static final String MESSAGE_EMPTY_LIST = "Empty contact list: No contacts available to update!";

    private final Phone phone;
    private final double amount;

    /**
     * Creates an UpdatePointsCommand to add spending to the specified customer.
     *
     * @param phone  Phone number identifying the customer.
     * @param billAmount Amount spent to add.
     */
    public UpdatePointsCommand(Phone phone, double billAmount) {
        requireAllNonNull(phone);
        this.phone = phone;
        this.amount = billAmount;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> personList = model.getFilteredPersonList();

        if (personList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        // Find person by phone number
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

        // Ensure person is a Customer
        if (!(personToUpdate instanceof Customer)) {
            throw new CommandException(String.format(MESSAGE_NOT_A_CUSTOMER, phone));
        }

        // Update amount spent
        Customer customerToUpdate = (Customer) personToUpdate;
        customerToUpdate.addPointsFromSpending(amount);
        int pointsAdded = customerToUpdate.calculatePointsFromSpending(amount);

        // Replace the old person with updated customer in the model
        model.setPerson(personToUpdate, customerToUpdate);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                customerToUpdate.getName(), pointsAdded));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdatePointsCommand)) {
            return false;
        }

        UpdatePointsCommand otherCommand = (UpdatePointsCommand) other;
        return phone.equals(otherCommand.phone)
                && Double.compare(amount, otherCommand.amount) == 0;
    }

    @Override
    public String toString() {
        return String.format("UpdatePointsCommand{phone=%s, amount=%.2f}", phone, amount);
    }
}

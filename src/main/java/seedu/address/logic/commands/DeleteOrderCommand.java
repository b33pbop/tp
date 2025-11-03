package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Supplier;

/**
 * Deletes an order from a supplier's list of orders.
 */
public class DeleteOrderCommand extends Command {
    public static final String COMMAND_WORD = "deleteOrder";
    public static final String COMMAND_LOWER = "deleteorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an order from the supplier's order list.\n"
        + "Parameters: "
        + PREFIX_PHONE + "PHONE "
        + seedu.address.logic.parser.CliSyntax.PREFIX_ORDERNUM + "ORDER_INDEX\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_PHONE + "91234567 "
        + seedu.address.logic.parser.CliSyntax.PREFIX_ORDERNUM + "1";

    public static final String MESSAGE_SUCCESS = "Order deleted successfully";
    public static final String MESSAGE_NOT_FOUND = "Entry with that phone number cannot be found.";
    public static final String ERROR_EXTENSION = " Try running 'list' before using the command again.";
    public static final String MESSAGE_NOT_SUPPLIER = "Person found is not a supplier, please try again";
    public static final String MESSAGE_INVALID_ORDER_INDEX = "Invalid order index for this supplier.";
    public static final String MESSAGE_EMPTY_ORDER_LIST = "Supplier currently has no orders.";

    private final Phone supplierPhone;
    private final int orderIndex;

    /**
     * Creates a DeleteOrderCommand to delete an order from a supplier's list of orders
     * @param supplierPhone The phone number of the specified supplier
     * @param orderIndex The index (1-based) of the order to delete
     */
    public DeleteOrderCommand(Phone supplierPhone, int orderIndex) {
        this.supplierPhone = supplierPhone;
        this.orderIndex = orderIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person foundPerson = null;
        ObservableList<Person> currentList = model.getFilteredPersonList();
        for (int i = 0; i < currentList.size(); i++) {
            if (currentList.get(i).getPhone().equals(this.supplierPhone)) {
                foundPerson = currentList.get(i);
                break;
            }
        }
        ObservableList<Person> fullList = model.getAddressBook().getPersonList();
        if (foundPerson == null && fullList.size() != currentList.size()) {
            throw new CommandException(MESSAGE_NOT_FOUND + ERROR_EXTENSION);
        }
        if (foundPerson == null) {
            throw new CommandException(MESSAGE_NOT_FOUND);
        }
        if (!foundPerson.getCategory().toString().equals("[Supplier]")) {
            throw new CommandException(MESSAGE_NOT_SUPPLIER);
        }
        Supplier foundSupplier = (Supplier) foundPerson;
        if (foundSupplier.getSize() == 0) {
            throw new CommandException(MESSAGE_EMPTY_ORDER_LIST);
        }
        if (orderIndex < 1 || orderIndex > foundSupplier.getOrders().size()) {
            throw new CommandException(MESSAGE_INVALID_ORDER_INDEX);
        }
        foundSupplier.removeOrder(orderIndex);
        model.setPerson(foundPerson, foundSupplier);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteOrderCommand)) {
            return false;
        }
        DeleteOrderCommand otherCommand = (DeleteOrderCommand) other;
        return supplierPhone.equals(otherCommand.supplierPhone) && orderIndex == otherCommand.orderIndex;
    }
}

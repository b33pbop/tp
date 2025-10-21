package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERYDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITPRICE;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.Supplier;

public class AddOrderCommand extends Command{

    public static final String COMMAND_WORD = "order";

    private final int supplierPhone;
    private final String newOrderItem;
    private final int newOrderQuantity;
    private final double newOrderUnitPrice;
    private final String newOrderDeliveryDate;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a order to the supplier's order list. "
            + "Parameters: "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_ITEM + "ITEM "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_UNITPRICE + "UNITPRICE"
            + PREFIX_DELIVERYDAY + "DELIVERYDAY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_ITEM + "Markers "
            + PREFIX_QUANTITY + "50 "
            + PREFIX_UNITPRICE + "$0.60 "
            + PREFIX_DELIVERYDAY + "every Thursday\n";

    public static final String MESSAGE_SUCCESS = "Order added successfully";
    public static final String MESSAGE_NOT_FOUND = "Entry with that phone number cannot be found.";
    public static final String MESSAGE_NOT_SUPPLIER = "Person found is not a supplier, please try again";


    public AddOrderCommand(int supplierPhone, String newOrderItem,
                           int newOrderQuantity, double newOrderUnitPrice,
                           String newOrderDeliveryDate) {
        this.supplierPhone = supplierPhone;
        this.newOrderItem = newOrderItem;
        this.newOrderQuantity = newOrderQuantity;
        this.newOrderUnitPrice = newOrderUnitPrice;
        this.newOrderDeliveryDate = newOrderDeliveryDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        // finds person tagged to phone number
        Person foundPerson = null;
        requireNonNull(model);
        ObservableList<Person> currentList = model.getAddressBook().getPersonList();
        for (int i = 0; i < currentList.size(); i++) {
            if (Integer.parseInt(currentList.get(i).getPhone().toString()) == this.supplierPhone) {
                foundPerson = currentList.get(i);
                break;
            }
        }

        // checks if it's a supplier and if it is a supplier
        if (foundPerson == null) {
            throw new CommandException(MESSAGE_NOT_FOUND);
        }
        if (!foundPerson.getCategory().toString().equals("[Supplier]")){
            throw new CommandException(MESSAGE_NOT_SUPPLIER);
        }
        // creates the new order (no parameters can be missing)

        Order newOrder = new Order(this.newOrderItem,
                                    this.newOrderQuantity,
                                    this.newOrderUnitPrice,
                                    this.newOrderDeliveryDate);

        // adds it to the back of the list

        Supplier foundSupplier = (Supplier) foundPerson;
        foundSupplier.addOrder(newOrder);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddOrderCommand)) {
            return false;
        }

        AddOrderCommand otherAddOrderCommand = (AddOrderCommand) other;
        return (supplierPhone == otherAddOrderCommand.supplierPhone)
                && (newOrderItem.equals(otherAddOrderCommand.newOrderItem))
                && (newOrderQuantity == otherAddOrderCommand.newOrderQuantity)
                && (newOrderUnitPrice == otherAddOrderCommand.newOrderUnitPrice)
                && (newOrderDeliveryDate.equals(otherAddOrderCommand.newOrderDeliveryDate));
    }
}

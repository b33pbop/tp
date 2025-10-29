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
import seedu.address.model.person.ItemDeliveryDay;
import seedu.address.model.person.ItemName;
import seedu.address.model.person.ItemQuantity;
import seedu.address.model.person.ItemUnitPrice;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Supplier;


/**
 * Adds an order to the specified supplier's list of orders.
 */

public class AddOrderCommand extends Command {
    // Static variables
    public static final String COMMAND_WORD = "addOrder";
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
    public static final String MESSAGE_DUPLICATE_ORDER = "Order already exists in the list, please try again";

    // Instance variables
    private final Phone supplierPhone;
    private final ItemName newOrderItem;
    private final ItemQuantity newOrderQuantity;
    private final ItemUnitPrice newOrderUnitPrice;
    private final ItemDeliveryDay newOrderDeliveryDay;

    // Constructor
    /**
     * Creates an AddOrderCommand to add the order to a supplier's list of orders
     * @param supplierPhone The phone number of the specified supplier
     * @param newOrderItem The name of the item in the new order
     * @param newOrderQuantity The quantity of the new item in the new order
     * @param newOrderUnitPrice The unit price of the new item in the new order
     * @param newOrderDeliveryDay The estimated day of delivery of the new order
     */
    public AddOrderCommand(Phone supplierPhone, ItemName newOrderItem,
            ItemQuantity newOrderQuantity, ItemUnitPrice newOrderUnitPrice,
            ItemDeliveryDay newOrderDeliveryDay) {
        this.supplierPhone = supplierPhone;
        this.newOrderItem = newOrderItem;
        this.newOrderQuantity = newOrderQuantity;
        this.newOrderUnitPrice = newOrderUnitPrice;
        this.newOrderDeliveryDay = newOrderDeliveryDay;
    }

    // Public getter for unit price
    public ItemUnitPrice getOrderUnitPrice() {
        return newOrderUnitPrice;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // finds person tagged to phone number
        Person foundPerson = null;
        requireNonNull(model);
        ObservableList<Person> currentList = model.getFilteredPersonList();
        for (int i = 0; i < currentList.size(); i++) {
            if (currentList.get(i).getPhone().equals(this.supplierPhone)) {
                foundPerson = currentList.get(i);
                break;
            }
        }

        // checks if it's a supplier and if it is a supplier
        if (foundPerson == null) {
            throw new CommandException(MESSAGE_NOT_FOUND);
        }
        if (!foundPerson.getCategory().toString().equals("[Supplier]")) {
            throw new CommandException(MESSAGE_NOT_SUPPLIER);
        }
        // creates the new order (no parameters can be missing)
        Order newOrder = new Order(this.newOrderItem,
                this.newOrderQuantity,
                this.newOrderUnitPrice,
                this.newOrderDeliveryDay);


        // adds it to the back of the list
        Supplier foundSupplier = (Supplier) foundPerson;
        if (foundSupplier.hasOrder(newOrder)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }
        foundSupplier.addOrder(newOrder);

        // Persist the updated supplier in the model/address book
        // Remove the old supplier and add the updated one
        model.setPerson(foundPerson, foundSupplier);

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
        return (supplierPhone.equals(otherAddOrderCommand.supplierPhone))
                && (newOrderItem.equals(otherAddOrderCommand.newOrderItem))
                && (newOrderQuantity.equals(otherAddOrderCommand.newOrderQuantity))
                && (newOrderUnitPrice.equals(otherAddOrderCommand.newOrderUnitPrice))
                && (newOrderDeliveryDay.equals(otherAddOrderCommand.newOrderDeliveryDay));
    }
}

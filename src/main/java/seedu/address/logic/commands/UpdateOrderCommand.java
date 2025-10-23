package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERYDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDERNUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITPRICE;

import java.util.Objects;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.Supplier;

/**
 * Updates an order in a specified supplier's list of orders
 */
public class UpdateOrderCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_UPDATE_SUCCESS = "Order has been updated successfully";
    public static final String MESSAGE_NO_CHANGE = "Values given are exactly the same, no update made.";
    public static final String MESSAGE_NOT_FOUND = "Entry with that phone number cannot be found.";
    public static final String MESSAGE_NOT_SUPPLIER = "Person found is not a supplier, please try again";
    public static final String MESSAGE_OUT_OF_BOUNDS = "Index given is out of bounds of supplier's list of orders.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the order identified "
            + "by the index number used in the supplier's order list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_ORDERNUM + "ORDER NUMBER] "
            + "[" + PREFIX_ITEM + "ITEM] "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_UNITPRICE + "UNIT PRICE] "
            + "[" + PREFIX_DELIVERYDAY + "DELIVERY DAY]...\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_PHONE + "91234567 "
            + PREFIX_ORDERNUM + "1"
            + PREFIX_ITEM + "Pens"
            + PREFIX_QUANTITY + "100"
            + PREFIX_UNITPRICE + "$0.90"
            + PREFIX_DELIVERYDAY + "every Tuesday";

    public final int supplierPhone;
    public final int orderIndex;
    public final UpdateOrderDescriptor descriptor;


    /**
     * @param orderIndex of the order in the supplier's list to edit
     */
    public UpdateOrderCommand(int supplierPhone, int orderIndex, UpdateOrderDescriptor descriptor) {
        requireNonNull(descriptor);

        this.supplierPhone = supplierPhone;
        this.orderIndex = orderIndex;
        this.descriptor = descriptor;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person person = null;
        ObservableList<Person> currentList = model.getAddressBook().getPersonList();
        for (int i = 0; i < currentList.size(); i++) {
            if (Integer.parseInt(currentList.get(i).getPhone().toString()) == this.supplierPhone) {
                person = currentList.get(i);
                break;
            }
        }

        if (person == null) {
            throw new CommandException(MESSAGE_NOT_FOUND);
        }
        if (!person.getCategory().toString().equals("[Supplier]")) {
            throw new CommandException(MESSAGE_NOT_SUPPLIER);
        }

        Supplier supplier = (Supplier) person;

        if (!(this.orderIndex > 0 && this.orderIndex <= supplier.getSize())) {
            throw new CommandException(MESSAGE_OUT_OF_BOUNDS);
        }

        Order newOrder = createEditedOrder(supplier.getOrder(this.orderIndex), this.descriptor);
        supplier.updateOrders(this.orderIndex, newOrder);

        model.setPerson(person, supplier);

        return new CommandResult(MESSAGE_UPDATE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateOrderCommand)) {
            return false;
        }

        UpdateOrderCommand otherUpdateOrderCommand = (UpdateOrderCommand) other;
        return this.supplierPhone == otherUpdateOrderCommand.supplierPhone
                && this.orderIndex == otherUpdateOrderCommand.orderIndex
                && this.descriptor.equals(otherUpdateOrderCommand.descriptor);
    }

    /**
     * Creates a new order based on the existing and new parameters
     * @param toEdit The pre-existing order to be edited
     * @param descriptor The descriptor that contains information on the parameter to be updated
     * @return A new order with the newly updated parameters
     */
    public static Order createEditedOrder(Order toEdit, UpdateOrderDescriptor descriptor) {
        assert toEdit != null;

        String updatedItem = descriptor.getItem().orElse(toEdit.getItem());
        int updatedQuantity = descriptor.getQuantity().orElse(toEdit.getQuantity());
        double updatedUnitPrice = descriptor.getUnitPrice().orElse(toEdit.getUnitPrice());
        String updatedDeliveryDay = descriptor.getDeliveryDay().orElse(toEdit.getDeliveryDay());

        return new Order(updatedItem, updatedQuantity, updatedUnitPrice, updatedDeliveryDay);
    }

    /**
     * Represents the information to be edited in an existing order
     */
    public static class UpdateOrderDescriptor {
        private String item;
        private Integer quantity;
        private Double unitPrice;
        private String deliveryDay;

        public UpdateOrderDescriptor() {}

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(item, quantity, unitPrice, deliveryDay);
        }

        public Optional<String> getItem() {
            return Optional.ofNullable(item);
        }

        public Optional<Integer> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public Optional<Double> getUnitPrice() {
            return Optional.ofNullable(unitPrice);
        }

        public Optional<String> getDeliveryDay() {
            return Optional.ofNullable(deliveryDay);
        }

        public void updateItem(String newItem) {
            this.item = newItem;
        }

        public void updateQuantity(Integer newQuantity) {
            this.quantity = newQuantity;
        }

        public void updateUnitPrice(Double newUnitPrice) {
            this.unitPrice = newUnitPrice;
        }

        public void updateDeliveryDay(String newDeliveryDay) {
            this.deliveryDay = newDeliveryDay;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateOrderDescriptor)) {
                return false;
            }

            UpdateOrderDescriptor otherUpdateOrderDescriptor = (UpdateOrderDescriptor) other;

            boolean isItemSame = Objects.equals(this.item, otherUpdateOrderDescriptor.item);
            boolean isQuantitySame = Objects.equals(this.quantity, otherUpdateOrderDescriptor.quantity);
            boolean isUnitPriceSame = Objects.equals(this.unitPrice, otherUpdateOrderDescriptor.unitPrice);
            boolean isDeliveryDaySame = Objects.equals(this.deliveryDay, otherUpdateOrderDescriptor.deliveryDay);



            return isItemSame && isQuantitySame && isUnitPriceSame && isDeliveryDaySame;
        }
    }
}

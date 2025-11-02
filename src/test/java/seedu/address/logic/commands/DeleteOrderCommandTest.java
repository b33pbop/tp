package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.DeleteOrderCommand.MESSAGE_INVALID_ORDER_INDEX;
import static seedu.address.logic.commands.DeleteOrderCommand.MESSAGE_NOT_FOUND;
import static seedu.address.logic.commands.DeleteOrderCommand.MESSAGE_NOT_SUPPLIER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.category.Category;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.ItemDeliveryDay;
import seedu.address.model.person.ItemName;
import seedu.address.model.person.ItemQuantity;
import seedu.address.model.person.ItemUnitPrice;
import seedu.address.model.person.Name;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Supplier;

public class DeleteOrderCommandTest {
    @Test
    public void execute_supplierNotFound_throwsCommandException() {
        Model model = new ModelManager();
        DeleteOrderCommand cmd = new DeleteOrderCommand(new Phone("99999999"), 1);
        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(MESSAGE_NOT_FOUND, ex.getMessage());
    }

    @Test
    public void execute_personNotSupplier_throwsCommandException() {
        Model model = new ModelManager();
        Person person = new Person(
            new seedu.address.model.person.Name("Alice"),
            new seedu.address.model.person.Phone("91234567"),
            new seedu.address.model.person.Email("alice@email.com"),
            new seedu.address.model.person.Address("address"),
            new seedu.address.model.category.Category("Staff")
        );
        model.addPerson(person);
        DeleteOrderCommand cmd = new DeleteOrderCommand(new Phone("91234567"), 1);
        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(MESSAGE_NOT_SUPPLIER, ex.getMessage());
    }

    @Test
    public void execute_invalidOrderIndex_throwsCommandException() {
        Model model = new ModelManager();
        Supplier supplier = new Supplier(
            new seedu.address.model.person.Name("Bob"),
            new seedu.address.model.person.Phone("91234567"),
            new seedu.address.model.person.Email("bob@email.com"),
            new seedu.address.model.person.Address("address"),
            new seedu.address.model.category.Category("Supplier")
        );
        supplier.getOrders().add(new Order(new ItemName("Pens"), new ItemQuantity("100"),
                new ItemUnitPrice("0.90"), new ItemDeliveryDay("Tuesday")));
        model.addPerson(supplier);
        DeleteOrderCommand cmd = new DeleteOrderCommand(new Phone("91234567"), 2); // Only 1 order
        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(MESSAGE_INVALID_ORDER_INDEX, ex.getMessage());
    }

    @Test
    public void equals_variousCases() {
        DeleteOrderCommand cmd1 = new DeleteOrderCommand(new Phone("91234567"), 1);
        DeleteOrderCommand cmd2 = new DeleteOrderCommand(new Phone("91234567"), 1);
        DeleteOrderCommand cmd3 = new DeleteOrderCommand(new Phone("81234567"), 1);
        assertTrue(cmd1.equals(cmd1)); // same object
        assertTrue(cmd1.equals(cmd2)); // same values
        assertFalse(cmd1.equals(cmd3)); // different phone
        assertFalse(cmd1.equals("not a command")); // different type
    }

    @Test
    public void execute_supplierNotFoundFiltered_throwsExtendedMessage() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Supplier s = new Supplier(new Name("Bob"), new Phone("91234567"),
                new Email("a@a.com"), new Address("Addr"), new Category("Supplier"));
        model.addPerson(s);

        model.updateFilteredPersonList(p -> false);

        DeleteOrderCommand cmd = new DeleteOrderCommand(new Phone("99999999"), 1);

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));

        assertEquals(MESSAGE_NOT_FOUND + DeleteOrderCommand.ERROR_EXTENSION, ex.getMessage());
    }

    @Test
    public void execute_emptyOrderList_throwsEmptyOrderListMessage() {
        Model model = new ModelManager();
        Supplier s = new Supplier(new Name("Bob"), new Phone("91234567"),
                new Email("a@a.com"), new Address("Addr"), new Category("Supplier"));
        model.addPerson(s);

        DeleteOrderCommand cmd = new DeleteOrderCommand(new Phone("91234567"), 1);

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));

        assertEquals(DeleteOrderCommand.MESSAGE_EMPTY_ORDER_LIST, ex.getMessage());
    }
}

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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ItemName;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.Supplier;

public class DeleteOrderCommandTest {
    @Test
    public void execute_supplierNotFound_throwsCommandException() {
        Model model = new ModelManager();
        DeleteOrderCommand cmd = new DeleteOrderCommand(99999999, 1);
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
        DeleteOrderCommand cmd = new DeleteOrderCommand(91234567, 1);
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
        supplier.getOrders().add(new Order(new ItemName("Pens"), 100, 0.90, "Tuesday"));
        model.addPerson(supplier);
        DeleteOrderCommand cmd = new DeleteOrderCommand(91234567, 2); // Only 1 order
        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(MESSAGE_INVALID_ORDER_INDEX, ex.getMessage());
    }

    @Test
    public void equals_variousCases() {
        DeleteOrderCommand cmd1 = new DeleteOrderCommand(91234567, 1);
        DeleteOrderCommand cmd2 = new DeleteOrderCommand(91234567, 1);
        DeleteOrderCommand cmd3 = new DeleteOrderCommand(81234567, 1);
        assertTrue(cmd1.equals(cmd1)); // same object
        assertTrue(cmd1.equals(cmd2)); // same values
        assertFalse(cmd1.equals(cmd3)); // different phone
        assertFalse(cmd1.equals("not a command")); // different type
    }
}

package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateOrderCommand.UpdateOrderDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.Supplier;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.SupplierBuilder;


public class UpdateOrderCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());
    private Supplier supplier = new SupplierBuilder().withCategory("Supplier").build();

    @Test
    public void updateOrderWithNoChanges() throws CommandException {
        Order baseOrder = new Order("Pencils",
                                    40,
                                    0.5,
                                    "every Friday");

        UpdateOrderDescriptor emptyDescriptor = new UpdateOrderDescriptor();
        supplier.addOrder(baseOrder);
        AddCommand addCommand = new AddCommand(supplier);
        addCommand.execute(model);

        int supplierPhone = Integer.parseInt(supplier.getPhone().value);
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand(supplierPhone, 1, emptyDescriptor);

        String expectedMessage = UpdateOrderCommand.MESSAGE_UPDATE_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), supplier);

        assertCommandSuccess(updateOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void updateOrderWithSomeChanges() throws CommandException {
        Order baseOrder = new Order("Pencils",
                40,
                0.5,
                "every Friday");
        supplier.addOrder(baseOrder);
        AddCommand addCommand = new AddCommand(supplier);
        addCommand.execute(model);

        Order finalOrder = new Order("Changed item",
                100,
                0.5,
                "every Friday");
        Supplier finalSupplier =new SupplierBuilder().withCategory("Supplier").build();
        finalSupplier.addOrder(finalOrder);

        UpdateOrderDescriptor changedSome= new UpdateOrderDescriptor();
        changedSome.updateItem("Changed item");
        changedSome.updateQuantity(100);

        int supplierPhone = Integer.parseInt(supplier.getPhone().value);
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand(supplierPhone, 1, changedSome);

        String expectedMessage = UpdateOrderCommand.MESSAGE_UPDATE_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), finalSupplier);

        assertCommandSuccess(updateOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void updateOrderWithAllChanges() throws CommandException {
        Order baseOrder = new Order("Pencils",
                40,
                0.5,
                "every Friday");
        supplier.addOrder(baseOrder);
        AddCommand addCommand = new AddCommand(supplier);
        addCommand.execute(model);

        Order finalOrder = new Order("Changed item",
                100,
                5.5,
                "Changed Day");
        Supplier finalSupplier =new SupplierBuilder().withCategory("Supplier").build();
        finalSupplier.addOrder(finalOrder);

        UpdateOrderDescriptor changedAll = new UpdateOrderDescriptor();
        changedAll.updateItem("Changed item");
        changedAll.updateQuantity(100);
        changedAll.updateUnitPrice(5.5);
        changedAll.updateDeliveryDay("Changed Day");

        int supplierPhone = Integer.parseInt(supplier.getPhone().value);
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand(supplierPhone, 1, changedAll);

        String expectedMessage = UpdateOrderCommand.MESSAGE_UPDATE_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), finalSupplier);

        assertCommandSuccess(updateOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void supplierNotFound_failure() {
        int supplierPhone = Integer.parseInt(supplier.getPhone().value);
        UpdateOrderDescriptor emptyDescriptor = new UpdateOrderDescriptor();
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand(supplierPhone, 1, emptyDescriptor);
        assertCommandFailure(updateOrderCommand, model, UpdateOrderCommand.MESSAGE_NOT_FOUND);
    }

    @Test
    public void personNotSupplier_failure() throws CommandException {
        Person testPerson = new PersonBuilder().build();
        UpdateOrderDescriptor emptyDescriptor = new UpdateOrderDescriptor();

        AddCommand addCommand = new AddCommand(testPerson);
        addCommand.execute(model);

        int testPersonPhone = Integer.parseInt(testPerson.getPhone().value);
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand(testPersonPhone, 1, emptyDescriptor);
        assertCommandFailure(updateOrderCommand, model, UpdateOrderCommand.MESSAGE_NOT_SUPPLIER);

    }

    @Test
    public void orderIndexOutOfBounds() throws CommandException {
        Order baseOrder = new Order("Pencils",
                40,
                0.5,
                "every Friday");
        supplier.addOrder(baseOrder);
        AddCommand addCommand = new AddCommand(supplier);
        addCommand.execute(model);

        UpdateOrderDescriptor emptyDescriptor = new UpdateOrderDescriptor();
        int supplierPhone = Integer.parseInt(supplier.getPhone().value);
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand(supplierPhone, 6, emptyDescriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), supplier);

        assertCommandFailure(updateOrderCommand, model, UpdateOrderCommand.MESSAGE_OUT_OF_BOUNDS);

    }

}

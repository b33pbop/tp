package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateOrderCommand.UpdateOrderDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ItemDeliveryDay;
import seedu.address.model.person.ItemName;
import seedu.address.model.person.ItemQuantity;
import seedu.address.model.person.ItemUnitPrice;
import seedu.address.model.person.Order;
import seedu.address.model.person.OrderIndex;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Supplier;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.SupplierBuilder;


public class UpdateOrderCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());
    private Supplier supplier = new SupplierBuilder().withCategory("Supplier").build();

    @Test
    public void updateOrderWithNoChanges() throws CommandException {
        Order baseOrder = new Order(new ItemName("Pencils"),
                                    new ItemQuantity("40"),
                                    new ItemUnitPrice("0.5"),
                                    new ItemDeliveryDay("every Friday"));

        UpdateOrderDescriptor emptyDescriptor = new UpdateOrderDescriptor();
        supplier.addOrder(baseOrder);
        AddCommand addCommand = new AddCommand(supplier);
        addCommand.execute(model);

        Phone supplierPhone = supplier.getPhone();
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand(supplierPhone,
                                                                        new OrderIndex("1"),
                                                                        emptyDescriptor);

        String expectedMessage = UpdateOrderCommand.MESSAGE_DUPLICATE_ORDER;
        assertCommandFailure(updateOrderCommand, model, expectedMessage);
    }

    @Test
    public void updateOrderWithSomeChanges() throws CommandException {
        Order baseOrder = new Order(new ItemName("Pencils"),
                new ItemQuantity("40"),
                new ItemUnitPrice("0.5"),
                new ItemDeliveryDay("every Friday"));
        supplier.addOrder(baseOrder);
        AddCommand addCommand = new AddCommand(supplier);
        addCommand.execute(model);

        Order finalOrder = new Order(new ItemName("Changed item"),
                new ItemQuantity("100"),
                new ItemUnitPrice("0.5"),
                new ItemDeliveryDay("every Friday"));
        Supplier finalSupplier = new SupplierBuilder().withCategory("Supplier").build();
        finalSupplier.addOrder(finalOrder);

        UpdateOrderDescriptor changedSome = new UpdateOrderDescriptor();
        changedSome.updateItem(new ItemName("Changed item"));
        changedSome.updateQuantity(new ItemQuantity("100"));

        Phone supplierPhone = supplier.getPhone();
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand(supplierPhone, new OrderIndex("1"), changedSome);

        String expectedMessage = UpdateOrderCommand.MESSAGE_UPDATE_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), finalSupplier);

        assertCommandSuccess(updateOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void updateOrderWithAllChanges() throws CommandException {
        Order baseOrder = new Order(new ItemName("Pencils"),
                new ItemQuantity("40"),
                new ItemUnitPrice("0.5"),
                new ItemDeliveryDay("every Friday"));
        supplier.addOrder(baseOrder);
        AddCommand addCommand = new AddCommand(supplier);
        addCommand.execute(model);

        Order finalOrder = new Order(new ItemName("Changed item"),
                new ItemQuantity("100"),
                new ItemUnitPrice("5.5"),
                new ItemDeliveryDay("Changed Day"));
        Supplier finalSupplier = new SupplierBuilder().withCategory("Supplier").build();
        finalSupplier.addOrder(finalOrder);

        UpdateOrderDescriptor changedAll = new UpdateOrderDescriptor();
        changedAll.updateItem(new ItemName("Changed item"));
        changedAll.updateQuantity(new ItemQuantity("100"));
        changedAll.updateUnitPrice(new ItemUnitPrice("5.5"));
        changedAll.updateDeliveryDay(new ItemDeliveryDay("Changed Day"));

        Phone supplierPhone = supplier.getPhone();
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand(supplierPhone, new OrderIndex("1"), changedAll);

        String expectedMessage = UpdateOrderCommand.MESSAGE_UPDATE_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), finalSupplier);

        assertCommandSuccess(updateOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void emptyList_failure() {
        Phone supplierPhone = supplier.getPhone();
        UpdateOrderDescriptor emptyDescriptor = new UpdateOrderDescriptor();

        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand(supplierPhone,
                                                                        new OrderIndex("1"),
                                                                        emptyDescriptor);
        assertCommandFailure(updateOrderCommand, model, UpdateOrderCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void personNotSupplier_failure() throws CommandException {
        Person testPerson = new PersonBuilder().build();
        UpdateOrderDescriptor emptyDescriptor = new UpdateOrderDescriptor();

        AddCommand addCommand = new AddCommand(testPerson);
        addCommand.execute(model);

        Phone testPersonPhone = testPerson.getPhone();
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand(testPersonPhone, new OrderIndex("1"), emptyDescriptor);
        assertCommandFailure(updateOrderCommand, model,
                String.format(AddOrderCommand.MESSAGE_NOT_SUPPLIER, testPersonPhone));

    }

    @Test
    public void orderIndexOutOfBounds() throws CommandException {
        Order baseOrder = new Order(new ItemName("Pencils"),
                new ItemQuantity("40"),
                new ItemUnitPrice("0.5"),
                new ItemDeliveryDay("every Friday"));
        supplier.addOrder(baseOrder);
        AddCommand addCommand = new AddCommand(supplier);
        addCommand.execute(model);

        UpdateOrderDescriptor changeNameDescriptor = new UpdateOrderDescriptor();
        changeNameDescriptor.updateItem(new ItemName("Updated"));

        Phone supplierPhone = supplier.getPhone();
        UpdateOrderCommand wrongValue = new UpdateOrderCommand(supplierPhone,
                                                                new OrderIndex("6"),
                                                                changeNameDescriptor);
        UpdateOrderCommand correctValue = new UpdateOrderCommand(supplierPhone,
                                                                new OrderIndex("1"),
                                                                changeNameDescriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandFailure(wrongValue, model, UpdateOrderCommand.MESSAGE_OUT_OF_BOUNDS);
        assertCommandSuccess(correctValue, model, UpdateOrderCommand.MESSAGE_UPDATE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        UpdateOrderDescriptor baseDescriptor = new UpdateOrderDescriptor();
        baseDescriptor.updateItem(new ItemName("Item"));
        Phone supplierPhone = supplier.getPhone();

        UpdateOrderCommand toCompare = new UpdateOrderCommand(supplierPhone,
                                                                new OrderIndex("1"),
                                                                baseDescriptor);
        UpdateOrderCommand sameCompare = new UpdateOrderCommand(supplierPhone,
                                                                new OrderIndex("1"),
                                                                baseDescriptor);

        assertEquals(toCompare, toCompare);
        assertEquals(toCompare, sameCompare);

        // another type of command
        AddCommand addCommand = new AddCommand(supplier);
        assertNotEquals(toCompare, addCommand);
        assertNotEquals(null, toCompare);

        // different phone number
        Phone anotherPhone = new Phone("91111234");
        UpdateOrderCommand differentPhone = new UpdateOrderCommand(anotherPhone, new OrderIndex("1"), baseDescriptor);
        assertNotEquals(toCompare, differentPhone);

        // different descriptor
        UpdateOrderDescriptor updateDescriptor = new UpdateOrderDescriptor();
        updateDescriptor.updateItem(new ItemName("Different Item"));
        UpdateOrderCommand differentDescriptor = new UpdateOrderCommand(supplierPhone,
                                                                        new OrderIndex("1"),
                                                                        updateDescriptor);
        assertNotEquals(toCompare, differentDescriptor);
    }

    @Test
    void execute_personNotFound_throwsCommandException() {
        Phone phone = new Phone("89998888");
        UpdateOrderDescriptor baseDescriptor = new UpdateOrderDescriptor();

        Person person = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getAddress(), ALICE.getCategory());
        model.addPerson(person);

        UpdateOrderCommand command = new UpdateOrderCommand(phone, new OrderIndex("1"), baseDescriptor);

        Exception exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(UpdateOrderCommand.MESSAGE_NOT_FOUND, phone), exception.getMessage());
    }

}

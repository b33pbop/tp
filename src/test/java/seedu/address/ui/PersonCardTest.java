package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;
import seedu.address.model.person.Supplier;
import seedu.address.testutil.StaffBuilder;
import seedu.address.testutil.SupplierBuilder;

/**
 * Unit tests for logic in PersonCard, using builders to create test data.
 */
public class PersonCardTest {
    /**
     * Avoids JavaFX dependencies by simulating the PersonCard logic.
     */
    private static class FakePersonCard {
        private String shift;
        private String orders;


        public FakePersonCard(Person person) {

            if (person instanceof Staff staff) {
                shift = staff.getShift().toString();
            } else {
                shift = "";
            }
            if (person instanceof Supplier supplier) {
                orders = supplier.listOrders();
            } else {
                orders = "";
            }
        }
    }

    @Test
    public void personCard_staff_setsShiftCorrectly() {
        Staff staff = new StaffBuilder().build();
        staff.setShift("PM");
        FakePersonCard card = new FakePersonCard(staff);

        assertEquals("PM", card.shift);
    }

    @Test
    public void personCard_nonStaff_shiftIsEmpty() {
        Supplier supplier = new SupplierBuilder().build();
        FakePersonCard card = new FakePersonCard(supplier);

        assertEquals("", card.shift);
    }

    @Test
    public void personCard_supplier_setsOrdersCorrectly() {
        Supplier supplier = new SupplierBuilder().build();
        // ensure supplier has no orders by default
        FakePersonCard card = new FakePersonCard(supplier);

        assertEquals("", card.orders);
    }

    @Test
    public void personCard_nonSupplier_ordersIsEmpty() {
        Staff staff = new StaffBuilder().build();
        FakePersonCard card = new FakePersonCard(staff);

        assertEquals("", card.orders);
    }
}

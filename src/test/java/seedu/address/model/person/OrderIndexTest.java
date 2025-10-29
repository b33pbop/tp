package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class OrderIndexTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OrderIndex(null));
    }

    @Test
    public void constructor_invalidOrderIndex_throwsIllegalArgumentException() {
        String emptyOrderIndex = "";
        assertThrows(IllegalArgumentException.class, () -> new ItemUnitPrice(emptyOrderIndex));

        String invalidOrderIndex = "0.11111";
        assertThrows(IllegalArgumentException.class, () -> new ItemUnitPrice(invalidOrderIndex));

        String invalidOrderIndexWithLetters = "011AA";
        assertThrows(IllegalArgumentException.class, () -> new ItemUnitPrice(invalidOrderIndexWithLetters));
    }


    @Test
    public void isValidOrderIndex() {
        // null name
        assertThrows(NullPointerException.class, () -> OrderIndex.isValidOrderIndex(null));

        // invalid quantity
        assertFalse(OrderIndex.isValidOrderIndex("")); // empty string
        assertFalse(OrderIndex.isValidOrderIndex(" ")); // spaces only
        assertFalse(OrderIndex.isValidOrderIndex("^")); // only non-alphanumeric characters
        assertFalse(OrderIndex.isValidOrderIndex("peter*")); // contains non-alphanumeric characters
        assertFalse(OrderIndex.isValidOrderIndex("0.99999")); // no decimals allowed

        // valid quantity
        assertTrue(OrderIndex.isValidOrderIndex("1")); // numbers only
    }

    @Test
    public void equals() {
        OrderIndex orderIndex = new OrderIndex("9");

        // same values -> returns true
        assertTrue(orderIndex.equals(new OrderIndex("9")));

        // same object -> returns true
        assertTrue(orderIndex.equals(orderIndex));

        // null -> returns false
        assertFalse(orderIndex.equals(null));

        // different types -> returns false
        assertFalse(orderIndex.equals("Testing"));

        // different values -> returns false
        assertFalse(orderIndex.equals(new ItemQuantity("900")));
    }
}

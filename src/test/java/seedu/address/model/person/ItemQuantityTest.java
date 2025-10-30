package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ItemQuantityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ItemQuantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String emptyQuantity = "";
        assertThrows(IllegalArgumentException.class, () -> new ItemUnitPrice(emptyQuantity));

        String invalidQuantity = "0.11111";
        assertThrows(IllegalArgumentException.class, () -> new ItemUnitPrice(invalidQuantity));

        String invalidQuantityWithLetters = "011AA";
        assertThrows(IllegalArgumentException.class, () -> new ItemUnitPrice(invalidQuantityWithLetters));
    }


    @Test
    public void isValidQuantity() {
        // null name
        assertThrows(NullPointerException.class, () -> ItemQuantity.isValidItemQuantity(null));

        // invalid quantity
        assertFalse(ItemQuantity.isValidItemQuantity("")); // empty string
        assertFalse(ItemQuantity.isValidItemQuantity(" ")); // spaces only
        assertFalse(ItemQuantity.isValidItemQuantity("^")); // only non-alphanumeric characters
        assertFalse(ItemQuantity.isValidItemQuantity("peter*")); // contains non-alphanumeric characters
        assertFalse(ItemQuantity.isValidItemQuantity("0.99999")); // no decimals allowed

        // valid quantity
        assertTrue(ItemQuantity.isValidItemQuantity("12345")); // numbers only
    }

    @Test
    public void equals() {
        ItemQuantity itemQuantity = new ItemQuantity("9");

        // same values -> returns true
        assertTrue(itemQuantity.equals(new ItemQuantity("9")));

        // same object -> returns true
        assertTrue(itemQuantity.equals(itemQuantity));

        // null -> returns false
        assertFalse(itemQuantity.equals(null));

        // different types -> returns false
        assertFalse(itemQuantity.equals(11));

        // different values -> returns false
        assertFalse(itemQuantity.equals(new ItemQuantity("900")));
    }
}

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ItemUnitPriceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ItemUnitPrice(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String emptyUnitPrice = "";
        assertThrows(IllegalArgumentException.class, () -> new ItemUnitPrice(emptyUnitPrice));

        String invalidUnitPrice = "0.11111";
        assertThrows(IllegalArgumentException.class, () -> new ItemUnitPrice(invalidUnitPrice));
    }


    @Test
    public void isValidUnitPrice() {
        // null name
        assertThrows(NullPointerException.class, () -> ItemUnitPrice.isValidItemUnitPrice(null));

        // invalid name
        assertFalse(ItemUnitPrice.isValidItemUnitPrice("")); // empty string
        assertFalse(ItemUnitPrice.isValidItemUnitPrice(" ")); // spaces only
        assertFalse(ItemUnitPrice.isValidItemUnitPrice("^")); // only non-alphanumeric characters
        assertFalse(ItemUnitPrice.isValidItemUnitPrice("peter*")); // contains non-alphanumeric characters
        assertFalse(ItemUnitPrice.isValidItemUnitPrice("0.99999")); // too many decimal places

        // valid name
        assertTrue(ItemUnitPrice.isValidItemUnitPrice("12345")); // numbers only
        assertTrue(ItemUnitPrice.isValidItemUnitPrice("123.1")); // one decimal place
        assertTrue(ItemUnitPrice.isValidItemUnitPrice("0.99")); // two decimal places
    }

    @Test
    public void equals() {
        ItemUnitPrice itemUnitPrice = new ItemUnitPrice("0.99");

        // same values -> returns true
        assertTrue(itemUnitPrice.equals(new ItemUnitPrice("0.99")));

        // same object -> returns true
        assertTrue(itemUnitPrice.equals(itemUnitPrice));

        // null -> returns false
        assertFalse(itemUnitPrice.equals(null));

        // different types -> returns false
        assertFalse(itemUnitPrice.equals(11));

        // different values -> returns false
        assertFalse(itemUnitPrice.equals(new ItemUnitPrice("0.09")));
    }
}

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ItemDeliveryDayTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ItemDeliveryDay(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String emptyItemDeliveryDay = "";
        assertThrows(IllegalArgumentException.class, () -> new ItemDeliveryDay(emptyItemDeliveryDay));

        String invalidItemDeliveryDay = "N@me$";
        assertThrows(IllegalArgumentException.class, () -> new ItemDeliveryDay(invalidItemDeliveryDay));
    }

    @Test
    public void isValidDeliveryDay() {
        // null name
        assertThrows(NullPointerException.class, () -> ItemDeliveryDay.isValidItemDeliveryDay(null));

        // invalid delivery day
        assertFalse(ItemDeliveryDay.isValidItemDeliveryDay("")); // empty string
        assertFalse(ItemDeliveryDay.isValidItemDeliveryDay(" ")); // spaces only
        assertFalse(ItemDeliveryDay.isValidItemDeliveryDay("^")); // only non-alphanumeric characters
        assertFalse(ItemDeliveryDay.isValidItemDeliveryDay("peter*")); // contains non-alphanumeric characters

        // valid delivery day
        assertTrue(ItemDeliveryDay.isValidItemDeliveryDay("every monday")); // alphabets only
        assertTrue(ItemDeliveryDay.isValidItemDeliveryDay("1st of every month")); // alphanumeric characters
        assertTrue(ItemDeliveryDay.isValidItemDeliveryDay("Every Tuesday")); // with capital letters
    }

    @Test
    public void equals() {
        ItemDeliveryDay itemDeliveryDay = new ItemDeliveryDay("every Monday");

        // same values -> returns true
        assertTrue(itemDeliveryDay.equals(new ItemDeliveryDay("every Monday")));

        // same object -> returns true
        assertTrue(itemDeliveryDay.equals(itemDeliveryDay));

        // null -> returns false
        assertFalse(itemDeliveryDay.equals(null));

        // different types -> returns false
        assertFalse(itemDeliveryDay.equals(5.0f));

        // different values -> returns false
        assertFalse(itemDeliveryDay.equals(new ItemName("every Saturday")));
    }
}

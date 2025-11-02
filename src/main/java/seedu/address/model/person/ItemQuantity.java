package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the number of items for a specified order
 *  * Guarantees: immutable; is valid as declared in {@link #isValidItemQuantity(String)}
 */
public class ItemQuantity {
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid Item Quantity, only positive integers are allowed (No decimals)";
    public static final String VALIDATION_REGEX = "^[1-9]\\d*$";

    public final String itemQuantity;

    /**
     * Constructs a {@code ItemQuantity}.
     *
     * @param itemQuantity A Valid quantity value
     */
    @JsonCreator
    public ItemQuantity(@JsonProperty("itemQuantity") String itemQuantity) {
        requireNonNull(itemQuantity);
        checkArgument(isValidItemQuantity(itemQuantity.trim()), MESSAGE_CONSTRAINTS);
        this.itemQuantity = itemQuantity;
    }

    public static boolean isValidItemQuantity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return itemQuantity;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ItemQuantity)) {
            return false;
        }

        ItemQuantity otherItemQuantity = (ItemQuantity) other;
        return itemQuantity.equals(otherItemQuantity.itemQuantity);
    }
}

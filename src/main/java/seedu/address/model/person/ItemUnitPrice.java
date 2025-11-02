package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents the price of the items for a specific order.
 * Guarantees: immutable; is valid as declared in {@link #isValidItemUnitPrice(String)}
 */
public class ItemUnitPrice {

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid Unit Price, only positive numerical values with strictly 2 decimal places are allowed";
    public static final String VALIDATION_REGEX = "^\\d+(\\.\\d{2})?$";

    public final String itemUnitPrice;

    /**
     * Constructs a {@code ItemUnitPrice}.
     *
     * @param itemUnitPrice A valid unit price value.
     */
    @JsonCreator
    public ItemUnitPrice(@JsonProperty("itemUnitPrice") String itemUnitPrice) {
        requireNonNull(itemUnitPrice);
        checkArgument(isValidItemUnitPrice(itemUnitPrice.trim()), MESSAGE_CONSTRAINTS);
        this.itemUnitPrice = itemUnitPrice.contains(".") ? itemUnitPrice : itemUnitPrice + ".00";
    }

    public static boolean isValidItemUnitPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return itemUnitPrice;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ItemUnitPrice)) {
            return false;
        }

        ItemUnitPrice otherItemUnitPrice = (ItemUnitPrice) other;
        return itemUnitPrice.equals(otherItemUnitPrice.itemUnitPrice);
    }

}

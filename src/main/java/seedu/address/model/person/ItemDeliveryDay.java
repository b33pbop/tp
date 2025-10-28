package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an Order's day of delivery in the address book
 * Guarantees: is immutable; is valid as declared in {@link #isValidItemDeliveryDay(String)}
 */
public class ItemDeliveryDay {
    public static final String MESSAGE_CONSTRAINTS =
            "Delivery day should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the input must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String itemDeliveryDay;

    /**
     * Constructs a {@code itemDeliveryDay}.
     *
     * @param itemDeliveryDay A valid delivery day
     */
    @JsonCreator
    public ItemDeliveryDay(@JsonProperty("itemDeliveryDay") String itemDeliveryDay) {
        requireNonNull(itemDeliveryDay);
        checkArgument(isValidItemDeliveryDay(itemDeliveryDay), MESSAGE_CONSTRAINTS);
        this.itemDeliveryDay = itemDeliveryDay;
    }

    public static boolean isValidItemDeliveryDay(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.itemDeliveryDay;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ItemDeliveryDay)) {
            return false;
        }

        ItemDeliveryDay otherName = (ItemDeliveryDay) other;
        return this.itemDeliveryDay.equals(otherName.itemDeliveryDay);
    }
}

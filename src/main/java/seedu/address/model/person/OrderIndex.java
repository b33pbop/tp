package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the number of items for a specified order
 *  * Guarantees: immutable; is valid as declared in {@link #isValidOrderIndex(String)}
 */
public class OrderIndex {
    public static final String MESSAGE_CONSTRAINTS =
            "Only positive numerical values that are larger than zero are allowed.";
    public static final String VALIDATION_REGEX = "^[1-9]\\d*$";

    public final String orderIndex;

    /**
     * Constructs a {@code ItemQuantity}.
     *
     * @param orderIndex A valid order index number
     */
    @JsonCreator
    public OrderIndex(@JsonProperty("orderIndex") String orderIndex) {
        requireNonNull(orderIndex);
        checkArgument(isValidOrderIndex(orderIndex.trim()), MESSAGE_CONSTRAINTS);
        this.orderIndex = orderIndex;
    }

    public static boolean isValidOrderIndex(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return orderIndex;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderIndex)) {
            return false;
        }

        OrderIndex otherOrderIndex = (OrderIndex) other;
        return orderIndex.equals(otherOrderIndex.orderIndex);
    }
}

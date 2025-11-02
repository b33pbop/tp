package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the number of items for a specified order
 *  * Guarantees: immutable; is valid as declared in {@link #isValidOrderIndex(String)}
 */
public class OrderIndex {
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid Order Index, only positive integers lesser than 2,147,483,647 are allowed.";
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

    /**
     * Checks the input given for Order Index to confirm its validity
     * @param test The string input given by the user
     * @return A boolean value indicating whether it is a valid Order Index
     */
    public static boolean isValidOrderIndex(String test) {
        return test.matches(VALIDATION_REGEX)
                && new BigInteger(test).compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 0;
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

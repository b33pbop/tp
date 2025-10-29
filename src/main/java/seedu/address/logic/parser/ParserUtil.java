package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.ItemDeliveryDay;
import seedu.address.model.person.ItemName;
import seedu.address.model.person.ItemQuantity;
import seedu.address.model.person.ItemUnitPrice;
import seedu.address.model.person.Name;
import seedu.address.model.person.Order;
import seedu.address.model.person.OrderIndex;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Shift;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        // Remove additional whitespace between parts of the name
        String trimmedName = name.trim().replaceAll("\\s+", " ");
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static OrderIndex parseOrderIndex(String orderIndex) throws ParseException {
        requireNonNull(orderIndex);
        // Remove additional whitespace between parts of the name
        String trimmedOrderIndex = orderIndex.trim().replaceAll("\\s+", " ");
        if (!OrderIndex.isValidOrderIndex(trimmedOrderIndex)) {
            throw new ParseException(OrderIndex.MESSAGE_CONSTRAINTS);
        }
        return new OrderIndex(trimmedOrderIndex);
    }

    /**
     * Parses a {@code String itemName} into a {@code ItemName}
     * Leading and trailing white spaces will be trimmed.
     * @param itemName The user input for itemUnitPrice
     * @return An ItemName object
     * @throws ParseException If the given input is invalid
     */
    public static ItemName parseItemName(String itemName) throws ParseException {
        requireNonNull(itemName);
        // Remove additional whitespace between parts of the item name
        String trimmedItemName = itemName.trim().replaceAll("\\s+", " ");
        if (!Name.isValidName(trimmedItemName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new ItemName(trimmedItemName);
    }

    /**
     * Parses a {@code String itemName} into a {@code ItemName}
     * Leading and trailing white spaces will be trimmed.
     * @param itemQuantity The user input for itemUnitPrice
     * @return An ItemName object
     * @throws ParseException If the given input is invalid
     */
    public static ItemQuantity parseItemQuantity(String itemQuantity) throws ParseException {
        requireNonNull(itemQuantity);
        // Remove additional whitespace between parts of the item name
        String trimmedItemQuantity = itemQuantity.trim().replaceAll("\\s+", " ");
        if (!ItemQuantity.isValidItemQuantity(trimmedItemQuantity)) {
            throw new ParseException(ItemQuantity.MESSAGE_CONSTRAINTS);
        }
        return new ItemQuantity(trimmedItemQuantity);
    }

    /**
     * Parses a {@code String itemUnitPrice} into a {@code ItemUnitPrice}
     * Leading and trailing white spaces will be trimmed.
     * @param itemUnitPrice The user input for itemUnitPrice
     * @return An ItemUnitPrice object
     * @throws ParseException If the given input is invalid
     */
    public static ItemUnitPrice parseItemUnitPrice(String itemUnitPrice) throws ParseException {
        requireNonNull(itemUnitPrice);
        // Remove additional whitespace between parts of the item name
        String trimmedItemUnitPrice = itemUnitPrice.trim().replaceAll("\\s+", " ");
        if (!ItemUnitPrice.isValidItemUnitPrice(trimmedItemUnitPrice)) {
            throw new ParseException(ItemUnitPrice.MESSAGE_CONSTRAINTS);
        }
        return new ItemUnitPrice(trimmedItemUnitPrice);
    }

    /**
     * Parses a {@code String itemDeliveryDay} into a {@code ItemDeliveryDay}
     * Leading and trailing white spaces will be trimmed.
     * @param itemDeliveryDay The user input for itemUnitPrice
     * @return An ItemName object
     * @throws ParseException If the given input is invalid
     */
    public static ItemDeliveryDay parseItemDeliveryDay(String itemDeliveryDay) throws ParseException {
        requireNonNull(itemDeliveryDay);
        // Remove additional whitespace between parts of the item name
        String trimmedItemDeliveryDay = itemDeliveryDay.trim().replaceAll("\\s+", " ");
        if (!ItemDeliveryDay.isValidItemDeliveryDay(trimmedItemDeliveryDay)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new ItemDeliveryDay(trimmedItemDeliveryDay);
    }


    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim().replaceAll("\\s+", " ");
        if (!Address.isValidAddressCharacters(trimmedAddress)) {
            throw new ParseException(Address.INVALID_CHARACTER_MESSAGE_CONSTRAINTS);
        }

        if (!Address.isValidAddressLength(trimmedAddress)) {
            throw new ParseException(Address.INVALID_ADDRESS_LENGTH_MESSAGE_CONSTRAINTS);
        }

        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim().replaceAll("\\s+", " ");
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Category parseCategory(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Category.isValidCategoryName(trimmedTag)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedTag);
    }

    /**
     * Parses a {@code String s} into a {@code Shift}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code s} is invalid.
     */
    public static Shift parseShift(String s) throws ParseException {
        requireNonNull(s);
        String trimmedShift = s.trim();

        if (!Shift.isValidShift(trimmedShift)) {
            throw new ParseException(Shift.MESSAGE_CONSTRAINTS);
        }

        return new Shift(trimmedShift);
    }

}

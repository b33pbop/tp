package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.Category;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.ItemDeliveryDay;
import seedu.address.model.person.Name;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Shift;
import seedu.address.model.person.Staff;
import seedu.address.model.person.Supplier;
import seedu.address.model.tier.Tier;

/**
 * Jackson-friendly version of {@link Person}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class JsonAdaptedPerson {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @JsonProperty("name") private final String name;
    @JsonProperty("phone") private final String phone;
    @JsonProperty("email") private final String email;
    @JsonProperty("address") private final String address;
    @JsonProperty("category") private final String category;

    // only for Supplier class
    @JsonProperty("orders") private List<JsonAdaptedOrder> orders;

    // only for Staff class
    @JsonProperty("shift") private String shift;
    @JsonProperty("numberOfLeaves") private Integer numberOfLeaves;

    // only for Customer class
    @JsonProperty("points") private Integer points;
    @JsonProperty("tier") private Tier tier;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    public JsonAdaptedPerson(String name, String phone,
             String email, String address, JsonAdaptedCategory category) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;

        this.category = (category == null) ? null : category.getCategoryName();

        this.shift = null;
        this.numberOfLeaves = null;

        this.points = null;
        this.tier = null;
    }

    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty(value = "name") String name,
                             @JsonProperty(value = "phone") String phone,
                             @JsonProperty(value = "email") String email,
                             @JsonProperty(value = "address") String address,
                             @JsonProperty(value = "category") String category
                             ) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.category = category;

        this.orders = null;

        this.shift = null;
        this.numberOfLeaves = null;

        this.points = null;
        this.tier = null;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        Category sourceCategory = source.getCategory();
        category = sourceCategory.getCategoryName();

        if (source instanceof Supplier) {
            this.orders = new ArrayList<>();
            Supplier supplier = (Supplier) source;
            List<Order> orders = supplier.getOrders();
            for (Order order : orders) {
                ItemDeliveryDay deliveryDay = order.getDeliveryDay();
                JsonAdaptedOrder jsonOrder = new JsonAdaptedOrder(order.getItem(),
                        order.getQuantity(),
                        order.getUnitPrice(),
                        deliveryDay);
                this.orders.add(jsonOrder);
            }
        } else if (source instanceof Staff) {
            Staff staff = (Staff) source;
            this.shift = staff.getShift() == null ? null : staff.getShift().getValue();
            this.numberOfLeaves = staff.getNumberOfLeaves();
            assert numberOfLeaves >= 0 : "Staff leaves must not be negative";
        } else if (source instanceof Customer) {
            Customer customer = (Customer) source;
            this.points = customer.getPoints();
            this.tier = customer.getTier();
        } else {
            this.orders = null;

            this.shift = null;
            this.numberOfLeaves = null;

            this.points = null;
            this.tier = null;
        }
    }

    public List<JsonAdaptedOrder> getOrders() {
        return orders;
    }

    public int getPoints() {
        return (this.points == null) ? 0 : this.points;
    }

    public Tier getTier() {
        return (this.tier == null) ? Tier.MEMBER : this.tier;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddressCharacters(address)) {
            throw new IllegalValueException(Address.INVALID_CHARACTER_MESSAGE_CONSTRAINTS);
        }
        if (!Address.isValidAddressLength(address)) {
            throw new IllegalValueException(Address.INVALID_ADDRESS_LENGTH_MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Category.class.getSimpleName()));
        }
        if (!Category.isValidCategoryName(category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }

        final Category modelCategory = new Category(category);

        if ("Supplier".equalsIgnoreCase(category)) {
            return toSupplierModelType(modelCategory, modelName, modelPhone, modelEmail, modelAddress);
        }

        if ("Customer".equalsIgnoreCase(category)) {
            return toCustomerModelType(modelCategory, modelName, modelPhone, modelEmail, modelAddress);
        }

        if ("Staff".equalsIgnoreCase(category)) {
            return toStaffModelType(modelCategory, modelName, modelPhone, modelEmail, modelAddress);
        }

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelCategory);
    }

    /**
     * Converts this Jackson-friendly adapted person object into a {@code Supplier} object.
     */
    private Supplier toSupplierModelType(Category modelCategory,
                                       Name modelName,
                                       Phone modelPhone,
                                       Email modelEmail,
                                       Address modelAddress) {
        Supplier supplier = new Supplier(modelName, modelPhone, modelEmail, modelAddress, modelCategory);

        if (this.orders != null) {
            for (JsonAdaptedOrder jsonOrder : this.orders) {
                supplier.addOrder(jsonOrder.toModelType());
            }
        }

        return supplier;
    }

    /**
     * Converts this Jackson-friendly adapted person object into a {@code Customer} object.
     */
    private Customer toCustomerModelType(Category modelCategory,
                                         Name modelName,
                                         Phone modelPhone,
                                         Email modelEmail,
                                         Address modelAddress) {
        Customer customer = new Customer(modelName, modelPhone, modelEmail, modelAddress, modelCategory);

        int modelPoints = (this.points == null) ? 0 : this.points;
        customer.addPointsFromSpending(modelPoints);

        return customer;
    }

    /**
     * Converts this Jackson-friendly adapted person object into a {@code Staff} object.
     */
    private Staff toStaffModelType(Category modelCategory,
                                   Name modelName,
                                   Phone modelPhone,
                                   Email modelEmail,
                                   Address modelAddress) throws IllegalValueException {
        final String safeShift = (shift == null || shift.isBlank()) ? "AM" : shift;
        final Shift modelShift = new Shift(safeShift);

        final int leaves = (numberOfLeaves == null) ? 14 : numberOfLeaves;
        if (leaves < 0) {
            throw new IllegalValueException("numberOfLeaves cannot be negative");
        }

        Staff staff = new Staff(modelName, modelPhone, modelEmail, modelAddress, modelCategory, modelShift);
        int base = staff.getNumberOfLeaves(); // default is 14
        if (leaves > base) {
            staff.addLeaves(leaves - base);
        } else if (leaves < base) {
            staff.removeLeaves(base - leaves);
        }
        return staff;
    }
}

package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Shift;
import seedu.address.model.person.Staff;
import seedu.address.model.person.Supplier;
import seedu.address.model.tier.Tier;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label shift;
    @FXML
    private Label orders;
    @FXML
    private Label tier;
    @FXML
    private Label points;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        tags.getChildren().add(new Label(person.getCategory().categoryName));
        // Hide and clear category-specific fields by default
        shift.setVisible(false);
        shift.setManaged(false);
        shift.setText("");

        orders.setVisible(false);
        orders.setManaged(false);
        orders.setText("");

        tier.setVisible(false);
        tier.setManaged(false);
        tier.setText("");

        points.setVisible(false);
        tier.setManaged(false);
        tier.setText("");

        if (person instanceof Customer) {
            Customer customer = (Customer) person;
            Integer customerPoints = customer.getPoints();
            Tier customerTier = customer.getTier();

            if (points != null) {
                points.setText(customerPoints != null ? customerPoints.toString() : "0");
                points.setVisible(true);
                points.setManaged(true);
            }
            if (tier != null) {
                tier.setText(customerTier != null ? customerTier.toString() : "N/A");
                tier.setVisible(true);
                tier.setManaged(true);
            }
        }


        if (person instanceof Staff) {
            Staff staff = (Staff) person;
            Shift staffShift = staff.getShift();
            shift.setText(staffShift.toString());
            shift.setVisible(true);
            shift.setManaged(true);
        }
        if (person instanceof Supplier) {
            Supplier supplier = (Supplier) person;
            orders.setText(supplier.listOrders());
            orders.setVisible(true);
            orders.setManaged(true);
        }
    }
}

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
 * A UI component that displays information of a {@code Person}.
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
    private FlowPane category;
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
        initializeCommonFields(displayedIndex);
        displayCategorySpecificInfo();
    }

    // ---------- Common Info ----------
    private void initializeCommonFields(int displayedIndex) {
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText("\uD83D\uDCDE Phone: " + person.getPhone().value);

        // Create a label for the category
        Label categoryLabel = new Label(person.getCategory().getCategoryName());

        // Assign style class based on category
        switch (person.getCategory().getCategoryName().toLowerCase()) {
        case "customer":
            categoryLabel.getStyleClass().add("category-customer");
            break;
        case "staff":
            categoryLabel.getStyleClass().add("category-staff");
            break;
        case "supplier":
            categoryLabel.getStyleClass().add("category-supplier");
            break;
        default:
            categoryLabel.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
            break;
        }

        category.getChildren().add(categoryLabel);

        // Hide and clear category-specific fields by default
        hideCategorySpecificFields();
    }

    // ---------- Category-Specific Info ----------
    private void displayCategorySpecificInfo() {
        if (person instanceof Customer customer) {
            showCustomerFields(customer);
        } else if (person instanceof Staff staff) {
            showStaffFields(staff);
        } else if (person instanceof Supplier supplier) {
            showSupplierFields(supplier);
        }
    }

    private void showCustomerFields(Customer customer) {
        int customerPoints = customer.getPoints();
        Tier customerTier = customer.getTier();

        points.setText("\u2B50 Points: " + customerPoints);
        tier.setText("\uD83C\uDFC5 Tier: " + (customerTier != null ? customerTier.toString() : "N/A"));
        show(points, tier);
    }

    private void showStaffFields(Staff staff) {
        Shift staffShift = staff.getShift();
        shift.setText("\uD83D\uDD52 Shift: " + (staffShift != null ? staffShift.toString() : "N/A"));
        show(shift);
    }

    private void showSupplierFields(Supplier supplier) {
        String orderList = supplier.listOrders();
        orders.setText("\uD83D\uDCE6 Orders: " + (orderList.isEmpty() ? "None" : orderList));
        show(orders);
    }

    // ---------- Utility Methods ----------
    private void hideCategorySpecificFields() {
        hide(shift, orders, tier, points);
    }

    private void hide(Label... labels) {
        for (Label label : labels) {
            label.setVisible(false);
            label.setManaged(false);
            label.setText("");
        }
    }

    private void show(Label... labels) {
        for (Label label : labels) {
            label.setVisible(true);
            label.setManaged(true);
        }
    }
}

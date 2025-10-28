package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;
import seedu.address.model.person.Supplier;
import seedu.address.model.tier.Tier;

/**
 * Controller for a person detail view popup
 */
public class ViewWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(ViewWindow.class);
    private static final String FXML = "ViewWindow.fxml";

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label staffShiftLabel;

    @FXML
    private Label staffLeavesLabel;

    @FXML
    private Label supplierOrdersLabel;

    @FXML
    private Label customerPointsLabel;

    @FXML
    private Label customerTierLabel;

    @FXML
    private Button closeButton;

    /**
     * Creates a new ViewWindow using the given Stage as root.
     *
     * @param root The Stage to use as the root of this ViewWindow
     */
    public ViewWindow(Stage root) {
        super(FXML, root);
        hideAllExtraFields();
    }

    public ViewWindow() {
        this(new Stage());
    }

    /**
     * Hides all category-specific fields until needed.
     */
    private void hideAllExtraFields() {
        // hide and un-manage staff-specific fields
        staffShiftLabel.setVisible(false);
        staffShiftLabel.setManaged(false);
        staffLeavesLabel.setVisible(false);
        staffLeavesLabel.setManaged(false);
        staffShiftLabel.setText("");
        staffLeavesLabel.setText("");

        // hide and un-manage supplier-specific fields
        supplierOrdersLabel.setVisible(false);
        supplierOrdersLabel.setManaged(false);
        supplierOrdersLabel.setText("");

        customerPointsLabel.setVisible(false);
        customerTierLabel.setVisible(false);
        customerPointsLabel.setManaged(false);
        customerTierLabel.setManaged(false);
        customerPointsLabel.setText("");
        customerTierLabel.setText("");
    }

    /**
     * Populate the view window with the given person's details.
     */
    public void setPerson(Person person) {
        hideAllExtraFields();

        nameLabel.setText(person.getName().toString());
        phoneLabel.setText(person.getPhone().toString());
        emailLabel.setText(person.getEmail().toString());
        addressLabel.setText(person.getAddress().toString());
        categoryLabel.setText(person.getCategory().toString());

        if (person instanceof Staff staff) {
            staffShiftLabel.setVisible(true);
            staffShiftLabel.setManaged(true);
            staffLeavesLabel.setVisible(true);
            staffLeavesLabel.setManaged(true);
            staffShiftLabel.setText("Shift: " + staff.getShift().toString());
            staffLeavesLabel.setText("Remaining Leaves: " + staff.getNumberOfLeaves());
        } else if (person instanceof Supplier supplier) {
            // removed supplierItemLabel
            supplierOrdersLabel.setVisible(true);
            supplierOrdersLabel.setManaged(true);
            // removed supplierItemLabel
            String orders = supplier.listOrders();
            supplierOrdersLabel.setText("Orders:\n" + (orders.isEmpty() ? "No orders" : orders));
        } else if (person instanceof Customer customer) {
            customerPointsLabel.setVisible(true);
            customerTierLabel.setVisible(true);
            customerPointsLabel.setManaged(true);
            customerTierLabel.setManaged(true);
            int points = customer.getPoints();
            Tier tier = customer.getTier();
            customerPointsLabel.setText("Points: " + points);
            customerTierLabel.setText("Tier: " + tier);
        }
    }

    /**
     * Shows the view window and centers it on screen.
     */
    public void show() {
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the view window is showing.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the view window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Brings focus to the view window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    @FXML
    private void handleClose() {
        hide();
    }
}

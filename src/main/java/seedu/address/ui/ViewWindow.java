package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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
    private GridPane backgroundPane;
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
        // Set all formatting dynamically (no hardcoding in FXML)
        backgroundPane.setHgap(10);
        backgroundPane.setVgap(10);
        backgroundPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        backgroundPane.setStyle(
            backgroundPane.getStyle()
            + "; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.18), 12, 0.2, 0, 4;"
            + " border-width: 2; border-color: #ffe066;"
        );
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

        // Set background color to match tag color for each category
        String category = person.getCategory().getCategoryName().toLowerCase();
        String bgColor;
        String textColor = "#222";
        switch (category) {
        case "customer":
            bgColor = "#3e7b91"; // blue (tag color)
            textColor = "white";
            break;
        case "staff":
            bgColor = "#ff1493"; // pink (tag color)
            textColor = "white";
            break;
        case "supplier":
            bgColor = "#4caf50"; // green (tag color)
            textColor = "white";
            break;
        default:
            bgColor = "#888888"; // neutral gray for any unexpected category
            textColor = "white";
            break;
        }
        backgroundPane.setStyle(
            "-fx-background-color: " + bgColor
            + "; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.18), 12, 0.2, 0, 4); "
            + "border-width: 2; border-color: #ffe066;"
            + "; -fx-text-fill: " + textColor + ";"
        );

        // Standard font size for all labels
        String labelStyle = "-fx-font-size: 16px; -fx-text-fill: " + textColor + ";";
        nameLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: " + textColor + ";");
        phoneLabel.setStyle(labelStyle);
        emailLabel.setStyle(labelStyle);
        addressLabel.setStyle(labelStyle);
        categoryLabel.setStyle(labelStyle);
        staffShiftLabel.setStyle(labelStyle);
        staffLeavesLabel.setStyle(labelStyle);
        supplierOrdersLabel.setStyle(labelStyle);
        customerPointsLabel.setStyle(labelStyle);
        customerTierLabel.setStyle(labelStyle);

        // Standardize window size
        Stage stage = (Stage) backgroundPane.getScene().getWindow();
        stage.setWidth(400);
        stage.setHeight(420);
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

package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s1-cs2103t-w12-2.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Command Summary\n\n"
            + String.format("%-18s %-12s %s%n", "Action", "Type", "Format")
            + String.format("%-18s %-12s %s%n", "──────────────────", "────────────",
                    "──────────────────────────────────────────────────────────────────────")
            + String.format("%-18s %-12s %s%n", "Help", "General", "help")
            + "\n"
            + String.format("%-18s %-12s %s%n", "Add", "General",
                    "add n/NAME p/PHONE e/EMAIL a/ADDRESS c/CATEGORY")
            + String.format("%-32s%s%n", "", "e.g., add n/James Ho p/98765432 e/jamesho@example.com")
            + String.format("%-32s%s%n", "", "      a/123, Clementi Rd c/Customer")
            + "\n"
            + String.format("%-18s %-12s %s%n", "List", "General", "list")
            + "\n"
            + String.format("%-18s %-12s %s%n", "Edit", "General",
                    "edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]")
            + String.format("%-32s%s%n", "", "         [c/CATEGORY]")
            + String.format("%-32s%s%n", "", "e.g., edit 2 n/James Lee e/jameslee@example.com")
            + "\n"
            + String.format("%-18s %-12s %s%n", "Find", "General", "find KEYWORD [MORE_KEYWORDS]")
            + String.format("%-32s%s%n", "", "e.g., find James Jake")
            + "\n"
            + String.format("%-18s %-12s %s%n", "View", "General", "view p/PHONE")
            + String.format("%-32s%s%n", "", "e.g., view p/91234567")
            + "\n"
            + String.format("%-18s %-12s %s%n", "Delete", "General", "delete INDEX")
            + String.format("%-32s%s%n", "", "e.g., delete 3")
            + "\n"
            + String.format("%-18s %-12s %s%n", "Clear", "General", "clear")
            + "\n"
            + String.format("%-18s %-12s %s%n", "Exit", "General", "exit")
            + "\n"
            + String.format("%-18s %-12s %s%n", "Update Points", "Customer",
                    "updatePoints p/PHONE b/BILL_AMOUNT")
            + String.format("%-32s%s%n", "", "e.g., updatePoints p/98765432 b/100.00")
            + "\n"
            + String.format("%-18s %-12s %s%n", "Update Shift", "Staff",
                    "updateShift p/PHONE s/SHIFT")
            + String.format("%-32s%s%n", "", "e.g., updateShift p/98765432 s/PM")
            + "\n"
            + String.format("%-18s %-12s %s%n", "Add Order", "Supplier",
                    "addOrder p/PHONE i/ITEM_NAME q/QUANTITY u/UNIT_PRICE")
            + String.format("%-32s%s%n", "", "         d/DELIVERY_DAY")
            + String.format("%-32s%s%n", "", "e.g., addOrder p/91234567 i/Chicken q/20 u/5.60")
            + String.format("%-32s%s%n", "", "      d/every Tuesday")
            + "\n"
            + String.format("%-18s %-12s %s%n", "Update Order", "Supplier",
                    "updateOrder p/PHONE o/ORDER_INDEX [i/ITEM_NAME]")
            + String.format("%-32s%s%n", "", "           [q/QUANTITY] [u/UNIT_PRICE] [d/DELIVERY_DAY]")
            + String.format("%-32s%s%n", "", "e.g., updateOrder p/91234567 o/1 i/Fish")
            + "\n"
            + String.format("%-18s %-12s %s%n", "Delete Order", "Supplier",
                    "deleteOrder p/PHONE o/ORDER_INDEX")
            + String.format("%-32s%s%n", "", "e.g., deleteOrder p/91234567 o/1")
            + "\n\nFor more details, refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}

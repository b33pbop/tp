package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import seedu.address.model.person.Staff;
import seedu.address.testutil.StaffBuilder;

public class PersonCardTest {

    @BeforeAll
    static void initToolkit() {
        // Starts JavaFX runtime once before all tests
        Platform.startup(() -> {});
    }

    @Test
    public void display_staff_displaysShift() {
        Staff staff = new StaffBuilder().withName("Alex Tan")
                .withPhone("81234567")
                .withShift("PM")
                .build();

        PersonCard card = new PersonCard(staff, 1);

        // Verify displayed shift value
        assertEquals("PM", card.getShiftLabel().getText());
    }
}

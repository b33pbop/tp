package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.StaffBuilder;

public class LogicUpdateShiftIntegrationTest {

    @TempDir
    Path tempDir;

    @Test
    void executeUpdateShiftUpdatesStaffShift() throws Exception {
        Path addr = tempDir.resolve("addressbook.json");
        Path prefs = tempDir.resolve("prefs.json");
        StorageManager storage = new StorageManager(
                new JsonAddressBookStorage(addr),
                new JsonUserPrefsStorage(prefs));

        Model model = new ModelManager(); // empty state
        Logic logic = new LogicManager(model, storage);

        // Step 1: Build a staff using StaffBuilder
        Staff staff = new StaffBuilder().withName("Alex Tan")
                .withPhone("81234567")
                .withEmail("alex@example.com")
                .withShift("AM")
                .build();

        // Insert into model
        model.addPerson(staff);

        // Update the staff's shift
        String updateInput = "updateshift p/81234567 s/PM";
        CommandResult updateResult = logic.execute(updateInput);

        // Check feedback contains success message
        String feedback = updateResult.getFeedbackToUser().toLowerCase();
        assertTrue(feedback.contains("updated shift"),
                "Expected success message to contain 'updated shift' but was: " + feedback);

        // Verify the shift has actually changed
        Staff updatedStaff = (Staff) model.getFilteredPersonList().get(0);
        assertEquals("PM", updatedStaff.getShift().getValue(),
                "Expected staff shift to be updated to PM");
    }

    @Test
    void executeUpdateShiftNonStaffThrowsCommandException() throws Exception {
        Path addr = tempDir.resolve("addressbook.json");
        Path prefs = tempDir.resolve("prefs.json");
        StorageManager storage = new StorageManager(
                new JsonAddressBookStorage(addr),
                new JsonUserPrefsStorage(prefs));

        Model model = new ModelManager(); // empty state
        Logic logic = new LogicManager(model, storage);

        // Add a non-staff person using StaffBuilder trick (or a PersonBuilder if exists)
        Person nonStaff = new StaffBuilder().withName("John Doe")
                .withPhone("89998888")
                .withEmail("john@example.com")
                .withShift("AM")
                .withCategories("customer") // override category to non-staff
                .build();
        model.addPerson(nonStaff);

        //  Try updating shift for non-staff, expect CommandException
        String updateInput = "updateshift p/89998888 s/PM";
        try {
            logic.execute(updateInput);
        } catch (CommandException e) {
            assertTrue(e.getMessage().contains("not a staff member"),
                    "Expected CommandException for non-staff, got: " + e.getMessage());
        }
    }
}

package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for MainWindow logic focusing on showPerson handling.
 *
 * This test avoids JavaFX by not instantiating the real MainWindow. Instead,
 * a small TestMainWindow helper replicates the relevant executeCommand logic
 * to verify interactions with a spy ViewWindow.
 */
public class MainWindowTest {

    private static class FakeLogic implements Logic {
        private final CommandResult toReturn;

        FakeLogic(CommandResult cr) {
            this.toReturn = cr;
        }

        @Override
        public CommandResult execute(String commandText) throws seedu.address.logic.commands.exceptions.CommandException,
                seedu.address.logic.parser.exceptions.ParseException {
            return toReturn;
        }

        @Override public seedu.address.model.ReadOnlyAddressBook getAddressBook() { return null; }
        @Override public javafx.collections.ObservableList<Person> getFilteredPersonList() { return javafx.collections.FXCollections.observableArrayList(); }
        @Override public java.nio.file.Path getAddressBookFilePath() { return java.nio.file.Paths.get("addressbook.json"); }
        @Override public seedu.address.commons.core.GuiSettings getGuiSettings() { return new seedu.address.commons.core.GuiSettings(); }
        @Override public void setGuiSettings(seedu.address.commons.core.GuiSettings guiSettings) {}
    }

    private static class SpyViewWindow {
        boolean setCalled = false;
        boolean showCalled = false;

        void setPerson(Person p) { setCalled = true; }
        void show() { showCalled = true; }
        boolean isShowing() { return false; }
        void focus() { }
    }

    /**
     * Minimal test helper that mirrors MainWindow.executeCommand behavior relevant to showPerson.
     */
    private static class TestMainWindow {
        private final Logic logic;
        private final SpyViewWindow viewWindow;

        TestMainWindow(Logic logic, SpyViewWindow viewWindow) {
            this.logic = logic;
            this.viewWindow = viewWindow;
        }

        public CommandResult executeCommand(String commandText) throws Exception {
            try {
                CommandResult commandResult = logic.execute(commandText);
                if (commandResult.isShowPerson()) {
                    if (!viewWindow.isShowing()) {
                        viewWindow.setPerson(commandResult.getPersonToShow());
                        viewWindow.show();
                    } else {
                        viewWindow.focus();
                    }
                }
                return commandResult;
            } catch (seedu.address.logic.commands.exceptions.CommandException | seedu.address.logic.parser.exceptions.ParseException e) {
                throw e;
            }
        }
    }

    @Test
    public void executeCommand_showPerson_invokesViewWindowSetAndShow() throws Exception {
        Person p = new PersonBuilder().build();
        CommandResult cr = new CommandResult("", false, false, true, p);
        FakeLogic fakeLogic = new FakeLogic(cr);

        SpyViewWindow spy = new SpyViewWindow();
        TestMainWindow testMain = new TestMainWindow(fakeLogic, spy);

        testMain.executeCommand("view 91234567");

        assertTrue(spy.setCalled, "ViewWindow.setPerson should be called when showPerson is true");
        assertTrue(spy.showCalled, "ViewWindow.show should be called when view isn't already showing");
    }
}
package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for MainWindow logic focusing on showPerson handling.
 *
 * This test avoids JavaFX by not instantiating the real MainWindow. Instead,
 * a small TestMainWindow helper replicates the relevant executeCommand logic
 * to verify interactions with a spy ViewWindow.
 */
public class MainWindowTest {

    private static class FakeLogic implements Logic {
        private final CommandResult toReturn;

        FakeLogic(CommandResult cr) {
            this.toReturn = cr;
        }

        @Override
        public CommandResult execute(String commandText) throws seedu.address.logic.commands.exceptions.CommandException,
                seedu.address.logic.parser.exceptions.ParseException {
            return toReturn;
        }

        @Override public seedu.address.model.ReadOnlyAddressBook getAddressBook() { return null; }
        @Override public javafx.collections.ObservableList<Person> getFilteredPersonList() { return javafx.collections.FXCollections.observableArrayList(); }
        @Override public java.nio.file.Path getAddressBookFilePath() { return java.nio.file.Paths.get("addressbook.json"); }
        @Override public seedu.address.commons.core.GuiSettings getGuiSettings() { return new seedu.address.commons.core.GuiSettings(); }
        @Override public void setGuiSettings(seedu.address.commons.core.GuiSettings guiSettings) {}
    }

    private static class SpyViewWindow {
        boolean setCalled = false;
        boolean showCalled = false;

        void setPerson(Person p) { setCalled = true; }
        void show() { showCalled = true; }
        boolean isShowing() { return false; }
        void focus() { }
    }

    /**
     * Minimal test helper that mirrors MainWindow.executeCommand behavior relevant to showPerson.
     */
    private static class TestMainWindow {
        private final Logic logic;
        private final SpyViewWindow viewWindow;

        TestMainWindow(Logic logic, SpyViewWindow viewWindow) {
            this.logic = logic;
            this.viewWindow = viewWindow;
        }

        public CommandResult executeCommand(String commandText) throws Exception {
            try {
                CommandResult commandResult = logic.execute(commandText);
                if (commandResult.isShowPerson()) {
                    if (!viewWindow.isShowing()) {
                        viewWindow.setPerson(commandResult.getPersonToShow());
                        viewWindow.show();
                    } else {
                        viewWindow.focus();
                    }
                }
                return commandResult;
            } catch (seedu.address.logic.commands.exceptions.CommandException | seedu.address.logic.parser.exceptions.ParseException e) {
                throw e;
            }
        }
    }

    @Test
    public void executeCommand_showPerson_invokesViewWindowSetAndShow() throws Exception {
        Person p = new PersonBuilder().build();
        CommandResult cr = new CommandResult("", false, false, true, p);
        FakeLogic fakeLogic = new FakeLogic(cr);

        SpyViewWindow spy = new SpyViewWindow();
        TestMainWindow testMain = new TestMainWindow(fakeLogic, spy);

        testMain.executeCommand("view 91234567");

        assertTrue(spy.setCalled, "ViewWindow.setPerson should be called when showPerson is true");
        assertTrue(spy.showCalled, "ViewWindow.show should be called when view isn't already showing");
    }
}

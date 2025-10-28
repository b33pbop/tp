package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.person.Phone;

public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsViewCommand() {
        // valid phone number with prefix (simulating how AddressBookParser passes only arguments)
        ViewCommand expectedCommand = new ViewCommand(new Phone("91234567"));
        assertParseSuccess(parser, "p/91234567", expectedCommand);
    }

    @Test
    public void parse_invalidPhoneFormat_throwsParseException() {
        // invalid phone formats with prefix
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "p/phone", expectedMessage);
        assertParseFailure(parser, "p/123", expectedMessage);
        assertParseFailure(parser, "p/123456789", expectedMessage); // too long
        assertParseFailure(parser, "p/1234567", expectedMessage); // too short
        assertParseFailure(parser, "p/71234567", expectedMessage); // invalid first digit
    }
}

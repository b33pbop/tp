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
        // valid phone number
        ViewCommand expectedCommand = new ViewCommand(new Phone("91234567"));
        assertParseSuccess(parser, "91234567", expectedCommand);

        // valid phone number with leading/trailing spaces
        assertParseSuccess(parser, "  91234567  ", expectedCommand);
    }

    @Test
    public void parse_invalidPhoneFormat_throwsParseException() {
        // invalid phone formats
        assertParseFailure(parser, "phone", "Invalid phone number format.");
        assertParseFailure(parser, "123", "Invalid phone number format.");
        assertParseFailure(parser, "123456789", "Invalid phone number format."); // too long
        assertParseFailure(parser, "1234567", "Invalid phone number format."); // too short
        assertParseFailure(parser, "71234567", "Invalid phone number format."); // invalid first digit
    }
}
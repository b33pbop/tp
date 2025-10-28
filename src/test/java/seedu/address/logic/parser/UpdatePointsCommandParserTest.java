package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdatePointsCommand;
import seedu.address.model.person.Phone;

/**
 * Unit tests for {@code UpdatePointsCommandParser}.
 */
public class UpdatePointsCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdatePointsCommand.MESSAGE_USAGE);

    private final UpdatePointsCommandParser parser = new UpdatePointsCommandParser();

    @Test
    public void parse_missingParameters_failure() {
        // Missing phone
        String missingPhone = " b/200";
        assertParseFailure(parser,
                UpdatePointsCommand.COMMAND_WORD + missingPhone,
                MESSAGE_INVALID_FORMAT);

        // Missing amount
        String missingAmount = " p/81234567";
        assertParseFailure(parser,
                UpdatePointsCommand.COMMAND_WORD + missingAmount,
                MESSAGE_INVALID_FORMAT);

        // Missing both
        String missingBoth = "";
        assertParseFailure(parser,
                UpdatePointsCommand.COMMAND_WORD + missingBoth,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validInput_success() {
        String userInput = "p/81234567 b/200";
        String phone = "81234567";
        double amount = 200.0;

        UpdatePointsCommand expectedCommand = new UpdatePointsCommand(new Phone(phone), amount);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validInputWithDecimalAmount_success() {
        String userInput = "p/81234567 b/123.45";
        String phone = "81234567";
        double amount = 123.45;

        UpdatePointsCommand expectedCommand = new UpdatePointsCommand(new Phone(phone), amount);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidAmount_failure() {
        // Non-numeric
        assertParseFailure(parser,
                "p/81234567 b/abc",
                UpdatePointsCommandParser.MESSAGE_INVALID_AMOUNT);

        // Negative number
        assertParseFailure(parser,
                "p/81234567 b/-50",
                UpdatePointsCommandParser.MESSAGE_INVALID_AMOUNT);
    }
}

package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteOrderCommandParserTest {
    private final DeleteOrderCommandParser parser = new DeleteOrderCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteOrderCommand() throws Exception {
        DeleteOrderCommand command = parser.parse(" p/91234567 i/1");
        assertEquals(new DeleteOrderCommand(91234567, 1), command);
    }

    @Test
    public void parse_missingPhone_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" i/1"));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" p/91234567"));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" p/91234567 i/notanumber"));
    }

    @Test
    public void parse_invalidPhone_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" p/notanumber i/1"));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" p/91234567 i/-1"));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" p/91234567 i/0"));
    }
}

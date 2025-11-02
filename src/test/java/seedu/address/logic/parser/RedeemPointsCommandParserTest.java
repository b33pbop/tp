package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RedeemPointsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Phone;

public class RedeemPointsCommandParserTest {

    private final RedeemPointsCommandParser parser = new RedeemPointsCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        RedeemPointsCommand cmd = parser.parse(" p/91234567 pts/200");
        // we can’t easily assert whole object without equals, but we can assert fields
        RedeemPointsCommand expected = new RedeemPointsCommand(new Phone("91234567"), 200);
        assertEquals(expected, cmd);
    }

    @Test
    public void parse_missingPhone_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" pts/200"));
    }

    @Test
    public void parse_missingPoints_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" p/91234567"));
    }

    @Test
    public void parse_nonIntegerPoints_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" p/91234567 pts/twohundred"));
    }

    @Test
    public void parse_negativePoints_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" p/91234567 pts/-5"));
    }
}

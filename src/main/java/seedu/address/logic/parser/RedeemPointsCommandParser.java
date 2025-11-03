package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RedeemPointsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new RedeemPointsCommand object
 */
public class RedeemPointsCommandParser implements Parser<RedeemPointsCommand> {

    private static final Prefix PREFIX_PHONE = new Prefix("p/");
    private static final Prefix PREFIX_POINTS = new Prefix("pts/");

    @Override
    public RedeemPointsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_POINTS);

        if (!argMultimap.getValue(PREFIX_PHONE).isPresent()
                || !argMultimap.getValue(PREFIX_POINTS).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RedeemPointsCommand.MESSAGE_USAGE));
        }

        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        String ptsStr = argMultimap.getValue(PREFIX_POINTS).get();

        int pts;
        try {
            pts = Integer.parseInt(ptsStr);
        } catch (NumberFormatException e) {
            throw new ParseException("Points to redeem must be an integer.");
        }

        if (pts <= 0) {
            throw new ParseException("Points to redeem must be positive.");
        }

        return new RedeemPointsCommand(phone, pts);
    }
}

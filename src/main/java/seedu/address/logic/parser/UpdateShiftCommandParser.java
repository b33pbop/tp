package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFT;

import seedu.address.logic.commands.UpdateShiftCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Shift;

/**
 * Parses input arguments and creates a new {@code UpdateShiftCommand} object.
 * Command format: update shift p/<phone> s/<shift>
 */
public class UpdateShiftCommandParser implements Parser<UpdateShiftCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code UpdateShiftCommand}
     * and returns an {@code UpdateShiftCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public UpdateShiftCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_SHIFT);

        // ✅ Manually check that both required prefixes are present
        boolean hasPhone = argMultimap.getValue(PREFIX_PHONE).isPresent();
        boolean hasShift = argMultimap.getValue(PREFIX_SHIFT).isPresent();

        if (!hasPhone || !hasShift || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, UpdateShiftCommand.MESSAGE_USAGE));
        }

        // Prevent duplicate prefixes (optional but good practice)
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE, PREFIX_SHIFT);

        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Shift shift = ParserUtil.parseShift(argMultimap.getValue(PREFIX_SHIFT).get());

        return new UpdateShiftCommand(phone, shift);
    }
}

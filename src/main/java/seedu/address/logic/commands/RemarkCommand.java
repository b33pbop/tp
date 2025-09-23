package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark of the person given "
            + "by the index number of the person in the listing. "
            + "Overwrites existing remarks.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_REMARK + "REMARK\n"
            + "Example: remark 2 r/Likes baseball";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Remark command not implemented yet.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Redeems points from customer for benefits
 */
public class RedeemPointsCommand extends Command {

    public static final String COMMAND_WORD = "redeemPoints";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redeems points from a customer "
            + "identified by phone number. "
            + "Parameters: p/PHONE pts/POINTS\n"
            + "Example: " + COMMAND_WORD + " p/98765432 pts/500";

    public static final String MESSAGE_NOT_FOUND =
            "No person found with phone number %s.";
    public static final String MESSAGE_NOT_CUSTOMER =
            "The person with phone number %s is not a customer.";
    public static final String MESSAGE_SUCCESS =
            "Redeemed %d points from %s. Remaining: %d. Tier: %s.";
    public static final String MESSAGE_NOT_ENOUGH_POINTS =
            "%s does not have enough points to redeem %d (current: %d).";

    private final Phone phone;
    private final int pointsToRedeem;

    /**
     * Constructor for redeem points command
     * @param phone phone number
     * @param pointsToRedeem points to redeem
     */
    public RedeemPointsCommand(Phone phone, int pointsToRedeem) {
        this.phone = phone;
        this.pointsToRedeem = pointsToRedeem;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Person target = model.getFilteredPersonList().stream()
                .filter(p -> p.getPhone().equals(phone))
                .findFirst()
                .orElse(null);

        if (target == null) {
            return new CommandResult(String.format(MESSAGE_NOT_FOUND, phone.value));
        }

        if (!(target instanceof Customer)) {
            return new CommandResult(String.format(MESSAGE_NOT_CUSTOMER, phone.value));
        }

        Customer customer = (Customer) target;

        if (customer.getPoints() < pointsToRedeem) {
            return new CommandResult(String.format(MESSAGE_NOT_ENOUGH_POINTS,
                    customer.getName().fullName, pointsToRedeem, customer.getPoints()));
        }

        // mutate
        customer.redeemPoints(pointsToRedeem);

        // notify model so UI updates (AB3 pattern)
        model.setPerson(customer, customer);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                pointsToRedeem,
                customer.getName().fullName,
                customer.getPoints(),
                customer.getTier().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RedeemPointsCommand)
                && phone.equals(((RedeemPointsCommand) other).phone)
                && pointsToRedeem == ((RedeemPointsCommand) other).pointsToRedeem;
    }
}

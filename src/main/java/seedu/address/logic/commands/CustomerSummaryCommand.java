package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.Model;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.tier.Tier;

/**
 * Shows an overview of all customers and their tiers/points.
 */
public class CustomerSummaryCommand extends Command {

    public static final String COMMAND_WORD = "customerSummary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a summary of customers.\n"
            + "Example: " + COMMAND_WORD;

    private static final String NO_CUSTOMERS_MESSAGE = "There are currently no customers in the address book.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Person> persons = model.getFilteredPersonList();

        int totalCustomers = 0;
        int totalPoints = 0;

        // count per tier
        Map<Tier, Integer> tierCounts = new EnumMap<>(Tier.class);
        for (Tier t : Tier.values()) {
            tierCounts.put(t, 0);
        }

        for (Person p : persons) {
            if (p instanceof Customer) {
                Customer c = (Customer) p;
                totalCustomers++;
                totalPoints += c.getPoints();

                Tier tier = c.getTier();
                tierCounts.put(tier, tierCounts.get(tier) + 1);
            }
        }

        if (totalCustomers == 0) {
            return new CommandResult(NO_CUSTOMERS_MESSAGE);
        }

        // Build nice output
        StringBuilder sb = new StringBuilder();
        sb.append("Customer summary:\n");
        sb.append("Total customers: ").append(totalCustomers).append("\n");
        for (Tier t : Tier.values()) {
            sb.append(" - ").append(t.name()).append(": ").append(tierCounts.get(t)).append("\n");
        }
        sb.append("Total points across all customers: ").append(totalPoints);

        return new CommandResult(sb.toString());
    }
}

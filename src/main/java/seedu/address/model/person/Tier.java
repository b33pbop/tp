package seedu.address.model.person;

/**
 * Represents a Customer's membership tier in the address book.
 */
public enum Tier {
    MEMBER(0, 0.00),
    BRONZE(100, 0.05),
    SILVER(500, 0.10),
    GOLD(1000, 0.15),
    PLATINUM(5000, 0.20);

    private final int minPoints;
    private final double discountRate;

    /**
     * Constructs a {@code Tier}
     *
     * @param minPoints Minimum points to reach this tier.
     * @param discountRate A discount rate benefit for a customer in this tier,
     */
    Tier(int minPoints, double discountRate) {
        this.minPoints = minPoints;
        this.discountRate = discountRate;
    }

    public int getMinPoints() {
        return this.minPoints;
    }

    public double getDiscountRate() {
        return this.discountRate;
    }

    /**
     * Returns the current tier based on points.
     */
    public static Tier getTierForPoints(int points) {
        if (points >= PLATINUM.minPoints) {
            return PLATINUM;
        }
        if (points >= GOLD.minPoints) {
            return GOLD;
        }
        if (points >= SILVER.minPoints) {
            return SILVER;
        }
        if (points >= BRONZE.minPoints) {
            return BRONZE;
        }
        return MEMBER;
    }
}

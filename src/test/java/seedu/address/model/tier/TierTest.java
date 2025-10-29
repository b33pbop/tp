package seedu.address.model.tier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class TierTest {
    @Test
    public void testGetters() {
        assertEquals(0, Tier.MEMBER.getMinPoints());
        assertEquals(100, Tier.BRONZE.getMinPoints());
        assertEquals(500, Tier.SILVER.getMinPoints());
        assertEquals(1000, Tier.GOLD.getMinPoints());
        assertEquals(2500, Tier.PLATINUM.getMinPoints());

        assertEquals(0.00, Tier.MEMBER.getDiscountRate());
        assertEquals(0.05, Tier.BRONZE.getDiscountRate());
        assertEquals(0.10, Tier.SILVER.getDiscountRate());
        assertEquals(0.15, Tier.GOLD.getDiscountRate());
        assertEquals(0.20, Tier.PLATINUM.getDiscountRate());
    }

    @Test
    public void getTierForPoints_member_tier() {
        Tier memberTier = Tier.MEMBER;
        assertEquals(memberTier, Tier.getTierForPoints(0));
        assertEquals(memberTier, Tier.getTierForPoints(1));
        assertEquals(memberTier, Tier.getTierForPoints(50));
        assertEquals(memberTier, Tier.getTierForPoints(99));
        assertNotEquals(memberTier, Tier.getTierForPoints(100));
    }

    @Test
    public void getTierForPoints_bronze_tier() {
        Tier memberTier = Tier.BRONZE;
        assertEquals(memberTier, Tier.getTierForPoints(100));
        assertEquals(memberTier, Tier.getTierForPoints(101));
        assertEquals(memberTier, Tier.getTierForPoints(350));
        assertEquals(memberTier, Tier.getTierForPoints(499));
        assertNotEquals(memberTier, Tier.getTierForPoints(500));
    }

    @Test
    public void getTierForPoints_silver_tier() {
        Tier memberTier = Tier.SILVER;
        assertEquals(memberTier, Tier.getTierForPoints(500));
        assertEquals(memberTier, Tier.getTierForPoints(501));
        assertEquals(memberTier, Tier.getTierForPoints(750));
        assertEquals(memberTier, Tier.getTierForPoints(999));
        assertNotEquals(memberTier, Tier.getTierForPoints(1000));
    }

    @Test
    public void getTierForPoints_gold_tier() {
        Tier memberTier = Tier.GOLD;
        assertEquals(memberTier, Tier.getTierForPoints(1000));
        assertEquals(memberTier, Tier.getTierForPoints(1001));
        assertEquals(memberTier, Tier.getTierForPoints(1750));
        assertEquals(memberTier, Tier.getTierForPoints(2499));
        assertNotEquals(memberTier, Tier.getTierForPoints(2500));
    }

    @Test
    public void getTierForPoints_platinum_tier() {
        Tier memberTier = Tier.PLATINUM;
        assertEquals(memberTier, Tier.getTierForPoints(2500));
        assertEquals(memberTier, Tier.getTierForPoints(100000));
    }
}

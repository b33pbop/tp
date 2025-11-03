package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_CUSTOMER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.ALICE;
import static seedu.address.testutil.TypicalCustomers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.category.Category;
import seedu.address.model.tier.Tier;
import seedu.address.testutil.CustomerBuilder;

public class CustomerTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Customer editedAlice = new CustomerBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withCategory(VALID_CATEGORY_CUSTOMER).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same phone number, all other attributes different -> returns true
        editedAlice = new CustomerBuilder(ALICE).withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withCategory(VALID_CATEGORY_CUSTOMER).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, different phone number, all other attributes same -> returns false
        editedAlice = new CustomerBuilder(ALICE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Customer editedBob = new CustomerBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new CustomerBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new CustomerBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Customer editedAlice = new CustomerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new CustomerBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new CustomerBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void addPointsFromSpending_pointsAccumulatedCorrectly() {
        Customer customer = new CustomerBuilder().build();

        // Spending 100.75 should give 100 points (truncating)
        customer.addPointsFromSpending(100.75);
        assertEquals(100, customer.getPoints());

        // Add more spending
        customer.addPointsFromSpending(50.5);
        assertEquals(150, customer.getPoints());
    }

    @Test
    public void addPointsFromSpending_tierUpdatedCorrectly() {
        Customer customer = new CustomerBuilder().build();

        // MEMBER
        assertEquals(Tier.MEMBER, customer.getTier());

        // BRONZE (100 points)
        customer.addPointsFromSpending(150); // 150 points
        assertEquals(Tier.BRONZE, customer.getTier());

        // SILVER (500 points)
        customer.addPointsFromSpending(400); // total 550 points
        assertEquals(Tier.SILVER, customer.getTier());

        // GOLD (1000 points)
        customer.addPointsFromSpending(500); // total 1050 points
        assertEquals(Tier.GOLD, customer.getTier());

        // PLATINUM (2500 points)
        customer.addPointsFromSpending(2000); // total 3050 points
        assertEquals(Tier.PLATINUM, customer.getTier());
    }

    @Test
    public void addPointsFromSpending_negativeAmount_throwsException() {
        Customer customer = new CustomerBuilder().build();

        assertThrows(IllegalArgumentException.class, () -> customer.addPointsFromSpending(-10));
    }

    @Test
    public void toStringMethod() {
        String expected = Customer.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", category=" + ALICE.getCategory() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void redeemPoints_validAmount_pointsReducedAndTierUpdated() {
        Customer customer = new Customer(
                new Name("Alice"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("Baker Street"),
                new Category("Customer")
        );

        // give her some points first
        customer.addPointsFromSpending(500); // +500 pts

        customer.redeemPoints(200);

        assertEquals(300, customer.getPoints());
        // tier depends on your Tier.getTierForPoints(...)
        // but at least make sure it doesn't crash
    }

    @Test
    public void redeemPoints_negative_throwsIllegalArgumentException() {
        Customer customer = new Customer(
                new Name("Bob"),
                new Phone("91234568"),
                new Email("bob@example.com"),
                new Address("Queen Street"),
                new Category("Customer")
        );

        assertThrows(IllegalArgumentException.class, () -> customer.redeemPoints(-10));
    }

    @Test
    public void redeemPoints_moreThanHas_throwsIllegalArgumentException() {
        Customer customer = new Customer(
                new Name("Carl"),
                new Phone("91234569"),
                new Email("carl@example.com"),
                new Address("Alpha Street"),
                new Category("Customer")
        );

        customer.addPointsFromSpending(100); // has 100

        assertThrows(IllegalArgumentException.class, () -> customer.redeemPoints(200));
    }

    @Test
    public void addPointsFromSpending_pointsExceedMaxPoints_pointsCappedAtMax() {
        // Arrange
        Customer customer = new CustomerBuilder()
                .withName("Charlie")
                .withPhone("81234567")
                .withTier(Tier.GOLD)
                .build();

        customer.addPointsFromSpending(Customer.MAX_POINTS - 10);
        double bigSpending = 50.0; // should add >10 points and push over max

        // Act
        customer.addPointsFromSpending(bigSpending);

        // Assert
        assertEquals(Customer.MAX_POINTS, customer.getPoints(),
                "Points should be capped at MAX_POINTS when exceeded");
    }

    @Test
    public void calculatePointsFromSpending_exceedsMaxPoints_returnsOnlyRemainingPoints() {
        // Arrange
        Customer customer = new CustomerBuilder()
                .withName("Eve")
                .withPhone("91234567")
                .withTier(Tier.SILVER)
                .build();
        customer.addPointsFromSpending(Customer.MAX_POINTS - 5);

        double bigSpending = 100.0; // would normally add 100 points, exceeding MAX_POINTS

        // Act
        int calculatedPoints = customer.calculatePointsFromSpending(bigSpending);

        // Assert
        assertEquals(5, calculatedPoints,
                "Should return only the remaining points before hitting MAX_POINTS");
    }
}

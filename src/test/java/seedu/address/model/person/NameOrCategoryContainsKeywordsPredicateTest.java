package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameOrCategoryContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameOrCategoryContainsKeywordsPredicate firstPredicate =
                new NameOrCategoryContainsKeywordsPredicate(firstPredicateKeywordList);
        NameOrCategoryContainsKeywordsPredicate secondPredicate =
                new NameOrCategoryContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameOrCategoryContainsKeywordsPredicate firstPredicateCopy =
                new NameOrCategoryContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword matching name
        NameOrCategoryContainsKeywordsPredicate predicate =
                new NameOrCategoryContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords, one matching name
        predicate = new NameOrCategoryContainsKeywordsPredicate(Arrays.asList("Alice", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords matching name
        predicate = new NameOrCategoryContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_categoryContainsKeywords_returnsTrue() {
        // One keyword matching tag
        NameOrCategoryContainsKeywordsPredicate predicate =
                new NameOrCategoryContainsKeywordsPredicate(Collections.singletonList("Customer"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withCategories("Customer").build()));

        // Multiple keywords, one matching tag
        predicate = new NameOrCategoryContainsKeywordsPredicate(Arrays.asList("Customer", "Staff"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withCategories("Customer").build()));

        // Mixed-case keywords matching tag
        predicate = new NameOrCategoryContainsKeywordsPredicate(Arrays.asList("CustOmER"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withCategories("Customer").build()));

        // Multiple categories, keyword matches one of them
        predicate = new NameOrCategoryContainsKeywordsPredicate(Collections.singletonList("Supplier"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob")
                .withCategories("Customer", "Supplier").build()));
    }

    @Test
    public void test_nameAndCategoryContainsKeywords_returnsTrue() {
        // Different keywords matching name and tag respectively
        NameOrCategoryContainsKeywordsPredicate predicate =
                new NameOrCategoryContainsKeywordsPredicate(Arrays.asList("Alice", "Customer"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withCategories("Customer").build()));
    }

    @Test
    public void test_nameAndCategoryDoNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameOrCategoryContainsKeywordsPredicate predicate =
                new NameOrCategoryContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withCategories("Customer").build()));

        // Non-matching keyword
        predicate = new NameOrCategoryContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withCategories("Customer").build()));

        // Keywords match phone, email and address, but do not match name or categories
        predicate = new NameOrCategoryContainsKeywordsPredicate(Arrays.asList("91234567", "alice@email.com",
                "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withCategories("Customer").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameOrCategoryContainsKeywordsPredicate predicate = new NameOrCategoryContainsKeywordsPredicate(keywords);

        String expected = NameOrCategoryContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

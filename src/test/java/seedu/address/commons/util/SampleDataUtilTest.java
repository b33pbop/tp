package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_coversCategories() {
        Person[] people = SampleDataUtil.getSamplePersons();
        assertTrue(people.length > 0);

        for (Person p : people) {
            // assuming Person#getCategories() returns a
            // Set<Category> and Category#categoryName is accessible or via toString()
            String categoryName = p.getCategory().getCategoryName();
            assertTrue(
                    categoryName.equals("Customer") || categoryName.equals("Supplier") || categoryName.equals("Staff"),
                    "unexpected category " + categoryName
            );
        }
    }
}

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
            p.getCategories().forEach(t -> {
                String v = t.toString().replace("[", "").replace("]", "");
                assertTrue(
                        v.equals("Customer") || v.equals("Supplier") || v.equals("Staff"),
                        "unexpected tag " + v
                );
            });
        }
    }
}

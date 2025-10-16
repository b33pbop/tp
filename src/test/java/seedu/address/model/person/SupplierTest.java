package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.category.Category;

public class SupplierTest {

    @Test
    public void testaddOrder() {
        Phone testPhone = new Phone("82192309");
        Name testName = new Name("Grace Chan");
        Email testEmail = new Email("grace01@gmail.com");
        Address testAddress = new Address("Block 416 Bukit Batok Drive");
        Set<Category> testCat = new HashSet<>();
        testCat.add(new Category("Supplier"));
        Supplier test = new Supplier(testName, testPhone, testEmail, testAddress, testCat, "Computer");
        Order test1 = new Order("Computer", 5, 100.0,
                LocalDate.of(2023, 10, 2));
        Order test2 = new Order("Computer", 10, 120.0,
                LocalDate.of(2023, 11, 2));
        test.addOrder(test1);
        test.addOrder(test2);
        String expected = "5 of Computer (at $100.0 each) to be delivered 2023-10-02"
              + '\n' + "10 of Computer (at $120.0 each) to be delivered 2023-11-02";
        assertEquals(expected, test.listOrders());
    }
}

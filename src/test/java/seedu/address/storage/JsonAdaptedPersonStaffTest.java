package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;


class JsonAdaptedPersonStaffTest {

    @Test
    void toModelType_staffWithoutFields_defaultsApplied() throws Exception {
        String json = """
          {"name":"X","phone":"91234567","email":"x@e.com","address":"Main","category":"Staff"}
            """;
        JsonAdaptedPerson jap = JsonUtil.fromJsonString(json, JsonAdaptedPerson.class);
        Person p = jap.toModelType();
        assertTrue(p instanceof Staff);
        Staff st = (Staff) p;
        assertEquals("AM", st.getShift().getValue());
        assertEquals(14, st.getNumberOfLeaves());
    }

    @Test
    void toModelType_blankShift_defaultsToAM() throws Exception {
        String json = """
          {"name":"X","phone":"91234567","email":"x@e.com","address":"Main",
           "category":"Staff","shift":""}
            """;
        JsonAdaptedPerson jap = JsonUtil.fromJsonString(json, JsonAdaptedPerson.class);
        Staff st = (Staff) jap.toModelType();
        assertEquals("AM", st.getShift().getValue());
    }

    @Test
    void toModelType_increaseLeaves_normalized() throws Exception {
        String json = """
          {"name":"X","phone":"91234567","email":"x@e.com","address":"Main",
           "category":"Staff","numberOfLeaves":20}
            """;
        JsonAdaptedPerson jap = JsonUtil.fromJsonString(json, JsonAdaptedPerson.class);
        Staff st = (Staff) jap.toModelType();
        assertEquals(20, st.getNumberOfLeaves());
    }

    @Test
    void toModelType_decreaseLeaves_normalized() throws Exception {
        String json = """
          {"name":"X","phone":"91234567","email":"x@e.com","address":"Main",
           "category":"Staff","numberOfLeaves":10}
            """;
        JsonAdaptedPerson jap = JsonUtil.fromJsonString(json, JsonAdaptedPerson.class);
        Staff st = (Staff) jap.toModelType();
        assertEquals(10, st.getNumberOfLeaves());
    }


    @Test
    void toModelType_categoryCaseInsensitive_works() throws Exception {
        String json = """
          {"name":"X","phone":"91234567","email":"x@e.com","address":"Main",
           "category":"sTaFf","numberOfLeaves":14}
            """;
        JsonAdaptedPerson jap = JsonUtil.fromJsonString(json, JsonAdaptedPerson.class);
        assertTrue(jap.toModelType() instanceof Staff);
    }

    @Test
    void toModelType_nonStaff_ignoresStaffFields() throws Exception {
        String json = """
          {"name":"Y","phone":"91234567","email":"y@e.com","address":"Main",
           "category":"Customer","shift":"AM","numberOfLeaves":99}
            """;
        JsonAdaptedPerson jap = JsonUtil.fromJsonString(json, JsonAdaptedPerson.class);
        Person p = jap.toModelType();
        assertFalse(p instanceof Staff);
    }

    @Test
    void roundTrip_staff_serializationPreservesFields() throws Exception {
        Staff original = new Staff(
                new seedu.address.model.person.Name("S"),
                new seedu.address.model.person.Phone("91234567"),
                new seedu.address.model.person.Email("s@e.com"),
                new seedu.address.model.person.Address("Baker Street"),
                new seedu.address.model.category.Category("Staff"),
                new seedu.address.model.person.Shift("AM")
        );
        original.addLeaves(3); // 17

        String json = JsonUtil.toJsonString(new JsonAdaptedPerson(original));
        JsonAdaptedPerson read = JsonUtil.fromJsonString(json, JsonAdaptedPerson.class);
        Staff restored = (Staff) read.toModelType();

        assertEquals("AM", restored.getShift().getValue());
        assertEquals(17, restored.getNumberOfLeaves());
    }
}


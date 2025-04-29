package Test;

import Database.DBHandler;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import Backend.*;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class PharmacistTest {

    private Pharmacist pharmacist;
    private DBHandler dbHandler;

    @Before
    public void setUp() {
        // Real DBHandler (assuming in-memory or mock DBHandler)
        dbHandler = DBHandler.getInstance(); // Using the singleton instance

        pharmacist = new Pharmacist(
                "user", "pass", "John Doe", "Male", 30, "john@example.com",
                LocalDate.of(1990, 1, 1),
                1, "LIC123", "Active"
        );
    }

    // 1. Test getters and setters
    @Test
    public void testGettersAndSetters() {
        // Test getters
        assertEquals("Active", pharmacist.getStatus());
        assertEquals(1, pharmacist.getPharmaId());
        assertEquals("LIC123", pharmacist.getLicenseNumber());

        // Test setters
        pharmacist.setStatus("Inactive");
        assertEquals("Inactive", pharmacist.getStatus());

        pharmacist.setPharmaId(42);
        assertEquals(42, pharmacist.getPharmaId());

        pharmacist.setLicenseNumber("NEWLICENSE456");
        assertEquals("NEWLICENSE456", pharmacist.getLicenseNumber());
    }

    // 2. Test AddPrescription method
    @Test
    public void testAddPrescription() throws Exception {
        ArrayList<Medicine> meds = new ArrayList<>();
        meds.add(new Medicine(1, 10, 50.0, LocalDate.now().plusDays(30),
                LocalDate.now().minusDays(60), "Med1", "Approved", "Started", "Type1", 2));

        pharmacist.AddPrescription(2,2, LocalDate.now(), "Patient A", "TypeA", "Dr. Smith", "Hospital X", meds);

        assertEquals(1, pharmacist.getPrescription().size());

        Prescription presc = pharmacist.getPrescription().get(0);
        assertEquals("Patient A", presc.getPatientName());
        assertEquals(2, presc.getPresciId());
    }

    // 3. Test CalculateTotal method
    @Test
    public void testCalculateTotal() {
        ArrayList<Medicine> meds = new ArrayList<>();
        meds.add(new Medicine(1, 10, 50.0, LocalDate.now().plusDays(30),
                LocalDate.now().minusDays(60), "Med1", "Approved", "Started", "Type1", 2));
        meds.add(new Medicine(2, 5, 30.0, LocalDate.now().plusDays(20),
                LocalDate.now().minusDays(70), "Med2", "Approved", "Started", "Type2", 3));

        double total = pharmacist.CalculateTotal(meds, 2);
        assertEquals(160.0, total, 0.001); // (50 + 30) * 2
    }

    // 4. Test CheckQuan method
    @Test
    public void testCheckQuan() {
        ArrayList<Medicine> meds = new ArrayList<>();
        meds.add(new Medicine(1, 10, 50.0, LocalDate.now().plusDays(30),
                LocalDate.now().minusDays(60), "Med1", "Approved", "Started", "Type1", 2));

        assertTrue(pharmacist.CheckQuan(meds, 5));  // enough quantity
        assertFalse(pharmacist.CheckQuan(meds, 15)); // not enough
    }

    // 5. Test DeletePrescription method
    @Test
    public void testDeletePrescription() throws SQLException {
        ArrayList<Medicine> meds = new ArrayList<>();
        meds.add(new Medicine(1, 10, 50.0, LocalDate.now().plusDays(30),
                LocalDate.now().minusDays(60), "Med1", "Approved", "Started", "Type1", 2));

        pharmacist.AddPrescription(1,2, LocalDate.now(), "Patient A", "TypeA", "Dr. Smith", "Hospital X", meds);

        Prescription pres = pharmacist.getPrescription().get(0);
        pharmacist.DeletePrescription(pres);

        assertTrue(pharmacist.getPrescription().isEmpty());  // No prescriptions after deletion
    }

    // 6. Test SellMedicine method
    @Test
    public void testSellMedicine() throws SQLException {
        ArrayList<Medicine> meds = new ArrayList<>();
        meds.add(new Medicine(1, 10, 50.0, LocalDate.now().plusDays(30),
                LocalDate.now().minusDays(60), "Med1", "Approved", "Started", "Type1", 5));

        ArrayList<SoldMedicine> soldMeds = new ArrayList<>();
        soldMeds.add(new SoldMedicine(meds.get(0), "Supplier A"));
        soldMeds.get(0).setQuantity(2); // Sell 2 units

        pharmacist.SellMedicine(soldMeds, meds);

        // After selling, quantity should be updated
        assertEquals(8, meds.get(0).getQuantity());  // (10 - 2)
    }

    // 7. Test SellPrescription method
    @Test
    public void testSellPrescription() throws SQLException {
        ArrayList<Medicine> meds = new ArrayList<>();
        meds.add(new Medicine(1, 10, 50.0, LocalDate.now().plusDays(30),
                LocalDate.now().minusDays(60), "Med1", "Approved", "Started", "Type1", 5));

        Prescription pres = new Prescription(1, 5, LocalDate.now(), "Patient A", "TypeA", "Dr. Smith", "Hospital X");
        pres.setMeds(meds);
        pharmacist.AddPrescription(1,5, LocalDate.now(), "Patient A", "TypeA", "Dr. Smith", "Hospital X", meds);

        pharmacist.SellPrescription(pres);

        // Check if the medicine quantity is updated
        assertEquals(5, meds.get(0).getQuantity());  // After selling, 10 - 5 left
    }
}

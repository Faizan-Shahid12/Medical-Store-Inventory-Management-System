/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Backend.Medicine;
import Backend.Prescription;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PrescriptionTest {

    private Prescription prescription;

    @Before
    public void setUp() {
        prescription = new Prescription(1, 2, LocalDate.of(2025, 5, 10),
                "John Doe", "Restricted", "Dr. Smith", "City Hospital");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(1, prescription.getPresciId());
        assertEquals(2, prescription.getQuantity());
        assertEquals(LocalDate.of(2025, 5, 10), prescription.getAddDate());
        assertEquals("John Doe", prescription.getPatientName());
        assertEquals("Restricted", prescription.getType());
        assertEquals("Dr. Smith", prescription.getDoctorName());
        assertEquals("City Hospital", prescription.getHospitalName());
        assertNull(prescription.getMeds()); // because Meds is not initialized in constructor
    }

    @Test
    public void testSetters() {
        prescription.setPresciId(5);
        prescription.setQuantity(10);
        prescription.setAddDate(LocalDate.of(2026, 6, 15));
        prescription.setPatientName("Jane Smith");
        prescription.setType("Non-Restricted");
        prescription.setDoctorName("Dr. Brown");
        prescription.setHospitalName("General Hospital");

        assertEquals(5, prescription.getPresciId());
        assertEquals(10, prescription.getQuantity());
        assertEquals(LocalDate.of(2026, 6, 15), prescription.getAddDate());
        assertEquals("Jane Smith", prescription.getPatientName());
        assertEquals("Non-Restricted", prescription.getType());
        assertEquals("Dr. Brown", prescription.getDoctorName());
        assertEquals("General Hospital", prescription.getHospitalName());
    }

    @Test
    public void testSetAndGetMeds() {
        ArrayList<Medicine> meds = new ArrayList<>();
        meds.add(new Medicine(1, 5, 10.0, LocalDate.now().plusMonths(6),
                LocalDate.now().minusMonths(1), "MedA", "Approved", "Started", "Tablet", 1001));

        prescription.setMeds(meds);

        assertNotNull(prescription.getMeds());
        assertEquals(1, prescription.getMeds().size());
        assertEquals("MedA", prescription.getMeds().get(0).getName());
    }
}

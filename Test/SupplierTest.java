/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Backend.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SupplierTest {

    private Supplier supplier;
    private Medicine med;

    @Before
    public void setUp() {
        supplier = new Supplier(
                "supUser",
                "supPass",
                "SupName",
                "Male",
                40,
                "sup@example.com",
                LocalDate.of(1985, 3, 10),
                1,
                "LIC12345",
                "Pending"
        );

        med = new Medicine(
                1,
                50,
                30.0,
                LocalDate.of(2025, 5, 10),
                LocalDate.of(2024, 5, 1),
                "MedTest",
                "Pending",
                "Stopped",
                "Tablet",
                2001
        );

        supplier.setSupMed(new ArrayList<>());
        supplier.getSupMed().add(med);
    }

    @Test
    public void testAddRequest() throws SQLException {
        supplier.AddRequest(
                "NewMed",
                "Capsule",
                20,
                15.5,
                LocalDate.of(2025, 6, 30),
                LocalDate.of(2024, 6, 1)
        );

        assertTrue(true); // No exception thrown
    }

    @Test
    public void testDeleteMedicine() throws SQLException {
        supplier.DeleteMedicine(med);

        assertFalse(supplier.getSupMed().contains(med));
    }

    @Test
    public void testRemoveRequest() throws SQLException {
        supplier.RemoveRequest(med);

        assertFalse(supplier.getSupMed().contains(med));
    }

    @Test
    public void testStopSupply() throws SQLException {
        supplier.StopSupply(med);

        assertEquals("Stopped", med.getSupplyStatus());
    }

    @Test
    public void testStartSupply() throws SQLException {
        supplier.StartSupply(med);

        assertEquals("Started", med.getSupplyStatus());
    }

    @Test
    public void testGetPendingRequest() {
        ArrayList<Medicine> pending = supplier.GetPendingRequest();

        assertTrue(pending.contains(med));
    }

    @Test
    public void testGetSupId() {
        assertEquals(1, supplier.getSupId());
    }

    @Test
    public void testSetSupId() {
        supplier.setSupId(3002);
        assertEquals(3002, supplier.getSupId());
    }

    @Test
    public void testGetLicenseNumber() {
        assertEquals("LIC12345", supplier.getLicenseNumber());
    }

    @Test
    public void testSetLicenseNumber() {
        supplier.setLicenseNumber("NEWLIC9876");
        assertEquals("NEWLIC9876", supplier.getLicenseNumber());
    }

    @Test
    public void testGetStatus() {
        assertEquals("Pending", supplier.getStatus());
    }

    @Test
    public void testSetStatus() {
        supplier.setStatus("Approved");
        assertEquals("Approved", supplier.getStatus());
    }

    @Test
    public void testSetSupMed() {
        ArrayList<Medicine> newMeds = new ArrayList<>();
        supplier.setSupMed(newMeds);
        assertEquals(newMeds, supplier.getSupMed());
    }

    @Test
    public void testGetSupMed() {
        ArrayList<Medicine> meds = supplier.getSupMed();
        assertNotNull(meds);
    }
}

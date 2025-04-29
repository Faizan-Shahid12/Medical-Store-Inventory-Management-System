/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Backend.Medicine;
import Backend.SoldMedicine;
import Backend.Supplier;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SoldMedicineTest {

    private SoldMedicine soldMedicine;

    @Before
    public void setUp() {
        Medicine medicine = new Medicine(20, 15, 75.5,
                LocalDate.of(2026, 11, 15),
                LocalDate.of(2023, 3, 3),
                "Cough Syrup",
                "Approved",
                "Started",
                "Syrup",
                201);

        soldMedicine = new SoldMedicine(medicine, "SupplierX");
    }

    @Test
    public void testGetMedicineId() {
        assertEquals(20, soldMedicine.getMedicineId());
    }

    @Test
    public void testSetAndGetQuantity() {
        soldMedicine.setQuantity(5);
        assertEquals(5, soldMedicine.getQuantity());
    }

    @Test
    public void testSetNegativeQuantity() {
        soldMedicine.setQuantity(-5);
        assertEquals(-5, soldMedicine.getQuantity());
    }

    @Test
    public void testSetAndGetPrice() {
        soldMedicine.setPrice(55.5);
        assertEquals(55.5, soldMedicine.getPrice(), 0.001);
    }

    @Test
    public void testSetZeroPrice() {
        soldMedicine.setPrice(0.0);
        assertEquals(0.0, soldMedicine.getPrice(), 0.001);
    }

    @Test
    public void testSetAndGetExpiry() {
        LocalDate newExpiry = LocalDate.of(2025, 5, 5);
        soldMedicine.setExpiry(newExpiry);
        assertEquals(newExpiry, soldMedicine.getExpiry());
    }

    @Test
    public void testExpiryBeforeManufactor() {
        soldMedicine.setExpiry(LocalDate.of(2022, 1, 1));
        soldMedicine.setManufactor(LocalDate.of(2023, 1, 1));
        assertTrue(soldMedicine.getExpiry().isBefore(soldMedicine.getManufactor()));
    }

    @Test
    public void testSetAndGetManufactor() {
        LocalDate newManufactor = LocalDate.of(2022, 2, 2);
        soldMedicine.setManufactor(newManufactor);
        assertEquals(newManufactor, soldMedicine.getManufactor());
    }

    @Test
    public void testSetAndGetName() {
        soldMedicine.setName("New Name");
        assertEquals("New Name", soldMedicine.getName());
    }

    @Test
    public void testSetAndGetStatus() {
        soldMedicine.setStatus("Added");
        assertEquals("Added", soldMedicine.getStatus());
    }

    @Test
    public void testSetAndGetSupplyStatus() {
        soldMedicine.setSupplyStatus("Stopped");
        assertEquals("Stopped", soldMedicine.getSupplyStatus());
    }

    @Test
    public void testSetAndGetType() {
        soldMedicine.setType("Injection");
        assertEquals("Injection", soldMedicine.getType());
    }

    @Test
    public void testSetAndGetSupplierId() {
        soldMedicine.setSup(501);
        assertEquals(501, soldMedicine.getSup());
    }

    @Test
    public void testGetSupplierName_EmptyList() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String supplierName = soldMedicine.getSupplierName(suppliers);
        assertEquals("", supplierName);
    }

    @Test
    public void testGetSupplierName_SupplierNotFound() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        suppliers.add(new Supplier("user1", "pass1", "SupplierA", "Male", 45, "email1@example.com",
                LocalDate.of(1980, 2, 2), 999, "LIC999", "Approved"));

        String supplierName = soldMedicine.getSupplierName(suppliers);
        assertEquals("", supplierName);
    }

    @Test
    public void testGetSupplierName_SupplierFound() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        suppliers.add(new Supplier("user1", "pass1", "SupplierA", "Male", 45, "email1@example.com",
                LocalDate.of(1980, 2, 2), 201, "LIC201", "Approved"));

        String supplierName = soldMedicine.getSupplierName(suppliers);
        assertEquals("SupplierA", supplierName);
    }

    @Test
    public void testSupNameGetter() {
        assertEquals("SupplierX", soldMedicine.getSupName());
    }
}

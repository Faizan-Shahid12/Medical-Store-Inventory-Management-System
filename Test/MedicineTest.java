/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;
import Backend.Medicine;
import Backend.Supplier;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MedicineTest {

    private Medicine medicine;

    @Before
    public void setUp() {
        medicine = new Medicine(1, 20, 15.5,
                LocalDate.of(2025, 12, 31),
                LocalDate.of(2023, 1, 1),
                "TestMedicine",
                "Approved",
                "Started",
                "Tablet",
                1001);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(1, medicine.getMedicineId());
        assertEquals(20, medicine.getQuantity());
        assertEquals(15.5, medicine.getPrice(), 0.001);
        assertEquals(LocalDate.of(2025, 12, 31), medicine.getExpiry());
        assertEquals(LocalDate.of(2023, 1, 1), medicine.getManufactor());
        assertEquals("TestMedicine", medicine.getName());
        assertEquals("Approved", medicine.getStatus());
        assertEquals("Started", medicine.getSupplyStatus());
        assertEquals("Tablet", medicine.getType());
        assertEquals(1001, medicine.getSup());
    }

    @Test
    public void testSetters() {
        medicine.setMedicineId(2);
        medicine.setQuantity(50);
        medicine.setPrice(25.0);
        medicine.setExpiry(LocalDate.of(2026, 6, 15));
        medicine.setManufactor(LocalDate.of(2024, 5, 10));
        medicine.setName("NewMedicine");
        medicine.setStatus("Pending");
        medicine.setSupplyStatus("Stopped");
        medicine.setType("Capsule");
        medicine.setSup(2002);

        assertEquals(2, medicine.getMedicineId());
        assertEquals(50, medicine.getQuantity());
        assertEquals(25.0, medicine.getPrice(), 0.001);
        assertEquals(LocalDate.of(2026, 6, 15), medicine.getExpiry());
        assertEquals(LocalDate.of(2024, 5, 10), medicine.getManufactor());
        assertEquals("NewMedicine", medicine.getName());
        assertEquals("Pending", medicine.getStatus());
        assertEquals("Stopped", medicine.getSupplyStatus());
        assertEquals("Capsule", medicine.getType());
        assertEquals(2002, medicine.getSup());
    }

    @Test
    public void testGetSupplierNameWhenSupplierExists() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        suppliers.add(new Supplier(
                "supplierUser", "supplierPass", "SupplierA", "Male", 35, 
                "supplier@example.com", LocalDate.of(1988, 5, 15),
                1001, "License123", "Approved"
        ));

        String supplierName = medicine.getSupplierName(suppliers);

        assertEquals("SupplierA", supplierName);
    }

    @Test
    public void testGetSupplierNameWhenSupplierDoesNotExist() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        suppliers.add(new Supplier(
                "supplierUser", "supplierPass", "SupplierB", "Female", 30, 
                "supplierB@example.com", LocalDate.of(1992, 3, 22),
                2002, "License456", "Approved"
        ));

        String supplierName = medicine.getSupplierName(suppliers);

        assertEquals("", supplierName); // No supplier with ID 1001
    }
}


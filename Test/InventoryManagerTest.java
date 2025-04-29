/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Backend.*;
import Database.DBHandler;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class InventoryManagerTest {

    private InventoryManager inventoryManager;
    private Medicine medicine1;
    private Medicine medicine2;

    @Before
    public void setUp() {
        ArrayList<Medicine> medicines = new ArrayList<>();

        medicine1 = new Medicine(
                1,
                50,
                20.0,
                LocalDate.of(2027, 5, 10),  // Assume expiry date
                LocalDate.of(2023, 5, 10),
                "Paracetamol",
                "Available",
                "None",
                "Tablet",
                1
        );

        medicine2 = new Medicine(
                2,
                30,
                15.0,
                LocalDate.of(2023, 4, 15), // Expired
                LocalDate.of(2022, 4, 15),
                "ExpiredMed",
                "Available",
                "None",
                "Syrup",
                1
        );

        medicines.add(medicine1);
        medicines.add(medicine2);

        inventoryManager = new InventoryManager(
                "invUser", "invPass", "Inventory Manager", "Male", 35,
                "inv@example.com", LocalDate.of(1989, 8, 20),
                101, "Pending", medicines
        );
    }

    @Test
    public void testAddMedicine() throws SQLException {
        inventoryManager.AddMedicine(medicine1);

        // Since we don't have real DB updates without mock,
        // just check if status is changed in memory
        assertEquals("Added", medicine1.getStatus());
    }

    @Test
    public void testRemoveMedicine() throws SQLException {
        inventoryManager.RemoveMedicine(medicine1);

        // Medicine status should change to "Approved"
        assertEquals("Approved", medicine1.getStatus());
    }

    @Test
    public void testDeleteMedicine() throws SQLException {
        inventoryManager.DeleteMedicine(medicine1);

        // After delete, Medicines should be updated from DB.
        assertNotNull(inventoryManager.getMedicines());
    }

    @Test
    public void testIdentifyExpired() {
        ArrayList<Medicine> expiredMeds = inventoryManager.IdentifyExpired();

        assertFalse(expiredMeds.isEmpty());
        assertEquals("ExpiredMed", expiredMeds.get(0).getName());
    }

    @Test
    public void testRestock() throws SQLException {
        int initialQuantity = medicine1.getQuantity();
        int additionalQuantity = 20;

        inventoryManager.Restock(medicine1.getMedicineId(), additionalQuantity);

        assertEquals(initialQuantity + additionalQuantity, medicine1.getQuantity());
    }

    @Test
    public void testGetStatus() {
        assertEquals("Pending", inventoryManager.getStatus());
    }

    @Test
    public void testGetImId() {
        assertEquals(101, inventoryManager.getImId());
    }
}


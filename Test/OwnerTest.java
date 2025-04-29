package Test;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import Backend.*;
import static org.junit.Assert.*;

public class OwnerTest {

    private Owner owner;
    private Medicine med;

    @Before
    public void setUp() {
        owner = new Owner(
                "ownerUser", 
                "ownerPass", 
                "OwnerName", 
                "Male", 
                45, 
                "owner@example.com", 
                LocalDate.of(1980, 1, 1), 
                1
        );

        med = new Medicine(
                1,
                100,
                50.0,
                LocalDate.of(2024, 5, 30),
                LocalDate.of(2023, 5, 1),
                "TestMed",
                "Pending",
                "None",
                "Tablet",
                1
        );
    }

    @Test
    public void testCreateInventoryManager() {
        ArrayList<Medicine> meds = new ArrayList<>();
        meds.add(med);

        owner.CreateInventoryManager(
                "invUser", 
                "invPass", 
                "InvName", 
                "Female", 
                30, 
                "inv@example.com", 
                LocalDate.of(1993, 8, 15), 
                1001, 
                meds
        );

        assertTrue(true); // No exception means success
    }

    @Test
    public void testCreateSupplier() {
        owner.CreateSupplier(
                "supUser", 
                "supPass", 
                "SupName", 
                "Male", 
                40, 
                "sup@example.com", 
                LocalDate.of(1985, 3, 10), 
                2001, 
                "LIC12345", 
                "Pending"
        );

        assertTrue(true);
    }

    @Test
    public void testCreatePharmacist() {
        owner.CreatePharmacist(
                "pharmaUser", 
                "pharmaPass", 
                "PharmaName", 
                "Female", 
                28, 
                "pharma@example.com", 
                LocalDate.of(1996, 12, 5), 
                3001, 
                "LIC54321", 
                "Pending"
        );

        assertTrue(true);
    }

    @Test
    public void testApproveManager() throws SQLException {
        owner.ApproveManager(1001);

        assertTrue(true);
    }

    @Test
    public void testRejectManager() throws SQLException {
        owner.RejectManager(1001);

        assertTrue(true);
    }

    @Test
    public void testDeleteManager() throws SQLException {
        owner.DeleteManager(1001);

        assertTrue(true);
    }

    @Test
    public void testApproveSupplier() throws SQLException {
        owner.ApproveSupplier(2001);

        assertTrue(true);
    }

    @Test
    public void testRejectSupplier() throws SQLException {
        owner.RejectSupplier(2001);

        assertTrue(true);
    }

    @Test
    public void testDeleteSupplier() throws SQLException {
        owner.DeleteSupplier(2001);

        assertTrue(true);
    }

    @Test
    public void testApprovePharmacist() throws SQLException {
        owner.ApprovePharmacist(3001);

        assertTrue(true);
    }

    @Test
    public void testRejectPharmacist() throws SQLException {
        owner.RejectPharmacist(3001);

        assertTrue(true);
    }

    @Test
    public void testDeletePharmacist() throws SQLException {
        owner.DeletePharmacist(3001);

        assertTrue(true);
    }

    @Test
    public void testApproveMed() throws SQLException {
        owner.ApproveMed(med);

        assertEquals("Approved", med.getStatus());
        assertEquals("Started", med.getSupplyStatus());
    }

    @Test
    public void testRejectMed() throws SQLException {
        owner.RejectMed(med);

        assertEquals("Rejected", med.getStatus());
    }
}

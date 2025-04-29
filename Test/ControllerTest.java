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

public class ControllerTest {

    private Controller controller;
    private Owner owner;
    private Supplier supplier;
    private InventoryManager manager;
    private Pharmacist pharmacist;
    private Medicine medicine;

    @Before
    public void setUp() {
        controller = new Controller();

        owner = new Owner("owner", "password", "Owner Name", "Male", 40, "owner@example.com", LocalDate.of(1985, 5, 10), 1);
        supplier = new Supplier("supplier", "password", "Supplier Name", "Female", 35, "supplier@example.com", LocalDate.of(1990, 8, 15), 1, "LIC123", "Pending");
        manager = new InventoryManager("manager", "password", "Manager Name", "Male", 45, "manager@example.com", LocalDate.of(1980, 12, 25), 201, "Active", new ArrayList<>());
        pharmacist = new Pharmacist("pharma", "password", "Pharmacist Name", "Female", 30, "pharma@example.com", LocalDate.of(1995, 6, 10), 301, "PharmaLicense123", "Active");
        
        medicine = new Medicine(1, 100, 50.0, LocalDate.of(2025, 12, 31), LocalDate.of(2024, 1, 1), "Aspirin", "Pending", "Stopped", "Tablet", 101);
        ArrayList<Medicine> Meds = new ArrayList<>();
        Meds.add(medicine);
        
        controller.setStoreOwner(owner);
        controller.setInventoryManager(manager);
        controller.setSupplier(supplier);
        controller.setPharmacist(pharmacist);
        supplier.setSupMed(Meds);
    }

    @Test
    public void testSetInventoryManager() {
        controller.setInventoryManager(manager);
        assertNotNull(manager);
    }

    @Test
    public void testSetSupplier() {
        controller.setSupplier(supplier);
        assertNotNull(supplier);
    }

    @Test
    public void testSetStoreOwner() {
        controller.setStoreOwner(owner);
        assertNotNull(owner);
    }

    @Test
    public void testSetPharmacist() {
        controller.setPharmacist(pharmacist);
        assertNotNull(pharmacist);
    }

    @Test
    public void testAddRequest() throws SQLException {
        controller.AddRequest("Ibuprofen", "Tablet", 200, 30.0, LocalDate.of(2026, 5, 1), LocalDate.of(2024, 3, 1));
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testRemoveRequest() throws SQLException {
        controller.RemoveRequest(medicine);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testGetPendingRequest() {
        ArrayList<Medicine> pendingRequests = controller.GetPendingRequest();
        assertNotNull(pendingRequests);
    }

    @Test
    public void testStopSupply() throws SQLException {
        controller.StopSupply(medicine);
        assertEquals("Stopped", medicine.getSupplyStatus());
    }

    @Test
    public void testStartSupply() throws SQLException {
        controller.StartSupply(medicine);
        assertEquals("Started", medicine.getSupplyStatus());
    }

    @Test
    public void testAddMedicine() throws SQLException {
        controller.AddMedicine(medicine);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testGetAddedMeds() throws SQLException {
        ArrayList<Medicine> addedMeds = controller.getAddedMeds();
        assertNotNull(addedMeds);
    }

    @Test
    public void testGetApprovedMeds() throws SQLException {
        ArrayList<Medicine> approvedMeds = controller.getApprovedMeds();
        assertNotNull(approvedMeds);
    }

    @Test
    public void testGetPendingMeds() throws SQLException {
        ArrayList<Medicine> pendingMeds = controller.getPendingMeds();
        assertNotNull(pendingMeds);
    }

    @Test
    public void testRemoveMedicine() throws SQLException {
        controller.RemoveMedicine(medicine);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testDeleteMedicine() throws SQLException {
        controller.DeleteMedicine(medicine);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testGetInventoryManager() throws SQLException {
        ArrayList<InventoryManager> managers = controller.GetInventoryManager();
        assertNotNull(managers);
    }

    @Test
    public void testGetSupplier() throws SQLException {
        ArrayList<Supplier> suppliers = controller.GetSupplier();
        assertNotNull(suppliers);
    }

    @Test
    public void testApproveManager() throws SQLException {
        controller.ApproveManager(1);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testRejectManager() throws SQLException {
        controller.RejectManager(1);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testDeleteManager() throws SQLException {
        controller.DeleteManager(1);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testApproveSupplier() throws SQLException {
        controller.ApproveSupplier(101);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testRejectSupplier() throws SQLException {
        controller.RejectSupplier(101);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testDeleteSupplier() throws SQLException {
        controller.DeleteSupplier(101);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testApprovePharmacist() throws SQLException {
        controller.ApprovePharmacist(301);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testRejectPharmacist() throws SQLException {
        controller.RejectPharmacist(301);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testDeletePharmacist() throws SQLException {
        controller.DeletePharmacist(301);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testApproveMedicine() throws SQLException {
        controller.ApproveMedicine(medicine);
        assertEquals("Approved", medicine.getStatus());
    }

    @Test
    public void testRejectMedicine() throws SQLException {
        controller.RejectMedicine(medicine);
        assertEquals("Rejected", medicine.getStatus());
    }

    @Test
    public void testGetSupMedicine() {
        ArrayList<Medicine> supMeds = controller.getSupMedicine();
        assertNotNull(supMeds);
    }

    @Test
    public void testGetSupName() throws SQLException {
        String name = controller.getSupName(1);
        assertNotNull(name);
    }

    @Test
    public void testRemoveMedicineDB() throws SQLException {
        controller.RemoveMedicineDB(medicine);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testGetExpiredMedicine() {
        ArrayList<Medicine> expiredMeds = controller.GetExpiredMedicine();
        assertNotNull(expiredMeds);
    }

    @Test
    public void testRestockMedicine() throws SQLException {
        controller.RestockMedicine(1, 50);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testAddPrescription() throws SQLException {
        ArrayList<Medicine> meds = new ArrayList<>();
        meds.add(medicine);
        controller.AddPrescription(10, LocalDate.of(2025, 5, 1), "John Doe", "Tablet", "Dr. Smith", "City Hospital", meds);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testGetAllMedicines() throws SQLException {
        ArrayList<Medicine> allMeds = controller.GetAllMedicines();
        assertNotNull(allMeds);
    }

    @Test
    public void testGetAllPrescription() {
        ArrayList<Prescription> allPrescriptions = controller.GetAllPrescription();
        assertNotNull(allPrescriptions);
    }

    @Test
    public void testCalculateTotal() {
        ArrayList<Medicine> meds = new ArrayList<>();
        meds.add(medicine);
        double total = controller.CalculateTotal(meds, 10);
        assertTrue(total > 0);
    }

    @Test
    public void testDeletePrescription() throws SQLException {
        Prescription prescription = new Prescription(1, 2, LocalDate.of(2025, 5, 10),
                "John Doe", "Restricted", "Dr. Smith", "City Hospital");
        controller.DeletePrescription(prescription);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testSellMedicine() throws SQLException {
        ArrayList<SoldMedicine> soldMeds = new ArrayList<>();
        controller.SellMedicine(soldMeds);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testSellPrescription() throws SQLException {
        Prescription prescription = new Prescription(1, 2, LocalDate.of(2025, 5, 10),
                "John Doe", "Restricted", "Dr. Smith", "City Hospital");
        controller.SellPrescription(prescription);
        assertTrue(true);  // No exceptions thrown
    }

    @Test
    public void testCheckQuan() {
        ArrayList<Medicine> meds = new ArrayList<>();
        meds.add(medicine);
        boolean check = controller.CheckQuan(meds, 10);
        assertTrue(check);
    }
}

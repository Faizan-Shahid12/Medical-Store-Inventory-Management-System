package Backend;

import Database.DBHandler;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Owner extends User
{
	int OwnerId;
        DBHandler DB = DBHandler.getInstance();
        
	public Owner() 
	{
            super();
	}

	public Owner(String Us, String Pass, String Nam, String Gender1, int age1, String Ema, LocalDate DateofBirth, int ownerId) 
	{
		super(Us, Pass, Nam, Gender1, age1, Ema, DateofBirth);
		OwnerId = ownerId;
	}
	
	public void CreateInventoryManager(String Us, String Pass, String Nam, String Gender,int age1, String Ema, LocalDate DateofBirth) throws SQLException 
        {

             DB.WriteUser(Us, Pass, Nam, Gender, age1, Ema, DateofBirth);

             int Id = DB.GetLastIMId() + 1;
             
             ArrayList<Medicine> Meds = new ArrayList<>();
             
             InventoryManager Manage = new InventoryManager(Us,Pass,Nam,Gender,age1,Ema,DateofBirth,Id, "Pending", Meds);

             DB.WriteInventoryManager(Manage);

         }

         public void CreateSupplier(String Us, String Pass, String Nam, String Gender1, int age1, String Ema, LocalDate DateofBirth, String licenseNumber) throws SQLException 
         {
            
             DB.WriteUser(Us, Pass, Nam, Gender1, age1, Ema, DateofBirth);

             int Id = DB.GetLastSupId() + 1;

             Supplier Sup = new Supplier(Us,Pass,Nam,Gender1,age1,Ema,DateofBirth,Id, licenseNumber ,"Pending");

             DB.WriteSupplier(Sup);
         }

         public void CreatePharmacist(String Us, String Pass, String Nam, String Gender1, int age1, String Ema, LocalDate DateofBirth,String licenseNumber) throws SQLException 
         {
             DB.WriteUser(Us, Pass, Nam, Gender1, age1, Ema, DateofBirth);

             int Id = DB.GetLastPharmaId() + 1;

             Pharmacist  Pharma = new Pharmacist(Us,Pass,Nam,Gender1,age1,Ema,DateofBirth,Id,"Pending", licenseNumber);

             DB.WritePharmacist(Pharma);
         }
        
         public void ApproveManager(int Id) throws SQLException
         {
             DB.ApproveManager(Id);
         }
        
         public void RejectManager(int Id) throws SQLException
         {
             DB.RejectManager(Id);
         }
        
         public void DeleteManager(int Id) throws SQLException
         {
             DB.DeleteManager(Id);
         }
        
         public void ApproveSupplier(int Id) throws SQLException
         {
             DB.ApproveSupplier(Id);
         }
        
         public void RejectSupplier(int Id) throws SQLException
         {
             DB.RejectSupplier(Id);
         }
        
         public void DeleteSupplier(int Id) throws SQLException
         {
             DB.DeleteSupplier(Id);
         }
         
         public void ApprovePharmacist(int Id) throws SQLException
         {
             DB.ApprovePharmacist(Id);
         }
        
         public void RejectPharmacist(int Id) throws SQLException 
         {
             DB.RejectPharmacist(Id);
         }

         public void DeletePharmacist(int Id) throws SQLException 
         {
             DB.DeletePharmacist(Id);
         }
         
         public void ApproveMed(Medicine Med) throws SQLException
         {
             Med.setStatus("Approved");
             Med.setSupplyStatus("Started");
             DB.ApproveMedicine(Med.getMedicineId());
             DB.StartSupply(Med.getMedicineId());
         }
         
         public void RejectMed(Medicine Med) throws SQLException
         {
             Med.setStatus("Rejected");
             DB.RejectMedicine(Med.getMedicineId());
         }
}

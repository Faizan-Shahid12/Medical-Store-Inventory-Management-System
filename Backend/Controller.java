package Backend;

import Database.DBHandler;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controller 
{
	private static InventoryManager Manager;
        private static Owner Own;
	private static Supplier Sup;
	private static Pharmacist Phar;
    
        private static DBHandler DB = DBHandler.getInstance();
        
	public Controller()
	{
		
	}
        
        public void setInventoryManager(InventoryManager Man)
        {
            Manager = Man;
        }
        
	public void setSupplier(Supplier Man)
        {
            Sup = Man;
        }
        
        public void setStoreOwner(Owner Man)
        {
            Own = Man;
        }
        
        public void setPharmacist(Pharmacist Man)
        {
            Phar = Man;
        }
           
        public ArrayList<Owner> getOwner() throws SQLException
        {
            return DB.GetAllOwner();
        }
        
        public ArrayList<Pharmacist> getPharmacist() throws SQLException
        {
            return DB.GetAllPharmacist();
        }
        
        
	public void AddRequest(String Name, String Type, int quan,double price, LocalDate expiry, LocalDate Manufac) throws SQLException
	{
            Sup.AddRequest(Name, Type, quan, price, expiry, Manufac);
	}
	
	public void RemoveRequest(Medicine Med) throws SQLException
	{
		Sup.RemoveRequest(Med);
	}
	
        public ArrayList<Medicine> GetPendingRequest()
        {
            return Sup.GetPendingRequest();
        }
        
	public void StopSupply(Medicine Med) throws SQLException
	{
		Sup.StopSupply(Med);
		
	}
	public void StartSupply(Medicine Med) throws SQLException
	{
		Sup.StartSupply(Med);
	}
	
	public void AddMedicine(Medicine Med) throws SQLException
	{
            Manager.AddMedicine(Med);
	}
        
        public ArrayList<Medicine> getAddedMeds() throws SQLException
        {
            
            ArrayList<Medicine> MedList = DB.GetAddedMedicines();
            
            return MedList; 
        }
	
        public ArrayList<Medicine> getApprovedMeds() throws SQLException
        {
            
            ArrayList<Medicine> MedList = DB.GetApprovedMedicines();
            
            return MedList;
        }
        
        public ArrayList<Medicine> getPendingMeds() throws SQLException
        {
            
            ArrayList<Medicine> MedList = DB.GetPendingMedicines();
            
            return MedList;
        }
        
        public void RemoveMedicine(Medicine Med) throws SQLException
        {
            Manager.RemoveMedicine(Med);
        }
        
        public void DeleteMedicine(Medicine Med) throws SQLException
        {
            Manager.DeleteMedicine(Med);
        }
        
        public ArrayList<InventoryManager> GetInventoryManager() throws SQLException
        {
            return DB.GetAllManagers();
        }
        
        public ArrayList<Supplier> GetSupplier() throws SQLException
        {
            return DB.GetAllSupplier();
        }
        
        public void ApproveManager(int Id) throws SQLException
        {
            Own.ApproveManager(Id);
        }
        
        public void RejectManager(int Id) throws SQLException
        {
            Own.RejectManager(Id);
        }
        
        public void DeleteManager(int Id) throws SQLException
        {
            Own.DeleteManager(Id);
        }
        
        public void ApproveSupplier(int Id) throws SQLException
        {
            Own.ApproveSupplier(Id);
        }
        
        public void RejectSupplier(int Id) throws SQLException
        {
            Own.RejectSupplier(Id);
        }
        
        public void DeleteSupplier(int Id) throws SQLException
        {
            Own.DeleteSupplier(Id);
        }
        
        public ArrayList<Pharmacist> GetPharmacist() throws SQLException
        {
            return DB.GetAllPharmacist();
        }
        
        public void ApprovePharmacist(int Id) throws SQLException
        {
            Own.ApprovePharmacist(Id);
        }
        
        public void RejectPharmacist(int Id) throws SQLException 
        {
            Own.RejectPharmacist(Id);
        }

        public void DeletePharmacist(int Id) throws SQLException 
        {
            Own.DeletePharmacist(Id);
        }
        
        public void ApproveMedicine(Medicine Med) throws SQLException
        {
            Own.ApproveMed(Med);
        }
        
        public void RejectMedicine(Medicine Med) throws SQLException
        {
            Own.RejectMed(Med);
        }
        
        public ArrayList<Medicine> getSupMedicine()
        {
            return Sup.getSupMed();
        }
        
        public String getSupName(int id) throws SQLException
        {
            ArrayList<Supplier> SupList = DB.GetAllSupplier();
            
            for(Supplier Sup: SupList)
            {
                if(Sup.getSupId() == id)
                {
                    return Sup.getName();
                }
            }
            
            return null;
        }
        
        public void RemoveMedicineDB(Medicine Med) throws SQLException
        {
            Sup.DeleteMedicine(Med);
        }
        
        public ArrayList<Medicine> GetExpiredMedicine()
        {
            return Manager.IdentifyExpired();
        }
        
        public void RestockMedicine(int MedId,int Quan) throws SQLException
        {
            Manager.Restock(MedId, Quan);
        }
        
        public void AddPrescription(int Quantity, LocalDate AddDate, String PatientName, String Type, String DoctorName, String HospitalName, ArrayList<Medicine> Meds) throws SQLException
        {
            Phar.AddPrescription(Quantity,AddDate,PatientName,Type,DoctorName,HospitalName,Meds);
        }
            
        public ArrayList<Medicine> GetAllMedicines() throws SQLException
        {
            return DB.GetAllMedicines();
        }
        
        public ArrayList<Prescription> GetAllPrescription()
        {
            return Phar.getPrescription();
        }
        
        public double CalculateTotal(ArrayList<Medicine> Meds, int quan)
        {
            return Phar.CalculateTotal(Meds, quan);
        }
        
        public void DeletePrescription(Prescription Pres) throws SQLException
        {
            Phar.DeletePrescription(Pres);
        }
        
        public void SellMedicine(ArrayList<SoldMedicine> Meds) throws SQLException
        {
            Phar.SellMedicine(Meds,getAddedMeds());
        }
        
        public void SellPrescription(Prescription Pres) throws SQLException
        {
            Phar.SellPrescription(Pres);
        }
        
        public boolean CheckQuan(ArrayList<Medicine> Meds, int Quan)
        {
            return Phar.CheckQuan(Meds, Quan);
        }
}

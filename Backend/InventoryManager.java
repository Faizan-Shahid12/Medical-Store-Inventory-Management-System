package Backend;

import Database.DBHandler;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class InventoryManager extends User
{
	int IMId;
	String Status;
	ArrayList<Medicine> Medicines;
	DBHandler DB = DBHandler.getInstance();
        
	public InventoryManager()
	{
		Medicines = new ArrayList<Medicine>();
	}
	
	public InventoryManager(String Us, String Pass, String Nam, String Gender1, int age1, String Ema,
			LocalDate DateofBirth, int iMId, String status, ArrayList<Medicine> medicines) 
	{
		super(Us, Pass, Nam, Gender1, age1, Ema, DateofBirth);
		IMId = iMId;
		Status = status;
		Medicines = medicines;
	}
        
        public String getStatus()
        {
            return Status;
        }
	
        public int getImId()
        {
            return IMId;
        }
        
	public void AddMedicine(Medicine Med) throws SQLException
	{
		for(Medicine Med1 : Medicines)
		{
			if(Med1 == Med)
			{
				Med1.setStatus("Added");
			}
		}
		
                DB.AddMedicineStore(Med);
	}
	
	public void RemoveMedicine(Medicine Med) throws SQLException
	{
		for(Medicine Med1 : Medicines)
		{
			if(Med1 == Med)
			{
				Med1.setStatus("Approved");
			}
		}
                DB.RemoveMedicine(Med);
	}
        
        public void DeleteMedicine(Medicine Med) throws SQLException
	{
            DB.DeleteMedicine(Med);
            Medicines = DB.GetAllMedicines();
	}
	
	public ArrayList<Medicine> IdentifyExpired()
	{
		ArrayList<Medicine> Expired = new ArrayList<Medicine>();
		
		for(Medicine Med: Medicines)
		{
                        if (Med.getExpiry().isBefore(LocalDate.now()) && !Med.getStatus().equalsIgnoreCase("Pending")) 
			{
                            Expired.add(Med);
			}
		}
		
		return Expired;
	}
	
	public void Restock(int MedId, int quantity) throws SQLException
	{
		for(Medicine Med1: Medicines)
		{
			if(Med1.getMedicineId() == MedId)
			{
                            int Quan = Med1.getQuantity();
                            Quan += quantity;
                            Med1.setQuantity(Quan);
                            DB.RestockMedicine(Med1);
                            break;
			}
		}
	}

}

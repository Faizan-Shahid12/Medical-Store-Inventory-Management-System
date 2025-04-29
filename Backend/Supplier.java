package Backend;

import Database.DBHandler;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Supplier extends User
{
    private int SupId;
    private String LicenseNumber;
    private String Status = "Pending";
    private ArrayList<Medicine> SupMed;
    private DBHandler DB = DBHandler.getInstance();
    
    public Supplier()
    {
        
    }
    
    public Supplier(String Us, String Pass, String Nam, String Gender1, int age1, String Ema, LocalDate DateofBirth, int supId, String licenseNumber, String status) 
    {
		super(Us, Pass, Nam, Gender1, age1, Ema, DateofBirth);
		SupId = supId;
		LicenseNumber = licenseNumber;
		Status = status;
		SupMed = new ArrayList<>();
    }
    
    public void AddRequest(String Name, String Type, int quan,double price, LocalDate expiry, LocalDate Manufac) throws SQLException
    {

    	int LastId = DB.getLastMedId();
    	LastId = LastId + 1;
    	Medicine newMed = new Medicine(LastId,quan,price,expiry,Manufac,Name,"Pending","Stopped",Type,this.SupId);
    	SupMed.add(newMed);
    	DB.AddMedicine(newMed);
    	
    }
    
    public void DeleteMedicine(Medicine Med) throws SQLException
    {
        SupMed.remove(Med);
        DB.DeleteMedicine(Med);
    }
    
    public void RemoveRequest(Medicine Med) throws SQLException
    {
    	SupMed.remove(Med);
    	DB.RemoveMedicine(Med);
    }
    
    public void StopSupply(Medicine Med) throws SQLException
    {
    	for (Medicine Med1: SupMed)
    	{
    		if(Med1 == Med)
    		{
    			Med1.SupplyStatus = "Stopped";
                        DB.StopSupply(Med.getMedicineId());
    			break;
    		}
    	}
    	
    }
    
    public void StartSupply(Medicine Med) throws SQLException
    {
    	for (Medicine Med1: SupMed)
    	{
    		if(Med1 == Med)
    		{
    			Med1.SupplyStatus = "Started";
                        DB.StartSupply(Med.getMedicineId());
                        break;
    		}
    	}
    }
    
    public ArrayList<Medicine> GetPendingRequest()
    {
        ArrayList<Medicine> MedList = new ArrayList<Medicine>();
        
        for(Medicine Med : SupMed)
        {
            if(Med.getStatus().equalsIgnoreCase("Pending"))
            {
                MedList.add(Med);
            }
        }
        
        return MedList;
        
    }
    
    public int getSupId()
    {
            return SupId;
    }
    public void setSupId(int supId)
    {
            SupId = supId;
    }
    public String getLicenseNumber() 
    {
            return LicenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) 
    {
            LicenseNumber = licenseNumber;
    }
    public String getStatus() 
    {
            return Status;
    }
    public void setStatus(String status) 
    {
            Status = status;
    }
    public void setSupMed(ArrayList<Medicine> SupMed1)
    {
            SupMed = SupMed1;
    }
    public ArrayList<Medicine> getSupMed()
    {
        return SupMed;
    }
}

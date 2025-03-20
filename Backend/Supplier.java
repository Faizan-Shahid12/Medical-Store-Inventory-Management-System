package Backend;

import java.time.LocalDate;
import java.util.ArrayList;

public class Supplier extends User
{
	private int SupId;
    private String LicenseNumber;
    private String Status = "Pending";
    private ArrayList<Medicine> SupMed;
    
    public Supplier(String Us, String Pass, String Nam, String Gender1, int age1, String Ema, LocalDate DateofBirth, int supId, String licenseNumber, String status) 
    {
		super(Us, Pass, Nam, Gender1, age1, Ema, DateofBirth);
		SupId = supId;
		LicenseNumber = licenseNumber;
		Status = status;
		SupMed = new ArrayList<>();
    }
    
    public Medicine AddRequest(String Name, String Type, int quan,double price, LocalDate expiry, LocalDate Manufac)
    {

    	//DB.getLastId();
    	int LastId = 0;
    	LastId = LastId + 1;
    	Medicine newMed = new Medicine(LastId,quan,price,expiry,Manufac,Name,"Pending","Stopped",Type,this);
    	SupMed.add(newMed);
    	//In DB
    	
    	return newMed;
    }
    
    public void RemoveRequest(Medicine Med)
    {
    	SupMed.remove(Med);
    	//In DB
    }
    
    public void StopSupply(Medicine Med)
    {
    	for (Medicine Med1: SupMed)
    	{
    		if(Med1 == Med)
    		{
    			Med1.SupplyStatus = "Stopped";
    			//In DB
    			break;
    		}
    	}
    	
    }
    
    public void StartSupply(Medicine Med)
    {
    	for (Medicine Med1: SupMed)
    	{
    		if(Med1 == Med)
    		{
    			Med1.SupplyStatus = "Started";
    			//In DB
    			break;
    		}
    	}
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
    
    
}
